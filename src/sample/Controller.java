package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import sample.History.HistoryController;
import sample.LocalSong.LocalVideoController;
import sample.LocalSong.localSongController;


import java.net.URL;
import java.util.*;

public class Controller extends DatabaseHandler implements Initializable {

    public static String Username;
    public int Separator;
    public  Label username;
    public Button profile;
    public ListView<String> recent,recommended,trending;

    private ObservableList<String> rec = FXCollections.observableArrayList();
    private ObservableList<String> recom = FXCollections.observableArrayList();
    private ObservableList<String> tre = FXCollections.observableArrayList();
    private ArrayList<Integer> recentId ;
    private ArrayList<Integer> trendingId;
    private Set<Integer> recommendedId1;
    private Set<Integer> recommendedId2 ;

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(Controller.class.getResource("sample.fxml"));
        return root;
    }

    public void setList()
    {
        Calendar calendar =  GregorianCalendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        recentId  = getRecent(username.getText());
        trendingId = getTrending();
        recommendedId1 = getRecommendedOnTime(""+hour,username.getText());
        recommendedId2 = getRecommendedOnLikes();

        Separator = recommendedId1.size();
        String Title;
        for (int id:recentId)
        {
            Title = getSongTitle(id);
            rec.add(Title);
        }

        for (int id:trendingId)
        {
            Title = getSongTitle(id);
            tre.add(Title);
        }

        for (int id:recommendedId1)
        {
            Title = getSongTitle(id);
            recom.add(Title);
        }

        for (int id:recommendedId2)
        {
            Title = getSongTitle(id);
            recom.add(Title);
        }

        recent.setItems(rec);
        trending.setItems(tre);
        recommended.setItems(recom);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(Username);
        setList();
    }

    public void history(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) username.getScene().getWindow();
        HistoryController.username = username.getText();
        Parent root = HistoryController.getRoot();
        stage.setTitle("History");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public void likes(ActionEvent actionEvent) {

    }

    public void local_music(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) username.getScene().getWindow();
        Parent root = localSongController.getParent();
        stage.setTitle("Local Songs");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public void local_video(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) username.getScene().getWindow();
        Parent root = LocalVideoController.getParent();
        stage.setTitle("Local Videos");
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }
}
