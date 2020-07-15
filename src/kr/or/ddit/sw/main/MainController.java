package kr.or.ddit.sw.main;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import kr.or.ddit.sw.vo.member.MemberVO;
import kr.or.ddit.sw.service.join.IJoinService;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class MainController{


    private Registry reg = LocateRegistry.getRegistry("localhost", 7774);
    private IJoinService ijoin = ijoin = (IJoinService) reg.lookup("join");;

    public MainController() throws RemoteException, NotBoundException {
    }

}
