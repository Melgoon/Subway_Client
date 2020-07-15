package kr.or.ddit.sw.view.mainview;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.login.LoginSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SlidePaneController implements Initializable {


    public ImageView side_mem_modify;
    public ImageView side_bill;
    public ImageView side_coupon;
    public ImageView side_tellsub;
    public ImageView side_logout;
    public Label side_id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image modify = new Image("file:src/images/modify.png");
        Image bill = new Image("file:src/images/bill.png");
        Image coupon = new Image("file:src/images/coupon.png");
        Image tellsub = new Image("file:src/images/tellsub.png");
        Image logout = new Image("file:src/images/logout.png");

        side_mem_modify.setImage(modify);
        side_logout.setImage(logout);
        side_bill.setImage(bill);
        side_coupon.setImage(coupon);
        side_tellsub.setImage(tellsub);
        side_id.setText(LoginSession.memberSession.getMEM_ID() + "님 환영합니다.");

        side_logout.setOnMouseClicked(event -> {
            LoginSession.memberSession = null;
            Stage stage = (Stage) side_tellsub.getScene().getWindow();
            stage.close();
        });

        side_coupon.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../stamp/Stamp.fxml"));
                LoginSession.mainViewController.bp.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }
}
