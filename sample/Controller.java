package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.converter.DurationConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;


import java.io.File;

public class Controller {


MediaPlayer player;
    @FXML
    private MediaView mediaView;
    @FXML
    private Button playBtn;



    @FXML
    private Button preBtn;


    @FXML
    private Button nextBtn;


    @FXML
    private Slider timeSlider;



    @FXML
    void openSongMenu(ActionEvent event) {
        try {
            System.out.println("Open songs clicked");
            FileChooser chooser = new FileChooser();
            File file = chooser.showOpenDialog(null);
            Media m = new Media(file.toURI().toURL().toString());


           if(player!=null)
           {
               player.dispose();

           }



            player = new MediaPlayer(m);
            mediaView.setMediaPlayer(player);


            player.setOnReady(()->{

                //when player gets ready
                timeSlider.setMin(0);
                timeSlider.setMax(player.getMedia().getDuration().toMinutes());

                timeSlider.setValue(0);
                playBtn.setText("paly");

            });


            //listener on player
            player.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration t1) {
                    Duration d=player.getCurrentTime();

                    timeSlider.setValue(d.toMinutes());
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



        }catch (Exception e)
        {

            e.printStackTrace();
        }
    }




    @FXML
    void preBtnClick(ActionEvent event) {
        double d=player.getCurrentTime().toSeconds();
        d=d-10;
        player.seek(new Duration(d*1000));


    }


    @FXML
    void nextBtnClick(ActionEvent event) {

        double d=player.getCurrentTime().toSeconds();
        d=d+10;
        player.seek(new Duration(d*1000));
    }









    @FXML
    void play(ActionEvent event) {
        MediaPlayer.Status status=player.getStatus();
        if (status==MediaPlayer.Status.PLAYING)
        {
            player.pause();
            playBtn.setText("play");

        }
        else{
        player.play();
        playBtn.setText("pause");
        }
    }




}
