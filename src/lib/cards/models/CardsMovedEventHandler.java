package lib.cards.models;


public class CardsMovedEventHandler extends
        EventHandler<Game.CardsMovedListener, CardsMovedEventObject> {

    public void fireOnCardsMoved(Game game, GameState oldState,
            GameState newState) {
        if (hasListeners()) {
            CardsMovedEventObject eventObject = new CardsMovedEventObject(game,
                    oldState, newState);
            notifyListeners(eventObject);
        }
    }

    @Override
    protected void notifyListener(Game.CardsMovedListener listener,
            CardsMovedEventObject args) {
        listener.onCardsMoved(args);
    }
}
