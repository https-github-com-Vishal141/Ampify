package sample.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Controller;

import java.util.ArrayList;

public class RegisterController {
    public TextField username,cPassword,email;
    public PasswordField password;
    public Button register;
    public CheckBox hindi,english,selena,justine,rockm,dubstep;

    registerDb regdb = new registerDb();

    public void onRegister(ActionEvent actionEvent) throws Exception {
        String uname = username.getText();
        String pass = password.getText();
        String em = email.getText();
        String cpass = cPassword.getText();

        if (uname!=null && pass!=null && em!=null && uname!="" && pass!="" && em!="")
        {
          if (!regdb.CheckForExist(em)){
              if (regdb.registerUser(uname,pass,em))
              {
                  ArrayList<String > language = new ArrayList<>();
                  ArrayList<String > generes = new ArrayList<>();
                  ArrayList<String > artist = new ArrayList<>();

                  if (hindi.isSelected())
                      language.add(hindi.getText());
                  if (english.isSelected())
                      language.add(english.getText());
                  if (rockm.isSelected())
                      generes.add(rockm.getText());
                  if (dubstep.isSelected())
                      generes.add(dubstep.getText());
                  if (justine.isSelected())
                      artist.add(justine.getText());
                  if (selena.isSelected())
                      artist.add(selena.getText());
                  for (String g:generes)
                  {
                      regdb.insertIntoGeneres(username.getText(),g);
                  }
                  for (String a:artist)
                  {
                      regdb.insertIntoArtist(username.getText(),a);
                  }
                  for (String l:language)
                      regdb.insertIntolanguage(username.getText(),l);
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
