package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import sample.CustomPlaylist.Create;
import sample.CustomPlaylist.CustomPlaylistController;
import sample.History.HistoryController;
import sample.LocalSong.LocalVideoController;
import sample.LocalSong.localSongController;
import sample.Search.SearchResultController;


import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    public static String Username;
    public int Separator;
    public  Label username;
    public ListView<String> recent,recommended,trending;
    public ComboBox<String> playlists;
    public ComboBox<String> groups;

    private ObservableList<String> rec = FXCollections.observableArrayList();
    private ObservableList<String> recom = FXCollections.observableArrayList();
    private ObservableList<String> tre = FXCollections.observableArrayList();
    private ObservableList<String> playLists = FXCollections.observableArrayList();
    private ArrayList<Integer> recentId ;
    public static ArrayList<String > recentTitle ;
    private ArrayList<Integer> trendingId;
    public static ArrayList<String> trendingTitle;
    public static Set<Integer> recommendedId1;
    public static ArrayList<String> reTimeTitle;
    public static Set<Integer> recommendedId2 ;
    public static ArrayList<String> reLikeTitle;

    handleServer handle1 = new handleServer();
    handleServer handle2 = new handleServer();
    handleServer handle3 = new handleServer();
    handleServer handle4 = new handleServer();

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(Controller.class.getResource("sample.fxml"));
        return root;
    }

    public void setList()
    {
        Calendar calendar =  GregorianCalendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        recentId  = handle1.getRecent(Username);
        trendingId = handle2.getTrending();
        handle3.getRecommended(Username,hour+"");

        Separator = recommendedId1.size();

        rec.addAll(recentTitle);
        tre.addAll(trendingTitle);
        recom.addAll(reTimeTitle);
        recom.addAll(reLikeTitle);

        recent.setItems(rec);
        trending.setItems(tre);
        recommended.setItems(recom);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText(Username);
        setList();
        playLists.add("Create Playlist");
        playLists.addAll(handle4.getPlaylists(Username));
        playlists.setItems(playLists);

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

    public void search(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) username.getScene().getWindow();
        Parent root = SearchResultController.getRoot();
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public void goToPlaylist(ActionEvent actionEvent) throws Exception{
        if (playlists.getSelectionModel().getSelectedItem()!="Create Playlist")
        {
            CustomPlaylistController.pName = playlists.getSelectionModel().getSelectedItem();
            Stage stage = (Stage) username.getScene().getWindow();
            Parent root = CustomPlaylistController.getRoot();
            stage.setTitle(playlists.getSelectionModel().getSelectedItem());
            stage.setScene(new Scene(root,600,600));
            stage.show();
        }
        else
        {
            Stage stage = (Stage) username.getScene().getWindow();
            Parent root = Create.getRoot();
            stage.setTitle("Create Playlist");
            stage.setScene(new Scene(root,400,400));
            stage.show();
        }
    }
}
