package com.jisoo.identityvquiz.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jisoo.identityvquiz.R;
import com.jisoo.identityvquiz.SoundPlayer;
import com.jisoo.identityvquiz.databinding.ActivityMainBinding;
import com.jisoo.identityvquiz.databinding.ActivityStartBinding;
import com.jisoo.identityvquiz.main.MainActivity;

public class StartActivity extends AppCompatActivity {


    private ActivityStartBinding binding;
    private StartViewModel viewModel;
    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;
    private SoundPlayer soundPlayer = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();
        initBinding();


        soundPlayer = new SoundPlayer(this);
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start);
        binding.setLifecycleOwner(this);
        binding.setStart(viewModel);

    }

    private void initViewModel() {

        if(viewModelFactory == null){
            viewModelFactory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        }
        viewModel = new ViewModelProvider(this,viewModelFactory).get(StartViewModel.class);
        
    }

    public void startQuiz(View view){

//        Spinner spinner = findViewById(R.id.spinner);
        int quizCategory = binding.spinner.getSelectedItemPosition();


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("QUIZ_CATEGORY",quizCategory);
        startActivity(intent);
        soundPlayer.playbtnSound();

    }

    @Override
    public void onBackPressed() {

    }
}
