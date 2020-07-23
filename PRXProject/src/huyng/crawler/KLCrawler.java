package huyng.crawler;

import huyng.constants.CrawlerConstant;
import huyng.constants.PageConstant;
import huyng.daos.BrandDAO;
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

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KLCrawler implements Runnable {
    private final Object LOCK = new Object();
    private List<Thread> THREADS = new ArrayList<>();
    private File file = null;
    private FileWriter writer = null;
    private Hashtable<LaptopEntity, String> cpus = new Hashtable<>();
    private Hashtable<LaptopEntity, String> rams = new Hashtable<>();
    private Hashtable<LaptopEntity, String> lcds = new Hashtable<>();
    public LaptopService laptopService = new LaptopService();
    private static String realPath;
    private static PageConstant pageConstant;

    public KLCrawler(String realPath) {
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
            Logger.getLogger(KLCrawler.class.getName()).log(Level.SEVERE, "Exception e : " + e.getMessage() + "| Line:" + e.getStackTrace()[0].getLineNumber());
        }
    }

    private void processCrawler() throws Exception {
        file = new File(realPath + CrawlerConstant.ERROR_KIMLONG);
        writer = new FileWriter(file);
        pageConstant = JAXBHelper.unmarshaller(realPath + CrawlerConstant.KL_PAGE_CONSTANT_XML, PageConstant.class);

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
                                    if (!laptopService.insertLaptop(l, cpus.get(l), rams.get(l), lcds.get(l), realPath)) {
                                        writer.write("********************************LAPTOP EXISTED********************************\n");
                                        writer.write(l.toString() + "\n\n\n");
                                        writer.flush();
                                    }
                                }
                            } catch (Exception e) {
                                try {
                                    synchronized (LOCK) {
                                        writer.write("********************************ERROR ON INSERT LAPTOP********************************\n");
                                        writer.write("Message : " + e.getMessage() + "\n");
                                        writer.write(l.toString() + "\n\n\n");
                                        writer.flush();
                                    }
                                } catch (IOException ioException) {
                                    Logger.getLogger(KLCrawler.class.getName()).log(Level.SEVERE, "IOException e : " + ioException.getMessage() + "| Line:" + ioException.getStackTrace()[0].getLineNumber());
                                }
                            }
                        });
                    } catch (Exception e) {
                        Logger.getLogger(KLCrawler.class.getName()).log(Level.SEVERE, "Exception e : " + e.getMessage() + "| Line:" + e.getStackTrace()[0].getLineNumber());
                    }
                    System.out.println("KimLongCrawler SUSSCESS Brand :" + finalBrand.getName());
                }
            };
            t.start();
            THREADS.add(t);
        });
    }

    private Hashtable<BrandEntity, String> getBrandCrawler() throws Exception {
        Hashtable<BrandEntity, String> brands = new Hashtable<>();
        String document = XMLHelper.getDocument2(pageConstant.getDomainUrl(), pageConstant.getBrandStartSignal(), pageConstant.getBrandTagName(), pageConstant.getBrandIgnoreText().getIgnoreText());
        Document dom = XMLHelper.parseStringToDOM(document);
        String exp_li = pageConstant.getBrandXPath().getContainer();
        String exp_link = pageConstant.getBrandXPath().getLink();
        String exp_name = pageConstant.getBrandXPath().getName();
        XPath xPath = XMLHelper.getXPath();
        NodeList liNodes = (NodeList) xPath.evaluate(exp_li, dom, XPathConstants.NODESET);
        for (int i = 0; i < liNodes.getLength(); i++) {
            Node aNode = liNodes.item(i);
            if (aNode != null) {
                String link = (String) xPath.evaluate(exp_link, aNode, XPathConstants.STRING);
                String name = (String) xPath.evaluate(exp_name, aNode, XPathConstants.STRING);
                if (!name.contains("PC - LCD") && !name.contains("Phụ Kiện") && !name.contains("Laptop Cũ"))
                    brands.put(new BrandEntity(name), link);
            }
        }
        return brands;
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
                    Logger.getLogger(KLCrawler.class.getName()).log(Level.SEVERE, "IOException e : " + e.getMessage() + "| Line:" + e.getStackTrace()[0].getLineNumber());
                }
            });
        }
        return laptops;
    }

    private int getPageSize(String url) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        String document = XMLHelper.getDocument2(url, pageConstant.getPageSizeStartSignal(), pageConstant.getPageSizeTagName(), pageConstant.getPageSizeIgnoreText().getIgnoreText());
        Document dom = XMLHelper.parseStringToDOM(document);
        String exp = pageConstant.getPageSizeXPath().getAllATagWithoutNext();
        XPath xPath = XMLHelper.getXPath();
        NodeList aTag = (NodeList) xPath.evaluate(exp, dom, XPathConstants.NODESET);
        String link = null;
        for (int i = 0; i < aTag.getLength(); i++) {
            Element a = (Element) aTag.item(i);
            if (a.hasAttribute("href")) {
                link = a.getAttribute("href");
            }
        }
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
        return 1;
    }

    private Hashtable<LaptopEntity, String> eachPageCrawler(String url) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        Hashtable<LaptopEntity, String> result = new Hashtable<>();
