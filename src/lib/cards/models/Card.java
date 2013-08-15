package lib.cards.models;

public class Card {
    public static final int CARDS_PER_DECK = 52;

    /*
     * ' Card id values: Ace| 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10
     * |Jack|Queen|King ' CLUBS: 0 4 8 12 16 20 24 28 32 36 40 44 48 ' DIAMONDS:
     * 1 5 9 13 17 21 25 29 33 37 41 45 49 ' HEARTS: 2 6 10 14 18 22 26 30 34 38
     * 42 46 50 ' SPADES: 3 7 11 15 19 23 27 31 35 39 43 47 51
     */

    private int id;

    public int getId() {
        return id;
    }

    // Index value from table above

    public int getIndex() {
        return getId() % CARDS_PER_DECK;
    }

    public int getPack() {
        return getId() / CARDS_PER_DECK;
    }

    public Card(int id) {
        this.id = id;
        this.faceUp = false;
    }

    protected Card(int id, boolean faceUp) {
        this.id = id;
        this.faceUp = faceUp;
    }

    private boolean faceUp;

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    public CardSuit getSuit() {
        return CardSuit.getAt(getIndex() % 4);
    }

    public CardValue getValue() {
        return CardValue.getAt((getIndex() / 4) + 1);
    }

    public CardColor getColor() {
        return (getSuit().equals(CardSuit.DIAMONDS) || getSuit().equals(
                CardSuit.HEARTS)) ? CardColor.RED : CardColor.BLACK;
    }

    public String getName() {
        return getValue().toString() + "Of" + getSuit().toString();
    }

    public String getShortName() {
        String[] valueShortNames = { "", "A", "2", "3", "4", "5", "6", "7",
                "8", "9", "X", "J", "Q", "K" };
        String value = valueShortNames[getValue().toInt()];
        return value + getSuit().toString().substring(0, 1);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Card other = (Card) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}
