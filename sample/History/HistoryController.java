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
import sample.handleServer;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HistoryController  implements Initializable {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet rs;
    public static String username;

    @FXML
    protected ListView<String> historySongs;
    protected ObservableList<String> list = FXCollections.observableArrayList();
    protected ArrayList[] history;
    protected ArrayList<Integer> songIds;
    protected ArrayList<String> songTitle ;
    protected ArrayList<String > date ;
    protected ArrayList<String> time ;

    handleServer handle = new handleServer();

    public void setList()
    {
        history = handle.getHistory(username);
        songIds = history[0];
        songTitle = history[1];
        date = history[2];
        time = history[3];
        for(int i=0;i<songIds.size();i++)
            list.add(songTitle.get(i)+"\t"+date.get(i)+time.get(i));
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
}
