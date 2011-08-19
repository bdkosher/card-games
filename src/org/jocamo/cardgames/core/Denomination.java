package org.jocamo.cardgames.core;

/**
 *
 * @author Joe
 */
public enum Denomination {

    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    JACK(11, true), QUEEN(12, true), KING(13, true), ACE(1);
    private final Integer value;
    private final boolean royalty;

    Denomination(Integer value) {
        this(value, false);
    }

    Denomination(Integer value, boolean royalty) {
        this.value = value;
        this.royalty = royalty;
    }

    public boolean isRoyalty() {
        return royalty;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
