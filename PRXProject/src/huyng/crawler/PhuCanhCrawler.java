package huyng.crawler;

import huyng.constants.CrawlerConstant;
import huyng.utils.XMLHelper;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhuCanhCrawler implements Serializable {
    public static void main(String[] args) {
        try {
//            crawlerProcess();
            getProductCrawler("https://www.phucanh.vn/laptop-lenovo-gaming-ideapad-l340-15irh-81lk01gkvn-core-i5-9300hf/8gb/256gb-ssd/15.6quot-fhd/gtx1050-3gb/win-10/black.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void crawlerProcess() throws IOException, XMLStreamException {
        String document = XMLHelper.getDocument(CrawlerConstant.PAGE_PHU_CANH, CrawlerConstant.PHU_CANH_PAGESIZE_START_SIGNAL, CrawlerConstant.PHU_CANH_PAGESIZE_TAG, null);

        //Get number of paging
        int pageSize = getPageSize(document);

        for (int i = 0; i < pageSize; i++) {
            String pageUrl = CrawlerConstant.PAGE_PHU_CANH + "?page=" + (i + 1);
            System.out.println("Page : " + (i+1));
            eachPageCrawler(pageUrl);
        }
        //Create Thread to crawl each page (use for)
        //Add list Product to mainList
        //Marshalling to xml file
        //Use Schema to validate
        //Save to DB
    }

    /***
     * Crawl 1 page of laptop
     * @param url
     * @throws IOException
     * @throws XMLStreamException
     */
    public static void eachPageCrawler(String url) throws IOException, XMLStreamException {
        String document = XMLHelper.getDocument(url, CrawlerConstant.PHU_CANH_START_SIGNAL, CrawlerConstant.PHU_CANH_TAG, null);
        Iterator<XMLEvent> xmlEvents = XMLHelper.autoAddMissingEndTag(document);
        String tagName = "";

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
                        xmlEvents.next();
                        event = xmlEvents.next();
                        startElement = event.asStartElement();
                        attrHref = startElement.getAttributeByName(new QName("href"));
                        if (attrHref != null) {
                            detailLink = CrawlerConstant.PHU_CANH_DOMAIN + attrHref.getValue();
                        }
                    }
                }
            }
            if (detailLink != null) {
                //TODO:get object from url
                System.out.println("Crawling in Link : " +detailLink);
                getProductCrawler(detailLink);
                //TODO:add to List Product
            }
        }
        //TODO:return List Product
    }

    /***
     * Crawl 1 product detail
     * @param url
     * @throws IOException
     * @throws XMLStreamException
     */
    public static void getProductCrawler(String url) throws IOException, XMLStreamException {
        String document = XMLHelper.getDocument(url, CrawlerConstant.PHU_CANH_DETAIL_START_SIGNAL, CrawlerConstant.PHU_CANH_DETAIL_TAG, null);
        Iterator<XMLEvent> xmlEvents = XMLHelper.autoAddMissingEndTag(document);
        String tagName = null;
        Hashtable<String, String> productDetail = new Hashtable<>();
        int trCount = 0;

        try {
            while (xmlEvents.hasNext()) {
                //Get each detail
                XMLEvent event = xmlEvents.next();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    tagName = startElement.getName().getLocalPart();
                    if ("tr".equals(tagName)) {
                        trCount++;
                        xmlEvents.next(); // next \t
                        xmlEvents.next(); // next <td
                        event = xmlEvents.next();
                        String key = event.asCharacters().getData();
                        xmlEvents.next(); //next </td
                        xmlEvents.next(); //next \t
                        xmlEvents.next(); //next <td
                        event = xmlEvents.next();
                        String value = event.asCharacters().getData();
                        productDetail.put(key, value);
                    }
                }
            }
        }catch (Exception e){
            System.out.println("Number of tr :" +trCount);
        }
        //return new object
        System.out.println(productDetail.toString());
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
        boolean isContinue = false;
        while (eventReader.hasNext()) {
            XMLEvent event = (XMLEvent) eventReader.next();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                tagName = startElement.getName().getLocalPart();
                if ("a".equals(tagName)) {
                    event = (XMLEvent) eventReader.next();
                    if (event.asCharacters().getData().equals("Tiếp theo")) {
                        isContinue = true;
                    } else {
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
                if (isContinue) {
                    document = XMLHelper.getDocument(CrawlerConstant.PAGE_PHU_CANH + "?page=" + result, CrawlerConstant.PHU_CANH_PAGESIZE_START_SIGNAL, CrawlerConstant.PHU_CANH_PAGESIZE_TAG, null);
                    result = getPageSize(document) + "";
                }
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
