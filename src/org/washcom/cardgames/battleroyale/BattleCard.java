package org.washcom.cardgames.battleroyale;

import com.google.common.base.Preconditions;
import org.washcom.cardgames.core.Card;
import static org.washcom.cardgames.core.Denomination.ACE;
import static org.washcom.cardgames.core.Denomination.KING;

/**
 *
 * @author Joe
 */
public class BattleCard {
    
    private static final int ACE_VALUE = KING.getValue() + 1;

    private final Card card;
    private final Player playedBy;

    public BattleCard(Card card, Player playedBy) {
        Preconditions.checkNotNull(card);
        Preconditions.checkNotNull(playedBy);
        this.card = card;
        this.playedBy = playedBy;
    }

    public Card getCard() {
        return card;
    }

    public Player getPlayedBy() {
        return playedBy;
    }
    
    /**
     * Returns true if the card is eligible to participate in a battle royale (i.e. Ace, King, Queen, Jack)
     */
    public boolean isBattleRoyaleEligible() {
        return card.getDenomination().isRoyalty() || card.getDenomination() == ACE;
    }
    
    /**
     * Computes the difference between denominations. Aces counted as high.
     * If this card is worth more than the other card, a positive integer is returned; if not, a
     * negative will be returned.
     */
    public int computeValueDifference(BattleCard other) {
        Preconditions.checkNotNull(other);
        int value = card.getDenomination() == ACE 
                ? ACE_VALUE : card.getDenomination().getValue();
        int otherValue = other.card.getDenomination() == ACE 
                ? ACE_VALUE : other.card.getDenomination().getValue();
        return value - otherValue;
    }
    
}
