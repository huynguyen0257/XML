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
import java.util.ArrayList;
import java.util.Collections;
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

    public List<String> getLevelOfProcessor() {
        Hashtable<String, String> result = new Hashtable<>();
        try {
            openConnection();
            List<ProcessorEntity> entities = em.createNamedQuery("Processor.findProcessorInUse").getResultList();
            entities.forEach(e -> {
                String level = null;
                String name = e.getName().replaceAll("[+]", "-");
                if (e.getBrand().equals("Intel")) level = name.substring(0, name.indexOf("-"));
                if (e.getBrand().equals("AMD")) level = name.split(" ")[0] + " " + name.split(" ")[1];
                level = level.trim();
                name = level.substring(level.lastIndexOf(" ") + 1);
                if (!result.containsKey(name)) result.put(name, level);
            });
        } finally {
            closeConnection();
        }
        List<String> rs = new ArrayList<String>(result.values());
        Collections.sort(rs);
        return rs;
    }


    public ProcessorEntity getAMDProcessor(String cpuNumber, String cpuModel, String realPath) throws IOException, ParserConfigurationException, SAXException, TransformerException, XPathExpressionException, JAXBException {
        ProcessorEntity entity = null;
        String url = "https://www.amd.com/en/products/apu/amd-ryzen-" + cpuNumber + "-" + cpuModel + "#product-specs";
        String doc = null;
        try {
            doc = XMLHelper.getDocument2(url, "<div class=\"fieldset-wrapper\">", "div", null);
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
        ByteArrayOutputStream ps = TrAxHelper.transform(new ByteArrayInputStream(doc.getBytes("UTF-8")), realPath + CrawlerConstant.AMD_XSL_PATH, params);
        XMLHelper.validateXMLSchema(realPath + JAXBHelper.getXSDPath(ProcessorEntity.class), ps);
        String removeNSps = ps.toString().replaceAll("http://huyng/schema/processor", "");
        ByteArrayOutputStream outputStream = StringHelper.getByteArrayFromString(removeNSps);
        entity = (ProcessorEntity) JAXBHelper.unmarshalling(outputStream.toByteArray(), ProcessorEntity.class);
        return entity;
    }

    public boolean updateCount(ProcessorEntity entity) {
        try {
            openConnection();
            ProcessorEntity processor = em.find(ProcessorEntity.class, entity.getId());
            processor.setCount(entity.getCount());
            et.commit();
            return true;
        } finally {
            closeConnection();
        }
    }

    public void updateMark(ProcessorEntity entity) {
        try {
            openConnection();
            ProcessorEntity newEntity = em.find(ProcessorEntity.class, entity.getId());
            newEntity.setMark(entity.getMark());
            et.commit();
        } finally {
            closeConnection();
        }
    }
}
