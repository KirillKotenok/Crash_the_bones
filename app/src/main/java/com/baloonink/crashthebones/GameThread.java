package com.baloonink.crashthebones;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread{

    private GameView view;
    private boolean is_running = false;

    public GameThread (GameView view){
        this.view = view;
    }

    @Override
    public void run() {
        while(is_running) {
            SurfaceHolder holder = view.getHolder();
            if (holder.getSurface().isValid()) {
                Canvas canvas = holder.lockCanvas();
                view.myDraw(canvas);
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void setRun(boolean run){
        this.is_running = run;
    }
}
