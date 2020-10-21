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
import sample.handleServer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddSongController implements Initializable {
   @FXML
   public ListView<String> songList;
   private ObservableList titles = FXCollections.observableArrayList();
   private static ObservableList selectedSongs = FXCollections.observableArrayList();
   private static ArrayList<Integer> selectedIds = new ArrayList<Integer>();

   handleServer handle = new handleServer();

    public void AddSong(ActionEvent actionEvent) throws Exception {
       Stage stage = (Stage) songList.getScene().getWindow();
       Parent root = FXMLLoader.load(getClass().getResource("customPlaylist.fxml"));
       stage.setTitle("Playlist");
       stage.setScene(new Scene(root,600,600));
       stage.show();
    }

    public static ObservableList getSelectedSong(){return selectedSongs;}
    public static ArrayList<Integer> getSelectedIds(){return selectedIds;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titles.addAll(handle.getAllSong());
        songList.setItems(titles);
        songList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectedSongs.add(songList.getSelectionModel().getSelectedItem());
                int index = titles.indexOf(songList.getSelectionModel().getSelectedItem());
                selectedIds.add(index+1);
            }
        });

    }
}
