package projekt.sonus.mainwindow;

import projekt.sonus.grid.GridGrabber;
import projekt.sonus.sound.Sound;
import projekt.sonus.sound.SoundGrabber;
import projekt.sonus.sound.SoundListener;
import projekt.sonus.sound.SoundObserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.HORIZONTAL;

/**
 * This panel is created in the MainFrame class and is located in the lower part of the application. It houses
 * the tempo slider and the soundControl button. It implements the SoundObserver so that it gets to know
 * when the main sound is edited in the popup windows and when the user loads a saved sound file in the
 * directory panel. It uses the Singleton design pattern to ensure that only one instance of this panel
 * is created.
 */

public final class PlayPanel extends JPanel implements SoundObserver {
    //SINGLETON
    private static final PlayPanel INSTANCE = new PlayPanel();
    //SLIDER
    private JSlider tempoSlider;
    private static final int SLIDER_X = 250;
    private static final int SLIDER_Y = 30;
    private static final int SLIDER_TICK_SPACE = 20;
    private static final int TEMPO_LOW = 40;
    private static final int TEMPO_MAX = 220;
    private static final int TEMPO_INIT = 180;
    //BUTTONS
    private JButton prev;
    private JButton next;
    //SOUND CONTROL STATE
    private String soundControlState = "play";
    //LEFT PANEL
    private JPanel leftPanel;
    private GridBagLayout leftLayout;
    private GridBagConstraints leftC;
    private final static int LEFT_C_PAD_X = 40;
    private final static int LEFT_C_PAD_Y = 20;
    //MID PANEL
    private JPanel midPanel;
    private final static int MID_C_PAD_X = 20;
    private final static int MID_C_PAD_Y = 20;
    //LAYOUT
    private GridBagLayout mainLayout;
    private GridBagConstraints playC;
    private GridBagLayout midLayout;
    private GridBagConstraints midLayoutConstraints;
    //COLORS
    private static final Color BUTTON_BACKGROUND = Color.WHITE;
    private static final Color BACKGROUND_COLOR = new Color(210, 210, 210);
    private static final Color INACTIVE_BUTTON_COLOR = Color.BLACK;
    //ICONS
    private String fs = File.separator;

    private String soundControlActiveIcon = ".." + fs + "icons" + fs + "play_focus.png";
    private String soundControlInactiveIcon = ".." + fs + "icons" + fs + "play.png";
    private String prevActiveIcon = ".." + fs + "icons" + fs + "prev_focus.png";
    private String prevInactiveIcon = ".." + fs + "icons" + fs + "prev.png";
    private String nextActiveIcon = ".." + fs + "icons" + fs + "next_focus.png";
    private String nextInactiveIcon = ".." + fs + "icons" + fs + "next.png";
    private String clearActiveIcon = ".." + fs + "icons" + fs + "clear_focus.png";
    private String clearInactiveIcon = ".." + fs + "icons" + fs + "clear.png";
    //SOUND
    private PlayerThread pThread = null;
    private Thread threadPlyr = null;
    //CONSTANTS
    private static final int CLEAR_BUTTON_X = 20;
    private static final int CLEAR_BUTTON_Y = 0;
    private static final int PLAY_BUTTON_X = 75;
    private static final int PLAY_BUTTON_Y = 5;
    private static final int STATE_BUTTONS_X = 30;
    private static final int STATE_BUTTONS_Y = 10;
    //SOUND
    private JButton soundControl;
    private Sound sound;
    private SoundGrabber soundGrabber = null;
    //GRID GRABBER
    private GridGrabber gridGrabber = null;
    //MAINFRAME
    private MainFrame mainFrame = null;


    public static PlayPanel getInstance(MainFrame main) {
        INSTANCE.mainFrame = main;
        return INSTANCE;
    }

    private PlayPanel() {
        sound = new Sound();
        setMainLayout();
        makePlayPanel();

    }

    private void setMainLayout() {

        mainLayout = new GridBagLayout();
        this.setLayout(mainLayout);

    }

    /**
     * The leftPanel houses the clear button.
     */

