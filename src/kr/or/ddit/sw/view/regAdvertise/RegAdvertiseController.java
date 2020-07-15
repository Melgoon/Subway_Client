package kr.or.ddit.sw.view.regAdvertise;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.regAdvertise.IRegAdvertiseService;
import kr.or.ddit.sw.service.registFoodMtr.IRegistFoodMtrService;
import kr.or.ddit.sw.vo.advertise.AdvertiseVO;

import java.io.*;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class RegAdvertiseController implements Initializable {
    public JFXButton btn_addfile;
    public ImageView img_file;
    public JFXButton btn_cansel;
    public JFXButton btn_regist;

    private Registry reg;
    private IRegAdvertiseService iRegAdvertiseService;
    File file;
    String filepath;
    AdvertiseVO avo = new AdvertiseVO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iRegAdvertiseService = (IRegAdvertiseService) reg.lookup("advertise");
        }catch (RemoteException | NotBoundException e){
            e.printStackTrace();
        }

        btn_cansel.setOnAction(e2->{
            Stage stage = (Stage) btn_cansel.getScene().getWindow();
            stage.close();
        });


        btn_addfile.setOnAction(e1->{
            file = fileChoose();
            filepath =  file.toString();
        });

        btn_regist.setOnAction(e->{
            int cnt = 0;
            avo.setAd_pic_adr(file.getName());
            try {
                cnt = iRegAdvertiseService.insertAdvertise(avo);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            }

            Stage stage = (Stage) btn_regist.getScene().getWindow();
            stage.close();

        });


    }

    public File fileChoose(){
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
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try{
            //파일읽어오기
            fis = new FileInputStream(selectedFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            //이미지 생성하기
            Image img = new Image(bis);
            //이미지 띄우기
            img_file.setImage(img);

            fos = new FileOutputStream("C:\\Users\\hb930\\Desktop\\remote\\"+ selectedFile.getName());
            byte[] buffer = new byte[1024];

            while(fis.read(buffer,0,buffer.length) != -1){
                fos.write(buffer);

            }


        } catch (Exception e) {
            System.out.println("파일입출력 에러" + e);
        }
        finally {
            try {

                fis.close();
                fos.close();
            } catch (Exception e) {
                System.out.println("닫기 실패" + e);
            }

        }

        return selectedFile;
    }


}
