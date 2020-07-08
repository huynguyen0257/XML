
package huyng.entities;

import huyng.jaxb.Processor;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the huyng.schema.laptop package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Laptop_QNAME = new QName("http://huyng/schema/laptop", "laptop");
    private final static QName _Processor_QNAME = new QName("http://huyng/schema/processor", "processor");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: huyng.schema.laptop
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LaptopEntity }
     * 
     */
    public LaptopEntity createLaptop() {
        return new LaptopEntity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LaptopEntity }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://huyng/schema/laptop", name = "laptop")
    public JAXBElement<LaptopEntity> createLaptop(LaptopEntity value) {
        return new JAXBElement<LaptopEntity>(_Laptop_QNAME, LaptopEntity.class, null, value);
    }


    /**
     * Create an instance of {@link Processor }
     *
     */
    public ProcessorEntity createProcessor() {
        return new ProcessorEntity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Processor }{@code >}}
     *
     */
    @XmlElementDecl(namespace = "http://huyng/schema/processor", name = "processor")
    public JAXBElement<ProcessorEntity> createProcessor(ProcessorEntity value) {
        return new JAXBElement<ProcessorEntity>(_Processor_QNAME, ProcessorEntity.class, null, value);
    }

}
