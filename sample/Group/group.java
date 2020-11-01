package sample.Group;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Controller;
import sample.CustomPlaylist.Create;
import sample.CustomPlaylist.CustomPlaylistController;
import sample.Player.AudioPlayer;
import sample.handleServer;

import java.net.URL;
import java.util.ResourceBundle;

public class group implements Initializable {
    public Label gName;
    public ListView<String> memberList,songList;
    public ComboBox<String> gPlaylist;
    public static ObservableList songs= FXCollections.observableArrayList();
    public static ObservableList members= FXCollections.observableArrayList();
    public static ObservableList GROUP_PLAYLIST= FXCollections.observableArrayList();
    public static String GNAME;

    Stage stage = new Stage();

    handleServer server1 = new handleServer();
    handleServer server2 = new handleServer();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        songs.clear();
        members.clear();
        //GROUP_PLAYLIST.clear();
        gName.setText(GNAME);
        server1.getGroupDetails(GNAME);
        GROUP_PLAYLIST.clear();
        GROUP_PLAYLIST.add("Create Playlist");
        GROUP_PLAYLIST.addAll(server2.getPlaylists(GNAME));
        gPlaylist.setItems(GROUP_PLAYLIST);
        songList.setItems(songs);
        memberList.setItems(members);
        setListener();
    }

    public void setListener()
    {
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

    public void addUser(ActionEvent actionEvent) throws Exception{
        AddUser.GNAME = GNAME;
        Stage stage = (Stage) songList.getScene().getWindow();
        Parent root = AddUser.getRoot();
        stage.setTitle("Add Member");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public void gotoGplaylist(ActionEvent actionEvent) throws Exception{
        if (!gPlaylist.getSelectionModel().getSelectedItem().equals("Create Playlist"))
        {
            CustomPlaylistController.USER = GNAME;
            CustomPlaylistController.pName = gPlaylist.getSelectionModel().getSelectedItem();
            Stage stage = (Stage) gName.getScene().getWindow();
            Parent root = CustomPlaylistController.getRoot();
            stage.setTitle(gPlaylist.getSelectionModel().getSelectedItem());
            stage.setScene(new Scene(root,600,600));
            stage.show();
        }
        else
        {
            Create.USER = GNAME;
            Stage stage = (Stage) gName.getScene().getWindow();
            Parent root = Create.getRoot();
            stage.setTitle("Create Playlist");
            stage.setScene(new Scene(root,400,400));
            stage.show();
        }
    }

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(group.class.getResource("group.fxml"));
        return root;
    }

    public void back(ActionEvent actionEvent) throws Exception{
        Stage stage1 = (Stage) gName.getScene().getWindow();
        Parent root = Controller.getRoot();
        stage1.setTitle("Ampify");
        stage1.setScene(new Scene(root,800,600));
        stage1.show();
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
}
