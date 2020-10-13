package sample.LocalSong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setList();
        videoList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
    }
}
