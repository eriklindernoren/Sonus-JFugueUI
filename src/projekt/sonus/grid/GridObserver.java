package projekt.sonus.grid;


import java.awt.*;
import java.util.List;

/**
 * Houses the method and arguments needed to implement the GridObserver.
 */

public interface GridObserver {
    public void update(List<Point> fillCells, List<Color> cellColor, List<String> icons);
}
