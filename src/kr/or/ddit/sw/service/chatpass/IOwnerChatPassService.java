package kr.or.ddit.sw.service.chatpass;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IOwnerChatPassService extends Remote {

    public String OwnerPassChk(int code) throws RemoteException;
}
