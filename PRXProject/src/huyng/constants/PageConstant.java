
package huyng.constants;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="domainUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="brandStartSignal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="brandTagName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="brandIgnoreText" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ignoreText" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="brandXPath">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="container" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pageSizeStartSignal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageSizeTagName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pageSizeIgnoreText" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ignoreText" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="pageSizeXPath">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="allATagWithoutNext" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="eachPageStartSignal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="eachPageTagName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="eachPageIgnoreText" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ignoreText" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="eachPageXPath">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="container" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/all>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="detailPageStartSignal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="detailPageEndSignal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="detailPageTagName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="detailPageIgnoreText" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ignoreText" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="data" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="entity" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "pageConstant", namespace = "http://huyng/schema/laptop")
public class PageConstant {

    @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
    protected String domainUrl;
    @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
    protected String brandStartSignal;
    @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
    protected String brandTagName;
    @XmlElement(namespace = "http://huyng/schema/laptop")
    protected PageConstant.BrandIgnoreText brandIgnoreText;
    @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
    protected PageConstant.BrandXPath brandXPath;
    @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
    protected String pageSizeStartSignal;
    @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
    protected String pageSizeTagName;
    @XmlElement(namespace = "http://huyng/schema/laptop")
    protected PageConstant.PageSizeIgnoreText pageSizeIgnoreText;
    @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
    protected PageConstant.PageSizeXPath pageSizeXPath;
    @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
    protected String eachPageStartSignal;
    @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
    protected String eachPageTagName;
    @XmlElement(namespace = "http://huyng/schema/laptop")
    protected PageConstant.EachPageIgnoreText eachPageIgnoreText;
    @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
    protected PageConstant.EachPageXPath eachPageXPath;
    @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
    protected String detailPageStartSignal;
    @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
    protected String detailPageEndSignal;
    @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
    protected String detailPageTagName;
    @XmlElement(namespace = "http://huyng/schema/laptop")
    protected PageConstant.DetailPageIgnoreText detailPageIgnoreText;
    @XmlElement(namespace = "http://huyng/schema/laptop")
    protected PageConstant.Data data;

    public String getDataValue(String key){
        for (int i = 0; i < data.entity.size(); i++) {
            if (data.entity.get(i).key.contains(key)) return data.entity.get(i).value;
        }
        return null;
    }

    /**
     * Gets the value of the domainUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomainUrl() {
        return domainUrl;
    }

    /**
     * Sets the value of the domainUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomainUrl(String value) {
        this.domainUrl = value;
    }

    /**
     * Gets the value of the brandStartSignal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrandStartSignal() {
        return brandStartSignal;
    }

    /**
     * Sets the value of the brandStartSignal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrandStartSignal(String value) {
        this.brandStartSignal = value;
    }

    /**
     * Gets the value of the brandTagName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrandTagName() {
        return brandTagName;
    }

    /**
     * Sets the value of the brandTagName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrandTagName(String value) {
        this.brandTagName = value;
    }

    /**
     * Gets the value of the brandIgnoreText property.
     * 
     * @return
     *     possible object is
     *     {@link PageConstant.BrandIgnoreText }
     *     
     */
    public PageConstant.BrandIgnoreText getBrandIgnoreText() {
        return brandIgnoreText;
    }

    /**
     * Sets the value of the brandIgnoreText property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageConstant.BrandIgnoreText }
     *     
     */
    public void setBrandIgnoreText(PageConstant.BrandIgnoreText value) {
        this.brandIgnoreText = value;
    }

    /**
     * Gets the value of the brandXPath property.
     * 
     * @return
     *     possible object is
     *     {@link PageConstant.BrandXPath }
     *     
     */
    public PageConstant.BrandXPath getBrandXPath() {
        return brandXPath;
    }

    /**
     * Sets the value of the brandXPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageConstant.BrandXPath }
     *     
     */
    public void setBrandXPath(PageConstant.BrandXPath value) {
        this.brandXPath = value;
    }

