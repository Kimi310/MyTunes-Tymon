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
    @FXML
    private void createNewPlaylist(ActionEvent actionEvent) {
        if (!nametxt.getText().isEmpty()){
            dh.createPlaylistTable(nametxt.getText());
            Stage stage = (Stage) nametxt.getScene().getWindow();
            stage.close();
        }else {
            errorlbl.setVisible(true);
        }
    }
}
