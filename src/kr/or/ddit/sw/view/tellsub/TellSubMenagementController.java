
package kr.or.ddit.sw.view.tellsub;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import kr.or.ddit.sw.service.tellsub.ITellSubService;
import kr.or.ddit.sw.service.tellsubmem.ITellsubMemService;
import kr.or.ddit.sw.vo.tellsub.TellSubVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TellSubMenagementController implements Initializable {


    public TableColumn titletc;
    public TableColumn indextc;
    public TableColumn nametc;
    public TableColumn sysdatetc;
    public TableColumn yesorno;


    public TableView table;
    private Registry reg;
    private ITellSubService itell;


    //게시물 번호
    int cnt_de = 0;
    int cnt_de2 = 0;


    public static String get_tellsub_no = "";
    private ArrayList<TellSubVO> list = new ArrayList<>();
    private static ObservableList<TellSubVO> allTableData;


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

        indextc.setCellValueFactory(new PropertyValueFactory<>("Tellsub_no"));
        nametc.setCellValueFactory(new PropertyValueFactory<>("Mem_id"));
        titletc.setCellValueFactory(new PropertyValueFactory<>("Tellsub_name"));
        sysdatetc.setCellValueFactory(new PropertyValueFactory<>("Tellsub_visitdate"));
        yesorno.setCellValueFactory(new PropertyValueFactory<>("Tellsub_mem_chk"));

        allTableData = FXCollections.observableArrayList();

        allTableData.setAll(list);
        table.setItems(allTableData);

        addButtonToTable();

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
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("TellSubMenagementSelect.fxml"));
                                parent = loader.load();
                                TellSubMenagementSelectController tc = loader.getController();
                                tc.initData((TellSubVO) table.getSelectionModel().getSelectedItem());
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

        table.getColumns().add(colBtn);
    }
}

