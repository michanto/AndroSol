package lib.cards.models;

public class ScoreChangedEventHandler extends
        EventHandler<Game.ScoreChangedListener, ScoreChangedEventObject> {

    public void fireOnScoreChanged(Game source, int oldScore, int newScore) {
        if (hasListeners()) {
            ScoreChangedEventObject args = new ScoreChangedEventObject(source,
                    oldScore, newScore);
            notifyListeners(args);
        }
    }

    @Override
    protected void notifyListener(Game.ScoreChangedListener listener,
            ScoreChangedEventObject args) {
        listener.onScoreChanged(args);
    }

}
