package org.washcom.cardgames.core;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.washcom.util.LoopingIterator;

/**
 * A utility class with methods for dealing decks to players.
 * 
 * @author Joe
 */
public class DeckDealer {
    
    private static void validatePlayers(Collection<Player> players) {
        if (players == null) {
            throw new NullPointerException("Players cannot be null.");
        } else if (players.isEmpty()) {
            throw new IllegalArgumentException("Players must exceed 0.");
        }
    }
    
    /**
     * Deals the entire deck to all of the given players. Depending on the number of cards/players, some players may
     * have more cards than others.
     * 
     * @param deck
     * @param players 
     */
    public static void dealEntirely(Deck deck, Player... players) {
        dealEntirely(deck, (players == null || (players.length == 1 && players[0] == null)) ? null : Arrays.asList(players));
    }
    
    /**
     * Deals the entire deck to all of the given players. Depending on the number of cards/players, some players may
     * have more cards than others.
     * 
     * @param deck
     * @param players 
     */
    public static void dealEntirely(Deck deck, Collection<Player> players) {
        validatePlayers(players);
        Iterator<Player> playerIterator = new LoopingIterator<>(players);
        for (Card card : deck) {
            playerIterator.next().getHand().replace(card);
        }
    }
    
    /**
     * Deals the entire deck to all of the given players so that each player has an equal number of cards. Returns the
     * leftover cards as a new, potentially empty Deck (never null).
     * 
     * @param deck
     * @param players 
     * @throws IllegalArgumentException if there are more cards than players
     */
    public static Deck dealFairly(Deck deck, Player... players) {
        return dealFairly(deck, (players == null || (players.length == 1 && players[0] == null)) ? null : Arrays.asList(players));
    }
    
    /**
     * Deals the entire deck to all of the given players so that each player has an equal number of cards. Returns the
     * leftover cards as a new, potentially empty Deck (never null).
     * 
     * @param deck
     * @param players 
     * @throws IllegalArgumentException if there are more cards than players
     */
    public static Deck dealFairly(Deck deck, Collection<Player> players) {
        validatePlayers(players);
        final int nbrOfPlayers = players.size();
        if (deck.size() < nbrOfPlayers) {
            throw new IllegalArgumentException("Deck not large enough for number of players.");
        }
        Deck leftover = new Deck();
        leftover.collect(deck.drawFromBottom(deck.size() % nbrOfPlayers));
        dealEntirely(deck, players);
        return leftover;
    }
    
}
