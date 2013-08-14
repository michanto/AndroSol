package lib.cards.views;

import lib.cards.models.EventHandler;
import lib.cards.views.GameBoard.SpriteSelectedListener;

public class SpriteSelectedEventHandler extends
        EventHandler<SpriteSelectedListener, SpriteEventObject> {

    public void fireOnSpriteSelected(GameBoard board, Sprite sprite) {
        if (hasListeners()) {
            SpriteEventObject eventObject = new SpriteEventObject(board, sprite);
            notifyListeners(eventObject);
        }
    }

    @Override
    public void notifyListener(SpriteSelectedListener listener,
            SpriteEventObject eventObject) {
        listener.onSpriteSelected(eventObject);
    }
}
