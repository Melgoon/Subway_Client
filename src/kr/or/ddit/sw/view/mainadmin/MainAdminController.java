package kr.or.ddit.sw.view.mainadmin;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.chat.ChatClientImpl;
import kr.or.ddit.sw.service.chat.ChatController;
import kr.or.ddit.sw.service.chatpass.IOwnerChatPassService;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.sales.ISalesService;
import kr.or.ddit.sw.view.mainview.MainViewController;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainAdminController implements Initializable {
    public ImageView logo;
    public ImageView maechool;
    public ImageView tellSub;
    public ImageView today;
    public JFXDrawer drawer;
    public JFXHamburger hamburger;
    public ImageView ingredient;
    public ImageView notice;
    public ImageView menu;

    public ImageView adminChat;
    public BorderPane bp;
    public Label todayUser;
    public Label todayPr;
    public ImageView order;
    private Registry reg;
    private IOwnerChatPassService regChat;
    private ISalesService regSales;
    private int ownerCode;
    private String ownerPassChk;
    int totalUser;
    String totalPr;
    String totalPr2;
    int b;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("file:src/images/subway_logo2.png");
        logo.setImage(img);

        Image img1 = new Image("file:src/images/주문조회.png");
        order.setImage(img1);

        Image img3 = new Image("file:src/images/매출관리.png");
        maechool.setImage(img3);

        Image img4 = new Image("file:src/images/공지사항_admin.png");
        notice.setImage(img4);

        Image img5 = new Image("file:src/images/TellSubway.png");
        tellSub.setImage(img5);

        Image img6 = new Image("file:src/images/오늘의.png");
        today.setImage(img6);

        Image img7 = new Image("file:src/images/식자재관리.png");
        ingredient.setImage(img7);

        Image img8 = new Image("file:src/images/메뉴관리btn.png");
        menu.setImage(img8);

        Image Chat = new Image("file:src/images/OwnerChat.png");
        adminChat.setImage(Chat);
        try {
            reg= LocateRegistry.getRegistry("localhost",7774);
            regChat = (IOwnerChatPassService) reg.lookup("ownerPass");
            regSales= (ISalesService) reg.lookup("sales");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        ownerCode= LoginSession.ownerSession.getOwner_code();

        try {
            AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("../basic/AdminSlidePane.fxml"));
            drawer.setSidePane(anchorPane);
        } catch (IOException e) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, e);
            //e.printStackTrace();
        }

        HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburger);
        burgerTask2.setRate(-1);

        totalPr=null;
        try {
            //하루 고객 총 수
           totalUser = regSales.deliveryChatNo(LoginSession.ownerSession.getOwner_jijum(),4);
           totalUser+= regSales.reservationChatNo(LoginSession.ownerSession.getOwner_jijum(),4);
            //하루 매출 총 액
            totalPr=regSales.deliveryChatPr(LoginSession.ownerSession.getOwner_jijum(),4);
            if (totalPr==null){
                totalPr="0";
            }
            totalPr2 =regSales.reservationChatPr(LoginSession.ownerSession.getOwner_jijum(),4);
            if (totalPr2==null){
                totalPr2="0";
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        b = Integer.parseInt(totalPr);
        b+= Integer.parseInt(totalPr2);

        todayUser.setText(String.valueOf(totalUser));
        todayPr.setText(String.valueOf(b));
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            burgerTask2.setRate(burgerTask2.getRate() * -1);
            burgerTask2.play();

            if (drawer.isOpened()) {
                drawer.close();
                drawer.setDisable(true);
            } else {
                drawer.open();
                drawer.setDisable(false);
            }

        });

        order.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../orderhis/adminhis.fxml"));
                bp.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        logo.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../mainadmin/MainAdmin.fxml"));
                Stage stage = (Stage) logo.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



/*        owner.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../managementOwner/OwnerView.fxml"));
                bp.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });*/

        ingredient.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../registFoodMtr/ViewFoodMtr.fxml"));
                bp.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        menu.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../registProd/ViewProd.fxml"));
                bp.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        tellSub.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../tellsub/TellsubwayManagementMain.fxml"));
                bp.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        notice.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../notice/Notice.fxml"));
                bp.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        maechool.setOnMouseClicked(e->{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../sales/chartView.fxml"));
                bp.setCenter(root);
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        });


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
        LoginSession.mainAdminController = this;
    }

    public static void errMsg(String title, String headerText, String msg) {
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle(title);
        errAlert.setHeaderText(headerText);
        errAlert.setContentText(msg);
        errAlert.showAndWait();
    }

}
