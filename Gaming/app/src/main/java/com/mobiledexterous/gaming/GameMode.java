package com.mobiledexterous.gaming;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.util.GAuthToken;

public class GameMode extends AppCompatActivity {

    ImageView mask;
    int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode);

        mask = (ImageView) findViewById(R.id.mask_game_mode);

        //Set touch listener
        mask.setOnTouchListener(new MyTouchListener());



    }

    //Touch Listener
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            final int evX = (int) motionEvent.getX();
            final int evY = (int) motionEvent.getY();
            int touchColor = GameUtilities.getHotspotColor((ImageView) findViewById(R.id.mask_game_mode), evX, evY);
            GameColorTool ct = new GameColorTool();
            int tolerance = 20;

            //Automatic
            if (ct.closeMatch(Color.parseColor("#332fe6"), touchColor, tolerance) ) {

                //Drag and drop
                if ( GameUtilities.gameNumber == 0) {
                    x = Integer.valueOf(GameUtilities.score_dad.intValue());
                    getLevel_two();
                }

                //Trace the path
                else if ( GameUtilities.gameNumber == 2) {
                    x = Integer.valueOf(GameUtilities.score_ttp.intValue());
                    getLevel_two();
                }

                //Tapping
                else
                    getLevel_three();

            }

            //Manual
            else if (ct.closeMatch(Color.parseColor("#02f4a3"), touchColor, tolerance)) {

                Intent gameDifficulty = new Intent(getApplicationContext(), GameDifficulty.class);
                startActivity(gameDifficulty);

            }

            return true;
        }//End of onTouch
    }//End of Touch Listener

    public void getLevel_three() {

        if ( GameUtilities.score_t < 30 ){
            GameUtilities.gameDifficulty = 0;

            if ( GameUtilities.score_t == 0 )
                GameUtilities.gameLevel = 1;

            else if ( GameUtilities.score_t == 3.5 )
                GameUtilities.gameLevel = 2;

            else if ( GameUtilities.score_t == 7 )
                GameUtilities.gameLevel = 3;

            else if ( GameUtilities.score_t == 10.5 )
                GameUtilities.gameLevel = 4;

            else if ( GameUtilities.score_t == 14 )
                GameUtilities.gameLevel = 5;

            else if ( GameUtilities.score_t == 17.5 )
                GameUtilities.gameLevel = 6;

            else if ( GameUtilities.score_t == 21 )
                GameUtilities.gameLevel = 7;

            else if ( GameUtilities.score_t == 24.5 )
                GameUtilities.gameLevel = 8;

            else if ( GameUtilities.score_t == 28 )
                GameUtilities.gameLevel = 9;

        }

        else if ( GameUtilities.score_t < 65 ){
            GameUtilities.gameDifficulty = 1;

            if ( GameUtilities.score_t == 33 )
                GameUtilities.gameLevel = 1;

            else if ( GameUtilities.score_t == 36.5 )
                GameUtilities.gameLevel = 2;

            else if ( GameUtilities.score_t == 40 )
                GameUtilities.gameLevel = 3;

            else if ( GameUtilities.score_t == 43.5 )
                GameUtilities.gameLevel = 4;

            else if ( GameUtilities.score_t == 47 )
                GameUtilities.gameLevel = 5;

            else if ( GameUtilities.score_t == 50.5 )
                GameUtilities.gameLevel = 6;

            else if ( GameUtilities.score_t == 54 )
                GameUtilities.gameLevel = 7;

            else if ( GameUtilities.score_t == 57.5 )
                GameUtilities.gameLevel = 8;

            else if ( GameUtilities.score_t == 61 )
                GameUtilities.gameLevel = 9;

        }

        else{
            GameUtilities.gameDifficulty = 2;

            if ( GameUtilities.score_t == 66 )
                GameUtilities.gameLevel = 1;

            else if ( GameUtilities.score_t == 69.5 )
                GameUtilities.gameLevel = 2;

            else if ( GameUtilities.score_t == 73 )
                GameUtilities.gameLevel = 3;

            else if ( GameUtilities.score_t == 76.5 )
                GameUtilities.gameLevel = 4;

            else if ( GameUtilities.score_t == 80 )
                GameUtilities.gameLevel = 5;

            else if ( GameUtilities.score_t == 83.5 )
                GameUtilities.gameLevel = 6;

            else if ( GameUtilities.score_t == 87 )
                GameUtilities.gameLevel = 7;

            else if ( GameUtilities.score_t == 90.5 )
                GameUtilities.gameLevel = 8;

            else if ( GameUtilities.score_t == 94 )
                GameUtilities.gameLevel = 9;

            else if ( GameUtilities.score_t == 100 )
                GameUtilities.gameLevel = 9;

        }

        startGame();

    }

    public void getLevel_two(){

        if ( x < 44 ){

            GameUtilities.gameLevel = (x/5) + 1;
            GameUtilities.gameDifficulty = 0;

        }
        else{

            GameUtilities.gameDifficulty = 2;

            if ( x == 100 )
                GameUtilities.gameLevel = 9;

            else if ( x == 45 )
                GameUtilities.gameLevel = 1;

            else{
                GameUtilities.gameLevel = ((x - 50) / 5) + 1;
            }

        }

        startGame();

    }

    public void startGame(){

        if (GameUtilities.gameNumber == 0) {

            if(GameUtilities.gameDifficulty == 0 ){
                Intent DAD_Easy = new Intent(getApplicationContext(), GameDragAndDropEasy.class);
                startActivity(DAD_Easy);
            }

            else if(GameUtilities.gameDifficulty == 2 ){
                Intent DAD_Hard = new Intent(getApplicationContext(), GameDragAndDropHard.class);
                startActivity(DAD_Hard);
            }


        }

        else if(GameUtilities.gameNumber == 2) {

            if(GameUtilities.gameDifficulty == 0 ){
                Intent tracing = new Intent(getApplicationContext(), GameTraceThePath.class);
                startActivity(tracing);
            }
            else if(GameUtilities.gameDifficulty == 1 ){
                Intent tracing = new Intent(getApplicationContext(), GameTraceThePath.class);
                startActivity(tracing);
            }
            else{
                Intent tracing = new Intent(getApplicationContext(), GameTraceThePath.class);
                startActivity(tracing);
            }
        }

        else if(GameUtilities.gameNumber == 3){

            if(GameUtilities.gameDifficulty == 0 ){
                Intent tapping = new Intent(getApplicationContext(), GameTappingEasy.class);
                startActivity(tapping);
            }
            else if(GameUtilities.gameDifficulty == 1 ){
                Intent tapping = new Intent(getApplicationContext(), GameTappingNormal.class);
                startActivity(tapping);
            }
            else if(GameUtilities.gameDifficulty == 2 ){
                Intent tapping = new Intent(getApplicationContext(), GameTappingHard.class);
                startActivity(tapping);
            }

        }

    }

    public void onBackPressed(){
        super.onBackPressed();
//        GameUtilities.stopPlaying();
//        GameUtilities.mediaPlayer=null;
//        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(this, GameMainActivity.class);
        startActivity(intent);
        finish();
    }

}
