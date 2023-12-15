package GUI.Controller;

import BE.Playlist;
import BE.Song;
import BLL.DataHandler;
import BLL.MusicPlayer;
import BLL.ViewProperitesSetter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlaylistViewController implements Initializable {
    @FXML
    private  TableColumn<Playlist,String> playlistNameColumn;
    @FXML
    private  TableView<Playlist> playlisttable;
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
    private TableView<Song> songtable;
    @FXML
    private TableColumn<Song,String> titleColumn;
    @FXML
    private TableColumn<Song,String> artistColumn;
    @FXML
    private TableColumn<Song,String> timeColumn;
    @FXML
    private Slider progressslider;
    private MusicPlayer player;
    private ViewProperitesSetter setter = new ViewProperitesSetter();
    private final ObservableList<Playlist> playlists = FXCollections.observableArrayList();
    private ObservableList<Song> data = FXCollections.observableArrayList();
    private DataHandler dh = new DataHandler();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtons();
        setter.setVolumeListener(volumeSlider, player);
        setter.setProgressOnMouse(progressslider,player);
        polutePlaylistsObservableList();
        setSongsTAbleProperties();
        setPlaylistsTAbleProperties();
    }
    public void playPauseMusicHandler(ActionEvent actionEvent) {
    }

    public void playNextSong(ActionEvent actionEvent) {
    }

    public void playPrevSong(ActionEvent actionEvent) {

    }
    private void polutePlaylistsObservableList(){
        dh.getAllPlaylists(playlists);
    }
    private void setPlaylistsTAbleProperties(){
        playlisttable.setEditable(true);
        playlistNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playlisttable.setItems(playlists);
        playlisttable.setRowFactory(tv -> {
            TableRow<Playlist> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount()==2 && !row.isEmpty()){
                    Playlist playlist = row.getItem();
                    data = playlist.getSongs();
                    System.out.println(data.get(0));
                }
            });
            return row;
        });
    }
    private void setSongsTAbleProperties(){
        songtable.setEditable(true);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        songtable.setItems(data);
        /*
        playlisttable.setRowFactory(tv -> {
            TableRow<Playlist> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount()==2 && !row.isEmpty()){
                    Playlist playlist = row.getItem();
                    data = playlist.getSongs();
                }
            });
            return row;
        });
         */
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

    public void createNewPlaylistPopUp(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CreatePlaylistView.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
