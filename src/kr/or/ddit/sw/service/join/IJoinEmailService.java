package kr.or.ddit.sw.service.join;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IJoinEmailService extends Remote {
    public void emailCheck(String email, Integer num) throws RemoteException;
}
