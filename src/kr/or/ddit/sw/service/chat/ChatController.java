package kr.or.ddit.sw.service.chat;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.or.ddit.sw.service.login.LoginSession;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ChatController implements Initializable{

	public JFXButton SendBtn;
    public JFXButton closeBtn;
	public ImageView view;
	@FXML private TextArea taChatList;
	@FXML private TextField tfChat;
	
	private String nickName; // 대화명
	
	private ChatServer server; // 원격 RMI 채팅 서비스 
	private ChatClientImpl chatClientImpl; // 원격객체
	
	
	public void setChatClientImpl(ChatClientImpl chatClientImpl) {
		this.chatClientImpl = chatClientImpl;

	}

	/**
	 * 초기화 메서드
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SendBtn.toFront();
		closeBtn.toFront();
		tfChat.toFront();
		taChatList.toFront();
		Image image = new Image("file:src/images/ChatView.png");
		view.setImage(image);

	}
	
	/**
	 * 닉네임(대화명) 설정하기
	 * @param nickName
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * RMI 서버 설정하기
	 * @param server
	 */
	public void setServer(ChatServer server) {
		this.server = server;
	}

	/**
	 * 메시지 전송시 호출되는 메서드
	 * @param event
	 * @throws RemoteException
	 */
	@FXML
	public void sendMessage(ActionEvent event) throws RemoteException{
		String message = tfChat.getText();
		server.say(message, nickName);
		tfChat.setText("");
	}
	
	/**
	 * 텍스트 박스에 메시지 출력하기
	 * @param message
	 */
	public void showMsg(String message) {
		taChatList.appendText(message+ "\n");
	}
	
	/**
	 * RMI 서버 접속 해제
	 * @param event
	 * @throws RemoteException
	 */
	@FXML 
	public void disconnect(ActionEvent event) throws RemoteException {
		
		server.disconnect(chatClientImpl, nickName);
		
		chatClientImpl.getPrimaryStage().close(); // 창 닫기
		
	}
	
}
