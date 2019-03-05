import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonException;
import javax.json.stream.JsonParsingException;
//import javax.jms.IllegalStateException;

public class HotelService {
    private static final String allHotelsUrl = "http://ibe.dev.youtravel.com/exercise/hotels";
    private static final String availableHotelsUrl = "http://ibe.dev.youtravel.com/exercise/availability";
    private static final String promotedHotelsUrl = "http://ibe.dev.youtravel.com/exercise/promoted-hotels";

    private HttpURLConnection conn = null;

    public String listAll() {
        return getList(this.allHotelsUrl);
    }

    public File listAllDemo() {
        return new File("webapps/hotelier/styles/listAllDemo.xml");
    }

    public Integer[] listPromoted() {
        JsonObject obj = this.getJson(this.promotedHotelsUrl);
        JsonArray arr = obj.getJsonArray("hotel-ids");
        int total = arr.size();
        if (total <= 0) {
            return null;
        }
        Integer[] promoted = new Integer[total];
        for (int i = 0; i < total; i++) {
            promoted[i] = Integer.valueOf(arr.getJsonString(i).getString());
        }
        return promoted;
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
        String url = this.availableHotelsUrl;
        if (rooms > 0) {
            url += "?rooms=" + rooms;
        }
        return getList(url);
    }

    private String getList(String urlStr) {
        StringBuilder strBuf = new StringBuilder();
        BufferedReader reader = null;
        try{
            reader = readFromUrl(urlStr);
            String output = null;
            while ((output = reader.readLine()) != null)
                strBuf.append(output);
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null) {
                conn.disconnect();
            }
        }

        return strBuf.toString();
    }

    private JsonObject getJson(String urlStr) {
        JsonReader jsonReader=null;
        BufferedReader reader = null;
        JsonObject obj = null;

        try{
            reader = readFromUrl(urlStr);
	        jsonReader = Json.createReader(reader);
            obj = jsonReader.readObject();
        } catch(JsonException je) {
            je.printStackTrace();
        } finally {
            if (jsonReader!=null) {
                jsonReader.close();
            }
            if (reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(conn!=null) {
                conn.disconnect();
            }
        }

        return obj;
    }

    private BufferedReader readFromUrl(String urlStr) {
        BufferedReader reader = null;
        conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "text/xml");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : "
                              + conn.getResponseCode());
            }

	        reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reader;
    }
}
