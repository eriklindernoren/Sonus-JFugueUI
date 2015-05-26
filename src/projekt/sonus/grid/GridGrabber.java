package projekt.sonus.grid;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the information needed to load and save the grid. The fillCells field stores the position in which
 * to draw the rectangles, the cellColors stores the color of the rectangle and the icons field stores the name of the
 * icon for that rectangle. It also needs to keep track of how many rectangles has been drawn out in the grids channels
 * to enable the user to add to those channels after a projekt is loaded. The reasons that we have so many fields
 * is so that we could implement a back and forward functionality so that the user can regret adding something.
 * To do that we needed to save the previously made grid and give the program the possibility to go back and forth.
 */


public class GridGrabber implements GridListener {
    private final List<GridObserver> observers;

    private static final int LIST_SIZE = 200;

    private boolean backTrackable = false;

    private int numbOfElementsLastAdded = 0;
    private int channelLastAddedTo = 0;
    private int tracker = 0;
    private final static int BACK_TRACKER_LIMIT = -1;

    private List<Point> prevFillCells;
    private List<Point> fillCells;
    private List<Point> nextFillCells;

    private List<Color> prevCellColor;
    private List<Color> cellColor;
    private List<Color> nextCellColor;

    private List<String> prevIcons;
    private List<String> icons;
    private List<String> nextIcons;

    private int prevChannelCount1;
    private int channelCount1;
    private int nextChannelCount1;

    private int prevChannelCount2;
    private int channelCount2;
    private int nextChannelCount2;

    private int prevChannelCount3;
    private int channelCount3;
    private int nextChannelCount3;

    private int prevChannelCount4;
    private int channelCount4;
    private int nextChannelCount4;

    private int prevChannelCount5;
    private int channelCount5;
    private int nextChannelCount5;

    private int prevChannelCount6;
    private int channelCount6;
    private int nextChannelCount6;

    private int prevChannelCount7;
    private int channelCount7;
    private int nextChannelCount7;

    private int prevChannelCount8;
    private int channelCount8;
    private int nextChannelCount8;


    public GridGrabber() {

        observers = new ArrayList<>();
        fillCells = new ArrayList<>(LIST_SIZE);
        cellColor = new ArrayList<>(LIST_SIZE);
        icons = new ArrayList<>(LIST_SIZE);

        channelCount1 = 0;
        channelCount2 = 0;
        channelCount3 = 0;
        channelCount4 = 0;
        channelCount5 = 0;
        channelCount6 = 0;
        channelCount7 = 0;
        channelCount8 = 0;

        prevFillCells = null;
        nextFillCells = null;

        prevIcons = null;
        nextIcons = null;

        prevCellColor = null;
        nextCellColor = null;

    }

    @Override
    public void register(GridObserver newObserver) {
        observers.add(newObserver);
    }

    /**
     * Goes through the observers that has been added as GridObservers and triggers their update method.
     */

    @Override
    public void notifyObserver() {
        for (GridObserver observer : observers) {
            observer.update(fillCells, cellColor, icons);
        }
    }

    public void back() {
        if (backTrackable && tracker > BACK_TRACKER_LIMIT) {
            prevChannelCount();
            prevGrid();
        }
    }

    public void next() {
        if (backTrackable && tracker < 0) {
            nextChannelCount();
            nextGrid();
        }
    }


    /**
     * The prevChannelCount sets the current channelCount to the previous channelCount, resets the
     * previous value and saves the current value in the nextChannelCount variable so that you can go back.
     */

    private void prevChannelCount() {
        switch (channelLastAddedTo) {
            case 1:
                backChannelCount1();
                break;
            case 2:
                backChannelCount2();
                break;
            case 3:
                backChannelCount3();
                break;
            case 4:
                backChannelCount4();
                break;
            case 5:
                backChannelCount5();
                break;
            case 6:
                backChannelCount6();
                break;
            case 7:
                backChannelCount7();
                break;
            case 8:
                backChannelCount8();
                break;

        }
    }

    /**
     * The forwardChannelCount sets the current channelCount to the nextChannelCount, which was the current
     * channel count before the user clicked the back button. It then resets the nextChannelCount and sets the
     * previous channel count to the channel count from which it switches from.
     */

