package sample.Login;

import javafx.application.Platform;
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
           if (cpass.equals(pass))
           {
               String result = handle.register(uname,pass,em);
               if (!result.equals("emailExist")){
                   if (!result.equals("userExist"))
                   {
                       insertDetail();
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
           else {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setHeaderText(null);
               alert.setContentText("both passwords are not match.");
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

    public void insertDetail()
    {
       // CheckBox[] array = {hindi,english,selena,justine,rockm,dubstep};
//        int i=0;
//        String detail;
        handleServer server1 = new handleServer();
        handleServer server2 = new handleServer();
        if (hindi.isSelected())
            server1.Details(username.getText(),"hindi","language");

        if (english.isSelected())
            server2.Details(username.getText(),"english","language");

        handleServer server3 = new handleServer();
        handleServer server4 = new handleServer();
        if (selena.isSelected())
            server3.Details(username.getText(),"selena","artist");

        if (justine.isSelected())
            server4.Details(username.getText(),justine.getText(),"artist");


        handleServer server5 = new handleServer();
        handleServer server6 = new handleServer();
        if (rockm.isSelected())
            server5.Details(username.getText(),rockm.getText(),"genere");

        if (dubstep.isSelected())
            server6.Details(username.getText(),dubstep.getText(),"genere");
    }

}
