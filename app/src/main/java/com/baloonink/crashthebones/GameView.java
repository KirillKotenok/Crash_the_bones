package com.baloonink.crashthebones;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private List<Sprite> skeleton_list;
    private Sprite sprite;
    private long start_time = System.currentTimeMillis();
    private Paint paint;
    private boolean is_hero_killed = false;
    private GameThread gameThread;
    private Timer timer;
    private SpriteFactory spriteFactory;
    private float touch_x;
    private float touch_y;
    private MainActivity mainActivity;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameThread = new GameThread(this);
        mainActivity = (MainActivity)context;

        paint = new Paint();
        spriteFactory = new SpriteFactory();
        sprite = new Sprite(this, scale_bitmap(R.drawable.skeleton));
        skeleton_list = spriteFactory.getAnotherSprite(sprite, 2);
        sprite = new Sprite(this, scale_bitmap(R.drawable.knight));
        sprite.setX_speed(28);
        sprite.setY_speed(28);
        timer = new Timer();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameThread.setRun(true);
                gameThread.start();
            }
        }, 100);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touch_x = event.getRawX();
        touch_y = event.getRawY();
        return super.onTouchEvent(event);
    }

    protected void myDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Alive: " + skeleton_list.size(), canvas.getWidth()/2, 40, paint);
        if (skeleton_list.size()==0){
            mainActivity.startEndActivity((System.currentTimeMillis() - start_time), is_hero_killed);
            gameThread.setRun(false);
        }
        for (int i = 0; i < skeleton_list.size(); i++) {
            if (touch_x < skeleton_list.get(i).getX() + skeleton_list.get(i).getBitmap().getWidth()
                    && touch_x > skeleton_list.get(i).getX()
                    && touch_y > skeleton_list.get(i).getY()
                    && touch_y < skeleton_list.get(i).getY() + skeleton_list.get(i).getBitmap().getHeight()) {
                skeleton_list.get(i).setBitmap(scale_bitmap(R.drawable.explosion));
                skeleton_list.get(i).drawSprite(canvas);
                skeleton_list.remove(skeleton_list.get(i));
                touch_y = -10;
                touch_x = -10;
            } else {
                skeleton_list.get(i).drawSprite(canvas);
            }
        }
        if (touch_x < sprite.getX() + sprite.getBitmap().getWidth()
                && touch_x > sprite.getX()
                && touch_y > sprite.getY()
                && touch_y < sprite.getY() + sprite.getBitmap().getHeight()) {
            //GAME OVER
            is_hero_killed = true;
            mainActivity.startEndActivity((System.currentTimeMillis() - start_time), is_hero_killed);
            gameThread.setRun(false);
        }
        sprite.drawSprite(canvas);

    }

    private Bitmap scale_bitmap(Integer img_id) {
        Bitmap buff = BitmapFactory.decodeResource(getResources(), img_id);
        Bitmap finalBitmap = Bitmap.createScaledBitmap(buff, 130, 130, false);
        return finalBitmap;
    }
}
