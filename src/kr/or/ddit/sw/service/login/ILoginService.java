package kr.or.ddit.sw.service.login;

import kr.or.ddit.sw.vo.member.MemberVO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ILoginService extends Remote {
    public MemberVO login(String id) throws RemoteException;
}
