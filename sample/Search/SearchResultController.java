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
import sample.handleServer;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class SearchResultController implements Initializable {
    private String status="previous";
    String Username=Controller.Username;
    public TextField filterFor;
    public ListView<String> resultList;
    public Label recentSearches;
    private ObservableList searchedSongs = FXCollections.observableArrayList();
    public static Set<Integer> Ids = new HashSet<Integer>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        previousHistory(Username);
        resultList.setItems(searchedSongs);
        resultList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (status.equals("search"))
                {

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

    public void back(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) filterFor.getScene().getWindow();
        Parent root = Controller.getRoot();
        stage.setScene(new Scene(root,800,600));
        stage.show();
    }
}
