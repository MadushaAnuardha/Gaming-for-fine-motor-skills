package com.mobiledexterous.gaming;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;



public class GameLevel extends AppCompatActivity {

    ImageView mask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_level);

        mask = (ImageView) findViewById(R.id.mask_level);

        //Set touch listener
        mask.setOnTouchListener(new MyTouchListener());

    }

    //Touch Listener
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            final int evX = (int) motionEvent.getX();
            final int evY = (int) motionEvent.getY();
            int touchColor = GameUtilities.getHotspotColor((ImageView) findViewById(R.id.mask_level), evX, evY);
            GameColorTool ct = new GameColorTool();
            int tolerance = 20;

            //Level 1
//            if (ct.closeMatch(Color.parseColor("#74e104"), touchColor, tolerance))
            if (touchColor == -16719616){
                GameUtilities.gameLevel = 1;
                startGame();
            }

            //Level 2
            if (ct.closeMatch(Color.parseColor("#d3d311"), touchColor, tolerance)) {
                GameUtilities.gameLevel = 2;
                startGame();
            }

            //Level 3
            if (ct.closeMatch(Color.parseColor("#0e7019"), touchColor, tolerance)) {
                GameUtilities.gameLevel = 3;
                startGame();
            }

            //Level 4
            if (ct.closeMatch(Color.parseColor("#03f6d1"), touchColor, tolerance)) {
                GameUtilities.gameLevel = 4;
                startGame();
            }

            //Level 5
            if (ct.closeMatch(Color.parseColor("#0c90f3"), touchColor, tolerance)) {
                GameUtilities.gameLevel = 5;
                startGame();
            }

            //Level 6
            if (ct.closeMatch(Color.parseColor("#8668e7"), touchColor, tolerance)) {
                GameUtilities.gameLevel = 6;
                startGame();
            }

            //Level 7
            if (ct.closeMatch(Color.parseColor("#f978d9"), touchColor, tolerance)) {
                GameUtilities.gameLevel = 7;
                startGame();
            }

            //Level 8
            if (ct.closeMatch(Color.parseColor("#ef03d8"), touchColor, tolerance)) {
                GameUtilities.gameLevel = 8;
                startGame();
            }

            //Level 9
            if (ct.closeMatch(Color.parseColor("#eb2949"), touchColor, tolerance)) {
                GameUtilities.gameLevel = 9;
                startGame();
            }


            //Home
            if (ct.closeMatch(Color.parseColor("#1af20a"), touchColor, tolerance)) {

                Intent back = new Intent(getApplicationContext(), GameDifficulty.class);
                startActivity(back);

            }

            return true;
        }//End of onTouch
    }//End of Touch Listener

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
        Intent intent = new Intent(this, GameDifficulty.class);
        startActivity(intent);
        finish();
    }
}
