package com.josericardojunior.runnergame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

/**
 * Created by josericardo on 09/10/17.
 */

public class GameView extends SurfaceView
        implements Runnable {

    private static final int TOTAL_ENEMIES = 5;
    private static final int DESIRED_FPS = 40;
    private static final float FRAME_TIME =
            1000.0f / (float) DESIRED_FPS;

    int score = 0;
    Paint paintScore = new Paint();


    GestureDetector gestureDetector;

    Context context;
    Thread gameLoop = null;
    SurfaceHolder surfaceHolder = null;
    boolean isRunning;
    boolean gameOver = false;
    int width, height;
    Player player;
    Enemy []enemies = new Enemy[TOTAL_ENEMIES];

    public GameView(Context context, int width, int height){
        super(context);

        gestureDetector = new GestureDetector(context,
                new RunnerGestureDetector(this));

        this.context = context;
        this.width = width;
        this.height = height;
        surfaceHolder = getHolder();
        surfaceHolder.setFixedSize(width, height);
        player = new Player(height, 70);

        for (int i = 0; i < TOTAL_ENEMIES; i++){
            enemies[i] = new Enemy(50, 50, height, i);

            if (i == 0) {
                enemies[i].setPositionX(width
                        + enemies[i].getSize().x);
                continue;
            }

            enemyRespawn(enemies[i]);
        }

        paintScore.setColor(Color.BLUE);
        paintScore.setTextSize(36);
        paintScore.setAntiAlias(true);
        paintScore.setTextAlign(Paint.Align.CENTER);
    }


    public void swipeDown(){
        //Player.crouch();
    }

    public void singleTap(){
        player.jump();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.gestureDetector.onTouchEvent(event);
    }


    public void resume(){
        isRunning = true;
        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void stop(){
        isRunning = false;

        try {
            gameLoop.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void desenha(Canvas canvas){
        canvas.drawColor(Color.WHITE);
        player.desenha(canvas);

        for (int i = 0; i < TOTAL_ENEMIES; i++){
            enemies[i].desenhar(canvas);
        }


        canvas.drawText("Score: " + score, 20, 0, paintScore);
    }

    private void update(){
        player.update();
        score += 2;

        for (int i = 0; i < TOTAL_ENEMIES; i++){
            enemies[i].update();

            if (0 > enemies[i].getPositionX() +
                    enemies[i].getSize().x){
                enemyRespawn(enemies[i]);
            }
        }

        for (int i = 0; i < TOTAL_ENEMIES; i++){
            if (player.collided(enemies[i])) {
                gameOver = true;
                break;
            }
        }


    }

    private void enemyRespawn(Enemy enemy){

        Enemy last = Enemy.getLastPosition(enemies);

        int r = (int) (Math.random() * 300.0);

        int newPos = last.getPositionX() +
                enemy.getSize().x + 450 + r;

        enemy.setPositionX(newPos);
    }

    @Override
    public void run() {

        while (isRunning){

            if (!surfaceHolder.getSurface().isValid())
                continue;

            float timeDiff = FRAME_TIME;

            if (!gameOver) {
                long start = System.currentTimeMillis();
                update();

                float timeFrame = (float) (System.currentTimeMillis() - start);
                timeDiff = (FRAME_TIME - timeFrame);

                while (timeDiff < 0) {
                    update();
                    timeDiff += FRAME_TIME;
                }
            }

            Canvas canvas = surfaceHolder.lockCanvas();
            desenha(canvas);

            if (gameOver){
                Paint p = new Paint();
                p.setColor(Color.BLUE);
                p.setTextSize(43);

                canvas.drawText("Game Over!", 30, 30, p);
            }


            surfaceHolder.unlockCanvasAndPost(canvas);

            try {
                gameLoop.sleep((int)timeDiff);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
