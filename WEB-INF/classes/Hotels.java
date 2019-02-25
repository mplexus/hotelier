import java.io.* ;

import javax.servlet.* ;
import javax.servlet.http.* ;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.TransformerFactory;

import net.sf.saxon.s9api.*;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.trans.XPathException;
import net.sf.saxon.s9api.XsltTransformer;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.TransformerFactoryImpl;

public class Hotels extends HttpServlet {

    private HotelService hotelService = null;

    public Hotels() {
        super();
        if (hotelService == null) {
            hotelService = new HotelService();
        }
    }

    public void init() throws ServletException {
        super.init();
        System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        response.setContentType("text/html") ;

        //PrintWriter out = response.getWriter() ;
        //ServletOutputStream out = response.getOutputStream();
        //out.println("<html><body>") ;
        //out.println("<h1>Hotels</h1>") ;

        String roomsStr = request.getParameter("rooms");
        int rooms = 0;
        try{
          rooms = Integer.parseInt(roomsStr);
        }
        catch(Exception e){
          rooms = 0;
        }

        String list = hotelService.listAvailable(rooms);
        //DocumentBuilder db = DocumentBuilderFactory.newDocumentBuilder();
        //InputStream listInputStream = new InputStream(new StringReader(list));
        //Document dom = db.parse(new InputSource(new StringReader(list)));

        try {
            String style = "styles/list.xsl";
            String stylePath = getServletContext().getRealPath(style);
            if (stylePath==null) {
                throw new XPathException("Stylesheet " + style + " not found");
            }
            TransformerFactory factory = TransformerFactory.newInstance();
            Templates templates = factory.newTemplates(new StreamSource(list));
            //Transformer transformer = templates.newTransformer();

            String mime = templates.getOutputProperties().getProperty(OutputKeys.MEDIA_TYPE);
            if (mime==null) {
               // guess
                response.setContentType("text/html");
            } else {
                response.setContentType(mime);
            }

            //Processor processor = new Processor(false);
            //XsltCompiler compiler = processor.newXsltCompiler();
            //XsltExecutable stylesheet = compiler.compile(new StreamSource(new File(path)), new StreamResult(out));
            //XsltTransformer transformer = stylesheet.load();
            Processor proc = new Processor(false);
            XsltCompiler comp = proc.newXsltCompiler();
            XsltExecutable exp = comp.compile(new StreamSource(new File("webapps/hotelier/styles/list.xsl")));
            //XdmNode source = proc.newDocumentBuilder().build(new StreamSource(new File("webapps/hotelier/styles/list.xml")));
            XdmNode source = proc.newDocumentBuilder().build(new StreamSource(new StringReader(hotelService.listAvailable(rooms))));
            Serializer out = proc.newSerializer(response.getOutputStream());
            out.setOutputProperty(Serializer.Property.METHOD, "html");
            out.setOutputProperty(Serializer.Property.INDENT, "yes");
            XsltTransformer trans = exp.load();
            trans.setInitialContextNode(source);
            trans.setDestination(out);
            trans.transform();

            //transformer.transform(new StreamSource(list), new StreamResult(out));
            //out.println(list);
        } catch (Exception err) {
            //out.println(err.getMessage());
            err.printStackTrace();
        }

        //out.println("</html></body>") ;
    }
}
