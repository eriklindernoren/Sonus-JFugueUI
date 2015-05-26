package projekt.sonus.popups;


import projekt.sonus.rectangle.RectangleGrabber;
import projekt.sonus.sound.SoundGrabber;

import java.awt.*;

/**
 * This class is called from the InstrumentPopupFactory.
 * Handles the creation and look of the popup window for the Sax.
 * It uses the framework set in the "instrumentpopup"-class and
 * passes the desired instruments and corresponding labels.
 */

public class SaxPopup extends InstrumentPopup {

    private final static Color SAX_COLOR = new Color(0, 199, 140);

    private static final int INSTRUMENT_SIZE = 5;

    private static final String SAX_1 = " I[SOPRANO_SAX] ";
    private static final String SAX_2 = " I[BASSOON] ";
    private static final String SAX_3 = " I[CLARINET] ";
    private static final String SAX_4 = " I[FLUTE] ";
    private static final String SAX_5 = " I[ENGLISH_HORN] ";

    SaxPopup(SoundGrabber soundGrabber, RectangleGrabber rectangleGrabber) {
        this.setInstrumentID("Sax");
        this.setRectangleGrabber(rectangleGrabber);
        this.setSoundGrabber(soundGrabber);
        this.setInstrumentColor(SAX_COLOR);

        String[] saxLabels = new String[INSTRUMENT_SIZE];
        saxLabels[0] = "Soprano Sax";
        saxLabels[1] = "Bassoon";
        saxLabels[2] = "Clarinet";
        saxLabels[3] = "Flute";
        saxLabels[4] = "Horn";

        String[] saxList = new String[INSTRUMENT_SIZE];
        saxList[0] = SAX_1;
        saxList[1] = SAX_2;
        saxList[2] = SAX_3;
        saxList[3] = SAX_4;
        saxList[4] = SAX_5;

        this.setInstrumentLabels(saxLabels);
        this.setInstrumentList(saxList);

        panelMaker(getContentPane());
        createWindow();

    }
}
