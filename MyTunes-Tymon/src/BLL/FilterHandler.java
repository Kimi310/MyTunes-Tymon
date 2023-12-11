package BLL;

import BE.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.text.TableView;
import javafx.scene.control.*;

public class FilterHandler {
    public ObservableList<Song> filter (ObservableList<Song> data, TextField filtertxt){
        ObservableList<Song> filteredData = FXCollections.observableArrayList();
        for (Song s:data) {
            if (s.getArtist().contains(filtertxt.getText())){
                filteredData.add(s);
                break;
            }
            if (s.getTitle().contains(filtertxt.getText())){
                filteredData.add(s);
                break;
            }
            if (s.getCategory().contains(filtertxt.getText())){
                filteredData.add(s);
                break;
            }
        }
        return filteredData;
    }
}
