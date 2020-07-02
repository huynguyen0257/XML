package huyng.utils;

import huyng.constants.CrawlerConstant;
import huyng.entities.LaptopEntity;
import org.w3c.dom.Node;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class TrAxHelper {


    public static ByteArrayOutputStream transform(InputStream xmlIs, String xslPath)
            throws FileNotFoundException, TransformerConfigurationException, TransformerException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        TransformerFactory factory = TransformerFactory.newInstance();

        StreamSource source = new StreamSource(xmlIs);
        StreamSource xslSource = new StreamSource(new FileInputStream(xslPath));
        StreamResult result = new StreamResult(outputStream);
        Transformer trans = factory.newTransformer(xslSource);
        trans.transform(source, result);

        return outputStream;
    }

    public static String transformDOMtoString(Node node) throws TransformerException {
        StringWriter sw = new StringWriter();
        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(node), new StreamResult(sw));
        return sw.toString();
    }

    public static String getXSLPath(Class T) {
        String path = null;
        if (T == LaptopEntity.class) path = "/laptop.xsl";
        if (path == null) return null;
        return CrawlerConstant.XSL_PATH + path;
    }

    public static String getXSDPath(Class T){
        String path = null;
        if (T == LaptopEntity.class) path = "/laptop.xsd";
        if (path == null) return null;
        return CrawlerConstant.XSD_PATH + path;
    }
}
