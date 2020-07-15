package kr.or.ddit.sw.view.orderhis;

import com.sun.org.apache.xpath.internal.operations.Or;
import kr.or.ddit.sw.service.delivery.IDeliveryService;
import kr.or.ddit.sw.service.orderhis.IOrderHisService;
import kr.or.ddit.sw.service.pay.IPayService;
import kr.or.ddit.sw.service.reservation.IReservationService;
import kr.or.ddit.sw.vo.orderhis.OrderHisVO;
import kr.or.ddit.sw.vo.ordertable.DeliveryVO;
import kr.or.ddit.sw.vo.ordertable.ReservationVO;
import kr.or.ddit.sw.vo.pay.PayVO;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class OrderHisInsert {
    public static void main(String[] args) throws RemoteException {
        IPayService service = null;
        Registry registry = null;

        //필요한 정보를 뽑아서 orderhis테이블에 넣기위해서 변수를 선언해준다.
        String br;
        String name;
        String time;
        String price;

        try {
            registry = LocateRegistry.getRegistry("localhost", 7774);
            service = (IPayService) registry.lookup("pay");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        //pay테이블에서 정보를 가져온다.
        PayVO vo = service.payvo();

        //배달이 아니면 0 맞다면 0이상이기 때문에 둘중하나는 0이거나 0보다 크다 따라서 다음과 같은 조건으로
        //배달 여부를 판단하여 배달이면 Deli table 아니면 Reservation 테이블에 접근하여 정보를 가져온다.
        if (vo.getDeli_no() > 0) {
            br = "배달";
            DeliveryVO deliveryVO = null;
            IDeliveryService iDeliveryService = null;
            try {
                iDeliveryService = (IDeliveryService) registry.lookup("delivery");
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
            deliveryVO = iDeliveryService.select(vo.getDeli_no());
            System.out.println(deliveryVO.getDeli_jijum());
            name = deliveryVO.getDeli_name();
            time = deliveryVO.getDeli_time();
            price = deliveryVO.getDeli_price();
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
            System.out.println(reservationVO.getReser_name());
            name = reservationVO.getReser_name();
            time = reservationVO.getReser_time();
            price = reservationVO.getReser_price();
        }

        //위에서 뽑은 자료로 vo에 삽입한다.
        OrderHisVO orderHisVO = new OrderHisVO();
        orderHisVO.setOrder_His_Content(name);
        orderHisVO.setOrder_His_Br(br);
        orderHisVO.setOrder_His_Date(time);
        orderHisVO.setOrder_His_Price(price);
        orderHisVO.setPay_No(String.valueOf(vo.getPay_no()));

        //db에 삽입
        try {
            IOrderHisService iOrderHisService = (IOrderHisService) registry.lookup("orderhis");
            //Object obj = iOrderHisService.insertHis(orderHisVO);
//            if(obj == null){
//                System.out.println("입력성공");
//            }else{
//                System.out.println("실패");
//            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
