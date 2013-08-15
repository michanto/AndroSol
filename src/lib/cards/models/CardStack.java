package lib.cards.models;

import java.util.ArrayList;
import java.util.Locale;

@SuppressWarnings("serial")
public class CardStack extends ArrayList<Card> {
    CardStack() {
    }

    CardStack(int minimumCapacity) {
        this.ensureCapacity(minimumCapacity);
    }

    private int index;

    // Will not be unique for plain CardStacks.
    public CardStackId getCardStackId() {
        CardStackId id = new CardStackId();
        id.index = getIndex();
        id.stackType = getStackType();
        return id;
    }

    public int getIndex() {
        return index;
    }

    protected void setIndex(int index) {
        this.index = index;
    }

    public StackType getStackType() {
        // StackType type = StackType.STACK;

        String typeName = this.getClass().getSimpleName()
                .toUpperCase(Locale.US);
        return StackType.valueOf(typeName);
        /*
         * for (StackType p : StackType.values()) { if
         * (p.name().equals(typeName)) { return p; } }
         * 
         * return type;
         */
    }

    public void init(Iterable<CardState> cards) {
        this.clear();
        for (CardState cardState : cards) {
            this.add(new Card(cardState.getId(), cardState.isFaceUp()));
        }
    }

    public static boolean buildsDown(Card topCard, Card card) {
        if (topCard != null) {
            return topCard.getValue().toInt() - 1 == card.getValue().toInt();
        }
        return false;
    }

    public static boolean buildsUp(Card topCard, Card card) {
        if (topCard != null) {
            return topCard.getValue().toInt() + 1 == card.getValue().toInt();
        }
        return false;
    }

    boolean canFillEmptyStackWith(Card card) {
        return true;
    }

    boolean couldBuildStackWith(Card topCard, Card card) {
        return true;
    }

    boolean canBuildStackWith(Card card) {
        return couldBuildStackWith(peek(), card);
    }

    public boolean canPush(Card card) {
        if (card == null) {
            return false;
        }

        if (peek() == null) {
            return canFillEmptyStackWith(card);
        }

        return canBuildStackWith(card);
    }

    public void push(Card card) {
        if (canPush(card)) {
            this.add(card);
        }
    }

    public Card pop() {
        if (size() == 0) {
            return null;
        }
        Card popped = this.get(size() - 1);
        this.remove(size() - 1);
        return popped;
    }

    public Card peek() {
        return size() == 0 ? null : this.get(size() - 1);
    }

    public String dumpString() {
        String dumpString = "";
        for (int i = 0; i < size(); i++) {
            if (i > 0) {
                dumpString += "  ";
            }
            dumpString += this.get(i).getShortName();
        }
        return dumpString;
    }
}
