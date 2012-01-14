package org.jocamo.cardgames.battleroyale.rules;

import org.jocamo.cardgames.battleroyale.BattleRoyaleGame;
import org.jocamo.cardgames.battleroyale.Player;
import org.jocamo.cardgames.core.Card;

/**
 * This is a standard rule.
 * 
 * A player becomes inactive when they run out of cards.
 * 
 * @author Joe
 */
public class PlayerInactiveWhenHandIsEmptyRule {

    public void onCardPlayed(BattleRoyaleGame game, Player player, Card playedCard) {
        player.setActive(player.getHand().isEmpty());
    }
}
