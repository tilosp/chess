package de.tilosp.chess.lib;

import static org.junit.Assert.*;
import org.junit.Test;

public class PlayerColorTest {

    @Test
    public void testLength() {
        assertEquals(2, PlayerColor.values().length);
    }

    @Test
    public void testOtherColor() {
        assertEquals(PlayerColor.WHITE, PlayerColor.BLACK.otherColor());
        assertEquals(PlayerColor.BLACK, PlayerColor.WHITE.otherColor());
    }

    @Test
    public void testOrdinal() {
        assertEquals(0, PlayerColor.WHITE.ordinal());
        assertEquals(1, PlayerColor.BLACK.ordinal());
    }

    @Test
    public void testSign() {
        assertEquals(1, PlayerColor.WHITE.sign);
        assertEquals(-1, PlayerColor.BLACK.sign);
    }

    @Test
    public void testName() {
        assertEquals("WHITE", PlayerColor.WHITE.name());
        assertEquals("BLACK", PlayerColor.BLACK.name());
    }
}
