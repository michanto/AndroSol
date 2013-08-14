package lib.cards.models;

public enum CardValue {
    JOKER(0), ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(
            8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

    private final Integer value;

    public Integer toInt() {
        return value;
    }

    CardValue(int value) {
        this.value = value;
    }

    static CardValue getAt(int value) {
        for (CardValue p : CardValue.values()) {
            if (p.toInt() == value) {
                return p;
            }
        }
        return JOKER;
    }
}
