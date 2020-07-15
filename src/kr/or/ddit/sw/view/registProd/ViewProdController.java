package kr.or.ddit.sw.view.registProd;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

public class ViewProdController implements Initializable {
    public TableView tv_prod;
    public TableColumn col_prod_no;
    public TableColumn col_kate_no;
    public TableColumn col_prod_name;
    public TableColumn col_prod_price;
    public JFXButton btn_delete;
    public ImageView vpimage;
    public JFXButton btn_registProd;

    private Registry reg;
    private IRegistProdService iRegistProdService;
    public ObservableList<ProdVO> data;
    ArrayList<ProdVO> list = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*Image img = new Image("file:src/images/subway_logo.png");
        vpimage.setImage(img);*/

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iRegistProdService = (IRegistProdService) reg.lookup("prod");
        }catch (RemoteException | NotBoundException e){
            e.printStackTrace();
        }

        col_prod_no.setCellValueFactory(new PropertyValueFactory<>("prod_no"));
        col_kate_no.setCellValueFactory(new PropertyValueFactory<>("kate_no"));
        col_prod_name.setCellValueFactory(new PropertyValueFactory<>("prod_name"));
        col_prod_price.setCellValueFactory(new PropertyValueFactory<>("prod_price"));


        searchList();
        /*try {
            list = (ArrayList<ProdVO>) iRegistProdService.selectProd();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        data = FXCollections.observableArrayList(list);

        tv_prod.setItems(data);*/

        btn_registProd.setOnAction(e->{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RgProd.fxml"));
            try {
                Pane root = (Pane) loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            /*Stage stage = (Stage) btn_registPro.getScene().getWindow();
            stage.close();*/

        });

        btn_delete.setOnAction(e->{
            if(tv_prod.getSelectionModel().isEmpty()){
                errMsg("작업오류","삭제 할 식제료를 선택 후 삭제하세요");
                return;
            }


            ProdVO vo = (ProdVO) tv_prod.getSelectionModel().getSelectedItem();

            if(vo.getKate_no()==0){

                int prod_no = ((ProdVO) tv_prod.getSelectionModel().getSelectedItem()).getProd_no();

                int cnt = 0;
                try {
                    cnt = iRegistProdService.deleteCombi(prod_no);
                } catch (RemoteException e3) {
                    e3.printStackTrace();
                }

            }
            int cnt = 0;
            try {
                cnt = iRegistProdService.deleteProd(vo);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }

            if( cnt != 0){

                try {
                    list = (ArrayList<ProdVO>) iRegistProdService.selectProd();
                } catch (RemoteException e3) {
                    e3.printStackTrace();
                }
                data = FXCollections.observableArrayList(list);
                tv_prod.setItems(data);
            }
        });


        LoginSession.viewProdController = this;
        //LoginSession.viewprod = (Stage) tv_prod.getScene().getWindow();
    }
    private void errMsg(String headerText, String msg) {
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle("오류");
        errAlert.setHeaderText(headerText);
        errAlert.setContentText(msg);
        errAlert.showAndWait();
    }

    public void searchList(){
        try {
            list = (ArrayList<ProdVO>) iRegistProdService.selectProd();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        data = FXCollections.observableArrayList(list);

        tv_prod.setItems(data);
    }


}
