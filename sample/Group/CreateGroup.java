package sample.Group;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Controller;
import sample.handleServer;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateGroup implements Initializable {

    public TextField gName,admin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        admin.setText(Controller.Username);
        admin.setEditable(false);
    }

    public void create(ActionEvent actionEvent) throws Exception{
        handleServer server = new handleServer();
        String result=null;
        if (gName.getText()!=null && !gName.getText().equals(""))
        {
            result = server.createGroup(admin.getText(),gName.getText());
            if (!result.equals("group exist"))
            {
                group.GNAME = gName.getText();
                Stage stage = (Stage) admin.getScene().getWindow();
                Parent root = group.getRoot();
                stage.setTitle(gName.getText());
                stage.setScene(new Scene(root,600,600));
                stage.show();

            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Group name already exist .\nchange group name and try again.");
                alert.show();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Group name can't be empty");
            alert.show();
        }
    }

    public void back(ActionEvent actionEvent) {
    }

    public static Parent getRoot() throws Exception
    {
        Parent root = FXMLLoader.load(CreateGroup.class.getResource("createGroup.fxml"));
        return root;
    }


}
