package sample.CustomPlaylist;

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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Controller;
import sample.Search.SearchResultController;
import sample.handleServer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

public class AddSongController implements Initializable {
   @FXML
   public ListView<String> songList;
   private ObservableList titles = FXCollections.observableArrayList();
   private static ArrayList<Integer> selectedIds = new ArrayList<Integer>();
   public TextField sSong;
   public static Set<Integer> Ids;
   public boolean status=false;
   public static String USER;

   handleServer handle1 = new handleServer();

    public void AddSong(ActionEvent actionEvent) throws Exception {
        for (int id:selectedIds)
        {
            handleServer handle2 = new handleServer();
            handle2.addToPlaylist(USER,CustomPlaylistController.pName,id);
        }
       Stage stage = (Stage) songList.getScene().getWindow();
       Parent root = FXMLLoader.load(getClass().getResource("customPlaylist.fxml"));
       stage.setTitle(CustomPlaylistController.pName);
       stage.setScene(new Scene(root,600,600));
       stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titles.addAll(handle1.getAllSong());
        songList.setItems(titles);
        songList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
               if (status)
               {
                   int index = songList.getSelectionModel().getSelectedIndex();
                   int id = get(index);
                   add(id);
               }else {
                   // selectedSongs.add(songList.getSelectionModel().getSelectedItem());
                   int index = titles.indexOf(songList.getSelectionModel().getSelectedItem());
                   selectedIds.add(index+1);
               }
            }
        });

    }

    public void back(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) songList.getScene().getWindow();
        Parent root = Controller.getRoot();
        stage.setTitle("Ampify");
        stage.setScene(new Scene(root,800,600));
        stage.show();
    }

    public void search(ActionEvent actionEvent) {
        status = true;
        titles.clear();
        String item = sSong.getText();
        handleServer handle3 = new handleServer();
        titles.addAll(handle3.getResult1(item));
        songList.setItems(titles);
    }

    public void add(int id)
    {
        handleServer handle = new handleServer();
        handle.addToPlaylist(Controller.Username,CustomPlaylistController.pName,id);
        Stage stage = (Stage) songList.getScene().getWindow();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("customPlaylist.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle(CustomPlaylistController.pName);
        stage.setScene(new Scene(root,600,600));
        stage.show();
    }

    public int get(int index)
    {
        int i=0;
        for (int id:Ids)
        {
            if (i==index)
                return id;
            i++;
        }
        return -1;
    }

}
