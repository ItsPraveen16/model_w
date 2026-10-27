import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class FirstServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
    {
        response.setContentType("text/html");
        int n1 = Integer.parseInt(request.getParameter("num1"));
        int n2 = Integer.parseInt(request.getParameter("num2"));
        int sum=n1+n2;
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<p> Num1: "+n1 +" Num2: "+n2+" The Sum: "+sum+" </p>");
        out.println("</body></html>");
        out.close();
    }
}
