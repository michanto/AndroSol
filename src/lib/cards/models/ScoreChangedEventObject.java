package lib.cards.models;


@SuppressWarnings("serial")
public class ScoreChangedEventObject extends GameEventObject {
    public ScoreChangedEventObject(Game source, int oldScore, int newScore) {
        super(source);
        this.oldScore = oldScore;
        this.newScore = newScore;
    }

    public int getOldScore() {
        return oldScore;
    }

    public int getNewScore() {
        return newScore;
    }

    private final int oldScore;
    private final int newScore;
}
