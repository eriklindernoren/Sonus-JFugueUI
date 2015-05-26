package projekt.sonus.mainwindow;

import org.jfugue.Player;
import projekt.sonus.sound.Sound;

/**
 * Makes it possible to start and later stop the music using a separate thread.
 */

public class PlayerThread implements Runnable {

    private Sound sound;
    private Player player = new Player();
    private PlayPanel playPanel;

    public PlayerThread(PlayPanel p) {
        sound = new Sound();
        playPanel = p;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

    @Override
    public void run() {
        try {

            player.play(sound);
            /**
             * When the player has stopped playing it notifies the PlayPanel so that it can can
             * change the soundControl button from "STOP" to "PLAY" for example.
             */
            playPanel.finishedPlaying();
        } catch (RuntimeException exception) {
            System.out.println(exception);
        }
    }

    /**
     * Is called from the PlayPanel when the soundControl pressed and it has the "STOP" symbol.
     */

    public void stopPlayer() {
        player.stop();
    }

}
