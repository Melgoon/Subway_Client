package kr.or.ddit.sw.view.join;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.join.*;
import kr.or.ddit.sw.service.stamp.IStampService;
import kr.or.ddit.sw.vo.member.MemberVO;
import kr.or.ddit.sw.vo.notice.NoticeVO;
import kr.or.ddit.sw.vo.review.ReviewVO;
import kr.or.ddit.sw.vo.stamp.StampHisVO;
import netscape.javascript.JSObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Member;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class JoinController implements Initializable {
    public ImageView imgView;
    public JFXTextField join_id;
    public JFXTextField join_name;
    public JFXPasswordField join_pw;
    public JFXPasswordField join_repw;
    public JFXDatePicker join_birth;
    public JFXTextField join_email;
    public JFXTextField join_hp;
    public JFXTextField join_adr;
    public JFXButton join_id_chk;

    public JFXButton join_cancel;
    public JFXButton join_confirm;
    public JFXButton join_email_chk_btn;
    public JFXTextField join_email_chk;
    public JFXButton join_email_chk1;

    public ToggleGroup join_gender;
    public ToggleGroup join_age;
    public JFXButton join_adr_src;
    public ImageView fx_captcha;
    public JFXButton join_captcha_re;
    public JFXTextField join_captcha_num;
    public JFXButton join_captcha_re1;
    public Label passChkLb;

    private Registry reg;
    private IJoinService join;
    private IIdCheckService idchk;
    private IJoinEmailService emailService;
    private IJoinAdrService adrService;
    private ICaptchKeyService captchaKeyService;
    private ICaptchaImgService captchaImgService;
    private ICaptchaChkService captchaChkService;
    private IStampService stamp; // hb. stamp
    private JSObject javascriptConnector;
    private JavaConnector javaConnector = new JavaConnector();
    Stage stage;

    static String[] temp = new String[2];
    int count = 0;
    String cap;
    String cap1;
    int chknum = 0;

    public void error(String header, String context) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("오류");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
        return;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("file:src/images/SignUp3.png");
        imgView.setImage(img);

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        captchaKeyService = CaptchKeyServiceImpl.getInstance();
        cap = captchaKeyService.check();
        cap = cap.substring(8, cap.length() - 2);

        captchaImgService = CaptchaImgServiceImpl.getInstance();
        String capName = captchaImgService.CaptchaImg(cap);

        System.out.println(capName);
        Image captcha = new Image("file:src/kr/or/ddit/sw/view/join/captcha/" + capName + ".jpg");
        fx_captcha.setImage(captcha);


        join_captcha_re.setOnAction(event -> {

            cap1 = captchaKeyService.check();
            cap1 = cap1.substring(8, cap1.length() - 2);
            System.out.println(cap1);
            captchaImgService = CaptchaImgServiceImpl.getInstance();
            String capName1 = captchaImgService.CaptchaImg(cap1);

            System.out.println(capName1);
            Image captcha1 = new Image("file:src/kr/or/ddit/sw/view/join/captcha/" + capName1 + ".jpg");
            fx_captcha.setImage(captcha1);
            count++;
        });

        join_captcha_re1.setOnAction(event -> {
            captchaChkService = CaptchaChkServiceImpl.getInstance();
            String chknum = join_captcha_num.getText();
            System.out.println(chknum);
            String response;
            if (count == 0) {
                response = captchaChkService.check(chknum, cap);
                System.out.println("0");
            } else {
                response = captchaChkService.check(chknum, cap1);
                System.out.println("other");

            }
            response = response.substring(10, 11);
            if (response.equals("t")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("인증성공");
                alert.setHeaderText("캡차성공");
                alert.setContentText("당신은 로봇이 아닙니다.");
                alert.showAndWait();
                join_captcha_re.setDisable(true);
                join_captcha_num.setDisable(true);
                join_captcha_re1.setDisable(true);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("인증실패");
                alert.setHeaderText("캡차실패");
                alert.setContentText("다시 시도해주세요");
                alert.showAndWait();
                cap1 = captchaKeyService.check();
                cap1 = cap1.substring(8, cap1.length() - 2);
                System.out.println(cap1);
                captchaImgService = CaptchaImgServiceImpl.getInstance();
                String capName1 = captchaImgService.CaptchaImg(cap1);

                System.out.println(capName1);
                Image captcha1 = new Image("file:src/kr/or/ddit/sw/view/join/captcha/" + capName1 + ".jpg");
                fx_captcha.setImage(captcha1);
                count++;
            }
        });

        join_id_chk.setOnAction(event -> {
            String id = join_id.getText();
            Object obj = null;
            temp[0] = id;
            try {
                idchk = (IIdCheckService) reg.lookup("idcheck");
                obj = idchk.checkId(id);
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
            if (obj != null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("경고");
                alert.setHeaderText("중복되었습니다.");
                alert.setContentText("다른 아이디를 사용해주세요");
                alert.showAndWait();
                temp[1] = "N";
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("정보");
                alert.setHeaderText("중복되지 않았습니다.");
                alert.setContentText("사용해도 좋습니다.");
                alert.showAndWait();
                temp[1] = "Y";
            }
        });

        join_email_chk_btn.setOnAction(event -> {
            try {
                System.out.println("A");
                emailService = (IJoinEmailService) reg.lookup("emailService");
                chknum = (int) ((Math.random() * 5000) + 1111);
                emailService.emailCheck(join_email.getText(), chknum);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        });

        join_email_chk1.setOnAction(event -> {
            int chknum_user = Integer.parseInt(join_email_chk.getText());
            if (chknum != chknum_user) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("인증번호 불일치");
                alert.setContentText("재시도해주세요");
                alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("인증번호 일치");
                alert.setContentText("인증되었습니다.");
                alert.showAndWait();
                join_email.setDisable(true);
                join_email_chk.setDisable(true);
                join_email_chk1.setDisable(true);
                join_email_chk_btn.setDisable(true);
            }
        });
//비밀번호 랑 비밀번호 체크 확인
        join_pw.setOnKeyPressed(e -> { // 패스워드 텍스트필드에 키보드를 입력할 경우
            if (!(join_pw.getText().equals(""))) {
                CheckPassword();
            }
        });
        join_repw.setOnKeyPressed(e -> { // 패스워드 중복 체크 텍스트 필드에 키보드를 입력할 경우
            if (!(join_repw.getText().equals(""))) {
                CheckPassword();
            }
        });

        /**
         * 도움준 사람 : 이용춘
         */
        join_pw.setOnKeyReleased(event -> {
            CheckPassword();
            // System.out.println(passTf.getText());
        });

        join_repw.setOnKeyReleased(event -> {
            CheckPassword();
            //   System.out.println(passChkTf.getText());
        });


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("MM-dd");
        Calendar cal = Calendar.getInstance();

        join_confirm.setOnAction(event -> {
            String a = String.valueOf(join_birth.getValue());
            System.out.println("될거란말이야"+a);
            String last_form_date = null;
            try {
                Date new_form_date = dateFormat.parse(a);
                last_form_date = dateFormat2.format(new_form_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("a의값을 보여주자"+last_form_date);

            String tempid = temp[0];
            if (join_id.getText().equals(tempid) && temp[1].equals("Y")) {
                if (join_pw.getText().equals(join_repw.getText())) {
                    if (join_name.getText().length() > 0) {
                        if (join_birth.getValue() != null) {
                            if (join_age.getSelectedToggle() != null) {
                                if (join_gender.getSelectedToggle() != null) {
                                    if (join_email.isDisabled()) {
                                        if (join_hp.getText() != null) {
                                            if (join_adr.getText() != null) {
                                                if (join_captcha_num.isDisabled()) {
                                                    MemberVO vo = new MemberVO();
                                                    vo.setMEM_ID(join_id.getText());
                                                    vo.setMEM_NAME(join_name.getText());
                                                    vo.setMEM_PW(join_pw.getText());
                                                    //vo.setMEM_BIRTH(String.valueOf(join_birth.getValue()));
                                                    vo.setMEM_BIRTH(last_form_date);
                                                    //여기서 잘들어갔는지 확인
                                                    System.out.println("vo bir꺼냄"+vo.getMEM_BIRTH());
                                                    vo.setMEM_AGE(String.valueOf(join_age.getSelectedToggle().getUserData().toString()));
                                                    vo.setMEM_GENDER(String.valueOf(join_gender.getSelectedToggle().getUserData().toString()));
                                                    vo.setMEM_EMAIL(join_email.getText());
                                                    vo.setMEM_HP(join_hp.getText());
                                                    vo.setMEM_ADDR(join_adr.getText());
                                                    try {
                                                        join = (IJoinService) reg.lookup("join");
                                                        join.insertMember(vo);
                                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);

                                                        //start hb. stamp
                                                        StampHisVO shv = new StampHisVO();
                                                        shv.setMem_id(vo.getMEM_ID());
                                                        shv.setStamp_count(0);
                                                        stamp = (IStampService) reg.lookup("stamp");
                                                        stamp.insertStamp(shv);
                                                        //end hb. stamp

                                                        alert.setHeaderText("삽입 성공");
                                                        alert.setContentText("회원가입에 성공하였습니다.");
                                                        alert.showAndWait();
                                                        Stage stage = (Stage) join_captcha_num.getScene().getWindow();
                                                        stage.close();
                                                    } catch (RemoteException e) {
                                                        e.printStackTrace();
                                                    } catch (NotBoundException e) {
                                                        e.printStackTrace();
                                                    }

                                                } else {
                                                    error("CAPTCHA 오류", "CAPTCHA 인증을 진행해주세요");
                                                }
                                            } else {
                                                error("주소 입력", "주소를 입력해주세요");
                                            }
                                        } else {
                                            error("휴대폰 입력오류", "핸드폰 번호를 입력해주세요");
                                        }
                                    } else {
                                        error("이메일 인증오류", "이메일 인증을 진행해주세요");
                                    }
                                } else {
                                    error("성별 선택 오류", "성별을 입력해주세요");
                                }
                            } else {
                                error("연령대 선택 오류", "연령대를 입력해주세요");
                            }
                        } else {
                            error("생년월일 입력 오류", "생년월일을 입력해주세요");
                        }
                    } else {
                        error("이름입력 오류", "이름을 입력해주세요");
                    }
                } else {
                    error("비밀번호 오류", "비밀번호가 일치하지 않습니다.");
                }
            } else {
                error("아이디 인증 오류", "아이디 인증을 다시해주세요");
            }
        });


        join_adr_src.setOnAction(event -> {
            try {
                openAddrDialog();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        /*join_confirm.setOnAction(event -> {

                infoMsg("작업결과", "정상적으로 가입되었습니다.");


                Stage stage = (Stage) join_confirm.getScene().getWindow();
                stage.close();

                // 다시 로드하는 부분
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../login/Login.fxml"));

                try {
                    Pane root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage1 = new Stage();
                    stage1.setScene(scene);
                    stage1.show();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                return;

        });*/

        join_cancel.setOnAction(event -> {
            Stage stage = (Stage) join_cancel.getScene().getWindow();
            stage.close();
        });

    }


    private void infoMsg(String headerText, String msg) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("작업 결과");
        infoAlert.setHeaderText(headerText);
        infoAlert.setContentText(msg);
        infoAlert.showAndWait();
    }

    private void errMsg(String headerText, String msg) {
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle("오류");
        errAlert.setHeaderText(headerText);
        errAlert.setContentText(msg);
        errAlert.showAndWait();
    }

    //join_pw join_repw
    public void CheckPassword() { // 비밀번호와 비밀번호 확인란에 있는 값이 일치하는지 확인
        if (!(join_pw.getText().trim().equals(join_repw.getText().trim()))) {
            passChkLb.setTextFill(Color.RED);
            passChkLb.setText("비밀번호가 일치하지 않습니다.");
        } else {
            passChkLb.setTextFill(Color.BLUE);
            passChkLb.setText("비밀번호가 일치합니다.");
        }
    }

    public class JavaConnector {
        /**
         * called when the JS side wants a String to be converted.
         *
         * @param value the String to convert
         */
        public void toLowerCase(String value) {
            if (null != value) {
                javascriptConnector.call("showResult", value.toLowerCase());
                // call : 자바 스크립트 메소드를 호출합니다. JavaScript의 "this.methodName (args [0], args [1], ...)"과 같습니다.
            }
        }

        public void sendAddr(String addr, String extraAddr, String zonecode) {
            System.out.println("자바스크립트에서 보내온 메시지 출력 : " + addr);
            join_adr.setText(addr);
            stage.close(); // 창닫기


        }
    }

    private void openAddrDialog() throws IOException {

        URL url = new File("src/basic/webview/sample.html").toURI().toURL();

        WebView webView = new WebView(); // webView 객체 만들기
        final WebEngine webEngine = webView.getEngine(); // webView 객체를 Returns the WebEngine object.

        // set up : 설정
        // listener : 프로그램에서 2가지로 구분할수 있다
        //            1. 네트워크 프로그램(소켓)에서 소켓 서버의 역할은 연결을 받아주는 것
        //               이 연결을 받아주기 위해서 필요한 것이 바로 listen 함수이며, 말 그대로 listener 라고 불리우기도 한다.
        //               ※ socket 서버에서 listener 하는 친구
        //             2. 다른 의미의 listener 라고 하는 것은 특정 이벤트를 받기위한 기능 을 제공하는 것을 말한다.
        //               ※ 이벤트를 받기 위해서 대기하는 친구
        // set up the listener
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            // getLoadWorker : 로드 진행률을 추적하는 데 사용할 수있는 javafx.concurrent.Worker 객체를 반환합니다.
            // stateProperty : 현재 상태를 나타내는 ReadOnlyObjectProperty를 가져옵니다.
            // addListener : ObservableValue의 값이 변경 될 때마다 통지되는 ChangeListener를 추가합니다. 동일한 리스너가 두 번 이상 추가되면 두 번 이상 알림을받습니다. 즉, 고유성을 보장하기 위해 점검이 이루어지지 않습니다.
            //              동일한 실제 ChangeListener 인스턴스는 다른 ObservableValues에 대해 안전하게 등록 될 수 있습니다.
            //             ObservableValue는 리스너에 대한 강력한 참조를 저장하므로 리스너가 가비지 수집되지 않으며 메모리 누수가 발생할 수 있습니다. 사용 후 removeListener를 호출하여 리스너를 등록 취소하거나 WeakChangeListener 인스턴스를 사용하여이 상황을 피하는 것이 좋습니다.
            //              매개 변수 : listener 등록 할 리스너 Throws : NullPointerException-리스너가 널인 경우 : removeListener (ChangeListener)
            if (Worker.State.SUCCEEDED == newValue) { // 페이지 로딩이 정상으로 모두 읽혀진 경우...
                // set an interface object named 'javaConnector' in the web engine's page
                JSObject window = (JSObject) webEngine.executeScript("window"); // window 객체 가져오기
                window.setMember("javaConnector", javaConnector); // winddow 객체에 Java 객체 등록하기
                // setMember : JavaScript 객체의 명명 된 멤버를 설정합니다. JavaScript에서 "this.name = value"와 동일

                // get the Javascript connector object.
                //javascriptConnector = (JSObject) webEngine.executeScript("getJsConnector()");
                webEngine.executeScript("sample3_execDaumPostcode()");
                // executeScript : 현재 페이지의 컨텍스트(어떤정보의 모음)에서 스크립트를 실행합니다.
                //               context : 경계, 영역, Boundary 와 연관
                //                  즉, '어떤 영역, 경계를 구분하는 데이터의 모음' 이거나 '어떤 영역, 경계를 넘어갈때 전달해야 하는 데이터 모음'의 의미가 강하다는 뜻
                // ex) System.Security.SecurityContext 라는 클래스에 대한 MSDN 의 설명 : '실행시 보안과 관련된 정보의 묶음'
                // https://mhchoi8423.tistory.com/20

            }
        });

        Scene scene = new Scene(webView, 520, 470);

        stage = new Stage();
        stage.setScene(scene);
        stage.show();

        // now load the page
        webEngine.load("http://localhost:8562/sample.html");
        // load : 이 엔진에 웹 페이지를로드합니다. 이 메소드는 비동기 로딩을 시작하고 즉시 리턴합니다.
    }


}
