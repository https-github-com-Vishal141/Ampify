package sample.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Controller;
import sample.handleServer;

import java.util.ArrayList;

public class RegisterController {
    public TextField username,cPassword,email;
    public PasswordField password;
    public Button register;
    public CheckBox hindi,english,selena,justine,rockm,dubstep;

    public void onRegister(ActionEvent actionEvent) throws Exception {
        String uname = username.getText();
        String pass = password.getText();
        String em = email.getText();
        String cpass = cPassword.getText();

        handleServer handle = new handleServer();
        if (uname!=null && pass!=null && em!=null && uname!="" && pass!="" && em!="")
        {
            String result = handle.register(uname,pass,em);
          if (!result.equals("emailExist")){
              if (!result.equals("userExist"))
              {
                  Stage stage = (Stage) register.getScene().getWindow();
                  Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                  stage.setTitle("Login");
                  stage.setScene(new Scene(root,600,600));
                  stage.show();
              }
              else {
                  Alert alert = new Alert(Alert.AlertType.WARNING);
                  alert.setHeaderText(null);
                  alert.setContentText("Username is already exist");
                  alert.show();
              }
          }
          else {
              Alert alert = new Alert(Alert.AlertType.WARNING);
              alert.setHeaderText(null);
              alert.setContentText("email is already exist");
              alert.show();
          }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("All fields are mandatory");
            alert.show();
        }
    }

    public void onLogin(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) register.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage.setTitle("Login");
        stage.setScene(new Scene(root,600,600) );
        stage.show();
    }

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(RegisterController.class.getResource("register.fxml"));
        return root;
    }
}
