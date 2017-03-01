package de.tilosp.chess.gui;

import javax.swing.*;

abstract class GUI extends JFrame {

    final JPanel panel;

    GUI() {
        // create panel
        panel = new JPanel();

        // initialise GUI
        initGUI();

        // add panel
        setContentPane(panel);
        pack();

        // initialise Listeners
        initListeners();
    }

    abstract void initGUI();

    abstract void initListeners();
}
