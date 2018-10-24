import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Translator {


    public static String translate(String langFrom, String langTo, String text) throws IOException {

        String urlStr = "https://script.google.com/macros/s/AKfycbytMoojUs834dq1icOhICSVbFq7Q4e8W4-hHSKQVmEGY4o-wJw/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
     //   int code=con.getResponseCode();
        //  System.out.println(code);
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
            System.out.println(response);
        }
        in.close();
        return response.toString();
    }

}
//import java.io.BufferedReader;
//import java.io.OutputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//public class Translator {
//
//    private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
//    private static final String CLIENT_SECRET = "PUBLIC_SECRET";
//    private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";
//
//    public static String translate(String fromLang, String toLang, String text) throws Exception {
//
//        String jsonPayload = new StringBuilder()
//                .append("{")
//                .append("\"fromLang\":\"")
//                .append(fromLang)
//                .append("\",")
//                .append("\"toLang\":\"")
//                .append(toLang)
//                .append("\",")
//                .append("\"text\":\"")
//                .append(text)
//                .append("\"")
//                .append("}")
//                .toString();
//
//        URL url = new URL(ENDPOINT);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setDoOutput(true);
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
//        conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
//        conn.setRequestProperty("Content-Type", "application/json");
//
//        OutputStream os = conn.getOutputStream();
//        os.write(jsonPayload.getBytes());
//        os.flush();
//        os.close();
//
//        int statusCode = conn.getResponseCode();
//        BufferedReader br = new BufferedReader(new InputStreamReader(
//                (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
//        ));
//        String output,finalOutput="";
//        StringBuilder stringBuilder=new StringBuilder();
//        while ((output = br.readLine()) != null) {
//            stringBuilder.append(output);
//            finalOutput=stringBuilder.toString();
//
//        }
//
//        conn.disconnect();
//        return finalOutput;
//    }
//
//}
