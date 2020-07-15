package kr.or.ddit.sw.service.orderhis;

import kr.or.ddit.sw.vo.orderhis.OrderHisVO;
import kr.or.ddit.sw.vo.ordertable.OrderVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IOrderHisService extends Remote {
    public List<OrderVO> selectid(String id) throws RemoteException;
}
