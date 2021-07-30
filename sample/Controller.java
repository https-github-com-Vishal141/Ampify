package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.CustomPlaylist.Create;
import sample.CustomPlaylist.CustomPlaylistController;
import sample.Group.CreateGroup;
import sample.Group.group;
import sample.History.HistoryController;
import sample.History.Liked;
import sample.LocalSong.Downloads;
import sample.LocalSong.LocalVideoController;
import sample.LocalSong.localSongController;
import sample.Player.AudioPlayer;
import sample.Search.SearchResultController;


import java.io.File;
import java.net.URI;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;

public class Controller implements Initializable {

    public static String Username;
    public static int Separator;
    public  Label username;
    public ListView<String> recent,recommended,trending,newSongs;
    public ComboBox<String> playlists;
    public ComboBox<String> groups;

    private ObservableList<String> rec = FXCollections.observableArrayList();
    private ObservableList<String> recom = FXCollections.observableArrayList();
    private ObservableList<String> tre = FXCollections.observableArrayList();
    private ObservableList<String> playLists = FXCollections.observableArrayList();
    private ObservableList<String> Groups = FXCollections.observableArrayList();
    public static ArrayList<String> reTimeTitle;
    public static ArrayList<String> reLikeTitle;
    public static ArrayList<String> AllTitles;

    Stage stage = new Stage();

    handleServer handle = new handleServer();
    handleServer handle1 = new handleServer();
    handleServer handle2 = new handleServer();
    handleServer handle3 = new handleServer();
    handleServer handle4 = new handleServer();
    handleServer handle5 = new handleServer();

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(Controller.class.getResource("sample.fxml"));
        return root;
    }

    public void setList()
    {
        int hour = LocalTime.now().getHour();
        handle3.getRecommended(Username,hour+"");

        rec.addAll(handle1.getRecent(Username));
        tre.addAll(handle2.getTrending());
        recom.addAll(reTimeTitle);
        recom.addAll(reLikeTitle);

        recent.setItems(rec);
        trending.setItems(tre);
        recommended.setItems(recom);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(Username);
        setList();
        playLists.add("Create Playlist");
        playLists.addAll(handle4.getPlaylists(Username));
        playlists.setItems(playLists);
        Groups.add("Create Group");
        Groups.addAll(handle5.getGroups(Username));
        groups.setItems(Groups);
        AllTitles = handle.getAllSong();
        recent.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount()==2)
                {
                    int index = recent.getSelectionModel().getSelectedIndex();
                    AudioPlayer.index = index;
                    AudioPlayer.name=recent.getSelectionModel().getSelectedItem();
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
                    AudioPlayer.queueSongs.addAll(rec);
                    gotoPlayer();
                }
            }
        });

        trending.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
               if (mouseEvent.getClickCount()==2)
               {
                   int index = trending.getSelectionModel().getSelectedIndex();
                   AudioPlayer.index = index;
                   //System.out.println("index:"+index);
                   AudioPlayer.name=trending.getSelectionModel().getSelectedItem();
                   if (stage.isShowing())
                   {
                       stage.close();
                       AudioPlayer.mediaPlayer.stop();
                       System.out.println("inside stage");
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
                   AudioPlayer.queueSongs.addAll(tre);
                   System.out.println("outside stage");
                   gotoPlayer();
               }
            }
        });

        recommended.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount()==2)
                {
                    int index = recommended.getSelectionModel().getSelectedIndex();
                    AudioPlayer.index = index;
                    AudioPlayer.name=recommended.getSelectionModel().getSelectedItem();
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
                    AudioPlayer.queueSongs.addAll(recom);
                    gotoPlayer();
                }
            }
        });

    }

    public void history(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) username.getScene().getWindow();
        HistoryController.username = username.getText();
        Parent root = HistoryController.getRoot();
        stage.setTitle("History");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public void likes(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) username.getScene().getWindow();
        Parent root = Liked.getRoot();
        stage.setTitle("Local Songs");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public void local_music(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) username.getScene().getWindow();
        Parent root = localSongController.getParent();
        stage.setTitle("Local Songs");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public void local_video(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) username.getScene().getWindow();
        Parent root = LocalVideoController.getParent();
        stage.setTitle("Local Videos");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public void search(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) username.getScene().getWindow();
        Parent root = SearchResultController.getRoot();
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public void goToPlaylist(ActionEvent actionEvent) throws Exception{
        if (playlists.getSelectionModel().getSelectedItem()!="Create Playlist")
        {
            CustomPlaylistController.USER = Username;
            CustomPlaylistController.pName = playlists.getSelectionModel().getSelectedItem();
            Stage stage = (Stage) username.getScene().getWindow();
            Parent root = CustomPlaylistController.getRoot();
            stage.setTitle(playlists.getSelectionModel().getSelectedItem());
            stage.setScene(new Scene(root,600,600));
            stage.show();
        }
        else
        {
            Create.USER = Username;
            Stage stage = (Stage) username.getScene().getWindow();
            Parent root = Create.getRoot();
            stage.setTitle("Create Playlist");
            stage.setScene(new Scene(root,400,400));
            stage.show();
        }
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

    public static URL getSong(String Title)
    {
//        File file1=null;
        File file2=null;
        URL uri=null;
        handleServer handle5 = new handleServer();
        handleServer handle6 = new handleServer();
        handleServer handle7 = new handleServer();
        handleServer handle8 = new handleServer();
        int index = AllTitles.indexOf(Title);
        uri = handle5.getSong(index+1);
//        file1 = handle5.getSong("getSong",index+1);
        file2 = handle6.getSrt(index+1);
        handle7.addToHistory(index+1);
        handle8.increaseView(index+1);
        AudioPlayer.srtFile = file2;
        AudioPlayer.id = index+1;
        //System.out.println(uri.toString());
        return uri;
    }

    public static File getSongFile(String title)
    {
        handleServer handle9 = new handleServer();
        int id = AllTitles.indexOf(title);
        return handle9.getSongFile(id+1);
    }

    public void gotoGroups(ActionEvent actionEvent) throws Exception{
        if (!groups.getSelectionModel().getSelectedItem().equals("Create Group"))
        {
            group.GNAME = groups.getSelectionModel().getSelectedItem();
            Stage stage = (Stage) username.getScene().getWindow();
            Parent root = group.getRoot();
            stage.setTitle(groups.getSelectionModel().getSelectedItem());
            stage.setScene(new Scene(root,600,600));
            stage.show();
        }
        else
        {
            Stage stage = (Stage) username.getScene().getWindow();
            Parent root = CreateGroup.getRoot();
            stage.setTitle("Create Group");
            stage.setScene(new Scene(root,400,400));
            stage.show();
        }
    }

    public void downloads(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) username.getScene().getWindow();
        Parent root = Downloads.getRoot();
        stage.setTitle("Downloads");
        stage.setScene(new Scene(root,600,700));
        stage.show();
    }

    public void logOut(ActionEvent actionEvent) throws Exception{
        localSongController.isLogin = false;
        Stage stage = (Stage) username.getScene().getWindow();
        Parent root = localSongController.getParent();
        stage.setTitle("Local Songs");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }
}
