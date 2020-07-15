package kr.or.ddit.sw.view.pay;

import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.vo.member.MemberVO;

import java.net.URL;
import java.util.ResourceBundle;

public class KakaoController implements Initializable {
    public WebView kakao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebEngine webEngine = kakao.getEngine();
        MemberVO vo = LoginSession.memberSession;
        webEngine.load("http://localhost:8562/kakao.jsp?name="+vo.getMEM_ID()+"&email="+vo.getMEM_EMAIL()+"&phone="+vo.getMEM_HP()+"&address="+vo.getMEM_ADDR()+"&totalPrice="+LoginSession.price);

    }
}
