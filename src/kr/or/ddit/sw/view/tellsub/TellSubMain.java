package kr.or.ddit.sw.view.tellsub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.login.LoginSession;

public class TellSubMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("TellSubwayManagementMain.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TellSubwayMain.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("일반회원 텔서브웨이");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
