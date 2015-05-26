package projekt.sonus.popups;


import projekt.sonus.rectangle.RectangleGrabber;
import projekt.sonus.sound.SoundGrabber;

import java.awt.*;

/**
 * This class is called from the InstrumentPopupFactory.
 * Handles the creation and look of the popup window for the Swag.
 * It uses the framework set in the "instrumentpopup"-class and
 * passes the desired instruments and corresponding labels.
 */

public class Misc2Popup extends InstrumentPopup {

    private static final int INSTRUMENT_SIZE = 5;

    private final static Color MISC_2_COLOR = Color.ORANGE;

    private static final String MISC_2_1 = " I[BREATH_NOISE] ";
    private static final String MISC_2_2 = " I[BAGPIPE] ";
    private static final String MISC_2_3 = " I[ATMOSPHERE] ";
    private static final String MISC_2_4 = " I[CHOIR] ";
    private static final String MISC_2_5 = " I[HARMONICA] ";

    Misc2Popup(SoundGrabber soundGrabber, RectangleGrabber rectangleGrabber) {
        this.setInstrumentID("Misc 2");
        this.setRectangleGrabber(rectangleGrabber);
        this.setSoundGrabber(soundGrabber);
        this.setInstrumentColor(MISC_2_COLOR);

        String[] misc2Labels = new String[INSTRUMENT_SIZE];
        misc2Labels[0] = "Breath Noise";
        misc2Labels[1] = "Bagpipe";
        misc2Labels[2] = "Atmosphere";
        misc2Labels[3] = "Choir";
        misc2Labels[4] = "Harmonica";

        String[] misc2List = new String[INSTRUMENT_SIZE];
        misc2List[0] = MISC_2_1;
        misc2List[1] = MISC_2_2;
        misc2List[2] = MISC_2_3;
        misc2List[3] = MISC_2_4;
        misc2List[4] = MISC_2_5;

        this.setInstrumentLabels(misc2Labels);
        this.setInstrumentList(misc2List);

        panelMaker(getContentPane());
        createWindow();
    }
}
