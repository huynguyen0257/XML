package huyng.utils;

import org.w3c.dom.Node;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.Hashtable;

public class TrAxHelper {


    public static ByteArrayOutputStream transform(InputStream xmlIs, String xslPath, Hashtable<String,String> params)
            throws FileNotFoundException, TransformerConfigurationException, TransformerException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        TransformerFactory factory = TransformerFactory.newInstance();

        StreamSource source = new StreamSource(xmlIs);
        StreamSource xslSource = new StreamSource(new FileInputStream(xslPath));
        StreamResult result = new StreamResult(outputStream);
        Transformer trans = factory.newTransformer(xslSource);
        if (params != null){
            params.forEach((name,value)->{
                trans.setParameter(name,value);
            });
        }
        trans.transform(source, result);

        return outputStream;
    }
}
