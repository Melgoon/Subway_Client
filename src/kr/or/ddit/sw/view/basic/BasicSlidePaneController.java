package kr.or.ddit.sw.view.basic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.alarm.IAlarmService;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.notice.INoticeService;
import kr.or.ddit.sw.service.tellsubmem.ITellsubMemService;
import kr.or.ddit.sw.vo.notice.NoticeMemChkVO;
import kr.or.ddit.sw.vo.tellsub.TellSubVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class BasicSlidePaneController implements Initializable {


    public ImageView side_mem_modify;
    public ImageView side_bill;
    public ImageView side_coupon;
    public ImageView side_tellsub;
    public ImageView side_logout;
    public Label side_id;

    //알람 이미지
    public ImageView alarmView;
    //알람 이미지를 컨트롤할 카운트


    public static int countAlarm;
    public static int countTotal;
    public static int countTellSub;
    public static int countBir;

    Registry reg;
    IAlarmService regAlarm;
    ITellsubMemService regMem;
    //INoticeService regNotice;
    //String userId;
    int readTotal;
    public static BasicSlidePaneController con1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Image modify = new Image("file:src/images/user.png");
        Image bill = new Image("file:src/images/bills.png");
        Image coupon = new Image("file:src/images/coupon2.png");
        Image tellsub = new Image("file:src/images/order.png");
        Image logout = new Image("file:src/images/exit.png");

        side_mem_modify.setImage(modify);
        side_logout.setImage(logout);
        side_bill.setImage(bill);
        side_coupon.setImage(coupon);
        side_tellsub.setImage(tellsub);
        side_id.setText(LoginSession.memberSession.getMEM_ID() + "님 환영합니다.");

        side_logout.setOnMouseClicked(event -> {
            LoginSession.memberSession = null;
            Stage stage = (Stage) side_tellsub.getScene().getWindow();
            stage.close();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../first/scene1.fxml"));
                Scene scene = new Scene(root);
                Stage stage1 = new Stage();
                stage1.setScene(scene);
                stage1.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        side_coupon.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../stamp/Stamp.fxml"));
                LoginSession.mainViewController.bp.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        side_bill.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../orderhis/orderhis.fxml"));
                LoginSession.mainViewController.bp.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        side_tellsub.setOnMouseClicked(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../tellsub/TellSubwayMain.fxml"));
                LoginSession.mainViewController.bp.setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        //회원 정보 수정창으로 보내버리자
        side_mem_modify.setOnMouseClicked(e -> {
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../memup/MemberUpdate.fxml"));
                LoginSession.mainViewController.bp.setCenter(root);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });


        //생일인지 아닌지 체크하는것! 생일이면 Bir+1
        DateFormat dateFormat = new SimpleDateFormat("MM-dd");
        Calendar cal = Calendar.getInstance();

        System.out.println(dateFormat.format(cal.getTime()));
        System.out.println(LoginSession.memberSession.getMEM_BIRTH());

        if (LoginSession.memberSession.getMEM_BIRTH().equals(dateFormat.format(cal.getTime()))) {

            countBir += 1;
        }

        System.out.println("countBir"+countBir);

        try {
            reg = LocateRegistry.getRegistry("localhost",7774);
            regAlarm= (IAlarmService) reg.lookup("alarm");
            regMem = (ITellsubMemService) reg.lookup("tellsubmem");
            //여기서도 나타내줘야한다 답글이 왔는지 없는지

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        //Image alarmImg = new Image("file:src/images/종소리.JPG");
//     String userId = LoginSession.session.getMEM_ID();
//
//            try {
//                countAlarm=  regAlarm.noticeNumber(userId);
//               // System.out.println(countAlarm);
//            } catch (RemoteException e1) {
//                e1.printStackTrace();
//            }
//
//            Image image = new Image("file:src/images/종소리" + countAlarm + ".png");
//
//            alarmView.setImage(image);
            changebell();

            countAlarmView();

//            alarmView.setOnMouseClicked(e->{
//                Stage stage = new Stage();
//                try {
//                    Parent root = FXMLLoader.load(getClass().getResource("../alarm/readBoard.fxml"));
//                    Scene scene = new Scene(root);
//                    stage.setScene(scene);
//                    stage.show();
//                } catch (IOException e2) {
//                    e2.printStackTrace();
//                }
//            });

        alarmView.setOnMouseClicked(e->{
            Stage stage = new Stage();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../alarm/AlarmView.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });




        con1= this;

    }

    public void changebell(){
        //System.out.println("제발 되자");
        String userId = LoginSession.memberSession.getMEM_ID();
        NoticeMemChkVO chkvo = new NoticeMemChkVO();
        chkvo.setMEM_ID(userId);
        //게시판 번호만 뽑아 넣은 list
        List<Integer> list = null;

        //chk_mem_notice 의 번호만 뽑아넣은 chkListNO
        List<Integer> chkListNO =null;

        try {
            list = regAlarm.noticeNo();
            chkListNO= regAlarm.noticeChkNo(userId);

//            for (int i = 0; i <list.size() ; i++) {
//                System.out.print(list.get(i)+",");
//            }
//            System.out.println();
//
//            for (int i = 0; i <chkListNO.size() ; i++) {
//                System.out.print(chkListNO.get(i)+",");
//            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        try {
            //신규유저인지 아닌지 체크해주는 것
            if (regAlarm.newUserChk(userId)==null){
                //insert로 게시판 번호를 뽑아서 게시판 체크 테이블에 insert 때려준다.
                for (int i = 0; i < list.size(); i++) {
                    chkvo.setNOTICE_NO(list.get(i));
                    regAlarm.insertChk(chkvo);
                }
            }
            // 기존유저가 재접속 했는데 게시판이 추가되었을 경우
            if (list.size() != chkListNO.size()) {
                for (int i = 0; i < list.size(); i++) {

                    if (chkListNO.size() < i + 1) {
                        chkvo.setNOTICE_NO(list.get(i));
                        regAlarm.insertChk(chkvo);
                    }

                }
            }



        //    countAlarm=  regAlarm.noticeNumber(userId);
            // System.out.println(countAlarm);
        } catch (RemoteException e1) {
            e1.printStackTrace();
        }

        //tellsub 리스트


    }


    public void contTellSubAlarm(){

    }




    public void countAlarmView(){
        String userId =LoginSession.memberSession.getMEM_ID();
        try {
            countAlarm=  regAlarm.noticeNumber(userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        List<TellSubVO> tellsublist = null;
        try {
            tellsublist =  regMem.selectUnionTell(userId);
            countTellSub = tellsublist.size();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println(countAlarm);
        System.out.println(countBir);
        System.out.println("답변"+countTellSub);
        countTotal=countAlarm+countBir+countTellSub;
        System.out.println(countTotal);



        // countBir는 생일일때 +1해줬으니 알람+생일+탈서브 합쳐서 total로 받아야한다.;
        if (countTotal>11){
            //alarmView.setImage(null);
            Image MaxImage = new Image("file:src/images/종소리MAX.png");
            alarmView.setImage(MaxImage);
        }
        if(countTotal<11) {
            Image image = new Image("file:src/images/종소리" + (countBir + countAlarm+countTellSub) + ".png");
            alarmView.setImage(image);
        }
    }
}

