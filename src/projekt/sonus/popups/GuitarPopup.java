package projekt.sonus.popups;


import projekt.sonus.rectangle.RectangleGrabber;
import projekt.sonus.sound.SoundGrabber;

import java.awt.*;

/**
 * This class is called from the InstrumentPopupFactory.
 * Handles the creation and look of the popup window for the Guitar.
 * It uses the framework set in the "instrumentpopup"-class and
 * passes the desired instruments and corresponding labels.
 */

public class GuitarPopup extends InstrumentPopup {

    private final static Color GUITAR_COLOR = new Color(171, 130, 255);

    private static final int INSTRUMENT_SIZE = 5;

    private static final String GUITAR_1 = " I[GUITAR] ";
    private static final String GUITAR_2 = " I[PIZZICATO_STRINGS] ";
    private static final String GUITAR_3 = " I[DISTORTION_GUITAR] ";
    private static final String GUITAR_4 = " I[TIMPANI] ";
    private static final String GUITAR_5 = " I[TREMOLO_STRINGS] ";


    GuitarPopup(SoundGrabber soundGrabber, RectangleGrabber rectangleGrabber) {
        this.setInstrumentID("Guitar");
        this.setRectangleGrabber(rectangleGrabber);
        this.setSoundGrabber(soundGrabber);
        this.setInstrumentColor(GUITAR_COLOR);

        String[] guitarLabels = new String[INSTRUMENT_SIZE];
        guitarLabels[0] = "Acoustic Guitar";
        guitarLabels[1] = "Electric Guitar";
        guitarLabels[2] = "Distortion Guitar";
        guitarLabels[3] = "Timpani";
        guitarLabels[4] = "Tremolo Strings";

        String[] guitarList = new String[INSTRUMENT_SIZE];
        guitarList[0] = GUITAR_1;
        guitarList[1] = GUITAR_2;
        guitarList[2] = GUITAR_3;
        guitarList[3] = GUITAR_4;
        guitarList[4] = GUITAR_5;

        this.setInstrumentLabels(guitarLabels);
        this.setInstrumentList(guitarList);

        panelMaker(getContentPane());
        createWindow();

    }
}