    private void nextChannelCount() {
        switch (channelLastAddedTo) {
            case 1:
                forwardChannelCount1();
                break;
            case 2:
                forwardChannelCount2();
                break;
            case 3:
                forwardChannelCount3();
                break;
            case 4:
                forwardChannelCount4();
                break;
            case 5:
                forwardChannelCount5();
                break;
            case 6:
                forwardChannelCount6();
                break;
            case 7:
                forwardChannelCount7();
                break;
            case 8:
                forwardChannelCount8();
                break;
        }

    }

    /**
     * The prev command of the grid information sets the current instance of that list to the previous instance,
     * resets the previous instance and saves the previously current value in the nextChannelCount
     * variable so that you can go back.
     */

    private void prevGrid() {
        backFillCells();
        backCellColor();
        backIcons();

        setChannels(channelCount1, channelCount2, channelCount3, channelCount4
                , channelCount5, channelCount6, channelCount7, channelCount8);

        setGrid(fillCells, cellColor, icons);

    }

    /**
     * The next command of the grid information sets the current instance of that list to the previously current
     * value saved in the next variable, resets the next variable sets the previous variable to the current instance
     * except for the number of elements last added.
     */

    private void nextGrid() {
        forwardFillCells();
        forwardCellColor();
        forwardIcons();

        setChannels(channelCount1, channelCount2, channelCount3, channelCount4,
                channelCount5, channelCount6, channelCount7, channelCount8);

        setGrid(fillCells, cellColor, icons);

    }

    // BACK CHANNEL COUNT

    private void backChannelCount1() {
        nextChannelCount1 = channelCount1;
        channelCount1 = prevChannelCount1;
        prevChannelCount1 = 0;
    }

    private void backChannelCount2() {
        nextChannelCount2 = channelCount2;
        channelCount2 = prevChannelCount2;
        prevChannelCount2 = 0;
    }

    private void backChannelCount3() {
        nextChannelCount3 = channelCount3;
        channelCount3 = prevChannelCount3;
        prevChannelCount3 = 0;
    }

    private void backChannelCount4() {
        nextChannelCount4 = channelCount4;
        channelCount4 = prevChannelCount4;
        prevChannelCount4 = 0;
    }

    private void backChannelCount5() {
        nextChannelCount5 = channelCount5;
        channelCount5 = prevChannelCount5;
        prevChannelCount5 = 0;
    }

    private void backChannelCount6() {
        nextChannelCount6 = channelCount6;
        channelCount6 = prevChannelCount6;
        prevChannelCount6 = 0;
    }

    private void backChannelCount7() {
        nextChannelCount7 = channelCount7;
        channelCount7 = prevChannelCount7;
        prevChannelCount7 = 0;
    }

    private void backChannelCount8() {
        nextChannelCount8 = channelCount8;
        channelCount8 = prevChannelCount8;
        prevChannelCount8 = 0;
    }

    // FORWARD CHANNEL COUNT

    private void forwardChannelCount1() {
        prevChannelCount1 = channelCount1;
        channelCount1 = nextChannelCount1;
        nextChannelCount1 = 0;
    }

    private void forwardChannelCount2() {
        prevChannelCount2 = channelCount2;
        channelCount2 = nextChannelCount2;
        nextChannelCount2 = 0;
    }

    private void forwardChannelCount3() {
        prevChannelCount3 = channelCount3;
        channelCount3 = nextChannelCount3;
        nextChannelCount3 = 0;
    }

    private void forwardChannelCount4() {
        prevChannelCount4 = channelCount4;
        channelCount4 = nextChannelCount4;
        nextChannelCount4 = 0;
    }

    private void forwardChannelCount5() {
        prevChannelCount5 = channelCount5;
        channelCount5 = nextChannelCount5;
        nextChannelCount5 = 0;
    }

    private void forwardChannelCount6() {
        prevChannelCount6 = channelCount6;
        channelCount6 = nextChannelCount6;
        nextChannelCount6 = 0;
    }

    private void forwardChannelCount7() {
        prevChannelCount7 = channelCount7;
        channelCount7 = nextChannelCount7;
        nextChannelCount7 = 0;
    }

    private void forwardChannelCount8() {
        prevChannelCount8 = channelCount8;
        channelCount8 = nextChannelCount8;
        nextChannelCount8 = 0;
    }

