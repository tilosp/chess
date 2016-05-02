package de.tilosp.chess.lib;

public final class ChessPiece {

    public final ChessPieceType chessPieceType;
    public final PlayerColor playerColor;
    private final int movements;
    private final int movedInTurn;
    private final boolean enPassant;

    public ChessPiece(ChessPieceType chessPieceType, PlayerColor playerColor) {
        this(chessPieceType, playerColor, 0, 0, false);
    }

    private ChessPiece(ChessPieceType chessPieceType, PlayerColor playerColor, int movements, int movedInTurn, boolean enPassant) {
        this.chessPieceType = chessPieceType;
        this.playerColor = playerColor;
        this.movements = movements;
        this.movedInTurn = movedInTurn;
        this.enPassant = enPassant;
    }

    public int[][] getMove() {
        return chessPieceType.getMove(notMoved());
    }

    public int[][] getCapture() {
        return chessPieceType.capture;
    }

    public ChessPiece moved(int turn, boolean enPassant) {
        return new ChessPiece(chessPieceType, playerColor, movements + 1, turn, enPassant);
    }

    public ChessPiece promotion(ChessPieceType chessPieceType) {
        return new ChessPiece(chessPieceType, playerColor, movements, movedInTurn, false);
    }

    public boolean notMoved() {
        return movements == 0;
    }

    public boolean checkEnPassant(int turn) {
        return turn == movedInTurn + 1 && enPassant;
    }

    public boolean checkPromotion(int y) {
        return chessPieceType == ChessPieceType.PAWN && (playerColor == PlayerColor.WHITE ? 0 : 7) == y;
    }

    @Override
    public String toString() {
        return chessPieceType.toString() + playerColor.toString();
    }
}