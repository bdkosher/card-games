package org.jocamo.cardgames.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Represents a deck of cards.
 * 
 * TODO: add a cut method?
 * 
 * @author Joe
 */
public class Deck {

    private final Deque<Card> internal = new ArrayDeque<Card>();
    
    /**
     * Initializes the deck with no cards in it.
     */
    public Deck() {
    }

    /**
     * Initializes the deck with the given collection of cards. The Collection type is not used
     * by the deck, but the cards within the collection are.
     * 
     * The deck is not shuffled on initialization.
     * 
     * @param cards - cannot be null
     */
    public Deck(Collection<Card> cards) {
        if (cards == null) {
            throw new NullPointerException("Cards arg cannot be null.");
        }
        internal.addAll(cards);
    }

    /**
     * Shuffles the deck according to a uniform random distribution.
     */
    public void shuffle() {
        Random rand = new Random();
        List<Card> copy = new ArrayList<Card>();
        copy.addAll(internal);
        internal.clear();
        while (!copy.isEmpty()) {
            int idx = rand.nextInt(copy.size());
            internal.push(copy.remove(idx));
        }
    }
    
    /**
     * Remove the top card from the deck. 
     * 
     * @return
     * @throws DeckExhaustedException - if the deck is empty
     */
    public Card draw() throws DeckExhaustedException {
        if (internal.isEmpty()) {
            throw new DeckExhaustedException();
        }
        return internal.removeFirst();
    }

    /**
     * Draw the given number of cards from the deck. The order is preserved in the returned list:
     * the first card is the top card of the deck and so on.
     * 
     * @param nbrOfCards - must be greater than 0
     * @return
     * @throws DeckExhaustedException - if there aren't enough cards in the deck which can be drawn; the
     *      thrown exception will contain the partial list of drawn cards.
     */
    public List<Card> draw(int nbrOfCards) throws DeckExhaustedException {
        if (nbrOfCards < 1) {
            throw new IllegalArgumentException("Integer arg must be 1 or more.");
        }
        List<Card> hand = new ArrayList<Card>();
        try {
            for (int i = nbrOfCards; i > 0; --i) {
                hand.add(internal.removeFirst());
            }
        } catch (NoSuchElementException e) {
            throw new DeckExhaustedException(hand);
        }
        return hand;
    }
    
    /**
     * Returns the given card to the bottom of the deck.
     * 
     * @param card - cannot be null
     */
    public void collect(Card card) {
        if (card == null) {
            throw new NullPointerException("Card cannot be null.");
        }
        internal.addLast(card);
    }

    /**
     * Returns the given cards to the bottom of the deck in the order provided. The last card in the
     * list becomes the last card in the deck.
     * 
     * @param cards - cannot be null
     */
    public void collect(List<Card> cards) {
        if (cards == null) {
            throw new NullPointerException("Cards collected cannot be null.");
        }
        for (Card card : cards) {
            internal.addLast(card);
        }
    }
    
    /**
     * Returns the given card to the top of the deck.
     * 
     * @param card - cannot be null
     */
    public void replace(Card card) {
        if (card == null) {
            throw new NullPointerException("Card cannot be null.");
        }
        internal.addFirst(card);
    }

    /**
     * Returns the given cards to the top of the deck in the order provided. The last card in the
     * list becomes the top card of the deck.
     * 
     * @param cards - cannot be null
     */
    public void replace(List<Card> cards) {
        if (cards == null) {
            throw new NullPointerException("Cards collected cannot be null.");
        }
        for (Card card : cards) {
            internal.addFirst(card);
        }
    }

    /**
     * Returns the number of cards presently in the deck.
     * 
     * @return 
     */
    public int size() {
        return internal.size();
    }

    /**
     * Returns true if there are no cards left in the deck.
     * 
     * @return 
     */
    public boolean isEmpty() {
        return internal.isEmpty();
    }
}
