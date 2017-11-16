package com.josericardojunior.runnergame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by josericardo on 09/10/17.
 */

public class Player implements ICollidable {

    int x, y;
    float vspeed = 0;
    int ground;
    float gravity = 2;
    float jumpPower = -20;
    Rect rect = new Rect();
    int size = 1;

    boolean isGround;

    public Player(int ground, int size){
        x = 100;
        y = ground - size;
        isGround = true;
        this.ground = ground;
        this.size = size;
    }

    public void update(){

        if (!isGround){
            vspeed += gravity;
            y += vspeed;

            if (y >= ground - size){
                y = ground - size;
                isGround = true;
                vspeed = 0;
            }
        }

        rect.left = x;
        rect.right = rect.left + size;
        rect.top = y;
        rect.bottom = rect.top + size;
    }

    public void jump(){
        if (isGround){
            vspeed = jumpPower;
            isGround = false;
        }
    }

    public void desenha(Canvas canvas){

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawRect(rect, paint);
    }

    @Override
    public boolean collided(ICollidable other) {

        return getRect().intersect(other.getRect());
    }

    @Override
    public Rect getRect() {
        return rect;
    }
}
