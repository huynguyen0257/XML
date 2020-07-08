package huyng.daos;

import huyng.constants.CrawlerConstant;
import huyng.entities.ProcessorEntity;
import huyng.utils.JAXBHelper;
import huyng.utils.StringHelper;
import huyng.utils.TrAxHelper;
import huyng.utils.XMLHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

public class ProcessorDAO extends BaseDAO<ProcessorEntity, Integer> {
    public ProcessorDAO() {
        super();
    }

    public ProcessorEntity getByModel(String model) {
        ProcessorEntity result;
        try {
            openConnection();
            List results = em.createNamedQuery("Processor.findByModel")
                    .setParameter("model", model)
                    .getResultList();
            et.commit();
            if (results.size() != 0) result = (ProcessorEntity) results.get(0);
            else result = null;
        } finally {
            closeConnection();
        }
        return result;
    }

    public ProcessorEntity getAMDProcessor(String cpuNumber, String cpuModel) throws IOException, ParserConfigurationException, SAXException, TransformerException, XPathExpressionException, JAXBException {
        ProcessorEntity entity = null;
        String url = "https://www.amd.com/en/products/apu/amd-ryzen-" + cpuNumber + "-" + cpuModel + "#product-specs";
        String doc = null;
        try {
            doc = XMLHelper.getDocument(url, "<div class=\"fieldset-wrapper\">", "div", null);
        } catch (NullPointerException e) {
            return null;
        }
        Document dom = XMLHelper.parseStringToDOM(doc);

        String expCacheL1 = "//div[contains(text(),'Total L1 Cache')]/parent::node()/div[2]/text()";
        String expCacheL2 = "//div[contains(text(),'Total L2 Cache')]/parent::node()/div[2]/text()";
        String expCacheL3 = "//div[contains(text(),'Total L3 Cache')]/parent::node()/div[2]/text()";
        XPath xPath = XMLHelper.getXPath();
        String cacheL1 = ((String) xPath.evaluate(expCacheL1, dom, XPathConstants.STRING));
        String cacheL2 = ((String) xPath.evaluate(expCacheL2, dom, XPathConstants.STRING)).toLowerCase().replaceAll("(kb|mb)", "");
        String cacheL3 = ((String) xPath.evaluate(expCacheL3, dom, XPathConstants.STRING)).toLowerCase().replaceAll("(kb|mb)", "");
        double a = 0;
        if (!cacheL1.trim().isEmpty()) {
            a = Double.parseDouble(cacheL3) + Double.parseDouble(cacheL2) + Double.parseDouble(cacheL1.contains("KB") ? "0." + cacheL1.toLowerCase().replaceAll("(kb|mb)", "") : cacheL1.toLowerCase().replaceAll("(kb|mb)", ""));
        } else {
            a = Double.parseDouble(cacheL3) + Double.parseDouble(cacheL2);
        }
        Hashtable<String, String> params = new Hashtable<>();
        params.put("name", "Ryzen " + cpuNumber + " " + cpuModel);
        params.put("brand", "AMD");
        params.put("model", cpuModel);
        params.put("cache", a + "");
        ByteArrayOutputStream ps = TrAxHelper.transform(new ByteArrayInputStream(doc.getBytes("UTF-8")), CrawlerConstant.AMD_XSL_PATH, params);
        XMLHelper.validateXMLSchema(JAXBHelper.getXSDPath(ProcessorEntity.class), ps);
        String removeNSps = ps.toString().replaceAll("http://huyng/schema/processor", "");
        ByteArrayOutputStream outputStream = StringHelper.getByteArrayFromString(removeNSps);
        entity = (ProcessorEntity) JAXBHelper.unmarshalling(outputStream.toByteArray(), ProcessorEntity.class);
        return entity;
    }
}
