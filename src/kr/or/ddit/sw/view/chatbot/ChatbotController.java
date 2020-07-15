package kr.or.ddit.sw.view.chatbot;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import kr.or.ddit.sw.service.login.LoginSession;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatbotController implements Initializable {
    public VBox vbox;
    public JFXTextField text;
    public JFXButton submit;
    public JFXButton close;

    HBox hBox;
    ImageView cadoView;
    Image cado;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hBox = new HBox();
        hBox.setPrefSize(200,150);
        Image culture = new Image("file:src/images/cultures.png");
        cado = new Image("file:src/images/cado.png");
        cadoView = new ImageView();
        cadoView.setImage(cado);
        Label label = new Label();
        label.setText(LoginSession.introString);
        label.setPrefSize(800,150);
        hBox.getChildren().add(cadoView);
        hBox.getChildren().add(label);
        hBox.setSpacing(50);
        vbox.getChildren().addAll(hBox);

        submit.setOnAction(event -> {
            String input = text.getText();
            Dialog dialog = new Dialog();
            String output = dialog.dialog(LoginSession.uuid,input);
            hBox = new HBox();
            hBox.setPrefSize(200,150);
            Label label1 = new Label();
            label1.setText(input);
            ImageView pro = new ImageView();
            pro.setImage(culture);
            pro.setFitHeight(177);
            pro.setFitWidth(129);
            hBox.setSpacing(50);
            hBox.getChildren().addAll(label1,pro);
            hBox.setStyle("-fx-alignment: center-right");
            vbox.getChildren().add(hBox);
            hBox = new HBox();
            hBox.setPrefSize(200,150);
            Label label2 = new Label();
            label2.setText(output);
            ImageView imageView = new ImageView();
            imageView.setImage(cado);
            hBox.getChildren().addAll(imageView,label2);
            hBox.setSpacing(50);
            vbox.getChildren().add(hBox);
        });
        close.setOnAction(event -> {
            CloseDialog closeDialog = new CloseDialog();
            closeDialog.close(LoginSession.uuid);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("종료");
            alert.setContentText("종료되었습니다.");
            alert.showAndWait();
        });
    }
}
