package com.mobiledexterous.gaming;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.utilities.Utilities;

public class GameTappingEasy extends AppCompatActivity {

    Handler handler = new Handler();

    boolean timer_status = true;
    boolean status = true;

    int dot_count = -1;
    int visible_count =0;
    int failCount = 0;

    ImageView mask, dot_0, dot_1_1, dot_1_2, dot_1_3, dot_1_4, dot_1_5, dot_1_6, dot_2_1, dot_2_2, dot_2_3, dot_2_4, dot_2_5, dot_2_6;
    ImageView nextBtn, help_view, dot_3_1, dot_3_2, dot_3_3, dot_3_4, dot_3_5, dot_3_6, dot_4_1, dot_4_2, dot_4_3, dot_4_4, dot_4_5, dot_4_6;

    TextView timer;

    DatabaseReference databaseGameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tapping_easy);

        String ref = "User/Gaming/"+GameUtilities.patientId;

        databaseGameData = FirebaseDatabase.getInstance().getReference(ref);

        timer = (TextView) findViewById(R.id.timer);
        mask = (ImageView) findViewById(R.id.t_mask);
        nextBtn = (ImageView) findViewById(R.id.nextBtn);
        help_view = (ImageView) findViewById(R.id.help_view);

        dot_0 = (ImageView) findViewById(R.id.dot_0);

        dot_1_1 = (ImageView) findViewById(R.id.dot_1_1);
        dot_1_2 = (ImageView) findViewById(R.id.dot_1_2);
        dot_1_3 = (ImageView) findViewById(R.id.dot_1_3);
        dot_1_4 = (ImageView) findViewById(R.id.dot_1_4);
        dot_1_5 = (ImageView) findViewById(R.id.dot_1_5);
        dot_1_6 = (ImageView) findViewById(R.id.dot_1_6);

        dot_2_1 = (ImageView) findViewById(R.id.dot_2_1);
        dot_2_2 = (ImageView) findViewById(R.id.dot_2_2);
        dot_2_3 = (ImageView) findViewById(R.id.dot_2_3);
        dot_2_4 = (ImageView) findViewById(R.id.dot_2_4);
        dot_2_5 = (ImageView) findViewById(R.id.dot_2_5);
        dot_2_6 = (ImageView) findViewById(R.id.dot_2_6);

        dot_3_1 = (ImageView) findViewById(R.id.dot_3_1);
        dot_3_2 = (ImageView) findViewById(R.id.dot_3_2);
        dot_3_3 = (ImageView) findViewById(R.id.dot_3_3);
        dot_3_4 = (ImageView) findViewById(R.id.dot_3_4);
        dot_3_5 = (ImageView) findViewById(R.id.dot_3_5);
        dot_3_6 = (ImageView) findViewById(R.id.dot_3_6);

        dot_4_1 = (ImageView) findViewById(R.id.dot_4_1);
        dot_4_2 = (ImageView) findViewById(R.id.dot_4_2);
        dot_4_3 = (ImageView) findViewById(R.id.dot_4_3);
        dot_4_4 = (ImageView) findViewById(R.id.dot_4_4);
        dot_4_5 = (ImageView) findViewById(R.id.dot_4_5);
        dot_4_6 = (ImageView) findViewById(R.id.dot_4_6);

        mask.setOnTouchListener(new TapListener());

        loadLevel();

    }

    //Tap listener
    private final class TapListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            final int evX = (int) motionEvent.getX();
            final int evY = (int) motionEvent.getY();
            int touchColor = GameUtilities.getHotspotColor((ImageView) findViewById(R.id.t_mask), evX, evY);
            GameColorTool ct = new GameColorTool();
            int tolerance = 20;

            int v = nextBtn.getVisibility();
            int helpBtnAct = (Integer) help_view.getVisibility();

            //Next
            if (ct.closeMatch(Color.parseColor("#048fc2"), touchColor, tolerance) && v == 0 ){
                if(GameUtilities.gameLevel == 9){
                    GameUtilities.gameLevel = 1;
                    GameUtilities.gameDifficulty = 1;
                    next();
                }

                else{
                    nextBtn.setVisibility(View.INVISIBLE);
                    GameUtilities.gameLevel++;
                    failCount = 0;
                    loadLevel();
                }

            }

            if (helpBtnAct == 0)
                help_view.setVisibility(View.INVISIBLE);

            //Help
            if (ct.closeMatch(Color.parseColor("#583b1c"), touchColor, tolerance)){
                if (helpBtnAct == 4)
                    help_view.setVisibility(View.VISIBLE);
            }

            else if (timer_status ) {
                timer_status = false;
                startTimer();
            }

            //Retry
            if (ct.closeMatch(Color.parseColor("#1b10ac"), touchColor, tolerance)){
                loadLevel();
            }

            if (status ){

                // Dot_1_1
                if (ct.closeMatch(Color.parseColor("#f21111"), touchColor, tolerance)){ dot_1_1.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_1_2
                else if (ct.closeMatch(Color.parseColor("#ff00f6"), touchColor, tolerance)){ dot_1_2.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_1_3
                else if (ct.closeMatch(Color.parseColor("#c07fe9"), touchColor, tolerance)){ dot_1_3.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_1_4
                else if (ct.closeMatch(Color.parseColor("#009cff"), touchColor, tolerance)){ dot_1_4.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_1_5
                else if (ct.closeMatch(Color.parseColor("#05e64f"), touchColor, tolerance)){ dot_1_5.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_1_6
                else if (ct.closeMatch(Color.parseColor("#ff9600"), touchColor, tolerance)){ dot_1_6.setVisibility(View.INVISIBLE);
                    correct();}

                // Dot_2_1
                else if (ct.closeMatch(Color.parseColor("#f7e300"), touchColor, tolerance)){ dot_2_1.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_2_2
                else if (ct.closeMatch(Color.parseColor("#ec75b0"), touchColor, tolerance)){ dot_2_2.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_2_3
                else if (ct.closeMatch(Color.parseColor("#895da5"), touchColor, tolerance)){ dot_2_3.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_2_4
                else if (ct.closeMatch(Color.parseColor("#ff3900"), touchColor, tolerance)){ dot_2_4.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_2_5
                else if (ct.closeMatch(Color.parseColor("#077b2d"), touchColor, tolerance)){ dot_2_5.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_2_6
                else if (ct.closeMatch(Color.parseColor("#8e601e"), touchColor, tolerance)){ dot_2_6.setVisibility(View.INVISIBLE);
                    correct();}

                // Dot_3_1
                else if (ct.closeMatch(Color.parseColor("#8a6561"), touchColor, tolerance)){ dot_3_1.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_3_2
                else if (ct.closeMatch(Color.parseColor("#0aba5d"), touchColor, tolerance)){ dot_3_2.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_3_3
                else if (ct.closeMatch(Color.parseColor("#400624"), touchColor, tolerance)){ dot_3_3.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_3_4
                else if (ct.closeMatch(Color.parseColor("#6f4040"), touchColor, tolerance)){ dot_3_4.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_3_5
                else if (ct.closeMatch(Color.parseColor("#2f02f1"), touchColor, tolerance)){ dot_3_5.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_3_6
                else if (ct.closeMatch(Color.parseColor("#02d1ff"), touchColor, tolerance)){ dot_3_6.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_4_1
                else if (ct.closeMatch(Color.parseColor("#907806"), touchColor, tolerance)){ dot_4_1.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_4_2
                else if (ct.closeMatch(Color.parseColor("#3d5a0f"), touchColor, tolerance)){ dot_4_2.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_4_3
                else if (ct.closeMatch(Color.parseColor("#3c39c5"), touchColor, tolerance)){ dot_4_3.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_4_4
                else if (ct.closeMatch(Color.parseColor("#c6e671"), touchColor, tolerance)){ dot_4_4.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_4_5
                else if (ct.closeMatch(Color.parseColor("#898280"), touchColor, tolerance)){ dot_4_5.setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_4_6
                else if (ct.closeMatch(Color.parseColor("#b9e005"), touchColor, tolerance)){ dot_4_6.setVisibility(View.INVISIBLE);
                    correct();}

            }

            return false;
        }
    }

    //Load next
    public void next(){

        Intent intent = new Intent(this, GameTappingNormal.class);
        startActivity(intent);

    }

    //Load Level
    public void loadLevel(){

        handler.removeCallbacksAndMessages(null);
        nextBtn.setVisibility(View.INVISIBLE);
        timer.setText("10");
        status = true;
        timer_status = true;

       dot_1_1.setVisibility(View.INVISIBLE);
       dot_1_2.setVisibility(View.INVISIBLE);
       dot_1_3.setVisibility(View.INVISIBLE);
       dot_1_4.setVisibility(View.INVISIBLE);
       dot_1_5.setVisibility(View.INVISIBLE);
       dot_1_6.setVisibility(View.INVISIBLE);
       dot_2_1.setVisibility(View.INVISIBLE);
       dot_2_2.setVisibility(View.INVISIBLE);
       dot_2_3.setVisibility(View.INVISIBLE);
       dot_2_4.setVisibility(View.INVISIBLE);
       dot_2_5.setVisibility(View.INVISIBLE);
       dot_2_6.setVisibility(View.INVISIBLE);
       dot_3_1.setVisibility(View.INVISIBLE);
       dot_3_2.setVisibility(View.INVISIBLE);
       dot_3_3.setVisibility(View.INVISIBLE);
       dot_3_4.setVisibility(View.INVISIBLE);
       dot_3_5.setVisibility(View.INVISIBLE);
       dot_3_6.setVisibility(View.INVISIBLE);
       dot_4_1.setVisibility(View.INVISIBLE);
       dot_4_2.setVisibility(View.INVISIBLE);
       dot_4_3.setVisibility(View.INVISIBLE);
       dot_4_4.setVisibility(View.INVISIBLE);
       dot_4_5.setVisibility(View.INVISIBLE);
       dot_4_6.setVisibility(View.INVISIBLE);

        if( GameUtilities.gameLevel == 1){
            dot_0.setImageResource(R.drawable.tap_blue);
            dot_1_1.setImageResource(R.drawable.tap_blue);
            dot_1_2.setImageResource(R.drawable.tap_blue);
            dot_2_3.setImageResource(R.drawable.tap_blue);
            dot_2_4.setImageResource(R.drawable.tap_blue);
            dot_3_5.setImageResource(R.drawable.tap_blue);
            dot_3_6.setImageResource(R.drawable.tap_blue);

            dot_1_1.setVisibility(View.VISIBLE);
            dot_1_2.setVisibility(View.VISIBLE);
            dot_2_3.setVisibility(View.VISIBLE);
            dot_2_4.setVisibility(View.VISIBLE);
            dot_3_5.setVisibility(View.VISIBLE);
            dot_3_6.setVisibility(View.VISIBLE);

            dot_count = 6;
        }

        else if( GameUtilities.gameLevel == 2){
            dot_0.setImageResource(R.drawable.tap_red);
            dot_4_1.setImageResource(R.drawable.tap_red);
            dot_4_2.setImageResource(R.drawable.tap_red);
            dot_3_3.setImageResource(R.drawable.tap_red);
            dot_3_4.setImageResource(R.drawable.tap_red);
            dot_4_5.setImageResource(R.drawable.tap_red);
            dot_4_6.setImageResource(R.drawable.tap_red);

            dot_4_1.setVisibility(View.VISIBLE);
            dot_4_2.setVisibility(View.VISIBLE);
            dot_3_3.setVisibility(View.VISIBLE);
            dot_3_4.setVisibility(View.VISIBLE);
            dot_4_5.setVisibility(View.VISIBLE);
            dot_4_6.setVisibility(View.VISIBLE);

            dot_count = 6;
        }

        else if( GameUtilities.gameLevel == 3){
            dot_0.setImageResource(R.drawable.tap_green);
            dot_1_1.setImageResource(R.drawable.tap_green);
            dot_2_2.setImageResource(R.drawable.tap_green);
            dot_3_3.setImageResource(R.drawable.tap_green);
            dot_3_4.setImageResource(R.drawable.tap_green);
            dot_2_5.setImageResource(R.drawable.tap_green);
            dot_1_6.setImageResource(R.drawable.tap_green);

            dot_1_1.setVisibility(View.VISIBLE);
            dot_2_2.setVisibility(View.VISIBLE);
            dot_3_3.setVisibility(View.VISIBLE);
            dot_3_4.setVisibility(View.VISIBLE);
            dot_2_5.setVisibility(View.VISIBLE);
            dot_1_6.setVisibility(View.VISIBLE);

            dot_count = 6;
        }

        else if( GameUtilities.gameLevel == 4){
            dot_0.setImageResource(R.drawable.tap_yellow);
            dot_1_4.setImageResource(R.drawable.tap_yellow);
            dot_2_3.setImageResource(R.drawable.tap_yellow);
            dot_3_2.setImageResource(R.drawable.tap_yellow);
            dot_4_1.setImageResource(R.drawable.tap_yellow);
            dot_1_6.setImageResource(R.drawable.tap_yellow);
            dot_2_5.setImageResource(R.drawable.tap_yellow);
            dot_3_4.setImageResource(R.drawable.tap_yellow);
            dot_4_3.setImageResource(R.drawable.tap_yellow);

            dot_1_4.setVisibility(View.VISIBLE);
            dot_2_3.setVisibility(View.VISIBLE);
            dot_3_2.setVisibility(View.VISIBLE);
            dot_4_1.setVisibility(View.VISIBLE);
            dot_1_6.setVisibility(View.VISIBLE);
            dot_2_5.setVisibility(View.VISIBLE);
            dot_3_4.setVisibility(View.VISIBLE);
            dot_4_3.setVisibility(View.VISIBLE);

            dot_count = 8;
        }

        else if( GameUtilities.gameLevel == 5){
            dot_0.setImageResource(R.drawable.tap_blue);
            dot_1_1.setImageResource(R.drawable.tap_blue);
            dot_2_2.setImageResource(R.drawable.tap_blue);
            dot_1_3.setImageResource(R.drawable.tap_blue);
            dot_2_4.setImageResource(R.drawable.tap_blue);
            dot_1_5.setImageResource(R.drawable.tap_blue);
            dot_2_6.setImageResource(R.drawable.tap_blue);

            dot_1_1.setVisibility(View.VISIBLE);
            dot_2_2.setVisibility(View.VISIBLE);
            dot_1_3.setVisibility(View.VISIBLE);
            dot_2_4.setVisibility(View.VISIBLE);
            dot_1_5.setVisibility(View.VISIBLE);
            dot_2_6.setVisibility(View.VISIBLE);

            dot_count = 6;
        }

        else if( GameUtilities.gameLevel == 6){
            dot_0.setImageResource(R.drawable.tap_red);
            dot_1_1.setImageResource(R.drawable.tap_red);
            dot_1_6.setImageResource(R.drawable.tap_red);
            dot_2_3.setImageResource(R.drawable.tap_red);
            dot_2_4.setImageResource(R.drawable.tap_red);
            dot_3_3.setImageResource(R.drawable.tap_red);
            dot_3_4.setImageResource(R.drawable.tap_red);
            dot_4_1.setImageResource(R.drawable.tap_red);
            dot_4_6.setImageResource(R.drawable.tap_red);

            dot_1_1.setVisibility(View.VISIBLE);
            dot_1_6.setVisibility(View.VISIBLE);
            dot_2_3.setVisibility(View.VISIBLE);
            dot_2_4.setVisibility(View.VISIBLE);
            dot_3_3.setVisibility(View.VISIBLE);
            dot_3_4.setVisibility(View.VISIBLE);
            dot_4_1.setVisibility(View.VISIBLE);
            dot_4_6.setVisibility(View.VISIBLE);

            dot_count = 8;
        }

        else if( GameUtilities.gameLevel == 7){
            dot_0.setImageResource(R.drawable.tap_green);
            dot_1_3.setImageResource(R.drawable.tap_green);
            dot_1_4.setImageResource(R.drawable.tap_green);
            dot_2_2.setImageResource(R.drawable.tap_green);
            dot_2_5.setImageResource(R.drawable.tap_green);
            dot_3_2.setImageResource(R.drawable.tap_green);
            dot_3_5.setImageResource(R.drawable.tap_green);
            dot_4_3.setImageResource(R.drawable.tap_green);
            dot_4_4.setImageResource(R.drawable.tap_green);

            dot_1_3.setVisibility(View.VISIBLE);
            dot_1_4.setVisibility(View.VISIBLE);
            dot_2_2.setVisibility(View.VISIBLE);
            dot_2_5.setVisibility(View.VISIBLE);
            dot_3_2.setVisibility(View.VISIBLE);
            dot_3_5.setVisibility(View.VISIBLE);
            dot_4_3.setVisibility(View.VISIBLE);
            dot_4_4.setVisibility(View.VISIBLE);

            dot_count = 8;
        }

        else if( GameUtilities.gameLevel == 8){
            dot_0.setImageResource(R.drawable.tap_yellow);
            dot_1_2.setImageResource(R.drawable.tap_yellow);
            dot_1_3.setImageResource(R.drawable.tap_yellow);
            dot_1_4.setImageResource(R.drawable.tap_yellow);
            dot_1_5.setImageResource(R.drawable.tap_yellow);
            dot_2_4.setImageResource(R.drawable.tap_yellow);
            dot_3_3.setImageResource(R.drawable.tap_yellow);
            dot_4_5.setImageResource(R.drawable.tap_yellow);
            dot_4_4.setImageResource(R.drawable.tap_yellow);
            dot_4_3.setImageResource(R.drawable.tap_yellow);
            dot_4_2.setImageResource(R.drawable.tap_yellow);

            dot_1_2.setVisibility(View.VISIBLE);
            dot_1_3.setVisibility(View.VISIBLE);
            dot_1_4.setVisibility(View.VISIBLE);
            dot_1_5.setVisibility(View.VISIBLE);
            dot_2_4.setVisibility(View.VISIBLE);
            dot_3_3.setVisibility(View.VISIBLE);
            dot_4_5.setVisibility(View.VISIBLE);
            dot_4_4.setVisibility(View.VISIBLE);
            dot_4_3.setVisibility(View.VISIBLE);
            dot_4_2.setVisibility(View.VISIBLE);

            dot_count = 10;

        }

        else if( GameUtilities.gameLevel == 9){
            dot_0.setImageResource(R.drawable.tap_red);
            dot_1_1.setImageResource(R.drawable.tap_red);
            dot_1_3.setImageResource(R.drawable.tap_red);
            dot_1_5.setImageResource(R.drawable.tap_red);
            dot_2_6.setImageResource(R.drawable.tap_red);
            dot_4_6.setImageResource(R.drawable.tap_red);
            dot_4_4.setImageResource(R.drawable.tap_red);
            dot_4_2.setImageResource(R.drawable.tap_red);
            dot_3_1.setImageResource(R.drawable.tap_red);

            dot_1_1.setVisibility(View.VISIBLE);
            dot_1_3.setVisibility(View.VISIBLE);
            dot_1_5.setVisibility(View.VISIBLE);
            dot_2_6.setVisibility(View.VISIBLE);
            dot_4_6.setVisibility(View.VISIBLE);
            dot_4_4.setVisibility(View.VISIBLE);
            dot_4_2.setVisibility(View.VISIBLE);
            dot_3_1.setVisibility(View.VISIBLE);

            dot_count = 8;
        }
    }

    //If correct dot touch
    public void correct(){

        dotVisibility();

        if (visible_count == 0 ){
            handler.removeCallbacksAndMessages(null);
                nextBtn.setVisibility(View.VISIBLE);

            if ( GameUtilities.gameLevel == 1 )
                GameUtilities.score_t = 3.5;
            else if ( GameUtilities.gameLevel == 2 )
                GameUtilities.score_t = 7.0;
            else if ( GameUtilities.gameLevel == 3 )
                GameUtilities.score_t = 10.5;
            else if ( GameUtilities.gameLevel == 4 )
                GameUtilities.score_t = 14.0;
            else if ( GameUtilities.gameLevel == 5 )
                GameUtilities.score_t = 17.5;
            else if ( GameUtilities.gameLevel == 6 )
                GameUtilities.score_t = 21.0;
            else if ( GameUtilities.gameLevel == 7 )
                GameUtilities.score_t = 24.5;
            else if ( GameUtilities.gameLevel == 8 )
                GameUtilities.score_t = 28.0;
            else if ( GameUtilities.gameLevel == 9 )
                GameUtilities.score_t = 33.0;

        }
    }

    //Timer
    public void startTimer(){

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.setText("09");
            }
        },1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.setText("08");
            }
        },2000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.setText("07");
            }
        },3000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.setText("06");
            }
        },4000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.setText("05");
            }
        },5000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.setText("04");
            }
        },6000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.setText("03");
            }
        },7000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.setText("02");
            }
        },8000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.setText("01");
            }
        },9000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timer.setText("00");
                status = false;
                ++failCount;

                if ( failCount == 5 && GameUtilities.gameLevel > 1 ){

                    --GameUtilities.gameLevel;
                    loadLevel();
                    failCount = 0;
                    saveData();
                }

            }
        },10000);

    }

    //Dots visibility
    public void dotVisibility(){

        visible_count =0;
        int visibleArray[] = {4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4};

        visibleArray[0] = dot_1_1.getVisibility();
        visibleArray[1] = dot_1_2.getVisibility();
        visibleArray[2] = dot_1_3.getVisibility();
        visibleArray[3] = dot_1_4.getVisibility();
        visibleArray[4] = dot_1_5.getVisibility();
        visibleArray[5] = dot_1_6.getVisibility();
        visibleArray[6] = dot_2_1.getVisibility();
        visibleArray[7] = dot_2_2.getVisibility();
        visibleArray[8] = dot_2_3.getVisibility();
        visibleArray[9] = dot_2_4.getVisibility();
        visibleArray[10] = dot_2_5.getVisibility();
        visibleArray[11] = dot_2_6.getVisibility();
        visibleArray[12] = dot_3_1.getVisibility();
        visibleArray[13] = dot_3_2.getVisibility();
        visibleArray[14] = dot_3_3.getVisibility();
        visibleArray[15] = dot_3_4.getVisibility();
        visibleArray[16] = dot_3_5.getVisibility();
        visibleArray[17] = dot_3_6.getVisibility();
        visibleArray[18] = dot_4_1.getVisibility();
        visibleArray[19] = dot_4_2.getVisibility();
        visibleArray[20] = dot_4_3.getVisibility();
        visibleArray[21] = dot_4_4.getVisibility();
        visibleArray[22] = dot_4_5.getVisibility();
        visibleArray[23] = dot_4_6.getVisibility();

        for(int x=0; x<=23; x++){

            if ( visibleArray[x] == 0 ) {
                visible_count++;
            }

        }

    }

    //Play sound
    public void playSound( String audio) {

        try{
            GameUtilities.stopPlaying();
            GameUtilities.playAudio(getAssets().openFd(audio));
        } catch (Exception e){}

    }

    public void saveData(){
        String score = String.valueOf(GameUtilities.score_t - 3.5);
        String attempt = String.valueOf(++GameUtilities.attemptNo);
        String gameId = String.valueOf(GameUtilities.gameNumber);

        String temp_id = databaseGameData.push().getKey();

        GameData game_data = new GameData( attempt, gameId, score );

        databaseGameData.child(temp_id).setValue(game_data);
    }

    public void onBackPressed(){
        super.onBackPressed();
        GameUtilities.stopPlaying();
        GameUtilities.mediaPlayer=null;
        handler.removeCallbacksAndMessages(null);

        String score = String.valueOf(GameUtilities.score_t);
        String attempt = String.valueOf(++GameUtilities.attemptNo);
        String gameId = String.valueOf(GameUtilities.gameNumber);

        String temp_id = databaseGameData.push().getKey();

        GameData game_data = new GameData( attempt, gameId, score );

        databaseGameData.child(temp_id).setValue(game_data);

        Intent intent = new Intent(this, GameLevel.class);
        startActivity(intent);
        finish();
    }
}
