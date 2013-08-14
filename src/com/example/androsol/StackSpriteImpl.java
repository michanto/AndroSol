package com.example.androsol;

import lib.cards.models.CardStack;
import lib.cards.views.StackSprite;
import android.graphics.Bitmap;

public class StackSpriteImpl extends SpriteImpl implements StackSprite {
    StackSpriteImpl(CardStack stack, Bitmap bitmap) {
        super(bitmap);
        this.stack = stack;
    }

    CardStack stack;

    @Override
    public CardStack getStack() {
        return stack;
    }
}
