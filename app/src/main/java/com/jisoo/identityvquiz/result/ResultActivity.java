package com.jisoo.identityvquiz.result;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jisoo.identityvquiz.R;
import com.jisoo.identityvquiz.SoundPlayer;
import com.jisoo.identityvquiz.databinding.ActivityResultBinding;
import com.jisoo.identityvquiz.start.StartActivity;

public class ResultActivity extends AppCompatActivity {

    private SoundPlayer soundPlayer = null;
    private ActivityResultBinding binding;
    private ResultViewModel viewModel;
    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();
        initBinding();

        binding.goHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this,StartActivity.class));
                finish();
            }
        });

//        TextView resultLabel = (TextView)findViewById(R.id.scoreTV);


        int score = getIntent().getIntExtra("RIGHT_ANSWER_COUNT",0);


        SharedPreferences settings = getSharedPreferences("quizApp", Context.MODE_PRIVATE);
        int totalScore = settings.getInt("totalScore",0);
        totalScore += score;

        binding.scoreTV.setText(score + " /10");


        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("totalScore",totalScore);
        editor.apply();


        soundPlayer = new SoundPlayer(this);
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_result);
        binding.setLifecycleOwner(this);
        binding.setResult(viewModel);
    }

    private void initViewModel() {
        if(viewModelFactory == null){
            viewModelFactory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        }
        viewModel = new ViewModelProvider(this,viewModelFactory).get(ResultViewModel.class);
    }


//    public void returnTop(View view){
//        startActivity(new Intent(getApplicationContext(), StartActivity.class));
//
//        soundPlayer.playbtnSound();
//    }

    @Override
    public void onBackPressed() {

    }
}
