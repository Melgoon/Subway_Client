package kr.or.ddit.sw.view.mainserver;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.login.LoginSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainServerController implements Initializable {

    public javafx.scene.image.ImageView server_logo;
    public javafx.scene.image.ImageView server_owner_mng;
    public javafx.scene.image.ImageView server_coupon_send;
    public BorderPane bp;
    public ImageView exit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image img1 = new Image("file:src/images/subway_owner.png");
        server_logo.setImage(img1);

        Image img2 = new Image("file:src/images/사업자관리.png");
        server_owner_mng.setImage(img2);

        Image img3 = new Image("file:src/images/쿠폰발송.png");
        server_coupon_send.setImage(img3);

        Image img4 = new Image("file:src/images/logout.png");
        exit.setImage(img4);

        server_owner_mng.setOnMouseClicked(e->{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../managementOwner/OwnerView.fxml"));

            try {
                Pane root = loader.load();
               bp.setCenter(root);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });

        server_coupon_send.setOnMouseClicked(e->{


            FXMLLoader loader = new FXMLLoader(getClass().getResource("../stamp/birthSend.fxml"));

            try {
                Pane root = loader.load();
                bp.setCenter(root);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });


        exit.setOnMouseClicked(e->{
           /* FXMLLoader loader = new FXMLLoader(getClass().getResource("../login/Login.fxml"));

            try {
                Pane root = loader.load();
                bp.setCenter(root);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }*/



            try {
                Parent root = FXMLLoader.load(getClass().getResource("../login/Login.fxml"));
                Stage stage = (Stage) exit.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

    }
}
