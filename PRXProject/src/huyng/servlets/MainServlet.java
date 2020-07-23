package huyng.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
    private final String ERROR = "error.jsp";
    private final String SHOW_LIST_LAPTOP_SERVLET = "ShowListLaptopServlet";
    private final String DETAIL_LAPTOP_SERVLET = "DetailLaptopServlet";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }
    protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("btAction");
        String url = ERROR;
        try{
            if (action.equals("showAllLaptop")){
                url = SHOW_LIST_LAPTOP_SERVLET;
            }else if (action.equals("showDetailLaptop")){
                url = DETAIL_LAPTOP_SERVLET;
            }else{
                request.setAttribute("ERROR", "ERROR IN MainServlet \n Message: btAction not support!!!");
            }
        }catch (Exception e){
            request.setAttribute("ERROR", "ERROR IN MainServlet \n Message: "+e.getMessage());
        }finally {
            request.getRequestDispatcher(url).forward(request,response);
        }

    }
}
