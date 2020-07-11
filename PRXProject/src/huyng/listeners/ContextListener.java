package huyng.listeners;

import huyng.entities.LaptopEntity;
import huyng.entities.LaptopEntityList;
import huyng.services.LaptopService;
import huyng.utils.JAXBHelper;
import huyng.utils.XMLHelper;

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
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.util.List;

@WebListener()
public class ContextListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    LaptopService laptopService = null;
    // Public constructor is required by servlet spec
    public ContextListener() {
        laptopService = new LaptopService();
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        ServletContext context =sce.getServletContext();
        String realPath = context.getRealPath("/");
        System.out.println(realPath);
        LaptopEntityList laptops = new LaptopEntityList();
        laptops.setLaptop( laptopService.getAll());
        String xmlString = null;
        String xslt = realPath + "WEB-INF/xsl/index.xsl";
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(laptops.getClass(),LaptopEntity.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING,"UTF-8");

            StringWriter writer = new StringWriter();
            marshaller.marshal(laptops,writer);
            xmlString = writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
//        context.setAttribute("INFO",xmlString);
//        StreamSource source = new StreamSource(xmlString);
        context.setAttribute("INFO",xmlString);

        try {
            StreamSource xslSource = new StreamSource(new FileInputStream(xslt));
            TransformerFactory transfact = TransformerFactory.newInstance();
            Templates xslTemplate = transfact.newTemplates(xslSource);
            context.setAttribute("xslt",xslTemplate);
        } catch (FileNotFoundException | TransformerConfigurationException e) {
            e.printStackTrace();
        }
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
