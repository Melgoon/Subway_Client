package kr.or.ddit.sw.view.delivery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class deliveryMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dliverypayview.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);

        primaryStage.setTitle("배달 결제 화면");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
