package com.mobiledexterous.gaming;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameDragAndDropEasy extends AppCompatActivity {

    View tmpV = null;
    Handler handler = new Handler();
    ConstraintLayout parentLayout;

    TextView timeView,correct,incorrect;

    int tag = -1;

    boolean status = true;
    boolean timer = true;

    int correct_count, incorrect_count = 0;
    int failCount = 0;

    ImageView nextBtn, mask, help, buttons;

    ImageView inTray[] = new ImageView[27];
    ImageView outTray[] = new ImageView[27];

    DatabaseReference databaseGameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_drag_and_drop_easy);

        String ref = "User/Gaming/"+GameUtilities.patientId;

        databaseGameData = FirebaseDatabase.getInstance().getReference(ref);

        help = (ImageView) findViewById(R.id.help_dad);

        //Buttons
        nextBtn  = (ImageView) findViewById(R.id.nextBtn);

        buttons = (ImageView) findViewById(R.id.DAD_background);

        timeView = (TextView) findViewById(R.id.timer);
        correct = (TextView) findViewById(R.id.correctView);
        incorrect = (TextView) findViewById(R.id.incorrectView);

        //In Tray
        inTray[0] = (ImageView) findViewById(R.id.square_1_tray);

        inTray[1] = (ImageView) findViewById(R.id.square_2_tray);
        inTray[2] = (ImageView) findViewById(R.id.circle_2_tray);

        inTray[3] = (ImageView) findViewById(R.id.circle_3_tray);
        inTray[4] = (ImageView) findViewById(R.id.triangle_3_tray);

        inTray[5] = (ImageView) findViewById(R.id.square_4_tray);
        inTray[6] = (ImageView) findViewById(R.id.circle_4_tray);
        inTray[7] = (ImageView) findViewById(R.id.pentagon_4_tray);

        inTray[8] = (ImageView) findViewById(R.id.circle_5_tray);
        inTray[9] = (ImageView) findViewById(R.id.pentagon_5_tray);
        inTray[10] = (ImageView) findViewById(R.id.triangle_5_tray);

        inTray[11] = (ImageView) findViewById(R.id.square_6_tray);
        inTray[12] = (ImageView) findViewById(R.id.triangle_6_tray);
        inTray[13] = (ImageView) findViewById(R.id.circle_6_tray);

        inTray[14] = (ImageView) findViewById(R.id.triangle_7_tray);
        inTray[15] = (ImageView) findViewById(R.id.square_7_tray);
        inTray[16] = (ImageView) findViewById(R.id.circle_7_tray);
        inTray[17] = (ImageView) findViewById(R.id.pentagon_7_tray);

        inTray[18] = (ImageView) findViewById(R.id.square_8_tray);
        inTray[19] = (ImageView) findViewById(R.id.pentagon_8_tray);
        inTray[20] = (ImageView) findViewById(R.id.circle_8_tray);
        inTray[21] = (ImageView) findViewById(R.id.triangle_8_tray);

        inTray[22] = (ImageView) findViewById(R.id.circle_9_tray);
        inTray[23] = (ImageView) findViewById(R.id.triangle_9_tray);
        inTray[24] = (ImageView) findViewById(R.id.octagon_9_tray);
        inTray[25] = (ImageView) findViewById(R.id.square_9_tray);
        inTray[26] = (ImageView) findViewById(R.id.pentagon_9_tray);

        //Out Tray
        outTray[0] = (ImageView) findViewById(R.id.square_1);

        outTray[1] = (ImageView) findViewById(R.id.square_2);
        outTray[2] = (ImageView) findViewById(R.id.circle_2);

        outTray[3] = (ImageView) findViewById(R.id.circle_3);
        outTray[4] = (ImageView) findViewById(R.id.triangle_3);

        outTray[5] = (ImageView) findViewById(R.id.square_4);
        outTray[6] = (ImageView) findViewById(R.id.circle_4);
        outTray[7] = (ImageView) findViewById(R.id.pentagon_4);

        outTray[8] = (ImageView) findViewById(R.id.circle_5);
        outTray[9] = (ImageView) findViewById(R.id.pentagon_5);
        outTray[10] = (ImageView) findViewById(R.id.triangle_5);

        outTray[11] = (ImageView) findViewById(R.id.square_6);
        outTray[12] = (ImageView) findViewById(R.id.triangle_6);
        outTray[13] = (ImageView) findViewById(R.id.circle_6);

        outTray[14] = (ImageView) findViewById(R.id.triangle_7);
        outTray[15] = (ImageView) findViewById(R.id.square_7);
        outTray[16] = (ImageView) findViewById(R.id.circle_7);
        outTray[17] = (ImageView) findViewById(R.id.pentagon_7);

        outTray[18] = (ImageView) findViewById(R.id.square_8);
        outTray[19] = (ImageView) findViewById(R.id.pentagon_8);
        outTray[20] = (ImageView) findViewById(R.id.circle_8);
        outTray[21] = (ImageView) findViewById(R.id.triangle_8);

        outTray[22] = (ImageView) findViewById(R.id.circle_9);
        outTray[23] = (ImageView) findViewById(R.id.triangle_9);
        outTray[24] = (ImageView) findViewById(R.id.octagon_9);
        outTray[25] = (ImageView) findViewById(R.id.square_9);
        outTray[26] = (ImageView) findViewById(R.id.pentagon_9);

        mask = (ImageView) findViewById(R.id.dad_btn_mask);

        //Set touch listener for buttons
        mask.setOnTouchListener(new MyButtonTouchListener());

        //Game level
        loadLevel();

        parentLayout = (ConstraintLayout)findViewById(R.id.parent);
        parentLayout.setOnDragListener(new MyDragListener());

    }//End of OnCreate

    //Button touch listener
    private final class MyButtonTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            final int evX = (int) motionEvent.getX();
            final int evY = (int) motionEvent.getY();
            int touchColor = GameUtilities.getHotspotColor((ImageView) findViewById(R.id.dad_btn_mask), evX, evY);
            GameColorTool ct = new GameColorTool();
            int tolerance = 20;

            int nxtBtnAct = (Integer) nextBtn.getVisibility();
            int helpBtnAct = (Integer) help.getVisibility();

            if (helpBtnAct == 0)
                help.setVisibility(View.INVISIBLE);

            //Help
            if (ct.closeMatch(Color.parseColor("#5b1e09"), touchColor, tolerance) && (helpBtnAct == 4)){

                    help.setVisibility(View.VISIBLE);
            }

            //Next
            else if (ct.closeMatch(Color.parseColor("#5f610a"), touchColor, tolerance) && (nxtBtnAct == 0 )) {

                if ( GameUtilities.gameLevel == 9 ){
                    GameUtilities.gameLevel = 1;
                    GameUtilities.gameDifficulty = 2;
                    loadNext();
                }

                else{
                    GameUtilities.gameLevel++;
                    loadLevel();
                }
            }

            //Retry
            else if (ct.closeMatch(Color.parseColor("#8e4237"), touchColor, tolerance)) {
                loadLevel();
            }


            return false;
        }
    }

    //Touch Listener for shapes
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            tmpV = view;

            if (status) {

                tag = (Integer) view.getTag();

                if (timer) {
                    timer = false;
                    startTime();
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.INVISIBLE);
                    return true;
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    view.setVisibility(View.VISIBLE);
                    return true;
                }

            }

            return false;
        }
    }

    //Drag Listener
    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, final DragEvent event) {
            final int evX = (int) event.getX();
            final int evY = (int) event.getY();
            View view = (View) event.getLocalState();
            ImageView dragView = (ImageView) view;

            if(dragView==null)
            {
                GameUtilities.recoverDissappearingViews(tmpV);
                return false;
            }

            switch (event.getAction()) {

                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    dragView.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DROP:

                    int touchColor = GameUtilities.getHotspotColor((ImageView) findViewById(R.id.dad_easy_mask), evX, evY);
                    GameColorTool ct = new GameColorTool();
                    int tolerance = 50;

                    //blue
                    if ( ct.closeMatch(Color.parseColor("#1e71b8"), touchColor, tolerance) && tag == 1 ){
                        ((ImageView) view).setImageResource(0);
                        correct();
                        return true;
                    }
                    //red
                    if( ct.closeMatch(Color.parseColor("#d31c01"), touchColor, tolerance) && tag == 2 ){
                        ((ImageView) view).setImageResource(0);
                        correct();
                        return true;
                    }
                    //purple
                    else if( ct.closeMatch(Color.parseColor("#5f188c"), touchColor, tolerance) && tag == 3 ){
                        ((ImageView) view).setImageResource(0);
                        correct();
                        return true;
                    }
                    //ash
                    else if( ct.closeMatch(Color.parseColor("#685c71"), touchColor, tolerance) && tag == 4 ) {
                        ((ImageView) view).setImageResource(0);
                        correct();
                        return true;
                    }

                    //yellow
                    else if ( ct.closeMatch(Color.parseColor("#e9dd1f"), touchColor, tolerance) && tag == 5 ) {
                        ((ImageView) view).setImageResource(0);
                        correct();
                        return true;
                    }

                    //cream
                    else if ( ct.closeMatch(Color.parseColor("#f1d368"), touchColor, tolerance) && tag == 6 ) {
                        ((ImageView) view).setImageResource(0);
                        correct();
                        return true;
                    }

                    //pink
                    else if ( ct.closeMatch(Color.parseColor("#e013e2"), touchColor, tolerance) && tag == 7 ) {
                        ((ImageView) view).setImageResource(0);
                        correct();
                        return true;
                    }

                    //Dark yellow
                    else if ( ct.closeMatch(Color.parseColor("#ee960a"), touchColor, tolerance) && tag == 8 ) {
                        ((ImageView) view).setImageResource(0);
                        correct();
                        return true;
                    }

                    //green
                    else if ( ct.closeMatch(Color.parseColor("#306308"), touchColor, tolerance) && tag == 9 ) {
                        ((ImageView) view).setImageResource(0);
                        correct();
                        return true;
                    }

                    //light blue
                    else if ( ct.closeMatch(Color.parseColor("#7ca0bf"), touchColor, tolerance) && tag == 10 ) {
                        ((ImageView) view).setImageResource(0);
                        correct();
                        return true;
                    }

                    else {
                        dragView.setVisibility(View.VISIBLE);
                        incorrect_count++;
                        incorrect.setText(Integer.toString(incorrect_count));
                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    dragView.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }

            return true;
        }
    }

    //Load Next
    public void loadNext(){

        Intent intent = new Intent(this, GameDragAndDropHard.class);
        startActivity(intent);

    }

    //Load the level
    public void loadLevel() {

        status = true;
        failCount = 0;
        timer = true;
        timeView.setText("10");
        nextBtn.setVisibility(View.INVISIBLE);
        correct_count = 0;
        incorrect_count = 0;
        correct.setText(Integer.toString(correct_count));
        incorrect.setText(Integer.toString(incorrect_count));

        for (int t = 0; t < 26; t++){

            inTray[t].setVisibility(View.INVISIBLE);
            outTray[t].setVisibility(View.INVISIBLE);

        }


        for (int i = 0; i <= 26; i++){
            inTray[i].setVisibility(View.INVISIBLE);
           outTray[i].setOnTouchListener(null);
        }

        if (GameUtilities.gameLevel == 1){
            outTray[0].setImageResource(R.drawable.square);
            inTray[0].setVisibility(View.VISIBLE);
            outTray[0].setVisibility(View.VISIBLE);
            outTray[0].setTag(1);
            outTray[0].setOnTouchListener(new MyTouchListener());
        }

        else if (GameUtilities.gameLevel == 2){
            outTray[1].setImageResource(R.drawable.square);
            outTray[2].setImageResource(R.drawable.circle);
            for(int x=1; x<=2; x++){
                inTray[x].setVisibility(View.VISIBLE);
                outTray[x].setVisibility(View.VISIBLE);
                outTray[x].setOnTouchListener(new MyTouchListener());
            }
            outTray[1].setTag(8);
            outTray[2].setTag(3);
        }

        else if (GameUtilities.gameLevel == 3){
            outTray[3].setImageResource(R.drawable.circle);
            outTray[4].setImageResource(R.drawable.triangle);
            for(int x=3; x<=4; x++){
                inTray[x].setVisibility(View.VISIBLE);
                outTray[x].setVisibility(View.VISIBLE);
                outTray[x].setOnTouchListener(new MyTouchListener());
            }
            outTray[3].setTag(4);
            outTray[4].setTag(3);
        }

        else if (GameUtilities.gameLevel == 4){
            outTray[5].setImageResource(R.drawable.square);
            outTray[6].setImageResource(R.drawable.circle);
            outTray[7].setImageResource(R.drawable.pentagon);
            for(int x=5; x<=7; x++){
                inTray[x].setVisibility(View.VISIBLE);
                outTray[x].setVisibility(View.VISIBLE);
                outTray[x].setOnTouchListener(new MyTouchListener());
            }
            outTray[5].setTag(2);
            outTray[6].setTag(5);
            outTray[7].setTag(1);
        }

        else if (GameUtilities.gameLevel == 5){
            outTray[8].setImageResource(R.drawable.circle);
            outTray[9].setImageResource(R.drawable.pentagon);
            outTray[10].setImageResource(R.drawable.triangle);
            for(int x=8; x<=10; x++){
                inTray[x].setVisibility(View.VISIBLE);
                outTray[x].setVisibility(View.VISIBLE);
                outTray[x].setOnTouchListener(new MyTouchListener());
            }
            outTray[8].setTag(6);
            outTray[9].setTag(4);
            outTray[10].setTag(5);
        }

        else if (GameUtilities.gameLevel == 6){
            outTray[11].setImageResource(R.drawable.square);
            outTray[12].setImageResource(R.drawable.triangle);
            outTray[13].setImageResource(R.drawable.circle);
            for(int x=11; x<=13; x++){
                inTray[x].setVisibility(View.VISIBLE);
                outTray[x].setVisibility(View.VISIBLE);
                outTray[x].setOnTouchListener(new MyTouchListener());
            }
            outTray[11].setTag(9);
            outTray[12].setTag(7);
            outTray[13].setTag(10);
        }

        else if (GameUtilities.gameLevel == 7){
            outTray[14].setImageResource(R.drawable.triangle);
            outTray[15].setImageResource(R.drawable.square);
            outTray[16].setImageResource(R.drawable.circle);
            outTray[17].setImageResource(R.drawable.pentagon);
            for(int x=14; x<=17; x++){
                inTray[x].setVisibility(View.VISIBLE);
                outTray[x].setVisibility(View.VISIBLE);
                outTray[x].setOnTouchListener(new MyTouchListener());
            }
            outTray[14].setTag(6);
            outTray[15].setTag(3);
            outTray[16].setTag(4);
            outTray[17].setTag(10);
        }

        else if (GameUtilities.gameLevel == 8){
            outTray[18].setImageResource(R.drawable.square);
            outTray[19].setImageResource(R.drawable.pentagon);
            outTray[20].setImageResource(R.drawable.circle);
            outTray[21].setImageResource(R.drawable.triangle);
            for(int x=18; x<=21; x++){
                inTray[x].setVisibility(View.VISIBLE);
                outTray[x].setVisibility(View.VISIBLE);
                outTray[x].setOnTouchListener(new MyTouchListener());
            }
            outTray[18].setTag(9);
            outTray[19].setTag(7);
            outTray[20].setTag(5);
            outTray[21].setTag(10);
        }

        else if (GameUtilities.gameLevel == 9){
            outTray[22].setImageResource(R.drawable.circle);
            outTray[23].setImageResource(R.drawable.triangle);
            outTray[24].setImageResource(R.drawable.octagon);
            outTray[25].setImageResource(R.drawable.square);
            outTray[26].setImageResource(R.drawable.pentagon);
            for(int x=22; x<=26; x++){
                inTray[x].setVisibility(View.VISIBLE);
                outTray[x].setVisibility(View.VISIBLE);
                outTray[x].setOnTouchListener(new MyTouchListener());
            }
            outTray[22].setTag(6);
            outTray[23].setTag(3);
            outTray[24].setTag(7);
            outTray[25].setTag(4);
            outTray[26].setTag(10);
        }

    }

    //Timer
    public void startTime(){


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeView.setText("09");
            }
        },1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeView.setText("08");
            }
        },2000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeView.setText("07");
            }
        },3000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeView.setText("06");
            }
        },4000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeView.setText("05");
            }
        },5000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeView.setText("04");
            }
        },6000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeView.setText("03");
            }
        },7000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeView.setText("02");
            }
        },8000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeView.setText("01");
            }
        },9000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                timeView.setText("00");
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

    //Count correct moves
    public void correct(){

            correct_count++;

        if (GameUtilities.gameLevel == 1){
            handler.removeCallbacksAndMessages(null);
            nextBtn.setVisibility(View.VISIBLE);
            GameUtilities.score_dad = 5.0;
            status = false;
        }

        else if (GameUtilities.gameLevel == 2 && correct_count == 2){
            handler.removeCallbacksAndMessages(null);
            nextBtn.setVisibility(View.VISIBLE);
            GameUtilities.score_dad = 10.0;
            status = false;
        }

        else if (GameUtilities.gameLevel == 3 && correct_count == 2){
            handler.removeCallbacksAndMessages(null);
            nextBtn.setVisibility(View.VISIBLE);
            GameUtilities.score_dad = 15.0;
            status = false;
        }

        else if (GameUtilities.gameLevel == 4 && correct_count == 3){
            handler.removeCallbacksAndMessages(null);
            nextBtn.setVisibility(View.VISIBLE);
            GameUtilities.score_dad = 20.0;
            status = false;
        }

        else if (GameUtilities.gameLevel == 5 && correct_count == 3){
            handler.removeCallbacksAndMessages(null);
            nextBtn.setVisibility(View.VISIBLE);
            GameUtilities.score_dad = 25.0;
            status = false;
        }

        else if (GameUtilities.gameLevel == 6 && correct_count == 3){
            handler.removeCallbacksAndMessages(null);
            nextBtn.setVisibility(View.VISIBLE);
            GameUtilities.score_dad = 30.0;
            status = false;
        }

        else if (GameUtilities.gameLevel == 7 && correct_count == 4){
            handler.removeCallbacksAndMessages(null);
            nextBtn.setVisibility(View.VISIBLE);
            GameUtilities.score_dad = 35.0;
            status = false;
        }

        else if (GameUtilities.gameLevel == 8 && correct_count == 4){
            handler.removeCallbacksAndMessages(null);
            nextBtn.setVisibility(View.VISIBLE);
            GameUtilities.score_dad = 40.0;
            status = false;
        }

        else if (GameUtilities.gameLevel == 9 && correct_count == 5){
            handler.removeCallbacksAndMessages(null);
            nextBtn.setVisibility(View.VISIBLE);
            GameUtilities.score_dad = 45.0;
            status = false;
        }

            correct.setText(Integer.toString(correct_count));

    }

    public void saveData(){

        String score = String.valueOf(GameUtilities.score_dad - 5);
        String attempt = String.valueOf(++GameUtilities.attemptNo);
        String gameId = String.valueOf(GameUtilities.gameNumber);

        String temp_id = databaseGameData.push().getKey();

        GameData game_data = new GameData( attempt, gameId, score );

        databaseGameData.child(temp_id).setValue(game_data);

    }

    public void onBackPressed(){
        super.onBackPressed();
//        GameUtilities.stopPlaying();
//        GameUtilities.mediaPlayer=null;
        handler.removeCallbacksAndMessages(null);

        String score = String.valueOf(GameUtilities.score_dad);
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
