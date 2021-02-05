package sample.LocalSong;

import javafx.application.Platform;
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
import javafx.scene.media.Media;
import javafx.stage.Stage;
import jdk.jshell.Snippet;
import sample.Controller;
import sample.Player.AudioPlayer;
import sample.Player.VideoPlayer;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LocalVideoController implements Initializable {
    @FXML
    ListView<String > videoList;

    public static ObservableList<String> list = FXCollections.observableArrayList();
    static ArrayList<File>  videos = new ArrayList<>();
    public static boolean isFirst=true;
    Stage stage=new Stage();

    public void setList()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (isFirst)
                {
                    videos= getVideos(File.listRoots());
                    for (File f:videos)
                    {
                        list.add(f.getName().replace(".mp4",""));
                    }
                    isFirst=false;
                }
                videoList.setItems(list);
            }
        });
    }

    public ArrayList<File> getVideos(File file[])
    {
        ArrayList<File> video = new ArrayList<>();
        for (File singleFile:file)
        {
            if (singleFile.isDirectory()&& singleFile.length()!=0){
                try {
                    video.addAll(getVideos(singleFile.listFiles()));
                }catch (NullPointerException e){}
            }
            else{
                if (singleFile.getName().endsWith(".mp4"))
                {
                    video.add(singleFile);
                }
            }
        }
        return video;
    }

    public static Parent getParent() throws Exception
    {
        Parent root = FXMLLoader.load(localSongController.class.getResource("localVideo.fxml"));
        return root;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setList();
        videoList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount()==2)
                {
                    int index = videoList.getSelectionModel().getSelectedIndex();
                    VideoPlayer.index = index;
                    if (stage.isShowing())
                    {
                        VideoPlayer.player.stop();
                        stage.close();
                    }
                    else
                    {
                        if (VideoPlayer.stage!=null)
                        {
                            if (VideoPlayer.stage.isShowing())
                            {
                                VideoPlayer.player.stop();
                                VideoPlayer.stage.close();
                            }
                        }
                    }
                    VideoPlayer.Name=videoList.getSelectionModel().getSelectedItem();
                    VideoPlayer.queueSongs.addAll(list);
                    VideoPlayer.stage = stage;
                    Parent root = null;
                    try {
                        root = VideoPlayer.getRoot();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    stage.setTitle("Player");
                    stage.setScene(new Scene(root,800,600));
                    stage.show();
                }
            }
        });
    }

    public void back(ActionEvent actionEvent) throws Exception{
        if (localSongController.isLogin)
        {
            Stage stage = (Stage) videoList.getScene().getWindow();
            Parent root = Controller.getRoot();
            stage.setTitle("Ampify");
            stage.setScene(new Scene(root,600,700));
            stage.show();
        }
        else
        {
            Stage stage = (Stage) videoList.getScene().getWindow();
            Parent root = localSongController.getParent();
            stage.setTitle("local Music");
            stage.setScene(new Scene(root,600,700));
            stage.show();
        }
    }

    public static File getVideoById(int index)
    {
        return videos.get(index);
    }

}
