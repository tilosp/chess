package de.tilosp.chess.lib;

import static org.junit.Assert.*;
import org.junit.Test;

public class ChessPieceTypeTest {

    @Test
    public void testLength() {
        assertEquals(6, ChessPieceType.values().length);
    }

    @Test
    public void testName() {
        assertEquals("KING", ChessPieceType.KING.name());
        assertEquals("QUEEN", ChessPieceType.QUEEN.name());
        assertEquals("ROOK", ChessPieceType.ROOK.name());
        assertEquals("BISHOP", ChessPieceType.BISHOP.name());
        assertEquals("KNIGHT", ChessPieceType.KNIGHT.name());
        assertEquals("PAWN", ChessPieceType.PAWN.name());
    }

    @Test
    public void testMove() {
        for (ChessPieceType type : ChessPieceType.values())
            for (boolean notMoved : new boolean[] { false, true })
                for (int[] a : type.getMove(notMoved))
                    assertTrue(a.length == 2 || a.length == 3);
    }

    @Test
    public void testCapture() {
        for (ChessPieceType type : ChessPieceType.values())
            for (int[] a : type.capture)
                assertTrue(a.length == 2 || a.length == 3 || a.length == 4);
    }

    @Test
    public void testSquareValues() {
        for (ChessPieceType type : ChessPieceType.values()) {
            for (boolean endGame : new boolean[] { false, true }) {
                assertEquals(8, type.getSquareValues(endGame).length);

                for (int[] a : type.getSquareValues(endGame))
                    assertEquals(8, a.length);
            }
        }
    }
}
