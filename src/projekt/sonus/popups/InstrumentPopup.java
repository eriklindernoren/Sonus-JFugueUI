package projekt.sonus.popups;

import org.jfugue.Player;
import projekt.sonus.sound.Sound;
import projekt.sonus.rectangle.RectangleGrabber;
import projekt.sonus.sound.SoundGrabber;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.HORIZONTAL;

/**
 * The InstrumentPopup class decides what the look of all the instruments popups is like. Its the instrument-class's
 * job to pass its instruments to this class and then this class handles the rest. We thought this would be the better
 * solution so that you wouldn't need to search through this class every time you wanted to change something for a
 * instrument. When the user has created a tune which he or she wants to add to the song the InstrumentPopup class
 * sets the rectangleGrabber and the soundGrabber which updates the look and sound of the main window.
 */


public abstract class InstrumentPopup extends JFrame {

    //ID
    private String instrumentID = null;
    //LAYOUTS
    private GridBagLayout upperPanelLayout = null, lowerPanelLayout = null, popupLayout = null, iPanelLayout = null;
    private GridBagLayout midPanelLayout = null, aPanelLayout = null, durationPanelLayout = null;
    private GridBagLayout rightLowerPanelLayout = null, octavePanelLayout = null, rightPanelLayout = null;
    private GridBagLayout rightUpperPanelLayout = null;
    private GridBagConstraints upperC = null, lowerC = null, popupC = null, midC = null, instrumentC = null;
    private GridBagConstraints ackordC = null, durationC = null, rightLowerC = null;
    private GridBagConstraints octaveC = null, rightC = null, rightUpperC = null;
    //PANELS
    private JPanel leftPanel = null, midPanel = null, ackordPanel = null;
    private JPanel durationPanel = null, rightLowerPanel = null, upperPanel = null;
    private JPanel rightPanel = null, lowerPanel = null, octavePanel = null, rightUpperPanel = null;
    //COLOR
    private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);
    private static final Color BUTTON_BACKGROUND = Color.WHITE;
    private static final Color BORDER_COLOR = Color.WHITE;
    private Color instrumentColor = null;
    private static final Color ACTIVE_BUTTON_COLOR = Color.GRAY;
    private static final Color INACTIVE_BUTTON_COLOR = Color.BLACK;
    //CONSTANTS
    private final static int INSTRUMENT_BUTTON_X = 40;
    private final static int INSTRUMENT_BUTTON_Y = 68;
    private final static int TEXT_FIELD_SIZE = 50;
    private final static int ACKORD_LIST_SIZE = 10;
    private final static int ACKORD_BUTTON_X = 18;
    private final static int ACKORD_BUTTON_Y = 10;
    private final static int DURATION_SIZE = 6;
    private final static int DURATION_BUTTON_X = 0;
    private final static int DURATION_BUTTON_Y = 20;
    private final static int REP_MAX = 10;
    private final static int REP_LOW = 1;
    private final static int REP_INIT = 1;
    private final static int VOL_MAX = 16000;
    private final static int VOL_LOW = 0;
    private final static int VOL_INIT = 12000;
    private final static int CHANNEL_LOW = 1;
    private final static int CHANNEL_MAX = 7;
    private final static int CHANNEL_INIT = 1;
    private final static int SLIDER_X = 20;
    private final static int SLIDER_Y = 20;
    private final static int RIGHT_C_PAD_X = 0;
    private final static int RIGHT_C_PAD_Y = 30;
    private final static int RIGHT_BUTTON_X = 100;
    private final static int RIGHT_BUTTON_Y = 30;
    private final static int LOWER_C_PAD_X = 20;
    private final static int LOWER_C_PAD_Y = 20;
    private final static int UPPER_C_PAD_X = 0;
    private final static int UPPER_C_PAD_Y = 14;
    private JButton back = null, preview = null, add = null, clear = null;
    //SLIDERS
    private JSlider repeat = null, volume = null, channel = null;
    //CHANNEL
    private int channelValue;
    //VOLUME
    private int volumeValue;
    //REPEAT
    private int repeatedTimes;
    //LIST WITH BUTTONS
    private JButton[] instrumentButtons = null;
    private JButton[] ackordButtons = null;
    private JButton[] durationButtons = null;
    private JButton[] octaveButtons = null;
    //LOWER TEXT AREA
    private JTextField textField = null;
    //PLAYER
    private Player player = null;
    private Sound popupSound = null;
    private Sound testSound = null;
    //ACTIVE VALUES
    private String activeInstrument = null;
    // INSTRUMENTS
    private String[] instrumentLabels = null;
    private String[] instrumentList = null;
    //SOUND GRABBER
    private SoundGrabber soundGrabber = null;
    //RECTANGLE GRABBER
    private RectangleGrabber rectangleGrabber = null;
    //DURATION STRINGS
    private String[] durationStrings = null;
    private String[] durations = null;
    //PREV ACKORD SOUND
    private String lastAddedSound = null;


    void panelMaker(Container pane) {
        //Sets the mayor layout of the popup window.
        popupLayout = new GridBagLayout();
        pane.setLayout(popupLayout);
        popupC = new GridBagConstraints();

        /**
         * The upper panel houses the instrument panel, the mid panel and the right panel. Who in their turn houses
         * separate panels with buttons.
         */

        makeUpperPanel();

        makeLeftPanel();
        upperPanel.add(leftPanel, upperC);

        makeMidPanel();
        makeAckordPanel();
        midPanel.add(ackordPanel, midC);
        makeOctavePanel();
        midPanel.add(octavePanel, midC);
        makeDurationPanel();
        midPanel.add(durationPanel, midC);
        upperPanel.add(midPanel, upperC);

        makeRightPanel();
        makeRightUpperPanel();
        rightPanel.add(rightUpperPanel, rightC);
        makeRightLowerPanel();
        rightPanel.add(rightLowerPanel, rightC);
        upperPanel.add(rightPanel, upperC);

        pane.add(upperPanel, popupC);

        makeLowerPanel();
        pane.add(lowerPanel, popupC);


        addActionListenersToButtons();


        /**
         * The default instrument in JFugue is the piano. "instrumentButtons[0].doClick();" ensures that
         * when you for example operate the guitar popup, it always set the first guitar instrument as the default
         * instrument when opening the popup, and not the piano.
         */
        instrumentButtons[0].doClick();


    }

    private void makeUpperPanel() {
        upperPanel = new JPanel();
        upperPanel.setBackground(BACKGROUND_COLOR);
        setLayout(popupLayout);
        popupC.ipadx = 0;
        popupC.ipady = 0;
        popupC.gridx = 0;
        popupC.gridy = 0;

        /**
         * Sets the layout for leftPanel, midPanel and the rightPanel, which the upper panel houses.
         */
        upperPanelLayout = new GridBagLayout();
        upperC = new GridBagConstraints();
        upperPanel.setLayout(upperPanelLayout);
    }

    /**
     * The left panel is located in the left most part of the upperPanel and houses the instrumentbuttons.
     */

    private void makeLeftPanel() {

        Border lineBorder = BorderFactory.createLineBorder(BORDER_COLOR, 2);
        Border instrumentBorder = BorderFactory.createTitledBorder(lineBorder, "Instruments", 2, 3);


        leftPanel = new JPanel();
        leftPanel.setBorder(instrumentBorder);
        leftPanel.setBackground(BACKGROUND_COLOR);
        setLayout(upperPanelLayout);
        upperC.ipadx = UPPER_C_PAD_X;
        upperC.ipady = UPPER_C_PAD_Y;
        upperC.gridx = 0;
        upperC.gridy = 0;

        iPanelLayout = new GridBagLayout();
        instrumentC = new GridBagConstraints();
        leftPanel.setLayout(iPanelLayout);

        makeInstrumentButtons();

    }

    /**
     * The instrumentPopup class gets passed the desired instruments and their labels from its subclasses when it
     * is created, and those instruments and labels gets set in this method.
     */

    private void makeInstrumentButtons() {

        instrumentButtons = new JButton[5];

        for (int i = 0; i < instrumentLabels.length; i++) {
            instrumentButtons[i] = new JButton();
            instrumentButtons[i].setText(instrumentLabels[i]);
            instrumentButtons[i].setForeground(INACTIVE_BUTTON_COLOR);
            setLayout(iPanelLayout);
            instrumentC.ipadx = INSTRUMENT_BUTTON_X;
            instrumentC.ipady = INSTRUMENT_BUTTON_Y;
            instrumentC.gridx = 0;
            instrumentC.gridy = i;
            instrumentC.fill = GridBagConstraints.BOTH;
            leftPanel.add(instrumentButtons[i], instrumentC);
            final int index = i;

            /**
             * We wanted the user to get a stronger feeling of that the button that the mouse is over is in focus, so
             * this adds that the button labels changes color when you move the mouse over it. Every instrument type
             * has its own color in the application so we made it so that the instrument buttons labels would
             * change into that instrument types special color.
             */
            instrumentButtons[i].addMouseListener(new MouseListener() {
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
                    instrumentButtons[index].setForeground(instrumentColor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    instrumentButtons[index].setForeground(INACTIVE_BUTTON_COLOR);
                }
            });
        }

    }

    /**
     * The midPanel is located in the central part of the upperpanel and houses three separate panels. Those
     * in their turn houses the ackordButtons, the octaveButtons and the durationButtons.
     */

    private void makeMidPanel() {
        midPanel = new JPanel();
        midPanel.setBackground(BACKGROUND_COLOR);
        setLayout(upperPanelLayout);
        upperC.ipadx = 0;
        upperC.ipady = 0;
        upperC.gridx = 1;
        upperC.gridy = 0;
        upperC.fill = GridBagConstraints.BOTH;

        /**
         * The midPanel has three separate panels in it and this creates the layout for those panels.
         */
        midPanelLayout = new GridBagLayout();
        midC = new GridBagConstraints();
        midPanel.setLayout(midPanelLayout);
    }

    /**
     * Located at the top of the midPanel.
     */

    private void makeAckordPanel() {

        Border lineBorder = BorderFactory.createLineBorder(BORDER_COLOR, 2);
        Border ackordBorder = BorderFactory.createTitledBorder(lineBorder, "Ackords", 2, 3);

        ackordPanel = new JPanel();
        ackordPanel.setBackground(BACKGROUND_COLOR);
        ackordPanel.setBorder(ackordBorder);
        setLayout(midPanelLayout);
        midC.ipadx = 0;
        midC.ipady = 0;
        midC.gridx = 0;
        midC.gridy = 0;
        midC.fill = GridBagConstraints.BOTH;

        aPanelLayout = new GridBagLayout();
        ackordC = new GridBagConstraints();
        ackordPanel.setLayout(aPanelLayout);

        makeAckButtons();
    }

    private void makeAckButtons() {

        String[] ackords = new String[ACKORD_LIST_SIZE];
        ackords[0] = "A";
        ackords[1] = "B";
        ackords[2] = "C";
        ackords[3] = "D";
        ackords[4] = "E";
        ackords[5] = "F";
        ackords[6] = "G";
        ackords[7] = "Maj";
        ackords[8] = "Min";
        ackords[9] = "#";

        ackordButtons = new JButton[ACKORD_LIST_SIZE];

        int columnIndex = 0;
        int rowIndex = 0;
        for (int i = 0; i < ackordButtons.length; i++) {
            ackordButtons[i] = new JButton();
            ackordButtons[i].setText(ackords[i]);
            ackordButtons[i].setBackground(BUTTON_BACKGROUND);
            ackordButtons[i].setForeground(INACTIVE_BUTTON_COLOR);
            setLayout(aPanelLayout);
            ackordC.gridx = columnIndex;
            ackordC.gridy = rowIndex;
            ackordC.ipadx = ACKORD_BUTTON_X;
            ackordC.ipady = ACKORD_BUTTON_Y;
            ackordC.fill = GridBagConstraints.BOTH;
            ackordPanel.add(ackordButtons[i], ackordC);

            final int index = i;

            /**
             * We wanted the user to get a stronger feeling of that the button that the mouse is over is in focus, so
             * this adds that the button labels changes color when you move the mouse over it.
             */
            ackordButtons[i].addMouseListener(new MouseListener() {
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
                    ackordButtons[index].setForeground(ACTIVE_BUTTON_COLOR);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    ackordButtons[index].setForeground(INACTIVE_BUTTON_COLOR);
                }
            });

            columnIndex = (columnIndex < 1 ? columnIndex + 1 : 0);
            if (columnIndex == 0) {
                rowIndex += 1;
            }
        }

    }

    /**
     * Located in the middle part of the midPanel.
     */

    private void makeOctavePanel() {

        Border lineBorder = BorderFactory.createLineBorder(BORDER_COLOR, 2);
        Border octaveBorder = BorderFactory.createTitledBorder(lineBorder, "Octave", 2, 3);

        octavePanel = new JPanel();
        octavePanel.setBackground(BACKGROUND_COLOR);
        octavePanel.setBorder(octaveBorder);
        setLayout(midPanelLayout);
        midC.gridx = 0;
        midC.gridy = 1;
        midC.ipadx = 0;
        midC.ipady = 0;
        midC.fill = GridBagConstraints.HORIZONTAL;

        octavePanelLayout = new GridBagLayout();
        octaveC = new GridBagConstraints();
        octavePanel.setLayout(octavePanelLayout);

        makeOctaveButtons();

    }

    private void makeOctaveButtons() {

        octaveButtons = new JButton[9];

        int columnIndex = 0;
        int rowIndex = 0;
        for (int i = 0; i < octaveButtons.length; i++) {
            octaveButtons[i] = new JButton();
            octaveButtons[i].setText(String.valueOf(i + 1));
            octaveButtons[i].setForeground(INACTIVE_BUTTON_COLOR);
            setLayout(octavePanelLayout);
            octaveC.ipady = 5;
            octaveC.gridy = rowIndex;
            octaveC.gridx = columnIndex;
            octavePanel.add(octaveButtons[i], octaveC);

            columnIndex = (columnIndex <= 1 ? columnIndex + 1 : 0);
            if (columnIndex == 0) {
                rowIndex += 1;
            }

            /**
             * We wanted the user to get a stronger feeling of that the button that the mouse is over is in focus, so
             * this adds that the button labels changes color when you move the mouse over it.
             */
            final int index = i;
            octaveButtons[i].addMouseListener(new MouseListener() {
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
                    octaveButtons[index].setForeground(ACTIVE_BUTTON_COLOR);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    octaveButtons[index].setForeground(INACTIVE_BUTTON_COLOR);
                }
            });
        }
    }


    /**
     * Located in the lower part of the midPanel.
     */

    private void makeDurationPanel() {


        Border lineBorder = BorderFactory.createLineBorder(BORDER_COLOR, 2);
        Border durationBorder = BorderFactory.createTitledBorder(lineBorder, "Duration", 2, 3);

        durationPanel = new JPanel();
        durationPanel.setBackground(BACKGROUND_COLOR);
        durationPanel.setBorder(durationBorder);
        setLayout(midPanelLayout);
        midC.ipadx = 0;
        midC.ipady = 0;
        midC.gridx = 0;
        midC.gridy = 2;
        midC.fill = GridBagConstraints.BOTH;

        durationPanelLayout = new GridBagLayout();
        durationC = new GridBagConstraints();
        durationPanel.setLayout(durationPanelLayout);

        makeDurationButtons();
    }

    private void makeDurationButtons() {

        /**
         * The labels of the durationbuttons. These are added to the textfield in parentese when a button i pressed,
         * i.e "(Half)".
         */

        durations = new String[DURATION_SIZE];
        durations[0] = "Whole";
        durations[1] = "Half";
        durations[2] = "Quarter";
        durations[3] = " Eighth  ";
        durations[4] = "Sixteenth";
        durations[5] = "Silent";

        durationButtons = new JButton[DURATION_SIZE];

        int columnIndex = 0;
        int rowIndex = 0;
        for (int i = 0; i < durationButtons.length; i++) {
            durationButtons[i] = new JButton();
            durationButtons[i].setText(durations[i]);
            durationButtons[i].setBackground(BUTTON_BACKGROUND);
            durationButtons[i].setForeground(INACTIVE_BUTTON_COLOR);
            setLayout(durationPanelLayout);
            durationC.gridx = columnIndex;
            durationC.gridy = rowIndex;
            durationC.ipadx = DURATION_BUTTON_X;
            durationC.ipady = DURATION_BUTTON_Y;
            durationC.fill = GridBagConstraints.BOTH;
            durationPanel.add(durationButtons[i], durationC);


            /**
             * We wanted the user to get a stronger feeling of that the button that the mouse is over is in focus, so
             * this adds that the button labels changes color when you move the mouse over it.
             */
            final int index = i;
            durationButtons[i].addMouseListener(new MouseListener() {
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
                    durationButtons[index].setForeground(ACTIVE_BUTTON_COLOR);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    durationButtons[index].setForeground(INACTIVE_BUTTON_COLOR);
                }
            });

            columnIndex = (columnIndex < 1 ? columnIndex + 1 : 0);
            if (columnIndex == 0) {
                rowIndex += 1;
            }
        }
    }

    /**
     * The rightPanel is located in the right most part of the upperPanel and houses two panels.
     * The upper most one houses the sliders and the one in the lower part houses some buttons for adding the sound
     * and so one.
     */


    private void makeRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setBackground(BACKGROUND_COLOR);
        setLayout(upperPanelLayout);
        upperC.ipadx = 0;
        upperC.ipady = 0;
        upperC.gridx = 2;
        upperC.gridy = 0;
        upperC.fill = GridBagConstraints.VERTICAL;


        rightPanelLayout = new GridBagLayout();
        rightC = new GridBagConstraints();
        rightPanel.setLayout(rightPanelLayout);

    }

    /**
     * The rightUpperPanel contains the volume-, channel-, and repeat sliders.
     */

    private void makeRightUpperPanel() {
        Border lineBorder = BorderFactory.createLineBorder(BORDER_COLOR, 2);
        Border rightBorder = BorderFactory.createTitledBorder(lineBorder, "", 2, 3);

        rightUpperPanel = new JPanel();
        rightUpperPanel.setBackground(BACKGROUND_COLOR);
        rightUpperPanel.setBorder(rightBorder);
        setLayout(rightPanelLayout);
        rightC.ipadx = 0;
        rightC.ipady = 0;
        rightC.gridx = 0;
        rightC.gridy = 0;
        rightC.fill = GridBagConstraints.HORIZONTAL;

        rightUpperPanelLayout = new GridBagLayout();
        rightUpperC = new GridBagConstraints();
        rightUpperPanel.setLayout(rightUpperPanelLayout);

        makeRightSliders();
    }

    /**
     * The rightLowerPanel contains the add-, clear-, preview- and back button.
     */


    private void makeRightLowerPanel() {


        Border lineBorder = BorderFactory.createLineBorder(BORDER_COLOR, 2);
        Border rightBorder = BorderFactory.createTitledBorder(lineBorder, "", 2, 3);

        rightLowerPanel = new JPanel();
        rightLowerPanel.setBackground(BACKGROUND_COLOR);
        rightLowerPanel.setBorder(rightBorder);
        setLayout(rightPanelLayout);
        rightC.ipadx = RIGHT_C_PAD_X;
        rightC.ipady = RIGHT_C_PAD_Y;
        rightC.gridx = 0;
        rightC.gridy = 1;
        rightC.fill = GridBagConstraints.HORIZONTAL;

        rightLowerPanelLayout = new GridBagLayout();
        rightLowerC = new GridBagConstraints();
        rightLowerPanel.setLayout(rightLowerPanelLayout);


        makeRightButtons();
    }


    private void makeRightSliders() {

        String[] sliders = new String[3];
        sliders[0] = "Repeat";
        sliders[1] = "Volume";
        sliders[2] = "Channel";


        JLabel repeatLabel = new JLabel(sliders[0], CENTER);
        repeat = new JSlider(HORIZONTAL, REP_LOW, REP_MAX, REP_INIT);
        repeat.setMinorTickSpacing(REP_LOW);
        repeat.setSnapToTicks(true);
        repeat.setPaintTicks(true);

        JLabel volumeLabel = new JLabel(sliders[1], CENTER);
        volume = new JSlider(HORIZONTAL, VOL_LOW, VOL_MAX, VOL_INIT);
        volume.setMajorTickSpacing(VOL_MAX);
        volume.setMinorTickSpacing(VOL_LOW);


        JLabel channelLabel = new JLabel(sliders[2], CENTER);
        channel = new JSlider(HORIZONTAL, CHANNEL_LOW, CHANNEL_MAX, CHANNEL_INIT);
        channel.setMajorTickSpacing(CHANNEL_MAX);
        channel.setMinorTickSpacing(CHANNEL_LOW);
        channel.setPaintTicks(true);
        channel.setSnapToTicks(true);

        JLabel[] sliderLabels = new JLabel[3];
        sliderLabels[0] = repeatLabel;
        sliderLabels[1] = volumeLabel;
        sliderLabels[2] = channelLabel;

        JSlider[] rightSliders = new JSlider[3];
        rightSliders[0] = repeat;
        rightSliders[1] = volume;
        rightSliders[2] = channel;

        int y = 0;
        for (int i = 0; i < rightSliders.length; i++) {
            sliderLabels[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            setLayout(rightUpperPanelLayout);
            rightUpperC.gridx = 0;
            rightUpperC.gridy = y;
            rightUpperC.fill = GridBagConstraints.BOTH;
            rightUpperPanel.add(sliderLabels[i], rightUpperC);
            y += 1;

            rightSliders[i].setBackground(BUTTON_BACKGROUND);
            setLayout(rightUpperPanelLayout);
            rightUpperC.gridx = 0;
            rightUpperC.gridy = y;
            rightUpperC.ipadx = SLIDER_X;
            rightUpperC.ipady = SLIDER_Y;
            rightUpperC.fill = GridBagConstraints.BOTH;
            rightUpperPanel.add(rightSliders[i], rightUpperC);
            y += 1;

        }


    }

    private void makeRightButtons() {

        player = new Player();
        popupSound = new Sound();

        String[] buttons = new String[4];
        buttons[0] = "Back";
        buttons[1] = "Preview";
        buttons[2] = "Add";
        buttons[3] = "Clear";

        back = new JButton();
        preview = new JButton();
        add = new JButton();
        clear = new JButton();

        final JButton[] rightButtons = new JButton[4];
        rightButtons[0] = back;
        rightButtons[1] = preview;
        rightButtons[2] = add;
        rightButtons[3] = clear;

        for (int i = 0; i < rightButtons.length; i++) {
            rightButtons[i].setText(buttons[i]);
            rightButtons[i].setBackground(BUTTON_BACKGROUND);
            rightButtons[i].setForeground(INACTIVE_BUTTON_COLOR);
            setLayout(rightLowerPanelLayout);
            rightLowerC.gridx = 0;
            rightLowerC.gridy = i;
            rightLowerC.ipadx = RIGHT_BUTTON_X;
            rightLowerC.ipady = RIGHT_BUTTON_Y;
            rightLowerC.fill = GridBagConstraints.BOTH;
            rightLowerPanel.add(rightButtons[i], rightLowerC);

            /**
             * We wanted the user to get a stronger feeling of that the button that the mouse is over is in focus, so
             * this adds that the button labels changes color when you move the mouse over it.
             */
            final int index = i;
            rightButtons[i].addMouseListener(new MouseListener() {
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
                    rightButtons[index].setForeground(ACTIVE_BUTTON_COLOR);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    rightButtons[index].setForeground(INACTIVE_BUTTON_COLOR);
                }
            });
        }
        /**
         * So that the add button will change into the selected instrument types special color when in focus.
         */
        add.addMouseListener(new MouseListener() {
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
                add.setForeground(instrumentColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                add.setForeground(INACTIVE_BUTTON_COLOR);
            }
        });
    }

    /**
     * The lowerPanel is located in the lower part of the window and contains the textfield, in which the sound that
     * has been added to the musicstring is displayed.
     */

    private void makeLowerPanel() {
        Border lineBorder = BorderFactory.createLineBorder(BORDER_COLOR, 2);
        Border lowerBorder = BorderFactory.createTitledBorder(lineBorder, "", 2, 3);

        lowerPanel = new JPanel();
        lowerPanel.setBackground(BACKGROUND_COLOR);
        lowerPanel.setBorder(lowerBorder);
        setLayout(popupLayout);
        popupC.ipadx = 0;
        popupC.ipady = 0;
        popupC.gridx = 0;
        popupC.gridy = 1;

        lowerPanelLayout = new GridBagLayout();
        lowerC = new GridBagConstraints();
        lowerPanel.setLayout(lowerPanelLayout);

        makeLowerTextArea();
    }

    private void makeLowerTextArea() {


        textField = new JTextField(TEXT_FIELD_SIZE);
        textField.setEditable(false);
        setLayout(lowerPanelLayout);
        lowerC.gridx = 0;
        lowerC.gridy = 0;
        lowerC.ipadx = LOWER_C_PAD_X;
        lowerC.ipady = LOWER_C_PAD_Y;
        lowerC.fill = GridBagConstraints.HORIZONTAL;
        lowerPanel.add(textField, lowerC);
    }

    void addActionListenersToButtons() {

        /**
         * The functionality of ackord, duration and octave buttons is the same for all types of
         * instrument and doesn't change so we decided to set their musicstring here. These
         * are later read and translated to sound by JFugue.
         */

        final String[] ackordStrings = new String[ackordButtons.length];
        ackordStrings[0] = " A";
        ackordStrings[1] = " B";
        ackordStrings[2] = " C";
        ackordStrings[3] = " D";
        ackordStrings[4] = " E";
        ackordStrings[5] = " F";
        ackordStrings[6] = " G";
        ackordStrings[7] = "maj ";
        ackordStrings[8] = "min ";
        ackordStrings[9] = "#";

        durationStrings = new String[durationButtons.length];
        durationStrings[0] = "w";
        durationStrings[1] = "h";
        durationStrings[2] = "q";
        durationStrings[3] = "i";
        durationStrings[4] = "s";
        durationStrings[5] = " R ";

        String[] octaveStrings = new String[octaveButtons.length];
        for (int i = 0; i < octaveStrings.length; i++) {
            octaveStrings[i] = String.valueOf(i + 1);
        }

        setInstrumentListeners(instrumentButtons, instrumentList);
        setAckordListeners(ackordButtons, ackordStrings);
        setDurationListeners(durationButtons, durationStrings);
        setOctaveListeners(octaveButtons, octaveStrings);

        /**
         * Uses what information we have on the "lastAddedSound" to subtract
         * that from the musicstring and then from the textfield. Gives the user the ability to regret the last sound
         * that was added to the musicstring.
         */

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sound = popupSound.getMusicString();
                popupSound = new Sound();
                String prev = sound.substring(0, sound.length() - lastAddedSound.length());
                popupSound.add(prev);
                String currTextField = textField.getText();
                textField.setText(currTextField.substring(0, currTextField.length() - lastAddedSound.length()));
                lastAddedSound = "";
            }
        });

        /**
         * Preview simply plays the sound that has been composed so far.
         */
        preview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.play(popupSound);
            }
        });
        /**
         * Resets the sound, textfield and activates the first instrumentbutton.
         */
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupSound = new Sound();
                textField.setText("");
                instrumentButtons[0].doClick();
            }
        });
        /**
         * When the add-button is activated it uses the value selected on the repeat-slider and uses the
         * addSoundLoop method found in the Sound class to repeat the created musicstring for that many times.
         * After that it inserts the volume and channel value to the musicstring. Lastly it uses the SoundGrabber
         * and RectangleGrabber classes to notify the rest of the application of what changes is needed to be made
         * in the main window.
         */
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volumeValue = volume.getValue();
                repeatedTimes = repeat.getValue();
                channelValue = channel.getValue();

                Sound tempSound = new Sound();
                tempSound.addSoundLoop(popupSound.getMusicString(), repeatedTimes);

                popupSound = tempSound;
                popupSound.insertVolume(volumeValue);
                popupSound.insertChannel(channelValue);

                /**
                 * The grabbers let their listeners know that the sound and the look of the
                 * application needs to change.
                 */
                soundGrabber.addToSound(popupSound);
                rectangleGrabber.setChannelInstrumentTimes(channelValue, instrumentID, repeatedTimes);


                dispose();
            }

        });
    }

    private void setInstrumentListeners(JButton[] buttonList, String[] currentSound) {
        for (int i = 0; i < buttonList.length; i++) {
            final String sound = currentSound[i];
            final JButton button = buttonList[i];
            final int index = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String currentString = popupSound.getMusicString();
                    /**
                     * The setting of the activeInstrument is important to make sure that the testSound plays its
                     * ackords with the particular instrument that has previously been selected.
                     */
                    activeInstrument = instrumentList[index];
                    String instrumentLabel = instrumentLabels[index];

                    /**
                     * If the instrumentbutton is activated right after another instrumentbutton we wanted to
                     * replace the first instrument command with the first so that the musicstring and textfield
                     * don't get cluttered up with unnessesary commands.
                     */

                    for (int i = 0; i < instrumentList.length; i++) {
                        if (currentString.endsWith(instrumentList[i])) {
                            String prevSound = currentString.substring(
                                    0, currentString.length() - instrumentList[i].length()
                            );
                            popupSound = new Sound();
                            popupSound.add(prevSound + sound);
                            String newTextField = textField.getText().substring(
                                    0, textField.getText().length() - instrumentLabels[i].length() - 2
                            );
                            textField.setText(newTextField + instrumentLabel + ", ");
                            return;
                        }
                    }

                    /**
                     * We use the || to illustrate that from there the instrument has changed, and thereby the sound.
                     */

                    lastAddedSound = "";
                    popupSound.add(activeInstrument);
                    textField.setText(textField.getText() + " || " + instrumentLabel + ", ");

                }
            });

        }

    }

    private void setAckordListeners(JButton[] buttonList, String[] currentSound) {
        for (int i = 0; i < buttonList.length; i++) {
            final String sound = currentSound[i];
            final JButton button = buttonList[i];
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String currentString = popupSound.getMusicString();

                    if (sound.equals("maj ") || sound.equals("min ") || sound.equals("#")) {
                        //Checks so that maj, min or sharp isn't added after a duration since that results in a crash.
                        for (String duration : durationStrings) {
                            if (!(duration.equals(" R ")) && currentString.endsWith(duration)) {
                                return;
                            }
                        }
                        if (sound.equals("#")) {
                            // Checks so that sharp isn't added if it comes directly after a octave, since that causes a crash.
                            for (int i = 1; i <= 9; i++) {
                                if (currentString.endsWith(String.valueOf(i))) {
                                    return;
                                }
                            }
                        } else if (sound.equals("maj ") || sound.equals("min ")) {
                            //Checks if it's pushed directly after a maj or min and replaces the previous in that case
                            if (currentString.endsWith("min ") || currentString.endsWith("maj ")) {

                                /**
                                 * We decided to use the lastAddedSound variable so that we could let the user hear
                                 * the changes that are made to the first selected ackord, for example if you first
                                 * select a "A" ackord, and then add the major command, we play the lastAddedSound
                                 * which is then "Amaj".
                                 */

                                Sound tempSound = new Sound();
                                String stringWithoutMajMin = currentString.substring(0, currentString.length() - 4);
                                lastAddedSound = lastAddedSound.substring(0, lastAddedSound.length() - 4) + sound;
                                tempSound.add(stringWithoutMajMin + sound);
                                popupSound = tempSound;
                                String newTextField = textField.getText().substring(0, textField.getText().length() - 4);
                                textField.setText(newTextField + sound);

                                Sound test = new Sound();
                                test.add(activeInstrument + lastAddedSound);
                                player.play(test);

                                return;
                            }
                        }
                        if (currentString.endsWith(" ")) {
                            return;
                        } else {
                            lastAddedSound += sound;

                            Sound test = new Sound();
                            test.add(activeInstrument + lastAddedSound);
                            player.play(test);
                        }
                    } else {
                        /**
                         * If a new Ackord is chosen the lastAddedSound is set to that Ackord.
                         */
                        lastAddedSound = sound;
                        testSound = new Sound();
                        testSound.insertVolume(volume.getValue());
                        testSound.add(lastAddedSound);
                        testSound.insertInstrumentToMusicString(activeInstrument);
                        player.play(testSound);
                    }

                    /**
                     * The reason we create tempSound is because if we would just use the popupSound.add() method it
                     * automatically adds a space between the previously added sound and the new sound, and that prohibits
                     * the use of the maj, min, sharp, duration functionallity that JFugue has.
                     */
                    Sound tempSound = new Sound();
                    tempSound.add(popupSound.getMusicString() + sound);
                    popupSound = tempSound;

                    textField.setText(textField.getText() + sound);

                }
            });
        }

    }

    private void setOctaveListeners(JButton[] buttonList, String[] currentSound) {
        for (int i = 0; i < buttonList.length; i++) {
            final String sound = currentSound[i];
            final JButton button = buttonList[i];
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String currentString = popupSound.getMusicString();

                    /**
                     * If JFugue detects two integers in a row it will crash so we added this check to ensure that
                     * the user wouldn't add two octaves in a row and make the program crash. If two octave buttons
                     * are pressed after one another the last one pressed will replace the first. The octaves
                     * should also always follow a ackord or a duration so if the last element in the musicstring
                     * is a " " it doesn't need to be added at all.
                     */

                    if (!currentString.endsWith(" ")) {
                        //Checks so it's not added directly after a duration since that would cause a crash.
                        for (String duration : durationStrings) {
                            if (!(currentString.endsWith(" R ")) && currentString.endsWith(duration)) {
                                return;
                            }
                        }
                        for (int i = 1; i <= 9; i++) {
                            //If the octave is found directly after another the first is replaced by the second.
                            if (currentString.endsWith(String.valueOf(i))) {
                                /**
                                 * Here lastAddedSound is modified so that the last chosen octave is changed to the
                                 * current one.
                                 */
                                String stringWithNewOctave = currentString.substring(0, currentString.length() - 1) + sound;
                                lastAddedSound = lastAddedSound.substring(0, lastAddedSound.length() - 1) + sound;
                                popupSound = new Sound();
                                popupSound.add(stringWithNewOctave);
                                String newTextField = textField.getText().substring(0, textField.getText().length() - 1);
                                textField.setText(newTextField + sound);

                                Sound test = new Sound();
                                test.add(activeInstrument + lastAddedSound);
                                player.play(test);

                                return;
                            }
                        }
                    } else {
                        return;
                    }

                    /**
                     * The reason we create tempSound is because if we would just use the popupSound.add() method it
                     * automatically adds a space between the previously added sound and the new sound, and that prohibits
                     * the use of the maj, min, sharp, duration functionallity that JFugue has.
                     */
                    Sound tempSound = new Sound();
                    tempSound.add(popupSound.getMusicString() + sound);
                    popupSound = tempSound;

                    lastAddedSound += sound;
                    Sound test = new Sound();
                    test.add(activeInstrument + lastAddedSound);
                    player.play(test);

                    textField.setText(textField.getText() + sound);
                }
            });
        }

    }

    private void setDurationListeners(JButton[] buttonList, String[] currentSound) {
        for (int i = 0; i < buttonList.length; i++) {
            final String sound = currentSound[i];
            final JButton button = buttonList[i];
            final String label = durations[i];
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String currentString = popupSound.getMusicString();


                    /**
                     * The reason we create tempSound is because if we would just use the popupSound.add() method it
                     * automatically adds a space between the previously added sound and the new sound, and that prohibits
                     * the use of the maj, min, sharp, duration functionallity that JFugue has.
                     */

                    Sound tempSound = new Sound();

                    if (!(sound.equals(" R "))) {
                        /**
                         * Adds the ability to add a duration to a maj or min.
                         */
                        if (currentString.endsWith("min ") || currentString.endsWith("maj ")) {

                            /**
                             * The duration command is always added at the end of that sound so we wanted to be able
                             * to add it to the "maj " and "min " too, which meant that we needed to first erase the
                             * empty space at the end of those strings and then add the duration to that.
                             */

                            currentString = currentString.substring(0, currentString.length() - 1) + sound;

                            lastAddedSound = lastAddedSound.substring(0, lastAddedSound.length() - 1) + sound;

                            tempSound.add(currentString);
                            popupSound = tempSound;

                            textField.setText(textField.getText().substring(0, textField.getText().length() - 1) + " (" +
                                    label + ")");
                            return;
                        }
                        /**
                         * So that we don't add any unnessesary durations.
                         */
                        else if (currentString.endsWith(" ")) {
                            return;
                        } else {
                            /**
                             * Adds the duration to the lastAddedSound.
                             */
                            lastAddedSound += sound;

                        }
                    } else {
                        lastAddedSound = "";
                    }


                    tempSound.add(popupSound.getMusicString() + sound);
                    popupSound = tempSound;
                    textField.setText(textField.getText() + " (" + label + ")");

                }
            });
        }

    }

    void createWindow() {
        this.setTitle(instrumentID);
        this.setResizable(false);
        pack();
        this.setVisible(true);
    }

    /**
     * These grabbers are what communicates with the rest of the application when we want to adding out new sound.
     */
    void setRectangleGrabber(RectangleGrabber rectangleGrabber) {
        this.rectangleGrabber = rectangleGrabber;
    }

    void setSoundGrabber(SoundGrabber soundGrabber) {
        this.soundGrabber = soundGrabber;
    }

    /**
     * These set-methods is what changes the look and functionality of the popup dependent on what type of
     * instrument popup you want to create.
     */
    void setInstrumentColor(Color instrumentColor) {
        this.instrumentColor = instrumentColor;
    }

    void setInstrumentList(String[] instrumentList) {
        this.instrumentList = instrumentList;
    }

    void setInstrumentLabels(String[] instrumentLabels) {
        this.instrumentLabels = instrumentLabels;
    }

    void setInstrumentID(String instrumentID) {
        this.instrumentID = instrumentID;
    }

}
