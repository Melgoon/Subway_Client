package kr.or.ddit.sw.view.ordertable;

import com.jfoenix.controls.*;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.menuintro.IMenuIntroService;
import kr.or.ddit.sw.service.ordertable.IOrderTableService;
import kr.or.ddit.sw.vo.foodmtr.FoodMtrVO;
import kr.or.ddit.sw.vo.prod.CombiVO;
import kr.or.ddit.sw.vo.prod.ProdVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderTableViewController implements Initializable {

    public JFXDrawer drawer;

    public JFXMasonryPane masory;
    public ScrollPane scroll;
    public ImageView step;
    public JFXButton next;

    private Registry registry;
    private IMenuIntroService iMenuIntroService;
    private IOrderTableService iOrderTableService;
    public static ProdVO tempvo;
    public static FoodMtrVO foodMtr;
    public static ArrayList<CombiVO> combiVO = new ArrayList<CombiVO>();
    public static int tempint = 0;

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

        Image step1 = new Image("file:src/images/step/step1.png");
        Image step2 = new Image("file:src/images/step/step2.png");
        Image step3 = new Image("file:src/images/step/step3.png");
        Image step4 = new Image("file:src/images/step/step4.png");
        Image step5 = new Image("file:src/images/step/step5.png");
        Image step6 = new Image("file:src/images/step/step6.png");

        step.setImage(step1);

        List<ProdVO> list = null;
        try {
            list = iMenuIntroService.selectKate(0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        int i = 1;
        for (ProdVO vo : list) {
            VBox vbox = new VBox();
            ImageView imageView = new ImageView();
            imageView.setFitWidth(380);
            Image image = new Image(vo.getProd_pic_adr());
            imageView.setImage(image);
            vbox.getChildren().add(imageView);
            masory.getChildren().add(vbox);
            imageView.setOnMouseClicked(event -> {
                masory.getChildren().clear();
                tempvo = vo;
                List<FoodMtrVO> foodMtrVOList = null;
                try {
                    iOrderTableService = (IOrderTableService) registry.lookup("orderTableService");
                    foodMtrVOList = iOrderTableService.select(1);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                }

                for (FoodMtrVO foodMtrVO : foodMtrVOList) {
                    step.setImage(step2);

                    VBox vbox1 = new VBox();
                    ImageView imageView1 = new ImageView();
                    imageView1.setFitWidth(380);
                    Image image1 = new Image(foodMtrVO.getMTR_PIC_ADR());
                    imageView1.setImage(image1);
                    vbox1.getChildren().add(imageView1);
                    masory.getChildren().add(vbox1);
                    imageView1.setOnMouseClicked(event2 -> {
                        foodMtr = foodMtrVO;
                        selectCombi(tempvo, foodMtr);
                        masory.getChildren().clear();
                        List<FoodMtrVO> foodMtrVOList1 = null;
                        try {
                            foodMtrVOList1 = iOrderTableService.select(3);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        for (FoodMtrVO vo2 : foodMtrVOList1) {
                            step.setImage(step3);
                            VBox vbox2 = new VBox();
                            ImageView imageView2 = new ImageView();
                            imageView2.setFitWidth(380);
                            Image image2 = new Image(vo2.getMTR_PIC_ADR());
                            imageView2.setImage(image2);
                            vbox2.getChildren().add(imageView2);
                            masory.getChildren().add(vbox2);

                            imageView2.setOnMouseClicked(event3 -> {
                                foodMtr = vo2;
                                selectCombi(tempvo, foodMtr);
                                masory.getChildren().clear();
                                List<FoodMtrVO> foodMtrVOList2 = null;
                                try {
                                    foodMtrVOList2 = iOrderTableService.select(4);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                                for (FoodMtrVO vo3 : foodMtrVOList2) {
                                    next.setDisable(false);
                                    step.setImage(step4);
                                    VBox vbox3 = new VBox();
                                    ImageView imageView3 = new ImageView();
                                    imageView3.setFitWidth(380);
                                    Image image3 = new Image(vo3.getMTR_PIC_ADR());
                                    imageView3.setImage(image3);
                                    vbox3.getChildren().add(imageView3);
                                    masory.getChildren().add(vbox3);
                                    imageView3.setOnMouseClicked(event1 -> {
                                        imageView3.setVisible(false);
                                        foodMtr = vo3;
                                        selectCombi(vo, foodMtr);
                                    });
                                    next.setOnAction(event1 -> {
                                        List<FoodMtrVO> sourceList = null;
                                        try {
                                            sourceList = iOrderTableService.select(5);
                                        } catch (RemoteException e) {
                                            e.printStackTrace();
                                        }
                                        masory.getChildren().clear();
                                        for (FoodMtrVO vo4 : sourceList) {
                                            step.setImage(step5);
                                            VBox vBox4 = new VBox();
                                            ImageView imageView4 = new ImageView();
                                            imageView4.setFitWidth(380);
                                            Image image4 = new Image(vo4.getMTR_PIC_ADR());
                                            imageView4.setImage(image4);
                                            vBox4.getChildren().add(imageView4);
                                            masory.getChildren().add(vBox4);
                                            imageView4.setOnMouseClicked(event4 -> {
                                                imageView4.setVisible(false);
                                                foodMtr = vo4;
                                                selectCombi(vo, foodMtr);
                                            });
                                            next.setOnAction(event4 -> {
                                                masory.getChildren().clear();
                                                step.setImage(step6);
                                                next.setDisable(true);
                                                Image set = new Image("file:src/images/set.png");
                                                Image dan = new Image("file:src/images/dan.png");
                                                ImageView setview = new ImageView();
                                                ImageView danview = new ImageView();
                                                setview.setFitWidth(380);
                                                danview.setFitWidth(380);
                                                setview.setImage(set);
                                                danview.setImage(dan);
                                                HBox box1 = new HBox();
                                                box1.getChildren().add(setview);
                                                HBox box2 = new HBox();
                                                box2.getChildren().add(danview);
                                                masory.getChildren().add(box1);
                                                masory.getChildren().add(box2);
                                                danview.setOnMouseClicked(event5 -> {
                                                    masory.getChildren().clear();
                                                    for (CombiVO volist : combiVO) {
                                                        Object obj = null;
                                                        try {
                                                            obj = iOrderTableService.insert(volist);
                                                        } catch (RemoteException e) {
                                                            e.printStackTrace();
                                                        }
                                                        if (obj == null) {
                                                            System.out.println("성공적으로 입력되었습니다.");
                                                            //
                                                            combiVO = new ArrayList<>(); //추가함
                                                            next.setDisable(false);
                                                            next.setOnAction(event8 -> {
                                                                try {
                                                                    Parent root = FXMLLoader.load(getClass().getResource("../cart/CartLoader.fxml"));
                                                                    LoginSession.mainViewController.bp.setCenter(root);
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }

                                                            });


                                                            //
                                                        }
                                                    }
                                                });
                                                setview.setOnMouseClicked(event5 -> {
                                                    masory.getChildren().clear();
                                                    List<FoodMtrVO> cookieList = null;
                                                    try {
                                                        cookieList = iOrderTableService.select(7);
                                                    } catch (RemoteException e) {
                                                        e.printStackTrace();
                                                    }
                                                    for (FoodMtrVO cookies : cookieList) {
                                                        Image cookie = new Image(cookies.getMTR_PIC_ADR());
                                                        ImageView cookieView = new ImageView();
                                                        cookieView.setImage(cookie);
                                                        cookieView.setFitWidth(380);
                                                        VBox vBox = new VBox();
                                                        vBox.getChildren().add(cookieView);
                                                        masory.getChildren().add(vBox);
                                                        cookieView.setOnMouseClicked(event6 -> {
                                                            foodMtr = cookies;
                                                            selectCombi(vo, foodMtr);
                                                            masory.getChildren().clear();
                                                            List<FoodMtrVO> drinklist = null;
                                                            try {
                                                                drinklist = iOrderTableService.select(6);
                                                            } catch (RemoteException e) {
                                                                e.printStackTrace();
                                                            }
                                                            for (FoodMtrVO drinks : drinklist) {
                                                                Image drink = new Image(drinks.getMTR_PIC_ADR());
                                                                ImageView drinkView = new ImageView();
                                                                drinkView.setImage(drink);
                                                                drinkView.setFitWidth(380);
                                                                drinkView.setFitHeight(380);
                                                                VBox vBox1 = new VBox();
                                                                vBox1.getChildren().add(drinkView);
                                                                masory.getChildren().add(vBox1);
                                                                drinkView.setOnMouseClicked(event7 -> {
                                                                    masory.getChildren().clear();
                                                                    foodMtr = drinks;
                                                                    selectCombi(vo, foodMtr);
                                                                    for (CombiVO volist : combiVO) {
                                                                        Object obj = null;
                                                                        try {
                                                                            obj = iOrderTableService.insert(volist);
                                                                        } catch (RemoteException e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                        if (obj == null) {
                                                                            System.out.println("성공적으로 입력되었습니다.");
                                                                            combiVO = new ArrayList<>(); //추가함
                                                                            next.setDisable(false);
                                                                            next.setOnAction(event8 -> {
                                                                                try {
                                                                                    Parent root = FXMLLoader.load(getClass().getResource("../cart/CartLoader.fxml"));
                                                                                    LoginSession.mainViewController.bp.setCenter(root);
                                                                                } catch (IOException e) {
                                                                                    e.printStackTrace();
                                                                                }

                                                                            });
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                });

                                            });
                                        }
                                    });

                                }
                            });

                        }
                    });
                }


            });
        }

    }

    public static void selectCombi(ProdVO prodVO, FoodMtrVO foodMtrVO) {
        tempint++;

        CombiVO combiVO1 = new CombiVO();
        combiVO1.setCombi_no(tempint);
        combiVO1.setProd_no(prodVO.getProd_no());
        combiVO1.setMtr_no(foodMtrVO.getMTR_NO());
        combiVO.add(combiVO1);
        for (CombiVO vo : combiVO) {
            System.out.println(vo.getCombi_no());
        }
    }

}
