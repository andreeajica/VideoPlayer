package com.dizertatie.videoplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dizertatie.videoplayer.R;

public class SplashActivity extends AppCompatActivity {

    private int DELAY = 2000;

    //prima activitate a aplicatiei
    //porneste automat MainActivity dupa DELAY secunde
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                SplashActivity.this.finish();
            }
        }, DELAY);

    }


}
