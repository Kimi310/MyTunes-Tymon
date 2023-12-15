package GUI.Controller;

import BE.Song;
import BLL.AddingSongHandler;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.sql.SQLException;

public class AddSongViewController {
    @FXML
    private Label errorlbl;
    @FXML
    private TextField categorytxt;
    @FXML
    private TextField titletxt;
    @FXML
    private TextField artisttxt;
    @FXML
    private TextField timetxt;
    @FXML
    private TextField filetxt;
    private boolean editing = false;
    private AddingSongHandler handler = new AddingSongHandler();
    private int selectedSongIndex;

    private MainController controller = new MainController();
    private ObservableList<Song> songs;

    public void addSongToList(ActionEvent actionEvent) throws SQLException {
        if (handler.checkNewSong(handler.textFieldsToString(titletxt,artisttxt,categorytxt,timetxt,filetxt))){
            if (editing==false){
                Song song = new Song(titletxt.getText(),artisttxt.getText(),categorytxt.getText(),timetxt.getText(),filetxt.getText());
                controller.addSongToTable(song);
            }else {
                songs.get(selectedSongIndex).setArtist(artisttxt.getText());
                songs.get(selectedSongIndex).setCategory(categorytxt.getText());
                songs.get(selectedSongIndex).setFile(filetxt.getText());
                songs.get(selectedSongIndex).setTime(timetxt.getText());
                songs.get(selectedSongIndex).setTitle(titletxt.getText());
            }
            Stage stage = (Stage) filetxt.getScene().getWindow();
            stage.close();
        }else{
            errorlbl.setVisible(true);
        }
    }
    public void editInit(ObservableList<Song> data, int selectedSongIndex){
        this.selectedSongIndex = selectedSongIndex;
        editing = true;
        songs = data;
        categorytxt.setText(data.get(selectedSongIndex).getCategory());
        titletxt.setText(data.get(selectedSongIndex).getTitle());
        timetxt.setText(data.get(selectedSongIndex).getTime());
        artisttxt.setText(data.get(selectedSongIndex).getArtist());
        filetxt.setText(data.get(selectedSongIndex).getFile());
    }
    public void setParentController (MainController controller){
        this.controller = controller;
    }

    public void chooseFile(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        chooser.getExtensionFilters().add(new ExtensionFilter("MUSIC FILES","*.mp3"));
        chooser.getExtensionFilters().add(new ExtensionFilter("MUSIC FILES","*.wav"));
        File file = chooser.showOpenDialog(stage);
        if (file != null){
            filetxt.setText(file.getAbsolutePath());
            Media sound = new Media(file.toURI().toString());
            System.out.println(sound.getDuration().toSeconds());
            timetxt.setText(String.valueOf(sound.getDuration().toSeconds()));
            titletxt.setText(file.getName());
        }
    }

}
