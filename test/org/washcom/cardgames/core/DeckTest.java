package org.washcom.cardgames.core;

import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import static org.washcom.cardgames.core.Denomination.*;
import static org.washcom.cardgames.core.Suit.*;

/**
 *
 * @author Joe
 */
public class DeckTest {

    private Deck emptyDeck;
    private Deck singleCardDeck;
    private Deck twoCardDeck;

    @Before
    public void setUp() {
        emptyDeck = new Deck();
        singleCardDeck = new Deck(Collections.singleton(new Card(CLUBS, ACE)));
        twoCardDeck = new Deck(Arrays.asList(new Card(CLUBS, ACE), new Card(CLUBS, TWO)));
    }

    @Test
    public void testDrawZeroFromEmptyDeck() {
        assertTrue(emptyDeck.draw(0).isEmpty());
    }

    @Test
    public void testDrawZeroFromNonEmptyDeck() {
        assertTrue(singleCardDeck.draw(0).isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void testDrawLessThanZeroFromEmptyDeck() {
        emptyDeck.draw(-1);
    }

    @Test(expected = RuntimeException.class)
    public void testDrawLessThanZeroFromNonEmptyDeck() {
        emptyDeck.draw(-1);
    }

    @Test(expected = DeckExhaustedException.class)
    public void testDrawFromEmptyDeckThrowsExhaustedException() {
        emptyDeck.draw();
    }
    
    @Test
    public void testDrawFromSingleCardDeck() {
        assertEquals(new Card(CLUBS, ACE), singleCardDeck.draw());
    }
    
    @Test
    public void testDrawFromMultiCardDeck() {
        assertEquals(new Card(CLUBS, ACE), twoCardDeck.draw());
        assertEquals(new Card(CLUBS, TWO), twoCardDeck.draw());
    }
    
    @Test
    public void testBottmDrawFromMultiCardDeck() {
        assertEquals(new Card(CLUBS, TWO), twoCardDeck.drawFromBottom());
        assertEquals(new Card(CLUBS, ACE), twoCardDeck.drawFromBottom());
    }
    
    @Test
    public void testSize() {
        assertEquals(0, emptyDeck.size());
        assertEquals(1, singleCardDeck.size());
        assertEquals(2, twoCardDeck.size());
    }
}
