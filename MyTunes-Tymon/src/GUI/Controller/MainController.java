package GUI.Controller;

import BE.Song;
import BLL.DataHandler;
import BLL.FilterHandler;
import BLL.MusicPlayer;
import BLL.ViewProperitesSetter;
import DAL.DataBaseAccess;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button deletebtn;
    @FXML
    private Button editbtn;
    @FXML
    private Button addbtn;
    @FXML
    private Slider volumeSlider;
    @FXML
    private Slider progressslider;
    @FXML
    private TextField filtertxt;
    @FXML
    private TableColumn<Song,String> titleColumn;
    @FXML
    private TableColumn<Song,String> artistColumn;
    @FXML
    private TableColumn<Song,String> categoryColumn;
    @FXML
    private TableColumn<Song,String> timeColumn;

    @FXML
    private Label playinglbl;
    @FXML
    private TableView<Song> songtable = new TableView<Song>();
    @FXML
    private Button playbtn;
    @FXML
    private Button nextbtn;
    @FXML
    private Button prevbtn;
    private int currentSongIndex;
    private MusicPlayer player = new MusicPlayer();
    private final ObservableList<Song> data = FXCollections.observableArrayList();
    private Song selectedSong;
    private int selectedSongIndex;
    private ViewProperitesSetter setter = new ViewProperitesSetter();
    private DataHandler dh = new DataHandler();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        poluteData();
        setButtons();
        setTablePropertiesOnInit();
        setter.setVolumeListener(volumeSlider,player);
        setter.setProgressOnMouse(progressslider,player);
        setDataListener();
        progressslider.disableProperty().set(true);
        volumeSlider.disableProperty().set(true);
    }

    private void setDataListener() {
        data.addListener(new ListChangeListener<Song>() {
            @Override
            public void onChanged(Change<? extends Song> c) {
                if (data.isEmpty()){
                    progressslider.disableProperty().set(true);
                    volumeSlider.disableProperty().set(true);
                    playbtn.setDisable(true);
                    nextbtn.setDisable(true);
                    prevbtn.setDisable(true);
                }else {
                    playbtn.setDisable(false);
                    nextbtn.setDisable(false);
                    prevbtn.setDisable(false);
                }
            }
        });
    }

    @FXML
    private void addSong(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AddSongView.fxml"));
        Parent root = loader.load();
        AddSongViewController addSong = loader.getController();
        addSong.setParentController(this);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void addSongToTable(Song song) throws SQLException {
        dh.addSongToDB(song);
        dh.updateSongsFromDB(data);
    }
    public void playPauseMusicHandler(ActionEvent actionEvent) {
        player.playPause(playbtn);
    }

    private void setTablePropertiesOnInit(){  // sets all the necessary properties to song table
        songtable.setEditable(true);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artist"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        songtable.setItems(data);
        songtable.setRowFactory(tv -> {
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

    private void setButtons(){ // sets images to button
        nextbtn.setGraphic(new ImageView("Images/next.png"));
        prevbtn.setGraphic(new ImageView("Images/prev.png"));
        playbtn.setGraphic(new ImageView("Images/play.png"));
        deletebtn.setGraphic(new ImageView("Images/delete.png"));
        editbtn.setGraphic(new ImageView("Images/edit.png"));
        addbtn.setGraphic(new ImageView("Images/add.png"));
        if (data.isEmpty()){
            playbtn.setDisable(true);
            nextbtn.setDisable(true);
            prevbtn.setDisable(true);
        }
    }

    public void filterTableHandler(ActionEvent actionEvent) {
        if (!filtertxt.getText().isEmpty()){
            songtable.setItems(new FilterHandler().filter(data,filtertxt));
        }else {
            songtable.setItems(data);
        }
    }

    public void resetFilter(ActionEvent actionEvent) {
        filtertxt.setText("");
        songtable.setItems(data);
    }

    public void playPrevSong(ActionEvent actionEvent) {
        if (currentSongIndex != 0){
            Song prevSong = data.get(currentSongIndex-1);
            beginSongHandler(prevSong);
        }
    }

    public void playNextSong(ActionEvent actionEvent) {
        if (currentSongIndex != data.size()-1){
            Song nextSong = data.get(currentSongIndex+1);
            beginSongHandler(nextSong);
        }else {
            Song nextSong = data.get(0);
            beginSongHandler(nextSong);
        }
    }

    public void beginSongHandler(Song rowSong){ // initializes new song to be played
        for (int i=0;i<data.size();i++) {
            if (data.get(i) == rowSong){
                currentSongIndex = i;
                break;
            }
        }
        player.playNewSong(rowSong.getFile(),playbtn);
        player.beginTimer(progressslider);
        progressslider.disableProperty().set(false);
        volumeSlider.disableProperty().set(false);
        playinglbl.setText(rowSong.getTitle());
    }

    public void editSongHandler(ActionEvent actionEvent) throws IOException {
        selectedSong = songtable.getSelectionModel().getSelectedItem();
        if (selectedSong!=null && !data.isEmpty()){
            for (int i=0;i<data.size();i++) {
                if (data.get(i) == selectedSong){
                    selectedSongIndex = i;
                    break;
                }
            }
            Stage primaryStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/AddSongView.fxml"));
            Parent root = loader.load();
            AddSongViewController addSong = loader.getController();
            addSong.setParentController(this);
            addSong.editInit(data,selectedSongIndex);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
    }

    public void deleteSongHandler(ActionEvent actionEvent) {
        selectedSong = songtable.getSelectionModel().getSelectedItem();
        if (selectedSong!=null && !data.isEmpty()){
                dh.deleteSongFromDB(selectedSong.getId());
                dh.updateSongsFromDB(data);
                if (data.isEmpty() && player.getPlayer()!=null){
                    playinglbl.setText("Nothing is playing");
                    player.pausePlayer(playbtn);
                }else if (player.getPlayer()!=null){
                    if (currentSongIndex != data.size()-1){
                        Song nextSong = data.get(currentSongIndex+1);
                        beginSongHandler(nextSong);
                    }else {
                        Song nextSong = data.get(0);
                        beginSongHandler(nextSong);
                    }
                }
        }
    }
    public void openPlaylistSeciton(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage) nextbtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/PlaylistView.fxml"));
        Parent root = loader.load();
        player.playPause(playbtn);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void poluteData(){
        dh.getAllSongsFromDB(data);
    }
}
