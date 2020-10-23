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
import sample.Controller;
import sample.handleServer;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddSongController implements Initializable {
   @FXML
   public ListView<String> songList;
   private ObservableList titles = FXCollections.observableArrayList();
   private static ArrayList<Integer> selectedIds = new ArrayList<Integer>();

   handleServer handle1 = new handleServer();

    public void AddSong(ActionEvent actionEvent) throws Exception {
        for (int id:selectedIds)
        {
            handleServer handle2 = new handleServer();
            handle2.addToPlaylist(Controller.Username,CustomPlaylistController.pName,id);
        }
       Stage stage = (Stage) songList.getScene().getWindow();
       Parent root = FXMLLoader.load(getClass().getResource("customPlaylist.fxml"));
       stage.setTitle(CustomPlaylistController.pName);
       stage.setScene(new Scene(root,600,600));
       stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titles.addAll(handle1.getAllSong());
        songList.setItems(titles);
        songList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
               // selectedSongs.add(songList.getSelectionModel().getSelectedItem());
                int index = titles.indexOf(songList.getSelectionModel().getSelectedItem());
                selectedIds.add(index+1);
            }
        });

    }
}
