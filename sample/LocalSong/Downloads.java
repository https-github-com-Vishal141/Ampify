package sample.LocalSong;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Controller;
import sample.Player.AudioPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;
import java.util.Set;

public class Downloads implements Initializable {

    public ListView<String> list;
    public static ObservableList<String> songs = FXCollections.observableArrayList();

    private static File[] songFiles;
    Stage stage = new Stage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getFileNames();
        list.setItems(songs);

        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount()==2)
                {
                    int index = list.getSelectionModel().getSelectedIndex();
                    AudioPlayer.index = index;
                    AudioPlayer.name=list.getSelectionModel().getSelectedItem();
                    localSongController.isDownload = true;
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
                    AudioPlayer.isLocal = true;
                    AudioPlayer.queueSongs.clear();
                    AudioPlayer.queueSongs.addAll(songs);
                    gotoPlayer();
                }
            }
        });
    }

    public void getFileNames()
    {
        File[] files = File.listRoots();
        String p = files[1]+"\\Ampify\\"+Controller.Username;
        if (!Files.exists(Paths.get(p)))
            return;
        songFiles = new File(p).listFiles();
        for (File file:songFiles)
        {
            songs.add(file.getName().substring(0,file.getName().indexOf(".")));
        }
    }

    public static File getSong(String title)
    {
        int index = songs.indexOf(title);
        File file = songFiles[index];
        try {
            File tempFile = File.createTempFile(file.getName().substring(0,file.getName().indexOf(".")),".mp3");
            tempFile.deleteOnExit();
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            FileInputStream inputStream = new FileInputStream(file);
            outputStream.write(inputStream.readAllBytes());
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return tempFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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


    public void back(ActionEvent actionEvent) throws Exception{
        if (localSongController.isLogin)
        {
            Stage stage = (Stage) list.getScene().getWindow();
            Parent root = Controller.getRoot();
            stage.setTitle("Ampify");
            stage.setScene(new Scene(root,600,700));
            stage.show();
        }
        else
        {
            Stage stage = (Stage) list.getScene().getWindow();
            Parent root = localSongController.getParent();
            stage.setTitle("local Music");
            stage.setScene(new Scene(root,600,700));
            stage.show();
        }
    }

    public static Parent getRoot() throws Exception
    {
        return FXMLLoader.load(Downloads.class.getResource("downloads.fxml"));
    }
}
