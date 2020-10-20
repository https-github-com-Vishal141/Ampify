package sample.Player;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sample.LocalSong.localSongController;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class AudioPlayer implements Initializable {
    public  Label songName;
    public static String name;
    public Label cTime,tTime;
    public Slider slider;
    public Button play;
    public ComboBox<String> queue;
    public static ObservableList<String> queueSongs = FXCollections.observableArrayList();

    public static MediaPlayer.Status status;
    public Media media;
    public static MediaPlayer mediaPlayer;
    public static ArrayList<File> songFiles;
    public static int index;
    public static boolean isLocal = false;
    int min=0;

    public void back(ActionEvent actionEvent) {

    }

    public void play(ActionEvent actionEvent) {
        status = mediaPlayer.getStatus();
        if (status== MediaPlayer.Status.PLAYING)
        {
            if (mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getTotalDuration())){
                next(actionEvent);
            }
            else {
                mediaPlayer.pause();
                play.setText("Play");
            }
        }
        if (status== MediaPlayer.Status.HALTED || status== MediaPlayer.Status.STOPPED || status== MediaPlayer.Status.PAUSED)
        {
            mediaPlayer.play();
            play.setText("Pause");
        }
    }

    public void next(ActionEvent actionEvent) {
        if (isLocal)
        {
            int len = queueSongs.size();
            index = (index+1)%len;
            songName.setText(queueSongs.get(index));
            File file = localSongController.getSongById(index);
            media = new Media(file.toURI().toString());
        }
        else {

        }
        mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateSlider();
            }
        });
        mediaPlayer.play();
    }

    public void playNext()
    {
        if (isLocal)
        {
            int len = queueSongs.size();
            index = (index+1)%len;
            songName.setText(queueSongs.get(index));
            File file = localSongController.getSongById(index);
            media = new Media(file.toURI().toString());
        }
        else {

        }
        mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateSlider();
            }
        });
        mediaPlayer.play();
    }


    public void previous(ActionEvent actionEvent) {
        if (isLocal)
        {
            int len = queueSongs.size();
            index = ((index-1)<0)?(len-1):(index-1);
            songName.setText(queueSongs.get(index));
            File file = localSongController.getSongById(index);
            media = new Media(file.toURI().toString());
        }
        else {

        }
        mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateSlider();
            }
        });
        mediaPlayer.play();
    }

    public static Parent getRoot()throws Exception
    {
        Parent root = FXMLLoader.load(AudioPlayer.class.getResource("audioPlayer.fxml"));
        return root;
    }

    public void updateSlider()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                slider.setValue(mediaPlayer.getCurrentTime().toMillis()*100/mediaPlayer.getTotalDuration().toMillis());
                float seconds = Float.parseFloat(mediaPlayer.getCurrentTime().toSeconds()+"");
                float minutes = Float.parseFloat(mediaPlayer.getCurrentTime().toMinutes()+"");
                int m = (int) minutes;
                int second = (int) seconds%60;
                String time1 = String.format("%2d:%2d",m,second);
                cTime.setText(time1);
                float totolmin = Float.parseFloat(mediaPlayer.getTotalDuration().toMinutes()+"");
                float totolsec = Float.parseFloat(mediaPlayer.getTotalDuration().toSeconds()+"");
                int M = (int) totolmin;
                int sec = (int) totolsec%60;
                String time2 = String.format("%2d:%2d",M,sec);
                tTime.setText(time2);
                if (mediaPlayer.getCurrentTime().greaterThanOrEqualTo(mediaPlayer.getTotalDuration()))
                {
                    playNext();
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        songName.setText(name);
        queue.setItems(queueSongs);
        slider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (slider.isPressed())
                {
                    mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(slider.getValue()/100));
                }
                if (slider.getValue()==1.0)
                    playNext();

            }
        });

        if (mediaPlayer!=null)
        {
            status = mediaPlayer.getStatus();
            if (status== MediaPlayer.Status.PLAYING || status== MediaPlayer.Status.PAUSED)
                mediaPlayer.stop();
        }

        File file = songFiles.get(index);
        media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateSlider();
            }
        });
        mediaPlayer.play();
    }

    public void shuffle(ActionEvent actionEvent) {
        if (isLocal)
        {
            Random ran = new Random();
            int len = queueSongs.size();
            index = ran.nextInt(len);
            File file = localSongController.getSongById(index);
            songName.setText(queueSongs.get(index));
            media = new Media(file.toURI().toString());
        }
        else {

        }
        status = mediaPlayer.getStatus();
        if (status == MediaPlayer.Status.PLAYING || status== MediaPlayer.Status.PAUSED)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateSlider();
            }
        });
        mediaPlayer.play();
    }

    public void repeat(ActionEvent actionEvent) {
        mediaPlayer.seek(mediaPlayer.getStartTime());
        min=0;
    }

    public void playQueue(ActionEvent actionEvent) {
        index = queue.getSelectionModel().getSelectedIndex();
        if (isLocal)
        {
            File file = localSongController.getSongById(index);
            songName.setText(queueSongs.get(index));
            media = new Media(file.toURI().toString());
        }
        else {

        }
        status = mediaPlayer.getStatus();
        if (status == MediaPlayer.Status.PLAYING || status== MediaPlayer.Status.PAUSED)
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updateSlider();
            }
        });
        mediaPlayer.play();
    }
}
