package huyng.crawler;

import com.sun.org.apache.xerces.internal.dom.DeferredElementNSImpl;
import huyng.constants.CrawlerConstant;
import huyng.daos.BrandDAO;
import huyng.daos.LaptopDAO;
import huyng.entities.BrandEntity;
import huyng.entities.LaptopEntity;
import huyng.services.LaptopService;
import huyng.utils.JAXBHelper;
import huyng.utils.StringHelper;
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

//TODO: Recode KL
//TODO: print out all cpu and formated for 2 page
public class KLCrawler implements Runnable {
    private final Object LOCK = new Object();
    private List<Thread> THREADS = new ArrayList<>();
    private File file = null;
    private FileWriter writer = null;
    private Hashtable<LaptopEntity,String> cpus = new Hashtable<>();
    private Hashtable<LaptopEntity,String> rams = new Hashtable<>();
    private Hashtable<LaptopEntity,String> lcds = new Hashtable<>();
    public LaptopService laptopService = new LaptopService();

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

//    public static void main(String[] args) throws IOException, XMLStreamException, XPathExpressionException, SAXException, ParserConfigurationException {
//        KLCrawler crawler = new KLCrawler();
//        crawler.processCrawler();
//
////        crawler.getPageSize("https://kimlongcenter.com/danh-muc/laptop-dell-449.html");
////        crawler.getPageUrl("https://kimlongcenter.com/danh-muc/laptop-dell-449.html");
////        crawler.eachPageCrawler("https://kimlongcenter.com/danh-muc/laptop-asus-452/trang/1");
////        crawler.getLaptopCrawler("https://kimlongcenter.com/san-pham/asus-rog-strix-g-g531g-val319t-i7-9750h-3902.html", new LaptopEntity("NguyenGiaHuy", 123612874));
//    }

    private void processCrawler() throws IOException, XMLStreamException {
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
            }
            BrandEntity finalBrand = brand;
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        List<LaptopEntity> productOfBrands = getLaptopByBrandUrl(url);

                        //Inseart productOfBrand
                        productOfBrands.forEach(l -> {
                            l.setBrand(finalBrand);
                            try {
                                synchronized (LOCK) {
                                    laptopService.insertLaptop(l, cpus.get(l),rams.get(l),lcds.get(l));
                                }
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
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("KimLongCrawler SUSSCESS Brand :" +finalBrand.getName());
                }
            };
            t.start();
            THREADS.add(t);
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
        String exp_img = "div[@class='product-img row']/img/@data-src";
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
                String image = (String) xPath.evaluate(exp_img, aNode, XPathConstants.STRING);
                priceString = priceString.replaceAll("[.]", "")
                        .substring(0, priceString.indexOf(' ') - 2);
                int price = Integer.parseInt(priceString);
                LaptopEntity laptopEntity = new LaptopEntity(name, price, image);
                result.put(laptopEntity, link);
            }
        }
        return result;
    }

    private List<LaptopEntity> getLaptopByBrandUrl(String url) throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        List<LaptopEntity> laptops = new ArrayList<>();
        int pagesize = getPageSize(url);
        for (int i = 0; i < pagesize; i++) {
            String pageUrl = url.substring(0, url.indexOf(".html")) + "/trang/" + (i + 1);
            Hashtable<LaptopEntity, String> listLaptopLink = eachPageCrawler(pageUrl);
            listLaptopLink.forEach((laptopEntity, laptopDetailUrl) -> {
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

    private LaptopEntity getLaptopCrawler(String url, LaptopEntity laptop) throws IOException {
        String document = null;
        ByteArrayOutputStream ps = null;
        try {
            document = XMLHelper.getDocument(url, CrawlerConstant.KIM_LONG_DETAIL_START_SIGNAL, CrawlerConstant.KIM_LONG_DETAIL_TAG, new String[]{"™","®"});
            InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
            String xslPath = CrawlerConstant.KL_XSL_PATH;

            Hashtable<String,String> params = new Hashtable<>();
            params.put("name",laptop.getName());
            params.put("price",laptop.getPrice()+"");
            params.put("image",laptop.getImage());
            ps = TrAxHelper.transform(is, xslPath, params);

            try {
                XMLHelper.validateXMLSchema(JAXBHelper.getXSDPath(LaptopEntity.class), ps);
                String removeNSPs = ps.toString().replaceAll("http://huyng/schema/laptop","");
                ps = StringHelper.getByteArrayFromString(removeNSPs);
                laptop = (LaptopEntity) JAXBHelper.unmarshalling(ps.toByteArray(), LaptopEntity.class);
                cpus.put(laptop,StringHelper.getCPUFromXMLString(ps.toString()));
                rams.put(laptop, StringHelper.getRamFromXMLString(ps.toString()));
                lcds.put(laptop, StringHelper.getLcdFromXMLString(ps.toString()));
            } catch (JAXBException | SAXException e) {
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
}
