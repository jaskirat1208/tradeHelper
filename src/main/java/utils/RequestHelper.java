package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

public class RequestHelper {
    public String CommonHttpDataFetcher(String uri) {
        return commonHttpConnectionDataFetcher(uri);
    }

    public String commonHttpConnectionDataFetcher(String desiredUrl) {

        URL url;
        BufferedReader reader = null;
        HttpURLConnection connection=null;
        String line;
        try {
            // create the HttpURLConnection
            url = new URL(desiredUrl);
            connection = (HttpURLConnection) url.openConnection();


            // just want to do an HTTP GET here
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept-Encoding", "gzip,deflate");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Host", "www1.nseindia.com");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Connection", "Keep-Alive");


            // uncomment this if you want to write output to this url
            // connection.setDoOutput(true);

            // give it 15 seconds to respond
            connection.setReadTimeout(30 * 1000);
            connection.connect();

            // read the output from the server
            GZIPInputStream gzip = new GZIPInputStream(connection.getInputStream());
            reader = new BufferedReader(new InputStreamReader(gzip));
            while ((line = reader.readLine()) != null) {
                if(!line.isEmpty()) {
                    break;
                }
            }

            if(line == null)
                return null;

            return line;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            if(connection!=null) {
                connection.disconnect();
            }
            // close the reader; this can throw an exception too, so
            // wrap it in another try/catch block.
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

    }
}
