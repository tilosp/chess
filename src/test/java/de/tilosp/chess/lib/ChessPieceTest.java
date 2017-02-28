package de.tilosp.chess.lib;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.*;

public class ChessPieceTest {

    @Test
    public void testConstruction() {
        ChessPiece piece = new ChessPiece(ChessPieceType.PAWN, PlayerColor.BLACK);
        assertEquals(ChessPieceType.PAWN, piece.chessPieceType);
        assertEquals(PlayerColor.BLACK, piece.playerColor);
    }

    @Test
    public void testNotMoved() {
        ChessPiece piece = new ChessPiece(ChessPieceType.PAWN, PlayerColor.BLACK);
        assertTrue(piece.notMoved());

        piece = piece.moved(5, false);
        assertFalse(piece.notMoved());
    }

    @Test
    public void testGetMove() {
        assertSame(ChessPieceType.QUEEN.getMove(true), new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).getMove());
        assertSame(ChessPieceType.KING.getMove(false), new ChessPiece(ChessPieceType.KING, PlayerColor.BLACK).moved(10, false).getMove());
    }

    @Test
    public void testGetCapture() {
        assertSame(ChessPieceType.BISHOP.capture, new ChessPiece(ChessPieceType.BISHOP, PlayerColor.WHITE).getCapture());
    }

    @Test
    public void testEquals() {
        ChessPiece piece = new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE);
        assertEquals(piece, piece);

        assertEquals(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE),
                    new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE));

        assertNotEquals(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE),
                    new ChessPiece(ChessPieceType.KING, PlayerColor.WHITE));

        assertNotEquals(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE),
                    new ChessPiece(ChessPieceType.QUEEN, PlayerColor.BLACK));

        assertNotEquals(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE),
                new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).moved(0, false));

        assertEquals(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).moved(0, false),
                new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).moved(0, false));

        assertNotEquals(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).moved(0, false),
                new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).moved(1, false));

        assertNotEquals(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).moved(0, false),
                new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).moved(0, true));

        assertNotEquals(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), "");
    }

    @Test
    public void testCheckPromotion() {
        assertFalse(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).checkPromotion(0));
        assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).checkPromotion(5));
        assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).checkPromotion(7));
        assertTrue(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).checkPromotion(0));

        assertFalse(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.BLACK).checkPromotion(7));
        assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.BLACK).checkPromotion(5));
        assertTrue(new ChessPiece(ChessPieceType.PAWN, PlayerColor.BLACK).checkPromotion(7));
        assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.BLACK).checkPromotion(0));
    }

    @Test
    public void testToChar() {
        assertEquals(ChessPieceType.QUEEN.symbolWhite, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).toChar());
        assertEquals(ChessPieceType.BISHOP.symbolBlack, new ChessPiece(ChessPieceType.BISHOP, PlayerColor.BLACK).toChar());
    }

    @Test
    public void testToString() {
        assertEquals("QUEENWHITE", new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).toString());
        assertEquals("PAWNWHITE", new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).toString());
        assertEquals("ROOKBLACK", new ChessPiece(ChessPieceType.ROOK, PlayerColor.BLACK).toString());
        assertEquals("KNIGHTBLACK", new ChessPiece(ChessPieceType.KNIGHT, PlayerColor.BLACK).toString());
    }

    @Test
    public void testCheckEnPassant() {
        assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).checkEnPassant(1));
        assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).moved(5, false).checkEnPassant(6));
        assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).moved(4, true).checkEnPassant(6));
        assertTrue(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).moved(4, true).checkEnPassant(5));
    }

    @Test
    public void testPromotion() {
        ChessPiece piece = new ChessPiece(ChessPieceType.PAWN, PlayerColor.BLACK);
        piece = piece.moved(5, true).promotion(ChessPieceType.QUEEN);
        assertEquals(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.BLACK).moved(5, false), piece);
    }

    @Test
    public void testNetwork() throws IOException {
        ChessPiece piece = new ChessPiece(ChessPieceType.ROOK, PlayerColor.BLACK).moved(21, true);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        piece.write(new DataOutputStream(out));
        assertEquals(piece, ChessPiece.read(new DataInputStream(new ByteArrayInputStream(out.toByteArray()))));
    }
}
