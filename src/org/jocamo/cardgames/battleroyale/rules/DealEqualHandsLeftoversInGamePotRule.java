package org.jocamo.cardgames.battleroyale.rules;

import org.jocamo.cardgames.battleroyale.BattleRoyaleGame;
import org.jocamo.cardgames.battleroyale.utils.DealUtils;
import org.jocamo.cardgames.core.Deck;

/**
 * Deals all players an equal number of cards and places any leftover cards into the game pot.
 * 
 * @author Joe
 */
public class DealEqualHandsLeftoversInGamePotRule {
    
    private final Deck deck;

    /**
     * 
     * @param deck - holds all the cards to be dealt to the game's players; cannot be null
     */
    public DealEqualHandsLeftoversInGamePotRule(Deck deck) {
        if (deck == null) {
            throw new NullPointerException("Deck arg cannot be null.");
        }
        this.deck = deck;
    }
    
    /**
     * Because this rule requires players get equal number of cards, this method throws an exception
     * when there are less cards than players.
     * 
     * @param game 
     */
    public void onGameStart(BattleRoyaleGame game) {
        final int nbrOfPlayers = game.getPlayers().size();
        if (deck.size() < nbrOfPlayers) {
            throw new IllegalArgumentException("Deck not large enough for number of players.");
        }
        game.getGamePot().collect(deck.draw(deck.size() % nbrOfPlayers));
        DealUtils.dealAll(deck, game.getPlayers());
    }
}
