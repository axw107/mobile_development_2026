package com.example.front_exercise2;

import android.graphics.Canvas;

public abstract class Unit {
    protected int x, y;
    public abstract void draw(Canvas canvas);
    public abstract void move();

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    public abstract int getCenterX();

    public abstract int getCenterY();

    public abstract int getRadius();

    public boolean intersect(Unit unit) {
        int dx = (int)Math.abs(this.getCenterX() - unit.getCenterX());
        int dy = (int)Math.abs(this.getCenterY() - unit.getCenterY());
        int l = (int)Math.sqrt(dx*dx+dy*dy);
        if (l < this.getRadius() + unit.getRadius()){
            return true;
        } else{
            return false;
        }

    }
}
