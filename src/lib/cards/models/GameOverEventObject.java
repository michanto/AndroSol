package lib.cards.models;


@SuppressWarnings("serial")
public class GameOverEventObject extends GameEventObject {
    public GameOverEventObject(Game source, boolean won) {
        super(source);
        this.won = won;
    }

    final private boolean won;

    public boolean getWon() {
        return won;
    }
}
