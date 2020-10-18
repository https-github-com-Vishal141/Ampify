package sample.Player;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class VideoPlayer implements Initializable {
    public Label videoName,currentTime;
    public MediaView mediaView;
    public Button videoStatus;
    public Slider slider;
    public static MediaPlayer mediaPlayer;
    public static Media media;
    public static volatile boolean status;
    public static Parent previousRoot;
    public static String previousName;
    public static String Name;

    public void back(ActionEvent actionEvent) {
        Stage stage = (Stage) videoStatus.getScene().getWindow();
        stage.setTitle(previousName);
        stage.setScene(new Scene(previousRoot,600,600));
        stage.show();
    }

    public void play(ActionEvent actionEvent) {
        if (status==true)
        {
            status=false;
            videoStatus.setText("Play");
            mediaPlayer.pause();
        }
        else
        {
            status = true;
            videoStatus.setText("Pause");
            mediaPlayer.play();
        }
    }

    public void previos(ActionEvent actionEvent) {
    }

    public void next(ActionEvent actionEvent) {
    }

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(VideoPlayer.class.getResource("videoPlayer.fxml"));
        return root;
    }

    public void playVideo(Media m)
    {
       new Thread(new Runnable() {
           @Override
           public void run() {
               if (status)
               {
                   Platform.runLater(new Runnable() {
                       @Override
                       public void run() {
                           mediaPlayer = new MediaPlayer(m);
                           mediaView.setMediaPlayer(mediaPlayer);
                           Double time = mediaPlayer.getMedia().getDuration().toMinutes();
                           currentTime.setText(time+"");
                           mediaPlayer.play();
                           slider.setMax(mediaPlayer.getTotalDuration().toSeconds());
                           slider.setMin(0.0);
                           slider.setValue(mediaPlayer.getCurrentTime().toSeconds());
                       }
                   });
               }
           }
       }).start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (status)
            videoStatus.setText("Pause");
        else
            videoStatus.setText("Play");
        videoName.setText(Name);
        playVideo(media);
        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(),"width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(),"height"));
    }
}
