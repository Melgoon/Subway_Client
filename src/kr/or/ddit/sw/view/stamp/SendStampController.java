package kr.or.ddit.sw.view.stamp;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import kr.or.ddit.sw.service.stamp.IStampService;
import kr.or.ddit.sw.vo.coupon.CouponHisVO;
import kr.or.ddit.sw.vo.coupon.CouponVO;
import kr.or.ddit.sw.vo.member.MemberVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SendStampController implements Initializable {
    public JFXButton btn_send1;
    private Registry reg;
    private IStampService iStampService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iStampService = (IStampService) reg.lookup("stamp");
        }catch (RemoteException | NotBoundException e){
            e.printStackTrace();
        }


        btn_send1.setOnAction(e->{
            List<MemberVO> list =null;
            try {
                list = iStampService.findMemberId();
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }

            for(int i=0; i<list.size(); i++){
                String mem_id;
                mem_id=list.get(i).getMEM_ID();

                CouponVO cv = new CouponVO();
                CouponHisVO hv = new CouponHisVO();
                hv.setMem_id(mem_id);
                try {
                    iStampService.insertCouponB(cv);
                    iStampService.insertCouponHis(hv);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }



            }



        });




    }
}
