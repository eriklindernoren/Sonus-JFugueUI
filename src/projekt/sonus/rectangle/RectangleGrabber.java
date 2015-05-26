package projekt.sonus.rectangle;


import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the information needed to draw out new separate rectangles in the grid when the user adds
 * a new sound to the projekt in the popup windows. It tells the DisplayPanel which type of rectangle to
 * draw out dependent on the type of instrument is selected and in which channel to draw it.
 */

public class RectangleGrabber implements RectangleListener {
    private final List<RectangleObserver> observers;
    private int channel;
    private String instrument;

    private int times;

    public RectangleGrabber() {
        observers = new ArrayList<>();
        channel = 0;
        times = 0;
        instrument = null;
    }

    @Override
    public void register(RectangleObserver newObserver) {
        observers.add(newObserver);
    }


    /**
     * Goes through the observers that has been added as RectangleObservers and triggers their update method.
     */
    @Override
    public void notifyObserver() {
        for (RectangleObserver observer : observers) {
            observer.update(channel, instrument, times);
        }
    }

    /**
     * This method is called from the InstrumentPopup when the presses the 'add'-button. It receives the information about
     * what channel the rectangle object should be placed, what type of instrument it is and how rectangles to be added.
     */

    public void setChannelInstrumentTimes(int channel, String instrument, int times) {
        this.channel = channel;
        this.instrument = instrument;
        this.times = times;
        notifyObserver();
    }
}
