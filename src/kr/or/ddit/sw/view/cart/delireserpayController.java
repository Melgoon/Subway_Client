package kr.or.ddit.sw.view.cart;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.sw.service.login.LoginSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class delireserpayController implements Initializable {


    public ImageView reser;
    public ImageView deli;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image img = new Image("file:src/images/예약.png");
        reser.setImage(img);

        Image img1 = new Image("file:src/images/배달.png");
        deli.setImage(img1);
        reser.setOnMouseClicked(event -> {


            try {
                Parent parent = FXMLLoader.load(getClass().getResource("../reservation/Reservationpayview.fxml"));
                LoginSession.mainViewController.bp.setCenter(parent);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        deli.setOnMouseClicked(event -> {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("../delivery/dliverypayview.fxml"));
                LoginSession.mainViewController.bp.setCenter(parent);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
