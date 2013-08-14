package com.example.androsol;

import lib.cards.utilities.Point;
import lib.cards.utilities.Size;
import lib.cards.views.Sprite;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class SpriteImpl implements Sprite, GameSurface.Drawable {
    private String name;
    private boolean highlight;
    private boolean softHighlight;
    private Point position;
    private Size size;
    private int zOrder;
    private boolean visibility = true;

    public SpriteImpl(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private Bitmap bitmap;

    public void draw(Canvas canvas) {
        if (!getVisibility()) {
            return;
        }
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Style.FILL);
        canvas.drawBitmap(bitmap, (float) position.x, (float) position.y, paint);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String value) {
        name = value;
    }

    @Override
    public boolean getHighlight() {
        return highlight;
    }

    @Override
    public void setHighlight(boolean value) {
        highlight = value;
    }

    @Override
    public boolean getSoftHighlight() {
        return softHighlight;
    }

    @Override
    public void setSoftHighlight(boolean value) {
        softHighlight = value;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point value) {
        position = value;
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Override
    public void setSize(Size value) {
        size = value;
    }

    @Override
    public int getZOrder() {
        return zOrder;
    }

    @Override
    public void setZOrder(int value) {
        zOrder = value;
    }

    @Override
    public boolean getVisibility() {
        return visibility;
    }

    @Override
    public void setVisibility(boolean value) {
        visibility = value;
    }
}
