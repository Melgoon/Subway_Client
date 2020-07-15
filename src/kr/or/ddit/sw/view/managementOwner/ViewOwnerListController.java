package kr.or.ddit.sw.view.managementOwner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import kr.or.ddit.sw.service.managementOwner.IManagementOwnerService;
import kr.or.ddit.sw.service.registFoodMtr.IRegistFoodMtrService;
import kr.or.ddit.sw.view.tellsub.TellsubSelectController;
import kr.or.ddit.sw.vo.foodmtr.FoodMtrVO;
import kr.or.ddit.sw.vo.ordertable.DeliveryVO;
import kr.or.ddit.sw.vo.owner.OwnerVO;
import kr.or.ddit.sw.vo.tellsub.TellSubVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewOwnerListController implements Initializable {



    public ObservableList<OwnerVO> data;
    public TableColumn col_ownerCode;
    public TableColumn col_Addr;
    public TableColumn col_Jijum;
    public TableColumn col_hp;
    public TableColumn col_mail;
    public TableColumn col_pass;
    public TableView tv_Owner;

    public Pagination pagenation;

    private Registry reg;

    private int from, to, itemsForPage;
    private ObservableList<OwnerVO> allTableData, currentPageData;
    private IManagementOwnerService iManagementOwnerService;
    
    List<OwnerVO> list = new ArrayList<>();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tv_Owner.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()>1){
                    OwnerVO ov = new OwnerVO();
                    ov = (OwnerVO) tv_Owner.getSelectionModel().getSelectedItem();

                    Stage dialog = new Stage(StageStyle.UTILITY);
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.setTitle("사업자등록");

                    Parent parent = null;
                    try {
                        //parent = new FXMLLoader().load(getClass().getResource("TellSubwayselect.fxml"));
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("OwnerInfo.fxml"));
                        parent = loader.load();
                        OwnerUpdateController up = loader.getController();
                        up.initData(ov);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    Scene scene = new Scene(parent);

                    dialog.setScene(scene);
                    dialog.setResizable(false);
                    dialog.show();


                    /*Stage stage = (Stage) tv_Owner.getScene().getWindow();
                    stage.close();*/





                }
            }
        });


        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iManagementOwnerService = (IManagementOwnerService) reg.lookup("owner");
        }catch (RemoteException | NotBoundException e){
            e.printStackTrace();
        }

        col_ownerCode.setCellValueFactory(new PropertyValueFactory<>("owner_code"));
        col_Addr.setCellValueFactory(new PropertyValueFactory<>("owner_addr"));
        col_Jijum.setCellValueFactory(new PropertyValueFactory<>("owner_jijum"));
        col_hp.setCellValueFactory(new PropertyValueFactory<>("owner_tel"));
        col_mail.setCellValueFactory(new PropertyValueFactory<>("owner_email"));
        col_pass.setCellValueFactory(new PropertyValueFactory<>("owner_pw"));

        try {
            list = (ArrayList<OwnerVO>) iManagementOwnerService.selectOwner();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        //data = FXCollections.observableArrayList(list);
        allTableData = FXCollections.observableArrayList(list);

        addButtonToTable();

        itemsForPage = 14; //한페이지에 보여줄 항목 수
        int totalDataCnt = allTableData.size();
        int totalPageCnt = totalDataCnt % itemsForPage == 0 ?
                totalDataCnt / itemsForPage : totalDataCnt / itemsForPage + 1 ;

        pagenation.setPageCount(totalPageCnt); //전체 페이지 수 설정
        pagenation.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                from = pageIndex * itemsForPage;
                to = from + itemsForPage - 1;
                tv_Owner.setItems(getTableViewData(from,to));
                return tv_Owner;
            }

            private ObservableList<OwnerVO> getTableViewData(int from, int to){
                currentPageData = FXCollections.observableArrayList();
                int totSize = allTableData.size();
                for(int i=from; i<=to && i<totSize; i++){
                    currentPageData.add(allTableData.get(i));
                }
                return currentPageData;
            }
        });
        //tv_Owner.setItems(data);
       // addButtonToTable();


        
    }

    //TODO 자꾸 버튼이 자꾸 눌리고 정말 할 수 없다 페이징처리를 해도 안된다
    private void addButtonToTable() {

        TableColumn<OwnerVO, String> colBtn = new TableColumn("승인관리");

        Callback<TableColumn<OwnerVO, String>, TableCell<OwnerVO, String>> cellFactory = new Callback<TableColumn<OwnerVO, String>, TableCell<OwnerVO, String>>() {

            @Override
            public TableCell<OwnerVO, String> call(TableColumn<OwnerVO, String> param) {
                final TableCell<OwnerVO, String> cell = new TableCell<OwnerVO, String>() {

                    private final ToggleButton btn = new ToggleButton("승인");

                    {
                        btn.setOnAction(event -> {


                        });
                    }

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tv_Owner.getColumns().add(colBtn);
    }


   /* private void addButtonToTable() {

        TableColumn<DeliveryVO, String> colBtn = new TableColumn("승인유무");

        Callback<TableColumn<DeliveryVO, String>, TableCell<DeliveryVO, String>> cellFactory = new Callback<TableColumn<DeliveryVO, String>, TableCell<DeliveryVO, String>>() {
            @Override
            public TableCell<DeliveryVO, String> call(final TableColumn<DeliveryVO, String> param) {
                final TableCell<DeliveryVO, String> cell = new TableCell<DeliveryVO, String>() {

                    private final JFXToggleButton btn = new JFXToggleButton();



                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tv_Owner.getColumns().add(colBtn);
    }*/

}
