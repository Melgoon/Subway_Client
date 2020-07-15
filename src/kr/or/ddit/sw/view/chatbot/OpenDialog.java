package kr.or.ddit.sw.view.chatbot;////////////////////////// OPEN DIALOG //////////////////////////
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.*;
import kr.or.ddit.sw.service.login.LoginSession;

public class OpenDialog {
    public String open() {
        String openApiURL = "http://aiopen.etri.re.kr:8000/Dialog";
        String accessKey = "72f34215-ab0c-44ed-8f0b-ffdb74b1db4d";    // 발급받은 API Key
        String domain = "Subway";          // 도메인 명
        String access_method = "internal_data";   // 도메인 방식
        String method = "open_dialog";                      // method 호출 방식
        Gson gson = new Gson();

        Map<String, Object> request = new HashMap<>();
        Map<String, String> argument = new HashMap<>();

        ////////////////////////// OPEN DIALOG //////////////////////////

        argument.put("name", domain);
        argument.put("access_method", access_method);
        argument.put("method", method);

        request.put("access_key", accessKey);
        request.put("argument", argument);


        URL url;
        Integer responseCode = null;
        String responBody = null;
        String uuid = null;
        String system = null;
        try {
            url = new URL(openApiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
            //리턴을 json문자열로 한다.
            //이를 파싱해서 뽑아보자
            //먼저 파싱을 위해 JsonParser 객체를 만들어준다.
            JsonParser jsonParser = new JsonParser();
            //그다음 JsonArray를 선언하여 JsonParser로 문자열을 파싱한다.
            System.out.println(responBody);
            //json 전체를 파싱하고 싶을 때
            JsonObject jsonObject = (JsonObject) jsonParser.parse(responBody);
            //json안의 객체를 파싱하고 싶을 때
            String result = jsonObject.get("result").getAsString();
            System.out.println(result);
//            String return_object = jsonObject.get("return_object").get("uuid").getAsString();
//            System.out.println(return_object);

            //가장 큰 jsonObject를 가져옵니다
            JsonObject head = (JsonObject) jsonObject.get("return_object");
            //OpenDialog에 넣어야하는 값인 uuid를 변수로 만들어준다.
            uuid = String.valueOf(head.get("uuid"));
            uuid = uuid.replaceAll("\"","");
            // LoginSession에 UUID를 저장해서 다른 클래스에서도 사용할 수 있게 한다.
            // UUID를 자주 사용하기 때문에 LoginSession에 저장한다.
            LoginSession.uuid = uuid;
            JsonObject result1 = (JsonObject) head.get("result");
            system = String.valueOf(result1.get("system_text"));
            System.out.println(system);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return system;
    }
}