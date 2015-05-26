package projekt.sonus.sound;

/**
 * The SoundListener class defines what methods are needed in the SoundGrabber class.
 */

public interface SoundListener {
    public void register(SoundObserver o);

    /**
     * This is flagged as a unused declaration when we run the code analyzation but it is needed and used in the
     * SoundGrabber.
     */


    public void notifyObserver();
}