    /**
     * Gets the value of the pageSizeStartSignal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPageSizeStartSignal() {
        return pageSizeStartSignal;
    }

    /**
     * Sets the value of the pageSizeStartSignal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPageSizeStartSignal(String value) {
        this.pageSizeStartSignal = value;
    }

    /**
     * Gets the value of the pageSizeTagName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPageSizeTagName() {
        return pageSizeTagName;
    }

    /**
     * Sets the value of the pageSizeTagName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPageSizeTagName(String value) {
        this.pageSizeTagName = value;
    }

    /**
     * Gets the value of the pageSizeIgnoreText property.
     * 
     * @return
     *     possible object is
     *     {@link PageConstant.PageSizeIgnoreText }
     *     
     */
    public PageConstant.PageSizeIgnoreText getPageSizeIgnoreText() {
        return pageSizeIgnoreText;
    }

    /**
     * Sets the value of the pageSizeIgnoreText property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageConstant.PageSizeIgnoreText }
     *     
     */
    public void setPageSizeIgnoreText(PageConstant.PageSizeIgnoreText value) {
        this.pageSizeIgnoreText = value;
    }

    /**
     * Gets the value of the pageSizeXPath property.
     * 
     * @return
     *     possible object is
     *     {@link PageConstant.PageSizeXPath }
     *     
     */
    public PageConstant.PageSizeXPath getPageSizeXPath() {
        return pageSizeXPath;
    }

    /**
     * Sets the value of the pageSizeXPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageConstant.PageSizeXPath }
     *     
     */
    public void setPageSizeXPath(PageConstant.PageSizeXPath value) {
        this.pageSizeXPath = value;
    }

    /**
     * Gets the value of the eachPageStartSignal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEachPageStartSignal() {
        return eachPageStartSignal;
    }

    /**
     * Sets the value of the eachPageStartSignal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEachPageStartSignal(String value) {
        this.eachPageStartSignal = value;
    }

    /**
     * Gets the value of the eachPageTagName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEachPageTagName() {
        return eachPageTagName;
    }

    /**
     * Sets the value of the eachPageTagName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEachPageTagName(String value) {
        this.eachPageTagName = value;
    }

    /**
     * Gets the value of the eachPageIgnoreText property.
     * 
     * @return
     *     possible object is
     *     {@link PageConstant.EachPageIgnoreText }
     *     
     */
    public PageConstant.EachPageIgnoreText getEachPageIgnoreText() {
        return eachPageIgnoreText;
    }

    /**
     * Sets the value of the eachPageIgnoreText property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageConstant.EachPageIgnoreText }
     *     
     */
    public void setEachPageIgnoreText(PageConstant.EachPageIgnoreText value) {
        this.eachPageIgnoreText = value;
    }

    /**
     * Gets the value of the eachPageXPath property.
     * 
     * @return
     *     possible object is
     *     {@link PageConstant.EachPageXPath }
     *     
     */
    public PageConstant.EachPageXPath getEachPageXPath() {
        return eachPageXPath;
    }

    /**
     * Sets the value of the eachPageXPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageConstant.EachPageXPath }
     *     
     */
    public void setEachPageXPath(PageConstant.EachPageXPath value) {
        this.eachPageXPath = value;
    }

    /**
     * Gets the value of the detailPageStartSignal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailPageStartSignal() {
        return detailPageStartSignal;
    }

    /**
     * Sets the value of the detailPageStartSignal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailPageStartSignal(String value) {
        this.detailPageStartSignal = value;
    }

    /**
     * Gets the value of the detailPageEndSignal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailPageEndSignal() {
        return detailPageEndSignal;
    }

    /**
     * Sets the value of the detailPageEndSignal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailPageEndSignal(String value) {
        this.detailPageEndSignal = value;
    }

    /**
     * Gets the value of the detailPageTagName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDetailPageTagName() {
        return detailPageTagName;
    }

    /**
     * Sets the value of the detailPageTagName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDetailPageTagName(String value) {
        this.detailPageTagName = value;
    }

    /**
     * Gets the value of the detailPageIgnoreText property.
     * 
     * @return
     *     possible object is
     *     {@link PageConstant.DetailPageIgnoreText }
     *     
     */
    public PageConstant.DetailPageIgnoreText getDetailPageIgnoreText() {
        return detailPageIgnoreText;
    }

