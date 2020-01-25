package com.mobiledexterous.gaming;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class GameSplash extends AppCompatActivity {

    Handler handler = new Handler();

    ImageView txt_1, txt_2, txt_3, txt_4, loading_1, loading_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_splash);

        txt_1 = (ImageView) findViewById(R.id.txt_1);
        txt_2 = (ImageView) findViewById(R.id.txt_2);
        txt_3 = (ImageView) findViewById(R.id.txt_3);
        txt_4 = (ImageView) findViewById(R.id.txt_4);
        loading_1 = (ImageView) findViewById(R.id.loading_1);
        loading_2 = (ImageView) findViewById(R.id.loading_2);

        Loading();

        entranceAnimation1();
        entranceAnimation2();
        entranceAnimation3();
        entranceAnimation4();

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(GameSplash.this, GameMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();

    }

    public void entranceAnimation1(){

        Display display = getWindowManager().getDefaultDisplay();
        float width = display.getWidth();
        TranslateAnimation animation1 = new TranslateAnimation(+width, 0.0f, 0.0f, 0.0f);
        animation1.setDuration(2000);
        animation1.setFillAfter(true);
        txt_1.startAnimation(animation1);

    }

    public void entranceAnimation2(){

        Display display = getWindowManager().getDefaultDisplay();
        float width = display.getWidth();
        TranslateAnimation animation1 = new TranslateAnimation(+width, 0.0f, 0.0f, 0.0f);
        animation1.setDuration(2200);
        animation1.setFillAfter(true);
        txt_2.startAnimation(animation1);

    }

    public void entranceAnimation3(){

        Display display = getWindowManager().getDefaultDisplay();
        float width = display.getWidth();
        TranslateAnimation animation1 = new TranslateAnimation(+width, 0.0f, 0.0f, 0.0f);
        animation1.setDuration(2400);
        animation1.setFillAfter(true);
        txt_3.startAnimation(animation1);

    }

    public void entranceAnimation4(){

        Display display = getWindowManager().getDefaultDisplay();
        float width = display.getWidth();
        TranslateAnimation animation1 = new TranslateAnimation(+width, 0.0f, 0.0f, 0.0f);
        animation1.setDuration(2600);
        animation1.setFillAfter(true);
        txt_4.startAnimation(animation1);

    }

    public void Loading(){

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading_1.setVisibility(View.INVISIBLE);
                loading_2.setVisibility(View.VISIBLE);
            }
        },800);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading_2.setVisibility(View.INVISIBLE);
                loading_1.setVisibility(View.VISIBLE);
            }
        },1800);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading_1.setVisibility(View.INVISIBLE);
                loading_2.setVisibility(View.VISIBLE);
            }
        },2800);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading_2.setVisibility(View.INVISIBLE);
                loading_1.setVisibility(View.VISIBLE);
            }
        },3800);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading_2.setVisibility(View.INVISIBLE);
                loading_1.setVisibility(View.VISIBLE);
            }
        },4800);

    }

}
