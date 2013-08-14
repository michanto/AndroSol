package lib.cards.models;

public enum RedealsAllowed {
    NONE(0), ONE(1), TWO(2), THREE(3), UNLIMITED(Integer.MAX_VALUE);

    private final int value;

    public int getValue() {
        return value;
    }

    RedealsAllowed(int value) {
        this.value = value;
    }

}
