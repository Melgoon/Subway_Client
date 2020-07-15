package kr.or.ddit.sw.view.memup;

import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.join.IJoinService;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.vo.member.MemberVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class MemberUpdateController implements Initializable {


    @FXML
    private ImageView MainView;
    @FXML
    private JFXPasswordField passTf;
    @FXML
    private JFXPasswordField passChkTf;
    @FXML
    private JFXDatePicker birDp;
    @FXML
    private JFXRadioButton radioM;
    @FXML
    private JFXRadioButton radioG;
    @FXML
    private JFXTextField emailTf;
    @FXML
    private JFXComboBox emailCv;
    @FXML
    private JFXTextField addrTf;
    @FXML
    private JFXButton cancleBtn;
    @FXML
    private JFXButton insertBtn;
    @FXML
    private JFXTextField hpTf1;
    @FXML
    private ImageView FileimgV;
    @FXML
    private Label labelName;

    private Registry reg;
    private IJoinService regJoin;

    boolean pwChk = false;
    private MemberVO mv;
    @FXML
    private Label passLabel;
    private String emailVal;
    int a;
    boolean fu=false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //LoginSession.session= null;

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            regJoin = (IJoinService) reg.lookup("join");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        Image mainV = new Image("file:src/images/memberUpdateImg.png");
        MainView.setImage(mainV);

        //라벨이름
        labelName.setText(LoginSession.memberSession.getMEM_ID()+"님");
        //

        ObservableList<String> cvData = FXCollections.
                observableArrayList( "nate.com", "naver.com", "gmail.com", "daum.net");

        emailCv.setPromptText("이메일을 입력해주세요!");
        emailCv.setItems(cvData);

        ToggleGroup group = new ToggleGroup();

        radioG.setToggleGroup(group);
        radioM.setToggleGroup(group);


        //TODO 패스워드 불일치 .
        passTf.setOnKeyPressed(e -> { // 패스워드 텍스트필드에 키보드를 입력할 경우
            if (!(passTf.getText().equals(""))) {
                CheckPassword();
            }
        });
        passChkTf.setOnKeyPressed(e -> { // 패스워드 중복 체크 텍스트 필드에 키보드를 입력할 경우
            if (!(passChkTf.getText().equals(""))) {
                CheckPassword();
            }
        });
        /**
         * 도움준 사람 : 이용춘
         */
        passTf.setOnKeyReleased(event -> {
            CheckPassword();
           // System.out.println(passTf.getText());
        });

        passChkTf.setOnKeyReleased(event -> {
            CheckPassword();
         //   System.out.println(passChkTf.getText());
        });
        mv = new MemberVO();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("MM-dd");
        //수정 버튼 입력
        insertBtn.setOnAction(e -> {
            String bir = String.valueOf(birDp.getValue());
            String last_form_date = null;
            try {
              Date new_form_date = dateFormat.parse(bir);
              last_form_date = dateFormat2.format(new_form_date);
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            System.out.println("생일태스트"+last_form_date);

            fu = RegularPass(passChkTf.getText().trim());
            if (fu==false){
               errMsg("비밀번호 오류","비밀번호 정규식","대소문자 구분 숫자 특수문자  조합 9 ~ 12 자리");
               return;
           }

            if (pwChk == false) {
                errMsg("오류", "비밀번호 불일치", "비밀번호가 일치하지 않습니다.");
                return;
            }
            if (emailTf.getText().isEmpty()) {
                errMsg("이메일 오류", "이메일 입력", "이메일을 입력해주세요");
                return;
            }
            if (addrTf.getText().isEmpty()) {
                errMsg("주소 오류", "주소 입력", "주소를 입력해주세요");
                return;
            }
            if (hpTf1.getText().isEmpty()) {
                errMsg("연락처 오류", "연락처 입력", "연락처를 입력해주세요.");
                return;
            }


            //emailVal = emailTf.getText();
           // emailVal += emailCv.getSelectionModel().getSelectedItem().toString();

            //System.out.println(emailVal);
            mv.setMEM_EMAIL(emailTf.getText()+emailCv.getSelectionModel().getSelectedItem());
            mv.setMEM_ID(LoginSession.memberSession.getMEM_ID());
            mv.setMEM_PW(passChkTf.getText());
            mv.setMEM_ADDR(addrTf.getText());
            mv.setMEM_HP(hpTf1.getText());
            mv.setMEM_BIRTH(last_form_date);

            mv.setMEM_GENDER(String.valueOf(group.getSelectedToggle().getUserData().toString()));

            try {

                //System.out.println(vo);
                a = regJoin.updateMember(mv);
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }

            infoMsg("수정", "수정 성공", "수정이 완료되었습니다.");

        });

        cancleBtn.setOnAction(e -> {
          emailTf.clear();
          emailCv.setValue(0);
          passTf.clear();
            passChkTf.clear();
            addrTf.clear();
            hpTf1.clear();
            //
        });


    }

    public void CheckPassword() { // 비밀번호와 비밀번호 확인란에 있는 값이 일치하는지 확인
        if (!(passTf.getText().trim().equals(passChkTf.getText().trim()))) {
            passLabel.setTextFill(Color.RED);
            passLabel.setText("비밀번호가 일치하지 않습니다.");
            pwChk = false;
        } else {
            passLabel.setTextFill(Color.BLUE);
            passLabel.setText("비밀번호가 일치합니다.");
            pwChk = true;
        }
    }

    public void infoMsg(String title, String headerText, String msg) {
        Alert errAlert = new Alert(Alert.AlertType.INFORMATION);
        errAlert.setTitle(title);
        errAlert.setHeaderText(headerText);
        errAlert.setContentText(msg);
        errAlert.showAndWait();
    }


    public void errMsg(String title, String headerText, String msg) {
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle(title);
        errAlert.setHeaderText(headerText);
        errAlert.setContentText(msg);
        errAlert.showAndWait();
    }


    public boolean RegularPass(String passTf) {
        boolean chk = true;
        String sibar = passTf.trim();

        // 대소문자 구분 숫자 특수문자  조합 9 ~ 12 자리
        String pwPattern = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,}$";

        Boolean tt = Pattern.matches(pwPattern,sibar);

        if (tt == false){
            chk =false;
        }

        return chk;
    }

}
