package DAL;

import BE.Playlist;
import BE.Song;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DataBaseAccess{
    public void addSongToDB(Song song) throws SQLException{
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setDatabaseName("CSe22B_41_MyTunes_Group_A1");
        ds.setUser("CSe2023b_e_26");
        ds.setPassword("CSe2023bE26#23");
        ds.setPortNumber(1433);
        ds.setServerName("10.176.111.34");
        ds.setTrustServerCertificate(true);
        try {
            Connection con = ds.getConnection();
            String sql = "INSERT INTO Songs (title, artist, category, time, path) VALUES (?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            con.setAutoCommit(false);
            st.setString(1, song.getTitle());
            st.setString(2,song.getArtist());
            st.setString(3,song.getCategory());
            st.setString(4,song.getTime());
            st.setString(5,song.getFile());
            st.executeUpdate();
            ResultSet generatedKeys = st.getGeneratedKeys();
                if (generatedKeys.next()) {
                    song.setId( generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllSongsFromDB(ObservableList<Song> data){
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setDatabaseName("CSe22B_41_MyTunes_Group_A1");
        ds.setUser("CSe2023b_e_26");
        ds.setPassword("CSe2023bE26#23");
        ds.setPortNumber(1433);
        ds.setServerName("10.176.111.34");
        ds.setTrustServerCertificate(true);
        try{
            Connection con = ds.getConnection();
            String sql = "SELECT * FROM Songs";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                Song s = new Song(rs.getInt("id"),rs.getString("title"),rs.getString("artist"),rs.getString("category"), rs.getString("time"), rs.getString("path"));
                data.add(s);
            }
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSongFromDB(int id){
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setDatabaseName("CSe22B_41_MyTunes_Group_A1");
        ds.setUser("CSe2023b_e_26");
        ds.setPassword("CSe2023bE26#23");
        ds.setPortNumber(1433);
        ds.setServerName("10.176.111.34");
        ds.setTrustServerCertificate(true);
        try{
            Connection con = ds.getConnection();
            String sql = "DELETE FROM Songs WHERE id= ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1,id);
            st.executeUpdate();
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void editSongOnIndex(int id,Song song){
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setDatabaseName("CSe22B_41_MyTunes_Group_A1");
        ds.setUser("CSe2023b_e_26");
        ds.setPassword("CSe2023bE26#23");
        ds.setPortNumber(1433);
        ds.setServerName("10.176.111.34");
        ds.setTrustServerCertificate(true);
        try{
            Connection con = ds.getConnection();
            String sql = "UPDATE Songs SET title = ?, artist = ?, category = ?, time = ?, path = ? WHERE id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1,song.getTitle());
            st.setString(2,song.getArtist());
            st.setString(3,song.getCategory());
            st.setString(4,song.getTime());
            st.setString(5,song.getFile());
            st.setInt(6,id);
            st.executeUpdate();
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createPlaylistTable(String playlistName){
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setDatabaseName("CSe22B_41_MyTunes_Group_A1");
        ds.setUser("CSe2023b_e_26");
        ds.setPassword("CSe2023bE26#23");
        ds.setPortNumber(1433);
        ds.setServerName("10.176.111.34");
        ds.setTrustServerCertificate(true);
        try{
            Connection con = ds.getConnection();
            String sql = "CREATE TABLE "+playlistName+"(id INT, title nvarchar(250), artist nvarchar(250),category nvarchar(250),time nvarchar(10),path nvarchar(250))";
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            String sql1 = "ALTER TABLE "+playlistName+" ADD FOREIGN KEY (id) REFERENCES Songs(id)";
            PreparedStatement st1 = con.prepareStatement(sql1);
            st1.executeUpdate();
            String sql2 = "INSERT INTO Playlists (name) VALUES (?)";
            PreparedStatement st2 = con.prepareStatement(sql2);
            st2.setString(1,playlistName);
            st2.executeUpdate();
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void getAllPlaylists(ObservableList<Playlist> playlists){
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setDatabaseName("CSe22B_41_MyTunes_Group_A1");
        ds.setUser("CSe2023b_e_26");
        ds.setPassword("CSe2023bE26#23");
        ds.setPortNumber(1433);
        ds.setServerName("10.176.111.34");
        ds.setTrustServerCertificate(true);
        try{
            Connection con = ds.getConnection();
            String sql = "SELECT * FROM Playlists";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                Playlist p = new Playlist(rs.getString("name"),rs.getInt("id"));
                playlists.add(p);
            }

            for (Playlist p:playlists) {
                String sql2 = "SELECT * FROM "+p.getName();
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(sql2);
                ObservableList<Song> songs = FXCollections.observableArrayList();
                while (resultSet.next()){
                    Song s = new Song(resultSet.getInt("id"),resultSet.getString("title"),resultSet.getString("artist"),resultSet.getString("category"), resultSet.getString("time"), resultSet.getString("path"));
                    songs.add(s);
                }
                p.setSongs(songs);
            }
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}