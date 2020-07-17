package huyng.listeners;

import huyng.crawler.KLCrawler;
import huyng.crawler.MainThread;
import huyng.crawler.PACrawler;
import huyng.daos.BrandDAO;
import huyng.daos.MonitorDAO;
import huyng.daos.ProcessorDAO;
import huyng.daos.RamDAO;
import huyng.entities.*;
import huyng.services.LaptopService;
import huyng.utils.JAXBHelper;
import huyng.utils.XMLHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.Collections;
import java.util.List;

@WebListener()
public class ContextListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    LaptopService laptopService = null;
    RamDAO ramDAO = null;
    ProcessorDAO processorDAO = null;
    MonitorDAO monitorDAO = null;
    BrandDAO brandDAO = null;

    // Public constructor is required by servlet spec
    public ContextListener() {
        laptopService = new LaptopService();
        ramDAO = new RamDAO();
        processorDAO = new ProcessorDAO();
        monitorDAO = new MonitorDAO();
        brandDAO = new BrandDAO();
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */

        ServletContext context = sce.getServletContext();
        String realPath = context.getRealPath("/");
        if (laptopService.getNumberOfLaptop() == 0){
            Thread mainThread = new Thread(new MainThread(realPath));
            mainThread.start();
            while (mainThread.isAlive()){
                try {
                    Thread.currentThread().sleep(10*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        if (context.getAttribute("INFO") == null) {
            LaptopEntityList laptops = laptopService.getAllWithPaging(6,0);
            String xmlString = null;
            try {
                xmlString = marshallToXmlString(laptops, LaptopEntityList.class);
                context.setAttribute("INFO", xmlString);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }

        if (context.getAttribute("ADVICE_LAPTOP_LIST_XSLT") == null || context.getAttribute("NAV_XSLT") == null) {
            String xslt = realPath + "WEB-INF/xsl/index.xsl";
            Source xslSource = new StreamSource(xslt);
            context.setAttribute("ADVICE_LAPTOP_LIST_XSLT", xslSource);
            xslt = realPath + "WEB-INF/xsl/nav.xsl";
            Source navXslSource = new StreamSource(xslt);
            context.setAttribute("NAV_XSLT", navXslSource);
        }

        if (context.getAttribute("RAM") == null) {
            RamEntityList rams = new RamEntityList();
            rams.setRam(ramDAO.findAllByNameQuery("Ram.getAll"));
            try {
                String ramXmlString = marshallToXmlString(rams, RamEntityList.class);
                context.setAttribute("RAM", ramXmlString);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }

        if (context.getAttribute("MONITOR") == null) {
            MonitorEntityList monitors = new MonitorEntityList();
            monitors.setMonitor(monitorDAO.findAllByNameQuery("Monitor.findAll"));
            try {
                String monitorXmlString = marshallToXmlString(monitors, MonitorEntityList.class);
                context.setAttribute("MONITOR", monitorXmlString);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }

        if (context.getAttribute("BRAND") == null) {

            BrandEntityList brands = new BrandEntityList();
            brands.setBrand(brandDAO.findAllByNameQuery("Brand.findAll"));
            try {
                String brandXmlString = marshallToXmlString(brands, BrandEntityList.class);
                context.setAttribute("BRAND", brandXmlString);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }

        if (context.getAttribute("PROCESSOR") == null){
            List<String> processor = processorDAO.getLevelOfProcessor();
            context.setAttribute("PROCESSOR", processor);
        }
    }

    private String marshallToXmlString(Object objects, Class T) throws JAXBException {
        JAXBContext jaxbContext = null;
        if (T.getName().equals(RamEntityList.class.getName()))
            jaxbContext = JAXBContext.newInstance(RamEntityList.class, RamEntity.class);
        if (T.getName().equals(LaptopEntityList.class.getName()))
            jaxbContext = JAXBContext.newInstance(LaptopEntityList.class, LaptopEntity.class);
        if (T.getName().equals(MonitorEntityList.class.getName()))
            jaxbContext = JAXBContext.newInstance(MonitorEntityList.class, MonitorEntity.class);
        if (T.getName().equals(BrandEntityList.class.getName()))
            jaxbContext = JAXBContext.newInstance(BrandEntityList.class, BrandEntity.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

        StringWriter writer = new StringWriter();
        marshaller.marshal(objects, writer);
        return writer.toString();
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        System.out.println("------App shuts down--------");
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attribute
         is replaced in a session.
      */
    }
}
