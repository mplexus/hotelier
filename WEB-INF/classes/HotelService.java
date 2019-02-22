import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class HotelService {
    private static final String allHotelsUrl = "http://ibe.dev.youtravel.com/exercise/hotels";
    private static final String availableHotelsUrl = "http://ibe.dev.youtravel.com/exercise/availability?rooms=2";

    public String listAll() {
        return getList(allHotelsUrl);
    }

    public String listAvailable() {
        return getList(availableHotelsUrl);
    }

    private String getList(String urlStr) {
        StringBuilder strBuf = new StringBuilder();

        HttpURLConnection conn=null;
        BufferedReader reader=null;
        try{
            URL url = new URL(urlStr);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

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