    private void makeLeftPanel() {

        leftPanel = new JPanel();
        leftPanel.setBackground(BACKGROUND_COLOR);
        setLayout(mainLayout);
        playC.weightx = 0;
        playC.ipady = LEFT_C_PAD_Y;
        playC.ipadx = LEFT_C_PAD_X;
        playC.gridx = 0;
        playC.gridy = 0;

        leftLayout = new GridBagLayout();
        leftC = new GridBagConstraints();
        leftPanel.setLayout(leftLayout);

        makeLeftButtons();

    }

    /**
     * The midPanel houses the soundControl and the back and forth buttons.
     */

    private void makeMidPanel() {
        midPanel = new JPanel();
        midPanel.setBackground(BACKGROUND_COLOR);
        setLayout(mainLayout);
        playC.weightx = 0;
        playC.ipady = MID_C_PAD_Y;
        playC.ipadx = MID_C_PAD_X;
        playC.gridx = 2;
        playC.gridy = 0;

        midLayout = new GridBagLayout();
        midLayoutConstraints = new GridBagConstraints();
        midPanel.setLayout(midLayout);

        makeStateButtons();
        makeSoundControlButton();

        this.add(midPanel, playC);
    }

    /**
     * Makes the back and forth buttons in the PlayPanel. These can be activated when user wants to regret a added
     * sound.
     */

