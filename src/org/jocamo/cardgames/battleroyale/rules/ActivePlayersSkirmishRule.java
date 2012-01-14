package org.jocamo.cardgames.battleroyale.rules;

import java.util.List;
import org.jocamo.cardgames.battleroyale.Round;
import org.jocamo.cardgames.battleroyale.BattleRoyaleGame;
import org.jocamo.cardgames.battleroyale.Player;
import org.jocamo.cardgames.core.Card;
import org.jocamo.cardgames.util.CollectionFilter;

/**
 * This rule simply means that all active players must skirmish at the start of the round: i.e., play a single card to do
 * battle with a single card played by the other active player(s).
 * 
 * @author Joe
 */
public class ActivePlayersSkirmishRule {
    
    private final CollectionFilter<Card, List<Card>> victorChooser;

    public ActivePlayersSkirmishRule(CollectionFilter<Card, List<Card>> victorChooser) {
        if (victorChooser == null) {
            throw new NullPointerException("VictorChooser arg cannot be null.");
        }
        this.victorChooser = victorChooser;
    }
    
    public void onRoundStageStart(BattleRoyaleGame game) {
        Round battle = game.getCurrentRound();
        for (Player player : game.getActivePlayers()) {
            battle.playBattleCard(player, player.getHand().draw());
        }
        List<Player> victors = battle.getPlayersWhoPlayedBattleCards(victorChooser.filter(battle.getBattleCards()));
        if (victors.size() == 1) {
            battle.setWinner(victors.iterator().next());
        }
    }
}
