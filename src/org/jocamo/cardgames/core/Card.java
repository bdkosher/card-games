package org.jocamo.cardgames.core;

/**
 * A playing card.
 * 
 * @author Joe
 */
public class Card {
    
    private final Suit suit;
    
    private final Denomination denomination;

    public Card(Suit suit, Denomination denomination) {
        if (suit == null) {
            throw new NullPointerException("Suit arg cannot be null.");
        }
        if (denomination == null) {
            throw new NullPointerException("Denomination arg cannot be null.");
        }
        this.suit = suit;
        this.denomination = denomination;
    }

    public Suit getSuit() {
        return suit;
    }

    public Denomination getDenomination() {
        return denomination;
    }
    
    @Override
    public String toString() {
        return denomination + " of " + suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.suit != other.suit) {
            return false;
        }
        if (this.denomination != other.denomination) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.suit != null ? this.suit.hashCode() : 0);
        hash = 89 * hash + (this.denomination != null ? this.denomination.hashCode() : 0);
        return hash;
    }
    
}