    // PREV GRID

    private void backFillCells() {
        if (prevFillCells != null) {
            nextFillCells = fillCells;
            fillCells = prevFillCells;
            prevFillCells = null;

        }
    }

    private void backCellColor() {
        if (prevCellColor != null) {
            nextCellColor = cellColor;
            cellColor = prevCellColor;
            prevCellColor = null;
        }
    }

    private void backIcons() {
        if (prevIcons != null) {
            tracker -= 1;
            nextIcons = icons;
            icons = prevIcons;
            prevIcons = null;

        }
    }

    // NEXT GRID

    private void forwardFillCells() {
        if (nextFillCells != null) {
            fillCells = nextFillCells;
            prevFillCells = (nextFillCells.size() >= numbOfElementsLastAdded ?
                    nextFillCells.subList(0, nextFillCells.size() - numbOfElementsLastAdded) : null);
            nextFillCells = null;
        }
    }

    private void forwardCellColor() {
        if (nextCellColor != null) {
            cellColor = nextCellColor;
            prevCellColor = (nextCellColor.size() >= numbOfElementsLastAdded ?
                    nextCellColor.subList(0, nextCellColor.size() - numbOfElementsLastAdded) : null);
            nextCellColor = null;
        }
    }

    private void forwardIcons() {
        if (nextIcons != null) {
            tracker += 1;
            icons = nextIcons;
            prevIcons = (nextIcons.size() >= numbOfElementsLastAdded ?
                    nextIcons.subList(0, nextIcons.size() - numbOfElementsLastAdded) : null);
            nextIcons = null;
        }
    }

    /**
     * When the setChannelCount is called from the Grid class and from the load method in the Directory Panel
     * it sets the current channel count to the new value and saves the previous channel count to the prevChannelCount
     * variable. It sets the 'channelLastAddedTo' to the number of the channel in question so that we can keep track
     * of what channel was last changed.
     */

    // CHANNEL COUNT SETTERS
    public void setChannelCount1(int channelCount1) {
        prevChannelCount1 = (channelCount1 > numbOfElementsLastAdded ? channelCount1 - numbOfElementsLastAdded : 0);
        System.out.println(prevChannelCount1);
        this.channelCount1 = channelCount1;
        System.out.println(this.channelCount1);
        nextChannelCount1 = 0;

        channelLastAddedTo = 1;

    }

    public void setChannelCount2(int channelCount2) {
        prevChannelCount2 = (channelCount2 > numbOfElementsLastAdded ? channelCount2 - numbOfElementsLastAdded : 0);
        this.channelCount2 = channelCount2;
        nextChannelCount2 = 0;

        channelLastAddedTo = 2;

    }

    public void setChannelCount3(int channelCount3) {
        prevChannelCount3 = (channelCount3 > numbOfElementsLastAdded ? channelCount3 - numbOfElementsLastAdded : 0);
        System.out.println(prevChannelCount3);
        this.channelCount3 = channelCount3;
        System.out.println(this.channelCount3);
        nextChannelCount3 = 0;

        channelLastAddedTo = 3;
    }

    public void setChannelCount4(int channelCount4) {
        prevChannelCount4 = (channelCount4 > numbOfElementsLastAdded ? channelCount4 - numbOfElementsLastAdded : 0);
        this.channelCount4 = channelCount4;
        nextChannelCount4 = 0;

        channelLastAddedTo = 4;
    }

    public void setChannelCount5(int channelCount5) {
        prevChannelCount5 = (channelCount5 > numbOfElementsLastAdded ? channelCount5 - numbOfElementsLastAdded : 0);
        this.channelCount5 = channelCount5;
        nextChannelCount5 = 0;

        channelLastAddedTo = 5;
    }

    public void setChannelCount6(int channelCount6) {
        prevChannelCount6 = (channelCount6 > numbOfElementsLastAdded ? channelCount6 - numbOfElementsLastAdded : 0);
        this.channelCount6 = channelCount6;
        nextChannelCount6 = 0;

        channelLastAddedTo = 6;
    }

