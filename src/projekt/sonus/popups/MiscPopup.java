package projekt.sonus.popups;


import projekt.sonus.rectangle.RectangleGrabber;
import projekt.sonus.sound.SoundGrabber;

import java.awt.*;

/**
 * This class is called from the InstrumentPopupFactory.
 * Handles the creation and look of the popup window for the Misc.
 * It uses the framework set in the "instrumentpopup"-class and
 * passes the desired instruments and corresponding labels.
 */

public class MiscPopup extends InstrumentPopup {

    private final static Color MISC_COLOR = new Color(100, 100, 180);

    private static final int INSTRUMENT_SIZE = 5;

    private static final String MISC_1 = " I[GOBLINS] ";
    private static final String MISC_2 = " I[GUNSHOT] ";
    private static final String MISC_3 = " I[SEASHORE] ";
    private static final String MISC_4 = " I[RAIN] ";
    private static final String MISC_5 = " I[HELICOPTER] ";

    MiscPopup(SoundGrabber soundGrabber, RectangleGrabber rectangleGrabber) {
        this.setInstrumentID("Misc");
        this.setRectangleGrabber(rectangleGrabber);
        this.setSoundGrabber(soundGrabber);
        this.setInstrumentColor(MISC_COLOR);

        String[] miscLabels = new String[INSTRUMENT_SIZE];
        miscLabels[0] = "Goblins";
        miscLabels[1] = "Gunshot";
        miscLabels[2] = "Seashore";
        miscLabels[3] = "Rain";
        miscLabels[4] = "Helicopter";


        String[] miscList = new String[INSTRUMENT_SIZE];
        miscList[0] = MISC_1;
        miscList[1] = MISC_2;
        miscList[2] = MISC_3;
        miscList[3] = MISC_4;
        miscList[4] = MISC_5;

        this.setInstrumentLabels(miscLabels);
        this.setInstrumentList(miscList);

        panelMaker(getContentPane());
        createWindow();
    }
}
