package sample.Search;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;
import sample.Controller;
import sample.CustomPlaylist.CustomPlaylistController;
import sample.Player.AudioPlayer;
import sample.handleServer;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

public class SearchResultController implements Initializable {
    private String status="previous";
    String Username=Controller.Username;
    public TextField filterFor;
    public ListView<String> resultList;
    public Label recentSearches;
    private ObservableList searchedSongs = FXCollections.observableArrayList();
    public static Set<Integer> Ids = new HashSet<Integer>();
    Stage stage = new Stage();
   // public static boolean forAdd=false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        previousHistory(Username);
        resultList.setItems(searchedSongs);
        resultList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (status.equals("search"))
                {
                    handleServer server = new handleServer();
                    AudioPlayer.index = 0;
                    String n = resultList.getSelectionModel().getSelectedItem();
                    String name = split(n);
                    AudioPlayer.name=name;
                    if (stage.isShowing())
                    {
                        stage.close();
                        AudioPlayer.mediaPlayer.stop();
                    }
                    else
                    {
                        if (AudioPlayer.stage!=null)
                        {
                            if (AudioPlayer.stage.isShowing())
                            {
                                AudioPlayer.stage.close();
                                AudioPlayer.mediaPlayer.stop();
                            }
                        }
                    }
                    AudioPlayer.isLocal = false;
                    AudioPlayer.queueSongs.clear();
                    AudioPlayer.queueSongs.add(name);
                    AudioPlayer.queueSongs.addAll(server.getRecent(Controller.Username));
                    gotoPlayer();
                }
                else{
                    status="search";
                    filterFor.setText(resultList.getSelectionModel().getSelectedItem());
                    getResult();
                    resultList.setItems(searchedSongs);
                }
            }
        });
    }

    public void Search(ActionEvent actionEvent) {
        handleServer handle = new handleServer();
        status="search";
        getResult();
        resultList.setItems(searchedSongs);
        handle.addToSearchHistory(Username,filterFor.getText());
    }

    public void getResult()
    {
        handleServer handle = new handleServer();
        searchedSongs.clear();
        recentSearches.setText("Search Result");
        searchedSongs.addAll(handle.getResult(filterFor.getText()));
    }

    public void previousHistory(String uname)
    {
       handleServer handle = new handleServer();
       searchedSongs.addAll(handle.searchHistory(uname));
    }

    public static Parent getRoot()throws Exception
    {
        Parent root = FXMLLoader.load(SearchResultController.class.getResource("searchResult.fxml"));
        return root;
    }

    public int get(Set<Integer> set,int index)
    {
        int i=0;
        for (int id:set)
        {
            if (i==index)
                return id;
            i++;
        }
        return -1;
    }


    public void back(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) filterFor.getScene().getWindow();
        Parent root = Controller.getRoot();
        stage.setScene(new Scene(root,800,600));
        stage.show();
    }

    public void gotoPlayer()
    {
        Parent root = null;
        try {
            root = AudioPlayer.getRoot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.setTitle("Music Player");
        stage.setScene(new Scene(root,600,600));
        AudioPlayer.stage = stage;
        stage.show();
    }

    public String split(String str)
    {
        StringTokenizer tokenizer = new StringTokenizer(str,"\t");
        return tokenizer.nextToken();
    }
}
