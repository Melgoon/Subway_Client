package kr.or.ddit.sw.view.orderhis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.orderhis.IOrderHisService;
import kr.or.ddit.sw.vo.ordertable.OrderVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

public class OrderHisCon implements Initializable {

    public TableColumn num;
    public TableColumn text;
    public TableColumn price;
    public TableView tableview;
    public TableColumn date;
    private Registry registry;
    private IOrderHisService service;
    private ObservableList observableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            registry = LocateRegistry.getRegistry("localhost", 7774);
            service = (IOrderHisService) registry.lookup("orderhis");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        List<OrderVO> list = null;
        try {
            list = service.selectid(LoginSession.memberSession.getMEM_ID());
            observableList = FXCollections.observableList(list);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        tableview.setItems(observableList);
        num.setCellValueFactory(new PropertyValueFactory<>("order_no"));
        text.setCellValueFactory(new PropertyValueFactory<>("order_cont"));
        price.setCellValueFactory(new PropertyValueFactory<>("order_price"));
        date.setCellValueFactory(new PropertyValueFactory<>("order_date"));


    }
}
