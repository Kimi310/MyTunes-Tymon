package BLL;

import GUI.Controller.MainController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    public void setNextSongPlayer(MusicPlayer musicPlayer, MainController controller){
        MediaPlayer player = musicPlayer.getPlayer();
        player.setOnEndOfMedia(controller::nextSong);
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
}
