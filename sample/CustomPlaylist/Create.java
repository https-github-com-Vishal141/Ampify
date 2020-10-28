package sample.CustomPlaylist;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Controller;
import sample.handleServer;

public class Create {
    public TextField name;
    public static String USER;
    handleServer handle = new handleServer();

    public void create(ActionEvent actionEvent) throws Exception {
        if (name.getText()!=null && !name.getText().equals(""))
        {
            if (handle.createPlaylist(USER,name.getText()))
            {
                Stage stage = (Stage) name.getScene().getWindow();
                Parent root = CustomPlaylistController.getRoot();
                stage.setTitle(name.getText());
                CustomPlaylistController.pName = name.getText();
                stage.setScene(new Scene(root,600,600));
                stage.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setContentText("Error Occurred");
                alert.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("warning");
            alert.setContentText("Name of Playlist is mandatory");
            alert.show();
        }
    }

    public void back(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) name.getScene().getWindow();
        Parent root = Controller.getRoot();
        stage.setTitle("Ampify");
        stage.setScene(new Scene(root,800,600));
        stage.show();
    }

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(Create.class.getResource("create.fxml"));
        return root;
    }

}