    private void makeStateButtons() {
        prev = new JButton();
        prev.setBackground(BUTTON_BACKGROUND);
        prev.setForeground(INACTIVE_BUTTON_COLOR);
        setLayout(midLayout);
        midLayoutConstraints.gridx = 0;
        midLayoutConstraints.gridy = 0;
        midLayoutConstraints.ipadx = STATE_BUTTONS_X;
        midLayoutConstraints.ipady = STATE_BUTTONS_Y;
        midPanel.add(prev, midLayoutConstraints);

        next = new JButton();
        next.setBackground(BUTTON_BACKGROUND);
        next.setForeground(INACTIVE_BUTTON_COLOR);
        setLayout(midLayout);
        midLayoutConstraints.gridx = 2;
        midLayoutConstraints.gridy = 0;
        midLayoutConstraints.ipadx = STATE_BUTTONS_X;
        midLayoutConstraints.ipady = STATE_BUTTONS_Y;
        midPanel.add(next, midLayoutConstraints);

        try {
            Image img = ImageIO.read(getClass().getResource(prevInactiveIcon));
            prev.setIcon(new ImageIcon(img));
        } catch (IOException ignored) {
            System.out.println("Icon error");
        }
        try {
            Image img = ImageIO.read(getClass().getResource(nextInactiveIcon));
            next.setIcon(new ImageIcon(img));
        } catch (IOException ignored) {
            System.out.println("Icon error");
        }

        prev.addMouseListener(new MouseListener() {
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
                    Image img = ImageIO.read(getClass().getResource(prevActiveIcon));
                    prev.setIcon(new ImageIcon(img));
                } catch (IOException ignored) {
                    System.out.println("Icon error");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    Image img = ImageIO.read(getClass().getResource(prevInactiveIcon));
                    prev.setIcon(new ImageIcon(img));
                } catch (IOException ignored) {
                    System.out.println("Icon error");
                }
            }
        });
        next.addMouseListener(new MouseListener() {
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
                    Image img = ImageIO.read(getClass().getResource(nextActiveIcon));
                    next.setIcon(new ImageIcon(img));
                } catch (IOException ignored) {
                    System.out.println("Icon error");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    Image img = ImageIO.read(getClass().getResource(nextInactiveIcon));
                    next.setIcon(new ImageIcon(img));
                } catch (IOException ignored) {
                    System.out.println("Icon error");
                }
            }
        });

        /**
         * When the prev buttons is activated it loads the saved previous grid and saves the actual grid
         * as the grid that is loaded when the next button is activated.
         */
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To make sure the user doesn't change the window while the application is playing
                if (!soundControlState.equals("stop")) {
                    gridGrabber.back();
                    soundGrabber.backSound();
                    System.out.println(gridGrabber.getChannelCount1());
                    System.out.println(gridGrabber.getChannelCount3());
                }

            }
        });

        /**
         * When the next button is activated it loads the previously grid that was previously saved when the
         * prev button was last activated.
         */
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To make sure the user doesn't change the window while the application is playing
                if (!soundControlState.equals("stop")) {
                    gridGrabber.next();
                    soundGrabber.forwardSound();
                }
            }
        });
    }

    private void makeSoundControlButton() {

        soundControl = new JButton();
        soundControl.setBackground(BUTTON_BACKGROUND);
        soundControl.setForeground(INACTIVE_BUTTON_COLOR);
        setLayout(midLayout);
        midLayoutConstraints.gridx = 1;
        midLayoutConstraints.gridy = 0;
        midLayoutConstraints.ipadx = PLAY_BUTTON_X;
        midLayoutConstraints.ipady = PLAY_BUTTON_Y;
        midPanel.add(soundControl, midLayoutConstraints);

        try {
            Image img = ImageIO.read(getClass().getResource(soundControlInactiveIcon));
            soundControl.setIcon(new ImageIcon(img));
        } catch (IOException ignored) {
            System.out.println("Icon error");
        }

        soundControl.addMouseListener(new MouseListener() {
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
                    Image img = ImageIO.read(getClass().getResource(soundControlActiveIcon));
                    soundControl.setIcon(new ImageIcon(img));
                } catch (IOException ignored) {
                    System.out.println("Icon error");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    Image img = ImageIO.read(getClass().getResource(soundControlInactiveIcon));
                    soundControl.setIcon(new ImageIcon(img));
                } catch (IOException ignored) {
                    System.out.println("Icon error");
                }
            }
        });

        /**
         * When we played the sound without using threads in the beginning of the project the GUI froze, so the use
         * of a different thread was necessary so that we had the ability to stop the music, navigate the window
         * and close the window at the same time that the sound was playing.
         */
        soundControl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(sound.getMusicString());
                if (!soundGrabber.getSoundWithTempo().getMusicString().isEmpty()) {
                    if (soundControlState.equals("play")) {
                        soundControlActiveIcon = ".." + fs + "icons" + fs + "stop_focus.png";
                        soundControlInactiveIcon = ".." + fs + "icons" + fs + "stop.png";
                        try {
                            Image img = ImageIO.read(getClass().getResource(soundControlActiveIcon));
                            soundControl.setIcon(new ImageIcon(img));
                        } catch (IOException ignored) {
                            System.out.println("Icon error");
                        }
                        tempoSlider.setEnabled(false);
                        pThread = new PlayerThread(INSTANCE);
                        pThread.setSound(soundGrabber.getSoundWithTempo());
                        threadPlyr = new Thread(pThread);
                        threadPlyr.start();
                        soundControlState = "stop";
                    } else {
                        pThread.stopPlayer();
                        threadPlyr.interrupt();
                        threadPlyr = null;
                        pThread = null;
                        soundControlActiveIcon = ".." + fs + "icons" + fs + "play_focus.png";
                        soundControlInactiveIcon = ".." + fs + "icons" + fs + "play.png";
                        try {
                            Image img = ImageIO.read(getClass().getResource(soundControlActiveIcon));
                            soundControl.setIcon(new ImageIcon(img));
                        } catch (IOException ignored) {
                            System.out.println("Icon error");
                        }
                        soundControlState = "play";
                        tempoSlider.setEnabled(true);
                    }
                }

            }
        });
    }

    private void makeSliderPanel() {

        JPanel sliderPanel = new JPanel();
        sliderPanel.setBackground(BACKGROUND_COLOR);
        setLayout(mainLayout);
        playC.gridx = 4;
        playC.gridy = 0;

        GridBagLayout sliderLayout = new GridBagLayout();
        GridBagConstraints sliderC = new GridBagConstraints();
        this.setLayout(sliderLayout);

        //Tempo Slider
        JLabel sliderLabel = new JLabel("Tempo", CENTER);
        sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sliderPanel.setLayout(sliderLayout);
        sliderC.gridx = 0;
        sliderC.gridy = 0;
        sliderPanel.add(sliderLabel, sliderC);

        tempoSlider = new JSlider(HORIZONTAL, TEMPO_LOW, TEMPO_MAX, TEMPO_INIT);
        tempoSlider.setPreferredSize(new Dimension(SLIDER_X, SLIDER_Y));
        tempoSlider.setMinorTickSpacing(SLIDER_TICK_SPACE);
        tempoSlider.setSnapToTicks(true);
        tempoSlider.setPaintTicks(true);
        tempoSlider.setPaintTrack(true);
        sliderPanel.setLayout(sliderLayout);
        sliderC.gridx = 0;
        sliderC.gridy = 3;
        sliderPanel.add(tempoSlider, sliderC);

        tempoSlider.addMouseListener(new MouseListener() {
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
                String tempo = "T" + tempoSlider.getValue();
                Sound tempSound = new Sound();
                tempSound.add(tempo + sound.getMusicString());
                soundGrabber.setSoundWithTempo(tempSound);

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        this.add(sliderPanel, playC);

    }

    private void makePlayPanel() {

        playC = new GridBagConstraints();

        makeLeftPanel();
        this.add(leftPanel, playC);

        //To get the right separation between components. Used between clear and soundControl
        JLabel separator = new JLabel("                                                                 ");
        setLayout(mainLayout);
        playC.gridx = 1;
        playC.gridy = 0;
        this.add(separator, playC);

        makeMidPanel();

        /**
         * This empty JLabel is used so that the components gets the right separation. Used between soundControl and
         * tempo.
         */
        JLabel separator1 = new JLabel("                    ");
        setLayout(mainLayout);
        playC.gridx = 3;
        playC.gridy = 0;
        this.add(separator1, playC);

        makeSliderPanel();


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

    public void makeLeftButtons() {

        final JButton clear = new JButton();
        clear.setBackground(BUTTON_BACKGROUND);
        clear.setForeground(INACTIVE_BUTTON_COLOR);
        setLayout(leftLayout);
        leftC.gridx = 0;
        leftC.gridy = 0;
        leftC.ipadx = CLEAR_BUTTON_X;
        leftC.ipady = CLEAR_BUTTON_Y;
        leftPanel.add(clear, leftC);

        try {
            Image img = ImageIO.read(getClass().getResource(clearInactiveIcon));
            clear.setIcon(new ImageIcon(img));
        } catch (IOException ignored) {
            System.out.println("Icon error");
        }

        clear.addMouseListener(new MouseListener() {
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
                    Image img = ImageIO.read(getClass().getResource(clearActiveIcon));
                    clear.setIcon(new ImageIcon(img));
                } catch (IOException ignored) {
                    System.out.println("Icon error");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                try {
                    Image img = ImageIO.read(getClass().getResource(clearInactiveIcon));
                    clear.setIcon(new ImageIcon(img));
                } catch (IOException ignored) {
                    System.out.println("Icon error");
                }
            }
        });


        /**
         * The clear button resets the grid and the sound and disables the ability to back track.
         */

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //To make sure the user doesn't clear the window while the application is playing
                if (!soundControlState.equals("stop")) {
                    soundGrabber.resetSound();
                    soundGrabber.resetSoundWithTempo();
                    gridGrabber.resetGrid();
                    soundGrabber.setBackTrackable(false);
                    gridGrabber.setBackTrackable(false);
                }
            }
        });


    }

    /**
     * When the PlayPanel gets notified that the PlayerThread has stopped playing it changes the icon of the
     * soundControl and changes the state variable for the button to "play". The tempo slider is also activated.
     */
    public void finishedPlaying() {
        soundControlActiveIcon = ".." + fs + "icons" + fs + "play_focus.png";
        soundControlInactiveIcon = ".." + fs + "icons" + fs + "play.png";
        try {
            Image img = ImageIO.read(getClass().getResource(soundControlInactiveIcon));
            soundControl.setIcon(new ImageIcon(img));
        } catch (IOException ignored) {
            System.out.println("Icon error");
        }
        soundControlState = "play";
        tempoSlider.setEnabled(true);
    }

    /**
     * Gets called when the sound is updated. It sets the tempo slider to the initial value
     * and updates the sound that has the tempo taken into consideration.
     */
    @Override
    public void update(Sound sound) {
        this.sound = sound;
        tempoSlider.setValue(TEMPO_INIT);
        String tempo = "T" + tempoSlider.getValue();
        Sound tempSound = new Sound();
        tempSound.add(tempo + sound.getMusicString());
        soundGrabber.setSoundWithTempo(tempSound);
    }

    /**
     * Registers the PlayPanel as a SoundObserver.
     */

    public void registerSoundObserver(SoundListener listener) {
        listener.register(INSTANCE);
    }

    public void setSoundGrabber(SoundGrabber soundGrabber) {
        this.soundGrabber = soundGrabber;
    }

    public void setGridGrabber(GridGrabber gridGrabber) {
        this.gridGrabber = gridGrabber;
    }
}
