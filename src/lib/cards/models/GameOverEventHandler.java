package lib.cards.models;

public class GameOverEventHandler extends
        EventHandler<Game.GameOverListener, GameOverEventObject> {

    public void fireOnGameOver(Game game, boolean won) {
        if (hasListeners()) {
            GameOverEventObject eventObject = new GameOverEventObject(game, won);
            notifyListeners(eventObject);
        }
    }

    @Override
    protected void notifyListener(Game.GameOverListener listener,
            GameOverEventObject args) {
        listener.onGameOver(args);
    }
}
