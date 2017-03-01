package de.tilosp.chess.lib

import org.junit.Assert._
import org.junit.Test

class ChessPieceTypeTest {

  @Test
  def testName() {
    assertEquals("KING", ChessPieceType.KING.name)
    assertEquals("QUEEN", ChessPieceType.QUEEN.name)
    assertEquals("ROOK", ChessPieceType.ROOK.name)
    assertEquals("BISHOP", ChessPieceType.BISHOP.name)
    assertEquals("KNIGHT", ChessPieceType.KNIGHT.name)
    assertEquals("PAWN", ChessPieceType.PAWN.name)
  }

  @Test
  def testMove() {
    for (chessPieceType <- ChessPieceType.values)
      for (notMoved <- Array(false, true))
        for (a <- chessPieceType.getMove(notMoved))
          assertTrue(a.length == 2 || a.length == 3)
  }

  @Test
  def testCapture() {
    for (chessPieceType <- ChessPieceType.values)
      for (a <- chessPieceType.capture)
        assertTrue(a.length == 2 || a.length == 3 || a.length == 4)
  }

  @Test
  def testSquareValues() {
    for (chessPieceType <- ChessPieceType.values) {
      for (endGame <- Array[Boolean](false, true)) {
        assertEquals(8, chessPieceType.getSquareValues(endGame).length)

        for (a <- chessPieceType.getSquareValues(endGame))
          assertEquals(8, a.length)
      }
    }
  }
}
