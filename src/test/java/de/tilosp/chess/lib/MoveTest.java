package de.tilosp.chess.lib;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.*;

public class MoveTest {

    @Test
    public void testGetText() {
        String text = new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null).getText();
        assertTrue(text.contains("b6"));
        assertTrue(text.contains("d4"));;
        assertFalse(text.contains("f2"));
        assertFalse(text.contains(Character.toString(ChessPieceType.ROOK.symbolBlack)));

        text = new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, new ChessPiece(ChessPieceType.ROOK, PlayerColor.BLACK)).getText();
        assertTrue(text.contains("b6"));
        assertTrue(text.contains("d4"));;
        assertTrue(text.contains("f2"));
        assertTrue(text.contains(Character.toString(ChessPieceType.ROOK.symbolBlack)));
    }

    @Test
    public void testEquals() {
        Move move = new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null);
        assertEquals(move, move);

        assertEquals(new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null),
                new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null));

        assertNotEquals(new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE)),
                new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null));

        assertNotEquals(new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null),
                new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE)));

        assertEquals(new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, new ChessPiece(ChessPieceType.BISHOP, PlayerColor.BLACK)),
                new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, new ChessPiece(ChessPieceType.BISHOP, PlayerColor.BLACK)));

        assertNotEquals(new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, new ChessPiece(ChessPieceType.BISHOP, PlayerColor.BLACK)),
                new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, new ChessPiece(ChessPieceType.ROOK, PlayerColor.BLACK)));

        assertNotEquals(new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null),
                new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.ROOK, PlayerColor.WHITE), 5, 6, null));

        assertNotEquals(new Move(45, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null),
                new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null));
        assertNotEquals(new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null),
                new Move(1, 85, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null));
        assertNotEquals(new Move(1, 2, 41,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null),
                new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null));
        assertNotEquals(new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null),
                new Move(1, 2, 3,62, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null));
        assertNotEquals(new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 42, 6, null),
                new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null));
        assertNotEquals(new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null),
                new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 11, null));

        assertNotEquals(new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null), "");
    }

    @Test
    public void testNetwork() throws IOException {
        Move move = new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.ROOK, PlayerColor.WHITE), 5, 6, null);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        move.write(new DataOutputStream(out));
        assertEquals(move, Move.read(new DataInputStream(new ByteArrayInputStream(out.toByteArray()))));

        move = new Move(1, 2, 3,4, new ChessPiece(ChessPieceType.ROOK, PlayerColor.WHITE), 5, 6, new ChessPiece(ChessPieceType.BISHOP, PlayerColor.BLACK));
        out = new ByteArrayOutputStream();
        move.write(new DataOutputStream(out));
        assertEquals(move, Move.read(new DataInputStream(new ByteArrayInputStream(out.toByteArray()))));
    }
}
