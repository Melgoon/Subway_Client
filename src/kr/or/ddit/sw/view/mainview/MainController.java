package kr.or.ddit.sw.view.mainview;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.view.chatbot.OpenDialog;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public ImageView advertisement;
//    public JFXButton chatbot;
//    public JFXButton naggul;
//    public JFXButton ssub;
    public ImageView chatbot2;
    public ImageView honey;
    public ImageView recommend;
    MainViewController main = new MainViewController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image ad1 = new Image("file:src/images/ad1.jpg");
        //Image ad1 = new Image("file:C:\\Users\\hb930\\Desktop\\remote\\봄.jpg");
        advertisement.setImage(ad1);

        Image img1 = new Image("file:src/images/챗봇btn.png");
        chatbot2.setImage(img1);

        Image img2 = new Image("file:src/images/honeybtn.png");
        honey.setImage(img2);

        Image img3 = new Image("file:src/images/recommendbtn.png");
        recommend.setImage(img3);

        EventHandler eventhandler = new EventHandler() {
            @Override
            public void handle(Event event) {
                FadeTransition fd = new FadeTransition(Duration.millis(2000), advertisement);
                fd.setFromValue(0);
                fd.setToValue(1);
                fd.setCycleCount(Timeline.INDEFINITE);
                fd.setAutoReverse(true);
                fd.play();
            }
        };
        Timeline timeline = new Timeline();


        timeline.setCycleCount(2);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2000), eventhandler));

        timeline.play();


        honey.setOnMouseClicked(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../honeycombi/honeyCombiView.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            LoginSession.mainViewController.bp.setCenter(root);
        });

        recommend.setOnMouseClicked(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../recommendedmenu/RecommendedMenuFxml.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            LoginSession.mainViewController.bp.setCenter(root);
        });

        chatbot2.setOnMouseClicked(event -> {
            OpenDialog openDialog = new OpenDialog();
            LoginSession.introString = openDialog.open();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../chatbot/chatbotUI.fxml"));
                LoginSession.mainViewController.bp.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}
