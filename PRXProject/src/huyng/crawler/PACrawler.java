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
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PACrawler implements Runnable {
    private static File file = null;
    private static FileWriter writer = null;
    private static final Object LOCK = new Object();
    private static List<Thread> threads = new ArrayList<>();
    private static Hashtable<LaptopEntity, String> cpus = new Hashtable<>();
    private static Hashtable<LaptopEntity, String> rams = new Hashtable<>();
    private static Hashtable<LaptopEntity, String> lcds = new Hashtable<>();
    private static LaptopService laptopService = new LaptopService();
    private static String realPath;
    private static PageConstant pageConstant;

    public PACrawler(String realPath) {
        this.realPath = realPath;
    }

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
//                    closeStream();
                    break;
                }
                count = 0;
                Thread.currentThread().sleep(CrawlerConstant.THREAD_WAIT_CHECK);

            }
        } catch (Exception e) {
            Logger.getLogger(PACrawler.class.getName()).log(Level.SEVERE, "Exception e : " + e.getMessage() + "| Line:" + e.getStackTrace()[0].getLineNumber());
        }
    }

    public static void closeStream() throws IOException {
        writer.close();
    }

    /***
     * main Crawler PHUC_ANH
     * @throws IOException
     * @throws XMLStreamException
     */
    public static void crawlerProcess() throws Exception {
        file = new File(realPath + CrawlerConstant.ERROR_PHUCANH);
        writer = new FileWriter(file);
        pageConstant = JAXBHelper.unmarshaller(realPath + CrawlerConstant.PA_PAGE_CONSTANT_XML, PageConstant.class);

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
                }

                //Crawl Laptop
                int pageSize = getPageSize(brandUrl);

                for (int i = 0; i < pageSize; i++) {
                    List<LaptopEntity> laptopOfBrand = new ArrayList<>();
                    String pageUrl = brandUrl + "?page=" + (i + 1);
                    BrandEntity finalBrand = brand;
                    int finalI = i;
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            try {
                                laptopOfBrand.addAll(eachPageCrawler(pageUrl));
                            } catch (Exception e) {
//                                e.printStackTrace();
                                Logger.getLogger(PACrawler.class.getName()).log(Level.SEVERE, "Exception e : " + e.getMessage() + "| Line:" + e.getStackTrace()[0].getLineNumber());
                            }

                            //Save ListLaptop to DB
                            laptopOfBrand.forEach((l) -> {
                                try {
                                    synchronized (LOCK) {
                                        l.setBrand(finalBrand);
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
//                                        e.printStackTrace();
                                        Logger.getLogger(PACrawler.class.getName()).log(Level.SEVERE, "IOException e : " + ioException.getMessage() + "| Line:" + ioException.getStackTrace()[0].getLineNumber());
                                    }
                                }
                            });//End save list laptop
                            System.out.println("PhuCanhCrawler SUSSCESS each page of brand " + finalBrand.getName() + " \t|Page : " + finalI);
                        }
                    };
                    threads.add(t);
                    t.start();
                    t.sleep(CrawlerConstant.THREAD_WAIT_CALL);
                }
            } catch (Exception e) {
//                e.printStackTrace();
                Logger.getLogger(PACrawler.class.getName()).log(Level.SEVERE, "Exception e : " + e.getMessage() + "| Line:" + e.getStackTrace()[0].getLineNumber());
            }
        });
    }

    /***
     * Get brand Name and URL
     * @return
     * @throws IOException
     * @throws XMLStreamException
     */
    public static Hashtable<String, String> getBrandCrawler() throws Exception {
        Hashtable<String, String> categories = new Hashtable<>();
        String document = XMLHelper.getDocument2(pageConstant.getDomainUrl(), pageConstant.getBrandStartSignal(), pageConstant.getBrandTagName(), pageConstant.getEachPageIgnoreText().getIgnoreText());
        Document dom = XMLHelper.parseStringToDOM(document);
        String exp = pageConstant.getBrandXPath().getContainer();
        String exp_link = pageConstant.getBrandXPath().getLink();
        String exp_name = pageConstant.getBrandXPath().getName();
        XPath xPath = XMLHelper.getXPath();
        NodeList aNodes = (NodeList) xPath.evaluate(exp, dom, XPathConstants.NODESET);
        for (int i = 0; i < aNodes.getLength(); i++) {
            Node aNode = aNodes.item(i);
            if (aNode != null) {
                String link = pageConstant.getDomainUrl() + xPath.evaluate(exp_link, aNode, XPathConstants.STRING);
                String name = (String) xPath.evaluate(exp_name, aNode, XPathConstants.STRING);
                categories.put(name, link);
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
    public static List<LaptopEntity> eachPageCrawler(String url) throws Exception {
        List<LaptopEntity> result = new ArrayList<>();
        Hashtable<LaptopEntity, String> laptops = new Hashtable<>();
        String document = XMLHelper.getDocument2(url, pageConstant.getEachPageStartSignal(), pageConstant.getEachPageTagName(), pageConstant.getEachPageIgnoreText().getIgnoreText());
        Document dom = XMLHelper.parseStringToDOM(document);
        XPath xPath = XMLHelper.getXPath();

        String exp_div = pageConstant.getEachPageXPath().getContainer();
        String exp_link = pageConstant.getEachPageXPath().getLink();
        String exp_img = pageConstant.getEachPageXPath().getImage();
        String exp_name = pageConstant.getEachPageXPath().getName();
        String exp_price = pageConstant.getEachPageXPath().getPrice();
        NodeList div = (NodeList) xPath.evaluate(exp_div, dom, XPathConstants.NODESET);
        for (int i = 0; i < div.getLength(); i++) {
            Node divNode = div.item(i);
            if (divNode != null) {
                String link = pageConstant.getDomainUrl() + xPath.evaluate(exp_link, divNode, XPathConstants.STRING);
                String img = (String) xPath.evaluate(exp_img, divNode, XPathConstants.STRING);
                String name = (String) xPath.evaluate(exp_name, divNode, XPathConstants.STRING);
                String priceString = (String) xPath.evaluate(exp_price, divNode, XPathConstants.STRING);
                priceString = priceString.replaceAll("[.]", "").replaceAll(" ₫", "").trim();
                int price = 0;
                try {
                    price = Integer.parseInt(priceString);
                } catch (NumberFormatException e) {
                    System.out.println("continue in Laptop : " + name);
                    continue;
                }
                LaptopEntity laptop = new LaptopEntity(name, price, img);
                laptops.put(laptop, link);
            }
        }

        //get each product of Brand
        laptops.forEach((laptopEntity, laptopUrl) -> {
            //get detail of product from url
            try {
                laptopEntity = getLaptopCrawler(laptopUrl, laptopEntity);
                if (laptopEntity != null) result.add(laptopEntity);
            } catch (Exception e) {
//                e.printStackTrace();
                Logger.getLogger(PACrawler.class.getName()).log(Level.SEVERE, "IOException e : " + e.getMessage() + "| Line:" + e.getStackTrace()[0].getLineNumber());
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
    public static LaptopEntity getLaptopCrawler(String url, LaptopEntity laptop) throws IOException, TransformerException, JAXBException, InterruptedException {
        Thread.currentThread().sleep(CrawlerConstant.THREAD_WAIT_CALL);
        String document = XMLHelper.getDocument2(url, pageConstant.getDetailPageStartSignal(), pageConstant.getDetailPageTagName(), pageConstant.getDetailPageIgnoreText().getIgnoreText());

        InputStream is = new ByteArrayInputStream(document.getBytes("UTF-8"));
        String xslPath = realPath + CrawlerConstant.PA_XSL_PATH;
        Hashtable<String, String> params = new Hashtable<>();
        params.put("name", laptop.getName());
        params.put("price", laptop.getPrice() + "");
        params.put("image", laptop.getImage());
        ByteArrayOutputStream ps = TrAxHelper.transform(is, xslPath, params);
        try {
            XMLHelper.validateXMLSchema(realPath + JAXBHelper.getXSDPath(LaptopEntity.class), ps);
            String removeNSPs = ps.toString().replaceAll("http://huyng/schema/laptop", "");

            ByteArrayOutputStream formatXML = StringHelper.getByteArrayFromString(removeNSPs);
            laptop = (LaptopEntity) JAXBHelper.unmarshalling(formatXML.toByteArray(), LaptopEntity.class);
            cpus.put(laptop, StringHelper.getCPUFromXMLString(ps.toString()));
            rams.put(laptop, StringHelper.getRamFromXMLString(ps.toString()));
            lcds.put(laptop, StringHelper.getLcdFromXMLString(ps.toString()));
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
        return null;
    }

    /***
     * Get page size of Phu Canh
     * @param brandUrl
     * @return
     * @throws IOException
     * @throws XMLStreamException
     */
    private static int getPageSize(String brandUrl) throws Exception {
        String document = XMLHelper.getDocument2(brandUrl, pageConstant.getPageSizeStartSignal(), pageConstant.getPageSizeTagName(), pageConstant.getPageSizeIgnoreText().getIgnoreText());
        document = document.trim();
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


}
