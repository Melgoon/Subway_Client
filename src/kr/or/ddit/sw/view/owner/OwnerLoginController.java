package kr.or.ddit.sw.view.owner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.owner.IOwnerService;
import kr.or.ddit.sw.vo.owner.OwnerVO;
import kr.or.ddit.sw.vo.owner.TempOwnerVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class OwnerLoginController implements Initializable {

    public JFXTextField jijumcode;
    public JFXTextField pw;
    public JFXButton confirm;
    public JFXButton cancel;
    public ImageView image;
    private Registry registry;
    private IOwnerService iOwnerService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("file:src/images/owner_login_view.png");
        image.setImage(img);
        try {
            registry = LocateRegistry.getRegistry("localhost", 7774);
            iOwnerService = (IOwnerService) registry.lookup("ownerService");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        confirm.setOnAction(event -> {
            //Server화면으로 넘어가는 부분
            if(jijumcode.getText().equals("9999")&& pw.getText().equals("9999")){
                /////////////////////
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("로그인성공");
                alert.setHeaderText("로그인에 성공하였습니다.");
                alert.setContentText("관리자님 환영합니다.");
                alert.showAndWait();
                Stage stage9 = (Stage) confirm.getScene().getWindow();
                stage9.close();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("../mainserver/MainServer.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root);
                Stage stage99 = new Stage();
                stage99.setScene(scene);
                stage99.showAndWait();
                Stage temp = (Stage) confirm.getScene().getWindow();
                temp.close();
                return;
                //////////////////////////////

            }
            OwnerVO vo = null;
            TempOwnerVO tempOwnerVO = new TempOwnerVO();
            tempOwnerVO.setJijumCode(Integer.parseInt(jijumcode.getText()));
            tempOwnerVO.setOwnerPW(pw.getText());
            try {
                vo = iOwnerService.owerlogin(tempOwnerVO);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            if (vo != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("로그인성공");
                alert.setContentText("로그인에 성공하였습니다!!");
                alert.showAndWait();
                LoginSession.ownerSession = vo;
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../mainadmin/MainAdmin.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) confirm.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("오류가 발생하였습니다.");
                alert.setContentText("코드 오류이거나 승인을 받아주세요");
                alert.showAndWait();
            }
        });

    }
}
