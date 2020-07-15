package kr.or.ddit.sw.view.honeycombi;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.honeycombi.IHoneyCombiService;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.vo.honeycombi.HoneyCombiVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class HoneyCombiAddController implements Initializable {

    public JFXButton honey_add_confirm;
    public JFXButton honey_add_cancel;
    public JFXTextArea honey_add_context;
    public JFXTextField honey_add_title;
    public JFXComboBox honey_add_menu;
    public ImageView honey_add_imgview;

    private Registry registry;
    private ObservableList<String> menulist = FXCollections.observableArrayList("bmt", "steak");
    private IHoneyCombiService honeyCombiService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("file:src/images/꿀조합등록.png");
        honey_add_imgview.setImage(image);
        try {
            registry = LocateRegistry.getRegistry("localhost", 7774);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        honey_add_menu.setItems(menulist);

        honey_add_confirm.setOnAction(event -> {

            HoneyCombiVO vo = new HoneyCombiVO();
            //현재 로그인한 아이디를 아이디로 정해주는 것
            vo.setMEM_ID(LoginSession.memberSession.getMEM_ID());
            vo.setHONEY_NAME(honey_add_title.getText());
            vo.setHONEY_CONTENT(honey_add_context.getText());
            vo.setHONEY_MENU((String) honey_add_menu.getSelectionModel().getSelectedItem());
            vo.setHONEY_BAD(0);
            vo.setHONEY_GOOD(0);
            vo.setMEM_ID("admin");
            try {
                honeyCombiService = (IHoneyCombiService) registry.lookup("honey");
                Object obj = 1;
                obj = honeyCombiService.insertHoney(vo);
                if (obj == null) {
                    System.out.println("성공적으로 입력되었습니다.");
                    Parent root = FXMLLoader.load(getClass().getResource("honeyCombiView.fxml"));
                    Stage stage = (Stage) honey_add_confirm.getScene().getWindow();
                    stage.close();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("honeyCombiView.fxml"));

                    try {
                        Pane root1 = loader.load();
                        LoginSession.mainViewController.bp.setCenter(root1);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    return;
                } else {
                    System.out.println("실패하였습니다.");

                }
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        honey_add_cancel.setOnAction(event -> {
            Stage stage = (Stage) honey_add_cancel.getScene().getWindow();
            stage.close();
        });

    }
}
