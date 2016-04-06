package de.tilosp.chess.gui;

import de.tilosp.chess.player.LocalPlayer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemListener;

public class NewGameGUI extends GUI {

    private JButton startButton;
    private JRadioButton onePlayerRadioButton;
    private JRadioButton twoPlayerRadioButton;
    private JTextField hostTextField;
    private JTextField portTextField;
    private JComboBox<String> twoPlayerModeComboBox;
    private JComboBox<String> colorComboBox;
    private JLabel colorLabel;
    private JLabel hostLabel;
    private JLabel portLabel;

    @Override
    void initGUI() {
        setTitle("New Game");
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        GridBagConstraints c = new GridBagConstraints();

        ButtonGroup buttonGroup = new ButtonGroup();

        c.insets = new Insets(2, 2, 2, 2);
        c.fill = GridBagConstraints.BOTH;

        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 2;
        panel.add(onePlayerRadioButton = new JRadioButton("1 Player"), c);
        buttonGroup.add(onePlayerRadioButton);
        onePlayerRadioButton.setSelected(true);
        c.gridx = 2;
        c.gridwidth = 1;
        c.gridheight = 3;
        panel.add(Box.createHorizontalStrut(10), c);
        c.gridheight = 1;
        c.gridx = 3;
        panel.add(colorLabel = new JLabel("Color"), c);
        c.gridx = 4;
        panel.add(colorComboBox = new JComboBox<>(new String[] { "white", "black" }), c);

        c.gridy = 1;
        c.gridx = 0;
        c.gridwidth = 2;
        panel.add(twoPlayerRadioButton = new JRadioButton("2 Player"), c);
        buttonGroup.add(twoPlayerRadioButton);
        c.gridx = 3;
        c.gridwidth = 1;
        panel.add(hostLabel = new JLabel("Host"), c);
        c.gridx = 4;
        panel.add(hostTextField = new JTextField("localhost"), c);
        hostTextField.setPreferredSize(new Dimension(150, -1));

        c.gridy = 2;
        c.gridx = 0;
        panel.add(Box.createHorizontalStrut(18), c);
        c.gridx = 1;
        panel.add(twoPlayerModeComboBox = new JComboBox<>(new String[] { "local", "server", "client" }), c);
        c.gridx = 3;
        panel.add(portLabel = new JLabel("Port"), c);
        c.gridx = 4;
        panel.add(portTextField = new JTextField("49152"), c);

        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 5;
        panel.add(startButton = new JButton("Start"), c);
        getRootPane().setDefaultButton(startButton);

        updateEnabledStatus();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    @Override
    void initListeners() {
        // listener to update enabled status
        ItemListener listener = e -> updateEnabledStatus();
        onePlayerRadioButton.addItemListener(listener);
        twoPlayerRadioButton.addItemListener(listener);
        twoPlayerModeComboBox.addItemListener(listener);

        // listener for start button
        startButton.addActionListener(e -> onStartButtonPressed());

        // listener to change the color of the port text field
        portTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            void update() {
                try {
                    int port = Integer.parseInt(portTextField.getText());
                    portTextField.setForeground(port < 49152 || port > 65535 ? Color.ORANGE : Color.BLACK);
                } catch (NumberFormatException e) {
                    portTextField.setForeground(Color.RED);
                }
            }
        });
    }

    private void onStartButtonPressed() {
        if (onePlayerRadioButton.isSelected()) {
            // TODO 1 Player
        } else {
            if (twoPlayerModeComboBox.getSelectedIndex() == 0) {
                // 2 Players local
                new ChessboardGUI(new LocalPlayer(), new LocalPlayer()).setVisible(true);
            } else if(twoPlayerModeComboBox.getSelectedIndex() == 1) {
                // TODO 2 Players host
            } else {
                // TODO 2 Players client
            }
        }
        dispose();
    }

    private void updateEnabledStatus() {
        twoPlayerModeComboBox.setEnabled(twoPlayerRadioButton.isSelected());

        boolean enabled = onePlayerRadioButton.isSelected() || twoPlayerRadioButton.isSelected() && twoPlayerModeComboBox.getSelectedIndex() == 1;
        colorLabel.setEnabled(enabled);
        colorComboBox.setEnabled(enabled);

        enabled = twoPlayerRadioButton.isSelected() && twoPlayerModeComboBox.getSelectedIndex() == 2;
        hostLabel.setEnabled(enabled);
        hostTextField.setEnabled(enabled);
        portLabel.setEnabled(enabled);
        portTextField.setEnabled(enabled);

    }
}