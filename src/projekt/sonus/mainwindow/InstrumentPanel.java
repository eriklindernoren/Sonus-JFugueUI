package projekt.sonus.mainwindow;


import projekt.sonus.popups.DrumPopup;
import projekt.sonus.popups.InstrumentPopup;
import projekt.sonus.popups.InstrumentPopupFactory;
import projekt.sonus.rectangle.RectangleGrabber;
import projekt.sonus.sound.SoundGrabber;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

/**
 * Creates the left-most panel which contains the instruments and its corresponding popup-windows in which the sound is
 * created and edited. The instrument buttons triggers the popup windows and the InstrumentPopupFactory takes a string
 * which defines what type of popup window is created. When calling on the popup factory it passes the grabbers needed
 * to make sure the rest of the application knows what is created and edited in the popup window.
 * We decided to use the Singleton design pattern to ensure that only one instance of the InstrumentPanel is created.
 */


public final class InstrumentPanel extends JPanel {
    //SINGLETON
    private static final InstrumentPanel INSTANCE = new InstrumentPanel();
    //CONSTANTS
    private final static int INSTRUMENT_LIST_SIZE = 8;
    private static final int BUTTON_PADDING_X = 45;
    private static final int BUTTON_PADDING_Y = 45;
    //COLOR
    private static final Color BUTTON_BACKGROUND = Color.WHITE;
    //SOUND GRABBER
    private SoundGrabber soundGrabber = null;
    //RECTANGLE GRABBER
    private RectangleGrabber rectangleGrabber = null;
    // INSTRUMENT POPUP FACTORY
    private InstrumentPopupFactory popupFactory;
    // INSTRUMENT COLOR FOR DRUMS
    private final static Color DRUMS_COLOR = new Color(205, 198, 115);
    //MAINFRAME
    private MainFrame mainFrame = null;
    //BUTTONS
    private final JButton[] buttonList = new JButton[INSTRUMENT_LIST_SIZE];
    //INSTRUMENT ID:S
    private final String[] instrumentID = new String[INSTRUMENT_LIST_SIZE];
    //ICONS
    private final String[] instrumentIcons = new String[INSTRUMENT_LIST_SIZE];
    //FOCUSED ICONS
    private final String[] iconsFocused = new String[INSTRUMENT_LIST_SIZE];

    public static InstrumentPanel getInstance(MainFrame main) {
        INSTANCE.mainFrame = main;
        return INSTANCE;
    }

    //Constructor
    private InstrumentPanel() {
        popupFactory = new InstrumentPopupFactory();
        gridMaker();
    }


    /**
     * Makes the grid, buttons and actions for iPanel
     */
    void gridMaker() {

        this.setLayout(new GridBagLayout());
        GridBagConstraints instrumentC = new GridBagConstraints();

        String fs = File.separator;

        instrumentIcons[0] = ".." + fs + "icons" + fs + "guitar.png";
        instrumentIcons[1] = ".." + fs + "icons" + fs + "saxophone.png";
        instrumentIcons[2] = ".." + fs + "icons" + fs + "drums.png";
        instrumentIcons[3] = ".." + fs + "icons" + fs + "bass.png";
        instrumentIcons[4] = ".." + fs + "icons" + fs + "piano.png";
        instrumentIcons[5] = ".." + fs + "icons" + fs + "misc.png";
        instrumentIcons[6] = ".." + fs + "icons" + fs + "misc2.png";
        instrumentIcons[7] = ".." + fs + "icons" + fs + "misc3.png";


        iconsFocused[0] = ".." + fs + "icons" + fs + "guitar_focus.png";
        iconsFocused[1] = ".." + fs + "icons" + fs + "saxophone_focus.png";
        iconsFocused[2] = ".." + fs + "icons" + fs + "drums_focus.png";
        iconsFocused[3] = ".." + fs + "icons" + fs + "bass_focus.png";
        iconsFocused[4] = ".." + fs + "icons" + fs + "piano_focus.png";
        iconsFocused[5] = ".." + fs + "icons" + fs + "misc_focus.png";
        iconsFocused[6] = ".." + fs + "icons" + fs + "misc2_focus.png";
        iconsFocused[7] = ".." + fs + "icons" + fs + "misc3_focus.png";


        instrumentID[0] = "Guitar";
        instrumentID[1] = "Saxophone";
        instrumentID[2] = "Drums";
        instrumentID[3] = "Bass";
        instrumentID[4] = "Piano";
        instrumentID[5] = "Misc";
        instrumentID[6] = "Misc 2";
        instrumentID[7] = "Misc 3";

        /**
         * The for loop goes through the list with the instruments and adds the matching icon to that button
         * and.
         */


        int columnIndex = 0;
        int rowIndex = 0;
        for (int i = 0; i < buttonList.length; i++) {
            buttonList[i] = new JButton();
            try {
                Image img = ImageIO.read(getClass().getResource(instrumentIcons[i]));
                buttonList[i].setIcon(new ImageIcon(img));
            } catch (IOException ignored) {
                System.out.println("Icon error");
            }
            buttonList[i].setBackground(BUTTON_BACKGROUND);
            instrumentC.fill = GridBagConstraints.BOTH;
            instrumentC.ipady = BUTTON_PADDING_Y;
            instrumentC.ipadx = BUTTON_PADDING_X;
            instrumentC.gridx = columnIndex;
            instrumentC.gridy = rowIndex;
            this.add(buttonList[i], instrumentC);


            columnIndex = (columnIndex < 1 ? columnIndex + 1 : 0);
            if (columnIndex == 0) {
                rowIndex += 1;
            }
        }

        setInstrumentPanelListeners();

    }

