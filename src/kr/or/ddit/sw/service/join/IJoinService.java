package kr.or.ddit.sw.service.join;

import kr.or.ddit.sw.vo.member.MemberVO;
import kr.or.ddit.sw.vo.owner.OwnerVO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IJoinService extends Remote {
    Object insertMember(MemberVO mv) throws RemoteException;

    Object insertOwner(OwnerVO own) throws RemoteException;
    public int updateMember(MemberVO mv) throws RemoteException;
}
