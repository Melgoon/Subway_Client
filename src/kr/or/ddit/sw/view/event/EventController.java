package kr.or.ddit.sw.view.event;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.stamp.IStampService;
import kr.or.ddit.sw.vo.coupon.CouponHisVO;
import kr.or.ddit.sw.vo.coupon.CouponVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;
import java.util.Scanner;

public class EventController implements Initializable {

    public ImageView left;
    public ImageView right;
    public ImageView sub;
    public ImageView me;
    public ImageView title;
    public ImageView result;
    public JFXTextField input;
    public JFXButton btn_start;

    private Registry reg;
    private IStampService iStampService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iStampService = (IStampService) reg.lookup("stamp");
        }catch (RemoteException | NotBoundException e){
            e.printStackTrace();
        }

        Image img1 = new Image("file:src/images/가위바위보.png");
        title.setImage(img1);

        Image img2 = new Image("file:src/images/SUB.png");
        sub.setImage(img2);

        Image img3 = new Image("file:src/images/나.png");
        me.setImage(img3);

        Image com1 = new Image("file:src/images/좌_가위.png");
        Image com2 = new Image("file:src/images/좌_바위.png");
        Image com3 = new Image("file:src/images/좌_보.png");

        Image me1 = new Image("file:src/images/우_가위.png");
        Image me2 = new Image("file:src/images/우_바위.png");
        Image me3 = new Image("file:src/images/우_보.png");

        Image result1 = new Image("file:src/images/same.png");
        Image result2 = new Image("file:src/images/나win.png");
        Image result3 = new Image("file:src/images/섭win.png");

        btn_start.setOnAction(event -> {
            btn_start.setDisable(true);
            Scanner scanner = new Scanner(System.in);

            int comNum = (int) (Math.random() * 3) + 1;  // 1 ~ 3 사이의 난수 발생

            int myNum = 0;
            // int myNum = scanner.nextInt();  // int형 사용자값 입력 받기

            if (comNum == 1) {
                left.setImage(com1);
            } else if (comNum == 2) {
                left.setImage(com2);
            } else {
                left.setImage(com3);
            }

            // 사용자
            String na = input.getText();

            switch (na) {
                case "가위":
                    myNum = 1;
                    break;

                case "바위":
                    myNum = 2;
                    break;

                case "보":
                    myNum = 3;
                    break;

                default:
                    myNum = 3;
                    break;
            }

            // 나
            if (myNum == 1) {
                right.setImage(me1);
            } else if (myNum == 2) {
                right.setImage(me2);
            } else {
                right.setImage(me3);
            }


            // 결과
            if (comNum == myNum) {
                result.setImage(result1);
            } else if ((comNum == 1 && myNum == 2) || (comNum == 2 && myNum == 3) || (comNum == 3 && myNum == 1)) {
                result.setImage(result2);
                //쿠폰발송

                CouponVO cv = new CouponVO();
                CouponHisVO hv = new CouponHisVO();
                //todo Session_id얻어오기
                hv.setMem_id(LoginSession.memberSession.getMEM_ID());
                try {
                    iStampService.insertCoupon(cv);
                    iStampService.insertCouponHis(hv);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }


            } else {
                result.setImage(result3);
            }
        });




    }
}



