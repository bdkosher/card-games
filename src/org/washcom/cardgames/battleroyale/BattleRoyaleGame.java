package org.washcom.cardgames.battleroyale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.washcom.cardgames.core.Deck;
import org.washcom.cardgames.core.DeckDealer;

/**
 * The game class maintains the state of the game. - the players - the current round - game cards (not belonging to any particular
 * player)
 *
 * The game class also contains methods to move state, namely, to start a new round.
 *
 * @author Joe
 */
public class BattleRoyaleGame {

    private static final int MINIMUM_NUMBER_OF_PLAYERS = 2;
    private static final boolean LEFTOVER_CARDS_FROM_DEAL_GO_INTO_GAMEPOT = true;
    private final BattleAssessor assessor;

    /**
     * Initializes a new game. The game is not started until {@code start} is called. The provided listener is responsible for
     * driving the state of the game.
     *
     * @param listener
     */
    public BattleRoyaleGame(BattleAssessor assessor) {
        if (assessor == null) {
            throw new IllegalArgumentException("BattleAssessor arg cannot be null.");
        }
        this.assessor = assessor;
    }
    private List<Player> players;
    private Deck gameCards;
    private int currentRoundNumber = 0;
    private Battle currentBattle;
    private Player winner = null;

    /**
     * Starts a new game of Battle Royale.
     *
     * @param deck - the cards to be used in the game
     * @param players - the players who will be playing the game
     */
    public void start(Deck deck, Player... players) {
        if (currentRoundNumber > 0) {
            throw new IllegalStateException("Game has already been started.");
        }
        initializeGame(deck, players);
    }

    private void initializeGame(Deck deck, Player... players) {
        if (deck == null) {
            throw new IllegalArgumentException("Deck cannot be null.");
        }
        if (players == null || players.length < MINIMUM_NUMBER_OF_PLAYERS) { // TODO: set 2 in config.
            throw new IllegalArgumentException("Must have at least two players.");
        }
        if (deck.size() < MINIMUM_NUMBER_OF_PLAYERS) {
            throw new IllegalArgumentException("Must have at least as many cards as minimum players.");
        }
        this.players = Arrays.asList(players);
        this.gameCards = deck;
        if (LEFTOVER_CARDS_FROM_DEAL_GO_INTO_GAMEPOT) {
            DeckDealer.dealFairly(this.gameCards, players);
        } else {
            DeckDealer.dealEntirely(this.gameCards, players);
        }
    }

    public void play() {
        /*
         * Battle as long as there are two or more players in the game.
         */
        while (getActivePlayers().size() > 1) {
            battle();
        }

        /*
         * If there's one player left standing, declare him the winner. It is possible given few enough cards and high enough max
         * battle continuations that all active players are engaged in a battle that never concludes, thus ending the game without
         * a declared winner.
         */
        if (getActivePlayers().size() == 1) {
            winner = getActivePlayers().get(0);
        }
    }

    public void battle() {
        currentBattle = new Battle(++currentRoundNumber, this);
        while (currentBattle.isBeingFought()) {
            collectBattleFees();
            List<Player> battlers = getActivePlayers();
            if (battlers.size() <= 1) {
                break;
            }
            currentBattle.skirmish(battlers);
//            assessor.pickWinner(null)
            //List<BattleCard> battleCards = currentBattle.getBattleCards();
            if (battlers.size() > 1) {
            }
        }
    }

    private void collectBattleFees() {
        Map<Player, Integer> fees = assessor.determineFees(currentBattle);
                currentBattle.continueBattle();
        for (Map.Entry<Player, Integer> entry : fees.entrySet()) {
            Player player = entry.getKey();
            int fee = entry.getValue();
            gameCards.put(player.getHand().drawUpTo(fee));

            /*
             * TODO: allow configurable policy that addresses what happens when fees cannot be paid: - person pays all he can and
             * therefore becomes inactive - the person pays a fee equal to his number of cards minus 1 (so he can skirmish) -
             * person doesn't pay any fees and drops out of battle but stays active
             */
        }
    }

    /**
     * Returns the current battle.
     *
     * @return
     */
    public Battle getCurrentBattle() {
        return currentBattle;
    }

    /**
     * Returns the players playing this game.
     *
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the cards which do not belong to any player in particular.
     *
     * @return
     */
    public Deck getGameCards() {
        return gameCards;
    }

    /**
     * Returns all players who are participating in this game.
     *
     * @return
     */
    public List<Player> getActivePlayers() {
        List<Player> active = new ArrayList<>(players.size());
        for (Player player : players) {
            if (!player.getHand().isEmpty()) {
                active.add(player);
            }
        }
        return active;
    }

    /**
     * Returns the winner of the game. If the game hasn't started, this method returns null.
     *
     * @return
     */
    public Player getWinner() {
        return winner;
    }

    /**
     * Returns the number of rounds played so far.
     *
     * @return
     */
    public int getRoundsPlayed() {
        return currentRoundNumber;
    }
}
