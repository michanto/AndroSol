package com.example.androsol;

import lib.cards.views.Deck;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

public abstract class AndroidDeck extends Deck<Bitmap> {

    AndroidDeck() {
    }

    private Resources resources;

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    protected abstract int getTextureId(String name);

    @Override
    protected Bitmap loadTexture(String name) {
        /* Set the options */
        Options opts = new Options();
        opts.inDither = true;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        opts.inScaled = false; /* Flag for no scaling */

        /* Load the bitmap with the options */
        return BitmapFactory
                .decodeResource(resources, getTextureId(name), opts);
    }
}
