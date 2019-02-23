import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;

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
        out.println(list);

        out.println("</html></body>") ;
    }
}
