package lib.cards.views;

import lib.cards.models.CardStack;
import lib.cards.models.Foundation;
import lib.cards.models.FreeCell;
import lib.cards.models.GameProperties;
import lib.cards.models.Stock;
import lib.cards.models.Tableau;
import lib.cards.models.Waste;
import lib.cards.utilities.Point;
import lib.cards.utilities.Size;

public abstract class GameBoardMetrics {
    protected GameBoardMetrics() {
    }

    public abstract DeckMetrics getDeckMetrics();

    public abstract GameProperties getGameProperties();

    public Size getBoardSize() {
        double widthPlusMargin = getDeckMetrics().getCardSize().width
                + getDeckMetrics().getMargin().width;
        double heightPlusMargin = getDeckMetrics().getCardSize().height
                + getDeckMetrics().getMargin().height;

        // Board size length includes space for the stock, the waste and the
        // tableaus plus a right margin.
        // Board size height includes space for the foundations 13 overlapped
        // cards in a tableau plus a right margin
        return new Size(
                (Math.max(getGameProperties().getNumberOfTableauPiles(), 2
                        + getGameProperties().getNumberOfFreeCells()
                        + getGameProperties().getNumberOfFoundations()) * (widthPlusMargin))
                        + getDeckMetrics().getMargin().width,
                (heightPlusMargin)
                        + (13 + getGameProperties()
                                .getNumberOfCardsPerTableau())
                        * getDeckMetrics().getCardOverlap().height
                        + getDeckMetrics().getMargin().width);

    }

    public boolean usesStock() {
        // If all cards are dealt, no need for stock.
        return getGameProperties().getNumberOfCardsPerTableau()
                * getGameProperties().getNumberOfTableauPiles() < getGameProperties()
                .getNumberOfCards();
    }

    public boolean usesWaste() {
        return usesStock();
    }

    public Point getStockPosition() {
        if (usesStock()) {
            return new Point(20, 20);
        }

        return new Point(20 - getDeckMetrics().getCardSize().width
                - getDeckMetrics().getMargin().width, 20);
    }

    public Point getWastePosition() {
        if (usesWaste()) {
            return new Point(getStockPosition().x
                    + getDeckMetrics().getCardSize().width
                    + getDeckMetrics().getMargin().width, getStockPosition().y);
        }
        return getStockPosition();
    }

    public Point getTableauPosition(int tableau, int cardIndex) {
        Point basePosition;
        if (usesStock()) {
            basePosition = new Point(getStockPosition().x
                    + (tableau)
                    * (getDeckMetrics().getCardSize().width + getDeckMetrics()
                            .getMargin().width), getStockPosition().y
                    + (getDeckMetrics().getCardSize().height + getDeckMetrics()
                            .getMargin().height));
        } else {
            basePosition = new Point(getFreeCellPosition(0).x
                    + (tableau)
                    * (getDeckMetrics().getCardSize().width + getDeckMetrics()
                            .getMargin().width), getStockPosition().y
                    + (getDeckMetrics().getCardSize().height + getDeckMetrics()
                            .getMargin().height));
        }
        return new Point(basePosition.x, basePosition.y + cardIndex
                * (getDeckMetrics().getCardOverlap().height));
    }

    public Point getFreeCellPosition(int index) {
        return new Point(getWastePosition().x
                + (index + 1)
                * (getDeckMetrics().getCardSize().width + getDeckMetrics()
                        .getMargin().width), getStockPosition().y);
    }

    public Point getFoundationPosition(int index) {
        return new Point(getFreeCellPosition(getGameProperties()
                .getNumberOfFreeCells() - 1).x
                + (index + 1)
                * (getDeckMetrics().getCardSize().width + getDeckMetrics()
                        .getMargin().width), getStockPosition().y);
    }

    public Point CardPosition(CardStack stack, int cardIndex) {
        if (stack.getClass() == Stock.class) {
            return getStockPosition();
        }
        if (stack.getClass() == Waste.class) {
            return getWastePosition();
        }
        if (stack.getClass() == Foundation.class) {
            return getFoundationPosition(stack.getIndex());
        }
        if (stack.getClass() == FreeCell.class) {
            return getFreeCellPosition(stack.getIndex());
        }
        if (stack.getClass() == Tableau.class) {
            return getTableauPosition(stack.getIndex(), cardIndex);
        }
        return new Point(0, 0);
    }
}
