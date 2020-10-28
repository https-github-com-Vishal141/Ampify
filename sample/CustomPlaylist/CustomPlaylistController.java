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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Controller;
import sample.Player.AudioPlayer;
import sample.handleServer;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;


public class CustomPlaylistController implements Initializable {
    @FXML
    public ListView<String> songList;
    public Label playlistName;
    public static String pName;
    public static ObservableList Songs = FXCollections.observableArrayList();
    public Set<Integer> Ids ;
    public static String USER;
    Stage stage = new Stage();

    handleServer handle = new handleServer();

    public void AddSong(ActionEvent actionEvent) throws Exception {
        AddSongController.USER = USER;
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
        Songs.clear();
        playlistName.setText(pName);
        Ids = handle.getPlaylist(USER,pName);
        songList.setItems(Songs);
       songList.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent mouseEvent) {
               int index = songList.getSelectionModel().getSelectedIndex();
               AudioPlayer.index = index;
               AudioPlayer.name=songList.getSelectionModel().getSelectedItem();
               if (stage.isShowing())
               {
                   stage.close();
                   AudioPlayer.mediaPlayer.stop();
               }
               AudioPlayer.isLocal = false;
               AudioPlayer.queueSongs.addAll(Songs);
               gotoPlayer();
           }
       });
    }

    public void gotoPlayer()
    {
        Parent root = null;
        try {
            root = AudioPlayer.getRoot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.setTitle("Music Player");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public void back(ActionEvent actionEvent) throws Exception{
        Stage stage1 = (Stage) playlistName.getScene().getWindow();
        Parent root = Controller.getRoot();
        stage1.setTitle("Ampify");
        stage1.setScene(new Scene(root,800,600));
        stage1.show();
    }

    public void Share(ActionEvent actionEvent) throws Exception{
        share.PNAME = pName;
        Stage stage1 = (Stage) playlistName.getScene().getWindow();
        Parent root = share.getRoot();
        stage1.setTitle("Share");
        stage1.setScene(new Scene(root,400,200));
        stage1.show();
    }
}
