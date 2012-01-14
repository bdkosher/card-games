package org.jocamo.cardgames.battleroyale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jocamo.cardgames.core.Card;
import org.jocamo.cardgames.core.Deck;

/**
 * In Battle Royale, a round begins with players all playing one card face up; this is considered a "skirmish".
 * If there's no definite winner, the round continues with non-eliminated players playing an additional card
 * face up, preceeded by playing cards face down, the number of which are determined by the situation. 
 * 
 * @author Joe
 */
public class Round {

    private final Deck pot = new Deck();
    /**
     * Holds the card(s) which the a player is battling with--the cards played face up.
     * In traditional Battle Royale, a player plays only one battle card at a time, but we
     * use a List of cards in case of rule variants which may wish to utilize multiple
     * battle cards.
     * 
     */
    private final Map<Player, List<Card>> playersBattleCards = new HashMap<>();
    private int stageNumber = 0;
    private boolean over = false;
    private Player winner;

    public int getStageNumber() {
        return stageNumber;
    }

    public void nextStage() {
        ++stageNumber;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver() {
        this.over = true;
    }

    public Deck getPot() {
        return pot;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void playBattleCard(Player player, Card card) {
        List<Card> battleCardsForPlayer = playersBattleCards.get(player);
        if (battleCardsForPlayer == null) {
            battleCardsForPlayer = new ArrayList<>();
            playersBattleCards.put(player, battleCardsForPlayer);
        }
        battleCardsForPlayer.add(card);
        //rules.onCardPlayed
    }

    /**
     * Returns all of the played battle cards.
     * 
     * @return 
     */
    public List<Card> getBattleCards() {
        List<Card> battleCards = new ArrayList<>();
        for (List<Card> playerBattleCards : playersBattleCards.values()) {
            battleCards.addAll(playerBattleCards);
        }
        return battleCards;
    }
    
    /**
     * Returns the player who played the given battle card(s); if no battle cards have been played that match
     * one of the given card, an empty list is returned.
     * 
     * @param cards
     * @return 
     */
    public List<Player> getPlayersWhoPlayedBattleCard(Card card) {
        return getPlayersWhoPlayedBattleCards(card == null ? null : Collections.singletonList(card));
    }

    /**
     * Returns the player who played the given battle card(s); if no battle cards have been played that match
     * one of the given card, an empty list is returned.
     * 
     * @param cards
     * @return 
     */
    public List<Player> getPlayersWhoPlayedBattleCards(List<Card> cards) {
        List<Player> players = new ArrayList<>(playersBattleCards.size());
        if (cards == null || cards.isEmpty()) {
            return players;
        }
        for (Map.Entry<Player, List<Card>> entry : playersBattleCards.entrySet()) {
            for (Card card : cards) {
                if (entry.getValue().contains(card)) {
                    players.add(entry.getKey());
                }
            }
        }
        return players;
    }

    public void resetBattleCards() {
        playersBattleCards.clear();
    }
}
