package lib.cards.controllers;

import lib.cards.models.Game;
import lib.cards.models.GameProperties;

public class NewGameAction extends GameAction {
    private NewGameActionState getNewGameActionState() {
        return (NewGameActionState) getState();
    }

    public NewGameAction(GameProperties gameProperties, Game game) {
        NewGameActionState state = new NewGameActionState();
        state.gameProperties = gameProperties;
        this.setGame(game);
        this.setState(state);
    }

    public NewGameAction(GameProperties gameProperties, Game game,
            int gameNumber) {
        NewGameActionState state = new NewGameActionState();
        state.gameProperties = gameProperties;
        state.gameNumber = gameNumber;
        this.setGame(game);
        this.setState(state);
    }

    @Override
    public String getName() {
        return "New Game";
    }

    @Override
    protected boolean invokeInternal() {
        if (getNewGameActionState().gameNumber != null) {
            getGame().newGame(getNewGameActionState().gameProperties,
                    getNewGameActionState().gameNumber);
        } else {
            getGame().newGame(getNewGameActionState().gameProperties);
            getNewGameActionState().gameNumber = getGame().getGameNumber();
        }
        return true;
    }

}
