package com.example.front_exercise3;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameField extends SurfaceView implements SurfaceHolder.Callback {

    private Random random = new Random();
    private Bird bird;
    private Enemy enemy = new Enemy(2400, random.nextInt(800), getResources());
    private Context context;
    private int score;
    private DrawThread drawThread;

    class DrawThread extends Thread {
        private boolean runFlag = false;
        private SurfaceHolder surfaceHolder;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean run) {
            runFlag = run;
        }

        @Override
        public void run() {
            Canvas canvas;
            while (runFlag) {
                if (enemy == null) {
                    enemy = new Enemy(3000, random.nextInt(800), getResources());
                }
                if (enemy.getCenterX() < -200) {
                    enemy = new Enemy(3000, random.nextInt(800), getResources());
                }
                if (bird != null && bird.intersect(enemy)) {
                    enemy = new Enemy(3000, random.nextInt(800), getResources());
                    score++;
                }
                if (bird != null) bird.move();
                enemy.move();

                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    synchronized (surfaceHolder) {
                        canvas.drawColor(Color.WHITE);
                        if (bird != null) bird.draw(canvas);
                        enemy.draw(canvas);
                    }
                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }

                try { Thread.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }

    public GameField(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.setRunning(false);
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (bird != null) bird.setXY((int) event.getX(), (int) event.getY());
        return true;
    }

    public void pause() {
        SharedPreferences sp = context.getSharedPreferences("BIRD", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (bird != null) {
            editor.putInt("BirdX", bird.getCenterX());
            editor.putInt("BirdY", bird.getCenterY());
            editor.apply();
        }
        drawThread.setRunning(false);
    }

    public void resume() {
        SharedPreferences sp = context.getSharedPreferences("BIRD", Context.MODE_PRIVATE);
        bird = new Bird(sp.getInt("BirdX", 300), sp.getInt("BirdY", 300), getResources());
        bird.setXY(sp.getInt("BirdX", 300), sp.getInt("BirdY", 300));
    }

    public int getScore() {
        return score;
    }
}