package lib.cards.models;

public class SubStackMovedEventHandler extends
        EventHandler<Game.SubStackMovedListener, SubStackMovedEventObject> {

    public void fireOnSubStackMoved(Game game, SubStack from, CardStack to) {
        if (hasListeners()) {
            SubStackMovedEventObject eventObject = new SubStackMovedEventObject(
                    game, from, to);
            notifyListeners(eventObject);
        }
    }

    @Override
    protected void notifyListener(Game.SubStackMovedListener listener,
            SubStackMovedEventObject args) {
        listener.onSubStackMoved(args);
    }
}
