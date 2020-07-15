package kr.or.ddit.sw.view.review;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.notice.INoticeService;
import kr.or.ddit.sw.service.review.IReviewService;
import kr.or.ddit.sw.vo.notice.NoticeVO;
import kr.or.ddit.sw.vo.review.ReviewVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

public class ReviewController implements Initializable {
    public ImageView subway_logo2;
    public ImageView main_menu1;
    public ImageView main_menu2;
    public ImageView main_menu3;
    public ImageView main_menu4;
    public ImageView main_menu5;
    public TableView tvReview;
    public TableColumn tcIndex;
    public TableColumn tcTitle;
    public TableColumn tcDate;
    public TableColumn tcContent;
    public TableColumn tcStars;
    public JFXButton btn_regist;
    public JFXButton btn_delete;

    private Registry reg;
    private IReviewService iReviewService;

    private ObservableList obList;
    private static ReviewVO select;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image img = new Image("file:src/images/subway_logo2.png");
        subway_logo2.setImage(img);

        Image menu1 = new Image("file:src/images/menu1.png");
        main_menu1.setImage(menu1);

        Image menu2 = new Image("file:src/images/menu2.png");
        main_menu2.setImage(menu2);

        Image menu3 = new Image("file:src/images/menu3.png");
        main_menu3.setImage(menu3);

        Image menu4 = new Image("file:src/images/menu4.png");
        main_menu4.setImage(menu4);

        Image menu5 = new Image("file:src/images/menu5.png");
        main_menu5.setImage(menu5);

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iReviewService = (IReviewService) reg.lookup("review");

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
            List<ReviewVO> rlist = null;
        try {
            rlist = iReviewService.selectReviewAll();
            obList = FXCollections.observableArrayList(rlist);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        tvReview.setItems(obList);
        tcIndex.setCellValueFactory(new PropertyValueFactory<>("review_no"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("review_name"));
        tcContent.setCellValueFactory(new PropertyValueFactory<>("review_content"));
        tcStars.setCellValueFactory(new PropertyValueFactory<>("review_start"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("order_his_no"));

        // 리뷰 두번 클릭 시
        tvReview.setOnMouseClicked(event -> {
            if (event.getClickCount() > 1){
                select = (ReviewVO) tvReview.getSelectionModel().getSelectedItem();

                List<ReviewVO> list = null;

                Parent root = null;

                try {
                    root = FXMLLoader.load(getClass().getResource("Review.fxml"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                Scene scene = new Scene(root);
                Stage add = new Stage();
                add.setScene(scene);
                add.setScene(scene);
                add.show();
            }
        });

        // 등록
        btn_regist.setOnAction(event -> {

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("RegistReview.fxml"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            Scene scene = new Scene(root);
            Stage add = new Stage();
            add.setScene(scene);
            add.show();
        });

        // 삭제
        btn_delete.setOnAction(event -> {
            ReviewVO reviewVO = (ReviewVO) tvReview.getSelectionModel().getSelectedItem();
            Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
            alertConfirm.setTitle("공지사항 삭제");
            alertConfirm.setContentText("게시글을 삭제하시겠습니까?");

            ButtonType confirmResult = alertConfirm.showAndWait().get();

            if(confirmResult == ButtonType.OK){
                try {
                    int chk = iReviewService.deleteReview(reviewVO);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                obList.remove(tvReview.getSelectionModel().getSelectedIndex());
            }

        });

    }

    public static ReviewVO getSelect() {
        return select;
    }
}
