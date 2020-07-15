package kr.or.ddit.sw.view.hambugermenu;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HamburgerMenuController implements Initializable {

    public ImageView main_test;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawer.setDisable(true);
        Image img = new Image("file:src/images/main_test.jpg");
        main_test.setImage(img);

        try {
            VBox vbox = FXMLLoader.load(getClass().getResource("SlidePane.fxml"));
            drawer.setSidePane(vbox);
        } catch (IOException e) {
            Logger.getLogger(HamburgerMenuController.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        }

        HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburger);
        burgerTask2.setRate(-1);

        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)-> {
            burgerTask2.setRate(burgerTask2.getRate() * -1);
            burgerTask2.play();

            if(drawer.isOpened()) {
                drawer.close();
                drawer.setDisable(true);
            }
            else {
                drawer.open();
                drawer.setDisable(false);
            }
        });

    }
}
