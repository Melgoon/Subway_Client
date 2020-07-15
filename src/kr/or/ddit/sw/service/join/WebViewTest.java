package kr.or.ddit.sw.service.join;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class WebViewTest extends Application {
	/** for communication to the Javascript engine. */
    private JSObject javascriptConnector;

    /** for communication from the Javascript engine. */
    private JavaConnector javaConnector = new JavaConnector();
    
    Stage stage; // 주소검색을 위한 Stage()

    TextField tfAddr;

    TextField tfPost;

    @Override
    public void start(Stage primaryStage) throws Exception {

    	VBox vBox = new VBox(10);
    	vBox.setPadding(new Insets(10));

    	Button btnAddr = new Button("주소검색");

    	tfPost = new TextField(); // 우편번호

    	tfAddr = new TextField(); // 주소

    	vBox.getChildren().addAll(btnAddr, tfPost, tfAddr);

    	btnAddr.setOnAction(e->{
    		try {
				openAddrDialog();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	});



        Scene scene = new Scene(vBox, 500, 110);
        primaryStage.setTitle("WebView를 이용한 주소검색 기능 사용 예제");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public class JavaConnector {
        /**
         * called when the JS side wants a String to be converted.
         *
         * @param value
         *         the String to convert
         */
        public void toLowerCase(String value) {
            if (null != value) {
                javascriptConnector.call("showResult", value.toLowerCase());
            }
        }
        public void sendAddr(String addr, String extraAddr, String zonecode) {
        	System.out.println("자바스크립트에서 보내온 메시지 출력 : " + addr);
        	tfAddr.setText(addr);
        	tfPost.setText(zonecode);
        	stage.close(); // 창닫기
        	
        	
        }
    }
    
    private void openAddrDialog() throws IOException {
    	
    	URL url = new File("src/webview/sample.html").toURI().toURL();

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
                webEngine.executeScript("sample3_execDaumPostcode()");
                
            }
        });

        Scene scene = new Scene(webView, 520, 470);

        stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        // now load the page
       // webEngine.load("http://localhost:8088/SimpleWeb/sample.html");
        webEngine.load(url.toString());
    }
    
    public static void main(String[] args) {
		launch(args);
	}
}
