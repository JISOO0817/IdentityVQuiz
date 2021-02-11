package com.jisoo.identityvquiz.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import com.jisoo.identityvquiz.R;
import com.jisoo.identityvquiz.start.StartActivity;

public class SplashActivity extends AppCompatActivity {


    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mp= MediaPlayer.create(this,R.raw.backg);
        mp.setLooping(true);
        mp.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplication(), StartActivity.class));

                SplashActivity.this.finish();
            }
        },3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp.pause();
    }
}
