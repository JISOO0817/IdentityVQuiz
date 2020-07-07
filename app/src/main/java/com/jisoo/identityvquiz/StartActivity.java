package com.jisoo.identityvquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {


    private SoundPlayer soundPlayer = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        soundPlayer = new SoundPlayer(this);
    }

    public void startQuiz(View view){

        Spinner spinner = findViewById(R.id.quizLev);
        int quizCategory = spinner.getSelectedItemPosition();


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("QUIZ_CATEGORY",quizCategory);
        startActivity(intent);
        soundPlayer.playbtnSound();

    }

    @Override
    public void onBackPressed() {

    }
}