    /**
     * Sets the value of the detailPageIgnoreText property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageConstant.DetailPageIgnoreText }
     *     
     */
    public void setDetailPageIgnoreText(PageConstant.DetailPageIgnoreText value) {
        this.detailPageIgnoreText = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link PageConstant.Data }
     *     
     */
    public PageConstant.Data getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link PageConstant.Data }
     *     
     */
    public void setData(PageConstant.Data value) {
        this.data = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ignoreText" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ignoreText"
    })
    public static class BrandIgnoreText {

        @XmlElement(namespace = "http://huyng/schema/laptop")
        protected List<String> ignoreText;

        /**
         * Gets the value of the ignoreText property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ignoreText property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIgnoreText().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getIgnoreText() {
            if (ignoreText == null) {
                ignoreText = new ArrayList<String>();
            }
            return this.ignoreText;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;all>
     *         &lt;element name="container" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/all>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class BrandXPath {

        @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
        protected String container;
        @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
        protected String link;
        @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
        protected String name;

        /**
         * Gets the value of the container property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContainer() {
            return container;
        }

        /**
         * Sets the value of the container property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContainer(String value) {
            this.container = value;
        }

        /**
         * Gets the value of the link property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLink() {
            return link;
        }

        /**
         * Sets the value of the link property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLink(String value) {
            this.link = value;
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

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="entity" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "entity"
    })
    public static class Data {

        @XmlElement(namespace = "http://huyng/schema/laptop")
        protected List<PageConstant.Data.Entity> entity;

        /**
         * Gets the value of the entity property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entity property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntity().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link PageConstant.Data.Entity }
         * 
         * 
         */
        public List<PageConstant.Data.Entity> getEntity() {
            if (entity == null) {
                entity = new ArrayList<PageConstant.Data.Entity>();
            }
            return this.entity;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "key",
            "value"
        })
        public static class Entity {

            @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
            protected String key;
            @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
            protected String value;

            /**
             * Gets the value of the key property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKey() {
                return key;
            }

            /**
             * Sets the value of the key property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKey(String value) {
                this.key = value;
            }

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ignoreText" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ignoreText"
    })
    public static class DetailPageIgnoreText {

        @XmlElement(namespace = "http://huyng/schema/laptop")
        protected List<String> ignoreText;

        /**
         * Gets the value of the ignoreText property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ignoreText property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIgnoreText().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getIgnoreText() {
            if (ignoreText == null) {
                ignoreText = new ArrayList<String>();
            }
            return this.ignoreText;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ignoreText" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ignoreText"
    })
    public static class EachPageIgnoreText {

        @XmlElement(namespace = "http://huyng/schema/laptop")
        protected List<String> ignoreText;

        /**
         * Gets the value of the ignoreText property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ignoreText property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIgnoreText().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getIgnoreText() {
            if (ignoreText == null) {
                ignoreText = new ArrayList<String>();
            }
            return this.ignoreText;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;all>
     *         &lt;element name="container" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="image" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/all>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class EachPageXPath {

        @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
        protected String container;
        @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
        protected String link;
        @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
        protected String name;
        @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
        protected String image;
        @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
        protected String price;

        /**
         * Gets the value of the container property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContainer() {
            return container;
        }

        /**
         * Sets the value of the container property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContainer(String value) {
            this.container = value;
        }

        /**
         * Gets the value of the link property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLink() {
            return link;
        }

        /**
         * Sets the value of the link property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLink(String value) {
            this.link = value;
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
         * Gets the value of the image property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getImage() {
            return image;
        }

        /**
         * Sets the value of the image property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setImage(String value) {
            this.image = value;
        }

        /**
         * Gets the value of the price property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrice() {
            return price;
        }

        /**
         * Sets the value of the price property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrice(String value) {
            this.price = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="ignoreText" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "ignoreText"
    })
    public static class PageSizeIgnoreText {

        @XmlElement(namespace = "http://huyng/schema/laptop")
        protected List<String> ignoreText;

        /**
         * Gets the value of the ignoreText property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ignoreText property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getIgnoreText().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getIgnoreText() {
            if (ignoreText == null) {
                ignoreText = new ArrayList<String>();
            }
            return this.ignoreText;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;all>
     *         &lt;element name="allATagWithoutNext" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *       &lt;/all>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class PageSizeXPath {

        @XmlElement(namespace = "http://huyng/schema/laptop", required = true)
        protected String allATagWithoutNext;

        /**
         * Gets the value of the allATagWithoutNext property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAllATagWithoutNext() {
            return allATagWithoutNext;
        }

        /**
         * Sets the value of the allATagWithoutNext property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAllATagWithoutNext(String value) {
            this.allATagWithoutNext = value;
        }

    }

}
