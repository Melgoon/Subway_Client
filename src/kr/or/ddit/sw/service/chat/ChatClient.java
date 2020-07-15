package kr.or.ddit.sw.service.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatClient extends Remote {
	
	/**
	 * RMI서버에서 각 클라이언트에 메시지를 전송해 주기 위해 필요한 메서드
	 * @param msg
	 * @throws RemoteException
	 */
	public void printMessage(String msg) throws RemoteException;

}
