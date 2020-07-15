package kr.or.ddit.sw.service.chat;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

@SuppressWarnings("serial")
public class ChatClientImpl extends UnicastRemoteObject implements ChatClient{

   private ChatServer server; // 원격 RMI 채팅 서비스 
   
   private ChatController controller; // 채팅화면 제어용 컨트롤러
   
   private Stage primaryStage;

   private static String name;


	public ChatClientImpl(String name, ChatController controller, Stage primaryStage) throws RemoteException, Exception {

		ChatClientImpl.name = name;
		
		this.controller = controller;
		
		controller.setChatClientImpl(this);
		
		this.setPrimaryStage(primaryStage);
		
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	/**
	 * 서버에 접속하기
	 * @throws Exception
	 */
	public void connect() throws Exception {
		
		
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
		
		
		//채팅시 주석 스와핑
		Registry reg = LocateRegistry.getRegistry("192.168.206.55",7774);
		//Registry reg = LocateRegistry.getRegistry("들어갈IP주소", 8888);


		////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
		
		
		
		
		//System.setProperty("java.rmi.server.hostname","localhost");

		server = (ChatServer) reg.lookup("RMIChatServer");

		server.addClient(this, name); // 현재 객체를 RMI Chat 서버에 등록
		
		this.controller.setNickName(name); // 닉네임(대화명) 설정하기
		
		this.controller.setServer(server); // RMI 서버 설정
		
	}
	
	/**
	 * RMI서버에서 호출될 메서드
	 */
	public void printMessage(String msg) throws RemoteException {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				controller.showMsg(msg); // 컨트롤러객체를 이용하여 대화창에 메시지 출력하기
			}
		});

	}
}
