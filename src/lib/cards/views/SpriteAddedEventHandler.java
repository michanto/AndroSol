package lib.cards.views;

import lib.cards.models.EventHandler;
import lib.cards.views.GameBoard.SpriteAddedListener;

public class SpriteAddedEventHandler extends
        EventHandler<SpriteAddedListener, SpriteEventObject> {

    public void fireOnSpriteAdded(GameBoard board, Sprite sprite) {
        if (hasListeners()) {
            SpriteEventObject eventObject = new SpriteEventObject(board, sprite);
            notifyListeners(eventObject);
        }
    }

    @Override
    public void notifyListener(SpriteAddedListener listener,
            SpriteEventObject eventObject) {
        listener.onSpriteAdded(eventObject);
    }
}
