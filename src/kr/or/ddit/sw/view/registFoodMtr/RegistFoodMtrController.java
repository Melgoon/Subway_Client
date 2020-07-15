package kr.or.ddit.sw.view.registFoodMtr;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.registFoodMtr.IRegistFoodMtrService;
import kr.or.ddit.sw.vo.foodmtr.FoodMtrVO;
import kr.or.ddit.sw.vo.foodmtr.MtrPicVO;

import javax.swing.*;
import javax.swing.plaf.FileChooserUI;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegistFoodMtrController implements Initializable {


  
    public JFXTextField txtf_mtr_name;
    public JFXTextField txtf_mtr_cal;

    public JFXComboBox combo_mtr_div;
    public JFXButton btn_RegistFoodMtr;
    public JFXButton btn_registImg;
    public ImageView img_FoodMtr;
    public JFXTextArea txta_mtr_detail;
    public JFXButton btn_Cansel;
    public ImageView img_FoodMtback;
    private AnchorPane parentContainer;
    String filepath;


    public ObservableList<FoodMtrVO> data;

    private Registry reg;
    private IRegistFoodMtrService iRegistFoodMtrService;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("file:src/images/foodMtr.png");
        img_FoodMtback.setImage(img);

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iRegistFoodMtrService = (IRegistFoodMtrService) reg.lookup("foodMtr");
        }catch (RemoteException | NotBoundException e){
            e.printStackTrace();
        }

        ObservableList<String> foodMtrList = FXCollections.observableArrayList("빵","미트","치즈","샐러드","소스","사이드","음료","야채");
        combo_mtr_div.setItems(foodMtrList);




        btn_RegistFoodMtr.setOnAction(e->{
            int cnt = 0;
            int cnt2 = 0;
            String mtr_name = txtf_mtr_name.getText();
            String mtr_cal = txtf_mtr_cal.getText();
            int smtr_cal = Integer.parseInt(mtr_cal);
            String mtr_detail = txta_mtr_detail.getText();
            String s_mtr_div = (String) combo_mtr_div.getValue();
            String mtr_filepath = filepath;

            int mtr_div= 0;

            if(s_mtr_div.equals("빵")){
                 mtr_div = 1;
            }else if(s_mtr_div.equals("미트")){
                 mtr_div = 2;
            }else if(s_mtr_div.equals("치즈")){
                mtr_div = 3;
            }else if(s_mtr_div.equals("샐러드")){
                mtr_div = 4;
            }else if(s_mtr_div.equals("소스")){
                mtr_div = 5;
            }else if(s_mtr_div.equals("사이드")){
                mtr_div = 6;
            }else if(s_mtr_div.equals("음료")){
                mtr_div = 7;
            }else if(s_mtr_div.equals("야채")){
                mtr_div = 8;
            }

            FoodMtrVO fvo = new FoodMtrVO();
            MtrPicVO mpvo = new MtrPicVO();

            fvo.setMTR_NAME(mtr_name);
            fvo.setMTR_CAL(smtr_cal);
            fvo.setMTR_DETAIL(mtr_detail);
            fvo.setMTR_DIV(mtr_div);

            mpvo.setMTR_PIC_ADDR(mtr_filepath);

            if(txtf_mtr_cal.getText() != null && mpvo.getMTR_PIC_ADDR() != null){
                try {
                    cnt = iRegistFoodMtrService.insertFoodMtrPic(mpvo);
                    cnt2 = iRegistFoodMtrService.insertFoodMtr(fvo);
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }

                if(cnt>0&& cnt2>0){
                    infoMsg("재료 등록 성공", "등록에 성공하였습니다 :) ");


                }else{
                    errMsg("재료 등록 실패","등록에 실패하였습니다 :(");
                }




            } else{
                errMsg("재료 등록 실패","등록에 실패하였습니다 :(");


            }


           Stage stage = (Stage) btn_Cansel.getScene().getWindow();
            stage.close();
            /* FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewFoodMtr.fxml"));
            try {
                Pane root = (Pane) loader.load();
                Scene scene = new Scene(root);
                Stage stage1 = new Stage();
                stage1.initModality(Modality.APPLICATION_MODAL);
                stage1.setScene(scene);
                stage1.show();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }*/

            LoginSession.viewFoodMtrController.searchList();



        });

        btn_Cansel.setOnAction(e2->{
            Stage stage = (Stage) btn_Cansel.getScene().getWindow();
            stage.close();



        });

        btn_registImg.setOnAction(e1->{
          filepath =  fileChoose();
        });

    }

    private void errMsg(String headerText, String msg) {
        Alert errAlert = new Alert(Alert.AlertType.ERROR);
        errAlert.setTitle("오류");
        errAlert.setHeaderText(headerText);
        errAlert.setContentText(msg);
        errAlert.showAndWait();
    }

    private void infoMsg(String headerText, String msg) {
        Alert errAlert = new Alert(Alert.AlertType.INFORMATION);
        errAlert.setTitle("등록 성공");
        errAlert.setHeaderText(headerText);
        errAlert.setContentText(msg);
        errAlert.showAndWait();
    }

    public String fileChoose(){
        FileChooser fc = new FileChooser();
        fc.setTitle("이미지 선택");
        fc.setInitialDirectory(new File("C:/")); //default 디렉토리
        //선택한 파일 정보 추출
        //확장자 제한
        FileChooser.ExtensionFilter imgType = new FileChooser.ExtensionFilter("image file","*.jpg","*.gif","*.png");
        FileChooser.ExtensionFilter txtType = new FileChooser.ExtensionFilter("text file","*.txt","*.doc");
        fc.getExtensionFilters().addAll(imgType,txtType);

        File selectedFile = fc.showOpenDialog(null); // showOpenDialog는 창을 띄우는데 어느 위치에 띄울건지 인자를 받고


        //그리고 선택한 파일의 경로값을 반환한다.
        //System.out.println(selectedFile); //선택한 경로가 출력된다.
        String filePath = selectedFile.toString();

        try{
            //파일읽어오기
            FileInputStream fis = new FileInputStream(selectedFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            //이미지 생성하기
            Image img = new Image(bis);
            //이미지 띄우기
            img_FoodMtr.setImage(img);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        return filePath;
    }


    /**
     * 새로고침 by 종우
     * @param a
     */


}
