package lib.cards.controllers;

import lib.cards.models.Game;

public class DealAction extends GameAction {
    public DealAction(Game game) {
        this.setState(new GameActionState());
        this.setGame(game);
    }

    @Override
    public String getName() {
        return "Deal";
    }

    @Override
    public boolean invokeInternal() {
        getGame().deal();
        return true;
    }
}
