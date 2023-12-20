package GUI.Controller;

import BE.Playlist;
import BE.Song;
import BLL.DataHandler;
import BLL.MusicPlayer;
import BLL.ViewProperitesSetter;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
    private Label playinglbl;
    @FXML
    private TableView<Song> songstable;
    @FXML
    private TableColumn<Song,String> titleColumn;
    @FXML
    private TableColumn<Song,String> artistColumn;
    @FXML
    private TableColumn<Song,String> timeColumn;
    @FXML
    private Slider progressslider;
    private MusicPlayer player = new MusicPlayer();
    private ViewProperitesSetter setter = new ViewProperitesSetter();
    private final ObservableList<Playlist> playlists = FXCollections.observableArrayList();
    private ObservableList<Song> data = FXCollections.observableArrayList();
    private DataHandler dh = new DataHandler();
    private Playlist selectedPlaylist;
    private Song selectedSong;
    private int currentSongIndex;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setButtons();
        setter.setDataListener(data,progressslider,volumeSlider,playbtn,nextbtn,prevbtn);
        setter.setVolumeListener(volumeSlider, player);
        setter.setProgressOnMouse(progressslider,player);
        polutePlaylistsObservableList();
        setSongsTAbleProperties();
        setPlaylistsTAbleProperties();
    }
    @FXML
    private void playPauseMusicHandler(ActionEvent actionEvent) {
        player.playPause(playbtn);
    }

    @FXML
    private void playNextSong(ActionEvent actionEvent) {
        nextSongPlaylist();
    }

    @FXML
    private void playPrevSong(ActionEvent actionEvent) {
        if (currentSongIndex != 0){
            Song prevSong = data.get(currentSongIndex-1);
            beginSongHandler(prevSong);
        }
    }
    public void polutePlaylistsObservableList(){
        playlists.clear();
        dh.getAllPlaylists(playlists);
    }
    private void setPlaylistsTAbleProperties(){
        playlisttable.setEditable(true);
        playlistNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playlisttable.setItems(playlists);
        playlisttable.setRowFactory(tv -> {
            TableRow<Playlist> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount()==1 && !row.isEmpty()){
                    if (player.getPlayer()!=null){
                        player.pausePlayer(playbtn,playinglbl);
                    }
                    Playlist playlist = row.getItem();
                    data.clear();
                    data.addAll(playlist.getSongs());
                }else if (event.getClickCount()==2 && !row.isEmpty()){
                    Playlist playlist = row.getItem();
                    if (!playlist.getSongs().isEmpty()){
                        beginSongHandler(playlist.getSongs().get(0));
                    }
                }
            });
            return row;
        });
    }
    private void setSongsTAbleProperties(){
        songstable.setEditable(true);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        songstable.setItems(data);
        songstable.setRowFactory(tv -> {
            TableRow<Song> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount()==2 && !row.isEmpty()){
                    Song rowSong = row.getItem();
                    beginSongHandler(rowSong);
                }
            });
            return row;
        });
    }
    public void openSongsSection(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) nextbtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/MainView.fxml"));
        Parent root = loader.load();
        player.pausePlayer(playbtn, playinglbl);
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
        if (data.isEmpty()){
            playbtn.setDisable(true);
            nextbtn.setDisable(true);
            prevbtn.setDisable(true);
            progressslider.setDisable(true);
            volumeSlider.setDisable(true);
        }
    }

    @FXML
    private void createNewPlaylistPopUp(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CreatePlaylistView.fxml"));
        Parent root = loader.load();
        CreatePlaylistViewController controller = loader.getController();
        controller.setParentController(this);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    @FXML
    private void deletePlaylistFromDB(ActionEvent actionEvent) {
        selectedPlaylist = playlisttable.getSelectionModel().getSelectedItem();
        if (selectedPlaylist!=null && !playlists.isEmpty()){
            dh.deletePlaylist(selectedPlaylist);
            playlists.clear();
            dh.getAllPlaylists(playlists);
            data.clear();
        }
    }
    public void editPlaylistNameDataAccess(String oldName, String newName){
        dh.changePlaylistName(oldName,newName);
    }
    @FXML
    private void editPlaylistsName(ActionEvent actionEvent) throws IOException {
        selectedPlaylist = playlisttable.getSelectionModel().getSelectedItem();
        if (selectedPlaylist!=null && !playlists.isEmpty()) {
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CreatePlaylistView.fxml"));
            Parent root = loader.load();
            CreatePlaylistViewController controller = loader.getController();
            controller.setEditPlaylistOnInit(selectedPlaylist.getName());
            controller.setParentController(this);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }

    @FXML
    private void addSongToPlaylist(ActionEvent actionEvent) throws IOException {
        selectedPlaylist = playlisttable.getSelectionModel().getSelectedItem();
        if (selectedPlaylist!=null && !playlists.isEmpty()){
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AddSongToPlaylistView.fxml"));
            Parent root = loader.load();
            AddSongToPlaylistViewController controller = loader.getController();
            controller.setController(this);
            controller.getPlaylistName(selectedPlaylist.getName());
            controller.excludeAlreadyAddedSongs(selectedPlaylist.getSongs());
            controller.poluteData();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }
    public void updateDataWhenSelected(){
        data.clear();
        playlists.clear();
        dh.getAllPlaylists(playlists);
    }
    @FXML
    private void deleteSongFromPlaylist(ActionEvent actionEvent) {
        selectedPlaylist = playlisttable.getSelectionModel().getSelectedItem();
        if (selectedPlaylist!=null && !playlists.isEmpty()){
            selectedSong = songstable.getSelectionModel().getSelectedItem();
            if (selectedSong!=null && !data.isEmpty()){
                player.pausePlayer(playbtn, playinglbl);
                dh.removeSongFromPlaylist(selectedPlaylist.getName(),selectedSong);
                updateDataWhenSelected();
            }
        }
    }

    private void beginSongHandler(Song rowSong){ // initializes new song to be played
        for (int i=0;i<data.size();i++) {
            if (data.get(i) == rowSong){
                currentSongIndex = i;
                break;
            }
        }
        player.playNewSong(rowSong.getFile(),playbtn,progressslider,volumeSlider);
        progressslider.disableProperty().set(false);
        volumeSlider.disableProperty().set(false);
        playinglbl.setText(rowSong.getTitle());
        setter.setNextSongPlayerPlaylist(player,this);
    }
    public void nextSongPlaylist(){
        if (currentSongIndex != data.size()-1){
            Song nextSong = data.get(currentSongIndex+1);
            beginSongHandler(nextSong);
        }else if (!data.isEmpty()){
            Song nextSong = data.get(0);
            beginSongHandler(nextSong);
        }
    }
}

