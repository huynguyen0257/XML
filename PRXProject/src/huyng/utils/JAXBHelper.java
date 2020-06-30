package huyng.utils;

import com.sun.codemodel.internal.JCodeModel;
import com.sun.tools.internal.xjc.api.ErrorListener;
import com.sun.tools.internal.xjc.api.S2JJAXBModel;
import com.sun.tools.internal.xjc.api.SchemaCompiler;
import com.sun.tools.internal.xjc.api.XJC;
import huyng.entities.LaptopEntity;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class JAXBHelper {
    public static void generateJaxbPackage() throws JAXBException {
        try {
            String output = "src/";
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
            File schema = new File("src/huyng/jaxb/laptop.xsd");
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
        Object laptop = null;
        Class t = null;
        if (T == LaptopEntity.class) t = LaptopEntity.class;
        JAXBContext jaxbContext = JAXBContext.newInstance(t);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        laptop = unmarshaller.unmarshal(new ByteArrayInputStream(array));
        return laptop;
    }
}
