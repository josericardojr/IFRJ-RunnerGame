package com.josericardojunior.runnergame;

import android.graphics.Rect;

/**
 * Created by josericardo on 16/10/17.
 */

public interface ICollidable {
    boolean collided(ICollidable other);
    Rect getRect();
}
