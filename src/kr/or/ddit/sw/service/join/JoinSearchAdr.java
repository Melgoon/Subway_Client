package kr.or.ddit.sw.service.join;

import javafx.concurrent.Worker;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class JoinSearchAdr {
    private JSObject javascriptConnector;
    private JavaConnector javaConnector = new JavaConnector();
    private TextField textField;


    public class JavaConnector{
        public void toLowerCase(String value){
            if(null != value){
                javascriptConnector.call("showResult",value);
            }
        }
        public void sendAddr(String addr, String extraAddr, String zonecode) {
            System.out.println("자바스크립트에서 보내온 메시지 출력 : " + addr);
            textField.setText(zonecode + " " + addr + " " + extraAddr);
            Stage stage = (Stage) textField.getScene().getWindow();
            stage.close();


        }
    }

    public void openAddrDialog() throws IOException {

        URL url = new File("web/sample.html").toURI().toURL();

        WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();

        // set up the listener
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED == newValue) { // 페이지 로딩이 정상으로 모두 읽혀진 경우...
                // set an interface object named 'javaConnector' in the web engine's page
                JSObject window = (JSObject) webEngine.executeScript("window"); // window 객체 가져오기
                window.setMember("javaConnector", javaConnector); // winddow 객체에 Java 객체 등록하기
                // get the Javascript connector object.
                //javascriptConnector = (JSObject) webEngine.executeScript("getJsConnector()");

            }
        });

        // now load the page
        webEngine.load("http://localhost:8881/sample.html");
    }

    public void sendParameter(TextField text) throws IOException {
        textField = text;
        JavaConnector a = new JavaConnector();
        openAddrDialog();

    }
}
