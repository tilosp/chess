package de.tilosp.chess.icon;

import de.tilosp.chess.lib.ChessPieceType;
import de.tilosp.chess.lib.PlayerColor;

import javax.swing.*;

public final class Icons {

    private static final ImageIcon[][] icons;

    static {
        icons = new ImageIcon[PlayerColor.values().length][ChessPieceType.values().length];
        for (PlayerColor playerColor : PlayerColor.values())
            for (ChessPieceType chessPieceType : ChessPieceType.values())
                icons[playerColor.ordinal()][chessPieceType.ordinal()] = new ImageIcon(Icons.class.getResource(playerColor.name().toLowerCase() + "_" + chessPieceType.name().toLowerCase() + ".png"));
    }

    public static ImageIcon getIcon(PlayerColor playerColor, ChessPieceType chessPieceType) {
        return icons[playerColor.ordinal()][chessPieceType.ordinal()];
    }
}
