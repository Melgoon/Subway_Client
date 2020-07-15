package kr.or.ddit.sw.view.tellsub;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import kr.or.ddit.sw.service.tellsub.ITellSubService;
import kr.or.ddit.sw.service.tellsubmem.ITellsubMemService;
import kr.or.ddit.sw.vo.tellsub.TellSubMemVO;
import kr.or.ddit.sw.vo.tellsub.TellSubVO;


import java.io.IOException;
import java.net.URL;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class TellSubController implements Initializable {


    public JFXButton deletebtn;
    public JFXButton insertbtn;
    public TableView<TellSubVO> Table;
    public TableColumn<TellSubVO, Integer> Indextc;
    public TableColumn<TellSubVO, String> tellname;
    public TableColumn<TellSubVO, Date> sysdatetc;

    public TableColumn shop;
    public Pagination pn;
    public TableColumn<TellSubVO, String> yesorno;

    private Registry reg;
    private ITellSubService itell;
    private ITellsubMemService itellmem;


    public static String get_tellsub_no = "";
    private ArrayList<TellSubVO> list = new ArrayList<>();
    private ObservableList<TellSubVO> allTableData, currentPageData;
    private int from, to, itemsForPage;
    public static TellSubController contell;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            itell = (ITellSubService) reg.lookup("tell");

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        try {
            list = (ArrayList<TellSubVO>) itell.selectAllTell(get_tellsub_no);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Indextc.setCellValueFactory(new PropertyValueFactory<>("Tellsub_no"));
        tellname.setCellValueFactory(new PropertyValueFactory<>("Tellsub_name"));
        sysdatetc.setCellValueFactory(new PropertyValueFactory<>("Tellsub_visitdate"));
        shop.setCellValueFactory(new PropertyValueFactory<>("Tellsub_jijum"));
        yesorno.setCellValueFactory(new PropertyValueFactory<>("Tellsub_mem_chk"));

        

        allTableData = FXCollections.observableArrayList();

        allTableData.setAll(list);
        Table.setItems(allTableData);


        loadData();
        addButtonToTable();

        insertbtn.setOnAction(event -> {
            Stage dialog = new Stage(StageStyle.UTILITY);
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("텔 서브웨이 등록");

            try {
                Parent parent = FXMLLoader.load(getClass().getResource("TellSubInsert.fxml"));
                Scene scene = new Scene(parent);
                dialog.setScene(scene);
                dialog.setResizable(false);
                dialog.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        });
        //삭제
        Table.setOnMouseClicked(event -> {

            deletebtn.setOnAction(event1 -> {
                System.out.println("게시물 삭제 테스트");


                int cnt = 0;
                TellSubVO tv = Table.getSelectionModel().getSelectedItem();


                try {
                    cnt = itell.deleteTell(tv);


                } catch (RemoteException e) {
                    e.printStackTrace();
                    System.out.println("정말 맞아?");
                }
                if (cnt != 0) {
                    allTableData.remove(Table.getSelectionModel().getSelectedIndex());

                    try {
                        list = (ArrayList<TellSubVO>) itell.selectAllTell(get_tellsub_no);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    allTableData.setAll(list);

                    Table.setItems(allTableData);
                }
            });

        });

        contell = this;
    }

    private void addButtonToTable() {

        TableColumn<TellSubVO, String> colBtn = new TableColumn("상세보기");

        Callback<TableColumn<TellSubVO, String>, TableCell<TellSubVO, String>> cellFactory = new Callback<TableColumn<TellSubVO, String>, TableCell<TellSubVO, String>>() {
            @Override
            public TableCell<TellSubVO, String> call(final TableColumn<TellSubVO, String> param) {
                final TableCell<TellSubVO, String> cell = new TableCell<TellSubVO, String>() {

                    private final JFXButton btn = new JFXButton("상세보기");

                    {
                        btn.setOnAction(event -> {

                            Stage dialog = new Stage(StageStyle.UTILITY);
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.setTitle("텔 서브웨이 문의");

                            Parent parent = null;
                            try {
                                //parent = new FXMLLoader().load(getClass().getResource("TellSubwayselect.fxml"));
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("TellSubwayselect.fxml"));
                                parent = loader.load();
                                TellsubSelectController tc = loader.getController();
                                tc.initData(Table.getSelectionModel().getSelectedItem());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }

                            Scene scene = new Scene(parent);

                            dialog.setScene(scene);
                            dialog.setResizable(false);
                            dialog.show();
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

        Table.getColumns().add(colBtn);
    }


    public void loadData() {
        try {
            allTableData = FXCollections.observableArrayList(itell.selectAllTell(get_tellsub_no));

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
                    Table.setItems(getTableViewData(from, to));
                    return Table;
                }

                private ObservableList<TellSubVO> getTableViewData(int from, int to) {
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
}