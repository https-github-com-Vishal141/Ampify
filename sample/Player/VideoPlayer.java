package sample.Player;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import sample.LocalSong.LocalVideoController;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class VideoPlayer implements Initializable {

    public static MediaPlayer player;
    public static boolean Status = false;
    public Media media;
    public static String Name;
    public static int index;
    public File file;
    public static ObservableList<String> queueSongs = FXCollections.observableArrayList();
    public ComboBox queue;
    public Label name;
    public static Stage stage;

    @FXML
    private MediaView mediaView;
    @FXML
    private Button playBtn;
    @FXML
    private Slider timeSlider;

    @FXML
    private Slider valumeSlider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        checkAudio();
        queue.setItems(queueSongs);
        name.setText(Name);
        try {

            if(player!=null)
            {
                player.dispose();

            }
            file = LocalVideoController.getVideoById(index);
            media = new Media(file.toURI().toString());
            player = new MediaPlayer(media);
            mediaView.setMediaPlayer(player);
            Status = true;
            playBtn.setText("pause");
            player.play();

            DoubleProperty width=mediaView.fitWidthProperty();
            DoubleProperty height=mediaView.fitHeightProperty();


            valumeSlider.setValue(player.getVolume()*100);
            valumeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    player.setVolume(valumeSlider.getValue()/100);
                }
            });


            player.setOnReady(()->{

                //when player gets ready
                timeSlider.setMin(0);
                timeSlider.setMax(player.getMedia().getDuration().toMinutes());

                timeSlider.setValue(0);
                playBtn.setText("play");

            });


            //listener on player
            player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                    Duration d=player.getCurrentTime();

                    timeSlider.setValue(d.toMinutes());
                    valumeSlider.setValue(player.getVolume()*100);

                    valumeSlider.valueProperty().addListener(new InvalidationListener() {
                        @Override
                        public void invalidated(Observable observable) {
                            player.setVolume(valumeSlider.getValue()/100);
                        }
                    });
                }
            });


            timeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    if(timeSlider.isPressed()) {
                        double val = timeSlider.getValue();
                        player.seek(new Duration(val * 60*000));
                    }


                }
            });

            DoubleProperty Width = mediaView.fitWidthProperty();
            DoubleProperty Height = mediaView.fitHeightProperty();
            Width.bind(Bindings.selectDouble(mediaView.parentProperty(),"width"));
            Height.bind(Bindings.selectDouble(mediaView.parentProperty(),"height"));

        }catch (Exception e)
        {

            e.printStackTrace();
        }
    }


    @FXML
    void preBtnClick(ActionEvent event) {
//        double d=player.getCurrentTime().toSeconds();
//        d=d-10;
//        player.seek(new Duration(d*1000));
        index = (index-1)>0?(index-1):(queueSongs.size()-1);
        file = LocalVideoController.getVideoById(index);
        name.setText(queue.getSelectionModel().getSelectedItem().toString());
        media = new Media(file.toURI().toString());
        MediaPlayer.Status status = player.getStatus();
        if (status == MediaPlayer.Status.PLAYING)
            player.stop();
        player = new MediaPlayer(media);
        mediaView.setMediaPlayer(player);
        Status = true;
        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                Duration d=player.getCurrentTime();

                timeSlider.setValue(d.toMinutes());
                valumeSlider.setValue(player.getVolume()*100);

                valumeSlider.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        player.setVolume(valumeSlider.getValue()/100);
                    }
                });
            }
        });
        player.play();


    }


    @FXML
    void nextBtnClick(ActionEvent event) {

//        double d=player.getCurrentTime().toSeconds();
//        d=d+10;
//        player.seek(new Duration(d*1000));
        index = (index+1)%queueSongs.size();
        file = LocalVideoController.getVideoById(index);
        name.setText(queue.getSelectionModel().getSelectedItem().toString());
        media = new Media(file.toURI().toString());
        MediaPlayer.Status status = player.getStatus();
        if (status == MediaPlayer.Status.PLAYING)
            player.stop();
        player = new MediaPlayer(media);
        mediaView.setMediaPlayer(player);
        Status = true;
        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                Duration d=player.getCurrentTime();

                timeSlider.setValue(d.toMinutes());
                valumeSlider.setValue(player.getVolume()*100);

                valumeSlider.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        player.setVolume(valumeSlider.getValue()/100);
                    }
                });
            }
        });
        player.play();
    }

    @FXML
    void exitPlayer(ActionEvent event) {
        player.stop();
        stage.close();
    }


    @FXML
    void play(ActionEvent event) {
        MediaPlayer.Status status=player.getStatus();
        if (status==MediaPlayer.Status.PLAYING)
        {
            player.pause();
            playBtn.setText("play");
            Status = false;
        }
        else{
            player.play();
            playBtn.setText("pause");
            Status = true;
        }
    }

    public static Parent getRoot() throws Exception
    {
        //System.out.println("inside root");
        Parent root = FXMLLoader.load(VideoPlayer.class.getResource("videoPlayer.fxml"));
        return root;
    }


    public void playQueue(ActionEvent actionEvent) {
        index = queue.getSelectionModel().getSelectedIndex();
        file = LocalVideoController.getVideoById(index);
        media = new Media(file.toURI().toString());
        name.setText(queue.getSelectionModel().getSelectedItem().toString());
        MediaPlayer.Status status = player.getStatus();
        if (status == MediaPlayer.Status.PLAYING)
            player.stop();
        player = new MediaPlayer(media);
        mediaView.setMediaPlayer(player);
        Status = true;
        player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                Duration d=player.getCurrentTime();

                timeSlider.setValue(d.toMinutes());
                valumeSlider.setValue(player.getVolume()*100);

                valumeSlider.valueProperty().addListener(new InvalidationListener() {
                    @Override
                    public void invalidated(Observable observable) {
                        player.setVolume(valumeSlider.getValue()/100);
                    }
                });
            }
        });
        player.play();
    }

    public void checkAudio()
    {
        if (AudioPlayer.stage!=null) {
            if (AudioPlayer.stage.isShowing()) {
                AudioPlayer.mediaPlayer.stop();
                AudioPlayer.stage.close();
            }
        }
    }

}