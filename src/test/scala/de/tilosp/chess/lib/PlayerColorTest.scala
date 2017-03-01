package de.tilosp.chess.lib

import org.junit.Assert._
import org.junit.Test

class PlayerColorTest {

  @Test
  def testOtherColor() = {
    assertEquals(PlayerColor.WHITE, PlayerColor.BLACK.otherColor)
    assertEquals(PlayerColor.BLACK, PlayerColor.WHITE.otherColor)
  }

  @Test
  def testID() = {
    assertEquals(0, PlayerColor.WHITE.ordinal)
    assertEquals(1, PlayerColor.BLACK.ordinal)
  }

  @Test
  def testSign() = {
    assertEquals(1, PlayerColor.WHITE.sign)
    assertEquals(-1, PlayerColor.BLACK.sign)
  }

  @Test
  def testName() = {
    assertEquals("WHITE", PlayerColor.WHITE.name)
    assertEquals("BLACK", PlayerColor.BLACK.name)
  }
}
