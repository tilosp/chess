package de.tilosp.chess.lib;

// the two player colors
public enum PlayerColor {
    WHITE,
    BLACK;

    public PlayerColor otherColor() {
        return values()[ordinal() ^ 1];
    }
}
