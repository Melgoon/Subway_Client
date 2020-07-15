package kr.or.ddit.sw.view.managementOwner;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import kr.or.ddit.sw.service.managementOwner.IManagementOwnerService;
import kr.or.ddit.sw.service.regAdvertise.IRegAdvertiseService;
import kr.or.ddit.sw.vo.owner.OwnerVO;
import kr.or.ddit.sw.vo.tellsub.TellSubVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class OwnerUpdateController implements Initializable {

    public JFXTextField txtf_owner_no;
    public JFXTextField txtf_owner_pass;
    public JFXButton btn_cansel;
    public JFXButton btn_update;
    public JFXTextField txtf_owner_email;
    public JFXTextField txtf_owner_addr;
    public JFXTextField txtf_awner_jijum;
    public JFXTextField txtf_owner_tel;
    public JFXToggleButton tbtn_Accept;

    private Registry reg;
    private IManagementOwnerService iManagementOwnerService;

    public void initData(OwnerVO ov){

        txtf_owner_no.setText(Integer.toString(ov.getOwner_code()));
        txtf_owner_pass.setText(ov.getOwner_pw());
        txtf_owner_email.setText(ov.getOwner_email());
        txtf_owner_addr.setText(ov.getOwner_addr());
        txtf_awner_jijum.setText(ov.getOwner_jijum());
        txtf_owner_tel.setText(ov.getOwner_tel());

        if(ov.getOwner_chk().equals("FALSE")){
            tbtn_Accept.setSelected(false);
        }else{
            tbtn_Accept.setSelected(true);
        }


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iManagementOwnerService = (IManagementOwnerService) reg.lookup("owner");
        }catch (RemoteException | NotBoundException e){
            e.printStackTrace();
        }

        tbtn_Accept.setOnMouseClicked(e->{
            if(tbtn_Accept.isSelected() == false){
                tbtn_Accept.setOnAction(e1->{

                });
            }
            if(tbtn_Accept.isSelected() == true){
                tbtn_Accept.setOnAction(e1->{

                });
            }
        });


        btn_cansel.setOnAction(e->{
            Stage stage = (Stage) btn_cansel.getScene().getWindow();
            stage.close();
        });


        btn_update.setOnAction(e1->{
            OwnerVO vo = new OwnerVO();
            vo.setOwner_addr(txtf_owner_addr.getText());
            vo.setOwner_email(txtf_owner_email.getText());
            vo.setOwner_jijum(txtf_awner_jijum.getText());
            vo.setOwner_pw(txtf_owner_pass.getText());
            vo.setOwner_tel(txtf_owner_tel.getText());
            vo.setOwner_code(Integer.parseInt(txtf_owner_no.getText()));
            if(tbtn_Accept.isSelected()==true){
                vo.setOwner_chk("TRUE");
            }else{
                vo.setOwner_chk("FALSE");
            }

            try {
                iManagementOwnerService.updateOwner(vo);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) btn_update.getScene().getWindow();
            stage.close();
            /*

            FXMLLoader loader = new FXMLLoader(getClass().getResource("OwnerView.fxml"));
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


            /*Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("OwnerView.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            Stage stage = (Stage) btn_update.getScene().getWindow();
            stage.setScene(scene);*/


        });


    }
}
