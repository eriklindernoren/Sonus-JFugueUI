package projekt.sonus.rectangle;

/**
 * The RectangleListener class defines what methods are needed in the SoundGrabber class.
 */


public interface RectangleListener {
    public void register(RectangleObserver o);

    /**
     * This is flagged as a unused declaration when we run the code analyzation but it is needed and used in the
     * RectangleGrabber.
     */


    public void notifyObserver();
}
