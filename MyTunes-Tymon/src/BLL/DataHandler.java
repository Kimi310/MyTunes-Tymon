package BLL;

import BE.Song;
import DAL.DataBaseAccess;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class DataHandler {
    private DataBaseAccess dba = new DataBaseAccess();

    public void getAllSongsFromDB(ObservableList<Song> data){
        dba.getAllSongsFromDB(data);
    }

    public void addSongToDB(Song song) throws SQLException {
        dba.addSongToDB(song);
    }
    public void deleteSongFromDB(int id){
        dba.deleteSongFromDB(id);
    }

    public void updateSongsFromDB(ObservableList<Song> data){
        data.clear();
        dba.getAllSongsFromDB(data);
    }
}
