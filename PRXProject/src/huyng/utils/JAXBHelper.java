package huyng.utils;

import com.sun.codemodel.internal.JCodeModel;
import com.sun.tools.internal.xjc.api.ErrorListener;
import com.sun.tools.internal.xjc.api.S2JJAXBModel;
import com.sun.tools.internal.xjc.api.SchemaCompiler;
import com.sun.tools.internal.xjc.api.XJC;
import huyng.constants.CrawlerConstant;
import huyng.entities.LaptopEntity;
import huyng.entities.ProcessorEntity;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class JAXBHelper {
    public static void generateJaxbPackage(String xsdFilePath) throws JAXBException {
        try {
            String output = "src/";
            xsdFilePath = "src/huyng/jaxb/" +xsdFilePath;
            SchemaCompiler sc = XJC.createSchemaCompiler();
            sc.setErrorListener(new ErrorListener() {
                @Override
                public void error(org.xml.sax.SAXParseException e) {
                    System.out.println(e.getMessage());
                }

                @Override
                public void fatalError(org.xml.sax.SAXParseException e) {
                    System.out.println(e.getMessage());

                }

                @Override
                public void warning(org.xml.sax.SAXParseException e) {
                    System.out.println(e.getMessage());

                }

                @Override
                public void info(org.xml.sax.SAXParseException e) {

                }
            });
            sc.forcePackageName("huyng.jaxb");
            File schema = new File(xsdFilePath);
            InputSource is = new InputSource(schema.toURI().toString());
            sc.parseSchema(is);
            S2JJAXBModel model = sc.bind();
            JCodeModel code = model.generateCode(null, null);
            code.build(new File(output));
            System.out.println("Finish");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object unmarshalling(byte[] array, Class T) throws JAXBException {
        Object object = null;
        Class t = null;
        if (T == LaptopEntity.class) t = LaptopEntity.class;
        if (T == ProcessorEntity.class) t = ProcessorEntity.class;
        JAXBContext jaxbContext = JAXBContext.newInstance(t);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        object = unmarshaller.unmarshal(new ByteArrayInputStream(array));
        return object;
    }

    public static String getXSDPath(Class T){
        String path = null;
        if (T == LaptopEntity.class) path = "/laptop.xsd";
        if (T == ProcessorEntity.class) path = "/processor.xsd";
        if (path == null) return null;
        return CrawlerConstant.XSD_PATH + path;
    }
}
