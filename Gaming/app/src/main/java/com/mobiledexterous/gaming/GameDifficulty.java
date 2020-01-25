package com.mobiledexterous.gaming;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class GameDifficulty extends AppCompatActivity {

    ImageView mask,image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_difficulty);

        if(GameUtilities.gameNumber == 3){
            mask = (ImageView) findViewById(R.id.mask);
            image = (ImageView) findViewById(R.id.image);
        }

        else{
            mask = (ImageView) findViewById(R.id.mask);
            image = (ImageView) findViewById(R.id.image);
            mask.setImageResource(R.drawable.difficulty_mask2);
            image.setImageResource(R.drawable.game_difficulty2);
        }

        //Set touch listener
        mask.setOnTouchListener(new MyTouchListener());

    }

    //Touch Listener
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            final int evX = (int) motionEvent.getX();
            final int evY = (int) motionEvent.getY();
            int touchColor = GameUtilities.getHotspotColor((ImageView) findViewById(R.id.mask), evX, evY);
            GameColorTool ct = new GameColorTool();
            int tolerance = 20;

            //Easy
            if (ct.closeMatch(Color.parseColor("#00fff4"), touchColor, tolerance)) {
                GameUtilities.gameDifficulty = 0;
                Intent easy = new Intent(getApplicationContext(), GameLevel.class);
                startActivity(easy);
            }

            //Normal
            if (ct.closeMatch(Color.parseColor("#1b10ac"), touchColor, tolerance)) {
                GameUtilities.gameDifficulty = 1;
                Intent normal = new Intent(getApplicationContext(), GameLevel.class);
                startActivity(normal);
            }

            //Hard
            if (ct.closeMatch(Color.parseColor("#ee0097"), touchColor, tolerance)) {
                GameUtilities.gameDifficulty = 2;
                Intent hard = new Intent(getApplicationContext(), GameLevel.class);
                startActivity(hard);
            }

            return true;
        }//End of onTouch
    }//End of Touch Listener


    public void onBackPressed(){
        super.onBackPressed();
//        GameUtilities.stopPlaying();
//        GameUtilities.mediaPlayer=null;
//        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(this, GameMode.class);
        startActivity(intent);
        finish();
    }

}
