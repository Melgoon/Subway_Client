package kr.or.ddit.sw.service.ordertable;

import kr.or.ddit.sw.vo.foodmtr.FoodMtrVO;
import kr.or.ddit.sw.vo.ordertable.OrderVO;
import kr.or.ddit.sw.vo.prod.CombiVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IOrderTableService extends Remote {
    public List<FoodMtrVO> select(int mtr_div) throws RemoteException;
    public Object insert(CombiVO vo) throws RemoteException;

    public List<OrderVO> orderselect() throws RemoteException;
}
