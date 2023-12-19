package BLL;

import GUI.Controller.MainController;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MusicPlayer {
    private boolean playing = false;
    private Media sound;
    private MediaPlayer player;
    private Timer timer;
    private TimerTask timerTask;

    public void playNewSong(String file, Button playButton, Slider progressSlider, Slider volumeSlider){ // plays new song from the list
        if (!playing){
            if (timer!=null){
                cancelTimer();
            }
            playButton.setGraphic(new ImageView("Images/pause.png"));
            sound = new Media(new File(file).toURI().toString());
            player = new MediaPlayer(sound);
            player.play();
            beginTimer(progressSlider);
            player.setVolume(volumeSlider.getValue() * 0.01);
            playing=true;
        }else {
            if (timer!=null){
                cancelTimer();
            }
            player.stop();
            playButton.setGraphic(new ImageView("Images/pause.png"));
            sound = new Media(new File(file).toURI().toString());
            player = new MediaPlayer(sound);
            player.play();
            beginTimer(progressSlider);
            player.setVolume(volumeSlider.getValue() * 0.01);
            playing=true;
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

    private void beginTimer(Slider progressSlider){
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                    double current = player.getCurrentTime().toSeconds();
                    double end = sound.getDuration().toSeconds();
                    progressSlider.setMax(end);
                    progressSlider.setValue(current);
                    if (current/end == 1){
                        cancelTimer();
                    }
            }
        };
        timer.scheduleAtFixedRate(timerTask,1000,1000);
    }
    public void cancelTimer(){
        timer.cancel();
    }

    public void pausePlayer(Button playButton){
        if (player!=null){
            player.pause();
            playButton.setGraphic(new ImageView("Images/pause.png"));
            playing=false;
        }
    }
}
