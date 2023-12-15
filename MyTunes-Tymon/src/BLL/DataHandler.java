package BLL;

import BE.Playlist;
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

    public void editSongOnIndex(int id, Song s){
        dba.editSongOnIndex(id,s);
    }

    public void createPlaylistTable(String name){
        dba.createPlaylistTable(name);
    }
    public void getAllPlaylists(ObservableList<Playlist> playlists){
        dba.getAllPlaylists(playlists);
    }
    public void deletePlaylist(Playlist p){
        dba.deleteTable(p);
    }
}
