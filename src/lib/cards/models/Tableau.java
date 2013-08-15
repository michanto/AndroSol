package lib.cards.models;

@SuppressWarnings("serial")
public class Tableau extends CardStack {

    public Tableau(int index, TableauSequence tableauSequence,
            EmptyTableauPileFilledBy emptyTableauPileFilledBy) {
        setIndex(index);
        this.tableauSequence = tableauSequence;
        this.emptyTableauPileFilledBy = emptyTableauPileFilledBy;
    }

    public TableauSequence getTableauSequence() {
        return tableauSequence;
    }

    public EmptyTableauPileFilledBy getEmptyTableauPileFilledBy() {
        return emptyTableauPileFilledBy;
    }

    private TableauSequence tableauSequence;
    private EmptyTableauPileFilledBy emptyTableauPileFilledBy;

    @Override
    public boolean canFillEmptyStackWith(Card card) {
        switch (getEmptyTableauPileFilledBy()) {
        case ANY_CARD_IN_SEQUENCE:
        case CARD_FROM_STOCK_OR_WASTE:
            return true;
        case KING:
            return card.getValue().equals(CardValue.KING);
        case NONE:
            return false;
        }
        return false;
    }

    @Override
    public boolean couldBuildStackWith(Card topCard, Card card) {
        if (!buildsDown(topCard, card)) {
            return false;
        }

        switch (getTableauSequence()) {
        case BUILD_DOWN_IN_ALTERNATE_COLORS:
            return !card.getColor().equals(topCard.getColor());
        case BUILD_DOWN_IN_ANY_SUIT:
            return true;
        case BUILD_DOWN_IN_ANY_SUIT_BUT_SAME:
            return !card.getSuit().equals(topCard.getSuit());
        case BUILD_DOWN_IN_SAME_COLOR:
            return card.getColor().equals(topCard.getColor());
        case BUILD_DOWN_IN_SAME_SUIT:
            return card.getSuit().equals(topCard.getSuit());
        }
        return false;
    }
}
