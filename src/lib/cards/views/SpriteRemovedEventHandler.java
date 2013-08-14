package lib.cards.views;

import lib.cards.models.EventHandler;
import lib.cards.views.GameBoard.SpriteRemovedListener;

public class SpriteRemovedEventHandler extends
        EventHandler<SpriteRemovedListener, SpriteEventObject> {

    public void fireOnSpriteRemoved(GameBoard board, Sprite sprite) {
        if (hasListeners()) {
            SpriteEventObject eventObject = new SpriteEventObject(board, sprite);
            notifyListeners(eventObject);
        }
    }

    @Override
    public void notifyListener(SpriteRemovedListener listener,
            SpriteEventObject eventObject) {
        listener.onSpriteRemoved(eventObject);
    }
}
