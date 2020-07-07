package com.jisoo.identityvquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private SoundPlayer soundPlayer = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView resultLabel = (TextView)findViewById(R.id.resultLabel);


        int score = getIntent().getIntExtra("RIGHT_ANSWER_COUNT",0);


        SharedPreferences settings = getSharedPreferences("quizApp", Context.MODE_PRIVATE);
        int totalScore = settings.getInt("totalScore",0);
        totalScore += score;

        resultLabel.setText(score + " /10");


        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("totalScore",totalScore);
        editor.apply();


        soundPlayer = new SoundPlayer(this);
    }

    public void returnTop(View view){
        startActivity(new Intent(getApplicationContext(),StartActivity.class));

        soundPlayer.playbtnSound();
    }

    @Override
    public void onBackPressed() {

    }
}
