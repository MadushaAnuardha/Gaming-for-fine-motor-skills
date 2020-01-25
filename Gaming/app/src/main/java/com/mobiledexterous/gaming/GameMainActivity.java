package com.mobiledexterous.gaming;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GameMainActivity extends AppCompatActivity {

    ImageView traceThePath,dragAndDrop,dexterity,mask;

    DatabaseReference databaseGameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);

        //buttons
        traceThePath = (ImageView) findViewById(R.id.traceThePathBtn);
        dragAndDrop = (ImageView) findViewById(R.id.dragAndDropBtn);
        dexterity = (ImageView) findViewById(R.id.dexterityBtn);

        //Mask
        mask = (ImageView) findViewById(R.id.mask);

        //Set touch listener
        mask.setOnTouchListener(new MyTouchListener());

        String ref = "User/Gaming/"+GameUtilities.patientId;

        databaseGameData = FirebaseDatabase.getInstance().getReference(ref);

        databaseGameData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }//OnCreate end

    public void showData( DataSnapshot dataSnapshot){

        for ( DataSnapshot ds : dataSnapshot.getChildren()){

            GameData data = new GameData();
            data.setScore(ds.getValue(GameData.class).getScore());
            data.setAttemptNo(ds.getValue(GameData.class).getAttemptNo());
            data.setGameId(ds.getValue(GameData.class).getGameId());

            Log.e("database","Score : "+data.getScore());
            Log.e("database","Attempt : "+data.getAttemptNo());
            Log.e("database","GameId : "+data.getGameId());

            //Update Attempt
            if ( GameUtilities.attemptNo < Integer.parseInt(data.getAttemptNo()) )
                GameUtilities.attemptNo = Integer.parseInt(data.getAttemptNo());

            //Drag and drop
            if ( Integer.parseInt(data.getGameId()) == 0 ){

                if ( GameUtilities.attemptNo_dad < Integer.parseInt(data.getAttemptNo()) )
                    GameUtilities.score_dad = Double.parseDouble(data.getScore());

            }

            //Trace the path
            else if ( Integer.parseInt(data.getGameId()) == 2 ){

                if ( GameUtilities.attemptNo_ttp < Integer.parseInt(data.getAttemptNo()) )
                    GameUtilities.score_ttp = Double.parseDouble(data.getScore());

            }

            //Tapping
            else if ( Integer.parseInt(data.getGameId()) == 3 ){

                if ( GameUtilities.attemptNo_t < Integer.parseInt(data.getAttemptNo()) )
                    GameUtilities.score_t = Double.parseDouble(data.getScore());

            }

        }
    }

    //Touch Listener
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            final int evX = (int) motionEvent.getX();
            final int evY = (int) motionEvent.getY();
            int touchColor = GameUtilities.getHotspotColor((ImageView) findViewById(R.id.mask), evX, evY);
            GameColorTool ct = new GameColorTool();
            int tolerance = 20;

            //Drag and drop - blue
            if (ct.closeMatch(Color.parseColor("#332fe6"), touchColor, tolerance)) {
                Intent gameMode = new Intent(getApplicationContext(), GameMode.class);
                startActivity(gameMode);
                GameUtilities.gameNumber = 0;

            }

            //Trace the path - green
            if (ct.closeMatch(Color.parseColor("#2ee53a"), touchColor, tolerance)) {
                Intent gameMode = new Intent(getApplicationContext(), GameMode.class);
                startActivity(gameMode);
                GameUtilities.gameNumber = 2;

            }

            //Tapping - Cream
            if (ct.closeMatch(Color.parseColor("#d5955d"), touchColor, tolerance)) {
                Intent gameMode = new Intent(getApplicationContext(), GameMode.class);
                startActivity(gameMode);
                GameUtilities.gameNumber = 3;

            }

            //Results
            if (ct.closeMatch(Color.parseColor("#d1d31d"), touchColor, tolerance)) {
                Intent gameMode = new Intent(getApplicationContext(), GameGraphView.class);
                startActivity(gameMode);

            }

            return true;
        }//End of onTouch
    }//End of Touch Listener

    public void onBackPressed(){
//        super.onBackPressed();
//        GameUtilities.stopPlaying();
//        GameUtilities.mediaPlayer=null;
//        handler.removeCallbacksAndMessages(null);
//        Intent intent = new Intent(this, GameMainActivity.class);
//        startActivity(intent);
//        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
