package kr.or.ddit.sw.service.delivery;

import kr.or.ddit.sw.vo.ordertable.DeliveryVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IDeliveryService extends Remote {

    Object insert(DeliveryVO dv) throws RemoteException;

    public List<DeliveryVO> deliselect() throws RemoteException;

    public int updatedel(DeliveryVO dv) throws RemoteException;

    public DeliveryVO select(int deli_no) throws RemoteException;

    public List<DeliveryVO> selectJijum(String jijum) throws RemoteException;
}
