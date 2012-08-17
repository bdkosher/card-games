package org.washcom.cardgames.battleroyale;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Joe
 */
public class Battle {
    private static final Logger log = Logger.getLogger(Battle.class.toString());
    public static final int MAXIMUM_BATTLE_CONTINUATIONS = 3;
    private final int number;
    private final BattleRoyaleGame game;
    private final List<BattleCard> battleCards = new ArrayList<>();
    private List<Player> battlers;
    private int continuations = 0;

    /**
     * Creates a new battle.
     *
     * @param number
     * @param game
     */
    public Battle(int number, BattleRoyaleGame game) {
        if (game == null) {
            throw new IllegalArgumentException("Game arg cannot be null.");
        }
        if (number < 1) {
            throw new IllegalArgumentException("Round number must be greater than 0");
        }
        this.number = number;
        this.game = game;
    }

    /**
     * Returns the round number.
     *
     * @return
     */
    public int getNumber() {
        return number;
    }

    public BattleRoyaleGame getGame() {
        return game;
    }

    public List<BattleCard> getBattleCards() {
        return battleCards;
    }

    /**
     * Returns the number of times the battle has continued beyond the initial playing of battle cards.
     * 
     * @return 
     */
    public int getContinuations() {
        return continuations;
    }
    
    /**
     * Does a skirmish: collects a single card from each active player. Returns the winning player or null if there is no winning
     * player.
     *
     * @return
     */
    public void fight(BattleAssessor assessor) {
        log.info("Fighting battle " + number);
        this.battlers = game.getActivePlayers();
        Player nonBattler = findNonBattler();
        for (; continuations <= MAXIMUM_BATTLE_CONTINUATIONS; ++continuations) {
            log.info("Executing continuation " + continuations + " of battle " + number);
            playBattleCards();
            BattleCard winner = assessor.pickWinner(this);
            addBattleCardsToGamePot();
            if (winner != null) {
                spoilsToTheVictor(winner.getPlayedBy());
                return;
            }
            
            eliminateCardlessBattlers();
            if (battlers.size() == 1) {
                spoilsToTheVictor(battlers.get(0));
                return;
            }
            
            addFeeCardsToGamePot(assessor.determineFees(this));
            
            eliminateCardlessBattlers();
            if (battlers.size() == 1) {
                spoilsToTheVictor(battlers.get(0));
                return;
            }
        }
        // unresolved battle, so inactive player gets the spoils
        spoilsToTheVictor(nonBattler);
    }

    /**
     * The non-battler is eligible to receive all the spoils in the event of an unresolvable battle. If all players are battling,
     * or if there is more than one non-battler, this method returns null.
     *
     * @return
     */
    private Player findNonBattler() {
        List<Player> allPlayers = new ArrayList<>(game.getPlayers());
        allPlayers.removeAll(battlers);
        return allPlayers.size() == 1 ? allPlayers.get(0) : null;
    }

    private void playBattleCards() {
        log.info("Playing battle cards");
        for (Player battler : battlers) {
            battleCards.add(new BattleCard(battler.getHand().draw(), battler));
        }
    }

    private void spoilsToTheVictor(Player victor) {
        log.info("Giving spoils to " + victor);
        victor.getHand().putOnBottom(game.getGameCards().drawAll());
    }

    private void addBattleCardsToGamePot() {
        log.info("Adding " + battleCards.size() + " battle cards to game pot");
        for (BattleCard battleCard : battleCards) {
            game.getGameCards().put(battleCard.getCard());
        }
        battleCards.clear();
    }

    private void eliminateCardlessBattlers() {
        log.info("Eliminating cardless battlers");
        for (Iterator<Player> it = battlers.iterator(); it.hasNext();) {
            if (it.next().getHand().isEmpty()) {
                it.remove();
                log.info("Removed battler");
            }
        }
    }

    /**
     * Collects fees from the battle
     *
     * @return
     */
    private void addFeeCardsToGamePot(Map<Player, Integer> fees) {
        log.info("Adding fee cards to battle pot");
        for (Map.Entry<Player, Integer> entry : fees.entrySet()) {
            Player battler = entry.getKey();
            int fee = entry.getValue();
            log.info("Battler " + battler + " pays fee of " + fee);
            game.getGameCards().put(battler.getHand().drawUpTo(fee));
        }
    }
}
