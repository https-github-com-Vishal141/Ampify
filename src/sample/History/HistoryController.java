package sample.History;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import sample.DatabaseHandler;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class HistoryController extends DatabaseHandler implements Initializable {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet rs;
    public static String username;

    @FXML
    protected ListView<String> historySongs;
    protected ObservableList<String> list = FXCollections.observableArrayList();
    protected ArrayList<Integer> songIds = new ArrayList<Integer>();
    protected ArrayList<String> time = new ArrayList<String>();

    public void getHistory()
    {
        String query = "SELECT songId,time FROM History WHERE username=? ORDER BY serial_no DESC";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            rs = preparedStatement.executeQuery();
            while (rs.next())
            {
                songIds.add(rs.getInt("songId"));
                time.add(rs.getString("time"));
            }
        }catch (SQLException e){
            return;
        }
        finally {
            close();
        }
    }

    public void setList()
    {
        int i=0;
        for (int id:songIds)
        {
            String title = getSongTitle(id);
            list.add(title+"\t"+time.get(i));
            i++;
        }
        historySongs.setItems(list);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connection = getConnection();
        historySongs.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
    }
}
