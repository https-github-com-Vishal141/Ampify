package sample.History;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Controller;
import sample.Player.AudioPlayer;
import sample.handleServer;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HistoryController  implements Initializable {
    public static String username;

    @FXML
    protected ListView<String> historySongs;
    protected ObservableList<String> list = FXCollections.observableArrayList();
    protected ArrayList[] history;
    protected ArrayList<Integer> songIds;
    protected ArrayList<String> songTitle ;
    protected ArrayList<String > date ;
    protected ArrayList<String> time ;

    Stage stage = new Stage();

    handleServer handle = new handleServer();

    public void setList()
    {
        history = handle.getHistory(username);
        songIds = history[0];
        songTitle = history[1];
        date = history[2];
        time = history[3];
        for(int i=0;i<songIds.size();i++)
            list.add(songTitle.get(i)+"\t"+date.get(i)+"\t"+time.get(i));
        historySongs.setItems(list);
    }

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(HistoryController.class.getResource("history.fxml"));
        return root;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setList();
        historySongs.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount()==2)
                {
                    int index = historySongs.getSelectionModel().getSelectedIndex();
                    AudioPlayer.index = index;
                    //AudioPlayer.name=historySongs.getSelectionModel().getSelectedItem();
                    AudioPlayer.name = songTitle.get(index);
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
                    AudioPlayer.queueSongs.addAll(songTitle);
                    gotoPlayer();
                }
            }
        });
    }

    public void back(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) historySongs.getScene().getWindow();
        Parent root = Controller.getRoot();
        stage.setTitle("Ampify");
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
}
