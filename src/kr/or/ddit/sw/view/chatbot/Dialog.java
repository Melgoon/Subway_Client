package kr.or.ddit.sw.view.chatbot;////////////////////////// DIALOG //////////////////////////
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Dialog {

    public String dialog(String uuuid,String utext){
        String openApiURL = "http://aiopen.etri.re.kr:8000/Dialog";
        String accessKey = "72f34215-ab0c-44ed-8f0b-ffdb74b1db4d";    // 발급받은 API Key
        String uuid = uuuid;  // Open Dialog로 부터 생성된 UUID
        String method = "dialog";
        String text = utext; // method 호출 방식
        String text1 = null;
        Gson gson = new Gson();

        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        ////////////////////////// OPEN DIALOG //////////////////////////

        argument.put("uuid", uuid);
        argument.put("method", method);
        argument.put("text", text);

        request.put("access_key", accessKey);
        request.put("argument", argument);


        URL url;
        Integer responseCode = null;
        String responBody = null;
        try {
            url = new URL(openApiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(gson.toJson(request).getBytes("UTF-8"));
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();
            InputStream is = con.getInputStream();
            byte[] buffer = new byte[is.available()];
            int byteRead = is.read(buffer);
            responBody = new String(buffer);

            System.out.println("[responseCode] " + responseCode);
            System.out.println("[responBody]");
            System.out.println(responBody);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject) jsonParser.parse(responBody);
            JsonObject rtn = (JsonObject) jsonObject.get("return_object");
            JsonObject result = (JsonObject) rtn.get("result");
            text1 = String.valueOf(result.get("system_text"));
            text1 = text1.replaceAll("\"","");
            System.out.println(text1);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text1;
    }
}