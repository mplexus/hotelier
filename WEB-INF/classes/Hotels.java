import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;

public class Hotels extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        response.setContentType("text/html") ;
        PrintWriter out = response.getWriter() ;
        out.println("<html><body>") ;
        out.println("<h1>Hotels</h1>") ;
        out.println("</html></body>") ;
    }
}
