import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LinkTracker extends HttpServlet {

    static private Hashtable links = new Hashtable();

    String tstamp;

    public LinkTracker() {
        tstamp = new Date().toString();
    }

    public void doGet(HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {
        String link = request.getParameter("link");
        if (link != null && !link.equals("")) {
            synchronized (links){
                Integer count = (Integer) links.get(link);
                if (count == null) {
                    links.put(link, new Integer(1));
                }
                else {
                    links.put(link, new Integer(1+count.intValue()));
                }
            }
            response.sendRedirect(link);
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            request.getSession();
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Links Tracker Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>Links Tracked Since");
            out.println(tstamp+":</p>");

            if (links.size() != 0) {
                Enumeration myenum = links.keys();
                while (myenum.hasMoreElements()) {
                    String key = (String)myenum.nextElement();
                    int count = ((Integer)links.get(key)).intValue();
                    out.println(key+" : "+count+" visits<br>");
                }
            }
            else {
                out.println("No links have been tracked!<br>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    public void doPost(HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {
        doGet(request, response);
    }
}
