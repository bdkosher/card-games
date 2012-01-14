package org.jocamo.cardgames.battleroyale.rules;

import org.jocamo.cardgames.battleroyale.BattleRoyaleGame;
import org.jocamo.cardgames.battleroyale.utils.DealUtils;
import org.jocamo.cardgames.core.Deck;

/**
 * Deals out all cards to all players. This may result in some players having a larger hand than others.
 * 
 * @author Joe
 */
public class DealAllCardsRule {
    
    private final Deck deck;

    /**
     * 
     * @param deck - holds all the cards to be dealt to the game's players; cannot be null
     */
    public DealAllCardsRule(Deck deck) {
        if (deck == null) {
            throw new NullPointerException("Deck arg cannot be null.");
        }
        this.deck = deck;
    }
    
    /**
     * Deals out all cards to all of the players.
     * 
     * @param game 
     */
    public void onGameStart(BattleRoyaleGame game) {
        DealUtils.dealAll(deck, game.getPlayers());
    }
}
