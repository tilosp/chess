package de.tilosp.chess.lib;

public final class Move {

    private final int fromX;
    private final int fromY;
    private final int toX;
    private final int toY;
    public final ChessPiece chessPiece;
    private final int captureX;
    private final int captureY;
    private final ChessPiece captureChessPiece;

    Move(int fromX, int fromY, int toX, int toY, ChessPiece chessPiece, int captureX, int captureY, ChessPiece captureChessPiece) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.chessPiece = chessPiece;
        this.captureX = captureX;
        this.captureY = captureY;
        this.captureChessPiece = captureChessPiece;
    }

    public String getText() {
        String text = getChar(fromX) + getInt(fromY) + " \u2192 " + getChar(toX) + getInt(toY);
        if (captureChessPiece != null)
            text += "   \u2717 " + captureChessPiece.toChar() + getChar(captureX) + getInt(captureY);
        return text;
    }

    private static String getInt(int i) {
        return Integer.toString(8 - i);
    }

    private static String getChar(int i) {
        return Character.toString((char) (0x61 + i));
    }
}
