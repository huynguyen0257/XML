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

@WebServlet("/AdviceServlet")
public class AdviceServlet extends HttpServlet {
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
            String ramMemory = request.getParameter("ramMemory");
            String monitorSize = request.getParameter("monitorSize");
            String priceString = request.getParameter("priceString");
            String processorLevel = request.getParameter("processorLevel");
            LaptopEntityList laptops = service.getWithSuggest(processorLevel,ramMemory,monitorSize,priceString);
            JAXBHelper.marshallerToTransfer(laptops,response.getOutputStream());
        }catch (Exception e){
            request.setAttribute("ERROR", "ERROR IN AdviceServlet \n Message: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }
}
