package huyng.jaxb;

import com.sun.codemodel.internal.JCodeModel;
import com.sun.tools.internal.xjc.api.ErrorListener;
import com.sun.tools.internal.xjc.api.S2JJAXBModel;
import com.sun.tools.internal.xjc.api.SchemaCompiler;
import com.sun.tools.internal.xjc.api.XJC;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;

public class Generate {
    public static void main(String[] args) throws JAXBException {
        try {
            String output = "src/";
            String xsdFilePath = "src/huyng/jaxb/processor.xsd";
            SchemaCompiler sc = XJC.createSchemaCompiler();
            sc.setErrorListener(new ErrorListener() {
                @Override
                public void error(org.xml.sax.SAXParseException e) {
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
}
