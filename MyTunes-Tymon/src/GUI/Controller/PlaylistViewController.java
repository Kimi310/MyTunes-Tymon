package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistViewController implements Initializable {
    public TableColumn playlistNameColumn;
    public TableView playlisttable;
    public Button editPlaylistbtn;
    public Button createPlaylistbtn;
    public Button deletePlaylistbtn;
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtons();
    }
}
