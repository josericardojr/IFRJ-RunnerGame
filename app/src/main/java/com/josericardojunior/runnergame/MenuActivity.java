package com.josericardojunior.runnergame;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity
        implements View.OnClickListener {

    ImageButton btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        btnPlay = (ImageButton)
                findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == btnPlay){
            Intent intent = new Intent(this,
                    GameActivity.class);
            startActivity(intent);
        }
    }
}
