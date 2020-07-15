package kr.or.ddit.sw.view.join;

import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class FindAdr implements Initializable {

    public WebView webview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       WebEngine webEngine = webview.getEngine();
       webEngine.load("http://localhost:8088/chatbot/sample.html");
    }
}
