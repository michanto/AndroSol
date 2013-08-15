package com.example.androsol;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import lib.cards.models.Card;
import lib.cards.models.CardStack;
import lib.cards.models.Game.NewGameListener;
import lib.cards.models.GameEventObject;
import lib.cards.models.GameProperties;
import lib.cards.models.GameState;
import lib.cards.utilities.Point;
import lib.cards.views.CardSprite;
import lib.cards.views.GameBoardImpl;
import lib.cards.views.Sprite;
import lib.cards.views.SpriteEventObject;
import lib.cards.views.StackSprite;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.FrameLayout;

public class AndroidGameBoard extends GameBoardImpl<Bitmap> implements
        GameSurface.Drawable {
    GameSurface surface;

    public AndroidGameBoard(FrameLayout frame) {
        this.surface = new GameSurface(this, this, frame.getContext());
        frame.addView(this.surface);
        this.getGame().getNewGameEvent().add(new NewGameListener() {

            @Override
            public void onNewGameAction(GameEventObject game) {
                draw();
            }
        });
        this.getSpriteSelectedEvent().add(new SpriteSelectedListener() {
            @Override
            public void onSpriteSelected(SpriteEventObject args) {
                draw();
            }
        });
        this.getSpriteDefaultActionEvent().add(
                new SpriteDefaultActionListener() {
                    @Override
                    public void onSpriteDefaultAction(SpriteEventObject args) {
                        draw();
                    }

                });
    }

    public void draw() {
        this.surface.draw();
    }

    // GameSurface.Drawable
    @Override
    public void draw(Canvas canvas) {
        for (Sprite s : this.getSprites()) {
            ((GameSurface.Drawable) s).draw(canvas);
        }
    }

    @Override
    protected void doMoveCardAnimation(CardStack cards, Point delta) {
        this.layoutBoard();
        this.draw();
    }

    @Override
    protected void doMoveCardAnimation(GameState oldState, GameState newState) {
        // TODO Auto-generated method stub
        this.layoutBoard();
        this.draw();
    }

    @Override
    public StackSprite addStackSprite(CardStack stack, Bitmap cardImage) {
        StackSprite sprite = new StackSpriteImpl(stack, cardImage);
        stackSprites.put(stack, sprite);
        return sprite;
    }

    @Override
    public CardSprite addCardSprite(Card card, Bitmap cardImage) {
        CardSprite sprite = new CardSpriteImpl(card, cardImage);
        cardSprites.put(card, sprite);
        return sprite;
    }

    HashMap<Card, CardSprite> cardSprites = new HashMap<Card, CardSprite>();
    HashMap<CardStack, StackSprite> stackSprites = new HashMap<CardStack, StackSprite>();

    @Override
    public void deleteSprite(Sprite sprite) {
        if (CardSprite.class.isAssignableFrom(sprite.getClass())) {
            cardSprites.remove(((CardSprite) sprite).getCard());
        } else if (StackSprite.class.isAssignableFrom(sprite.getClass())) {
            stackSprites.remove(((StackSprite) sprite).getStack());
        } else {
            throw new RuntimeException(
                    "Sprite must be either card sprite or stack sprite.");
        }
    }

    /**
     * In android, the drawing will take place elsewhere. Instead, this just
     * sets the index and position.
     */
    @Override
    protected void layoutCard(Card card, Point position, int zIndex) {
        Sprite sprite = cardSprites.get(card);
        sprite.setPosition(position);
        sprite.setZOrder(zIndex);
    }

    @Override
    public GameProperties getGameProperties() {
        return getGame().getGameProperties();
    }

    public Sprite hitTest(Point eventPoint) {
        List<Sprite> sprites = getSprites();
        Collections.reverse(sprites);
        for (Sprite s : sprites) {
            if (s.isVisibile() && s.getRect().Contains(eventPoint)) {
                return s;
            }
        }
        return null;
    }
}
