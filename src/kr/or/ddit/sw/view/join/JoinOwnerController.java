package kr.or.ddit.sw.view.join;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import kr.or.ddit.sw.service.join.*;
import kr.or.ddit.sw.vo.owner.OwnerVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class JoinOwnerController implements Initializable {
    public ImageView imgView;
    public ImageView fx_captcha;
    public JFXButton join_email_chk_btn;
    public JFXButton join_cancel;
    public JFXButton join_confirm;
    public JFXButton join_email_chk1;
    public JFXButton join_adr_src;
    public JFXButton join_captcha_re;
    public JFXButton join_captcha_re1;
    public JFXPasswordField join_pw;
    public JFXPasswordField join_repw;
    public JFXTextField join_id;
    public JFXTextField join_email;
    public JFXTextField join_hp;
    public JFXTextField join_adr;
    public JFXTextField join_email_chk;
    public JFXTextField join_captcha_num;
    private IJoinService iJoinService;
    private IJoinEmailService emailService;
    private ICaptchKeyService captchaKeyService;
    private ICaptchaImgService captchaImgService;
    private ICaptchaChkService captchaChkService;
    private Registry reg;

    static String[] temp = new String[2];
    int count = 0;
    String cap;
    String cap1;
    int chknum = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Image background = new Image("file:src/images/signUp_license.jpg");
        imgView.setImage(background);

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

        join_email_chk_btn.setOnAction(event -> {
            try {
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
        join_cancel.setOnAction(event -> {

        });

        join_confirm.setOnAction(event -> {
            if(join_id!=null) {
                if (join_pw.getText().equals(join_repw.getText())||join_pw!=null||join_repw!=null) {
                    if(join_email.isDisabled()){
                        if(join_hp!=null){
                            if(join_adr!=null){
                                if(join_captcha_num.isDisabled()){
                                    try {
                                        OwnerVO vo = new OwnerVO();
                                        vo.setOwner_pw(join_pw.getText());
                                        vo.setOwner_addr(join_adr.getText());
                                        vo.setOwner_email(join_email.getText());
                                        vo.setOwner_jijum(join_id.getText());
                                        vo.setOwner_tel(join_hp.getText());
                                        //vo.setOwner_chk("FALSE");
                                        iJoinService= (IJoinService) reg.lookup("join");
                                        Object obj = iJoinService.insertOwner(vo);
                                        if(obj==null){
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setHeaderText("성공");
                                            alert.setContentText(join_id.getText() + "이 입력되었습니다. \r\n 승인대기해주세요");
                                            alert.showAndWait();
                                        }
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    } catch (NotBoundException e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    error("캡차 인증 오류","캡차인증을 진행해주세요");
                                }
                            }else {
                                error("주소 입력 오류","주소를 입력해주세요");
                            }
                        }else {
                            error("전화번호 인증 오류","전화번호를 입력해주세요");
                        }
                    }else {
                        error("이메일 인증 오류","이메일을 인증해주세요");
                    }
                }else{
                    error("비밀번호 입력오류","비밀번호를 정확하게 입력해주세요");
                }
            }else {
                error("아이디를 입력오류","아이디를 입력해주세요");
            }
        });
    }
    public void error(String header, String context) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("오류");
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
        return;
    }
}
