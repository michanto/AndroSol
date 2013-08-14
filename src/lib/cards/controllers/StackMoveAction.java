package lib.cards.controllers;

import lib.cards.models.CardStack;
import lib.cards.models.Game;
import lib.cards.models.GameState;
import lib.cards.models.SubStack;
import lib.cards.utilities.CollectionUtils;

public class StackMoveAction extends GameAction {
    private StackMoveState getStackMoveState() {
        return (StackMoveState) getState();
    }

    private CardStack getSource() {
        return getGame().findStack(getStackMoveState().source);
    }

    private CardStack getTarget() {
        return getGame().findStack(getStackMoveState().target);
    }

    public StackMoveAction(Game game, SubStack fromSubStack, CardStack target) {
        StackMoveState state = new StackMoveState();
        state.cardsToMove = GameState.stackState(fromSubStack.getCards());
        state.source = fromSubStack.getStack().getCardStackId();
        state.target = target.getCardStackId();
        setGame(game);
        setState(state);
    }

    @Override
    public String getName() {
        return getStackMoveState().cardsToMove.size() > 1 ? "Move Cards"
                : "Move Card";
    }

    @Override
    protected boolean invokeInternal() {
        // Create a substack
        SubStack source = new SubStack(getSource(),
                getStackMoveState().cardsToMove.size());
        // Ensure it is valid
        if (CollectionUtils.intersect(GameState.stackState(source.getCards()),
                getStackMoveState().cardsToMove).size() == getStackMoveState().cardsToMove
                .size()) {
            // Do the move
            return getGame().moveSubStack(source, getTarget());
        }

        return false;
    }
}
