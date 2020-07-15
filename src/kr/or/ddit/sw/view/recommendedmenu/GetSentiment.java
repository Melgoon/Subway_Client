package kr.or.ddit.sw.view.recommendedmenu;

import com.google.gson.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
 * Gson: https://github.com/google/gson
 * Maven info:
 *     groupId: com.google.code.gson
 *     artifactId: gson
 *     version: 2.8.1
 *
 * Once you have compiled or downloaded gson-2.8.1.jar, assuming you have placed it in the
 * same folder as this file (GetSentiment.java), you can compile and run this program at
 * the command line as follows.
 *
 * Execute the following two commands to build and run (change gson version if needed):
 * javac GetSentiment.java -classpath .;gson-2.8.1.jar -encoding UTF-8
 * java -cp .;gson-2.8.1.jar GetSentiment
 */

class Document {
    public String id, language, text;

    public Document(String id, String language, String text) {
        this.id = id;
        this.language = language;
        this.text = text;
    }
}

class Documents {
    public List<Document> documents;

    public Documents() {
        this.documents = new ArrayList<Document>();
    }

    public void add(String id, String language, String text) {
        this.documents.add(new Document(id, language, text));
    }
}

public class GetSentiment {
    static String subscription_key_var;
    static String subscription_key;
    static String endpoint_var;
    static String endpoint;

    public static void Initialize() throws Exception {
        subscription_key = "b0a2b4c6e58247f38fe2b922cf0c9a2d";
        endpoint = "https://subtext.cognitiveservices.azure.com";
    }

    static String path = "/text/analytics/v2.1/sentiment";

    public static String getTheSentiment(Documents documents) throws Exception {
        String text = new Gson().toJson(documents);
        byte[] encoded_text = text.getBytes("UTF-8");

        URL url = new URL(endpoint + path);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "text/json");
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscription_key);
        connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.write(encoded_text, 0, encoded_text.length);
        wr.flush();
        wr.close();

        StringBuilder response = new StringBuilder();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return response.toString();
    }

    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json_text);
        JsonObject json = parser.parse(json_text).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String id = jsonElement.getAsJsonObject().get("documents").getAsJsonArray().get(0).getAsJsonObject().get("score").toString();

        return id;
    }

    public static String returnMenu(String keyword){
        String response = null;
        try {
            Initialize();
            Documents documents = new Documents();
            documents.add("1","en",keyword);

            response = getTheSentiment(documents);
            System.out.println(prettify(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prettify(response);
    }

    public static void main(String[] args) {
        try {
            Initialize();
            Documents documents = new Documents();
            documents.add("1","en","happy sad fuck no way");

            String response = getTheSentiment(documents);
            System.out.println(prettify(response));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}