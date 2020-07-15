package kr.or.ddit.sw.view.basic;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.alarm.IAlarmService;
import kr.or.ddit.sw.service.login.LoginSession;

import java.io.IOException;
import java.net.URL;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class AdminSlidePaneController implements Initializable {
    public Label side_id;
    public ImageView side_mem_modify;
    public ImageView side_logout;

    Registry reg;
    IAlarmService regAlarm;
    //INoticeService regNotice;
    //String userId;
    int readTotal;
    public static BasicSlidePaneController con1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Image logout = new Image("file:src/images/exit.png");


        side_logout.setImage(logout);
        System.out.println(LoginSession.ownerSession.getOwner_jijum()+"입니당당당");
        side_id.setText(LoginSession.ownerSession.getOwner_jijum() + "점주님 환영합니다.");

        side_logout.setOnMouseClicked(event -> {
            LoginSession.memberSession = null;
            Stage stage = (Stage) side_logout.getScene().getWindow();
            stage.close();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../first/scene1.fxml"));
                Scene scene = new Scene(root);
                Stage stage1 = new Stage();
                stage1.setScene(scene);
                stage1.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
}

