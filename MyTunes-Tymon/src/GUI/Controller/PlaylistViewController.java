package GUI.Controller;

import BLL.MusicPlayer;
import BLL.ViewProperitesSetter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistViewController implements Initializable {
    @FXML
    private  TableColumn playlistNameColumn;
    @FXML
    private  TableView playlisttable;
    @FXML
    private  Button editPlaylistbtn;
    @FXML
    private  Button createPlaylistbtn;
    @FXML
    private  Button deletePlaylistbtn;
    @FXML
    private  Button deleteSongFromPlaylistbtn;
    @FXML
    private  Button addSongToPlaylistbtn;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Button playbtn;
    @FXML
    private Button nextbtn;
    @FXML
    private Button prevbtn;
    @FXML
    private TextField filtertxt;
    @FXML
    private Label playinglbl;
    @FXML
    private TableView songtable;
    @FXML
    private TableColumn titleColumn;
    @FXML
    private TableColumn artistColumn;
    @FXML
    private TableColumn categoryColumn;
    @FXML
    private TableColumn timeColumn;
    @FXML
    private Slider progressslider;
    private MusicPlayer player;
    private ViewProperitesSetter setter = new ViewProperitesSetter();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtons();
        setter.setVolumeListener(volumeSlider, player);
        setter.setProgressOnMouse(progressslider,player);
    }
    public void playPauseMusicHandler(ActionEvent actionEvent) {
    }

    public void playNextSong(ActionEvent actionEvent) {
    }

    public void playPrevSong(ActionEvent actionEvent) {
    }


    public void openSongsSection(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) nextbtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainView.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void setButtons(){ // sets images to button
        nextbtn.setGraphic(new ImageView("Images/next.png"));
        prevbtn.setGraphic(new ImageView("Images/prev.png"));
        playbtn.setGraphic(new ImageView("Images/play.png"));
        deletePlaylistbtn.setGraphic(new ImageView("Images/delete.png"));
        editPlaylistbtn.setGraphic(new ImageView("Images/edit.png"));
        createPlaylistbtn.setGraphic(new ImageView("Images/add.png"));
        addSongToPlaylistbtn.setGraphic(new ImageView("Images/add.png"));
        deleteSongFromPlaylistbtn.setGraphic(new ImageView("Images/delete.png"));
    }

}
