package lib.cards.views;

import lib.cards.models.EventHandler;
import lib.cards.views.GameBoard.SpriteDefaultActionListener;

public class SpriteDefaultActionEventHandler extends
        EventHandler<SpriteDefaultActionListener, SpriteEventObject> {

    public void fireOnSpriteDefaultAction(GameBoard board, Sprite sprite) {
        if (hasListeners()) {
            SpriteEventObject eventObject = new SpriteEventObject(board, sprite);
            notifyListeners(eventObject);
        }
    }

    @Override
    public void notifyListener(SpriteDefaultActionListener listener,
            SpriteEventObject eventObject) {
        listener.onSpriteDefaultAction(eventObject);
    }
}
