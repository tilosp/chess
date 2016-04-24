package de.tilosp.chess.player;

import de.tilosp.chess.gui.ChessboardGUI;
import de.tilosp.chess.lib.Chessboard;
import de.tilosp.chess.lib.PlayerColor;

import javax.swing.*;

public abstract class Player {

    PlayerColor color;
    ChessboardGUI gui;

    public void init(PlayerColor color, ChessboardGUI gui) {
        this.color = color;
        this.gui = gui;
    }

    public abstract void sendUpdate(Chessboard chessboard);

    void update(Chessboard chessboard) {
        SwingUtilities.invokeLater(() -> gui.externalUpdate(chessboard));
    }
}
