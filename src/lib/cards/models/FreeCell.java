package lib.cards.models;

@SuppressWarnings("serial")
public class FreeCell extends CardStack {
    public FreeCell(int index) {
        setIndex(index);
    }

    @Override
    boolean canFillEmptyStackWith(Card card) {
        return true;
    }

    @Override
    boolean couldBuildStackWith(Card topCard, Card card) {
        // Only one card per FreeCell
        return false;
    }
}
