package kr.or.ddit.sw.service.honeycombi;

import kr.or.ddit.sw.vo.honeycombi.HoneyCombiVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IHoneyCombiService extends Remote {
    public List<HoneyCombiVO> selectAllHoney() throws RemoteException;

    public List<HoneyCombiVO> selectHoney(String menu) throws RemoteException;

    public Object insertHoney(HoneyCombiVO honeyCombiVO) throws RemoteException;

    public HoneyCombiVO selectHoneyBtn(String name) throws RemoteException;

}
