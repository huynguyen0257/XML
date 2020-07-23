package huyng.utils;

import com.sun.xml.internal.stream.events.EndElementEvent;
import huyng.constants.CrawlerConstant;
import huyng.entities.*;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.print.Doc;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLHelper {

    public static String getDocument2(String url, String beginSignal, String tag, List<String> IGNORE_TEXTS) throws IOException {
        BufferedReader reader = null;
        try{
            reader = getBufferedReaderForURL(url);
        }catch (FileNotFoundException e){
            System.out.println("Link Not Existed : " +url);
        }
        String devide = " ";
//            devide = "\n";
        String line = "";
        String document = "";
        int tagCount = 0;
        boolean isFound = false;
        while ((line = reader.readLine()) != null) {
            //check the begin tag
            if (!isFound && document.length() == 0 && line.contains(beginSignal)) {
                isFound = true;
            }
            if (isFound && line.contains("<" + tag)) {
                tagCount = tagCount + getAllMatches(line, "<" + tag);
            }
            if (tagCount > 0 && line.contains("</" + tag + ">")) {
                tagCount = tagCount - getAllMatches(line, "</" + tag + ">");
                if (tagCount == 0) {
                    line = formatHTMLLine2(line,IGNORE_TEXTS);

                    document += line.trim() + devide;
                    isFound = false;
                }
            }
            if (isFound) {
                line = formatHTMLLine2(line,IGNORE_TEXTS);

                document += line.trim() + devide;
            }
        }
        document = document.replaceAll("&", "và");
        document = document.replaceAll("<input.*?>", "");
        document = document.replaceAll("<br.*?>", "");
        document = document.replaceAll("<!--.*?-->", "");
        document = document.replaceAll("<style.*?</style>", "");
        document = document.replaceAll("<script.*?</script>", "");

        return document;
    }

    public static String getDocumentWithStartEnd(String url, String beginSignal, String endSignal,List<String> IGNORE_TEXTS) throws IOException {
        BufferedReader reader = getBufferedReaderForURL(url);
        String line = "";
        String document = "";
        int tagCount = 0;
        boolean isFound = false;
        while ((line = reader.readLine()) != null){
            if (!isFound && document.length() == 0 && line.contains(beginSignal)) {
                isFound = true;
            }
            if (isFound){
                if (!line.contains(endSignal)){
                    line = formatHTMLLine2(line,IGNORE_TEXTS);
                    document += line.trim();
                }else break;
            }
        }
        return document;
    }

    private static String formatHTMLLine2(String line,List<String> IGNORE_TEXTS){
        if (IGNORE_TEXTS != null) {
            for (String ignore_text : IGNORE_TEXTS) {
                line = line.replaceAll(ignore_text, "");
            }
        }
        line = line.replaceAll("&ocirc;", "ô")
                .replaceAll("ó", "ó")
                .replaceAll("cứng", "cung")
                .replaceAll("&oacute;", "ó")
                .replaceAll("&igrave;", "ì")
                .replaceAll("&ecirc;", "ê")
                .replaceAll("&egrave;", "è")
                .replaceAll("&iacute;", "í")
                .replaceAll("&ograve;", "ò")
                .replaceAll("&nbsp;", "")
                .replaceAll("&agrave;", "à")
                .replaceAll("&aacute;", "á")
                .replaceAll("&reg;", "")
                .replaceAll("&trade;", "")
                .replaceAll("&acirc;", "â");
        return line;
    }

    private static int getAllMatches(String text, String regex) {
        Matcher m = Pattern.compile("(?=(" + regex + "))").matcher(text);
        return m.groupCount();
    }

    public static XMLEventReader parseStringToXMLEventReader(String xmlSection) throws UnsupportedEncodingException, XMLStreamException {
        byte[] buteArray = xmlSection.getBytes("UTF-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(buteArray);
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = inputFactory.createXMLEventReader(inputStream);
        return reader;
    }

    public static BufferedReader getBufferedReaderForURL(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        return reader;
    }

    public static Document parseStringToDOM(String doc) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document domRs = builder.parse(new InputSource(new StringReader(doc)));
        return domRs;
    }

    public static XPath getXPath(){
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        return xPath;
    }

    public static void validateXMLSchema(String xsdPath, ByteArrayOutputStream xmlPath) throws SAXException, IOException {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(xsdPath));
            Validator validator = schema.newValidator();

            validator.validate(new StreamSource(new ByteArrayInputStream(xmlPath.toByteArray())));
    }

    public static String marshallToXmlString(Object objects, Class T) throws JAXBException {
        JAXBContext jaxbContext = null;
        if (T.getName().equals(RamEntityList.class.getName()))
            jaxbContext = JAXBContext.newInstance(RamEntityList.class, RamEntity.class);
        if (T.getName().equals(LaptopEntityList.class.getName()))
            jaxbContext = JAXBContext.newInstance(LaptopEntityList.class, LaptopEntity.class);
        if (T.getName().equals(MonitorEntityList.class.getName()))
            jaxbContext = JAXBContext.newInstance(MonitorEntityList.class, MonitorEntity.class);
        if (T.getName().equals(BrandEntityList.class.getName()))
            jaxbContext = JAXBContext.newInstance(BrandEntityList.class, BrandEntity.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        StringWriter writer = new StringWriter();
        marshaller.marshal(objects, writer);
        return writer.toString();
    }
}
