package sample.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    public TextField username;
    public TextField password;
    public Button signIn,signUp;

    loginDb ldb = new loginDb();

    public void SignIn(ActionEvent actionEvent) {
        if (username.getText()!=null && password.getText()!=null){
            if (ldb.login(username.getText(),password.getText())){

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
}
