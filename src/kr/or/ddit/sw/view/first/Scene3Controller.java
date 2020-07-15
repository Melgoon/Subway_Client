package kr.or.ddit.sw.view.first;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class Scene3Controller implements Initializable {
    public ImageView signUp_test;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("file:src/images/signUp_test.JPG");
        signUp_test.setImage(img);

    }
}
