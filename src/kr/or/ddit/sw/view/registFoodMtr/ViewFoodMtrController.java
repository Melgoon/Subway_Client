package kr.or.ddit.sw.view.registFoodMtr;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import kr.or.ddit.sw.vo.foodmtr.FoodMtrVO;
import kr.or.ddit.sw.vo.foodmtr.VFoodMtrVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewFoodMtrController implements Initializable {



    public TableView<FoodMtrVO> tv_foodMtr;
    public TableColumn<FoodMtrVO,Integer> col_mtr_no;
    public TableColumn<FoodMtrVO,String> col_mtr_name;
    public TableColumn<FoodMtrVO,Integer> col_mtr_cal;
    public TableColumn<FoodMtrVO,String> col_mtr_detail;
    public TableColumn<FoodMtrVO,Integer> col_mtr_div;
    public Button btn_registMtr;
    public JFXButton btn_delete;


    private Registry reg;
    private IRegistFoodMtrService iRegistFoodMtrService;
    public ObservableList<FoodMtrVO> data;
    ArrayList<FoodMtrVO> list = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iRegistFoodMtrService = (IRegistFoodMtrService) reg.lookup("foodMtr");
        }catch (RemoteException | NotBoundException e){
            e.printStackTrace();
        }

        col_mtr_no.setCellValueFactory(new PropertyValueFactory<>("MTR_NO"));
        col_mtr_name.setCellValueFactory(new PropertyValueFactory<>("MTR_NAME"));
        col_mtr_cal.setCellValueFactory(new PropertyValueFactory<>("MTR_CAL"));
        col_mtr_detail.setCellValueFactory(new PropertyValueFactory<>("MTR_DETAIL"));
        col_mtr_div.setCellValueFactory(new PropertyValueFactory<>("MTR_DIV"));


        try {
            list = (ArrayList<FoodMtrVO>) iRegistFoodMtrService.selectFoodMtr();
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        data = FXCollections.observableArrayList(list);
        tv_foodMtr.setItems(data);


        btn_delete.setOnAction(e1->{
            if(tv_foodMtr.getSelectionModel().isEmpty()){
                errMsg("작업오류","삭제 할 식제료를 선택 후 삭제하세요");
                return;
            }

            FoodMtrVO vo = tv_foodMtr.getSelectionModel().getSelectedItem();

            int cnt = 0;
            try {
                cnt = iRegistFoodMtrService.deleteFoodMtr(vo);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if( cnt != 0){
                searchList();

                /*try {
                    list = (ArrayList<FoodMtrVO>) iRegistFoodMtrService.selectFoodMtr();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                data = FXCollections.observableArrayList(list);
                tv_foodMtr.setItems(data);*/
            }
        });


        btn_registMtr.setOnAction(e->{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("RgFoodMtr.fxml"));
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
            


        });



        LoginSession.viewFoodMtrController = this;



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
            list = (ArrayList<FoodMtrVO>) iRegistFoodMtrService.selectFoodMtr();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        data = FXCollections.observableArrayList(list);
        tv_foodMtr.setItems(data);
    }

}

