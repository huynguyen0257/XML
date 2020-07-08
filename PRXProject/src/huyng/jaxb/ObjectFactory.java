
package huyng.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the huyng.jaxb package. 
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

    private final static QName _Processor_QNAME = new QName("http://huyng/schema/processor", "processor");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: huyng.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Processor }
     * 
     */
    public Processor createProcessor() {
        return new Processor();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Processor }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://huyng/schema/processor", name = "processor")
    public JAXBElement<Processor> createProcessor(Processor value) {
        return new JAXBElement<Processor>(_Processor_QNAME, Processor.class, null, value);
    }

}
