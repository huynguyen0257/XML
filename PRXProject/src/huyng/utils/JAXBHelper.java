package huyng.utils;

import com.sun.codemodel.internal.JCodeModel;
import com.sun.tools.internal.xjc.api.ErrorListener;
import com.sun.tools.internal.xjc.api.S2JJAXBModel;
import com.sun.tools.internal.xjc.api.SchemaCompiler;
import com.sun.tools.internal.xjc.api.XJC;
import huyng.constants.CrawlerConstant;
import huyng.entities.*;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class JAXBHelper {
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

    public static void marshallerToTransfer(LaptopEntityList laptops, OutputStream os){
        try{
            JAXBContext jaxbContext = JAXBContext.newInstance(LaptopEntityList.class);
            Marshaller marshal = jaxbContext.createMarshaller();
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshal.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");
            marshal.marshal(laptops,os);
//            StringWriter writer = new StringWriter();
//            marshal.marshal(laptops,writer);
//            System.out.println(writer.toString());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
