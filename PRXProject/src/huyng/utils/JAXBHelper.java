package huyng.utils;

import huyng.constants.CrawlerConstant;
import huyng.constants.PageConstant;
import huyng.crawler.PACrawler;
import huyng.entities.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static PageConstant unmarshaller(String XMLFilePath,Class tClass){
        try{
            if(tClass == PageConstant.class){
                JAXBContext context = JAXBContext.newInstance(PageConstant.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                File f = new File(XMLFilePath);
                PageConstant item = (PageConstant) unmarshaller.unmarshal(f);
                return item;
            }else{
                System.out.println("Not support");
            }
        } catch (JAXBException e) {
            Logger.getLogger(JAXBHelper.class.getName()).log(Level.SEVERE, "JAXBException e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
        }
        return null;
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
            Logger.getLogger(JAXBHelper.class.getName()).log(Level.SEVERE, "JAXBException e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
        }
    }
}
