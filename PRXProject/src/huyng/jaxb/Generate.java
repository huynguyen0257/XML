package huyng.jaxb;

import huyng.utils.JAXBHelper;

import javax.xml.bind.JAXBException;

public class Generate {
    public static void main(String[] args) throws JAXBException {
        JAXBHelper.generateJaxbPackage("processor.xsd");
    }
}
