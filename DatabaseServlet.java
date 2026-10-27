import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DatabaseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request , HttpServletResponse response) throws IOException ,ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "pavi2007@k";
        out.println("<html><body>");
        out.println("<h2>Employee Records</h2>");

        out.println("<table border='1'>");
        out.println("<tr>");
        out.println("<th>ID</th><th>Age</th>");
        out.println("<th>First Name</th><th>Last Name</th>");
        out.println("</tr>");
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url,user,password);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select id,age,first,last from employee");

            while (rs.next()) {
                out.println("<tr>");

                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getInt("age") + "</td>");
                out.println("<td>" + rs.getString("first") + "</td>");
                out.println("<td>" + rs.getString("last") + "</td>");

                out.println("</tr>");
            }

            rs.close();
            stmt.close();
            con.close();

        }       
        catch(Exception e)
        {
            e.printStackTrace();
            out.println("<tr><td colspan='4'>");
            out.println("Error: " + e.getMessage());
            out.println("</td></tr>");
        }     
         out.println("</table>");
        out.println("</body></html>");
    }
}
