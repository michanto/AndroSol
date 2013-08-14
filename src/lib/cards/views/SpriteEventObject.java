package lib.cards.views;

import java.util.EventObject;

@SuppressWarnings("serial")
public class SpriteEventObject extends EventObject {
    SpriteEventObject(GameBoard source, Sprite sprite) {
        super(source);
        this.sprite = sprite;
    }

    public GameBoard getGameBoard() {
        return (GameBoard) getSource();
    }

    private Sprite sprite;

    public Sprite getSprite() {
        return sprite;
    }
}
