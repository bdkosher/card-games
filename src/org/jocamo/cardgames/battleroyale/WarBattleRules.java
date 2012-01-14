package org.jocamo.cardgames.battleroyale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import org.jocamo.cardgames.core.Card;
import org.jocamo.cardgames.core.Deck;
import org.jocamo.cardgames.core.DeckExhaustedException;

/**
 * For each battle, all active players play a card; the player with the highest value card wins. If there is no
 * authoritive winner, each potential winner adds so many cards to the battle hand before revealing a new high card;
 * repeat until we have a winner.
 * 
 * @author Joe
 */
public class WarBattleRules implements BattleRules {

    private HighCardChooser highCardChooser;

    @Override
    public void doBattle(Round battle, BattleRoyaleGame game) {
        List<Player> battlers = game.getActivePlayers();
        while (!battle.isOver()) {
            battle.nextStage();
            Card[] fightCards = getFightCards(battlers);
            battlers = fight(battle, battlers, fightCards);
            if (battlers.size() > 1) {
                int[] fightDiscardsRequired = new int[battlers.size()];
                int i = 0;
                for (ListIterator<Player> it = battlers.listIterator(); it.hasNext(); ++i) {
                    Player battler = it.next();
                    try {
                        List<Card> discards = battler.getHand().draw(fightDiscardsRequired[i]);
                        battle.getPot().replace(discards);
                    } catch (DeckExhaustedException e) {
                        /* Player has run out of cards and cannot participate any longer. */
                        it.remove();
                    }
                }
            }

            if (battlers.size() == 1) {
                battle.setOver();
                Player victor = battlers.get(0);
                Deck spoils = battle.getPot();
                spoils.shuffle();
                victor.getHand().collect(spoils.draw(spoils.size()));
                battle.setOver();
            } else if (battlers.isEmpty()) {
                /* Ruh roh! */
                battle.setOver();
            }
        }
    }

    

    /**
     * The battlers fight; surviving battlers are returned.
     * @param battle
     * @param game
     * @param battlers
     * @return 
     */
    protected List<Player> fight(Round battle, List<Player> battlers, Card[] fightCards) {
        battle.getPot().replace(Arrays.asList(fightCards));
        int[] winners = highCardChooser.choose(fightCards);
        List<Player> continuingPlayers = new ArrayList<Player>(winners.length);
        for (int i = 0; i < winners.length; ++i) {
            continuingPlayers.add(battlers.get(winners[i]));
        }
        return continuingPlayers;
    }
    
    protected List<Player> postFight(Round battle, List<Player> battlers, Card[] fightCards) {
        List<Player> continuingPlayers = new ArrayList<Player>(battlers.size());
        int[] fightDiscardsRequired = new int[battlers.size()];
        for (int i = 0; i < battlers.size(); ++i) {
            Player battler = battlers.get(i);
            try {
                List<Card> discards = battler.getHand().draw(fightDiscardsRequired[i]);
                battle.getPot().replace(discards);
                continuingPlayers.add(battler);
            } catch (DeckExhaustedException e) {
                /* Player has run out of cards and cannot participate any longer. */
            }
        }
        return continuingPlayers;
    }

    /**
     * Returns an array of cards which the given list of players are using in the fight.
     * 
     * @param battlers
     * @return 
     */
    protected Card[] getFightCards(List<Player> battlers) {
        Card[] cards = new Card[battlers.size()];
        for (int i = 0; i < battlers.size(); ++i) {
            cards[i] = battlers.get(i).getHand().draw();
        }
        return cards;
    }
}
