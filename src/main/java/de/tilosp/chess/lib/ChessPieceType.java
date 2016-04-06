package de.tilosp.chess.lib;

public enum ChessPieceType {

    KING(new int[][] {
            { 0, 1, },
            { 0, -1 },
            { 1, 0 },
            { -1, 0 },
            { 1, 1 },
            { 1, -1 },
            { -1, 1 },
            { -1, -1 } }),
    QUEEN(new int[][] {
            { 0, 1, 7 },
            { 0, -1, 7 },
            { 1, 0, 7 },
            { -1, 0, 7 },
            { 1, 1, 7 },
            { 1, -1, 7 },
            { -1, 1, 7 },
            { -1, -1, 7 } }),
    ROOK(new int[][] {
            { 0, 1, 7 },
            { 0, -1, 7 },
            { 1, 0, 7 },
            { -1, 0, 7 } }),
    BISHOP(new int[][] {
            { 1, 1, 7 },
            { 1, -1, 7 },
            { -1, 1, 7 },
            { -1, -1, 7 } }),
    KNIGHT(new int[][] {
            { 1, 2 },
            { -1, 2 },
            { 1, -2 },
            { -1, -2 },
            { 2, 1 },
            { 2, -1 },
            { -2, 1 },
            { -2, -1 } }),
    PAWN(new int[][] {
            { 0, 1 }
    }, new int[][] {
            { 0, 1, 2 }
    }, new int[][] {
            { 1, 1 },
            { -1, 1 },
            { 1, 1, 1, 0 },
            { -1, 1, -1, 0 }
    });

    public static final ChessPieceType[] POSITIONS_FIRST_ROW = { ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK };
    public static final ChessPieceType[] POSITIONS_PROMOTION = { QUEEN, KNIGHT, ROOK, BISHOP };
    public final int[][] capture;
    private final int[][] move;
    private int[][] moveFirstTurn;

    ChessPieceType(int[][] move) {
        this.move = move;
        this.capture = move;
    }

    ChessPieceType(int[][] move, int[][] moveFirstTurn, int[][] capture) {
        this.move = move;
        this.moveFirstTurn = moveFirstTurn;
        this.capture = capture;
    }

    public int[][] getMove(boolean notMoved) {
        return notMoved && moveFirstTurn != null ? moveFirstTurn : move;
    }
}
