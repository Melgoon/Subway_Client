package kr.or.ddit.sw.view.review;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.notice.INoticeService;
import kr.or.ddit.sw.service.review.IReviewService;
import kr.or.ddit.sw.vo.notice.NoticeVO;
import kr.or.ddit.sw.vo.review.ReviewVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class SelectReviewController implements Initializable {

    public ImageView selectReview;
    public JFXTextField txtf_title;
    public DatePicker dp_review_date;
    public JFXTextArea txta_content;
    public ImageView iv_star;
    public JFXButton btn_select;
    public JFXButton btn_update;

    private Registry reg;
    private IReviewService iReviewService;

    ReviewVO selected;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image img = new Image("file:src/images/SelectReview.png");
        selectReview.setImage(img);

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iReviewService = (IReviewService) reg.lookup("review");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        btn_select.setOnAction(event -> {
            Stage stage = (Stage) btn_select.getScene().getWindow();
            stage.close();
        });

        selected = ReviewController.getSelect();

        txtf_title.setText(selected.getReview_name());
        txta_content.setText(selected.getReview_content());

        // 수정
        btn_update.setOnAction(event -> {
            selected.setReview_name(txtf_title.getText());
            selected.setReview_content(txta_content.getText());


            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("리뷰 수정");

            ButtonType confirmResult = alertConfirm.showAndWait().get();

            int chk = 0;

            if (confirmResult == ButtonType.OK) {
                try {
                    chk = iReviewService.updateReview(selected);

                    if (chk == 1) {
                        infoMsg("작업결과", "정상적으로 입력되었습니다.");
                    } else {
                        errMsg("에러", "비정상적인 접근입니다.");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (confirmResult == ButtonType.CANCEL) {

            }
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
