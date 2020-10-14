package sample.Search;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import sample.Controller;
import sample.DatabaseHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchResultController extends DatabaseHandler implements Initializable {
    private String status="previous";
    String Username=Controller.Username;
    public TextField filterFor;
    public ListView<String> resultList;
    private ObservableList searchedSongs = FXCollections.observableArrayList();
    private ArrayList<Integer> Ids = new ArrayList<Integer>();

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet rs;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = getConnection();
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
                    getResult();
                    resultList.setItems(searchedSongs);
                }
            }
        });
    }

    public void Search(ActionEvent actionEvent) {
        status="search";
        getResult();
        resultList.setItems(searchedSongs);
        addToSearchHistory(Username,filterFor.getText());
    }

    public void getResult()
    {
        String query = "SELECT Id,Title,Artist FROM Songs WHERE Title=? OR Artist=? OR Album=?";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,filterFor.getText());
            preparedStatement.setString(2,filterFor.getText());
            preparedStatement.setString(3,filterFor.getText());
            rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                searchedSongs.add(rs.getString("Title")+"\t"+rs.getString("Artist"));
                Ids.add(rs.getInt("Id"));
            }
        }catch (SQLException e){
            searchedSongs.add("No Song Found");
        }
        finally {
            close();
        }
    }

    public void previousHistory(String uname)
    {
        String query = "SELECT searchedItems FROM searchedHistory WHERE username=?";
        try{
            connection =  getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,uname);
            rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                searchedSongs.add(rs.getString("searchedItem"));
            }
        }catch (SQLException e){
            searchedSongs.add("");
        }finally {
            close();
        }
    }
}
