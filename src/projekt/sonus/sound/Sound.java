/*
Handles the creation and modifying of music strings.
 */
package projekt.sonus.sound;

import org.jfugue.Pattern;

/**
 * Extends Pattern which handles the string that the Player class in JFugue reads when it's
 * time to play a created soundpattern. We extended that class
 * to be able to add the methods we needed in the GUI.
 */

public class Sound extends Pattern {

    /**
     * Since much of music are repeated sound we wanted to add the functionality of repeating a created sound.
     */
    public void addSoundLoop(String sound, int times) {
        for (int i = 1; i <= times; i++) {
            this.add(sound);
        }
    }

    public void insertInstrumentToMusicString(String instrument) {
        this.insert(instrument);
    }

    /**
     * To set the volumelevel in JFugue you need to add the "x[volume]=volumelevel" to the musicstring.
     */

    public void insertVolume(int volumeLevel) {
        this.insert(" x[volume]=" + volumeLevel);
    }

    /**
     * To set on which the sound is added you add "V" and then the channel to the beginning of the sound
     * you want to add.
     */

    public void insertChannel(int channel) {
        this.insert(" V" + channel + " ");
    }

    /**
     * The percussion tracks in JFugue are locked to the 9th channel, hence the addition of " V9 " before beat.
     */

    public void addBeatToMusicString(String beat) {
        this.add(" V9 " + beat);
    }

}
