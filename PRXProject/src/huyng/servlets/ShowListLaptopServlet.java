package huyng.servlets;

import huyng.daos.BrandDAO;
import huyng.entities.BrandEntity;
import huyng.entities.LaptopEntity;
import huyng.entities.LaptopEntityList;
import huyng.services.LaptopService;
import huyng.utils.JAXBHelper;
import huyng.utils.XMLHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//TODO: Get with paging
@WebServlet("/ShowListLaptopServlet")
public class ShowListLaptopServlet extends HttpServlet {
    private final String ERROR = "error.jsp";
    private final String LIST_LAPTOP_VIEW_PAGE = "laptops.jsp";
    private final int PAGE_SIZE = 15;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = ERROR;
        try {
            String brandString = request.getParameter("brandId");
            String pageNumberString = request.getParameter("pageNumber");
            int pageNumber = Integer.parseInt(pageNumberString != null ? pageNumberString : "1");
            BrandDAO brandDAO = new BrandDAO();
            LaptopService service = new LaptopService();
            LaptopEntityList laptops;
            if (brandString == null || brandString.isEmpty()) {
                laptops = service.getAllWithPaging(PAGE_SIZE,pageNumber);
                request.setAttribute("BRAND_NAME","ALL LAPTOP");
            } else {
                int id = Integer.parseInt(brandString);
                laptops = service.getByBrandWithPaging(id,PAGE_SIZE,pageNumber);
                request.setAttribute("BRAND_NAME",brandDAO.findByID(id).getName().toUpperCase());
            }
            String laptopXmlString = XMLHelper.marshallToXmlString(laptops,LaptopEntityList.class);
            request.setAttribute("LIST_LAPTOP_XML",laptopXmlString);
            request.setAttribute("TOTAL_PAGE",laptops.getTotalPage());
            request.setAttribute("brandId",brandString);
            url = LIST_LAPTOP_VIEW_PAGE;
        } catch (Exception e) {
            request.setAttribute("ERROR", "ERROR IN ShowListLaptopServlet \n Message: "+e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
}
