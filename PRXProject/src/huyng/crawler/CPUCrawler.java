package huyng.crawler;

import huyng.constants.CrawlerConstant;
import huyng.daos.ProcessorDAO;
import huyng.entities.ProcessorEntity;
import huyng.utils.JAXBHelper;
import huyng.utils.StringHelper;
import huyng.utils.TrAxHelper;
import huyng.utils.XMLHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class CPUCrawler implements Runnable {
    private static final Object LOCK = new Object();
    private static List<Thread> THREADS = new ArrayList<>();
    private static File file = null;
    private static FileWriter writer = null;
    private static String realPath;

    public CPUCrawler(String realPath) {
        this.realPath = realPath;
    }

    @Override
    public void run() {
        try {
            processCrawler();
            int count = 0;
            while (true) {
                for (int i = 0; i < THREADS.size(); i++) {
                    if (!THREADS.get(i).isAlive()) count++;
                }
                if (count == THREADS.size()) {
                    writer.close();
                    break;
                }
                count = 0;
                Thread.currentThread().sleep(30 * 1000);
            }
        } catch (IOException e) {
            Logger.getLogger(CPUCrawler.class.getName()).log(Level.SEVERE, "IOException e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
        } catch (ParserConfigurationException e) {
            Logger.getLogger(CPUCrawler.class.getName()).log(Level.SEVERE, "ParserConfigurationException e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
        } catch (SAXException e) {
            Logger.getLogger(CPUCrawler.class.getName()).log(Level.SEVERE, "SAXException e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
        } catch (XPathExpressionException e) {
            Logger.getLogger(CPUCrawler.class.getName()).log(Level.SEVERE, "XPathExpressionException e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
        } catch (InterruptedException e) {
            Logger.getLogger(CPUCrawler.class.getName()).log(Level.SEVERE, "InterruptedException e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
        }
    }

//    public static void main(String[] args) throws IOException, XPathExpressionException, SAXException, ParserConfigurationException, TransformerException {
//        CPUCrawler crawler = new CPUCrawler();
//        crawler.processCrawler();
//        crawler.getProduct(new ProcessorEntity("Nguyen GIa Huy","https://www.intel.com/content/www/us/en/products/processors/core/i5-processors/i5-7200u.html"));
//        crawler.amdCrawler("5", "2500U");
//        crawler.amdCrawler("7","4800HS");
//    }


    public void processCrawler() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        file = new File(realPath+CrawlerConstant.ERROR_INTEL);
        writer = new FileWriter(file);
        String doc = XMLHelper.getDocument(CrawlerConstant.INTEL_ALL_PRODUCT, CrawlerConstant.INTEL_PAGAING_START_SIGNAL, CrawlerConstant.INTEL_PAGAING_TAG, null);
        Document dom = XMLHelper.parseStringToDOM(doc);
        String exp = "//span[@class='page-total']";
        XPath xPath = XMLHelper.getXPath();
        int pageSize = Integer.parseInt((String) xPath.evaluate(exp, dom, XPathConstants.STRING));
        System.out.println("Intel have total : " + pageSize + " pages");
        for (int i = 0; i < pageSize; i++) {
            String pageUrl = CrawlerConstant.INTEL_ALL_PRODUCT + "?page=" + (i + 1);
            int finalI = i;
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        eachPageCrawler(pageUrl);
                        System.out.println("IntelCrawler SUCCESS with page : " + finalI);
                    } catch (IOException | XMLStreamException e) {
                        Logger.getLogger(CPUCrawler.class.getName()).log(Level.SEVERE, "IOException | XMLStreamException e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
                    }
                }
            };
            t.start();
            THREADS.add(t);
//            eachPageCrawler(pageUrl);
        }
    }

    public void eachPageCrawler(String pageUrl) throws IOException, XMLStreamException {
        List<ProcessorEntity> result = new ArrayList<>();
        String doc = XMLHelper.getDocument(pageUrl, CrawlerConstant.INTEL_EACH_PAGE_START_SIGNAL, CrawlerConstant.INTEL_EACH_PAGE_TAG, new String[]{"itemtype.+\"", "itemscope", "data-picture data-alt=\"(.+|.?)\""});
        Iterator<XMLEvent> xmlEvents = XMLHelper.autoAddMissingEndTag(doc);

        while (xmlEvents.hasNext()) {
            XMLEvent event = xmlEvents.next();
            if (event.isStartElement()) {
                StartElement element = event.asStartElement();
                if ("a".equals(element.getName().getLocalPart())) {
                    Attribute attrs = element.getAttributeByName(new QName("class"));
                    if (attrs != null && attrs.getValue().equals("productTitleHref blade-item-link")) {
                        String productUrl = CrawlerConstant.INTEL_DOMAIN + element.getAttributeByName(new QName("href")).getValue();
                        xmlEvents.next();
                        xmlEvents.next();
                        event = xmlEvents.next();
                        String productName = event.asCharacters().getData();
                        result.add(new ProcessorEntity(productName, productUrl));
                    }
                }
            }
        }
        ProcessorDAO dao = new ProcessorDAO();
        for (int i = 0; i < result.size(); i++) {
            try {
                ProcessorEntity entity = getProduct(result.get(i));
                if (entity != null) {
                    dao.insert(entity);
                } else {
                    synchronized (LOCK) {
                        writer.write("********************************Get CPU NULL********************************\n");
                        writer.write(result.get(i).toString() + "\n\n\n");
                        writer.flush();
                    }
                }
            } catch (IOException e) {
                try {
                    synchronized (LOCK) {
                        writer.write("********************************ERROR insert LAPTOP********************************\n");
                        writer.write("Message : " + e.getMessage() + "\n");
                        writer.write(result.get(i).toString() + "\n\n\n");
                        writer.flush();
                    }
                } catch (IOException ioException) {
                    Logger.getLogger(CPUCrawler.class.getName()).log(Level.SEVERE, "IOException e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
                }
            }
        }
    }

    public ProcessorEntity getProduct(ProcessorEntity entity) throws IOException {
        String productUrl = entity.getModel();
        String doc = "<div>" + XMLHelper.getDocumentWithStartEnd(entity.getModel(), CrawlerConstant.INTEL_PRODUCT_DETAIL_START_SIGNAL, CrawlerConstant.INTEL_PRODUCT_DETAIL_END_SIGNAL, null);//new String[]{"itemscope itemtype=\"http://schema.org/ListItem\"","data-picture data-alt=\"(.+|.?)\""}
        String name = entity.getName();
        Hashtable<String, String> params = new Hashtable<>();
        params.put("brand", "Intel");
        params.put("name", name);
        ByteArrayOutputStream ps = null;
        try {
            ps = TrAxHelper.transform(new ByteArrayInputStream(doc.getBytes("UTF-8")), realPath+CrawlerConstant.INTEL_XSL_PATH, params);
        } catch (TransformerException e) {
            synchronized (LOCK) {
                writer.write("********************************ERROR Transform HTMLString to XMLString ********************************" + "\n");
                writer.write(e.getMessage());
                writer.write("ProductUrl = " + productUrl + "\n");
                writer.write("Before TrAx.tranform\n");
                writer.write(doc + "\n");
                writer.flush();
            }
            return null;
        }

        try {
            XMLHelper.validateXMLSchema(realPath+JAXBHelper.getXSDPath(ProcessorEntity.class), ps);
            String removeNSps = ps.toString().replaceAll("http://huyng/schema/processor", "");
            ByteArrayOutputStream outputStream = StringHelper.getByteArrayFromString(removeNSps);
            entity = (ProcessorEntity) JAXBHelper.unmarshalling(outputStream.toByteArray(), ProcessorEntity.class);
        } catch (SAXException | JAXBException e) {
            synchronized (LOCK) {
                writer.write("********************************NOT VALID********************************" + "\n");
                writer.write(e.getMessage());
                writer.write("ProductUrl = " + productUrl + "\n");
                writer.write("Before TrAx.tranform\n");
                writer.write(doc + "\n");
                writer.write("After TrAx.tranform" + "\n");
                writer.write(ps.toString() + "\n");
                writer.write("\n\n");
                writer.flush();
                entity = null;
            }
        }
        return entity;
    }
}
