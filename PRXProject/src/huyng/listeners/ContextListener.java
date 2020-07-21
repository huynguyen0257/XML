package huyng.listeners;

import huyng.crawler.MainThread;
import huyng.daos.BrandDAO;
import huyng.daos.MonitorDAO;
import huyng.daos.ProcessorDAO;
import huyng.daos.RamDAO;
import huyng.entities.*;
import huyng.services.LaptopService;

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
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                    Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, "JAXBException e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
                }
            }
        }

        if (context.getAttribute("NAV_XSLT") == null) {
            String xslt = realPath + "WEB-INF/xsl/nav.xsl";
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
                Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, "JAXBException e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
            }
        }

        if (context.getAttribute("MONITOR") == null) {
            MonitorEntityList monitors = new MonitorEntityList();
            monitors.setMonitor(monitorDAO.findAllByNameQuery("Monitor.findAll"));
            try {
                String monitorXmlString = marshallToXmlString(monitors, MonitorEntityList.class);
                context.setAttribute("MONITOR", monitorXmlString);
            } catch (JAXBException e) {
                Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, "JAXBException e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
            }
        }

        if (context.getAttribute("BRAND") == null) {

            BrandEntityList brands = new BrandEntityList();
            brands.setBrand(brandDAO.findAllByNameQuery("Brand.findAll"));
            try {
                String brandXmlString = marshallToXmlString(brands, BrandEntityList.class);
                context.setAttribute("BRAND", brandXmlString);
            } catch (JAXBException e) {
                Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, "JAXBException e : " + e.getMessage() +"| Line:" + e.getStackTrace()[0].getLineNumber());
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
