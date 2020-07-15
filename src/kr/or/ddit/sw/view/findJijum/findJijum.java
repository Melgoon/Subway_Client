package kr.or.ddit.sw.view.findJijum;

import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class findJijum implements Initializable {

    public WebView webview;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebEngine webEngine = webview.getEngine();
        webEngine.load("https://www.google.com/maps/d/embed?mid=1w4e3Uu8viyvqVupLQHT1ruqcccqZV23v");
    }
}
