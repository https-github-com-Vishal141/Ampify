package sample.LocalSong;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import sample.Controller;
import sample.Login.LoginController;
import sample.Player.AudioPlayer;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class localSongController implements Initializable {
    @FXML
    public ListView<String> myList;
    public Button login,back;
    public static ObservableList<String> list = FXCollections.observableArrayList();
    public static ArrayList<File> songs;
    public static boolean isDownload=false;
    public Stage stage = new Stage();
    public static boolean isLogin = false;
    public static boolean isFirst=true;

    public void setList()
    {
        Platform.runLater(() -> {
            if (isFirst)
            {
                songs= getSongs(File.listRoots());
                for (File f:songs)
                {
                    list.add(f.getName().replace(".mp3",""));
                }
                isFirst=false;
            }
            myList.setItems(list);
        });
    }

    public ArrayList<File> getSongs(File file[])
    {
        ArrayList<File> song = new ArrayList<>();
        for (File singleFile:file)
        {
            if (singleFile.isDirectory()&& singleFile.length()!=0){
                try {
                    song.addAll(getSongs(singleFile.listFiles()));
                }catch (NullPointerException e){}
            }
            else{
                if (singleFile.getName().endsWith(".mp3"))
                {
                    song.add(singleFile);
                }
            }
        }
        return song;
    }

    public static Parent getParent() throws Exception
    {
        Parent root = FXMLLoader.load(localSongController.class.getResource("localSong.fxml"));
        return root;
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

    public static File getSongById(String title)
    {
        if (isDownload)
        {
            return Downloads.getSong(title);
        }
        return songs.get(list.indexOf(title));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (isLogin)
            login.setDisable(true);
        else
        {
            login.setDisable(false);
            back.setDisable(true);
        }
        myList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount()==2)
                {
                    int index = myList.getSelectionModel().getSelectedIndex();
                    AudioPlayer.index = index;
                    AudioPlayer.name=myList.getSelectionModel().getSelectedItem();
                    isDownload = false;
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
                    AudioPlayer.isLocal = true;
                    AudioPlayer.queueSongs.clear();
                    AudioPlayer.queueSongs.addAll(list);
                    gotoPlayer();
                }
            }
        });
        Platform.runLater(this::setList);
    }

    public void back(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) myList.getScene().getWindow();
        Parent root = Controller.getRoot();
        stage.setTitle("Ampify");
        stage.setScene(new Scene(root,600,700));
        stage.show();
    }

    public void gotoVideos(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) myList.getScene().getWindow();
        Parent root = LocalVideoController.getParent();
        stage.setTitle("Local Videos");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public void gotoLogin(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) myList.getScene().getWindow();
        Parent root = LoginController.getRoot();
        stage.setTitle("SignIn");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public void downloads(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) myList.getScene().getWindow();
        Parent root = Downloads.getRoot();
        stage.setTitle("Downloads");
        stage.setScene(new Scene(root,600,700));
        stage.show();
    }
}
