package kr.or.ddit.sw.view.delivery;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.delivery.IDeliveryService;
import kr.or.ddit.sw.vo.ordertable.DeliveryVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class DeliveryManagementSelectController implements Initializable {


    public JFXButton ok;
    public JFXButton reset;
    public JFXTextField menu;
    public JFXTextField adr;
    public JFXTextField price;
    public JFXComboBox time;
    public JFXTextField index;

    private Registry reg;
    private IDeliveryService ids;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            ids = (IDeliveryService) reg.lookup("delivery");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        ok.setOnAction(event -> {
            if(time.getControlCssMetaData().isEmpty()){
                errMsg("에러","값을 입력하세요.");
                return;
            }else{
                DeliveryVO dv = new DeliveryVO();

                dv.setDeli_status("접수됨");
                dv.setDeli_timerq(String.valueOf(time.getValue()));
                dv.setDeli_no(index.getText());

                Object obj = 1;

                try{
                    obj = ids.updatedel(dv);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                if (obj == null) {
                    errMsg("에러","비정상적인 접근입니다.");
                } else {
                    infoMsg("작업결과","정상적으로 입력되었습니다.");
                }
            }

            Stage stage = (Stage) ok.getScene().getWindow();
            stage.close();
        });

        reset.setOnAction(event -> {

            DeliveryVO dv = new DeliveryVO();

                dv.setDeli_status("접수 거부");
                dv.setDeli_no(index.getText());
                Object obj = 1;

                try{
                    obj = ids.updatedel(dv);
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

        time.getItems().addAll("10분","20분","30분","40분","50분","1시간");
    }

    public void initData(DeliveryVO dv) {
        menu.setDisable(true);
        adr.setDisable(true);
        price.setDisable(true);
        index.setVisible(false);

        index.setText(dv.getDeli_no());
        menu.setText(dv.getDeli_name());
        adr.setText(dv.getDeli_adr());
        price.setText(dv.getDeli_price());
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
