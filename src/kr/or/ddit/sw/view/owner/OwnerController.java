package kr.or.ddit.sw.view.owner;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OwnerController implements Initializable {

    public ImageView owner_signup;
    public ImageView owner_login;
    public ImageView owner;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image login = new Image("file:src/images/owner_login.png");

        Image signup = new Image("file:src/images/admin_signUp.png");

        Image ownerimg = new Image("file:src/images/subway_owner.png");
        owner.setImage(ownerimg);

        owner_signup.setImage(signup);

        owner_login.setImage(login);

        owner_signup.setOnMouseClicked(event -> {
            Stage stage = (Stage) owner_signup.getScene().getWindow();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("owner_insert.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        owner_login.setOnMouseClicked(event -> {
            Stage stage = (Stage) owner_signup.getScene().getWindow();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("owner_login.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

    }
}
