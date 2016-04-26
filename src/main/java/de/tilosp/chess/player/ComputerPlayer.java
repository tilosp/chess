package de.tilosp.chess.player;

import de.tilosp.chess.lib.ChessEngine;
import de.tilosp.chess.lib.Chessboard;
import de.tilosp.chess.lib.PlayerColor;

public class ComputerPlayer extends Player {

    @Override
    public void sendUpdate(Chessboard chessboard) {
        if (chessboard.playerColor == color && !chessboard.isDraw() && !chessboard.isWin(PlayerColor.WHITE) && !chessboard.isWin(PlayerColor.BLACK)) {
            new Thread(() -> {
                update(ChessEngine.compute(chessboard));
            }).start();
        }
    }
}
