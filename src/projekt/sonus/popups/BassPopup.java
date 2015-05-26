package projekt.sonus.popups;


import projekt.sonus.rectangle.RectangleGrabber;
import projekt.sonus.sound.SoundGrabber;

import java.awt.*;

/**
 * This class is called from the InstrumentPopupFactory.
 * Handles the creation and look of the popup window for the Bass.
 * It uses the framework set in the "instrumentpopup"-class and
 * passes the desired instruments and corresponding labels.
 */

public class BassPopup extends InstrumentPopup {

    private final static Color BASS_COLOR = new Color(227, 168, 105);

    private static final int INSTRUMENT_SIZE = 5;

    private static final String BASS_1 = " I[ACOUSTIC_BASS] ";
    private static final String BASS_2 = " I[STRING_ENSEMBLE_1] ";
    private static final String BASS_3 = " I[CONTRABASS] ";
    private static final String BASS_4 = " I[SLAP_BASS_1] ";
    private static final String BASS_5 = " I[SYNTH_BASS_1] ";


    BassPopup(SoundGrabber soundGrabber, RectangleGrabber rectangleGrabber) {
        this.setInstrumentID("Bass");
        this.setRectangleGrabber(rectangleGrabber);
        this.setSoundGrabber(soundGrabber);

        this.setInstrumentColor(BASS_COLOR);


        String[] bassLabels = new String[INSTRUMENT_SIZE];
        bassLabels[0] = "Acoustic Bass";
        bassLabels[1] = "String Ensemble";
        bassLabels[2] = "Contrabass";
        bassLabels[3] = "Slap Bass";
        bassLabels[4] = "Synth Bass";

        String[] bassList = new String[INSTRUMENT_SIZE];
        bassList[0] = BASS_1;
        bassList[1] = BASS_2;
        bassList[2] = BASS_3;
        bassList[3] = BASS_4;
        bassList[4] = BASS_5;

        this.setInstrumentLabels(bassLabels);
        this.setInstrumentList(bassList);

        panelMaker(getContentPane());
        createWindow();
    }
}

