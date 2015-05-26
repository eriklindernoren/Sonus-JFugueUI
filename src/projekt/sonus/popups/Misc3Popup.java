package projekt.sonus.popups;


import projekt.sonus.rectangle.RectangleGrabber;
import projekt.sonus.sound.SoundGrabber;

import java.awt.*;

/**
 * This class is called from the InstrumentPopupFactory.
 * Handles the creation and look of the popup window for the Horse.
 * It uses the framework set in the "instrumentpopup"-class and
 * passes the desired instruments and corresponding labels.
 */

public class Misc3Popup extends InstrumentPopup {

    private final static Color MISC_3_COLOR = new Color(227, 100, 20);

    private static final int INSTRUMENT_SIZE = 5;

    private static final String MISC_3_1 = " I[VOICE] ";
    private static final String MISC_3_2 = " I[WARM] ";
    private static final String MISC_3_3 = " I[NEW_AGE] ";
    private static final String MISC_3_4 = " I[MUSIC_BOX] ";
    private static final String MISC_3_5 = " I[HONKEY_TONK] ";

    Misc3Popup(SoundGrabber soundGrabber, RectangleGrabber rectangleGrabber) {
        this.setInstrumentID("Misc 3");
        this.setRectangleGrabber(rectangleGrabber);
        this.setSoundGrabber(soundGrabber);
        this.setInstrumentColor(MISC_3_COLOR);

        String[] misc3Labels = new String[INSTRUMENT_SIZE];
        misc3Labels[0] = "Voice";
        misc3Labels[1] = "Warm";
        misc3Labels[2] = "New Age";
        misc3Labels[3] = "Music Box";
        misc3Labels[4] = "Honkey Tonk";

        String[] misc3List = new String[INSTRUMENT_SIZE];
        misc3List[0] = MISC_3_1;
        misc3List[1] = MISC_3_2;
        misc3List[2] = MISC_3_3;
        misc3List[3] = MISC_3_4;
        misc3List[4] = MISC_3_5;

        this.setInstrumentLabels(misc3Labels);
        this.setInstrumentList(misc3List);

        panelMaker(getContentPane());
        createWindow();
    }
}
