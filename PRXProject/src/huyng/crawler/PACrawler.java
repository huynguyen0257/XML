package huyng.crawler;

import huyng.constants.CrawlerConstant;
import huyng.daos.BrandDAO;
import huyng.daos.LaptopDAO;
import huyng.entities.BrandEntity;
import huyng.entities.LaptopEntity;
import huyng.utils.JAXBHelper;
import huyng.utils.TrAxHelper;
import huyng.utils.XMLHelper;
import org.xml.sax.SAXException;
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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO: catch url SSL Exception
//TODO: create method recrawl SSL exception
//TODO: create method import record not valid
public class PACrawler implements Runnable {
    @Override
    public void run() {
        try {
            crawlerProcess();
            int count = 0;
            while (true) {
                for (int i = 0; i < threads.size(); i++) {
                    if (!threads.get(i).isAlive()) count++;
                }
                if (count == threads.size()) {
                    closeStream();
                    break;
                }
                count = 0;
                System.out.println(Thread.currentThread().getName());
                System.out.println("COUNT : " + count);
                Thread.currentThread().sleep(30 * 1000);

            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
//            crawlerProcess();
//            eachPageCrawler("https://www.phucanh.vn/may-tinh-xach-tay-laptop-apple.html&page=1");
//            getLaptopCrawler("https://www.phucanh.vn/laptop-apple-macbook-pro-muhr2-256gb-2019-silver-touch-bar.html",null);//Oke
//            getLaptopCrawler("https://www.phucanh.vn/laptop-asus-a512fa-ej1281t-i5-10210u/8gb/512gb-ssd/15.6quotfhd/vga-on/win10/silver.html",null);//Not oke
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File file = null;
    private static FileWriter writer = null;
    private static final Object LOCK = new Object();
    private static List<Thread> threads = new ArrayList<>();

    public static void closeStream() throws IOException {
        writer.close();
    }

    /***
     * main Crawler PHUC_ANH
     * @throws IOException
     * @throws XMLStreamException
     */
    public static void crawlerProcess() throws IOException, XMLStreamException, InterruptedException {
        file = new File(CrawlerConstant.ERROR_PHUCANH);
        writer = new FileWriter(file);

        //Get brandName,Url of laptop
        Hashtable<String, String> brands = getBrandCrawler();
        brands.forEach((brandName, brandUrl) -> {
            try {
                //Insert Brand to DB
                BrandDAO brandDao = new BrandDAO();
                List<BrandEntity> b = brandDao.findByName(brandName);
                BrandEntity brand = null;
                if (b.size() != 0) {
                    brand = b.get(0);
                } else {
                    brand = new BrandEntity(brandName);
                    brandDao.insert(brand);
                    System.out.println("Insert Brand SUCCESS!!! --- " + brandName);
                }

                //Crawl Laptop
                String document = XMLHelper.getDocument(brandUrl, CrawlerConstant.PHU_CANH_PAGESIZE_START_SIGNAL, CrawlerConstant.PHU_CANH_PAGESIZE_TAG, null);
                int pageSize = getPageSize(document);

                for (int i = 0; i < pageSize; i++) {
                    List<LaptopEntity> laptopOfBrand = new ArrayList<>();
                    String pageUrl = brandUrl + "?page=" + (i + 1);
                    BrandEntity finalBrand = brand;
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            try {
                                laptopOfBrand.addAll(eachPageCrawler(pageUrl));
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (XMLStreamException e) {
                                e.printStackTrace();
                            }

                            //Save ListLaptop to DB
                            LaptopDAO laptopDao = new LaptopDAO();
                            laptopOfBrand.forEach((l) -> {
                                l.setBrand(finalBrand);
                                try {

                                    laptopDao.insert(l);
                                } catch (Exception e) {
                                    try {
                                        synchronized (LOCK) {
                                            writer.write("********************************ERROR insert LAPTOP********************************\n");
                                            writer.write("Message : " + e.getMessage() + "\n");
                                            writer.write(l.toString() + "\n\n\n");
                                            writer.flush();
                                        }
                                    } catch (IOException ioException) {
                                        ioException.printStackTrace();
                                    }
                                }
                            });//End save list laptop
                            System.out.println("********************************DONE LINK : " + pageUrl);
                        }
                    };
                    threads.add(t);
                    t.start();
//                    laptopOfBrand.addAll(eachPageCrawler(pageUrl));
//                    System.out.println("********************************DONE LINK : " + pageUrl);
                }
            } catch (IOException | XMLStreamException e) {
                e.printStackTrace();
            }
            System.out.println("********************************DONE BRAND : " + brandName);
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
    public static List<LaptopEntity> eachPageCrawler(String url) throws IOException, XMLStreamException {
        List<LaptopEntity> result = new ArrayList<>();
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
                                if ("span".equals(tagName)) {
                                    attrHref = startElement.getAttributeByName(new QName("class"));
                                    if (attrHref != null && attrHref.getValue().equals("p-price2 ")) {
                                        xmlEvents.next();
                                        xmlEvents.next();
                                        xmlEvents.next();
                                        event = xmlEvents.next();
                                        String price = event.asCharacters().getData().replaceAll("[.]", "").replaceAll(" ₫ ", "");
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
        laptops.forEach((laptopEntity, laptopUrl) -> {
            //get detail of product from url
            try {
                laptopEntity = getLaptopCrawler(laptopUrl, laptopEntity);
                if (laptopEntity != null) result.add(laptopEntity);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (XPathExpressionException e) {
                e.printStackTrace();
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    /***
     * Crawl 1 product detail
     * @param url
     * @throws IOException
     * @throws XMLStreamException
     */
    public static LaptopEntity getLaptopCrawler(String url, LaptopEntity laptop) throws IOException, TransformerException, ParserConfigurationException, SAXException, XPathExpressionException, JAXBException {
        String document = XMLHelper.getDocument(url, CrawlerConstant.PHU_CANH_DETAIL_START_SIGNAL, CrawlerConstant.PHU_CANH_DETAIL_TAG, new String[]{"<p>.*?</p>"});

        InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
        String xslPath = CrawlerConstant.PA_XSL_PATH;

        ByteArrayOutputStream ps = TrAxHelper.transform(is, xslPath);
        Document newDoc = XMLHelper.parseStringToDOM(ps.toString());

        String exp = "//*[local-name()='laptop']";
//        String exp = "/laptop";
        XPath xPath = XMLHelper.getXPath();

        Node root = (Node) xPath.evaluate(exp, newDoc, XPathConstants.NODE);
        Element name = XMLHelper.CreateElement(newDoc, "name", laptop.getName(), null);
        Element price = XMLHelper.CreateElement(newDoc, "price", laptop.getPrice() + "", null);
        root.appendChild(name);
        root.appendChild(price);


        String xmlContent = TrAxHelper.transformDOMtoString(root);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(xmlContent.getBytes("UTF-8").length);
        outputStream.write(xmlContent.getBytes("UTF-8"), 0, xmlContent.getBytes("UTF-8").length);
        try {
            XMLHelper.validateXMLSchema(TrAxHelper.getXSDPath(LaptopEntity.class), outputStream);
            laptop = (LaptopEntity) JAXBHelper.unmarshalling(xmlContent.getBytes("UTF-8"), LaptopEntity.class);
            return laptop;
        } catch (SAXException | IOException e) {
            synchronized (LOCK) {
                writer.write("********************************NOT VALID********************************" + "\n");
                writer.write(e.getMessage());
                writer.write("ProductUrl = " + url + "\n");
                writer.write("Before TrAx.tranform\n");
                writer.write(document + "\n");
                writer.write("After TrAx.tranform" + "\n");
                writer.write(ps.toString() + "\n");
                writer.write("\n\n");
                writer.flush();
            }
        }
        return null;
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
