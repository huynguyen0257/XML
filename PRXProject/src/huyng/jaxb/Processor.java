
package huyng.jaxb;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Processor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Processor">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="brand" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="model" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="core" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="thread" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="baseClock" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="boostClock" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="cache" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="mark" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Processor", propOrder = {
    "id",
    "brand",
    "name",
    "model",
    "core",
    "thread",
    "baseClock",
    "boostClock",
    "cache",
    "mark"
})
public class Processor {

    protected Integer id;
    @XmlElement(required = true)
    protected String brand;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String model;
    @XmlElement(required = true)
    protected BigInteger core;
    @XmlElement(required = true)
    protected BigInteger thread;
    protected double baseClock;
    protected double boostClock;
    protected double cache;
    protected String mark;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the brand property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the value of the brand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrand(String value) {
        this.brand = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the core property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCore() {
        return core;
    }

    /**
     * Sets the value of the core property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCore(BigInteger value) {
        this.core = value;
    }

    /**
     * Gets the value of the thread property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getThread() {
        return thread;
    }

    /**
     * Sets the value of the thread property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setThread(BigInteger value) {
        this.thread = value;
    }

    /**
     * Gets the value of the baseClock property.
     * 
     */
    public double getBaseClock() {
        return baseClock;
    }

    /**
     * Sets the value of the baseClock property.
     * 
     */
    public void setBaseClock(double value) {
        this.baseClock = value;
    }

    /**
     * Gets the value of the boostClock property.
     * 
     */
    public double getBoostClock() {
        return boostClock;
    }

    /**
     * Sets the value of the boostClock property.
     * 
     */
    public void setBoostClock(double value) {
        this.boostClock = value;
    }

    /**
     * Gets the value of the cache property.
     * 
     */
    public double getCache() {
        return cache;
    }

    /**
     * Sets the value of the cache property.
     * 
     */
    public void setCache(double value) {
        this.cache = value;
    }

    /**
     * Gets the value of the mark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMark() {
        return mark;
    }

    /**
     * Sets the value of the mark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMark(String value) {
        this.mark = value;
    }

}
