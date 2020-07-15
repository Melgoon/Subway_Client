package kr.or.ddit.sw.view.alarm;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

import static kr.or.ddit.sw.view.alarm.TellSubAlarmConoller.tellvo;

public class TellsubDetailController implements Initializable {
    @FXML
    private ImageView TellsubSedD;
    @FXML
    private JFXTextField tellsub_title;
    @FXML
    private JFXTextField tellsub_jijum;
    @FXML
    private TextArea tellsub_rep;
    @FXML
    private TextArea tellsub_content;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Image image = new Image("file:src/images/TellsubDetail.png");
        TellsubSedD.setImage(image);

        tellsub_title.setText(tellvo.getTellsub_name());
        tellsub_content.setText(tellvo.getTellsub_content());
        tellsub_jijum.setText(tellvo.getTellsub_jijum());
        tellsub_rep.setText(tellvo.getTellsub_rep());

        tellsub_title.setEditable(false);
        tellsub_content.setEditable(false);
        tellsub_jijum.setEditable(false);
        tellsub_jijum.setEditable(false);
    }
}