    public void setChannelCount7(int channelCount7) {
        prevChannelCount7 = (channelCount7 > numbOfElementsLastAdded ? channelCount7 - numbOfElementsLastAdded : 0);
        this.channelCount7 = channelCount7;
        nextChannelCount7 = 0;

        channelLastAddedTo = 7;
    }

    public void setChannelCount8(int channelCount8) {
        prevChannelCount8 = (channelCount8 > numbOfElementsLastAdded ? channelCount8 - numbOfElementsLastAdded : 0);
        this.channelCount8 = channelCount8;
        nextChannelCount8 = 0;

        channelLastAddedTo = 8;
    }


    /**
     * The set method sets the current instance to the new value past to it and sets the previous value to the
     * previously current value.
     */

    // GRID LIST SETTERS
    public void setFillCells(List<Point> newFillCells) {
        prevFillCells = (newFillCells.isEmpty() ? null : newFillCells.subList(0, newFillCells.size() - numbOfElementsLastAdded));
        this.fillCells = newFillCells;
        nextFillCells = null;
    }

    public void setCellColor(List<Color> newCellColor) {
        prevCellColor = (newCellColor.isEmpty() ? null :
                newCellColor.subList(0, newCellColor.size() - numbOfElementsLastAdded));
        this.cellColor = newCellColor;

        nextCellColor = null;
    }

    public void setIcons(List<String> newIcons) {
        tracker = 0;
        backTrackable = true;
        prevIcons = (newIcons.isEmpty() ? null : newIcons.subList(0, newIcons.size() - numbOfElementsLastAdded));
        this.icons = newIcons;

        System.out.println(prevIcons);
        System.out.println(icons);
        nextIcons = null;
    }

    // CHANNEL COUNT GETTERS

    public int getChannelCount1() {
        return channelCount1;
    }


    public int getChannelCount2() {
        return channelCount2;
    }

    public int getChannelCount3() {
        return channelCount3;
    }


    public int getChannelCount4() {
        return channelCount4;
    }

    public int getChannelCount5() {
        return channelCount5;
    }

    public int getChannelCount6() {
        return channelCount6;
    }


    public int getChannelCount7() {
        return channelCount7;
    }

    public int getChannelCount8() {
        return channelCount8;
    }

    // GRID INFO GETTERS

    public List<Point> getFillCells() {
        return fillCells;
    }

    public List<Color> getCellColor() {
        return cellColor;
    }

    public List<String> getIcons() {
        return icons;
    }


    // OTHER GRID METHODS

    /**
     * setGrid and setChannels are used when the user loads a project in the directorypanel. The setGrid method
     * then triggers the update method in the Grid class.
     */

    public void setGrid(List<Point> fillCells, List<Color> cellColor, List<String> icons) {
        this.fillCells = fillCells;
        this.cellColor = cellColor;
        this.icons = icons;

        notifyObserver();
    }

    public void setChannels(int channel1, int channel2, int channel3, int channel4, int channel5, int channel6, int channel7,
                            int channel8) {
        this.channelCount1 = channel1;
        this.channelCount2 = channel2;
        this.channelCount3 = channel3;
        this.channelCount4 = channel4;
        this.channelCount5 = channel5;
        this.channelCount6 = channel6;
        this.channelCount7 = channel7;
        this.channelCount8 = channel8;
    }

    public void resetGrid() {
        this.fillCells = new ArrayList<>(LIST_SIZE);
        this.cellColor = new ArrayList<>(LIST_SIZE);
        this.icons = new ArrayList<>(LIST_SIZE);

        channelCount1 = 0;
        channelCount2 = 0;
        channelCount3 = 0;
        channelCount4 = 0;
        channelCount5 = 0;
        channelCount6 = 0;
        channelCount7 = 0;
        channelCount8 = 0;

        notifyObserver();
    }


    /**
     * The setting of the setNumbOfElementsLastAdded is needed so that the forward method can set the previous value
     * to the current value minus the number of elements last added.
     */

    public void setNumbOfElementsLastAdded(int numbOfElementsLastAdded) {
        this.numbOfElementsLastAdded = numbOfElementsLastAdded;
    }

    /**
     * The setBackTrackable is set to false when the grid is cleared and when the user loads a new project
     * in the DirectoryPanel.
     */

    public void setBackTrackable(boolean backTrackable) {
        this.backTrackable = backTrackable;
    }

}
