package sample.CustomPlaylist;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.handleServer;

import java.net.URL;
import java.util.ResourceBundle;


public class share implements Initializable {
    public TextField username;
    public TextField pName;
    public static String PNAME;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pName.setText(PNAME);
        //pName.setEditable(false);
    }

    public void Share(ActionEvent actionEvent) {
        handleServer handle = new handleServer();
        String result = handle.sharePlaylist(pName.getText(),username.getText());
        if (result.equals("shared"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Playlist Shared");
            alert.show();
        }
        else
        {
            if (result.equals("user not exist"))
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("username does not exist");
                alert.show();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("playlist already exist .\nChange name of playlist");
                alert.show();
            }
        }
    }

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(share.class.getResource("share.fxml"));
        return root;
    }

    public void back(ActionEvent actionEvent) throws Exception{
        CustomPlaylistController.pName = pName.getText();
        Stage stage = (Stage) username.getScene().getWindow();
        Parent root = CustomPlaylistController.getRoot();
        stage.setTitle(pName.getText());
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }
}
