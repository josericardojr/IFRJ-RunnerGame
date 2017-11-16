package com.josericardojunior.runnergame;

import android.os.Debug;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by josericardo on 13/11/17.
 */

public class RunnerGestureDetector implements GestureDetector.OnGestureListener {

    final static int SWIPE_MIN_OFFSET = 50;
    GameView gameView;

    public RunnerGestureDetector(GameView gameView){
        this.gameView = gameView;
    }

    @Override
    public boolean onFling(MotionEvent motionEvent0, MotionEvent motionEvent1, float v, float v1) {
        if ( motionEvent1.getY() - motionEvent0.getY() > SWIPE_MIN_OFFSET){
            gameView.swipeDown();
            Toast.makeText(gameView.context,
                    "Swipe Down", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        gameView.singleTap();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }
}
