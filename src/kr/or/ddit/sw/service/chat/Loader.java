package kr.or.ddit.sw.service.chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.login.LoginSession;


public class Loader extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
			AnchorPane root = loader.load();
			ChatController controller = loader.getController();
			ChatClientImpl chatClientImpl = new ChatClientImpl(LoginSession.ownerSession.
					getOwner_jijum()+"점 점주", controller, primaryStage);
			chatClientImpl.connect(); // RMI 서버에 접속하기
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
