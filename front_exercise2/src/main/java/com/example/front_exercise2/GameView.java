package com.example.front_exercise2;

import android.content.Context;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class GameView extends View {
    private Bird bird = new Bird(200, 200, getResources());


    public GameView(Context context) {
        super(context);
        new CountDownTimer(Integer.MAX_VALUE, 20) {

            @Override
            public void onFinish() {}

            @Override
            public void onTick(long millisUntilFinished) {
                bird.move();
                invalidate();
            }
        }.start();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        bird.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        bird.setXY((int)event.getX(), (int)event.getY());
        return true;
    }
}
