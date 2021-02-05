package sample.Login;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Controller;
import sample.handleServer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public TextField username,cPassword,email;
    public PasswordField password;
    public Button register;
    public ListView<String> languages,generes,artists;
    public static ObservableList<String> languagesList= FXCollections.observableArrayList();
    public static ObservableList<String> artistList= FXCollections.observableArrayList();
    public static ObservableList<String> generesList= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languages.setItems(languagesList);
        artists.setItems(artistList);
        generes.setItems(generesList);

        languages.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleServer server = new handleServer();
                String lan = languages.getSelectionModel().getSelectedItem();
                server.LanguageDetails(username.getText(),lan);
            }
        });

        artists.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleServer server = new handleServer();
                String lan = artists.getSelectionModel().getSelectedItem();
                server.ArtistDetails(username.getText(),lan);
            }
        });

        generes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleServer server = new handleServer();
                String lan = generes.getSelectionModel().getSelectedItem();
                server.GeneresDetails(username.getText(),lan);
            }
        });
    }

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

}
