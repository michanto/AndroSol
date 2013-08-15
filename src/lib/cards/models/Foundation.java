package lib.cards.models;

@SuppressWarnings("serial")
public class Foundation extends CardStack {
    public Foundation(int index, FoundationSequence foundationSequence,
            FoundationBaseCard foundationBaseCard) {
        setIndex(index);
        this.foundationSequence = foundationSequence;
        this.foundationBaseCard = foundationBaseCard;
    }

    FoundationSequence foundationSequence;
    FoundationBaseCard foundationBaseCard;

    public FoundationSequence getFoundationSequence() {
        return foundationSequence;
    }

    public FoundationBaseCard getFoundationBaseCard() {
        return foundationBaseCard;
    }

    @Override
    boolean canFillEmptyStackWith(Card card) {
        switch (getFoundationBaseCard()) {
        case ACE:
        case ACES_DEALT_AT_START_OF_GAME:
            return card.getValue().equals(CardValue.ACE);
        case DEALT_AT_RANDOM:
            return true;
        }
        return false;
    }

    @Override
    public boolean couldBuildStackWith(Card topCard, Card card) {
        if (!buildsUp(topCard, card)) {
            return false;
        }

        switch (getFoundationSequence()) {
        case BUILD_UP_IN_ALTERNATE_COLORS:
            return !card.getColor().equals(topCard.getColor());
        case BUILD_UP_IN_SAME_COLOR:
            return card.getColor().equals(topCard.getColor());
        case BUILD_UP_IN_SAME_SUIT:
            return card.getSuit().equals(topCard.getSuit());
        case BUILD_UP_REGARDLESS_OF_SUIT:
            return true;
        }
        return false;
    }
}
