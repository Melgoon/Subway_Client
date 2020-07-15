package kr.or.ddit.sw.service.chat;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatServer extends Remote {
	
	/**
	 * 클라이언트 객체를 서버에 등록하기 위한 메서드
	 * @param client
	 * @param name
	 * @throws RemoteException
	 */
	public void addClient(ChatClient client, String name) throws RemoteException;
	
	/**
	 * 클라이언트를 제거(접속해제)하기 위한 메서드
	 * @param client
	 * @param name
	 * @throws RemoteException
	 */
	public void disconnect(ChatClient client, String name) throws RemoteException;

	/**
	 * 클라이언트에서 서버쪽으로 메시지 보내기 위한 메서드
	 * @param msg
	 * @param nickName
	 * @throws RemoteException
	 */
	public void say(String msg, String nickName) throws RemoteException;

}
