package BLL;

import BE.Song;
import GUI.Controller.MainController;
import GUI.Controller.PlaylistViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class ViewProperitesSetter {
    public void setVolumeListener(Slider volumeSlider, MusicPlayer player){
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                MediaPlayer mediaPlayer = player.getPlayer();
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });
    }
    public void setNextSongPlayerMain(MusicPlayer musicPlayer, MainController controller){
        MediaPlayer player = musicPlayer.getPlayer();
        player.setOnEndOfMedia(controller::nextSongMain);
    }
    public void setProgressOnMouse(Slider progressslider, MusicPlayer player) {
        progressslider.setOnMouseClicked(event -> {
            MediaPlayer mediaPlayer = player.getPlayer();
            mediaPlayer.seek(Duration.seconds(progressslider.getValue()));
        });
        progressslider.setOnMouseDragOver(event -> {
            MediaPlayer mediaPlayer = player.getPlayer();
            mediaPlayer.seek(Duration.seconds(progressslider.getValue()));
        });
    }

    public void setNextSongPlayerPlaylist(MusicPlayer musicPlayer, PlaylistViewController controller) {
        MediaPlayer player = musicPlayer.getPlayer();
        player.setOnEndOfMedia(controller::nextSongPlaylist);
    }
    public void setDataListener(ObservableList<Song> data,Slider progressslider,Slider volumeSlider,Button playbtn,Button nextbtn, Button prevbtn) {
        data.addListener(new ListChangeListener<Song>() {
            @Override
            public void onChanged(Change<? extends Song> c) {
                if (data.isEmpty()){
                    progressslider.disableProperty().set(true);
                    volumeSlider.disableProperty().set(true);
                    playbtn.setDisable(true);
                    nextbtn.setDisable(true);
                    prevbtn.setDisable(true);
                }else {
                    playbtn.setDisable(false);
                    nextbtn.setDisable(false);
                    prevbtn.setDisable(false);
                }
            }
        });
    }
}
