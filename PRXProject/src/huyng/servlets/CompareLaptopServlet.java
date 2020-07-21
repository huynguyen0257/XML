package huyng.servlets;

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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

@WebServlet("/CompareLaptopServlet")
public class CompareLaptopServlet extends HttpServlet {
    private final String ERROR = "error.jsp";
    private final String COMPARE_VIEW_PAGE = "compare.jsp";

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
            int id1 = Integer.parseInt(request.getParameter("id1"));
            int id2 = Integer.parseInt(request.getParameter("id2"));
            String compareString = request.getParameter("compareType");
            LaptopService.CompareType compareType = null;
            if (compareString == null) {
                compareType = LaptopService.CompareType.ALL;
            } else {
                LaptopService.CompareType[] type = LaptopService.CompareType.values();
                for (int i = 0; i < type.length; i++) {
                    if (compareString.toLowerCase().equals(type[i].toString().toLowerCase()))
                        compareType = type[i];
                }
            }
            List<Integer> list = new ArrayList<>();
            list.add(id1);
            list.add(id2);
            Hashtable<LaptopEntity, Double> compareLaptop = service.getCompareLaptop(list, compareType);
            LaptopEntityList laptops = new LaptopEntityList(Collections.list(compareLaptop.keys()));

            JAXBHelper.marshallerToTransfer(laptops, response.getOutputStream());
        } catch (Exception e) {
            request.setAttribute("ERROR", "ERROR IN CompareLaptopServlet \n Message: " + e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request,response);
        }
    }
}
