package BLL;

import BE.Song;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayer {
    private boolean playing = false;
    private Media sound;
    private MediaPlayer player;
    private Timer timer;
    private TimerTask timerTask;

    public void playNewSong(String file, Button playButton){ // plays new song from the list
        if (!playing){
            playButton.setGraphic(new ImageView("Images/pause.png"));
            sound = new Media(new File(file).toURI().toString());
            player = new MediaPlayer(sound);
            player.play();
            playing=true;
            if (timer!=null){
                cancelTimer();
            }
        }else {
            player.stop();
            playButton.setGraphic(new ImageView("Images/pause.png"));
            sound = new Media(new File(file).toURI().toString());
            player = new MediaPlayer(sound);
            player.play();
            playing=true;
            if (timer!=null){
                cancelTimer();
            }
        }
    }
    public void playPause (Button playButton){
        if (sound==null){
            return;
        }else{
            if (playing){
                playButton.setGraphic(new ImageView("Images/play.png"));
                player.pause();
                playing=false;
            }else {
                playButton.setGraphic(new ImageView("Images/pause.png"));
                player.play();
                playing=true;
            }
        }
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void beginTimer(Slider progressSlider){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                    double current = player.getCurrentTime().toSeconds();
                    double end = sound.getDuration().toSeconds();
                    progressSlider.setMax(end);
                    progressSlider.setValue(current);
                    if (current/end == 1){
                        progressSlider.disableProperty().set(true);
                        cancelTimer();
                    }
            }
        };
        timer.scheduleAtFixedRate(timerTask,1000,1000);
    }
    public void cancelTimer(){
        timer.cancel();
    }

    public String getCurrentSongURI(){
        return sound.getSource();
    }
}
