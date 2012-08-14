package org.washcom.cardgames.battleroyale;

import org.washcom.cardgames.core.Card;

/**
 *
 * @author Joe
 */
public class BattleCard {
    
    private final Card card;
    
    private final Player playedBy;

    public BattleCard(Card card, Player playedBy) {
        this.card = card;
        this.playedBy = playedBy;
    }

    public Card getCard() {
        return card;
    }

    public Player getPlayedBy() {
        return playedBy;
    }
    
}
