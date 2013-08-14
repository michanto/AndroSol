package com.example.androsol;

import java.util.HashMap;
import java.util.Locale;

import lib.cards.models.Card;
import lib.cards.utilities.Size;

public class StandardDeck extends AndroidDeck {

    @SuppressWarnings("serial")
    private static final HashMap<String, Integer> textures = new HashMap<String, Integer>() {
        {
            put("s1", R.drawable.standard_s1);
            put("s2", R.drawable.standard_s2);
            put("s3", R.drawable.standard_s3);
            put("s4", R.drawable.standard_s4);
            put("s5", R.drawable.standard_s5);
            put("s6", R.drawable.standard_s6);
            put("s7", R.drawable.standard_s7);
            put("s8", R.drawable.standard_s8);
            put("s9", R.drawable.standard_s9);
            put("s10", R.drawable.standard_s10);
            put("sj", R.drawable.standard_sj);
            put("sk", R.drawable.standard_sk);
            put("sq", R.drawable.standard_sq);

            put("d1", R.drawable.standard_d1);
            put("d2", R.drawable.standard_d2);
            put("d3", R.drawable.standard_d3);
            put("d4", R.drawable.standard_d4);
            put("d5", R.drawable.standard_d5);
            put("d6", R.drawable.standard_d6);
            put("d7", R.drawable.standard_d7);
            put("d8", R.drawable.standard_d8);
            put("d9", R.drawable.standard_d9);
            put("d10", R.drawable.standard_d10);
            put("dj", R.drawable.standard_dj);
            put("dk", R.drawable.standard_dk);
            put("dq", R.drawable.standard_dq);

            put("h1", R.drawable.standard_h1);
            put("h2", R.drawable.standard_h2);
            put("h3", R.drawable.standard_h3);
            put("h4", R.drawable.standard_h4);
            put("h5", R.drawable.standard_h5);
            put("h6", R.drawable.standard_h6);
            put("h7", R.drawable.standard_h7);
            put("h8", R.drawable.standard_h8);
            put("h9", R.drawable.standard_h9);
            put("h10", R.drawable.standard_h10);
            put("hj", R.drawable.standard_hj);
            put("hk", R.drawable.standard_hk);
            put("hq", R.drawable.standard_hq);

            put("c1", R.drawable.standard_c1);
            put("c2", R.drawable.standard_c2);
            put("c3", R.drawable.standard_c3);
            put("c4", R.drawable.standard_c4);
            put("c5", R.drawable.standard_c5);
            put("c6", R.drawable.standard_c6);
            put("c7", R.drawable.standard_c7);
            put("c8", R.drawable.standard_c8);
            put("c9", R.drawable.standard_c9);
            put("c10", R.drawable.standard_c10);
            put("cj", R.drawable.standard_cj);
            put("ck", R.drawable.standard_ck);
            put("cq", R.drawable.standard_cq);

            put("blank", R.drawable.standard_blank);
            put("b2fv", R.drawable.standard_b2fv);
        }
    };

    public StandardDeck() {
        this.setCardSize(new Size(71, 96));
    }

    @Override
    protected String getBlankTextureUrl() {
        return "blank";
    }

    @Override
    protected String getBackTextureUrl() {
        return "b2fv";
    }

    @Override
    protected String cardTextureUrl(Card card) {
        String suitPart = card.getSuit().toString().substring(0, 1)
                .toLowerCase(Locale.getDefault());
        String valuePart = (card.getValue().toInt()).toString();

        switch (card.getValue()) {
        case JACK:
        case QUEEN:
        case KING:
            valuePart = card.getValue().toString().substring(0, 1)
                    .toLowerCase(Locale.getDefault());
            break;
        default:
        }

        return suitPart + valuePart;
    }

    @Override
    protected int getTextureId(String name) {
        return textures.get(name);
    }

}
