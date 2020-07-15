package kr.or.ddit.sw.view.admintestview;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.chat.ChatClientImpl;
import kr.or.ddit.sw.service.chat.ChatController;
import kr.or.ddit.sw.service.chatpass.IOwnerChatPassService;
import kr.or.ddit.sw.service.login.LoginSession;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainAdminController1 implements Initializable {
    public ImageView logo;
    public ImageView jumoon;
    public ImageView jaego;
    public ImageView baljoo;
    public ImageView maechool;
    public ImageView tellSub;
    public ImageView today;
    public ImageView adminChat;
    Registry reg;
    IOwnerChatPassService regChat;
    int ownerCode;
    String ownerPassChk;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("file:src/images/subway_logo2.png");
        logo.setImage(img);

        Image img1 = new Image("file:src/images/주문조회.png");
        jumoon.setImage(img1);

        Image img2 = new Image("file:src/images/재고관리.png");
        jaego.setImage(img2);

        Image img3 = new Image("file:src/images/발주관리.png");
        baljoo.setImage(img3);

        Image img4 = new Image("file:src/images/매출관리.png");
        maechool.setImage(img4);

        Image img5 = new Image("file:src/images/TellSubway.png");
        tellSub.setImage(img5);

        Image img6 = new Image("file:src/images/오늘의.png");
        today.setImage(img6);

        Image Chat = new Image("file:src/images/OwnerChat.jpg");
        adminChat.setImage(Chat);

        try {
            reg= LocateRegistry.getRegistry("localhost",7774);
            regChat = (IOwnerChatPassService) reg.lookup("ownerPass");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

       ownerCode= LoginSession.ownerSession.getOwner_code();


        adminChat.setOnMouseClicked(e->{

            TextInputDialog dialog = new TextInputDialog("사업자 비밀코드");
            dialog.setTitle("입력창"); //창 제목
            dialog.setHeaderText(LoginSession.ownerSession.getOwner_jijum() +"님의 사업자 비밀코드를 입력해 주세요");

            //창을 보이고 입력한 값을 읽어오기
            Optional<String> result = dialog.showAndWait(); // Optional은 nullpoint오류를 잡아줌
            String strResult = null; //입력한 값이 저장될 변수

            // 입력한 값이 있는지 검사 (값 입력 후 'OK'버튼 눌렀는지 검사)
            if(result.isPresent()) {
                strResult= result.get().trim(); //값 읽어오기
            }
            System.out.println("읽어 온 값 : " + strResult);

            try {
              ownerPassChk =regChat.OwnerPassChk(ownerCode).trim();
                System.out.println("1"+ownerPassChk);
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
            System.out.println("2"+ownerPassChk);

            if (strResult.equals(ownerPassChk.trim())) {
                FXMLLoader loader = null;
                try {
                    loader = new FXMLLoader(getClass().getResource("/kr/or/ddit/sw/service/chat/Chat.fxml"));

                    AnchorPane root = (AnchorPane) loader.load();
                    ChatController controller = loader.getController();
                    Scene scene = new Scene(root);
                    Stage stage1 = new Stage();
                    stage1.setScene(scene);
                    ChatClientImpl chatClientImpl = null;
                    try {
                        chatClientImpl = new ChatClientImpl(LoginSession.ownerSession.
                                getOwner_jijum()+"점 점주", controller, stage1);
                        chatClientImpl.connect();
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    //stage1.initModality(Modality.APPLICATION_MODAL);
                    stage1.show();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }else{
                errMsg("오류","비밀코드 오류","비밀코드가 틀립니다.");
            }
        });
    }

    public static void errMsg(String title, String headerText, String msg) {
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle(title);
        errAlert.setHeaderText(headerText);
        errAlert.setContentText(msg);
        errAlert.showAndWait();
    }
}
