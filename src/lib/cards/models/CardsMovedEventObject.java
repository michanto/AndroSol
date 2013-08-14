package lib.cards.models;

import java.util.EventObject;

@SuppressWarnings("serial")
public class CardsMovedEventObject extends EventObject {
    public CardsMovedEventObject(Game game, GameState oldState,
            GameState newState) {
        super(game);
        this.oldState = oldState;
        this.newState = newState;
    }

    public GameState getOldState() {
        return oldState;
    }

    public GameState getNewState() {
        return newState;
    }

    GameState oldState;
    GameState newState;
}
