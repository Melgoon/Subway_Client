package kr.or.ddit.sw.view.tellsub;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class tellSubOwnerMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TellSubwayManagementMain.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);


        primaryStage.setTitle("업체회원 텔서브웨이");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
