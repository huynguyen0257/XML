package huyng.daos;

import huyng.constants.CrawlerConstant;
import huyng.entities.LaptopEntity;
import huyng.crawler.MainThread;
import huyng.utils.TrAxHelper;
import huyng.utils.XMLHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LaptopDAO extends BaseDAO<LaptopEntity, Integer> {
    public boolean checkExisted(String model) {
        boolean result;
        try {
            openConnection();
            result = em.createNamedQuery("Laptop.findByModel")
                    .setParameter("model", model)
                    .getResultList().size() != 0;
            et.commit();
        } finally {
            closeConnection();
        }
        return result;
    }

}
