package kr.or.ddit.sw.view.registProd;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.registFoodMtr.IRegistFoodMtrService;
import kr.or.ddit.sw.service.registProd.IRegistProdService;
import kr.or.ddit.sw.vo.foodmtr.FoodMtrVO;
import kr.or.ddit.sw.vo.prod.CombiVO;
import kr.or.ddit.sw.vo.prod.KategoriVO;
import kr.or.ddit.sw.vo.prod.ProdVO;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegistProdController implements Initializable {

    public ImageView img_Prod;
    public JFXComboBox combo_kate_no;
    public JFXTextField txtf_prod_name;
    public JFXTextField txtf_prod_price;
    public JFXComboBox combo_bread;
    public JFXComboBox combo_meat;
    public JFXComboBox combo_cheese;
    public JFXComboBox combo_source;
    public JFXComboBox combo_vegetable;
    public JFXButton btn_regist;
    public JFXButton btn_cansel;
    public Spinner<Integer> bread_count;
    public Spinner<Integer> meat_count;
    public Spinner<Integer> cheese_count;
    public Spinner<Integer> source_count;
    public Spinner<Integer> vegetable_count;

    int initialValue = 1;


    private Registry reg;
    private IRegistProdService iRegistProdService;
    private IRegistFoodMtrService iRegistFoodMtrService;

    List<KategoriVO> kategorilist = new ArrayList<>();
    List<FoodMtrVO> foodmtrlist = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("file:src/images/prod.png");
        img_Prod.setImage(img);

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iRegistProdService = (IRegistProdService) reg.lookup("prod");
            iRegistFoodMtrService = (IRegistFoodMtrService) reg.lookup("foodMtr");
        }catch (RemoteException | NotBoundException e){
            e.printStackTrace();
        }


        SpinnerValueFactory<Integer> valuebread = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5,initialValue);
        bread_count.setValueFactory(valuebread);
        SpinnerValueFactory<Integer> valuemeat = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5,initialValue);
        meat_count.setValueFactory(valuemeat);
        SpinnerValueFactory<Integer> valuechees = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5,initialValue);
        cheese_count.setValueFactory(valuechees);
        SpinnerValueFactory<Integer> valuesource = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5,initialValue);
        source_count.setValueFactory(valuesource);
        SpinnerValueFactory<Integer> valuevegetable = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,5,initialValue);
        vegetable_count.setValueFactory(valuevegetable);


        try {
            kategorilist = iRegistProdService.selectKategori();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            foodmtrlist = iRegistFoodMtrService.selectFoodMtr();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        ObservableList<String> kateList = FXCollections.observableArrayList();

        for(int i = 0; i< kategorilist.size(); i++){
            kateList.add(kategorilist.get(i).getKate_name());
        }
        combo_kate_no.setItems(kateList);





        List<FoodMtrVO> breadMtrlist = new ArrayList<>();
        try {
            breadMtrlist = iRegistProdService.selectMtr(1);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        ObservableList<String> breadList = FXCollections.observableArrayList();

        for(int i=0; i<breadMtrlist.size(); i++){
            breadList.add(breadMtrlist.get(i).getMTR_NAME());
        }
        combo_bread.setItems(breadList);



        List<FoodMtrVO> meatMtrlist = new ArrayList<>();
        try {
            meatMtrlist = iRegistProdService.selectMtr(2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        ObservableList<String> meatList = FXCollections.observableArrayList();

        for(int i=0; i<meatMtrlist.size(); i++){
            meatList.add(meatMtrlist.get(i).getMTR_NAME());
        }
        combo_meat.setItems(meatList);



        List<FoodMtrVO> cheeseMtrlist = new ArrayList<>();
        try {
            cheeseMtrlist = iRegistProdService.selectMtr(3);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        ObservableList<String> cheeseList = FXCollections.observableArrayList();

        for(int i=0; i<cheeseMtrlist.size(); i++){
            cheeseList.add(cheeseMtrlist.get(i).getMTR_NAME());
        }
        combo_cheese.setItems(cheeseList);


        List<FoodMtrVO> sourceMtrlist = new ArrayList<>();
        try {
            sourceMtrlist = iRegistProdService.selectMtr(4);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        ObservableList<String> sourceList = FXCollections.observableArrayList();

        for(int i=0; i<sourceMtrlist.size(); i++){
            sourceList.add(sourceMtrlist.get(i).getMTR_NAME());
        }
        combo_source.setItems(sourceList);



        List<FoodMtrVO> vegetableMtrlist = new ArrayList<>();
        try {
            vegetableMtrlist = iRegistProdService.selectMtr(8);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        ObservableList<String> vegitableList = FXCollections.observableArrayList();

        for(int i=0; i<vegetableMtrlist.size(); i++){
            vegitableList.add(vegetableMtrlist.get(i).getMTR_NAME());
        }
        combo_vegetable.setItems(vegitableList);



        btn_cansel.setOnAction(e2->{
            Stage stage = (Stage) btn_cansel.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewProd.fxml"));
            try {
                Pane root = (Pane) loader.load();
                Scene scene = new Scene(root);
                Stage stage1 = new Stage();
                stage1.initModality(Modality.APPLICATION_MODAL);
                stage1.setScene(scene);
                stage1.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });

        btn_regist.setOnAction(e->{
            ProdVO pvo = new ProdVO();

            for(int i = 0; i< kategorilist.size(); i++){
                if(kategorilist.get(i).getKate_name().equals(combo_kate_no.getValue())){
                    pvo.setKate_no(kategorilist.get(i).getKate_no());
                }
            }

            pvo.setProd_name(txtf_prod_name.getText());
            pvo.setProd_price(Integer.parseInt(txtf_prod_price.getText()));

            try {
                iRegistProdService.insertProd(pvo);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }



            if(pvo.getKate_no()==0){

                if(combo_bread.getValue()!=null){
                    CombiVO cvo = new CombiVO();
                    cvo.setMtr_no(1);
                    cvo.setCombi_count(bread_count.getValue());
                    try {
                        iRegistProdService.insertCombi(cvo);
                    } catch (RemoteException e3) {
                        e3.printStackTrace();
                    }
                }

                if(combo_meat.getValue()!=null){
                    CombiVO cvo = new CombiVO();
                    cvo.setMtr_no(2);
                    cvo.setCombi_count(meat_count.getValue());
                    try {
                        iRegistProdService.insertCombi(cvo);
                    } catch (RemoteException e3) {
                        e3.printStackTrace();
                    }
                }

                if(combo_cheese.getValue()!=null){
                    CombiVO cvo = new CombiVO();
                    cvo.setMtr_no(3);
                    cvo.setCombi_count(cheese_count.getValue());
                    try {
                        iRegistProdService.insertCombi(cvo);
                    } catch (RemoteException e3) {
                        e3.printStackTrace();
                    }
                }


                if(combo_source.getValue()!=null){
                    CombiVO cvo = new CombiVO();
                    cvo.setMtr_no(5);
                    cvo.setCombi_count(source_count.getValue());
                    try {
                        iRegistProdService.insertCombi(cvo);
                    } catch (RemoteException e3) {
                        e3.printStackTrace();
                    }
                }


                if(combo_vegetable.getValue()!=null){
                    CombiVO cvo = new CombiVO();
                    cvo.setMtr_no(8);
                    cvo.setCombi_count(vegetable_count.getValue());
                    try {
                        iRegistProdService.insertCombi(cvo);
                    } catch (RemoteException e3) {
                        e3.printStackTrace();
                    }
                }

            }

            /*Stage stage = (Stage) btn_cansel.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewProd.fxml"));
            try {
                Pane root = (Pane) loader.load();
                Scene scene = new Scene(root);
                Stage stage1 = new Stage();
                stage1.initModality(Modality.APPLICATION_MODAL);
                stage1.setScene(scene);
                stage1.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }*/

            Stage stage1 = (Stage) btn_cansel.getScene().getWindow();
            stage1.close();


            /*Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("ViewProd.fxml"));
                Scene scene = new Scene(root);
                //Stage stage = (Stage) LoginSession.viewProdController.btn_registProd.getScene().getWindow();
                //Stage stage = LoginSession.viewprod;
                //stage.setScene(scene);
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            *//*Scene scene = new Scene(root);
            //Stage stage = (Stage) LoginSession.viewProdController.btn_registProd.getScene().getWindow();
            Stage stage = LoginSession.viewprod;
            stage.setScene(root);*/

            LoginSession.viewProdController.searchList();



        });

    }

}
