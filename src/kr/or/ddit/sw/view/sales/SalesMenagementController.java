package kr.or.ddit.sw.view.sales;

import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.sales.ISalesService;
import kr.or.ddit.sw.vo.owner.OwnerVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class SalesMenagementController implements Initializable {

    public Label userPrLb;
    public Label userNoLb;
    public AreaChart noChart;
    public CategoryAxis userX;
    public NumberAxis userY;
    public LineChart prChart;
    public CategoryAxis priceX;
    public NumberAxis priceY;
    public ImageView mainView;
    public ImageView noView;
    public ImageView prView;

    private Registry reg;
    private ISalesService regSales;
    int totalUser, totalPrice,pr1,pr2,prTo;
    String test;
    String test2;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("file:src/images/jijumMainImg.png");
        Image image2 = new Image("file:src/images/JijumPriceImg.png");
        Image image3 = new Image("file:src/images/jiJumUserImg.png");

        mainView.setImage(image);
        noView.setImage(image2);
        prView.setImage(image3);
//        LoginSession.ownerSession= new OwnerVO(6,"6","brinst83@naver.com",
//                "서울특별시 서대문구 신촌로 121 아남오피스텔","신촌","02-3147-1248","TRUE");

        //x 는 1주일 요일, y 는 유저수와 가격
        try {
            reg = LocateRegistry.getRegistry("localhost",7774);
            regSales = (ISalesService) reg.lookup("sales");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        try {
            //1주일 별 고객 총 수
         totalUser =regSales.deliveryNo(LoginSession.ownerSession.getOwner_jijum());
         totalUser +=regSales.reservationNo(LoginSession.ownerSession.getOwner_jijum());
         totalPrice =regSales.deliveryPr(LoginSession.ownerSession.getOwner_jijum());
         totalPrice+=regSales.reservationPr(LoginSession.ownerSession.getOwner_jijum());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(totalUser+","+totalPrice);
        userNoLb.setText(totalUser+"명");
        userPrLb.setText(totalPrice+"원");

        noChart.setTitle("요일 별 고객수");
        userX.setLabel("일자");
        userY.setLabel("고객 수");
        XYChart.Series<String, Number> ser1 = new XYChart.Series<>();

        //String []a = {"금","목","수","화","월","일","토"};
        String []a = {"월","화","수","목","금","토","일"};

        try {
            for (int i=0; i<=6; i++) {

                ser1.getData().add(new XYChart.Data<>
                        (a[i],regSales.reservationChatNo(LoginSession.ownerSession.getOwner_jijum(), i)
                                +regSales.deliveryChatNo(LoginSession.ownerSession.getOwner_jijum(),i)));
                //ser1.getData().add(new XYChart.Data<>(i+"일",));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        noChart.getData().add(ser1);

        ser1.setName(LoginSession.ownerSession.getOwner_jijum()+"지점");

        prChart.setTitle("요일 별 매출액");
        priceX.setLabel("일자");
        priceY.setLabel("매출 액");

        XYChart.Series<String, Number> ser2 = new XYChart.Series<>();

        //String []b = {}
        pr1=0;
        pr2=0;
        prTo=0;
        test =null;
        test2= null;
        for (int i = 0; i <=6; i++) {
            try {
                test =regSales.reservationChatPr(LoginSession.ownerSession.getOwner_jijum(),i);
                if ( test==null){
                     test = "0";
                }

                test2 = regSales.deliveryChatPr(LoginSession.ownerSession.getOwner_jijum(),i);
                if (test2==null){
                    test2 ="0";
                }
                ser2.getData().add(new XYChart.Data<>(a[i],Integer.parseInt(test)+Integer.parseInt(test2)));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        prChart.getData().add(ser2);
        ser2.setName(LoginSession.ownerSession.getOwner_jijum()+"지점");
    }
}
