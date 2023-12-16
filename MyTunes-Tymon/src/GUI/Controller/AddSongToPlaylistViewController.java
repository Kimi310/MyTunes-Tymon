package GUI.Controller;

import BE.Song;
import BLL.DataHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddSongToPlaylistViewController implements Initializable {
    @FXML
    private TableColumn<Song,String> timeColumn;
    @FXML
    private TableColumn<Song,String> artistColumn;
    @FXML
    private TableColumn<Song,String> titleColumn;
    @FXML
    private TableView<Song> songChoicetable;
    private ObservableList<Song> data = FXCollections.observableArrayList();
    private DataHandler dh = new DataHandler();
    private Song selectedSong;
    private String playlistName;
    private PlaylistViewController controller;
    private ObservableList<Song> excludedSongs = FXCollections.observableArrayList();
    @FXML
    private void addSongToPlaylist(ActionEvent actionEvent) {
        selectedSong = songChoicetable.getSelectionModel().getSelectedItem();
        if (selectedSong!=null && !data.isEmpty()){
            dh.addSongToPlaylist(playlistName,selectedSong);
            controller.updateDataWhenSelected();
            Stage stage = (Stage) songChoicetable.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tablePropertiesOnInit();
    }

    private void tablePropertiesOnInit(){
        songChoicetable.setEditable(true);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        songChoicetable.setItems(data);
    }
    public void poluteData(){  //excludes all already added songs, so they don't collide when they are removed
        dh.getAllSongsFromDB(data);
        if (!excludedSongs.isEmpty()){
            System.out.println("Entered");
            for (Song s:excludedSongs) {
                for (Song song:data) {
                    if (s.getId() == song.getId()){
                        data.remove(song);
                        System.out.println("removed");
                        break;
                    }
                }
            }
        }
    }

    public void getPlaylistName(String playlistName){
        this.playlistName = playlistName;
    }

    public void setController(PlaylistViewController controller){
        this.controller = controller;
    }
    public void excludeAlreadyAddedSongs(ObservableList<Song> excludedSongs){
        this.excludedSongs = excludedSongs;
    }
}
