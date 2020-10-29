package sample.Player;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.media.EqualizerBand;

import java.net.URL;
import java.util.ResourceBundle;

public class Equalizer implements Initializable {

    @FXML
    public  Slider s1,s2,s3,s4,s5;
   // Slider[] sliders = {s1,s2,s3,s4,s5};

    private static final double START_FREQ = 250.0;
    private static final int BAND_COUNT=5;

    double min = EqualizerBand.MIN_GAIN;
    double max = EqualizerBand.MAX_GAIN;
    double mid = (max-min)/2;
    double freq = START_FREQ;

    public static ObservableList<EqualizerBand> bands = AudioPlayer.mediaPlayer.getAudioEqualizer().getBands();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AudioPlayer.mediaPlayer.getAudioEqualizer().setEnabled(true);
        bands.clear();
        setBands();
    }

    public void setBands()
    {
        for (int j=0;j<BAND_COUNT;j++)
        {
            double t = (double)j/(double)(BAND_COUNT-1)*(2*Math.PI);
            double scale = 0.4*(1+Math.cos(t));
            double gain = min+mid+(min*scale);
            bands.add(new EqualizerBand(freq,freq/2,gain));
            freq *= 2;
        }

//        for (int i=0;i<bands.size();i++)
//        {
//            sliders[i].valueProperty().bindBidirectional(bands.get(i).gainProperty());
//        }
        s1.valueProperty().bindBidirectional(bands.get(0).gainProperty());
        s2.valueProperty().bindBidirectional(bands.get(1).gainProperty());
        s3.valueProperty().bindBidirectional(bands.get(2).gainProperty());
        s4.valueProperty().bindBidirectional(bands.get(3).gainProperty());
        s5.valueProperty().bindBidirectional(bands.get(4).gainProperty());
    }

}
