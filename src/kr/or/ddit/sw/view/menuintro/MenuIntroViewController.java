package kr.or.ddit.sw.view.menuintro;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.sun.xml.internal.fastinfoset.tools.FI_SAX_XML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.menuintro.IMenuIntroService;
import kr.or.ddit.sw.vo.prod.ProdVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuIntroViewController implements Initializable {

    public JFXMasonryPane masory;
    public JFXComboBox combo;
    public ScrollPane scroll;

    private Registry registry;
    private IMenuIntroService iMenuIntroService;
    public static ProdVO tempvo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            registry = LocateRegistry.getRegistry("localhost", 7774);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            iMenuIntroService = (IMenuIntroService) registry.lookup("menuList");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

        List<ProdVO> list = null;
        try {
            list = iMenuIntroService.selectAll();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        int i = 1;
        for (ProdVO vo : list) {
            System.out.println(vo.getProd_name());
            VBox vbox = new VBox();
            ImageView imageView = new ImageView();

            Image image = new Image(vo.getProd_pic_adr());
            imageView.setImage(image);
            imageView.setFitWidth(380);
            vbox.getChildren().add(imageView);
            masory.getChildren().add(vbox);
            imageView.setOnMouseClicked(event -> {
                tempvo = vo;
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("MenuDetail.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        ObservableList list1 = FXCollections.observableArrayList("전체", "샌드위치", "샐러드", "쿠키");
        combo.setItems(list1);
        combo.setOnAction(event -> {
            masory.getChildren().clear();
            String temp = (String) combo.getSelectionModel().getSelectedItem();
            int temp1 = 999;
            switch (temp) {
                case "샌드위치":
                    temp1 = 0;
                    break;
                case "샐러드":
                    temp1 = 1;
                    break;
                case "쿠키":
                    temp1 = 2;
                    break;
                case "전체":
                    List<ProdVO> templist = null;
                    try {
                        templist = iMenuIntroService.selectAll();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    for (ProdVO vo : templist) {
                        System.out.println(vo.getProd_name());
                        VBox vbox = new VBox();
                        ImageView imageView = new ImageView();

                        Image image = new Image(vo.getProd_pic_adr());
                        imageView.setImage(image);
                        imageView.setFitWidth(380);

                        vbox.getChildren().add(imageView);
                        masory.getChildren().add(vbox);
                        imageView.setOnMouseClicked(event1 -> {
                            tempvo = vo;
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("MenuDetail.fxml"));
                                Scene scene = new Scene(root);
                                Stage stage = new Stage();
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                    break;
            }
            List<ProdVO> selectlist = null;
            try {
                selectlist = iMenuIntroService.selectKate(temp1);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            for (ProdVO vo : selectlist) {
                System.out.println(vo.getProd_name());
                VBox vbox = new VBox();
                ImageView imageView = new ImageView();

                Image image = new Image(vo.getProd_pic_adr());
                imageView.setFitWidth(380);

                imageView.setImage(image);
                vbox.getChildren().add(imageView);
                masory.getChildren().add(vbox);
                imageView.setOnMouseClicked(event1 -> {
                    tempvo = vo;
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("MenuDetail.fxml"));
                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }


        });


    }
}



