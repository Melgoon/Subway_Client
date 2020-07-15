package kr.or.ddit.sw.view.review;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.review.IReviewService;
import kr.or.ddit.sw.vo.review.ReviewVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class RegistReviewController implements Initializable {
    public ImageView registReview;
    public JFXTextField txtf_review_title;
    public DatePicker dp_review_date;
    public JFXTextArea txta_review_content;
    public ImageView iv_review_star;
    public JFXButton btn_RegistReview;
    public JFXButton btn_cancel;
    public ImageView star;

    private Registry reg;
    private IReviewService iReviewService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image img = new Image("file:src/images/RegistReview.png");
        registReview.setImage(img);

        Image img2 = new Image("file:src/images/star0.png");
        star.setImage(img2);

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iReviewService = (IReviewService) reg.lookup("review");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        ObservableList<String> ReviewList = FXCollections.observableArrayList("제목", "날짜", "내용", "별점");

        btn_RegistReview.setOnAction(event -> {
            if(txtf_review_title.getText().isEmpty() || txta_review_content.getText().isEmpty()){
                errMsg("에러", "값을 입력하세요.");
                return;
            } else {
                ReviewVO rv = new ReviewVO();

                rv.setReview_name(txtf_review_title.getText());
                rv.setReview_content(txta_review_content.getText());

                Object obj = 1;

                try {
                    obj = iReviewService.insertReview(rv);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                if (obj == null){
                    errMsg("에러", "비정상적인 접근입니다.");
                } else {
                    infoMsg("작업결과", "정상적으로 입력되었습니다.");

                }

                Stage stage = (Stage) btn_RegistReview.getScene().getWindow();
                stage.close();

                // 다시 로드하는 부분
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Review.fxml"));

                Pane root = null;
                try {
                    root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage1 = new Stage();
                    stage1.setScene(scene);
                    stage1.show();
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
