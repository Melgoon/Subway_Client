package kr.or.ddit.sw.view.alarm;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.tellsub.ITellSubService;
import kr.or.ddit.sw.service.tellsubmem.ITellsubMemService;
import kr.or.ddit.sw.vo.tellsub.TellSubMemVO;
import kr.or.ddit.sw.vo.tellsub.TellSubVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import static kr.or.ddit.sw.view.basic.BasicSlidePaneController.con1;
import static kr.or.ddit.sw.view.basic.BasicSlidePaneController.countTellSub;

public class TellSubAlarmConoller implements Initializable {

    @FXML
    private ImageView tellSubSendV;
    @FXML
    private TableView tv;
    @FXML
    private TableColumn tellsub_noCol;
    @FXML
    private TableColumn tellsub_jijumCol;
    @FXML
    private TableColumn tellsub_conCol;
    @FXML
    private TableColumn tellsub_dateCol;
    @FXML
    private JFXButton closeBtn;
    Registry reg;
    ITellsubMemService tellSubMem;
    ITellSubService tellSub;
    String user_id;
    ObservableList<TellSubVO> data;
    public static TellSubVO tellvo = new TellSubVO();
    private TellSubMemVO tellMem;
    String a;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            reg= LocateRegistry.getRegistry("localhost",7774);
            tellSubMem = (ITellsubMemService) reg.lookup("tellsubmem");
            tellSub = (ITellSubService) reg.lookup("tell");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        Image image = new Image("file:src/images/TellsubSend.png");
        tellSubSendV.setImage(image);

//        private String Tellsub_no;
//        private String Tellsub_name;
//        private String Tellsub_content;
//        private String Tellsub_rep;
//        private String Tellsub_jijum;
//        private String Tellsub_visitdate;


        tellsub_noCol.setCellValueFactory(new PropertyValueFactory<>("Tellsub_no"));
        tellsub_conCol.setCellValueFactory(new PropertyValueFactory<>("Tellsub_name"));
        tellsub_dateCol.setCellValueFactory(new PropertyValueFactory<>("Tellsub_jijum"));
        tellsub_jijumCol.setCellValueFactory(new PropertyValueFactory<>("Tellsub_visitdate"));

        user_id= LoginSession.memberSession.getMEM_ID();

        try {
            data = FXCollections.observableArrayList(tellSubMem.selectUnionTell(user_id));
            tv.setItems(data);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        tellMem = new TellSubMemVO();
        tv.setOnMouseClicked(e->{
            tellvo = (TellSubVO) tv.getSelectionModel().getSelectedItem();
            a = LoginSession.memberSession.getMEM_ID();
            tellMem.setMemId(a);
            tellMem.setTellsubMemNo(Integer.parseInt(tellvo.getTellsub_no()));
            try {
                tellSubMem.updateTellsubMem(tellMem);
                countTellSub-=1;
                con1.countAlarmView();
                tellsubView();
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }


            FXMLLoader loader= new FXMLLoader(getClass().getResource("TellsubDetail.fxml"));
            try {
                AnchorPane root = (AnchorPane)loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });
        closeBtn.setOnAction(e->{
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
        });

    }

    public void tellsubView(){

        try {
            data = FXCollections.observableArrayList(tellSubMem.selectUnionTell(user_id));
            tv.setItems(data);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
