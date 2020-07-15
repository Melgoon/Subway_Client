package kr.or.ddit.sw.view.owner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.owner.IOwnerService;
import kr.or.ddit.sw.vo.owner.OwnerVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class OwnerInsert implements Initializable {
    public JFXTextField jijumName;
    public JFXTextField adr;
    public JFXTextField hp;
    public JFXPasswordField pass1;
    public JFXPasswordField pass2;
    public JFXButton confirm;
    public JFXButton cancel;
    public ImageView imgView;
    public JFXTextField email;

    private Registry registry;
    private IOwnerService iOwnerService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            registry = LocateRegistry.getRegistry("localhost",7774);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Image image = new Image("file:src/images/ownerInsert.png");
        imgView.setImage(image);

        try {
            iOwnerService = (IOwnerService) registry.lookup("ownerService");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

        confirm.setOnAction(event -> {

            OwnerVO vo = new OwnerVO();
            vo.setOwner_jijum(jijumName.getText());
            vo.setOwner_pw(pass1.getText());
            vo.setOwner_email(email.getText());
            vo.setOwner_tel(hp.getText());
            vo.setOwner_addr(adr.getText());

            Object object = 1;
            try {
                object = iOwnerService.insertowner(vo);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if(object == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("입력완료");
                alert.setContentText("정상적으로 입력되었습니다.");
                alert.showAndWait();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("owner.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) confirm.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("입력완료");
                alert.setContentText("입력에 실패했습니다.");
            }
        });
    }
}
