package kr.or.ddit.sw.view.menuintro;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.or.ddit.sw.vo.prod.ProdVO;

import java.net.URL;
import java.security.Provider;
import java.util.ResourceBundle;

public class MenuIntroDetailViewController implements Initializable {

    public Label menu;
    public Label source;
    public Label price;
    public ImageView imageview;
    public TextArea menudetail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("file:src/images/menu_detail.png");
        imageview.setImage(image);
        ProdVO prodVO = MenuIntroViewController.tempvo;
        menu.setText(prodVO.getProd_name());
        menudetail.setText(prodVO.getProd_detail());
        source.setText(prodVO.getProd_source());
        price.setText(String.valueOf(prodVO.getProd_price()));
    }
}
