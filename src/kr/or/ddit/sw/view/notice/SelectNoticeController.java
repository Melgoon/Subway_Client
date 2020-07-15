package kr.or.ddit.sw.view.notice;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.notice.INoticeService;
import kr.or.ddit.sw.vo.notice.NoticeVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

public class SelectNoticeController<event> implements Initializable {
    public ImageView selectNotice;
    public Button btn_select;
    public JFXTextField txtf_title;
    public JFXTextField txtf_date;
    public JFXTextArea txta_content;
    public JFXButton btn_update;

    private Registry reg;
    private INoticeService iNoticeService;


    NoticeVO selected;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image img = new Image("file:src/images/SelectNotice.png");
        selectNotice.setImage(img);

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iNoticeService = (INoticeService) reg.lookup("notice");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        btn_select.setOnAction(event -> {
            Stage stage = (Stage) btn_select.getScene().getWindow();
            stage.close();
        });

        selected = NoticeController.getSelected();

        txtf_title.setText(selected.getNOTICE_NAME());
        txta_content.setText(selected.getNOTICE_CONTENT());
        txtf_date.setText(selected.getNOTICE_DATE());




        // 수정

        if(LoginSession.memberSession!=null) {
            if (LoginSession.memberSession.getClass().desiredAssertionStatus()) {
                btn_update.setDisable(false);
            } else {
                btn_update.setDisable(true);
            }
        }

        if(LoginSession.ownerSession!=null){
            if (LoginSession.ownerSession.getClass().desiredAssertionStatus()) {
                btn_update.setDisable(true);
            } else {
                btn_update.setDisable(false);
            }
        }

        btn_update.setOnAction(event -> {
            selected.setNOTICE_NAME(txtf_title.getText());
            selected.setNOTICE_CONTENT(txta_content.getText());

            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("공지사항 수정");

            ButtonType confirmResult = alertConfirm.showAndWait().get();

            int chk = 0;

            if(confirmResult == ButtonType.OK){
                try {
                    chk = iNoticeService.updateNotice(selected);

                    if (chk == 1) {
                        infoMsg("작업결과", "정상적으로 입력되었습니다.");
                    } else {
                        errMsg("에러", "비정상적인 접근입니다.");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
            else if(confirmResult == ButtonType.CANCEL) {

            }

            /*try {
                List<NoticeVO> list = iNoticeService.selectNoticeAll();
                NoticeController = FXCollections.observableArrayList(list);
            } catch (RemoteException e) {
                e.printStackTrace();
            }*/
        });

    }

    private void infoMsg(String headerText, String msg) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("작업 결과");
        infoAlert.setHeaderText(headerText);
        infoAlert.setContentText(msg);
        infoAlert.showAndWait();
    }

    private void errMsg(String headerText, String msg) {
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle("오류");
        errAlert.setHeaderText(headerText);
        errAlert.setContentText(msg);
        errAlert.showAndWait();
    }


}
