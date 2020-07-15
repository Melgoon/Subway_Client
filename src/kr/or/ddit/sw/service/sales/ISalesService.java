package kr.or.ddit.sw.service.sales;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISalesService extends Remote {
    public int deliveryNo(String jiJum) throws RemoteException;
    public int deliveryPr(String jiJum) throws RemoteException;
    public int deliveryChatNo(String jiJum, int chart_no) throws RemoteException;
    public String deliveryChatPr(String jiJum, int chart_no) throws RemoteException;


    public int reservationNo(String jiJum) throws RemoteException;
    public int reservationPr(String jiJum) throws RemoteException;
    public int reservationChatNo(String jiJum, int chart_no) throws RemoteException;
    public String reservationChatPr(String jiJum, int chart_no) throws RemoteException;
}
