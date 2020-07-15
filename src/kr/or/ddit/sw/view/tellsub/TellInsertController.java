package kr.or.ddit.sw.view.tellsub;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.managementOwner.IManagementOwnerService;
import kr.or.ddit.sw.service.tellsub.ITellSubService;
import kr.or.ddit.sw.service.tellsubmem.ITellsubMemService;
import kr.or.ddit.sw.vo.orderhis.OrderHisVO;
import kr.or.ddit.sw.vo.owner.OwnerVO;
import kr.or.ddit.sw.vo.tellsub.TellSubMemVO;
import kr.or.ddit.sw.vo.tellsub.TellSubVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static kr.or.ddit.sw.view.tellsub.TellSubController.contell;

public class TellInsertController implements Initializable {

    public JFXTextField tellnametf;
    public JFXTextArea contentta;
    public JFXButton insertbtn;
    public JFXButton cancel;
    public JFXDatePicker sysdatepk;
    public JFXComboBox jijum;
    public ImageView imgur;

    private Registry reg;
    private ITellSubService itell;
    private TellSubController a;
    private IManagementOwnerService imos;
    private ArrayList<OwnerVO> list = new ArrayList<>();
    private ObservableList<OwnerVO> allTableData;
    private ITellsubMemService itellmem;
    private TellSubMemVO tellSubMem;
    private String user_name;
    private OwnerVO vo;
    public void setA(TellSubController a) {
        this.a = a;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("file:src/images/TellSubInsert.png");
        imgur.setImage(img);

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            itell = (ITellSubService) reg.lookup("tell");
            imos = (IManagementOwnerService) reg.lookup("owner");
            itellmem = (ITellsubMemService) reg.lookup("tellsubmem");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        try{
            list = (ArrayList<OwnerVO>) imos.selectOwner();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        allTableData = FXCollections.observableArrayList();
        allTableData.addAll(list);
        user_name =LoginSession.memberSession.getMEM_ID();
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

        //TODO 매장 선택이 필요하고, 일단 시간이 오래걸릴 것 같아서 미룸
            insertbtn.setOnAction(event -> {
                if(tellnametf.getText().isEmpty() ||  contentta.getText().isEmpty()){
                    errMsg("에러","값을 입력하세요.");
                    return;
                }
                else{
                    TellSubVO tv =  new TellSubVO();
                    OrderHisVO ov = new OrderHisVO();

                    OwnerVO dd = (OwnerVO) jijum.getSelectionModel().getSelectedItem();

                    tv.setTellsub_name(tellnametf.getText());
                    tv.setTellsub_content(contentta.getText());
                    tv.setTellsub_visitdate(String.valueOf(sysdatepk.getValue()));
                    tv.setTellsub_jijum(dd.getOwner_jijum());
                    Object obj = 1;

                    try {
                        obj = itell.insertTell(tv);

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    if (obj == null) {
                        errMsg("에러","비정상적인 접근입니다.");
                    } else {
                        infoMsg("작업결과","정상적으로 입력되었습니다.");
                    }

                    //여기에 check 인설트 부분 넣어보자자

                    try {
                        itellmem.insertTellsubMem(user_name);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    // a.loadData();
                    contell.loadData();
                    Stage stage = (Stage) insertbtn.getScene().getWindow();
                    stage.close();

                }
            });

            cancel.setOnAction(event -> {

                Stage stage = (Stage) cancel.getScene().getWindow();
                stage.close();
            });
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
