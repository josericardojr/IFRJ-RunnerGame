package com.josericardojunior.runnergame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by josericardo on 09/10/17.
 */

public class Enemy implements ICollidable {

    private static final float VELOCITY = 20;

    int id = 0;

    Point size = new Point();
    int ground;
    Rect rect = new Rect();
    Paint paint = new Paint();

    public void setPositionX(int x){
        rect.left = x;
        rect.right = rect.left + size.x;
    }


    public void setPositionY(int y){
        rect.top = y;
        rect.bottom = rect.top + size.y;
    }

    public int getPositionX(){
        return rect.left;
    }

    public Point getSize(){
        return size;
    }

    public static Enemy getLastPosition(Enemy []enemies){

        Enemy last = null;
        int x = 0;

        for (int i = 0; i < enemies.length; i++){
            if (enemies[i] != null &&  enemies[i].rect.left > x){
                x = enemies[i].rect.left;
                last = enemies[i];
            }

        }

        return last;
    }

    public Enemy(int w, int h, int ground, int id){
        this.size.x = w;
        this.size.y = h;
        rect.top = ground - h;
        paint.setColor(Color.GREEN);
        this.id = id;
    }


    public void update(){
        rect.left -= VELOCITY;
        rect.right = rect.left + size.x;
        rect.bottom = rect.top + size.y;
    }

    public void desenhar(Canvas canvas){

        canvas.drawRect(rect, paint);
    }

    @Override
    public Rect getRect() {
        return rect;
    }

    @Override
    public boolean collided(ICollidable other) {

        return getRect().intersect(other.getRect());
    }
}
