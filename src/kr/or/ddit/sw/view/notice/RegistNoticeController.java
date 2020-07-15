package kr.or.ddit.sw.view.notice;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.notice.INoticeService;
import kr.or.ddit.sw.vo.notice.NoticeVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Calendar;
import java.util.ResourceBundle;

public class RegistNoticeController implements Initializable {


    public ObservableList<NoticeVO> data;
    public ImageView registNotice;
    public JFXTextField txtf_notice_title;
    public DatePicker dp_notice_sysdate;
    public JFXTextArea txta_notice_contents;
    public JFXButton btn_RegistNotice;
    public JFXButton btn_cancel;
    public JFXTextField txtf_date;

    private Registry reg;
    private INoticeService iNoticeService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image img = new Image("file:src/images/RegistNotice.png");
        registNotice.setImage(img);

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iNoticeService = (INoticeService) reg.lookup("notice");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        ObservableList<String> NoticeList = FXCollections.observableArrayList("제목", "날짜", "내용");

        btn_RegistNotice.setOnAction(event -> {
            if (txtf_notice_title.getText().isEmpty() || txta_notice_contents.getText().isEmpty()) {
                errMsg("에러", "값을 입력하세요.");
                return;
            } else {
                NoticeVO nv = new NoticeVO();

                nv.setNOTICE_NAME(txtf_notice_title.getText());
                nv.setNOTICE_CONTENT(txta_notice_contents.getText());



                Object obj = 1;

                try {
                    obj = iNoticeService.insertNotice(nv);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                if (obj == null) {
                    errMsg("에러", "비정상적인 접근입니다.");
                } else {
                    infoMsg("작업결과", "정상적으로 입력되었습니다.");
                }

                Stage stage = (Stage) btn_RegistNotice.getScene().getWindow();
                stage.close();

                // 다시 로드하는 부분
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Notice.fxml"));

                try {
                    Pane root = loader.load();
                    LoginSession.mainAdminController.bp.setCenter(root);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                return;
            }
        });

        btn_cancel.setOnAction(event -> {
            Stage stage = (Stage) btn_cancel.getScene().getWindow();
            stage.close();
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
