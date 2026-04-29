package com.example.front_exercise2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.LinkedList;

public class Enemy extends Unit {
    private Bitmap bird = null;
    private LinkedList<Rect> list = new LinkedList<>();
    private Rect dest;

    public Enemy(int x, int y, Resources resources){
        this.x = x;
        this.y = y;
        bird = BitmapFactory.decodeResource(resources, R.drawable.red_twitter);
        list.add(new Rect(0 * bird.getHeight(), 0, 1 * bird.getHeight(), bird.getHeight()));
        list.add(new Rect(1 * bird.getHeight(), 0, 2 * bird.getHeight(), bird.getHeight()));
        list.add(new Rect(2 * bird.getHeight(), 0, 3 * bird.getHeight(), bird.getHeight()));
        list.add(new Rect(3 * bird.getHeight(), 0, 4 * bird.getHeight(), bird.getHeight()));
        dest = new Rect(x, y, x + bird.getHeight(), y + bird.getHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bird, list.getFirst(), dest, new Paint());
        list.addLast(list.getFirst());
        list.removeFirst();
    }

    @Override
    public void move() {
        dest.left -= 30;
        dest.right -= 30;
    }

    public int getCenterX(){
        return dest.centerX();
    }

    public int getCenterY(){
        return dest.centerY();
    }

    @Override
    public int getRadius() {
        return (dest.right - dest.left) / 2;
    }


}
