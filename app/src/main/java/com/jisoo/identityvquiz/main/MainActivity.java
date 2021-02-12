package com.jisoo.identityvquiz.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jisoo.identityvquiz.QuizDatabaseHelper;
import com.jisoo.identityvquiz.R;
import com.jisoo.identityvquiz.databinding.ActivityMainBinding;
import com.jisoo.identityvquiz.result.ResultActivity;
import com.jisoo.identityvquiz.SoundPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;
    private TextView countLabel, questionLabel;
    private Button answerBtn1, answerBtn2, answerBtn3, answerBtn4;
    private MediaPlayer mp;

    private String rightAnswer;
    private int rightAnswerCount = 0;
    private int quizCount = 1;
    static final private int QUIZ_COUNT = 10;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    private QuizDatabaseHelper dbHelper = null;
    private SoundPlayer soundPlayer = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();
        initBinding();
        initView();
//        setContentView(R.layout.activity_main);

        soundPlayer = new SoundPlayer(this);

        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        answerBtn4 = findViewById(R.id.answerBtn4);
        answerBtn1 = findViewById(R.id.answerBtn1);
        answerBtn2 = findViewById(R.id.answerBtn2);
        answerBtn3 = findViewById(R.id.answerBtn3);

        // Receive quizCategory from StartActivity.
        int quizCategory = getIntent().getIntExtra("QUIZ_CATEGORY", 0);


        dbHelper = new QuizDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //쿼리 조회

        String table = "quiz";
        String[] columns = {"*"}; //all
        String selection = "category = ?"; //select by category
        String[] selectionArgs = {String.valueOf(quizCategory)};
        String orderBy = "RANDOM()";
        String limit = String.valueOf(QUIZ_COUNT);

        Cursor cursor;

        if( quizCategory != 0){

            cursor = db.query(table, columns, selection, selectionArgs, null, null, orderBy, limit);
        }else{
            cursor = db.query(table, columns, null, null, null, null, orderBy, limit);
        }


        //퀴즈 어레이 생성

        while(cursor.moveToNext()){
            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(cursor.getString(0));
            tmpArray.add(cursor.getString(1));
            tmpArray.add(cursor.getString(2));
            tmpArray.add(cursor.getString(3));
            tmpArray.add(cursor.getString(4));


            quizArray.add(tmpArray);
        }

        // Create quizArray from quizData.
   /*     for (int i = 0; i < quizData.length; i++) {

            // Prepare array.
            ArrayList<String> tmpArray = new ArrayList<>();
            tmpArray.add(quizData[i][0]); // Country
            tmpArray.add(quizData[i][1]); // Right Answer
            tmpArray.add(quizData[i][2]); // Choice1
            tmpArray.add(quizData[i][3]); // Choice2
            tmpArray.add(quizData[i][4]); // Choice3

            // Add tmpArray to quizArray.
            quizArray.add(tmpArray);
        }
*/
        showNextQuiz();


        ImageView closeBtn = findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        Glide.with(getApplicationContext()).load(R.drawable.ximage).into(binding.closeBtn);
    }

    private void initBinding() {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setMain(viewModel);
    }

    private void initViewModel() {
        if(viewModelFactory == null){
            viewModelFactory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        }

        viewModel = new ViewModelProvider(this,viewModelFactory).get(MainViewModel.class);
    }

    public void showNextQuiz() {

        // 업데이트 quizCountLabel.
        countLabel.setText("Q" + quizCount);

        // 0 ~ 14 사이에서 랜덤으로

        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        // Pick
        ArrayList<String> quiz = quizArray.get(randomNum);

        // Set questiona and right answer.
        // Array format: {"Country", "Right Answer", "Choice1", "Choice2", "Choice3"}
        questionLabel.setText(quiz.get(0));
        rightAnswer = quiz.get(1);

        // Remove "Country" from quiz and Shuffle choices.
        quiz.remove(0);
        Collections.shuffle(quiz);

        // 선택 set
        answerBtn1.setText(quiz.get(0));
        answerBtn2.setText(quiz.get(1));
        answerBtn3.setText(quiz.get(2));
        answerBtn4.setText(quiz.get(3));


        // 퀴즈 어레이에서 퀴즈 제거
        quizArray.remove(randomNum);
    }

    public void checkAnswer(View view) {


        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;

        if (btnText.equals(rightAnswer)) {
            // Correct
           // alertTitle = "정답입니다!";
            rightAnswerCount++;
            soundPlayer.playCorrectSound();

        } else {
           // alertTitle = "오답이에요..";
            soundPlayer.playWrongSound();
        }

        // Create AlertDialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle(alertTitle);
        builder.setMessage("정답 : " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizCount == QUIZ_COUNT) {

                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);



                } else {
                    quizCount++;
                    showNextQuiz();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onBackPressed() {

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mp.start();
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mp.release();
//        mp = null;
//    }

    /*   @Override
    protected void onPause() {
        super.onPause();
        soundPlayer = null;

    }*/
   /*  String quizData[][] = {

            {"제 5인격의 본 섭은?", "중국", "일본", "미국","한국"},
            {"정원사의 이름은?", "엠마 오즈", "엄마 우즈", "엠마 우즈"," 엔마 우즈"},
            {"선지자의 이름은?", "일라이 클락", "빅터 글랜즈", "루카 바르세이", "프레디 라일리"},
            {"해독 속도가 가장 빠른 캐릭터는?", "맹인", "샤먼", "정원사"," 변호사"},
            {"치료 속도가 가장 느린 캐릭터는?", "조향사", "우편배달부", "선지자"," 공군"},
            {"모험가의 생일은 ?", "3월 1일", "3월 2일", "3월 3일"," 3월 4일"},
            {" 감시자가 아닌 캐릭터는 ?", "주술사", "사진사", "광대","공장장"},
            {"상자깡으로 나오지 않는 아이템은?", "손전등", "주사기", "공"," 향수"},
            {"일반 게임에서 생존자가 돌려야 하는 해독기의 총 개수는?", "4개", "5개", "6개"," 7개"},
            {"게임 시작시 샤먼이 가지고 있는 포탈의 개수는?", "2개", "1개", "3개"," 무제한"},
            {"죄수가 한 번에 연결할 수 있는 최대 해독기 개수는?", "2개", "1개", "3개"," 4개"},
            {"생존자가 연합에서 살 수 있는 물약이 아닌 것은?", "강력제", "집중제", "가속제"," 행운제"},
            {"행운아의 생일은 ?", "11월 22일", "11월 11일", "11월 20일","10월 22일"},
            {"무희(마가레타)의 생일은 ?", "6월9일", "6월8일", "6월7일"," 6월10일"},
            {"제 5인격의 맵이 아닌 것은?", "네버슬리핑 타운", "군수 공장", "레오의 기억"," 달빛강 공원"}
    };
*/

}
