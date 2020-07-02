package huyng.crawler;

import huyng.constants.CrawlerConstant;
import huyng.entities.BrandEntity;
import huyng.utils.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPMCrawler {
//    @Override
//    public void run() {
//        //run processCrawler
//        //Check thread to close Stream
//
//
//    }

    public static void main(String[] args) throws IOException, XMLStreamException, XPathExpressionException, SAXException, ParserConfigurationException {
        CPMCrawler crawler = new CPMCrawler();
        crawler.processCrawler();
//        crawler.getPageSize("https://kimlongcenter.com/danh-muc/laptop-dell-449.html");
//        crawler.getPageUrl("https://kimlongcenter.com/danh-muc/laptop-dell-449.html");
    }

    private void processCrawler() throws IOException, XMLStreamException {
        //Get brand of Laptop getBrandCrawler
        Hashtable<BrandEntity, String> brands = getBrandCrawler();

        //ForEach Brand
        brands.forEach((entity, url) -> {
            //Create new Thread for each brand
            Thread t = new Thread() {
                @Override
                public void run() {
                    //Get pageSize getPageSize
                    try {
                        int pagesize = getPageSize(url);
                        System.out.println("Brand Name :" + entity.getName());
                        for (int i = 0; i < pagesize; i++) {
                            String pageUrl =url.substring(0,url.indexOf(".html"))+"/trang/"+(i+1);
                            System.out.println("Page Url : " + pageUrl);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParserConfigurationException e) {
                        e.printStackTrace();
                    } catch (SAXException e) {
                        e.printStackTrace();
                    } catch (XPathExpressionException e) {
                        e.printStackTrace();
                    }

                    //Fori eachPageCrawler
                    //Add List produc to productOfBrand

                    //Inseart productOfBrand

                    //Start + Add Thread to List Thread
                }
            };

            t.start();
        });


    }

    private Hashtable<BrandEntity, String> getBrandCrawler() throws IOException, XMLStreamException {
        Hashtable<BrandEntity, String> brands = new Hashtable<>();
        String document = XMLHelper.getDocument(CrawlerConstant.KIM_LONG_DOMAIN, CrawlerConstant.KIM_LONG_BRAND_START_SIGNAL, CrawlerConstant.KIM_LONG_BRAND_TAG, null);
        Iterator<XMLEvent> xmlEvents = XMLHelper.autoAddMissingEndTag(document);

        XMLEvent event = null;


        while (xmlEvents.hasNext()) {
            event = xmlEvents.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                if ("li".equals(startElement.getName().getLocalPart())) {
                    Attribute attr = startElement.getAttributeByName(new QName("class"));
                    if (attr != null && attr.getValue().equals(" has_child")) {
                        xmlEvents.next();
                        event = xmlEvents.next();
                        startElement = event.asStartElement();
                        String brandurl = startElement.getAttributeByName(new QName("href")).getValue();
                        xmlEvents.next();
                        event = xmlEvents.next();
                        String brandName = event.asStartElement().getAttributeByName(new QName("alt")).getValue();

                        if (!brandName.contains("PC - LCD") && !brandName.contains("Phụ Kiện") && !brandName.contains("Laptop Cũ"))
                            brands.put(new BrandEntity(brandName), brandurl);
                    }

                }
            }
        }

        return brands;
    }

    private int getPageSize(String url) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        String document = XMLHelper.getDocument(url, CrawlerConstant.KIM_LONG_PAGE_SIZE_START_SIGNAL, CrawlerConstant.KIM_LONG_PAGE_SIZE_TAG, new String[]{CrawlerConstant.DUPLICATE_SRC_KL});
        Document dom = XMLHelper.parseStringToDOM(document);
        String exp = "/ul/li[last()]/a";
        XPath xPath = XMLHelper.getXPath();
        Element aTag = (Element) xPath.evaluate(exp, dom, XPathConstants.NODE);
        String link = null;
        if (aTag != null && aTag.hasAttribute("href")){
            link = aTag.getAttribute("href");
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
        }
        return 1;
    }
    //TODO: EDit lai DB bo~ : ,[Webcam],[FingerprintRecognition],[FaceRecognition],[Size]
}
