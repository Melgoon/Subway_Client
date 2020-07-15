package kr.or.ddit.sw.view.mainview;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.regAdvertise.IRegAdvertiseService;

import java.io.IOException;
import java.net.URL;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainViewController implements Initializable {
    public ImageView menu_logo;
    public ImageView menu_intro;
    public ImageView menu_order;
    public ImageView menu_find_jijun;
    public ImageView menu_event;
    public ImageView menu_notice;
    //public ImageView advertisement;
    public JFXHamburger hamburger;
    public JFXDrawer drawer;
    public BorderPane bp;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        drawer.setDisable(true);
        Image img = new Image("file:src/images/subway_logo2.png");
        menu_logo.setImage(img);

        Image menu1 = new Image("file:src/images/menu1.png");
        menu_intro.setImage(menu1);

        Image menu2 = new Image("file:src/images/menu2.png");
        menu_order.setImage(menu2);

        Image menu3 = new Image("file:src/images/menu3.png");
        menu_find_jijun.setImage(menu3);

        Image menu4 = new Image("file:src/images/menu4.png");
        menu_event.setImage(menu4);

        Image menu5 = new Image("file:src/images/menu5.png");
        menu_notice.setImage(menu5);

        try {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("../basic/BasicSlidePane.fxml"));
            drawer.setSidePane(anchorPane);
        } catch (IOException e) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        }

        HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburger);
        burgerTask2.setRate(-1);

        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            burgerTask2.setRate(burgerTask2.getRate() * -1);
            burgerTask2.play();

            if (drawer.isOpened()) {
                drawer.close();
                drawer.setDisable(true);
            } else {
                drawer.open();
                drawer.setDisable(false);
            }

        });

        Parent basic = null;
        try {
            basic = FXMLLoader.load(getClass().getResource("main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bp.setCenter(basic);

        menu_logo.setOnMouseClicked(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("main.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            bp.setCenter(root);

        });

        menu_find_jijun.setOnMouseClicked(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../findJijum/find.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            bp.setCenter(root);

        });


        menu_intro.setOnMouseClicked(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../menuintro/MenuIntroView.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            bp.setCenter(root);

        });

        menu_order.setOnMouseClicked(event -> {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../ordertable/OrderTableView.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            bp.setCenter(root);


        });

        menu_event.setOnMouseClicked(event -> {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../event/Event.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            bp.setCenter(root);


        });

        menu_notice.setOnMouseClicked(event -> {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../notice/Notice.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            bp.setCenter(root);

        });

        LoginSession.mainViewController = this;


    }

    public void setter(Parent root) {
        bp.setCenter(root);
    }

}
