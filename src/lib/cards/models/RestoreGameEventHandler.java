package lib.cards.models;

public class RestoreGameEventHandler extends
        EventHandler<Game.RestoreGameListener, GameEventObject> {

    public void fireOnRestoreGame(Game game) {
        if (hasListeners()) {
            GameEventObject eventObject = new GameEventObject(game);
            notifyListeners(eventObject);
        }
    }

    @Override
    protected void notifyListener(Game.RestoreGameListener listener,
            GameEventObject eventObject) {
        listener.onRestoreGameAction(eventObject);
    }
}
