package sample.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Controller;
import sample.handleServer;

import java.io.IOException;
import java.net.Socket;


public class LoginController {
    public TextField username;
    public PasswordField password;
    public Button signIn,signUp;

    public void SignIn(ActionEvent actionEvent) throws Exception{
        handleServer handle = new handleServer();
        if (username.getText()!=null && password.getText()!=null){
            if (handle.login(username.getText(),password.getText())){
                Controller.Username = username.getText();
                Stage stage = (Stage) signIn.getScene().getWindow();
                Parent root = Controller.getRoot();
                stage.setTitle("Ampify");
                stage.setScene(new Scene(root,800,600));
                stage.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Warning");
                alert.setContentText("Username and password is not correct");
                alert.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Warning");
            alert.setContentText("Both fields are mandatory");
            alert.show();
        }
    }


    public void SignUp(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) signIn.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        stage.setTitle("Sign Up");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(RegisterController.class.getResource("login.fxml"));
        return root;
    }
}
