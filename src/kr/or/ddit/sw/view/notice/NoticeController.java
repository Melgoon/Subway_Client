package kr.or.ddit.sw.view.notice;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.alarm.IAlarmService;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.notice.INoticeService;
import kr.or.ddit.sw.vo.notice.NoticeMemChkVO;
import kr.or.ddit.sw.vo.notice.NoticeVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

public class NoticeController implements Initializable {

    /*
    public TableColumn<NoticeVO, Integer> index;
    public TableColumn<NoticeVO, String> title;
    public TableColumn<NoticeVO, Date> date;
    public TableColumn<NoticeVO, String> content;

*/

    public TableColumn tcIndex;
    public TableColumn tcTitle;
    public TableColumn tcDate;
    public TableColumn tcContent;
    public TableView tvNotice;
    public JFXButton btn_regist;
    public JFXButton btn_delete;

    private Registry reg;
    private INoticeService iNoticeService;
    private IAlarmService regA;
    private NoticeMemChkVO chkVO;
    private ObservableList obList;
    private static NoticeVO select;
    private String user_id;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iNoticeService = (INoticeService) reg.lookup("notice");
            regA= (IAlarmService) reg.lookup("alarm");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        try {
            List<NoticeVO> list = iNoticeService.selectNoticeAll();
            obList = FXCollections.observableArrayList(list);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // TODO 날짜출력
        //공지사항 목룩 출력

        tvNotice.setItems(obList);
        tcIndex.setCellValueFactory(new PropertyValueFactory<>("NOTICE_NO"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("NOTICE_NAME"));
        tcContent.setCellValueFactory(new PropertyValueFactory<>("NOTICE_CONTENT"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("NOTICE_DATE"));

        //공지사항 두번 클릭시
        tvNotice.setOnMouseClicked(event -> {
            if (event.getClickCount() > 1) {  // 더블클릭
                select = (NoticeVO) tvNotice.getSelectionModel().getSelectedItem();

                List<NoticeVO> list = null;

                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("SelectNotice.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root);
                Stage add = new Stage();
                add.setScene(scene);
                add.show();



            }

        });

        // 등록

        if(LoginSession.memberSession!=null) {
            if (LoginSession.memberSession.getClass().desiredAssertionStatus()) {
                btn_regist.setDisable(false);
            } else {
                btn_regist.setDisable(true);
            }
        }

        if(LoginSession.ownerSession!=null){
            if (LoginSession.ownerSession.getClass().desiredAssertionStatus()) {
                btn_regist.setDisable(true);
            } else {
                btn_regist.setDisable(false);
            }
        }

        btn_regist.setOnAction(event -> {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("RgNotice.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            Stage add = new Stage();
            add.setScene(scene);
            add.show();
        });



        // 삭제

        //TextField, TextArea등 문자를 입력하는 객체를
        //ReadOnly를 설정하는 메서드 => setEditable()
        //이 메서드에 true를 설정하면 '입력가능'
        //false를 설정하면 '읽기전용'이 된다.
        //버튼이름.setEditable(false);
        //버튼이름.setEditable(false);
        if(LoginSession.memberSession!=null) {
            if (LoginSession.memberSession.getClass().desiredAssertionStatus()) {
                btn_delete.setDisable(false);
            } else {
                btn_delete.setDisable(true);
            }
        }

        if(LoginSession.ownerSession!=null){
            if (LoginSession.ownerSession.getClass().desiredAssertionStatus()) {
                btn_delete.setDisable(true);
            } else {
                btn_delete.setDisable(false);
            }
        }
        btn_delete.setOnAction(event -> {
            NoticeVO noticeVO = (NoticeVO) tvNotice.getSelectionModel().getSelectedItem();
           // chkVO.setNOTICE_NO(noticeVO.getNOTICE_NO());

            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("공지사항 삭제");
            alertConfirm.setContentText("게시글을 삭제하시겠습니까?");

            ButtonType confirmResult = alertConfirm.showAndWait().get();

            if(confirmResult == ButtonType.OK){
                try {
                    regA.deleteChk(noticeVO.getNOTICE_NO());
                    int chk = iNoticeService.deleteNotice(noticeVO);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                obList.remove(tvNotice.getSelectionModel().getSelectedIndex());

            }
        });

    }

    public static NoticeVO getSelected() {
        return select;
    }

}






