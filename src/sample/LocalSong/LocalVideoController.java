package sample.LocalSong;

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
import sample.Controller;
import sample.Player.VideoPlayer;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LocalVideoController implements Initializable {
    @FXML
    ListView<String > videoList;

    ObservableList<String> list = FXCollections.observableArrayList();
    ArrayList<File> videos = new ArrayList<>();


    public void setList()
    {
        videos = getVideos(File.listRoots());
        for (File f:videos)
        {
            list.add(f.getName().replace(".mp4",""));
        }
        videoList.setItems(list);
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
                int index = videoList.getSelectionModel().getSelectedIndex();
                File file = videos.get(index);
                Media media = new Media(file.toURI().toString());
                if (VideoPlayer.status)
                {
                    VideoPlayer.mediaPlayer.stop();
                }
                VideoPlayer.status = true;
                VideoPlayer.media = media;
                VideoPlayer.Name=videoList.getSelectionModel().getSelectedItem();
                VideoPlayer.previousName= "Local Videos";
                try {
                    VideoPlayer.previousRoot = getParent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Stage stage = (Stage) videoList.getScene().getWindow();
                Parent root = null;
                try {
                    root = VideoPlayer.getRoot();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                stage.setTitle("Player");
                stage.setScene(new Scene(root,600,600));
                stage.show();
            }
        });
    }

    public void back(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) videoList.getScene().getWindow();
        Parent root = Controller.getRoot();
        stage.setTitle("Ampify");
        stage.setScene(new Scene(root,600,700));
        stage.show();
    }
}
