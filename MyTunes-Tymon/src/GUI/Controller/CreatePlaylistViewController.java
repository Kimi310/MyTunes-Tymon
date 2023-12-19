package GUI.Controller;

import BE.Playlist;
import BLL.DataHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreatePlaylistViewController implements Initializable {
    @FXML
    private Label errorlbl;
    @FXML
    private TextField nametxt;
    private DataHandler dh = new DataHandler();
    PlaylistViewController controller;
    String oldName;
    private boolean editing = false;
    private ObservableList<Playlist> playlists = FXCollections.observableArrayList();
    @FXML
    private void createNewPlaylist(ActionEvent actionEvent) {
        for (Playlist p:playlists) {
            if (Objects.equals(p.getName(), nametxt.getText())){
                errorlbl.setVisible(true);
                return;
            }
        }
        if (!nametxt.getText().isEmpty() && !editing){
            dh.createPlaylistTable(nametxt.getText());
            Stage stage = (Stage) nametxt.getScene().getWindow();
            controller.polutePlaylistsObservableList();
            stage.close();
        } else if (!nametxt.getText().isEmpty() && editing) {
            controller.editPlaylistNameDataAccess(oldName,nametxt.getText());
            Stage stage = (Stage) nametxt.getScene().getWindow();
            controller.polutePlaylistsObservableList();
            stage.close();
        } else {
            errorlbl.setVisible(true);
        }
    }
    public void setParentController(PlaylistViewController controller){
        this.controller = controller;
    }

    public void setEditPlaylistOnInit(String oldName){
        editing=true;
        this.oldName = oldName;
        nametxt.setText(oldName);
    }
    private void getAllPlaylistNames(){
        dh.getAllPlaylists(playlists);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAllPlaylistNames();
    }
}
