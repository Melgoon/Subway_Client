package kr.or.ddit.sw.view.honeycombi;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class HoneyCombiController implements Initializable {

    public JFXMasonryPane manso;
    public JFXButton createna;
    public ImageView naggul;
    private Registry reg;
    private IHoneyCombiService honey;
    public static String name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            honey = (IHoneyCombiService) reg.lookup("honey");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        Image naggulI = new Image("file:src/images/naggul.png");
        naggul.setImage(naggulI);
        refresh();

        createna.setOnAction(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("honeyCombiAddView.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            Stage add = new Stage();
            add.setScene(scene);
            add.show();


        });

    }

    public void refresh() {
        List<HoneyCombiVO> honeyList = null;
        try {
            honeyList = honey.selectAllHoney();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        for (HoneyCombiVO vo : honeyList) {
            Random r = new Random();
            JFXButton a = new JFXButton();
            a.setText(vo.getHONEY_NAME());
            a.setPrefSize(r.nextInt(200) + 100, r.nextInt(200) + 50);
            a.setStyle("-fx-background-color: rgb(" + r.nextInt(255) + "," + r.nextInt(255) + "," + r.nextInt(255) + ")");
            manso.getChildren().add(a);
            a.setOnAction(event -> {
                name = a.getText();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("honeyCombiRead.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();


            });
        }
    }
}
