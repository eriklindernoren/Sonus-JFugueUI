package projekt.sonus.grid;

/**
 * The GridListener class defines what methods are needed in the GridGrabber class.
 */

public interface GridListener {

    public void register(GridObserver o);

    /**
     * This is flagged as a unused declaration when we run the code analyzation but it is needed and used in the
     * GridGrabber.
     */

    public void notifyObserver();
}
