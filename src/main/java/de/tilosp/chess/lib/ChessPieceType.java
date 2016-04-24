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
            { -1, -1 }
    }, 20000, new int[][]{
            { -30, -30, -30, -30, -20, -10, 20, 20 },
            { -40, -40, -40, -40, -30, -20, 20, 30 },
            { -40, -40, -40, -40, -30, -20,  0, 10 },
            { -50, -50, -50, -50, -40, -20,  0,  0 },
            { -50, -50, -50, -50, -40, -20,  0,  0 },
            { -40, -40, -40, -40, -30, -20,  0, 10 },
            { -40, -40, -40, -40, -30, -20, 20, 30 },
            { -30, -30, -30, -30, -20, -10, 20, 20 }
    }, new int[][]{
            { -50, -30, -30, -30, -30, -30, -30, -50 },
            { -40, -20, -10, -10, -10, -10, -30, -30 },
            { -30, -10,  20,  30,  30,  20,   0, -30 },
            { -20,   0,  30,  40,  40,  30,   0, -30 },
            { -20,   0,  30,  40,  40,  30,   0, -30 },
            { -30, -10,  20,  30,  30,  20,   0, -30 },
            { -40, -20, -10, -10, -10, -10, -30, -30 },
            { -50, -30, -30, -30, -30, -30, -30, -50 }
    }),
    QUEEN(new int[][] {
            { 0, 1, 7 },
            { 0, -1, 7 },
            { 1, 0, 7 },
            { -1, 0, 7 },
            { 1, 1, 7 },
            { 1, -1, 7 },
            { -1, 1, 7 },
            { -1, -1, 7 }
    }, 900, new int[][]{
            { -20, -10, -10, -5, 0, -10, -10, -20 },
            { -10,   0,   0,  0, 0,   5,   0, -10 },
            { -10,   0,   5,  5, 5,   5,   5, -10 },
            {  -5,   0,   5,  5, 5,   5,   0,  -5 },
            {  -5,   0,   5,  5, 5,   5,   0,  -5 },
            { -10,   0,   5,  5, 5,   5,   0, -10 },
            { -10,   0,   0,  0, 0,   0,   0, -10 },
            { -20, -10, -10, -5, -5, -10, -10, -20 }
    }),
    ROOK(new int[][] {
            { 0, 1, 7 },
            { 0, -1, 7 },
            { 1, 0, 7 },
            { -1, 0, 7 }
    }, 500, new int[][]{
            { 0,  5, -5, -5, -5, -5, -5, 0 },
            { 0, 10,  0,  0,  0,  0,  0, 0 },
            { 0, 10,  0,  0,  0,  0,  0, 0 },
            { 0, 10,  0,  0,  0,  0,  0, 0 },
            { 0, 10,  0,  0,  0,  0,  0, 0 },
            { 0, 10,  0,  0,  0,  0,  0, 0 },
            { 0, 10,  0,  0,  0,  0,  0, 0 },
            { 0,  5, -5, -5, -5, -5, -5, 0 }
    }),
    BISHOP(new int[][] {
            { 1, 1, 7 },
            { 1, -1, 7 },
            { -1, 1, 7 },
            { -1, -1, 7 }
    }, 330, new int[][] {
            { -20, -10, -10, -10, -10, -10, -10, -20 },
            { -10,   0,   0,   5,   0,  10,   5, -10 },
            { -10,   0,   5,   5,  10,  10,   0, -10 },
            { -10,   0,  10,  10,  10,  10,   0, -10 },
            { -10,   0,  10,  10,  10,  10,   0, -10 },
            { -10,   0,   5,   5,  10,  10,   0, -10 },
            { -10,   0,   0,   5,   0,  10,   5, -10 },
            { -20, -10, -10, -10, -10, -10, -10, -20 }
    }),
    KNIGHT(new int[][] {
            { 1, 2 },
            { -1, 2 },
            { 1, -2 },
            { -1, -2 },
            { 2, 1 },
            { 2, -1 },
            { -2, 1 },
            { -2, -1 }
    }, 320, new int[][]{
            { -50, -40, -30, -30, -30, -30, -40, -50 },
            { -40, -20,   0,   5,   0,   5, -20, -40 },
            { -30,   0,  10,  15,  15,  10,   0, -30 },
            { -30,   0,  15,  20,  20,  15,   0, -30 },
            { -30,   0,  15,  20,  20,  15,   0, -30 },
            { -30,   0,  10,  15,  15,  10,   0, -30 },
            { -40, -20,   0,   5,   0,   5, -20, -40 },
            { -50, -40, -30, -30, -30, -30, -40, -50 }
    }),
    PAWN(new int[][] {
            { 0, 1 }
    }, new int[][] {
            { 0, 1, 2 }
    }, new int[][] {
            { 1, 1 },
            { -1, 1 },
            { 1, 1, 1, 0 },
            { -1, 1, -1, 0 }
    }, 100, new int[][] {
            { 0, 50, 10,  5,  0,   5,   5, 0 },
            { 0, 50, 10,  5,  0,  -5,  10, 0 },
            { 0, 50, 20, 10,  0, -10,  10, 0 },
            { 0, 50, 30, 25, 20,   0, -20, 0 },
            { 0, 50, 30, 25, 20,   0, -20, 0 },
            { 0, 50, 20, 10,  0, -10 , 10, 0 },
            { 0, 50, 10,  5,  0,  -5,  10, 0 },
            { 0, 50, 10,  5,  0,   5,   5, 0 },
    });

    public static final ChessPieceType[] POSITIONS_FIRST_ROW = { ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK };
    public static final ChessPieceType[] POSITIONS_PROMOTION = { QUEEN, KNIGHT, ROOK, BISHOP };

    public final int[][] capture;
    private final int[][] move;
    private final int[][] moveFirstTurn;
    public final int value; // relative value
    private final int[][] squareValues; // bonuses for pieces standing well and penalties for pieces standing badly
    private final int[][] squareValuesEndGame;

    ChessPieceType(int[][] move, int value, int[][] squareValues) {
        this(move, move, move, value, squareValues);
    }

    ChessPieceType(int[][] move, int value, int[][] squareValues, int[][] squareValuesEndGame) {
        this(move, move, move, value, squareValues, squareValuesEndGame);
    }

    ChessPieceType(int[][] move, int[][] moveFirstTurn, int[][] capture, int value, int[][] squareValues) {
        this(move, moveFirstTurn, capture, value, squareValues, squareValues);
    }

    ChessPieceType(int[][] move, int[][] moveFirstTurn, int[][] capture, int value, int[][] squareValues, int[][] squareValuesEndGame) {
        this.move = move;
        this.moveFirstTurn = moveFirstTurn;
        this.capture = capture;
        this.value = value;
        this.squareValues = squareValues;
        this.squareValuesEndGame = squareValuesEndGame;
    }

    public int[][] getMove(boolean notMoved) {
        return notMoved ? moveFirstTurn : move;
    }

    public int[][] getSquareValues(boolean endGame) {
        return endGame ? squareValuesEndGame : squareValues;
    }
}
