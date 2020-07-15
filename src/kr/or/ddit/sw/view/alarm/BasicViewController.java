package kr.or.ddit.sw.view.alarm;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BasicViewController implements Initializable {
    public ImageView subway_logo2;
    public ImageView menu_intro;
    public ImageView menu_order;
    public ImageView menu_find_jijun;
    public ImageView menu_event;
    public ImageView menu_notice;

    public JFXHamburger hamburger;
    public JFXDrawer drawer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image img = new Image("file:src/images/subway_logo2.png");
        subway_logo2.setImage(img);

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
            Logger.getLogger(BasicViewController.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        }

        HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburger);
        burgerTask2.setRate(-1);

        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            burgerTask2.setRate(burgerTask2.getRate() * -1);
            burgerTask2.play();

            if (drawer.isOpened())
                drawer.close();
            else
                drawer.open();

        });

    }

}
