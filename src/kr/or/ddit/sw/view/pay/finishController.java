package kr.or.ddit.sw.view.pay;

import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class finishController implements Initializable {
    public ImageView img;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("file:src/images/finish.png");
        img.setImage(image);
    }
}
