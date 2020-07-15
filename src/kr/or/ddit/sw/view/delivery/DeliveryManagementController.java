package kr.or.ddit.sw.view.delivery;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import kr.or.ddit.sw.service.delivery.IDeliveryService;
import kr.or.ddit.sw.vo.ordertable.DeliveryVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DeliveryManagementController implements Initializable {


    public TableColumn<DeliveryVO, Integer> index;
    public TableColumn<DeliveryVO, String> delihis;
    public TableColumn<DeliveryVO, String> delimenu;
    public TableColumn<DeliveryVO, Integer> delitime;
    public TableColumn<DeliveryVO, String> deliadr;
    public Pagination pn;
    public TableView<DeliveryVO> deli;

    private Registry reg;
    private IDeliveryService ids;
    private ArrayList<DeliveryVO> list = new ArrayList<>();
    private ObservableList<DeliveryVO> allTableData, currentPageData;
    private int from, to, itemsForPage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            ids = (IDeliveryService) reg.lookup("delivery");

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        try {
            list = (ArrayList<DeliveryVO>) ids.deliselect();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        index.setCellValueFactory(new PropertyValueFactory<>("deli_no"));
        delihis.setCellValueFactory(new PropertyValueFactory<>("deli_status"));
        delimenu.setCellValueFactory(new PropertyValueFactory<>("deli_name"));
        delitime.setCellValueFactory(new PropertyValueFactory<>("deli_time"));
        deliadr.setCellValueFactory(new PropertyValueFactory<>("deli_adr"));

        allTableData = FXCollections.observableArrayList();

        allTableData.setAll(list);
        deli.setItems(allTableData);

        loadData();
        addButtonToTable();



    }

    public void loadData() {
        try {
            allTableData = FXCollections.observableArrayList(ids.deliselect());

            itemsForPage = 13; // 한페이지당 보여줄 항목 수 설정
            int totalDataCnt = allTableData.size();
            int totalPageCnt = totalDataCnt % itemsForPage == 0 ? totalDataCnt / itemsForPage : totalDataCnt / itemsForPage + 1;
            //페이지 항목 수와 전체 페이지 수를 나눠서 똑 떨어지면 그 해당 숫자만큼의 페이지를 보여주고, 나머지가 생기면 페이지를 하나 더 생성해서 항목을 출력

            pn.setPageCount(totalPageCnt); // 전체 페이지수 설정

            // 방법1 Callback타입의 익명객체 생성
            pn.setPageFactory(new Callback<Integer, Node>() {

                @Override
                public Node call(Integer pageIndex) {
                    from = pageIndex * itemsForPage;
                    to = from + itemsForPage - 1;
                    deli.setItems(getTableViewData(from, to));
                    return deli;
                }

                private ObservableList<DeliveryVO> getTableViewData(int from, int to) {
                    currentPageData = FXCollections.observableArrayList();
                    int totSize = allTableData.size();
                    for (int i = from; i <= to && i < totSize; i++) {
                        currentPageData.add(allTableData.get(i));
                    }

                    return currentPageData;
                }

            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    private void addButtonToTable() {

        TableColumn<DeliveryVO, String> colBtn = new TableColumn("상세보기");

        Callback<TableColumn<DeliveryVO, String>, TableCell<DeliveryVO, String>> cellFactory = new Callback<TableColumn<DeliveryVO, String>, TableCell<DeliveryVO, String>>() {
            @Override
            public TableCell<DeliveryVO, String> call(final TableColumn<DeliveryVO, String> param) {
                final TableCell<DeliveryVO, String> cell = new TableCell<DeliveryVO, String>() {

                    private final JFXButton btn = new JFXButton("상세보기");

                    {
                        if(delihis.equals("접수됨")) {
                            btn.setDisable(true);
                        }
                        else{
                            btn.setOnAction(event -> {
                                Stage dialog = new Stage(StageStyle.UTILITY);
                                dialog.initModality(Modality.APPLICATION_MODAL);
                                dialog.setTitle("텔 서브웨이 문의");

                                Parent parent = null;
                                try {
                                    //parent = new FXMLLoader().load(getClass().getResource("TellSubwayselect.fxml"));
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("deliselect.fxml"));
                                    parent = loader.load();
                                    DeliveryManagementSelectController tc = loader.getController();
                                    tc.initData(deli.getSelectionModel().getSelectedItem());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }

                                Scene scene = new Scene(parent);

                                dialog.setScene(scene);
                                dialog.setResizable(false);
                                dialog.show();
                            });
                        }

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

        deli.getColumns().add(colBtn);
    }
}
