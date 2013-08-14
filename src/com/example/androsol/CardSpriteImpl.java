package com.example.androsol;

import lib.cards.models.Card;
import lib.cards.views.CardSprite;
import android.graphics.Bitmap;

public class CardSpriteImpl extends SpriteImpl implements CardSprite {
    CardSpriteImpl(Card card, Bitmap bitmap) {
        super(bitmap);
        this.card = card;
    }

    Card card;

    @Override
    public Card getCard() {
        return card;
    }

}
