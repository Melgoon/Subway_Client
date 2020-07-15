package kr.or.ddit.sw.view.recommendedmenu;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class RecommendedMenuController implements Initializable {


    public ImageView background;
    public JFXCheckBox a;
    public JFXCheckBox c;
    public JFXCheckBox b;
    public JFXCheckBox d;
    public JFXCheckBox e;
    public JFXCheckBox f;
    public JFXCheckBox g;
    public JFXCheckBox h;
    public JFXCheckBox i;
    public ImageView imageview;
    public JFXButton submit;
    public JFXCheckBox j;
    String str;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXCheckBox[] list = {a, b, c, d, e, f, g, h, i,j};
        Image image = new Image("file:src/images/recommended.png");
        background.setImage(image);
        submit.setOnAction(event -> {
            str = "";
            for (JFXCheckBox box : list) {
                if (box.isSelected()) {
                    str += box.getText() + " ";
                }
            }
            double temp = Double.parseDouble(GetSentiment.returnMenu(str));
            temp = Math.floor(temp * 100) / 100;
            String menu = "";
            if (0 < temp && temp < 0.1) {
                menu = "bmt";
            } else if (0.1 <= temp && temp < 0.2) {
                menu = "spicy";
            } else if (0.2 <= temp && temp < 0.3) {
                menu = "melt";
            } else if (0.3 <= temp && temp < 0.4) {
                menu = "ham";
            } else if (0.4 <= temp && temp < 0.5) {
                menu = "turkeybacon";
            } else if (0.5 <= temp && temp < 0.6) {
                menu = "meatball";
            } else if (0.6 <= temp && temp < 0.7) {
                menu = "club";
            } else if (0.7 <= temp && temp < 0.8) {
                menu = "tuna";
            }else if(0.8 <= temp && temp <0.9 ){
                menu = "eggmayo";
            }else if(0.9 <= temp && temp <1.0 ){
                menu = "vegi";
            }

            Image image1 = new Image("file:src/images/menu/"+menu+".png");
            imageview.setImage(image1);
        });


    }
}