package kr.or.ddit.sw.service.owner;

import kr.or.ddit.sw.vo.owner.OwnerVO;
import kr.or.ddit.sw.vo.owner.TempOwnerVO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IOwnerService extends Remote {
    //오너 회원가입 인터페이스
    public Object insertowner(OwnerVO vo) throws RemoteException;


    //오너 로그인 인터페이스
    public OwnerVO owerlogin(TempOwnerVO tempOwnerVO) throws RemoteException;
}
