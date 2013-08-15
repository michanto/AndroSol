package lib.cards.controllers;

import java.util.ArrayList;
import java.util.List;

import lib.cards.models.CardStack;
import lib.cards.models.Game;
import lib.cards.models.StackType;
import lib.cards.models.Stock;
import lib.cards.models.SubStack;
import lib.cards.models.Tableau;
import lib.cards.utilities.CollectionUtils;
import lib.cards.views.CardSprite;
import lib.cards.views.GameBoard;
import lib.cards.views.GameBoard.SpriteDefaultActionListener;
import lib.cards.views.GameBoard.SpriteSelectedListener;
import lib.cards.views.Sprite;
import lib.cards.views.SpriteEventObject;
import lib.cards.views.StackSprite;

public class GameController {
    public GameController(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.gameBoard.getSpriteSelectedEvent().add(
                new SpriteSelectedListener() {
                    @Override
                    public void onSpriteSelected(SpriteEventObject args) {
                        GameController.this.onSpriteSelected(args);
                    }
                });
        this.gameBoard.getSpriteDefaultActionEvent().add(
                new SpriteDefaultActionListener() {
                    @Override
                    public void onSpriteDefaultAction(SpriteEventObject args) {
                        GameController.this.onSpriteDefaultAction(args);
                    }
                });
        actions = new Actions(getGame());
    }

    private Actions actions;
    public GameBoard gameBoard;

    public Game getGame() {
        return gameBoard.getGame();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Actions getActions() {
        return actions;
    }

    CardSprite selectedSprite;

    CardSprite getSelectedSprite() {
        return selectedSprite;
    }

    void setSelectedSprite(CardSprite sprite) {
        selectedSprite = sprite;
    }

    public void onSpriteSelected(SpriteEventObject args) {
        Sprite sprite = args.getSprite();
        if (sprite == null) {
            // System.Diagnostics.Debug.WriteLine("Selected: nothing");
        } else {
            // System.Diagnostics.Debug.WriteLine("Selected: " + sprite.Name);
        }

        if (getSelectedSprite() == null && sprite != null) {
            if (sprite instanceof CardSprite) {
                CardSprite cardSprite = (CardSprite) sprite;
                setSelectedSprite(cardSprite);
                cardSprite.setHighlight(true);
            }

            if (sprite instanceof StackSprite) {
                StackSprite stackSprite = (StackSprite) sprite;
                if (stackSprite.getStack() != null
                        && stackSprite.getStack().getClass() == Stock.class) {
                    actions.deal();
                    return;
                }
            }
            return;
        }

        Sprite selectedSprite = getSelectedSprite();

        selectedSprite.setHighlight(false);
        setSelectedSprite(null);

        if (sprite == null) {
            return;
        }

        SubStack toMove = gameBoard.getSubStack(selectedSprite.getName());
        CardStack fromStack = gameBoard.getStack(selectedSprite);

        CardStack toStack = gameBoard.getStack(sprite);

        if (toMove == null || toStack == null || fromStack == null) {
            return;
        }

        if (fromStack.getClass() == Stock.class
                && toStack.getClass() == Stock.class) {
            actions.deal();
            return;
        }

        if (getGame().canMoveSubStack(toMove, toStack)) {
            actions.moveSubStack(toMove, toStack);
        } else {
            // If a card in a tableau is selected, try the cards above it as
            // well.
            if (toMove.getStack().getClass() == Tableau.class) {
                // Go up from that card and see if it is possible to move more.
                for (int i = toMove.getSize(); i < toMove.getStack().size(); i++) {
                    SubStack tryStack = new SubStack(toMove.getStack(), i);
                    if (getGame().canMoveSubStack(tryStack, toStack)) {
                        toMove = tryStack;
                        actions.moveSubStack(toMove, toStack);
                        break;
                    }
                }
            }
        }
    }

    public void moveToStackType(StackType stackType) {
        CardSprite selectedSprite = getSelectedSprite();
        if (selectedSprite == null) {
            return;
        }

        selectedSprite.setHighlight(false);
        setSelectedSprite(null);

        List<? extends CardStack> stacks = null;
        switch (stackType) {
        case STOCK:
            stacks = CollectionUtils.toList(getGame().getStock());
            break;
        case WASTE:
            stacks = CollectionUtils.toList(getGame().getWaste());
            break;
        case FOUNDATION:
            stacks = getGame().getFoundations();
            break;
        case TABLEAU:
            stacks = getGame().getTableaus();
            break;
        case FREECELL:
            stacks = getGame().getFreeCells();
            break;
        default:
        }

        CardStack fromStack = gameBoard.getStack(selectedSprite);
        CardStack toStack = null;

        // Try moving to an empty stack.
        List<CardStack> empties = new ArrayList<CardStack>();
        for (CardStack s : stacks) {
            if (s.size() == 0) {
                empties.add(s);
            }
        }

        if (empties.size() > 0) {
            toStack = getGame().canMoveCardToStackCollection(empties,
                    selectedSprite.getCard());
        }

        if (toStack == null) {
            toStack = getGame().canMoveCardToStackCollection(stacks,
                    selectedSprite.getCard());
        }

        if (toStack == null) {
            return;
        }

        setSelectedSprite(null);
        SubStack toMove = new SubStack(fromStack, selectedSprite.getCard());
        if (getGame().canMoveSubStack(toMove, toStack)) {
            actions.moveSubStack(toMove, toStack);
        }
    }

    public void onSpriteDefaultAction(SpriteEventObject args) {
        Sprite sprite = args.getSprite();
        if (sprite == null || !CardSprite.class.isInstance(sprite)) {
            setSelectedSprite(null);
            return;
        }

        sprite.setHighlight(false);
        setSelectedSprite(null);

        CardSprite selectedSprite = (CardSprite) sprite;

        CardStack fromStack = gameBoard.getStack(selectedSprite);
        CardStack toStack = getGame().canMoveCardToStackCollection(
                getGame().getFoundations(), selectedSprite.getCard());
        if (toStack == null) {
            // Can only move to freecell it is the top card in the stack.
            if (!getGame().getFreeCells().contains(fromStack)
                    && fromStack.peek().equals(selectedSprite.getCard())) {
                toStack = getGame().canMoveCardToStackCollection(
                        getGame().getFreeCells(), selectedSprite.getCard());
            }
            if (toStack == null) {
                // Try to move to another tableau, or an empty tableau.
                toStack = getGame().canMoveCardToStackCollection(
                        getGame().getTableaus(), selectedSprite.getCard());
            }
        }

        if (toStack == null) {
            return;
        }

        SubStack toMove = new SubStack(fromStack, selectedSprite.getCard());
        if (getGame().canMoveSubStack(toMove, toStack)) {
            actions.moveSubStack(toMove, toStack);
        }
    }
}
