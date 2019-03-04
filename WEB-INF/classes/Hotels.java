import java.io.* ;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.* ;
import javax.servlet.http.* ;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.TransformerFactory;

import javax.xml.bind.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;

import com.sun.xml.bind.v2.*;

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

    private List<Hotel> hotelList = new ArrayList<Hotel>();

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


        if (hotelList.isEmpty()) {
            retrieveHotelList();
        }

        String listAvailable = hotelService.listAvailable(rooms);
        //DocumentBuilder db = DocumentBuilderFactory.newDocumentBuilder();
        //InputStream listInputStream = new InputStream(new StringReader(listAvailable));
        //Document dom = db.parse(new InputSource(new StringReader(listAvailable)));

        try {
            //String style = "styles/list.xsl";
            //String stylePath = getServletContext().getRealPath(style);
            //if (stylePath==null) {
            //    throw new XPathException("Stylesheet " + style + " not found");
            //}
            //TransformerFactory factory = TransformerFactory.newInstance();
            //Templates templates = factory.newTemplates(new StreamSource(listAvailable));
            //Transformer transformer = templates.newTransformer();

            //String mime = templates.getOutputProperties().getProperty(OutputKeys.MEDIA_TYPE);
            //if (mime==null) {
               // guess
            //    response.setContentType("text/html");
            //} else {
            //    response.setContentType(mime);
            //}

            //Processor processor = new Processor(false);
            //XsltCompiler compiler = processor.newXsltCompiler();
            //XsltExecutable stylesheet = compiler.compile(new StreamSource(new File(path)), new StreamResult(out));
            //XsltTransformer transformer = stylesheet.load();
            Processor proc = new Processor(false);
            XsltCompiler comp = proc.newXsltCompiler();
            XsltExecutable exp = comp.compile(new StreamSource(new File("webapps/hotelier/styles/list.xsl")));
            //XdmNode source = proc.newDocumentBuilder().build(new StreamSource(new File("webapps/hotelier/styles/list.xml")));
            XdmNode source = proc.newDocumentBuilder().build(new StreamSource(new StringReader(listAvailable)));
            Serializer out = proc.newSerializer(response.getOutputStream());
            out.setOutputProperty(Serializer.Property.METHOD, "html");
            out.setOutputProperty(Serializer.Property.INDENT, "yes");
            //Xslt30Transformer trans = exp.load30();
            XsltTransformer trans = exp.load();

            //HashMap<QName, XdmValue> parameters = new HashMap<>();
            HashMap hashMap = new HashMap();
            for (int i = 0; i < hotelList.size(); i++) {
                //XdmNode document = proc.newDocumentBuilder().build(new StreamSource(new StringReader(hotelService.listAll())));
                //int id = hotelList.get(i).getId();
                //String expression = "/Hotels/Hotel[@id=" + "'" + id + "'" + "]";
                //XPathExecutable exec = proc.newXPathCompiler().compile(expression);
                //XPathSelector selector = exec.load();
                //selector.setContextItem((XdmItem)document);
                //XdmValue result = selector.evaluate();

                HashMap innerHashMap = new HashMap();
                Hotel hotel = hotelList.get(i);
                int id = hotel.getId();
                innerHashMap.put("name", hotel.getName());
                innerHashMap.put("stars", hotel.getStars_Rating());
                innerHashMap.put("description", hotel.getDescription());

                for (Photo p : hotel.getPhotos().getPhotos()) {
                    System.out.println("value: " + p.getContent());
                    System.out.println("featured: " + p.getFeatured());
                }
                /*
                List<Photo> photos = hotel.getPhotos();
                hotel.getPhotos().getPhotos()
                for (int j = 0; j < photos.size(); j++) {
                    System.err.println(photos.get(j));
                    System.err.println(photos.get(j).getFeatured());
                    //if (Boolean.TRUE.equals(photos.get(j).getFeatured())) {
                        innerHashMap.put("photo", photos.get(j).getContent());
                    //}
                }*/
                hashMap.put(id, innerHashMap);
                //parameters.put(new QName((String)(id)), hotelList.get(i).getName());
                //System.err.println(XdmValue.makeSequence((Iterable<Hotel>)hotelList));
                //out.serializeXdmValue(result);
                //trans.setParameter(new QName("hotelnames"), result);

                //parameters.put(new QName("mapData"), xdmMap);
                //trans.setStylesheetParameters(parameters);
                //trans.setInitialContextNode(xdmMap);
                //proc.writeXdmValue(result, out);
            }
            //XdmMap xdmMap = XdmMap.makeMap(parameters);
            XdmMap xdmMap = XdmMap.makeMap(hashMap);
            trans.setParameter(new QName("mapData"), xdmMap);
            trans.setInitialContextNode(source);
            trans.setDestination(out);
            trans.transform();
            //trans.applyTemplates(new StreamSource(new File("webapps/hotelier/styles/list.xsl")), trans.newSerializer(response.getOutputStream()));

            //transformer.transform(new StreamSource(listAvailable), new StreamResult(out));
            //out.println(listAvailable);
            System.err.println(listAvailable);
        } catch (Exception err) {
            ServletOutputStream out = response.getOutputStream();
            out.println(err.getMessage());
            err.printStackTrace();
        }
    }

    private void retrieveHotelList() {
        //File file = hotelService.listAllDemo();
        try {
            //FileReader reader = new FileReader(file);
            //XMLStreamReader xsr = xif.createXMLStreamReader(reader);
            XMLInputFactory xif = XMLInputFactory.newFactory();
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(hotelService.listAll()));
            JAXBContext jaxbContext = JAXBContext.newInstance(Hotel.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            while(xsr.hasNext()) {
                int eventType = xsr.next();
                switch (eventType) {
                    case XMLStreamReader.START_ELEMENT:
                        String elementName = xsr.getLocalName();
                        if (elementName.equals("Hotel")) {
                            Hotel hotel = (Hotel) jaxbUnmarshaller.unmarshal(xsr);
                            hotelList.add(hotel);
                        }
                        break;
                    case XMLStreamReader.END_ELEMENT:
                        break;
                }
            }
        //} catch (FileNotFoundException fnfe) {
            //fnfe.printStackTrace();
        } catch (XMLStreamException se) {
	        se.printStackTrace();
        } catch (JAXBException je) {
	         je.printStackTrace();
        }
    }
}
