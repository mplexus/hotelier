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

        String roomsStr = request.getParameter("rooms");
        int rooms = 0;
        try {
          rooms = Integer.parseInt(roomsStr);
        }
        catch (Exception e){
        }

        String starsStr = request.getParameter("stars");
        int stars = 0;
        try {
            stars = Integer.parseInt(starsStr);
        } catch (Exception e) {
        }

        try {

            //retrieve the promoted hotels list
            Integer[] promoted = hotelService.listPromoted();

            //retrieve once the complete hotel list
            if (hotelList.isEmpty()) {
                retrieveHotelList();
            }

            //retrieve the availability list
            String listAvailable = hotelService.listAvailable(rooms);
            //String listAvailable = hotelService.listAvailableDemo(); //demo

            Processor proc = new Processor(false);
            XsltCompiler comp = proc.newXsltCompiler();
            XsltExecutable exp = comp.compile(new StreamSource(new File("webapps/hotelier/styles/list.xsl")));
            XdmNode source = proc.newDocumentBuilder().build(new StreamSource(new StringReader(listAvailable)));
            Serializer out = proc.newSerializer(response.getOutputStream());
            out.setOutputProperty(Serializer.Property.METHOD, "html");
            out.setOutputProperty(Serializer.Property.INDENT, "yes");
            XsltTransformer trans = exp.load();

            //inject the complete hotel list in the xsl transformation process
            HashMap hashMap = new HashMap();
            for (int i = 0; i < hotelList.size(); i++) {
                HashMap innerHashMap = new HashMap();
                Hotel hotel = hotelList.get(i);
                int id = hotel.getId();
                innerHashMap.put("name", hotel.getName());
                innerHashMap.put("stars", hotel.getStars_Rating());
                innerHashMap.put("description", hotel.getDescription());

                /*
                for (Photo p : hotel.getPhotos().getPhotos()) {
                    System.out.println("value: " + p.getContent());
                    System.out.println("featured: " + p.getFeatured());
                }
                */
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
            }

            trans.setParameter(new QName("mapData"), XdmMap.makeMap(hashMap));
            trans.setParameter(new QName("filterstars"), XdmValue.makeValue(Integer.valueOf(stars)));
            trans.setParameter(new QName("promoted"), XdmValue.makeValue(promoted));
            trans.setInitialContextNode(source);
            trans.setDestination(out);
            trans.transform();
            System.err.println(listAvailable);
        } catch (Exception err) {
            ServletOutputStream out = response.getOutputStream();
            out.println(err.getMessage());
            err.printStackTrace();
        }
    }

    /**
     * Retrieve the complete hotel list.
     * Enable or disable demo-commented lines to utilize a demo list from file (copied from the real request).
     */
    private void retrieveHotelList() {
        try {
            XMLInputFactory xif = XMLInputFactory.newFactory();
            XMLStreamReader xsr = xif.createXMLStreamReader(new StringReader(hotelService.listAll()));
            //File file = hotelService.listAllDemo(); FileReader reader = new FileReader(file); XMLStreamReader xsr = xif.createXMLStreamReader(reader); //demo
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
        //} catch (FileNotFoundException fnfe) { fnfe.printStackTrace(); //demo
        } catch (XMLStreamException se) {
	        se.printStackTrace();
        } catch (JAXBException je) {
	         je.printStackTrace();
        }
    }
}
