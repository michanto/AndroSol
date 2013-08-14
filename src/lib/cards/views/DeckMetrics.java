package lib.cards.views;

import lib.cards.utilities.Size;

public class DeckMetrics {
    private Size cardSize;

    public Size getCardSize() {
        return cardSize;
    }

    public void setCardSize(Size cardSize) {
        this.cardSize = cardSize;
    }

    public Size getCardOverlap() {
        return new Size(cardSize.width / 4, cardSize.height / 4);
    }

    // Space between stacks of cards
    public Size getMargin() {
        return new Size(cardSize.width / 6, cardSize.width / 6);
    }
}
