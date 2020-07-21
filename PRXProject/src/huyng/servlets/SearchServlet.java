package huyng.servlets;

import huyng.entities.LaptopEntityList;
import huyng.services.LaptopService;
import huyng.utils.JAXBHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/xml");
        LaptopService service = new LaptopService();
        try {
            String txtSearch = request.getParameter("txtSearch");
            LaptopEntityList laptops = service.getByName(txtSearch);
            JAXBHelper.marshallerToTransfer(laptops,response.getOutputStream());
        }catch (Exception e){
            request.setAttribute("ERROR", "ERROR IN SearchServlet \n Message: "+e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }


}
