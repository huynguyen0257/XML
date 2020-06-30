package huyng.crawler;

import huyng.constants.CrawlerConstant;
import huyng.entities.LaptopEntity;
import huyng.utils.JAXBHelper;
import huyng.utils.TrAxUtils;
import huyng.utils.XMLHelper;
import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhuCanhCrawler implements Serializable {
    public static void main(String[] args) {
        try {
//            crawlerProcess();
            eachPageCrawler("https://www.phucanh.vn/may-tinh-xach-tay-laptop-apple.html&page=1");
//            getProductCrawler("https://www.phucanh.vn/laptop-apple-macbook-pro-muhr2-256gb-2019-silver-touch-bar.html",null);//Oke
//            getProductCrawler("https://www.phucanh.vn/laptop-apple-macbook-pro-mv962-saa-256gb-2019-space-gray-touch-bar.html",null);//Not oke
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * main Crawler PHUC_ANH
     * @throws IOException
     * @throws XMLStreamException
     */
    public static void crawlerProcess() throws IOException, XMLStreamException {
        //Get brandName,Url of laptop
        Hashtable<String, String> brand = getBrandCrawler();

        brand.forEach((brandName, brandUrl) -> {
//        testbrand.forEach((brandName, brandUrl) -> {
            try {
                //TODO: Check brand on DB to create new


                //Get html
                String document = XMLHelper.getDocument(brandUrl, CrawlerConstant.PHU_CANH_PAGESIZE_START_SIGNAL, CrawlerConstant.PHU_CANH_PAGESIZE_TAG, null);

                //Get number of paging
                int pageSize = getPageSize(document);

                for (int i = 0; i < pageSize; i++) {
                    String pageUrl = brandUrl + "?page=" + (i + 1);
                    System.out.println("********************************CategoryUrl With PageSize : "+pageUrl);


                    //Create Thread to crawl each page (use for)
                    Thread t = new Thread(){
                        @Override
                        public void run() {
                            try {
                                eachPageCrawler(pageUrl);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (XMLStreamException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    t.start();
                }

                //Add list Product to mainList
                //Marshalling to xml file
                //Use Schema to validate
                //Save to DB
            } catch (IOException | XMLStreamException e) {
                e.printStackTrace();
            }
        });

    }

    /***
     * Get brand Name and URL
     * @return
     * @throws IOException
     * @throws XMLStreamException
     */
    public static Hashtable<String, String> getBrandCrawler() throws IOException, XMLStreamException {
        String document = XMLHelper.getDocument(CrawlerConstant.PHU_CANH_DOMAIN, CrawlerConstant.PHU_CANH_CATEGORIES_START_SIGNAL, CrawlerConstant.PHU_CANH_CATEGORIES_TAG, new String[]{"&nbsp;"});
        Iterator<XMLEvent> xmlEvents = XMLHelper.autoAddMissingEndTag(document);
        String tagName = "";
        Hashtable<String, String> categories = new Hashtable<>();
        String brandUrl = null;

        while (xmlEvents.hasNext()) {
            brandUrl = null;
            //parse to get detail link
            XMLEvent event = xmlEvents.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if ("div".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("class"));
                    if (attrHref != null && attrHref.getValue().equals("position-relative d-inline-block")) {
                        //get brandUrl
                        xmlEvents.next();
                        event = xmlEvents.next();
                        startElement = event.asStartElement();
                        attrHref = startElement.getAttributeByName(new QName("href"));
                        if (attrHref != null) {
                            brandUrl = CrawlerConstant.PHU_CANH_DOMAIN + attrHref.getValue();
                        }
//                        xmlEvents.next();
                        String brandName = xmlEvents.next().asCharacters().getData();
                        categories.put(brandName, brandUrl);
                    }
                }
            }
        }
        return categories;
    }

    /***
     * Crawl 1 page of laptop
     * @param url
     * @throws IOException
     * @throws XMLStreamException
     */
    public static void eachPageCrawler(String url) throws IOException, XMLStreamException {
        String document = XMLHelper.getDocument(url, CrawlerConstant.PHU_CANH_EACH_PAGE_START_SIGNAL, CrawlerConstant.PHU_CANH_EACH_PAGE_TAG, new String[]{"&quot;"});
        Iterator<XMLEvent> xmlEvents = XMLHelper.autoAddMissingEndTag(document);
        String tagName = "";
        Hashtable<LaptopEntity, String> laptops = new Hashtable<>();

        //while each product available
        while (xmlEvents.hasNext()) {
            String detailLink = null;
            //parse to get detail link
            XMLEvent event = xmlEvents.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if ("div".equals(tagName)) {
                    Attribute attrHref = startElement.getAttributeByName(new QName("class"));
                    if (attrHref != null && attrHref.getValue().equals("p-container")) {
                        LaptopEntity laptop = new LaptopEntity();
                        //get detail Link
                        xmlEvents.next();
                        event = xmlEvents.next();
                        startElement = event.asStartElement();
                        attrHref = startElement.getAttributeByName(new QName("href"));
                        if (attrHref != null) {
                            detailLink = CrawlerConstant.PHU_CANH_DOMAIN + attrHref.getValue();
                        }

                        //get Laptop name
                        while (xmlEvents.hasNext()) {
                            if (event.isStartElement()) {
                                startElement = event.asStartElement();
                                tagName = startElement.getName().getLocalPart();
                                if ("h3".equals(tagName)) {
                                    event = xmlEvents.next();
                                    String productName = event.asCharacters().getData();
                                    laptop.setName(productName);
                                }
                                if ("span".equals(tagName)){
                                    attrHref = startElement.getAttributeByName(new QName("class"));
                                    if (attrHref != null && attrHref.getValue().equals("p-price2 ")) {
                                        xmlEvents.next();
                                        xmlEvents.next();
                                        xmlEvents.next();
                                        event = xmlEvents.next();
                                        String price = event.asCharacters().getData().replaceAll("[.]","").replaceAll(" ₫ ","");
                                        laptop.setPrice(Integer.parseInt(price));
                                        laptops.put(laptop, detailLink);
                                        break;
                                    }
                                }
                            }
                            event = xmlEvents.next();
                        }
                    }
                }
            }
        }
        //get each product of Brand
        laptops.forEach((laptopEntity, laptopUrl) ->{
            //get detail of product from url
            try {
                getProductCrawler(laptopUrl,laptopEntity);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (org.xml.sax.SAXException e) {
                e.printStackTrace();
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        });
        //TODO:add to List Product
        //TODO:return List Product
    }

    /***
     * Crawl 1 product detail
     * @param url
     * @throws IOException
     * @throws XMLStreamException
     */
    public static void getProductCrawler(String url, LaptopEntity productEntity) throws IOException, TransformerException, SAXException, ParserConfigurationException, org.xml.sax.SAXException, XPathExpressionException, JAXBException {
        String document = XMLHelper.getDocument(url, CrawlerConstant.PHU_CANH_DETAIL_START_SIGNAL, CrawlerConstant.PHU_CANH_DETAIL_TAG, new String[]{"<p>.*?</p>"});
        String tagName = null;
        Hashtable<String, String> productDetail = new Hashtable<>();

        InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
        String xslPath = TrAxUtils.getXSLPath(LaptopEntity.class);
        if (xslPath == null) throw new IOException("Dose not have support this Entity!!!");
        //TODO: Delete after test
        System.out.println("ProductUrl = "+url);
        System.out.println(document);
        ByteArrayOutputStream ps = TrAxUtils.transform(is, xslPath);
        Document newDoc = XMLHelper.parseStringToDOM(ps.toString());

        String exp = "//*[local-name()='laptop']";
//        String exp = "/laptop";
        XPath xPath = XMLHelper.getXPath();

        Node root = (Node) xPath.evaluate(exp,newDoc, XPathConstants.NODE);
        Element name = XMLHelper.CreateElement(newDoc,"name",productEntity.getName(),null);
        Element price = XMLHelper.CreateElement(newDoc,"price",productEntity.getPrice()+"",null);
        root.appendChild(name);
        root.appendChild(price);


        String xmlContent = TrAxUtils.transform(root);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(xmlContent.getBytes("UTF-8").length);
        outputStream.write(xmlContent.getBytes("UTF-8"),0,xmlContent.getBytes("UTF-8").length);
        if (XMLHelper.validateXMLSchema(TrAxUtils.getXSDPath(LaptopEntity.class),outputStream)){
            productEntity = (LaptopEntity) JAXBHelper.unmarshalling(xmlContent.getBytes("UTF-8"),LaptopEntity.class);
            System.out.println(productEntity.toString());
            System.out.println("DOME");
            //TODO : Recoding JPA DAO
//            return productEntity;
        }else{
                System.out.println("NOT VALID!!!");
                System.out.println(ps.toString());

        }
    }

    /***
     * Get page size of Phu Canh
     * @param document
     * @return
     * @throws IOException
     * @throws XMLStreamException
     */
    private static int getPageSize(String document) throws IOException, XMLStreamException {
        document = document.trim();
        XMLEventReader eventReader = XMLHelper.parseStringToXMLEventReader(document);
        String tagName = "";
        String link = "";
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if ("a".equals(tagName)) {
                    event = (XMLEvent) eventReader.next();
                    if (!event.asCharacters().getData().equals("Tiếp theo")) {
                        Attribute attrHref = startElement.getAttributeByName(new QName("href"));
                        link = attrHref == null ? "" : attrHref.getValue();
                    }
                }
            }//End if start element
        }//end While
        if (link != null && !link.isEmpty()) {
            String regex = "[0-9]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(link);
            if (matcher.find()) {
                String result = matcher.group();
                try {
                    int number = Integer.parseInt(result);
                    return number;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return 1;
    }
}
