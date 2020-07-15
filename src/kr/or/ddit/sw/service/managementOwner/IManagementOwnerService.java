package kr.or.ddit.sw.service.managementOwner;

import kr.or.ddit.sw.vo.owner.OwnerVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IManagementOwnerService extends Remote {
    public List<OwnerVO> selectOwner() throws RemoteException;
    public int updateOwner(OwnerVO vo) throws RemoteException;
}
