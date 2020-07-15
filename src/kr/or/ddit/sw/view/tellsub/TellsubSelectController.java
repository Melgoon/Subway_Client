package kr.or.ddit.sw.view.tellsub;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.tellsub.ITellSubService;
import kr.or.ddit.sw.vo.tellsub.TellSubVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TellsubSelectController implements Initializable {
    /**
     * 회원 Tellsub 상세보기
     */

    public JFXTextField tellnametf;
    public JFXDatePicker datepk;
    public JFXTextArea contentta;
    public JFXTextArea answerta;
    public JFXTextField jijum;
    public JFXButton ok;

    private Registry reg;
    private ITellSubService itell;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            itell = (ITellSubService) reg.lookup("tell");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        

        ok.setOnAction(event -> {
            Stage stage = (Stage) ok.getScene().getWindow();
            stage.close();
        });




    }
    public void initData(TellSubVO tv){
        tellnametf.setDisable(true);
        datepk.setDisable(true);
        jijum.setDisable(true);
        contentta.setDisable(true);
        answerta.setDisable(true);

        tellnametf.setText(tv.getTellsub_name());
        datepk.setPromptText(tv.getTellsub_visitdate());
        jijum.setText(tv.getTellsub_jijum());
        answerta.setText(tv.getTellsub_rep());

        contentta.setText(tv.getTellsub_content());
    }
}