    /**
     * Goes through the list of instruments and sets the string which will be passed to the InstrumentPopupFactory
     * when the button is pressed. That string is what the InstrumentPopupFactory reads and returns the matching type
     * of popup-window for that instrument.
     */

    private void setInstrumentPanelListeners() {

        for (int i = 0; i < INSTRUMENT_LIST_SIZE; i++) {

            /**
             * The ActionListener is here set to clear the selected element in the DirectoryPanels saveList.
             * For some reason this produces a 'ArrayIndexOutOfBoundsException: -1', but the .clearListSelection
             * method in the DirectoryPanel class does find the right index and deletes it as it should.
             * We have google:d the issue and found that many have the same issue but there has been no solution
             * that has worked for us to remove the exception.
             */

            final int index = i;
            buttonList[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (instrumentID[index].equals("Drums")) {
                        DrumPopup drumPopup = new DrumPopup(soundGrabber, rectangleGrabber, DRUMS_COLOR);
                        System.out.println(drumPopup.getTitle() + "Popup created.");
                        try {
                            mainFrame.clearDirectoryList();
                        } catch (IndexOutOfBoundsException ex) {
                            System.out.println("clearListSelection(); gives " + ex.toString());
                            System.out.println("Everything works though..");
                        }

                    } else {
                        InstrumentPopup instrumentPopup =
                                popupFactory.makeInstrumentPopup(instrumentID[index], soundGrabber, rectangleGrabber);
                        System.out.println(instrumentPopup.getTitle() + "Popup created.");
                        try {
                            mainFrame.clearDirectoryList();
                        } catch (IndexOutOfBoundsException ex) {
                            System.out.println("clearListSelection(); gives " + ex.toString());
                            System.out.println("Everything works though..");
                        }
                    }

                }
            });
            buttonList[i].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    try {
                        Image img = ImageIO.read(getClass().getResource(iconsFocused[index]));
                        buttonList[index].setIcon(new ImageIcon(img));
                    } catch (IOException ignored) {
                        System.out.println("Icon error");
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    try {
                        Image img = ImageIO.read(getClass().getResource(instrumentIcons[index]));
                        buttonList[index].setIcon(new ImageIcon(img));
                    } catch (IOException ignored) {
                        System.out.println("Icon error");
                    }
                }
            });

            /**
             * The mouse listener clears the selected element in the DirectoryPanels saveList. For some reason
             * this produces a 'ArrayIndexOutOfBoundsException: -1', but the .clearListSelection method in the
             * DirectoryPanel class does find the right index and deletes it as it should. We have google:d the issue and found
             * that many have the same issue but there has been no solution that has worked for us to remove the exception.
             */

            this.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        mainFrame.clearDirectoryList();
                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println("clearListSelection(); gives " + ex.toString());
                        System.out.println("Everything works though..");
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
        }
    }

    public void setSoundGrabber(SoundGrabber soundGrabber) {
        this.soundGrabber = soundGrabber;
    }

    public void setRectangleGrabber(RectangleGrabber rectangleGrabber) {
        this.rectangleGrabber = rectangleGrabber;
    }


}
