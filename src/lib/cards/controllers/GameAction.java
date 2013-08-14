package lib.cards.controllers;

import lib.cards.models.Game;
import lib.cards.models.GameState;
import lib.cards.utilities.CommandImpl;

public abstract class GameAction extends CommandImpl<GameActionState> {
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    protected abstract boolean invokeInternal();

    @Override
    public final boolean invoke() {
        getState().gameStateBefore = new GameState(getGame());
        boolean result = invokeInternal();
        if (result) {
            getState().gameStateAfter = new GameState(getGame());
        }
        return result;
    }

    @Override
    public boolean reInvoke() {
        return game.applyGameState(getState().gameStateAfter);
    }

    @Override
    public boolean undo() {
        return game.applyGameState(getState().gameStateBefore);
    }
}
