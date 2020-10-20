package sample.CustomPlaylist;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class CustomPlaylistController implements Initializable {
    @FXML
    public ListView<String> songList;
    private ObservableList selectedSongs = FXCollections.observableArrayList();
    private ArrayList<Integer> selectedIds = new ArrayList<Integer>();

    public void AddSong(ActionEvent actionEvent) throws Exception {
        Stage stage = (Stage) songList.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("addSong.fxml"));
        stage.setTitle("Select Songs");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(CustomPlaylistController.class.getResource("customPlaylist.fxml"));
        return root;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       selectedSongs = AddSongController.getSelectedSong();
       selectedIds = AddSongController.getSelectedIds();
       songList.setItems(selectedSongs);
       songList.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent mouseEvent) {

           }
       });
    }

}
