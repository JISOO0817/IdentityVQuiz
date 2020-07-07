package com.jisoo.identityvquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class QuizDatabaseHelper extends SQLiteOpenHelper {

    static final private String DBNAME = "identity.sqlite";
    static final private int VERSION = 1;

    String quizData[][] = {

            //1. 쉬움 2. 보통 3.어려움

            {"제 5인격의 본 섭은?", "중국", "일본", "미국", "한국", "1"},
            {"정원사의 이름은?", "엠마 우즈", "엄마 우즈", "엠마 오즈", "엔마 우즈", "1"},
            {"일반 게임에서 생존자가 돌려야 하는 총 해독기의 개수는?", "5개", "3개", "4개", "8개", "1"},
            {"선지자의 이름은?", "일라이 클락", "빅터 글랜즈", "루카 바르세이", "프레디 라일리", "1"},
            {"해독 속도가 가장 빠른 캐릭터는?", "맹인", "샤먼", "정원사", "변호사", "1"},
            {"감시자가 아닌 캐릭터는?", "주술사", "사진사", "광대", "공장장", "1"},
            {"치료 속도가 가장 느린 생존자는?", "조향사", "우편배달부", "선지자", "마술사", "1"},
            {"구출 속도가 가장 오래 걸리는 생존자는?", "마술사", "야만인", "변호사", "용병", "1"},
            {"해독 속도가 가장 느린 캐릭터는?", "야만인", "마술사", "선지자", "납관사", "1"},
            {"지하통로로 나갈 수 있는 생존자의 수는?", "1명", "2명", "3명", "4명", "1"},
            {"게임 시작시 샤먼이 가지고 있는 포탈의 개수는?", "2개", "1개", "3개", "5개", "2"},
            {"생존자가 연합에서 살 수 있는 물약이 아닌 것은?", "강력제", "집중제", "행운제", "가속제", "2"},
            {"제 5인격의 맵이 아닌 것은?", "에버슬리핑 다운", "군수 공장", "레오의 기억", "달빛강 공원", "2"},
            {"상자깡으로 얻을 수 없는 아이템은?", "손전등", "주사기", "공", "향수", "2"},
            {"감시자의 존재감 1단계는 몇 대를 맞아야 차는가?", "2대", "1대", "3대", "4대", "2"},
            {"일반 게임 생존자 봇전은 몇 판 지면 나오는가?", "2판", "1판", "3판", "랜덤", "2"},
            {"광산맵의 엘리베이터는 최대 몇 명이 탑승할 수 있는가?", "2명", "1명", "3명", "4명", "2"},
            {"연합사냥 모드에서 사용할 수 없는 감시자는?", "꿈의 마녀", "발크", "울보", "마리", "2"},
            {"지하통로는 해독기를 몇 개 돌려야 열리는가?", "2개", "1개", "3개", "전부", "2"},
            {"특징이 다른 감시자는?", "미치코", "리퍼", "울보", "거미", "2"},
            {"모험가의 생일은?", "3월 1일", "3월 2일", "3월 3일", "3월 4일", "3"},
            {"행운아의 생일은?", "11월 22일", "11월 11일", "11월 20일", "10월 22일", "3"},
            {"무희의 생일은?", "6월 9일", "6월 8일", "6월 7일", "6월 10일", "3"},
            {"광산맵의 엘리베이터 쿨타임은 몇 초인가?", "30초", "15초", "20초", "25초", "3"},
            {"포워드 공 사용 후의 쿨타임은?", "5초", "3초", "8초", "6초", "3"},
            {"바텐더의 술을 마실 수 없는 생존자는?", "공군", "의사", "정원사", "조향사", "3"},
            {"카우보이의 밧줄로 풍선구출 할 수 있는 최대 횟수는?", "3회", "1회", "2회", "5회", "3"},
            {"까마귀를 뜨지 않게 하는 방법 중 틀린 것은?", "걷기", "자가 치료", "콘솔 해킹", "해독", "3"},
            {"생존자 인격중 '강박증' 은 주변 몇 m 이내의 상자를 알려주는가?", "16m", "14m", "15m", "18m", "3"},
            {"기계 숙달 인격을 찍으면 해독 성공 범위가 몇 % 증가 하는가?", "10%", "3%", "5%", "8%", "3"}

    };



    public QuizDatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  quiz");
        //테이블 생성
        //테이블 이름: quiz

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS quiz(" +
              "question TEXT PRIMARY KEY, answer TEXT, choice1 TEXT," +
              "choice2 TEXT, choice3 TEXT, category TEXT)");



        //Insert


        sqLiteDatabase.beginTransaction();
        try{

            SQLiteStatement sql = sqLiteDatabase.compileStatement(
                    "INSERT INTO quiz(question, answer, choice1, choice2, choice3, category)" +
                                    "VALUES(?, ?, ?, ?, ?, ?)"


            );


            for(int i=0; i<quizData.length; i++){
                sql.bindString(1,quizData[i][0]);
                sql.bindString(2,quizData[i][1]);
                sql.bindString(3,quizData[i][2]);
                sql.bindString(4,quizData[i][3]);
                sql.bindString(5,quizData[i][4]);
                sql.bindString(6,quizData[i][5]);


                sql.executeInsert();

            }

            sqLiteDatabase.setTransactionSuccessful();

        }catch(SQLiteException e){
            e.printStackTrace();
        }finally {
            sqLiteDatabase.endTransaction();
        }




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
