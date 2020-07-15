package kr.or.ddit.sw.view.reservation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.delivery.IDeliveryService;
import kr.or.ddit.sw.service.reservation.IReservationService;
import kr.or.ddit.sw.vo.ordertable.ReservationVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class ReservationManagementSelectController implements Initializable
{
    public JFXButton ok;
    public JFXButton reset;
    public JFXTextField menu;
    public JFXTextField price;
    public JFXTimePicker time;
    public JFXDatePicker date;

    public JFXTextField index;

    private Registry reg;
    private IReservationService irs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            irs = (IReservationService) reg.lookup("reservation");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        ok.setOnAction(event -> {
            ReservationVO rv = new ReservationVO();
            rv.setReser_status("접수됨");
            rv.setReser_no(index.getText());
            Object obj = 1;

            try{
                obj = irs.updateReser(rv);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if (obj == null) {
                errMsg("에러","비정상적인 접근입니다.");
            } else {
                infoMsg("작업결과","정상적으로 입력되었습니다.");
            }

            Stage stage = (Stage) ok.getScene().getWindow();
            stage.close();
        });

        reset.setOnAction(event -> {
            ReservationVO rv = new ReservationVO();
            rv.setReser_status("접수 거부됨");
            rv.setReser_no(index.getText());

            Object obj = 1;

            try{
                obj = irs.updateReser(rv);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if (obj == null) {
                errMsg("에러","비정상적인 접근입니다.");
            } else {
                infoMsg("작업결과","정상적으로 입력되었습니다.");
            }

            Stage stage = (Stage) reset.getScene().getWindow();
            stage.close();
        });
    }

    public void initData(ReservationVO rv) {
            menu.setDisable(true);
            price.setDisable(true);
            time.setDisable(true);
            date.setDisable(true);
            index.setVisible(false);

            menu.setText(rv.getReser_name());
            price.setText(rv.getReser_price());
            time.setPromptText(rv.getReser_time());
            date.setPromptText(rv.getReser_date());
            index.setText(rv.getReser_no());
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
