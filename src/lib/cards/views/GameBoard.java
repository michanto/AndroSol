package lib.cards.views;

import java.util.EventListener;

import lib.cards.models.CardStack;
import lib.cards.models.Game;
import lib.cards.models.SubStack;

public interface GameBoard {
    Game getGame();

    public interface SpriteAddedListener extends EventListener {
        void onSpriteAdded(SpriteEventObject args);
    }

    public interface SpriteSelectedListener extends EventListener {
        void onSpriteSelected(SpriteEventObject args);
    }

    public interface SpriteDefaultActionListener extends EventListener {
        void onSpriteDefaultAction(SpriteEventObject args);
    }

    public interface SpriteRemovedListener extends EventListener {
        void onSpriteRemoved(SpriteEventObject args);
    }

    SpriteAddedEventHandler getSpriteAddedEvent();

    SpriteSelectedEventHandler getSpriteSelectedEvent();

    SpriteDefaultActionEventHandler getSpriteDefaultActionEvent();

    SpriteRemovedEventHandler getSpriteRemovedEvent();

    SubStack getSubStack(String name);

    CardStack getStack(Sprite selectedSprite);
}
