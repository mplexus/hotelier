import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HotelService {
    private static final String allHotelsUrl = "http://ibe.dev.youtravel.com/exercise/hotels";
    private static final String availableHotelsUrl = "http://ibe.dev.youtravel.com/exercise/availability";

    public String listAll() {
        return getList(allHotelsUrl);
    }

    public File listAllDemo() {
        return new File("webapps/hotelier/styles/listAllDemo.xml");
    }

    public String listAvailableDemo() {
        try{
            StringBuffer fileData = new StringBuffer();
            BufferedReader reader = new BufferedReader(new FileReader("webapps/hotelier/styles/listAllDemo.xml"));
            char[] buf = new char[1024];
            int numRead=0;
            while((numRead=reader.read(buf)) != -1){
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
            reader.close();
            return fileData.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String listAvailable(int rooms) {
        String url = availableHotelsUrl;
        if (rooms > 0) {
            url += "?rooms=" + rooms;
        }
        return getList(url);
    }

    private String getList(String urlStr) {
        StringBuilder strBuf = new StringBuilder();

        HttpURLConnection conn=null;
        BufferedReader reader=null;
        try{
            URL url = new URL(urlStr);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "text/xml");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : "
                              + conn.getResponseCode());
            }

	        reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String output = null;
            while ((output = reader.readLine()) != null)
                strBuf.append(output);
        }catch(MalformedURLException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        finally
        {
            if(reader!=null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null)
            {
                conn.disconnect();
            }
        }

        return strBuf.toString();
    }
}
