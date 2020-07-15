package kr.or.ddit.sw.service.registFoodMtr;

import kr.or.ddit.sw.vo.foodmtr.FoodMtrVO;
import kr.or.ddit.sw.vo.foodmtr.MtrPicVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRegistFoodMtrService extends Remote {
    public int insertFoodMtr(FoodMtrVO vo) throws RemoteException;

    public int insertFoodMtrPic(MtrPicVO vo) throws RemoteException;

    public List<FoodMtrVO> selectFoodMtr() throws RemoteException;

    public int deleteFoodMtr(FoodMtrVO vo) throws RemoteException;
}
