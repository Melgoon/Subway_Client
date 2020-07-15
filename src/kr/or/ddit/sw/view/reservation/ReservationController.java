package kr.or.ddit.sw.view.reservation;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import kr.or.ddit.sw.service.delivery.IDeliveryService;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.managementOwner.IManagementOwnerService;
import kr.or.ddit.sw.service.ordertable.IOrderTableService;
import kr.or.ddit.sw.service.pay.IPayService;
import kr.or.ddit.sw.service.reservation.IReservationService;
import kr.or.ddit.sw.vo.ordertable.DeliveryVO;
import kr.or.ddit.sw.vo.ordertable.OrderVO;
import kr.or.ddit.sw.vo.ordertable.ReservationVO;
import kr.or.ddit.sw.vo.owner.OwnerVO;
import kr.or.ddit.sw.vo.pay.PayVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {
    public JFXButton kakao;
    public ImageView imgur;

    public JFXComboBox coupon;
    public JFXTimePicker time;
    public JFXDatePicker date;
    public JFXComboBox jijum;
    public JFXTextField menu;
    public JFXTextField price;
    public TableColumn<OrderVO, String> menutc;
    public TableColumn<OrderVO, Integer> pricetc;
    public TableColumn<OrderVO, String> nametc;
    public TableView<OrderVO> order;
    public ImageView kakaopay;

    private Registry reg;

    private IManagementOwnerService imos;
    private ArrayList<OwnerVO> list = new ArrayList<>();
    private ObservableList<OwnerVO> allTableData;

    private IOrderTableService iots;
    private ArrayList<OrderVO> ov = new ArrayList<>();
    private ObservableList<OrderVO> allDate;
    private IReservationService irs;
    private IPayService ips;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("file:src/images/ReservationPay.png");
        imgur.setImage(img);

        Image img1 = new Image("file:src/images/카카오페이.png");
        kakaopay.setImage(img1);

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iots = (IOrderTableService) reg.lookup("orderTableService");
            imos = (IManagementOwnerService) reg.lookup("owner");
            irs = (IReservationService) reg.lookup("reservation");
            ips = (IPayService) reg.lookup("pay");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        try{
            list = (ArrayList<OwnerVO>) imos.selectOwner();
            ov = (ArrayList<OrderVO>) iots.orderselect();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        allTableData = FXCollections.observableArrayList();
        allTableData.addAll(list);

        allDate = FXCollections.observableArrayList();

        menutc.setCellValueFactory(new PropertyValueFactory<>("order_cont"));
        pricetc.setCellValueFactory(new PropertyValueFactory<>("order_price"));
        nametc.setCellValueFactory(new PropertyValueFactory<>("mem_id"));


        allDate.setAll(ov);
        order.setItems(allDate);

        addButtonToTable();

        jijum.setCellFactory((new Callback<ListView<OwnerVO>, ListCell<OwnerVO>>() {
            @Override
            public ListCell<OwnerVO> call(ListView param) {
                return new ListCell<OwnerVO>() {
                    protected void updateItem(OwnerVO item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getOwner_jijum());
                        }
                    }
                };
            }
        }));

        jijum.setButtonCell(new ListCell<OwnerVO>(){
            protected void updateItem(OwnerVO item, boolean empty){
                super.updateItem(item,empty);
                if(empty){
                    setText(null);
                }else{
                    setText(item.getOwner_jijum());
                }
            }
        });
        jijum.setItems(allTableData);
        kakao.setOnAction(event -> {
            System.out.println("delivery 삽입 테스트");
            if(menu.getText().isEmpty() || price.getText().isEmpty() || time.getControlCssMetaData().isEmpty() || date.getControlCssMetaData().isEmpty() ||
                    coupon.getControlCssMetaData().isEmpty() || jijum.getControlCssMetaData().isEmpty()){
                errMsg("에러","값을 입력하세요.");
                return;
            }else{

                ReservationVO rv = new ReservationVO();

                OwnerVO dd = (OwnerVO) jijum.getSelectionModel().getSelectedItem();

                PayVO pv = new PayVO();

                rv.setReser_name(menu.getText());
                rv.setReser_price(price.getText());
                rv.setReser_jijum(dd.getOwner_jijum());
                pv.setPay_way("kakao");
                rv.setReser_time(String.valueOf(time.getValue()));
                rv.setReser_date(String.valueOf(date.getValue()));
                LoginSession.price= rv.getReser_price();
                Object obj = 1;

                try{
                    obj = irs.insert(rv);
                    obj = ips.Reserinsert(pv);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                if (obj == null) {
                    errMsg("에러","비정상적인 접근입니다.");
                } else {
                    Stage dialog = new Stage(StageStyle.UTILITY);
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.setTitle("1");
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("../pay/kakao.fxml"));
                        Scene scene = new Scene(root);
                        dialog.setScene(scene);
                        dialog.setResizable(false);
                        dialog.showAndWait();
                        Parent root1 = FXMLLoader.load(getClass().getResource("../pay/finish.fxml"));
                        LoginSession.mainViewController.bp.setCenter(root1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        coupon.getItems().addAll("쿠폰 사용","쿠폰 미사용");


    }

    private void addButtonToTable() {

        TableColumn<OrderVO, String> colBtn = new TableColumn("장바구니");

        Callback<TableColumn<OrderVO, String>, TableCell<OrderVO, String>> cellFactory = new Callback<TableColumn<OrderVO, String>, TableCell<OrderVO, String>>() {
            @Override
            public TableCell<OrderVO, String> call(final TableColumn<OrderVO, String> param) {
                final TableCell<OrderVO, String> cell = new TableCell<OrderVO, String>() {

                    private final JFXButton btn = new JFXButton("선택");

                    {
                        btn.setOnAction(event -> {
                            OrderVO odv = order.getSelectionModel().getSelectedItem();


                            menu.setText(odv.getOrder_cont());
                            price.setText(String.valueOf(odv.getOrder_price()));
                        });
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        order.getColumns().add(colBtn);
    }

    private void infoMsg(String headerText, String msg){
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("작업 결과");
        infoAlert.setHeaderText(headerText);
        infoAlert.setContentText(msg);
        infoAlert.showAndWait();
    }

    private void errMsg(String headerText, String msg){
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle("오류");
        errAlert.setHeaderText(headerText);
        errAlert.setContentText(msg);
        errAlert.showAndWait();
    }
}
