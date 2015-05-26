package projekt.sonus.popups;


import projekt.sonus.rectangle.RectangleGrabber;
import projekt.sonus.sound.SoundGrabber;

/**
 * This class is called from the InstrumentPanel, it returns a popup window for the instrument
 * that matches the string that is passed from the InstrumentPanel.
 */


public class InstrumentPopupFactory {

    public InstrumentPopup makeInstrumentPopup(String popupType, SoundGrabber soundGrabber, RectangleGrabber rectangleGrabber) {
        switch (popupType) {
            case "Guitar":
                return new GuitarPopup(soundGrabber, rectangleGrabber);
            case "Saxophone":
                return new SaxPopup(soundGrabber, rectangleGrabber);
            case "Bass":
                return new BassPopup(soundGrabber, rectangleGrabber);
            case "Piano":
                return new PianoPopup(soundGrabber, rectangleGrabber);
            case "Misc":
                return new MiscPopup(soundGrabber, rectangleGrabber);
            case "Misc 2":
                return new Misc2Popup(soundGrabber, rectangleGrabber);
            case "Misc 3":
                return new Misc3Popup(soundGrabber, rectangleGrabber);
            default:
                return null;
        }
    }

}
