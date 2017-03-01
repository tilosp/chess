package de.tilosp.chess.lib

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, DataInputStream, DataOutputStream}

import org.junit.Assert._
import org.junit.Test

class MoveTest {

  @Test
  def testGetText() {
    var text = new Move(1, 2, 3, 4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, null).getText
    assertTrue(text.contains("b6"))
    assertTrue(text.contains("d4"))
    assertFalse(text.contains("f2"))
    assertFalse(text.contains(Character.toString(ChessPieceType.ROOK.symbolBlack)))

    text = new Move(1, 2, 3, 4, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE), 5, 6, new ChessPiece(ChessPieceType.ROOK, PlayerColor.BLACK)).getText
    assertTrue(text.contains("b6"))
    assertTrue(text.contains("d4"))
    assertTrue(text.contains("f2"))
    assertTrue(text.contains(Character.toString(ChessPieceType.ROOK.symbolBlack)))
  }

  @Test
  def testNetwork() {
    var move = new Move(1, 2, 3, 4, new ChessPiece(ChessPieceType.ROOK, PlayerColor.WHITE), 5, 6, null)
    var out = new ByteArrayOutputStream()
    move.write(new DataOutputStream(out))
    assertEquals(move, Move.read(new DataInputStream(new ByteArrayInputStream(out.toByteArray))))

    move = new Move(1, 2, 3, 4, new ChessPiece(ChessPieceType.ROOK, PlayerColor.WHITE), 5, 6, new ChessPiece(ChessPieceType.BISHOP, PlayerColor.BLACK))
    out = new ByteArrayOutputStream
    move.write(new DataOutputStream(out))
    assertEquals(move, Move.read(new DataInputStream(new ByteArrayInputStream(out.toByteArray))))
  }
}
