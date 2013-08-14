package lib.cards.models;


public class NewGameEventHandler extends
        EventHandler<Game.NewGameListener, GameEventObject> {

    public void fireOnNewGame(Game game) {
        if (hasListeners()) {
            GameEventObject eventObject = new GameEventObject(game);
            notifyListeners(eventObject);
        }
    }

    @Override
    public void notifyListener(Game.NewGameListener listener,
            GameEventObject eventObject) {
        listener.onNewGameAction(eventObject);
    }
}
