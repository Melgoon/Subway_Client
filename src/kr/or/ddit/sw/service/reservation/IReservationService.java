package kr.or.ddit.sw.service.reservation;

import kr.or.ddit.sw.vo.ordertable.ReservationVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IReservationService extends Remote {

    Object insert(ReservationVO rv) throws RemoteException;

    public List<ReservationVO> rserselect() throws RemoteException;

    public int updateReser(ReservationVO rv) throws RemoteException;

    public ReservationVO select(int reser_no) throws RemoteException;

    public List<ReservationVO> selectJijum(String jijum) throws RemoteException;
}
