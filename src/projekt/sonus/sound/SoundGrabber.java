package projekt.sonus.sound;

import java.util.ArrayList;
import java.util.List;

/**
 * This class updates the main sound for the application after a new sound is added in a popup window. It also
 * keeps information about a separate sound type in which the tempo selected in the PlayPanels is taken into account.
 * The soundWithTempo is the sound object that the user has the option to export, this enable the user to make wav-files with varying
 * tempos.
 */

public class SoundGrabber implements SoundListener {


    private final List<SoundObserver> observers;
    private Sound sound;
    private Sound soundWithTempo;


    private boolean backTrackable = false;

    /**
     * The tracker variable is important so that the user can only go back one step.
     * The tracker starts at 0 and can go -1 <= tracker <= 0.
     */

    private int tracker;
    private final static int BACK_TRACKER_LIMIT = -1;

    private Sound prevSound;
    private Sound nextSound;

    public SoundGrabber() {

        observers = new ArrayList<>();
        sound = new Sound();
        soundWithTempo = new Sound();

        tracker = 0;
        prevSound = null;
        nextSound = null;
    }

    @Override
    public void register(SoundObserver newObserver) {
        observers.add(newObserver);
    }

    /**
     * Goes through the observers that has been added as SoundObservers and triggers their update method.
     */

    @Override
    public void notifyObserver() {
        for (SoundObserver observer : observers) {
            observer.update(sound);
        }
    }


    public void resetSound() {
        this.sound = new Sound();
    }

    /**
     * The back command sets the nextSound variable to the current sound and sets the current sound to
     * the sound that was saved to the prevSound variable the last time
     * sound was added.
     */

    public void backSound() {
        if (tracker > BACK_TRACKER_LIMIT && prevSound != null && backTrackable) {
            tracker -= 1;
            nextSound = sound;
            sound = prevSound;
            System.out.println(sound.getMusicString());
            prevSound = null;
            notifyObserver();
        }
    }

    /**
     * The forward command is used when the user wants to go back to the state in which the sound was before
     * the back command was triggered. The value saved to the nextSound becomes the current sound and the
     * previously current sound becomes the prevSound.
     */

    public void forwardSound() {
        if (tracker < 0 && nextSound != null) {
            tracker += 1;
            prevSound = sound;
            sound = nextSound;
            System.out.println(sound.getMusicString());
            nextSound = null;
            notifyObserver();
        }
    }

    /**
     * Is called from the PlayPanel when the tempo slider is adjusted and when the sound is updated.
     */

    public void setSoundWithTempo(Sound newSound) {
        this.soundWithTempo = newSound;
    }

    public void resetSoundWithTempo() {
        this.soundWithTempo = new Sound();
    }

    public Sound getSoundWithTempo() {
        return soundWithTempo;
    }

    /**
     * Is used in the InstrumentPopup class when the user presses the 'add'-button. It adds the created sound to the
     * main sound and notifies the listening classes that the sound has changed. It also saves the previously current
     * sound in the prevSound variable and sets the backTrackable variable to true so that the user can go back.
     */

    public void addToSound(Sound addedSound) {
        tracker = 0;
        backTrackable = true;
        Sound tempSound = new Sound();
        tempSound.add(sound.getMusicString() + addedSound);

        prevSound = sound;
        this.sound = tempSound;
        nextSound = null;
        System.out.println(sound.getMusicString());
        notifyObserver();
    }

    /**
     * Makes sure the user can't go back when the grid is cleared or when the user loads a project.
     */

    public void setBackTrackable(boolean backTrackable) {
        this.backTrackable = backTrackable;
    }

    public Sound getSound() {
        return sound;
    }

}