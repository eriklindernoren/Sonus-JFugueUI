package projekt.sonus.main;

import projekt.sonus.mainwindow.MainFrame;

/**
 * The main method of the application. The menubar is set to the MacOS look.
 */


class MainClass {

    private MainClass() {
    }

    //MainClass function
    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        MainFrame mainFrame = new MainFrame();
        System.out.println(mainFrame.getTitle() + " is live.");
    }

}
