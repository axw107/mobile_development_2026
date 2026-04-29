package com.example.front_exercise2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Random;

public class GameField extends View {
    private Random random = new Random();
    private Bird bird;
    private Enemy enemy = enemy = new Enemy(2400, random.nextInt(800), getResources());;
    private Context context;
    private int score;

    class GameTimer extends CountDownTimer{
        public GameTimer(long countDownInterval) {
            super(Integer.MAX_VALUE, countDownInterval);
        }

        @Override
        public void onFinish() {}

        @Override
        public void onTick(long millisUntilFinished) {
            if (enemy == null) {
                enemy = new Enemy(2400, random.nextInt(800), getResources());
            }
            if (enemy.getCenterX() < -200){
                enemy = new Enemy(2400, random.nextInt(800), getResources());
            }
            if (bird.intersect(enemy)){
                enemy = new Enemy(2400, random.nextInt(800), getResources());
                ++score;
            }
            bird.move();
            enemy.move();
            invalidate();
        }
    }

    public GameField(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        new GameTimer(20).start();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        bird.draw(canvas);
        enemy.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        bird.setXY((int)event.getX(), (int)event.getY());
        return true;
    }

    public void pause(){
        SharedPreferences sp = context.getSharedPreferences("BIRD", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("BirdX", bird.getCenterX());
        editor.putInt("BirdY", bird.getCenterY());
        editor.commit();
    }

    public void resume(){
        SharedPreferences sp = context.getSharedPreferences("BIRD", Context.MODE_PRIVATE);
        bird = new Bird(sp.getInt("BirdX", 300), sp.getInt("BirdY", 300), getResources());
        bird.setXY(sp.getInt("BirdX", 300), sp.getInt("BirdY", 300));
    }

    public int getScore(){
        return score;
    }
}
