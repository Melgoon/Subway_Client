package kr.or.ddit.sw.service.join;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IJoinAdrService extends Remote {
    public void searchAdr() throws RemoteException;
}
