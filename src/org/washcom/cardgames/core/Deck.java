package org.washcom.cardgames.core;

import java.util.*;

/**
 * Represents a deck of cards.
 * 
 * TODO: add a cut method?
 * 
 * @author Joe
 */
public class Deck {

    private final Deque<Card> internal = new ArrayDeque<>();
    
    private final Random random = new Random();

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
        List<Card> copy = new ArrayList<>();
        copy.addAll(internal);
        internal.clear();
        while (!copy.isEmpty()) {
            int idx = random.nextInt(copy.size());
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
        return draw(true);
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
        return draw(nbrOfCards, true);
    }

    /**
     * Remove the bottom card from the deck. 
     * 
     * @return
     * @throws DeckExhaustedException - if the deck is empty
     */
    public Card drawFromBottom() throws DeckExhaustedException {
        return draw(false);
    }

    /**
     * Draw the given number of cards from the bottom of the deck. The order is preserved in the returned list:
     * the first card is the bottom card of the deck and so on.
     * 
     * @param nbrOfCards - must be greater than 0
     * @return
     * @throws DeckExhaustedException - if there aren't enough cards in the deck which can be drawn; the
     *      thrown exception will contain the partial list of drawn cards.
     */
    public List<Card> drawFromBottom(int nbrOfCards) throws DeckExhaustedException {
        return draw(nbrOfCards, false);
    }

    /**
     * Remove a card from the deck. If {@code top} is true, it's the top card; otherwise, it's the bottom card.
     *
     * @param top - true if to be drawn from the top; false if to be drawn from the bottom 
     * @return
     * @throws DeckExhaustedException - if the deck is empty
     */
    private Card draw(boolean top) throws DeckExhaustedException {
        if (internal.isEmpty()) {
            throw new DeckExhaustedException();
        }
        return top ? internal.removeFirst() : internal.removeLast();
    }

    /**
     * Draw the given number of cards from the top of the deck (if {@code top} is true) or the bottom of the deck
     * (if {@code top} is false). The order is preserved in the returned list.
     * 
     * @param nbrOfCards - must be non-negative
     * @param top - true if to be drawn from the top; false if to be drawn from the bottom
     * @return
     * @throws DeckExhaustedException - if there aren't enough cards in the deck which can be drawn; the
     *      thrown exception will contain the partial list of drawn cards.
     */
    private List<Card> draw(int nbrOfCards, boolean top) throws DeckExhaustedException {
        if (nbrOfCards < 0) {
            throw new IllegalArgumentException("Integer arg must be 1 or more.");
        }
        if (nbrOfCards == 0) {
            return Collections.emptyList();
        }
        List<Card> hand = new ArrayList<>();
        try {
            for (int i = nbrOfCards; i > 0; --i) {
                hand.add(top ? internal.removeFirst() : internal.removeLast());
            }
        } catch (NoSuchElementException e) {
            throw new DeckExhaustedException(hand);
        }
        return hand;
    }

    /**
     * Draws all cards from the deck.
     * 
     * @return 
     */
    public List<Card> drawAll() {
        try {
            return draw(internal.size());
        } catch (DeckExhaustedException e) {
            throw new IllegalStateException("This should never happen.", e);
        }
    }
    
    /**
     * Draws a card at random.
     * 
     * @return
     * @throws DeckExhaustedException - if there are no cards in the deck to draw
     */
    public Card drawAtRandom() throws DeckExhaustedException {
        if (internal.isEmpty()) {
            throw new DeckExhaustedException();
        }
        Iterator<Card> cards = internal.iterator();
        Card current = cards.next();
        for (int i = 0; i < new Random().nextInt(size()); ++i) {
            current = cards.next();
        }
        cards.remove();
        return current;
    }

    /**
     * Returns the given card to the bottom of the deck.
     * 
     * @param card - cannot be null
     */
    public void putOnBottom(Card card) {
        put(card, false);
    }

    /**
     * Returns the given cards to the bottom of the deck in the order provided. The last card in the
     * list becomes the last card in the deck.
     * 
     * @param cards - cannot be null
     */
    public void putOnBottom(List<Card> cards) {
        put(cards, false);
    }

    /**
     * Puts the given card on the top of the deck.
     * 
     * @param card - cannot be null
     */
    public void put(Card card) {
        put(card, true);
    }

    /**
     * Puts the given cards on the top of the deck in the order provided. The last card in the
     * list becomes the top card of the deck.
     * 
     * @param cards - cannot be null
     */
    public void put(List<Card> cards) {
        put(cards, true);
    }

    /**
     * Returns the given card to the top of the deck if {@code top} is true; otherwise returns the card
     * to the bottom of the deck.
     * 
     * @param top
     * @param card - cannot be null
     */
    private void put(Card card, boolean top) {
        if (card == null) {
            throw new NullPointerException("Card cannot be null.");
        }
        if (top) {
            internal.addFirst(card);
        } else {
            internal.addLast(card);
        }
    }

    /**
     * Returns the given cards to the deck in the order provided. If {@code top} is true, they're returned to
     * the top and the last card in the argument list becomes the top card of the deck; otherwise, they're
     * returned to the bottom of the deck and the last card in the argument list becomes the bottom card.
     * 
     * @param top
     * @param cards - cannot be null
     */
    private void put(List<Card> cards, boolean top) {
        if (cards == null) {
            throw new NullPointerException("Cards collected cannot be null.");
        }
        if (top) {
            for (Card card : cards) {
                internal.addFirst(card);
            }
        } else {
            for (Card card : cards) {
                internal.addLast(card);
            }
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

    /**
     * Returns true if there is at lease one card left in the deck. 
     * 
     * This is always true {@code assert isEmpty() != hasCards()}
     * 
     * @return 
     */
    public boolean hasCards() {
        return !isEmpty();
    }
}
