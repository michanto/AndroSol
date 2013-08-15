package com.example.androsol;

import lib.cards.utilities.Point;
import lib.cards.views.Sprite;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView {
    public interface Drawable {
        void draw(Canvas canvas);
    }

    private SurfaceHolder surfaceHolder;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Drawable callback;

    AndroidGameBoard board;

    public GameSurface(Context context) {
        super(context);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                GameSurface.this.surfaceDestroyed(holder);
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                GameSurface.this.surfaceCreated(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                    int width, int height) {
                GameSurface.this.surfaceChanged(holder, format, width, height);
            }
        });
        paint.setColor(Color.BLUE);
        paint.setStyle(Style.FILL);
    }

    public GameSurface(Drawable callback, AndroidGameBoard board,
            Context context) {
        this(context);
        this.board = board;
        this.callback = callback;
    }

    public void draw() {
        Canvas canvas = surfaceHolder.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.BLACK);
            callback.draw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        draw();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public static final String TAG = GameSurface.class.getName();

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Sprite sprite = board
                    .hitTest(new Point(event.getX(), event.getY()));

            if (sprite != null) {
                board.doSpriteSelected(sprite);
            }
            if (event.getY() > getHeight() - 50) {

                // thread.setRunning(false);
                ((Activity) getContext()).finish();
            } else {
                String name = "null";
                if (sprite != null) {
                    name = sprite.getName();
                }
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY()
                        + "Sprite=" + name);
            }
        }
        return super.onTouchEvent(event);
    }
}
