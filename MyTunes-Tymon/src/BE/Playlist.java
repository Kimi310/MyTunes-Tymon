package BE;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Playlist {
    private String name;
    private ObservableList<Song> songs = FXCollections.observableArrayList();
    private int id;
    public Playlist(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSongs(ObservableList<Song> songs) {
        this.songs = songs;
    }

    public ObservableList<Song> getSongs() {
        return songs;
    }
}
