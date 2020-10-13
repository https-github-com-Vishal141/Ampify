package sample.Login;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegisterController {
    public TextField username,password,cPassword,email;
    public Button register;

    registerDb regdb = new registerDb();

    public void onRegister(ActionEvent actionEvent) {
        String uname = username.getText();
        String pass = password.getText();
        String em = email.getText();
        String cpass = cPassword.getText();

        if (uname!=null && pass!=null && em!=null)
        {
          if (!regdb.CheckForExist(em)){
              if (regdb.registerUser(uname,pass,em))
              {

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
}
