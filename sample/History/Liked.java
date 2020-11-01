package sample.History;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import sample.Controller;
import sample.Player.AudioPlayer;
import sample.handleServer;

import java.net.URL;
import java.util.ResourceBundle;

public class Liked implements Initializable {

    public ListView<String> likeSong;
    public ObservableList<String> songs = FXCollections.observableArrayList();

    Stage stage  = new Stage();

    handleServer server = new handleServer();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        songs.clear();
        songs.addAll(server.getLiked(Controller.Username));
        FXCollections.reverse(songs);
        likeSong.setItems(songs);
        likeSong.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int index = likeSong.getSelectionModel().getSelectedIndex();
                AudioPlayer.index = index;
                //AudioPlayer.name=historySongs.getSelectionModel().getSelectedItem();
                AudioPlayer.name = songs.get(index);
                if (stage.isShowing())
                {
                    stage.close();
                    AudioPlayer.mediaPlayer.stop();
                }
                else
                {
                    if (AudioPlayer.stage!=null)
                    {
                        if (AudioPlayer.stage.isShowing())
                        {
                            AudioPlayer.stage.close();
                            AudioPlayer.mediaPlayer.stop();
                        }
                    }
                }
                AudioPlayer.isLocal = false;
                AudioPlayer.queueSongs.clear();
                AudioPlayer.queueSongs.addAll(songs);
                gotoPlayer();
            }
        });
    }

    public void back(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) likeSong.getScene().getWindow();
        Parent root = Controller.getRoot();
        stage.setTitle("Ampify");
        stage.setScene(new Scene(root,800,600));
        stage.show();
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
        AudioPlayer.stage = stage;
        stage.show();
    }

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(Liked.class.getResource("liked.fxml"));
        return root;
    }
}
