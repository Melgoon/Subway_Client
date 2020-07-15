package kr.or.ddit.sw.view.orderhis;


import javafx.scene.control.TableColumn;
import kr.or.ddit.sw.service.delivery.IDeliveryService;
import kr.or.ddit.sw.service.pay.IPayService;
import kr.or.ddit.sw.service.reservation.IReservationService;

import kr.or.ddit.sw.vo.ordertable.DeliveryVO;
import kr.or.ddit.sw.vo.ordertable.ReservationVO;
import kr.or.ddit.sw.vo.pay.PayVO;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OrderHisController {

    public TableColumn num;
    public TableColumn text;
    public TableColumn price;
    public TableColumn br;

    public static void main(String[] args) throws RemoteException {
        IPayService service = null;
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("localhost", 7774);
            service = (IPayService) registry.lookup("pay");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        PayVO vo = service.payvo();
        String br = null;
        if (vo.getDeli_no()>0) {
            br = "배달";
            DeliveryVO deliveryVO = null;
            IDeliveryService iDeliveryService = null;
            try {
                iDeliveryService = (IDeliveryService) registry.lookup("delivery");
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
            deliveryVO = iDeliveryService.select(vo.getDeli_no());
        } else {
            br = "예약";
            ReservationVO reservationVO = null;
            IReservationService iReservationService = null;
            try {
                iReservationService = (IReservationService) registry.lookup("reservation");
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
            reservationVO = iReservationService.select(vo.getReser_no());
        }





    }
}
