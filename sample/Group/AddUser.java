package sample.Group;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.handleServer;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUser implements Initializable {

    public TextField gName,username;
    public static String GNAME;

    public void add(ActionEvent actionEvent) throws Exception{
        handleServer handle = new handleServer();
        if (username.getText()!="")
        {
            String result = handle.addUser(GNAME,username.getText());
            if (!result.equals("user do not exist"))
            {
                group.GNAME = GNAME;
                Stage stage = (Stage) gName.getScene().getWindow();
                Parent root = group.getRoot();
                stage.setTitle(GNAME);
                stage.setScene(new Scene(root,600,600));
                stage.show();
            }
            else
            {
                System.out.println("user not exist");
            }
        }
        else
        {
            System.out.println("afad");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gName.setText(GNAME);
        gName.setEditable(false);
    }

    public void back(ActionEvent actionEvent) {
    }

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(AddUser.class.getResource("addUser.fxml"));
        return root;
    }
}
