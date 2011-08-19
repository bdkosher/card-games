package org.jocamo.cardgames.core;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Joe
 */
public class DeckBuilder {

    private DeckBuilder() {
    }

    public static Deck build52CardDeck() {
        Collection<Card> cards = new ArrayList<Card>();
        for (Suit suit : Suit.values()) {
            for (Denomination denom : Denomination.values()) {
                cards.add(new Card(suit, denom));
            }
        }
        return new Deck(cards);
    }
}
