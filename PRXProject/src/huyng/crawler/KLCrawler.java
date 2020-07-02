package huyng.crawler;

import com.sun.org.apache.xerces.internal.dom.DeferredElementNSImpl;
import huyng.constants.CrawlerConstant;
import huyng.daos.BrandDAO;
import huyng.daos.LaptopDAO;
import huyng.entities.BrandEntity;
import huyng.entities.LaptopEntity;
import huyng.utils.JAXBHelper;
import huyng.utils.TrAxHelper;
import huyng.utils.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KLCrawler implements Runnable {
    private static final Object LOCK = new Object();
    private static List<Thread> THREADS = new ArrayList<>();
    private static File file = null;
    private static FileWriter writer = null;

    @Override
    public void run() {
        try {
            //run processCrawler
            processCrawler();

            //Check thread to close Stream
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
                System.out.println(Thread.currentThread().getName());
                System.out.println("COUNT : " + count);
                Thread.currentThread().sleep(30 * 1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, XMLStreamException, InterruptedException {
        KLCrawler crawler = new KLCrawler();
        crawler.processCrawler();

//        crawler.getPageSize("https://kimlongcenter.com/danh-muc/laptop-dell-449.html");
//        crawler.getPageUrl("https://kimlongcenter.com/danh-muc/laptop-dell-449.html");
//        crawler.eachPageCrawler("https://kimlongcenter.com/danh-muc/laptop-asus-452/trang/1");
//        crawler.getLaptopCrawler("https://kimlongcenter.com/san-pham/asus-rog-strix-g-g531g-val319t-i7-9750h-3902.html", new LaptopEntity("NguyenGiaHuy", 123612874));
    }

    private void processCrawler() throws IOException, XMLStreamException, InterruptedException {
        file = new File(CrawlerConstant.ERROR_KIMLONG);
        writer = new FileWriter(file);
        Hashtable<BrandEntity, String> brands = getBrandCrawler();
        brands.forEach((brand, url) -> {
            //Insert Brand to DB
            BrandDAO brandDao = new BrandDAO();
            List<BrandEntity> b = brandDao.findByName(brand.getName());
            if (b.size() != 0) {
                brand = b.get(0);
            } else {
                brandDao.insert(brand);
                System.out.println("Insert Brand SUCCESS!!! --- " + brand.getName());
            }
            LaptopDAO laptopDAO = new LaptopDAO();
            BrandEntity finalBrand = brand;
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        List<LaptopEntity> productOfBrands = getLaptopByBrandUrl(url);

                        //Inseart productOfBrand
                        productOfBrands.forEach(l -> {
                            if (!laptopDAO.checkExisted(l.getModel())) {
                                l.setBrand(finalBrand);
                                laptopDAO.insert(l);
                            } else {
                                synchronized (LOCK) {
                                    try {
                                        writer.write("********************************EXISTED********************************" + "\n");
                                        writer.write("Name : " + l.getName());
                                        writer.write("Model : " + l.getModel());
                                        writer.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
            THREADS.add(t);
//            try {
//                List<LaptopEntity> productOfBrands = getLaptopByBrandUrl(url);
//
//                //Inseart productOfBrand
//                productOfBrands.forEach(l -> {
//                    if (!laptopDAO.checkExisted(l.getModel())) {
//                        l.setBrand(finalBrand);
//                        laptopDAO.insert(l);
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
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

    private static int getPageSize(String url) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        String document = XMLHelper.getDocument(url, CrawlerConstant.KIM_LONG_PAGE_SIZE_START_SIGNAL, CrawlerConstant.KIM_LONG_PAGE_SIZE_TAG, new String[]{CrawlerConstant.DUPLICATE_SRC_KL});
        Document dom = XMLHelper.parseStringToDOM(document);
        String exp = "/ul/li[last()]/a";
        XPath xPath = XMLHelper.getXPath();
        Element aTag = (Element) xPath.evaluate(exp, dom, XPathConstants.NODE);
        String link = null;
        if (aTag != null && aTag.hasAttribute("href")) {
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
                        synchronized (LOCK) {
                            writer.write("********************************ERROR Xpath.evaluate********************************" + "\n");
                            writer.write("Exception: " + e.getMessage() + "\n\n");
                        }
                    }
                }
            }
        }
        return 1;
    }

    private Hashtable<LaptopEntity, String> eachPageCrawler(String url) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        Hashtable<LaptopEntity, String> result = new Hashtable<>();
        String document = XMLHelper.getDocument(url, CrawlerConstant.KIM_LONG_EACH_PAGE_START_SIGNAL, CrawlerConstant.KIM_LONG_EACH_PAGE_TAG, new String[]{CrawlerConstant.DUPLICATE_SRC_KL});
        Document dom = XMLHelper.parseStringToDOM(document);
        String exp_a = "/div/div/div/a";
        String exp_name = "div[@class='product-info']/h3/text()";
        String exp_price = "div[@class='product-info']/div/p[@class='price']";
        XPath xPath = XMLHelper.getXPath();
        NodeList aNodes = (NodeList) xPath.evaluate(exp_a, dom, XPathConstants.NODESET);
        for (int i = 0; i < aNodes.getLength(); i++) {
            Node aNode = aNodes.item(i);
            if (aNode != null) {
                String link = null;
                Element element = (Element) aNode;
                if (element.hasAttribute("href")) {
                    link = element.getAttribute("href");
                }

                String name = (String) xPath.evaluate(exp_name, aNode, XPathConstants.STRING);
                String priceString = (String) xPath.evaluate(exp_price, aNode, XPathConstants.STRING);
                priceString = priceString.replaceAll("[.]", "")
                        .substring(0, priceString.indexOf(' ') - 2);
                int price = Integer.parseInt(priceString);
                LaptopEntity laptopEntity = new LaptopEntity(name, price);
                result.put(laptopEntity, link);
            }
        }
        return result;
    }

    private List<LaptopEntity> getLaptopByBrandUrl(String url) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        List<LaptopEntity> laptops = new ArrayList<>();
        int pagesize = getPageSize(url);
//                        System.out.println("Brand Name :" + entity.getName());
        for (int i = 0; i < pagesize; i++) {
            String pageUrl = url.substring(0, url.indexOf(".html")) + "/trang/" + (i + 1);
            Hashtable<LaptopEntity, String> listLaptopLink = eachPageCrawler(pageUrl);
            listLaptopLink.forEach((laptopEntity, laptopDetailUrl) -> {
                System.out.println("LaptopName: " + laptopEntity.getName() + "\t|\tLaptopUrl = " + laptopDetailUrl);
                try {
                    laptops.add(getLaptopCrawler(laptopDetailUrl, laptopEntity));
                } catch (IOException e) {
                    Logger.getLogger(KLCrawler.class.getName()).log(Level.SEVERE, "Writter not work");
                    e.printStackTrace();
                }
            });
        }
        return laptops;
    }

    private static LaptopEntity getLaptopCrawler(String url, LaptopEntity laptop) throws IOException {
        String document = null;
        ByteArrayOutputStream ps = null;
        try {
            document = XMLHelper.getDocument(url, CrawlerConstant.KIM_LONG_DETAIL_START_SIGNAL, CrawlerConstant.KIM_LONG_DETAIL_TAG, null);
            String[] xsdOrder = new String[]{"model", "cpu", "vga", "ram", "hardDisk", "lcd", "options", "port", "os", "battery", "weight", "color"};
            XPath xPath = XMLHelper.getXPath();

            InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
            String xslPath = CrawlerConstant.KL_XSL_PATH;
            ps = TrAxHelper.transform(is, xslPath);
            Document dom = XMLHelper.parseStringToDOM(ps.toString());
            String exp = "//*[local-name()='laptop']";
            Node root = (Node) xPath.evaluate(exp, dom, XPathConstants.NODE);

            Document newDom = XMLHelper.createNewDOM();
            Element name = XMLHelper.CreateElement(newDom, "name", laptop.getName(), null);
            Element price = XMLHelper.CreateElement(newDom, "price", laptop.getPrice() + "", null);
//        Element laptopElement = XMLHelper.CreateElement(newRoot,"laptop",null,null);
            Element laptopElement = newDom.createElementNS("http://huyng/schema/laptop", "laptop");
            Element id = XMLHelper.CreateElement(newDom, "id", "0", null);
            laptopElement.appendChild(id);
            for (int i = 0; i < xsdOrder.length; i++) {
                Node e = ((DeferredElementNSImpl) root).getElementsByTagName(xsdOrder[i]).item(0);
                String value = null;
                if (e != null)
                    value = ((DeferredElementNSImpl) root).getElementsByTagName(xsdOrder[i]).item(0).getTextContent();
                Element element = XMLHelper.CreateElement(newDom, xsdOrder[i], value, null);
                laptopElement.appendChild(element);
            }
            laptopElement.appendChild(name);
            laptopElement.appendChild(price);
            newDom.appendChild(laptopElement);

            String xmlContent = TrAxHelper.transformDOMtoString(newDom);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(xmlContent.getBytes("UTF-8").length);
            outputStream.write(xmlContent.getBytes("UTF-8"), 0, xmlContent.getBytes("UTF-8").length);


            try {
                XMLHelper.validateXMLSchema(TrAxHelper.getXSDPath(LaptopEntity.class), outputStream);
                laptop = (LaptopEntity) JAXBHelper.unmarshalling(xmlContent.getBytes("UTF-8"), LaptopEntity.class);
            } catch (JAXBException | SAXParseException e) {
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


            return laptop;
        } catch (XPathExpressionException e) {
            synchronized (LOCK) {
                writer.write("********************************ERROR Xpath.evaluate********************************" + "\n");
                writer.write("Exception: " + e.getMessage() + "\n\n");
                writer.flush();
            }
        } catch (ParserConfigurationException | SAXException e) {
            synchronized (LOCK) {
                writer.write("********************************ERROR Create DOM********************************" + "\n");
                writer.write("Exception: " + e.getMessage() + "\n\n");
                writer.flush();
            }
        } catch (TransformerException e) {
            synchronized (LOCK) {
                writer.write("\n");
                writer.write("********************************ERROR when transfer html -> xml via xlts********************************" + "\n");
                writer.write("Exception: " + e.getMessage());
                writer.write("\nProductUrl = " + url + "\n");
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

    //TODO: EDit lai DB bo~ : ,[Webcam],[FingerprintRecognition],[FaceRecognition],[Size]
}
