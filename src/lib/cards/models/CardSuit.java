package lib.cards.models;

public enum CardSuit {
    CLUBS(0), DIAMONDS(1), HEARTS(2), SPADES(3);

    private final int value;

    public int getValue() {
        return value;
    }

    CardSuit(int value) {
        this.value = value;
    }

    static CardSuit getAt(int value) {
        value = value % 4;
        for (CardSuit p : CardSuit.values()) {
            if (p.getValue() == value) {
                return p;
            }
        }
        // Not reached.
        return CardSuit.CLUBS;
    }
}
