package kr.or.ddit.sw.view.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.login.ILoginEmailService;
import kr.or.ddit.sw.service.login.ILoginService;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.vo.member.MemberVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public PasswordField txtPassword;
    public Button btnSignUp;
    public Button btnSearchId;

    public ImageView logo;
    public ImageView btnKakaoLogin;
    public Button btnLogin;
    public JFXTextField login_id;

    public JFXButton login_signup;
    public JFXButton login_src;
    public JFXPasswordField login_pw;
    public ImageView ownerlogin;

    private Registry regi;
    private ILoginService login;

    private ILoginEmailService emailService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            regi = LocateRegistry.getRegistry("localhost", 7774);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Image img = new Image("file:src/images/subway_logo.png");
        logo.setImage(img);

        /*Image img2 = new Image("file:src/images/kakao_login_btn.png");
        btnKakaoLogin.setImage(img2);*/
        Image avocado = new Image("file:src/images/avocado.png");
        ownerlogin.setImage(avocado);

        ownerlogin.setOnMouseClicked(event -> {
            Stage stage = (Stage) ownerlogin.getScene().getWindow();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../owner/owner.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        login_signup.setOnAction(event -> {
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../join/Join.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btnLogin.setOnAction(event -> {
            MemberVO vo = null;
            try {
                login = (ILoginService) regi.lookup("login");
                vo = login.login(login_id.getText());
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
            System.out.println(vo.getMEM_PW());
            System.out.println(vo.getMEM_ID());
            if (vo != null) {
                if (login_pw.getText().equals(vo.getMEM_PW())) {
                    LoginSession.memberSession = vo;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("로그인성공");
                    alert.setHeaderText("로그인에 성공하였습니다.");
                    alert.setContentText("반갑습니다 서브웨이입니다.");
                    alert.showAndWait();
                    System.out.println(LoginSession.memberSession.getMEM_ID());
                    Stage stage1 = (Stage) btnLogin.getScene().getWindow();
                    stage1.close();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../mainview/mainView1.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.showAndWait();
                    Stage temp = (Stage) btnLogin.getScene().getWindow();
                    temp.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("비밀번호오류");
                    alert.setHeaderText("비밀번호가 틀렸습니다.");
                    alert.setContentText("다시시도해주세요");
                    alert.showAndWait();

                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("로그인오류");
                alert.setHeaderText("아이디가 존재하지않습니다.");
                alert.setContentText("회원가입 해주세요");
                alert.showAndWait();
            }
        });

        login_src.setOnAction(event -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("ID/PW 찾기");
            textInputDialog.setHeaderText("ID/PW를 찾을 수 있습니다.");
            textInputDialog.setContentText("회원가입시 입력하신 Email을 입력해주세요");
            Optional<String> result = textInputDialog.showAndWait();
            String strResult = null;

            if (result.isPresent()) {
                strResult = result.get();
                System.out.println(strResult);
                try {
                    emailService = (ILoginEmailService) regi.lookup("idpwfind");
                    emailService.emailCheck(strResult);
                } catch (RemoteException | NotBoundException e) {
                    e.printStackTrace();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("이메일 확인");
                alert.setHeaderText("이메일을 보냈습니다.");
                alert.setContentText("이메일에서 비밀번호와 아이디를 확인해주세요");
                alert.showAndWait();
            }
        });
    }


    public void btnKakaoLoginClicked(MouseEvent mouseEvent) {
    }

    public void btnLoginClicked(ActionEvent actionEvent) {
    }

    public void kakao_clicked(MouseEvent mouseEvent) {
    }

    public void btnSearchIdClicked(ActionEvent actionEvent) {
    }
}
