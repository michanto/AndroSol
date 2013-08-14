package lib.cards.views;

import java.util.Hashtable;
import java.util.Map;

import lib.cards.models.Card;

public abstract class Deck<TTexture> extends DeckMetrics {
    protected Map<Integer, TTexture> cardTextures = new Hashtable<Integer, TTexture>();
    protected TTexture backTexture = null;
    protected TTexture blankTexture = null;

    public Deck() {
    }

    protected abstract String getBlankTextureUrl();

    protected abstract String getBackTextureUrl();

    protected abstract String cardTextureUrl(Card card);

    protected abstract TTexture loadTexture(String url);

    protected void ResetTextures() {
        cardTextures = new Hashtable<Integer, TTexture>();
        backTexture = null;
        blankTexture = null;
    }

    public TTexture getBlankTexture() {
        if (blankTexture == null) {
            blankTexture = loadTexture(getBlankTextureUrl());
        }
        return blankTexture;
    }

    public TTexture getBackTexture() {
        if (backTexture == null) {
            backTexture = loadTexture(getBackTextureUrl());
        }
        return backTexture;
    }

    public TTexture cardTexture(Card card) {
        if (!card.isFaceUp()) {
            return getBackTexture();
        }

        if (!cardTextures.containsKey(card.getIndex())) {
            cardTextures
                    .put(card.getIndex(), loadTexture(cardTextureUrl(card)));
        }
        return cardTextures.get(card.getIndex());
    }
}
