package com.baloonink.crashthebones;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

import lombok.Data;

@Data
public class Sprite {
    private int x = 0;
    private int y = 0;
    private int x_speed;
    private int y_speed;
    private Bitmap bitmap;

    private GameView gameView;

    public Sprite(GameView gameView, Bitmap bitmap) {
        this.gameView = gameView;
        this.bitmap = bitmap;
        Random random = new Random();

        x = random.nextInt(200)+1;
        y = random.nextInt(300)+80;
        x_speed = random.nextInt(20)+1;
        y_speed = random.nextInt(20)+1;
    }

    private void updateSprite(){
        if (x < 0 || x + bitmap.getWidth() >= gameView.getWidth()) {
            x_speed *= -1;
        }
        x = x + x_speed;

        if (y < 50 || y + bitmap.getHeight() >= gameView.getHeight()+50) {
            y_speed *= -1;
        }
        y = y + y_speed;
    }

    public void drawSprite(Canvas canvas){
        updateSprite();
        canvas.drawBitmap(bitmap, x, y, null);
    }
}
