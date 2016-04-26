package de.tilosp.chess.lib;

import java.util.ArrayList;
import java.util.Random;

public class ChessComputer {

    private static Random random = new Random();

    public static Chessboard compute(Chessboard chessboard) {
        long time = System.currentTimeMillis();

        int bestValue = Integer.MIN_VALUE;
        ArrayList<Chessboard> bestMoves = new ArrayList<>();
        for (Chessboard c : getPossibleMoves(chessboard)) {
            int value = -negamax(c, chessboard.endGame ? 6 : 3, Integer.MIN_VALUE + 1, Integer.MAX_VALUE);
            if (value > bestValue) {
                bestValue = value;
                bestMoves = new ArrayList<>();
                bestMoves.add(c);
            } else if (value == bestValue) {
                bestMoves.add(c);
            }
        }

        System.out.println("compute took " + (System.currentTimeMillis() - time)+ " ms, " + bestMoves.size() + " move(s) with score " + bestValue);
        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    private static ArrayList<Chessboard> getPossibleMoves(Chessboard chessboard){
        ArrayList<Chessboard> possibleMoves = new ArrayList<>();

        for (int i1 = 0; i1 < 8; i1++) {
            for (int i2 = 0; i2 < 8; i2++) {
                ChessPiece chessPiece = chessboard.getChessPiece(i1, i2);
                if (chessPiece != null && chessPiece.playerColor == chessboard.playerColor) {
                    for (int[] m : chessboard.getPossibleMoves(i1, i2)) {
                        Chessboard move = chessboard.move(new int[] { i1, i2 }, m);
                        if (move.promotion)
                            for (ChessPieceType type : ChessPieceType.POSITIONS_PROMOTION)
                                possibleMoves.add(move.promotion(type));
                        else
                            possibleMoves.add(move);
                    }
                }
            }
        }

        return possibleMoves;
    }

    // negamax algorithm with alpha beta pruning https://en.wikipedia.org/wiki/Negamax
    private static int negamax(Chessboard chessboard, int depth, int alpha, int beta) {
        if (depth == 0 || chessboard.isDraw() || chessboard.isWin(PlayerColor.WHITE) || chessboard.isWin(PlayerColor.BLACK))
            return chessboard.playerColor.sign * computeValue(chessboard);

        int bestValue = Integer.MIN_VALUE;

        for (Chessboard c : getPossibleMoves(chessboard)) {
            int value = -negamax(c, depth - 1, -beta, -alpha);
            bestValue = Math.max(bestValue, value);

            alpha = Math.max(alpha, value);
            if (alpha >= beta)
                break;
        }

        return bestValue;
    }


    // https://chessprogramming.wikispaces.com/Simplified+evaluation+function
    public static int computeValue(Chessboard chessboard) {
        int value = 0;
        for (int i1 = 0; i1 < 8; i1++) {
            for (int i2 = 0; i2 < 8; i2++) {
                ChessPiece chessPiece = chessboard.getChessPiece(i1, i2);
                if (chessPiece != null) {
                    value += (chessPiece.chessPieceType.value
                            + chessPiece.chessPieceType.getSquareValues(chessboard.endGame)[i1][chessPiece.playerColor == PlayerColor.WHITE ? i2 : 7 - i2])
                            * chessPiece.playerColor.sign;
                }
            }
        }

        return value;
    }

    private static int[][] computeThreatenedMatrix(Chessboard chessboard) {
        int[][] threatenedMatrix = new int[8][8];
        for (int i1 = 0; i1 < 8; i1++) {
            for (int i2 = 0; i2 < 8; i2++) {
                ChessPiece chessPiece = chessboard.getChessPiece(i1, i2);
                if (chessPiece != null) {
                    for (int[] a : chessboard.getThreatenedFields(i1, i2)) {
                        if (chessPiece.playerColor == chessboard.playerColor)
                            threatenedMatrix[a[0]][a[1]] += chessPiece.chessPieceType.value;
                        else
                            threatenedMatrix[a[0]][a[1]] -= chessPiece.chessPieceType.value;
                    }
                }
            }
        }
        return threatenedMatrix;
    }
}