package kr.or.ddit.sw.view.alarm;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.alarm.IAlarmService;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.tellsubmem.ITellsubMemService;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import static kr.or.ddit.sw.view.basic.BasicSlidePaneController.con1;
import static kr.or.ddit.sw.view.basic.BasicSlidePaneController.countBir;

public class MainAlarmContoller implements Initializable {
    public ImageView resetBtn;
    public AnchorPane mainAlarm;
    @FXML
    private ImageView AlarmTopMain;
    @FXML
    private ImageView AlarmNotice;
    @FXML
    private ImageView AlarmBir;
    @FXML
    private ImageView AlarmTellSub;
    private String user_id;
    private Registry reg;
    private IAlarmService regAlarm;
    private ITellsubMemService regMem;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image topMain = new Image("file:src/images/alarmMain.png");
        Image birMain = new Image("file:src/images/birMain.png");
        Image tellMain = new Image("file:src/images/TellsubMain.png");
        Image noticeMain = new Image("file:src/images/NoticeMain.png");
        Image reset = new Image("file:src/images/resetView.png");
        AlarmTopMain.setImage(topMain);
        AlarmNotice.setImage(noticeMain);
        //AlarmBir.setImage(birMain);
        AlarmTellSub.setImage(tellMain);
        user_id = LoginSession.memberSession.getMEM_ID();
        resetBtn.setImage(reset);

        try {
            reg= LocateRegistry.getRegistry("localhost",7774);
            regAlarm = (IAlarmService) reg.lookup("alarm");
            regMem = (ITellsubMemService) reg.lookup("tellsubmem");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }


        try {
            if (regAlarm.noticeNumber(user_id)<1){
                AlarmNotice.setImage(null);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        int cnt =0;
        try {
            cnt =regMem.selectTellSubNO(user_id);
            if (cnt<1){
                AlarmTellSub.setImage(null);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        DateFormat dateFormat = new SimpleDateFormat("MM-dd");
        Calendar cal = Calendar.getInstance();
        System.out.println("알람데이트를 string 으로 바꿈"+dateFormat.format(cal.getTime()));
        System.out.println(LoginSession.memberSession.getMEM_BIRTH());
//        Boolean www= false;
//        www = true;

        if (LoginSession.memberSession.getMEM_BIRTH().equals(dateFormat.format(cal.getTime()))){
            AlarmBir.setImage(birMain);

            //생을 쿠폰경로 이미지를 클릭했을때 스태틱 변수 - 처리
            AlarmBir.setOnMouseClicked(e->{
                //생을쿠폰 클릭햇을때 처리
                countBir-=1;
                con1.countAlarmView();
                Stage stage = new Stage();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../stamp/Stamp.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });

        }

        AlarmNotice.setOnMouseClicked(e->{
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("readBoard.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });




        //TeSub 나타내야함
        AlarmTellSub.setOnMouseClicked(e->{
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("TellsubSend.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        resetBtn.setOnMouseClicked(e->{
            try {
                if (regAlarm.noticeNumber(user_id)<1){
                    AlarmNotice.setImage(null);
                }
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }

            int cnt1 =0;
            try {
                cnt1 =regMem.selectTellSubNO(user_id);
                if (cnt1<1){
                    AlarmTellSub.setImage(null);
                }
            } catch (RemoteException e3) {
                e3.printStackTrace();
            }
            AnchorPane a = null;
            try {
                 a = FXMLLoader.load(getClass().getResource("AlarmView.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            mainAlarm.getChildren().addAll(a);
        });
    }


}
