package kr.or.ddit.sw.view.orderhis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kr.or.ddit.sw.service.delivery.IDeliveryService;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.reservation.IReservationService;
import kr.or.ddit.sw.vo.orderhis.AdminHisVO;
import kr.or.ddit.sw.vo.ordertable.DeliveryVO;
import kr.or.ddit.sw.vo.ordertable.ReservationVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminhisController implements Initializable {

    public TableColumn name;
    public TableColumn price;
    public TableColumn date;
    public TableColumn br;
    public TableView tableView;
    private Registry registry;
    private IReservationService iReservationService;
    private IDeliveryService iDeliveryService;
    private ObservableList observableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            registry = LocateRegistry.getRegistry("localhost",7774  );
            iReservationService = (IReservationService) registry.lookup("reservation");
            iDeliveryService = (IDeliveryService) registry.lookup("delivery");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        List<ReservationVO> reservationVOList = null;
        List<DeliveryVO> deliveryVOList = null;

        try {
            reservationVOList = iReservationService.selectJijum(LoginSession.ownerSession.getOwner_jijum());
            deliveryVOList = iDeliveryService.selectJijum(LoginSession.ownerSession.getOwner_jijum());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        AdminHisVO vo = null;
        List<AdminHisVO> adminHisVOS = new ArrayList<>();

        for(ReservationVO reservationVO : reservationVOList){
            vo = new AdminHisVO();
            vo.setName(reservationVO.getReser_name());
            vo.setPrice(reservationVO.getReser_price());
            vo.setDate(reservationVO.getReser_jijum());
            vo.setBr("예약");
            System.out.println(vo.getName());
            adminHisVOS.add(vo);
        }

        for(DeliveryVO deliveryVO : deliveryVOList){
            vo = new AdminHisVO();
            vo.setName(deliveryVO.getDeli_name());
            vo.setPrice(deliveryVO.getDeli_price());
            vo.setDate(deliveryVO.getDeli_jijum());
            vo.setBr("배달");
            System.out.println(vo.getName());
            adminHisVOS.add(vo);
        }

        observableList = FXCollections.observableList(adminHisVOS);
        tableView.setItems(observableList);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        br.setCellValueFactory(new PropertyValueFactory<>("br"));




    }
}
