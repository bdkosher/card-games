package org.washcom.cardgames.core;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A utility class with methods for building different kinds of decks.
 * 
 * @author Joe
 */
public final class DeckBuilder {

    /**
     * Do not instantiate.
     */
    private DeckBuilder() {
    }

    /**
     * Builds a standard deck of 52 cards--no Jokers.
     * 
     * @return 
     */
    public static Deck build52CardDeck() {
        Collection<Card> cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Denomination denom : Denomination.values()) {
                cards.add(new Card(suit, denom));
            }
        }
        assert cards.size() == 52;
        return new Deck(cards);
    }
}
