package org.washcom.cardgames.battleroyale;

import org.junit.Test;
import static org.junit.Assert.*;

import org.washcom.cardgames.core.Card;
import static org.washcom.cardgames.core.Suit.*;
import static org.washcom.cardgames.core.Denomination.*;

/**
 *
 * @author Joe
 */
public class PlayerTest {
    
    @Test
    public void testSwapHandsTwoEmpty() {
        Player one = new Player("one");
        Player two = new Player("two");
        one.swapHands(two);
        assertTrue(one.getHand().isEmpty());
        assertTrue(two.getHand().isEmpty());
    }
    
    @Test
    public void testSwapHandsTwoEmptySymmetric() {
        Player one = new Player("one");
        Player two = new Player("two");
        two.swapHands(one);
        assertTrue(one.getHand().isEmpty());
        assertTrue(two.getHand().isEmpty());
    }
    
    @Test
    public void testSwapHandsWithEmptyHandedPlayer() {
        Player one = new Player("one");
        one.getHand().put(new Card(QUEEN, HEARTS));
        Player two = new Player("two");
        one.swapHands(two);
        assertTrue(one.getHand().isEmpty());
        assertEquals(1, two.getHand().size());
        assertEquals(new Card(QUEEN, HEARTS), two.getHand().draw());
    }
    
    @Test
    public void testSwapHandsWithEmptyHandedPlayerSymmetric() {
        Player one = new Player("one");
        one.getHand().put(new Card(QUEEN, HEARTS));
        Player two = new Player("two");
        two.swapHands(one);
        assertTrue(one.getHand().isEmpty());
        assertEquals(1, two.getHand().size());
        assertEquals(new Card(QUEEN, HEARTS), two.getHand().draw());
    }
    
    
    
}
