package kr.or.ddit.sw.service.pay;

import kr.or.ddit.sw.vo.pay.PayVO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPayService extends Remote {
    Object Deliinsert(PayVO pv) throws RemoteException;
    Object Reserinsert(PayVO pv) throws RemoteException;
    public PayVO payvo() throws RemoteException;
}
