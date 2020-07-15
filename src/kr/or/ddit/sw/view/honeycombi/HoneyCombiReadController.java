package kr.or.ddit.sw.view.honeycombi;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.honeycombi.IHoneyCombiService;
import kr.or.ddit.sw.vo.honeycombi.HoneyCombiVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class HoneyCombiReadController implements Initializable {


    public ImageView honey_read_imgView;
    public JFXTextField honey_read_title;
    public JFXTextArea honey_read_content;
    public JFXTextField honey_read_menu;
    public JFXButton honey_read_out;
    private IHoneyCombiService honey;
    private Registry reg;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            honey = (IHoneyCombiService) reg.lookup("honey");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        Image image = new Image("file:src/images/꿀조합조회.png");
        honey_read_imgView.setImage(image);
        String name = HoneyCombiController.name;
        HoneyCombiVO honeyCombiVO = null;
        try {
            honeyCombiVO = honey.selectHoneyBtn(name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        honey_read_title.setText(honeyCombiVO.getHONEY_NAME());
        honey_read_content.setText(honeyCombiVO.getHONEY_CONTENT());
        honey_read_menu.setText(honeyCombiVO.getHONEY_MENU());

        honey_read_out.setOnAction(event -> {
            Stage stage = (Stage) honey_read_out.getScene().getWindow();
            stage.close();
        });
    }




}
