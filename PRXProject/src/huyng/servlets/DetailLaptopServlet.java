package huyng.servlets;

import huyng.entities.LaptopEntity;
import huyng.entities.LaptopEntityList;
import huyng.services.LaptopService;
import huyng.utils.XMLHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;

@WebServlet("/DetailLaptopServlet")
public class DetailLaptopServlet extends HttpServlet {
    private final String ERROR = "error.jsp";
    private final String LAPTOP_DETAIL_VIEW_PAGE = "detail.jsp";
    private final int NUMBER_OF_ADVICE_LAPTOP = 3;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        LaptopService service = new LaptopService();
        try{
            int id = Integer.parseInt(request.getParameter("laptopId"));
            LaptopEntity currentLaptop = service.getLaptopById(id);
            if (currentLaptop != null){
                LaptopEntityList adviceLaptops = service.getAdviceLaptops(currentLaptop,NUMBER_OF_ADVICE_LAPTOP);
                String currentLaptopXMLString = XMLHelper.marshallToXmlString(currentLaptop,LaptopEntityList.class);

                request.setAttribute("ADVICE_LAPTOP",XMLHelper.marshallToXmlString(adviceLaptops,LaptopEntityList.class));
                request.setAttribute("CURRENT_LAPTOP",currentLaptopXMLString);
                url = LAPTOP_DETAIL_VIEW_PAGE;
            }else{
                request.setAttribute("ERROR","ERROR in DetailLaptopServlet\nMessage : Can not find Laptop with id = " + id);
            }
        } catch (JAXBException e) {
            request.setAttribute("ERROR","ERROR in DetailLaptopServlet\nMessage : "+e.getMessage());

        } finally {
            request.getRequestDispatcher(url).forward(request,response);
        }
    }
}
