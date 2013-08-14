package lib.cards.controllers;

import lib.cards.models.CardStack;
import lib.cards.models.Game;
import lib.cards.models.GameProperties;
import lib.cards.models.SubStack;
import lib.cards.utilities.UndoStack;

public class Actions extends UndoStack {
    public Actions(Game game) {
        this.game = game;
    }

    private Game game;

    public boolean deal() {
        return executeCommand(new DealAction(game));
    }

    public boolean moveSubStack(SubStack toMove, CardStack toStack) {
        return executeCommand(new StackMoveAction(game, toMove, toStack));
    }

    public boolean newGame(GameProperties props, Game game) {
        return executeCommand(new NewGameAction(props, game));
    }

    public boolean newGame(GameProperties props, Game game, int gameNumber) {
        return executeCommand(new NewGameAction(props, game, gameNumber));
    }

}
