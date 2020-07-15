package kr.or.ddit.sw.view.tellsub;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.tellsub.ITellSubService;
import kr.or.ddit.sw.vo.tellsub.TellSubVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class TellSubMenagementSelectController implements Initializable {
    public JFXButton ok;
    public JFXTextField tellnametf;
    public JFXTextField tellname;
    public JFXTextArea contentta;
    public JFXTextArea answerta;
    public JFXTextField jijum;
    public ImageView imgur;


    private Registry reg;
    private ITellSubService itell;

    /**
     * Tellsub 공지사항 상세보기  (매니저전용)
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("file:src/images/TellSubMenagementSelect.png");
        imgur.setImage(img);

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            itell = (ITellSubService) reg.lookup("tell");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }
    // TODO 답변 작성 시 처리여부 처리가 되고, 클릭이 안되게 해야하는데.. 잘 모르겠습니다...

    public void initData(TellSubVO tv) {

            if(answerta.getText().isEmpty()){
                tellnametf.setDisable(true);
                tellname.setDisable(true);
                contentta.setDisable(true);
                jijum.setDisable(true);

                tellnametf.setText(tv.getTellsub_name());
                contentta.setText(tv.getTellsub_content());
                answerta.setText(tv.getTellsub_rep());
                jijum.setText(tv.getTellsub_jijum());

                ok.setOnAction(event -> {
                    if(answerta.getText().isEmpty()){
                        errMsg("에러","값을 입력하세요.");
                        return;
                    }
                    else{
                        tv.setTellsub_rep(answerta.getText());

                        Object obj = 1;

                        try {
                            obj = itell.updateTell(tv);

                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                        if (obj == null) {
                            errMsg("에러","비정상적인 접근입니다.");
                        } else {
                            infoMsg("작업결과","정상적으로 입력되었습니다.");
                        }
                        Stage stage = (Stage) ok.getScene().getWindow();
                        answerta.setEditable(true);
                        stage.close();
                    }
                });
            }else{
                answerta.setDisable(true);
            }




    }

    private void infoMsg(String headerText, String msg){
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle("작업 결과");
        infoAlert.setHeaderText(headerText);
        infoAlert.setContentText(msg);
        infoAlert.showAndWait();
    }

    private void errMsg(String headerText, String msg){
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle("오류");
        errAlert.setHeaderText(headerText);
        errAlert.setContentText(msg);
        errAlert.showAndWait();
    }
}
