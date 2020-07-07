package com.jisoo.identityvquiz;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer  {

    private AudioAttributes audioAttributes;
    private static SoundPool soundPool;
    private static int correctSound, wrongSound,btnSound;
    private final int SOUND_POOL_MAX =3;

    public SoundPlayer(Context context){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();


            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();

        }else {
            soundPool = new SoundPool(SOUND_POOL_MAX,AudioManager.STREAM_MUSIC,0);
        }

        correctSound = soundPool.load(context,R.raw.correct1,1);
        wrongSound = soundPool.load(context,R.raw.wrong,1);
        btnSound = soundPool.load(context,R.raw.c,1);
    }


    public void playCorrectSound(){

        soundPool.play(correctSound,1.0f,1.0f,1,0,1.3f);
    }

    public void playWrongSound(){

        soundPool.play(wrongSound,0.8f,0.8f,1,0,1.3f);
    }

    public void playbtnSound(){

        soundPool.play(btnSound,1.0f,1.0f,1,0,1.3f);
    }
}
