package kr.or.ddit.sw.service.menuintro;

import kr.or.ddit.sw.vo.prod.ProdVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IMenuIntroService extends Remote {
    public List<ProdVO> selectAll() throws RemoteException;
    public List<ProdVO> selectKate(int kate) throws RemoteException;
}
