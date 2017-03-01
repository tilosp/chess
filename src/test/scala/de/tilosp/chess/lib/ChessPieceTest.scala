package de.tilosp.chess.lib

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, DataInputStream, DataOutputStream}

import org.junit.Assert._
import org.junit.Test

class ChessPieceTest {

  @Test
  def testNotMoved() {
    var piece = new ChessPiece(ChessPieceType.PAWN, PlayerColor.BLACK)
    assertTrue(piece.notMoved)

    piece = piece.moved(5, false)
    assertFalse(piece.notMoved)
  }

  @Test
  def testGetMove() {
    assertSame(ChessPieceType.QUEEN.getMove(true), new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).getMove)
    assertSame(ChessPieceType.KING.getMove(false), new ChessPiece(ChessPieceType.KING, PlayerColor.BLACK).moved(10, false).getMove)
  }

  @Test
  def testGetCapture() {
    assertSame(ChessPieceType.BISHOP.capture, new ChessPiece(ChessPieceType.BISHOP, PlayerColor.WHITE).getCapture)
  }

  @Test
  def testCheckPromotion() {
    assertFalse(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).checkPromotion(0))
    assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).checkPromotion(5))
    assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).checkPromotion(7))
    assertTrue(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).checkPromotion(0))

    assertFalse(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.BLACK).checkPromotion(7))
    assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.BLACK).checkPromotion(5))
    assertTrue(new ChessPiece(ChessPieceType.PAWN, PlayerColor.BLACK).checkPromotion(7))
    assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.BLACK).checkPromotion(0))
  }

  @Test
  def testToChar() {
    assertEquals(ChessPieceType.QUEEN.symbolWhite, new ChessPiece(ChessPieceType.QUEEN, PlayerColor.WHITE).toChar)
    assertEquals(ChessPieceType.BISHOP.symbolBlack, new ChessPiece(ChessPieceType.BISHOP, PlayerColor.BLACK).toChar)
  }

  @Test
  def testCheckEnPassant() {
    assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).checkEnPassant(1))
    assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).moved(5, false).checkEnPassant(6))
    assertFalse(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).moved(4, true).checkEnPassant(6))
    assertTrue(new ChessPiece(ChessPieceType.PAWN, PlayerColor.WHITE).moved(4, true).checkEnPassant(5))
  }

  @Test def testPromotion() {
    var piece = new ChessPiece(ChessPieceType.PAWN, PlayerColor.BLACK)
    piece = piece.moved(5, true).promotion(ChessPieceType.QUEEN)
    assertEquals(new ChessPiece(ChessPieceType.QUEEN, PlayerColor.BLACK).moved(5, false), piece)
  }

  @Test
  def testNetwork() {
    val piece = new ChessPiece(ChessPieceType.ROOK, PlayerColor.BLACK).moved(21, true)
    val out = new ByteArrayOutputStream()
    piece.write(new DataOutputStream(out))
    assertEquals(piece, ChessPiece.read(new DataInputStream(new ByteArrayInputStream(out.toByteArray))))
  }
}
