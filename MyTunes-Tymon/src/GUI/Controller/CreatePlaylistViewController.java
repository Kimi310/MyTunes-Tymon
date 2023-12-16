package GUI.Controller;

import BE.Playlist;
import BLL.DataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreatePlaylistViewController {
    @FXML
    private Label errorlbl;
    @FXML
    private TextField nametxt;
    private DataHandler dh = new DataHandler();
    PlaylistViewController controller;
    String oldName;
    private boolean editing = false;
    @FXML
    private void createNewPlaylist(ActionEvent actionEvent) {
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
}
