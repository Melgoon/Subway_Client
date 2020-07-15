package kr.or.ddit.sw.view.stamp;

import com.jfoenix.controls.JFXMasonryPane;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.stamp.IStampService;
import kr.or.ddit.sw.vo.coupon.CouponHisVO;
import kr.or.ddit.sw.vo.coupon.CouponPicVO;
import kr.or.ddit.sw.vo.coupon.CouponVO;
import kr.or.ddit.sw.vo.stamp.StampHisVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewStampController implements Initializable {
    public ImageView cupon;
    public ImageView backimg;
    public JFXMasonryPane man;


    private Registry reg;
    private IStampService iStampService;
    StampHisVO sv = new StampHisVO();
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iStampService = (IStampService) reg.lookup("stamp");
        }catch (RemoteException | NotBoundException e){
            e.printStackTrace();
        }



        Image img2 = new Image("file:src/images/스탬프와쿠폰배경.png");
        backimg.setImage(img2);


        //TODO 로그인연동시 아래 세션 주석 풀어주어야함
        String mem_id = LoginSession.memberSession.getMEM_ID();
        int cnt = 0;

        try {
            sv = iStampService.findStampNumbers(mem_id);
            System.out.println("------------"+mem_id);
            System.out.println("-------------"+sv);
            cnt = sv.getStamp_count();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        //fileChoose(cnt);

        Image img = new Image("file:src/images/0.png");
        switch(cnt){
            case 0:
                img = new Image("file:src/images/0.png");
                break;
            case 1:
                img = new Image("file:src/images/1.png");
                break;
            case 2:
                img = new Image("file:src/images/2.png");
                break;
            case 3:
                img = new Image("file:src/images/3.png");
                break;
            case 4:
                img = new Image("file:src/images/4.png");
                break;
            case 5:
                img = new Image("file:src/images/5.png");
                break;
            case 6:
                img = new Image("file:src/images/6.png");
                break;
            case 7:
                img = new Image("file:src/images/7.png");
                break;
            case 8:
                img = new Image("file:src/images/8.png");
                break;
            case 9:
                img = new Image("file:src/images/9.png");
                break;
            case 10:
                img = new Image("file:src/images/10.png");
                break;

        }

        //이미지 띄우기
        cupon.setImage(img);

        if(cnt==10){
            StampHisVO svo = new StampHisVO();
            //todo 세션 아이디로 바꾸어야함(테스트를위해)
            svo.setMem_id(LoginSession.memberSession.getMEM_ID());
            svo.setStamp_count(0);
            try {
                iStampService.updateStamp(svo);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Image img1 = new Image("file:src/images/0.png");
            cupon.setImage(img1);
            //fileChoose(0);
            try {
                CouponVO cv = new CouponVO();
                iStampService.insertCoupon(cv);
                CouponHisVO hv = new CouponHisVO();
                //todo 세션 아이디로 바꾸어야함(테스트를위해)
                hv.setMem_id(LoginSession.memberSession.getMEM_ID());
                iStampService.insertCouponHis(hv);

            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        //사진불러오기
        List<CouponPicVO> listcoupon = new ArrayList<>();
        try {
            listcoupon = iStampService.selectCoupon(LoginSession.memberSession.getMEM_ID());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        int i = 1;
        for(CouponPicVO vo : listcoupon){
            VBox vbox = new VBox();
            ImageView imageView = new ImageView();
            Image image = new Image(vo.getCoupon_pic_adr());
            System.out.println("-------------"+vo.getCoupon_pic_adr());
            imageView.setImage(image);
            vbox.getChildren().add(imageView);
            man.getChildren().add(vbox);

        }
        //사진불러오기
    }


    /*public void fileChoose(int cnt){
        //그리고 선택한 파일의 경로값을 반환한다.
        //System.out.println(selectedFile); //선택한 경로가 출력된다.
        FileInputStream fis = null;

        try{
            //파일읽어오기
            fis = new FileInputStream("C:\\Users\\hb930\\Desktop\\remote\\"+cnt+".png");
            BufferedInputStream bis = new BufferedInputStream(fis);

            //이미지 생성하기
            Image img = new Image(bis);
            //이미지 띄우기
            cupon.setImage(img);

        } catch (Exception e) {
            System.out.println("파일입출력 에러" + e);
        }
        finally {
            try {

                fis.close();
            } catch (Exception e) {
                System.out.println("닫기 실패" + e);
            }

        }

    }*/


}
