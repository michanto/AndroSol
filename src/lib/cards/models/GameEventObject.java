package lib.cards.models;

import java.util.EventObject;

@SuppressWarnings("serial")
public class GameEventObject extends EventObject {
    public GameEventObject(Game game) {
        super(game);
    }

    public Game getGame() {
        return (Game) getSource();
    }
}
