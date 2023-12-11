package GUI.Controller;

import BE.Song;
import BLL.AddingSongHandler;
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
    private Song editedSong;
    private AddingSongHandler handler = new AddingSongHandler();
    private int selectedSongIndex;

    private MainController controller = new MainController();

    public void addSongToList(ActionEvent actionEvent) {
        if (handler.checkNewSong(handler.textFieldsToString(titletxt,artisttxt,categorytxt,timetxt,filetxt))){
            if (editing==false){
                Song song = new Song(titletxt.getText(),artisttxt.getText(),categorytxt.getText(),timetxt.getText(),filetxt.getText());
                controller.addSongToTable(song);
            }else {
                Song song = new Song(titletxt.getText(),artisttxt.getText(),categorytxt.getText(),timetxt.getText(),filetxt.getText());
                controller.changeSongOnIndex(selectedSongIndex,song);
            }
            Stage stage = (Stage) filetxt.getScene().getWindow();
            stage.close();
        }else{
            errorlbl.setVisible(true);
        }
    }
    public void editInit(Song s, int selectedSongIndex){
        this.selectedSongIndex = selectedSongIndex;
        editing = true;
        editedSong = s;
        categorytxt.setText(s.getCategory());
        titletxt.setText(s.getTitle());
        timetxt.setText(s.getTime());
        artisttxt.setText(s.getArtist());
        filetxt.setText(s.getFile());
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
