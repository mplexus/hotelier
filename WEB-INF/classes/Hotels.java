import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;
import net.sf.saxon.s9api.SaxonApiException;

public class Hotels extends HttpServlet {

    private HotelService hotelService = null;

    public Hotels() {
        super();
        if (hotelService == null) {
            hotelService = new HotelService();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        response.setContentType("text/html") ;

        PrintWriter out = response.getWriter() ;
        out.println("<html><body>") ;
        out.println("<h1>Hotels</h1>") ;

        String roomsStr = request.getParameter("rooms");
        int rooms = 0;
        try{
          rooms = Integer.parseInt(roomsStr);
        }
        catch(Exception e){
          rooms = 0;
        }

        String list = hotelService.listAvailable(rooms);


        try {
            Processor processor = new Processor(true);
            XsltCompiler compiler = processor.newXsltCompiler();
            XsltExecutable exe = compiler.compile(null);
            XsltTransformer transformer = exe.load();
            transformer.transform();
            out.println(list);
        } catch (SaxonApiException sae) {
            out.println(sae.getMessage());
        }

        out.println("</html></body>") ;
    }
}
