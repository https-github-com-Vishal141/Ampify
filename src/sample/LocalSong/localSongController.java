package sample.LocalSong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class localSongController implements Initializable {
    @FXML
    public ListView<String> myList;
    Media media;
    MediaPlayer mediaPlayer;

    ObservableList<String> list = FXCollections.observableArrayList();
    ArrayList<File> songs = new ArrayList<>();


    public void setList()
    {
        songs = getSongs(File.listRoots());
        for (File f:songs)
        {
            list.add(f.getName().replace(".mp3",""));
        }
        myList.setItems(list);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setList();
        myList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
    }
}