//        String document = XMLHelper.getDocument(url, CrawlerConstant.KIM_LONG_EACH_PAGE_START_SIGNAL, CrawlerConstant.KIM_LONG_EACH_PAGE_TAG, new String[]{CrawlerConstant.DUPLICATE_SRC_KL});
        String document = XMLHelper.getDocument2(url, pageConstant.getEachPageStartSignal(), pageConstant.getEachPageTagName(), pageConstant.getEachPageIgnoreText().getIgnoreText());
        Document dom = XMLHelper.parseStringToDOM(document);
        String exp_a = pageConstant.getEachPageXPath().getContainer();
        String exp_link = pageConstant.getEachPageXPath().getLink();
        String exp_name = pageConstant.getEachPageXPath().getName();
        String exp_price = pageConstant.getEachPageXPath().getPrice();
        String exp_img = pageConstant.getEachPageXPath().getImage();
        XPath xPath = XMLHelper.getXPath();
        NodeList aNodes = (NodeList) xPath.evaluate(exp_a, dom, XPathConstants.NODESET);
        for (int i = 0; i < aNodes.getLength(); i++) {
            Node aNode = aNodes.item(i);
            if (aNode != null) {
                String link = (String) xPath.evaluate(exp_link, aNode, XPathConstants.STRING);
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


    private LaptopEntity getLaptopCrawler(String url, LaptopEntity laptop) throws IOException {
        String document = null;
        ByteArrayOutputStream ps = null;
        try {
            document = XMLHelper.getDocument2(url, pageConstant.getDetailPageStartSignal(), pageConstant.getDetailPageTagName(), pageConstant.getDetailPageIgnoreText().getIgnoreText());
            InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
            String xslPath = realPath + CrawlerConstant.KL_XSL_PATH;

            Hashtable<String, String> params = new Hashtable<>();
            params.put("name", laptop.getName());
            params.put("price", laptop.getPrice() + "");
            params.put("image", laptop.getImage());
            ps = TrAxHelper.transform(is, xslPath, params);
            try {
                XMLHelper.validateXMLSchema(realPath + JAXBHelper.getXSDPath(LaptopEntity.class), ps);
                String removeNSPs = ps.toString().replaceAll("http://huyng/schema/laptop", "");
                ps = StringHelper.getByteArrayFromString(removeNSPs);
                laptop = (LaptopEntity) JAXBHelper.unmarshalling(ps.toByteArray(), LaptopEntity.class);
                cpus.put(laptop, StringHelper.getCPUFromXMLString(ps.toString()));
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
            } catch (IndexOutOfBoundsException e) {
                writer.write("********************************Missing LCD|Ram|Monitor********************************" + "\n");
                writer.write(e.getMessage());
                writer.write("ProductUrl = " + url + "\n");
                writer.write("Before TrAx.tranform\n");
                writer.write(document + "\n");
                writer.write("After TrAx.tranform" + "\n");
                writer.write(ps.toString() + "\n");
                writer.write("\n\n");
                writer.flush();
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
