package huyng.crawler;

import huyng.constants.CrawlerConstant;
import huyng.constants.PageConstant;
import huyng.daos.ProcessorDAO;
import huyng.entities.ProcessorEntity;
import huyng.utils.JAXBHelper;
import huyng.utils.StringHelper;
import huyng.utils.TrAxHelper;
import huyng.utils.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CPUCrawler implements Runnable {
    private static final Object LOCK = new Object();
    private static List<Thread> THREADS = new ArrayList<>();
    private static File file = null;
    private static FileWriter writer = null;
    private static String realPath;
    private static PageConstant pageConstant;

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
                Thread.currentThread().sleep(CrawlerConstant.THREAD_WAIT_CHECK);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            Logger.getLogger(CPUCrawler.class.getName()).log(Level.SEVERE, "Exception e : " + e.getMessage() + "| Line:" + e.getStackTrace()[0].getLineNumber());
        }
    }

    public void processCrawler() throws Exception {
        file = new File(realPath + CrawlerConstant.ERROR_INTEL);
        writer = new FileWriter(file);
        pageConstant = JAXBHelper.unmarshaller(realPath + CrawlerConstant.INTEL_PAGE_CONSTANT_XML, PageConstant.class);

        String doc = XMLHelper.getDocument2(pageConstant.getDataValue("all_product_page"), pageConstant.getPageSizeStartSignal(), pageConstant.getPageSizeTagName(), pageConstant.getPageSizeIgnoreText().getIgnoreText());
        Document dom = XMLHelper.parseStringToDOM(doc);
        String exp = pageConstant.getPageSizeXPath().getAllATagWithoutNext();
        XPath xPath = XMLHelper.getXPath();
        int pageSize = Integer.parseInt((String) xPath.evaluate(exp, dom, XPathConstants.STRING));
        System.out.println("Intel have total : " + pageSize + " pages");

        for (int i = 0; i < pageSize; i++) {
            String pageUrl = pageConstant.getDataValue("all_product_page") + "?page=" + (i + 1);
            int finalI = i;
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        eachPageCrawler(pageUrl);
                        System.out.println("IntelCrawler SUCCESS with page : " + finalI);
                    } catch (Exception e) {
//                        e.printStackTrace();
                            Logger.getLogger(CPUCrawler.class.getName()).log(Level.SEVERE, "Exception e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
                    }
                }
            };
            t.start();
            THREADS.add(t);
            Thread.currentThread().sleep(CrawlerConstant.THREAD_WAIT_CALL);
        }
    }

    public void eachPageCrawler(String pageUrl) throws Exception {
        List<ProcessorEntity> result = new ArrayList<>();
        String doc = XMLHelper.getDocument2(pageUrl, pageConstant.getEachPageStartSignal(), pageConstant.getEachPageTagName(), pageConstant.getEachPageIgnoreText().getIgnoreText());
        Document dom = XMLHelper.parseStringToDOM(doc);
        String exp_container = pageConstant.getEachPageXPath().getContainer();
        String exp_link = pageConstant.getEachPageXPath().getLink();
        String exp_name = pageConstant.getEachPageXPath().getName();
        XPath xPath = XMLHelper.getXPath();
        NodeList aNodes = (NodeList) xPath.evaluate(exp_container, dom, XPathConstants.NODESET);
        for (int i = 0; i < aNodes.getLength(); i++) {
            Node aNode = aNodes.item(i);
            if (aNode != null) {
                String link = pageConstant.getDomainUrl() + xPath.evaluate(exp_link, aNode, XPathConstants.STRING);
                String name = (String) xPath.evaluate(exp_name, aNode, XPathConstants.STRING);
                result.add(new ProcessorEntity(name, link));
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
//                e.printStackTrace();
                Logger.getLogger(CPUCrawler.class.getName()).log(Level.SEVERE, "IOException e : " + e.getMessage() + "| Line:" + e.getStackTrace()[0].getLineNumber());
                try {
                    synchronized (LOCK) {
                        writer.write("********************************ERROR insert LAPTOP********************************\n");
                        writer.write("Message : " + e.getMessage() + "\n");
                        writer.write(result.get(i).toString() + "\n\n\n");
                        writer.flush();
                    }
                } catch (IOException ioException) {
//                    e.printStackTrace();
                    Logger.getLogger(CPUCrawler.class.getName()).log(Level.SEVERE, "IOException e : " + e.getMessage() + "| Line:" + e.getStackTrace()[0].getLineNumber());
                }
            }
        }
    }

    public ProcessorEntity getProduct(ProcessorEntity entity) throws IOException {
        String productUrl = entity.getModel();
        String doc = "<div>" + XMLHelper.getDocumentWithStartEnd(entity.getModel(), pageConstant.getDetailPageStartSignal(), pageConstant.getDetailPageEndSignal(), pageConstant.getDetailPageIgnoreText().getIgnoreText());
        String name = entity.getName();
        Hashtable<String, String> params = new Hashtable<>();
        params.put("brand", "Intel");
        params.put("name", name);
        ByteArrayOutputStream ps = null;
        try {
            ps = TrAxHelper.transform(new ByteArrayInputStream(doc.getBytes("UTF-8")), realPath + CrawlerConstant.INTEL_XSL_PATH, params);
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
            XMLHelper.validateXMLSchema(realPath + JAXBHelper.getXSDPath(ProcessorEntity.class), ps);
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
