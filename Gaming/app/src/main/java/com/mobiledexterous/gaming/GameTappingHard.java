package com.mobiledexterous.gaming;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameTappingHard extends AppCompatActivity {

    Handler handler = new Handler();

    boolean timer_status = true;
    boolean status = true;

    int dot_count = -1;
    int visible_count =0;
    int failCount = 0;

    ImageView mask, dot_0, nextBtn, help_view;

    ImageView dot_1[] = new ImageView[6];
    ImageView dot_2[] = new ImageView[6];
    ImageView dot_3[] = new ImageView[6];
    ImageView dot_4[] = new ImageView[6];

    int color_code = 0;
    int colorArray[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    TextView timer;

    DatabaseReference databaseGameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tapping_hard);

        String ref = "User/Gaming/"+GameUtilities.patientId;

        databaseGameData = FirebaseDatabase.getInstance().getReference(ref);

        timer = (TextView) findViewById(R.id.timer);
        mask = (ImageView) findViewById(R.id.t_mask);
        nextBtn = (ImageView) findViewById(R.id.nextBtn);
        help_view = (ImageView) findViewById(R.id.help_view);

        dot_0 = (ImageView) findViewById(R.id.dot_0);

        dot_1[0] = (ImageView) findViewById(R.id.dot_1_1);
        dot_1[1] = (ImageView) findViewById(R.id.dot_1_2);
        dot_1[2] = (ImageView) findViewById(R.id.dot_1_3);
        dot_1[3] = (ImageView) findViewById(R.id.dot_1_4);
        dot_1[4] = (ImageView) findViewById(R.id.dot_1_5);
        dot_1[5] = (ImageView) findViewById(R.id.dot_1_6);

        dot_2[0] = (ImageView) findViewById(R.id.dot_2_1);
        dot_2[1] = (ImageView) findViewById(R.id.dot_2_2);
        dot_2[2] = (ImageView) findViewById(R.id.dot_2_3);
        dot_2[3] = (ImageView) findViewById(R.id.dot_2_4);
        dot_2[4] = (ImageView) findViewById(R.id.dot_2_5);
        dot_2[5] = (ImageView) findViewById(R.id.dot_2_6);

        dot_3[0] = (ImageView) findViewById(R.id.dot_3_1);
        dot_3[1] = (ImageView) findViewById(R.id.dot_3_2);
        dot_3[2] = (ImageView) findViewById(R.id.dot_3_3);
        dot_3[3] = (ImageView) findViewById(R.id.dot_3_4);
        dot_3[4] = (ImageView) findViewById(R.id.dot_3_5);
        dot_3[5] = (ImageView) findViewById(R.id.dot_3_6);

        dot_4[0] = (ImageView) findViewById(R.id.dot_4_1);
        dot_4[1] = (ImageView) findViewById(R.id.dot_4_2);
        dot_4[2] = (ImageView) findViewById(R.id.dot_4_3);
        dot_4[3] = (ImageView) findViewById(R.id.dot_4_4);
        dot_4[4] = (ImageView) findViewById(R.id.dot_4_5);
        dot_4[5] = (ImageView) findViewById(R.id.dot_4_6);

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

                    nextBtn.setVisibility(View.INVISIBLE);
                    GameUtilities.gameLevel++;
                    failCount = 0;
                    loadLevel();

            }

            else if (helpBtnAct == 0)
                help_view.setVisibility(View.INVISIBLE);

            //Help
            else if (ct.closeMatch(Color.parseColor("#583b1c"), touchColor, tolerance)){
                if (helpBtnAct == 4)
                    help_view.setVisibility(View.VISIBLE);
            }

            else if (timer_status ) {
                timer_status = false;
                startTimer();
            }

            //Retry
            else if (ct.closeMatch(Color.parseColor("#1b10ac"), touchColor, tolerance)){
                loadLevel();
            }

            else if (status ){

                // Dot_1_1
                if (ct.closeMatch(Color.parseColor("#f21111"), touchColor, tolerance) && ( colorArray[0] == color_code)){
                    dot_1[0].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_1_2
                else if (ct.closeMatch(Color.parseColor("#ff00f6"), touchColor, tolerance) && ( colorArray[1] == color_code)){
                    dot_1[1].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_1_3
                else if (ct.closeMatch(Color.parseColor("#c07fe9"), touchColor, tolerance) && ( colorArray[2] == color_code)){
                    dot_1[2].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_1_4
                else if (ct.closeMatch(Color.parseColor("#009cff"), touchColor, tolerance) && ( colorArray[3] == color_code)){
                    dot_1[3].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_1_5
                else if (ct.closeMatch(Color.parseColor("#05e64f"), touchColor, tolerance) && ( colorArray[4] == color_code)){
                    dot_1[4].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_1_6
                else if (ct.closeMatch(Color.parseColor("#ff9600"), touchColor, tolerance) && ( colorArray[5] == color_code)){
                    dot_1[5].setVisibility(View.INVISIBLE);
                    correct();}

                // Dot_2_1
                else if (ct.closeMatch(Color.parseColor("#f7e300"), touchColor, tolerance) && ( colorArray[6] == color_code)){
                    dot_2[0].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_2_2
                else if (ct.closeMatch(Color.parseColor("#ec75b0"), touchColor, tolerance) && ( colorArray[7] == color_code)){
                    dot_2[1].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_2_3
                else if (ct.closeMatch(Color.parseColor("#895da5"), touchColor, tolerance) && ( colorArray[8] == color_code)){
                    dot_2[2].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_2_4
                else if (ct.closeMatch(Color.parseColor("#ff3900"), touchColor, tolerance) && ( colorArray[9] == color_code)){
                    dot_2[3].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_2_5
                else if (ct.closeMatch(Color.parseColor("#077b2d"), touchColor, tolerance) && ( colorArray[10] == color_code)){
                    dot_2[4].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_2_6
                else if (ct.closeMatch(Color.parseColor("#8e601e"), touchColor, tolerance) && ( colorArray[11] == color_code)){
                    dot_2[5].setVisibility(View.INVISIBLE);
                    correct();}

                // Dot_3_1
                else if (ct.closeMatch(Color.parseColor("#8a6561"), touchColor, tolerance) && ( colorArray[12] == color_code)){
                    dot_3[0].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_3_2
                else if (ct.closeMatch(Color.parseColor("#0aba5d"), touchColor, tolerance) && ( colorArray[13] == color_code)){
                    dot_3[1].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_3_3
                else if (ct.closeMatch(Color.parseColor("#400624"), touchColor, tolerance) && ( colorArray[14] == color_code)){
                    dot_3[2].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_3_4
                else if (ct.closeMatch(Color.parseColor("#6f4040"), touchColor, tolerance) && ( colorArray[15] == color_code)){
                    dot_3[3].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_3_5
                else if (ct.closeMatch(Color.parseColor("#2f02f1"), touchColor, tolerance) && ( colorArray[16] == color_code)){
                    dot_3[4].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_3_6
                else if (ct.closeMatch(Color.parseColor("#02d1ff"), touchColor, tolerance) && ( colorArray[17] == color_code)){
                    dot_3[5].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_4_1
                else if (ct.closeMatch(Color.parseColor("#907806"), touchColor, tolerance) && ( colorArray[18] == color_code)){
                    dot_4[0].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_4_2
                else if (ct.closeMatch(Color.parseColor("#3d5a0f"), touchColor, tolerance) && ( colorArray[19] == color_code)){
                    dot_4[1].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_4_3
                else if (ct.closeMatch(Color.parseColor("#3c39c5"), touchColor, tolerance) && ( colorArray[20] == color_code)){
                    dot_4[2].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_4_4
                else if (ct.closeMatch(Color.parseColor("#c6e671"), touchColor, tolerance) && ( colorArray[21] == color_code)){
                    dot_4[3].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_4_5
                else if (ct.closeMatch(Color.parseColor("#898280"), touchColor, tolerance) && ( colorArray[22] == color_code)){
                    dot_4[4].setVisibility(View.INVISIBLE);
                    correct();}
                // Dot_4_6
                else if (ct.closeMatch(Color.parseColor("#b9e005"), touchColor, tolerance) && ( colorArray[23] == color_code)){
                    dot_4[5].setVisibility(View.INVISIBLE);
                    correct();}

                else
                    playSound("pop.mp3");

            }

            return false;
        }
    }//OnCreate

    //Load Level
    public void loadLevel(){

        nextBtn.setVisibility(View.INVISIBLE);
        handler.removeCallbacksAndMessages(null);
        timer.setText("10");
        status = true;
        timer_status = true;



        if( GameUtilities.gameLevel == 1){
            dot_0.setImageResource(R.drawable.tap_yellow);
            color_code = 1;

            dot_1[0].setImageResource(R.drawable.tap_yellow);
            dot_1[1].setImageResource(R.drawable.tap_yellow);
            dot_1[2].setImageResource(R.drawable.tap_yellow);
            dot_1[3].setImageResource(R.drawable.tap_yellow);
            dot_1[4].setImageResource(R.drawable.tap_yellow);
            dot_1[5].setImageResource(R.drawable.tap_yellow);
            dot_2[0].setImageResource(R.drawable.tap_green);
            dot_2[1].setImageResource(R.drawable.tap_yellow);
            dot_2[2].setImageResource(R.drawable.tap_yellow);
            dot_2[3].setImageResource(R.drawable.tap_yellow);
            dot_2[4].setImageResource(R.drawable.tap_yellow);
            dot_2[5].setImageResource(R.drawable.tap_yellow);
            dot_3[0].setImageResource(R.drawable.tap_green);
            dot_3[1].setImageResource(R.drawable.tap_green);
            dot_3[2].setImageResource(R.drawable.tap_green);
            dot_3[3].setImageResource(R.drawable.tap_green);
            dot_3[4].setImageResource(R.drawable.tap_green);
            dot_3[5].setImageResource(R.drawable.tap_yellow);
            dot_4[0].setImageResource(R.drawable.tap_green);
            dot_4[1].setImageResource(R.drawable.tap_green);
            dot_4[2].setImageResource(R.drawable.tap_green);
            dot_4[3].setImageResource(R.drawable.tap_green);
            dot_4[4].setImageResource(R.drawable.tap_green);
            dot_4[5].setImageResource(R.drawable.tap_green);

            colorArray[0] = 1;
            colorArray[1] = 1;
            colorArray[2] = 1;
            colorArray[3] = 1;
            colorArray[4] = 1;
            colorArray[5] = 1;
            colorArray[6] = 3;
            colorArray[7] = 1;
            colorArray[8] = 1;
            colorArray[9] = 1;
            colorArray[10] = 1;
            colorArray[11] = 1;
            colorArray[12] = 3;
            colorArray[13] = 3;
            colorArray[14] = 3;
            colorArray[15] = 3;
            colorArray[16] = 3;
            colorArray[17] = 1;
            colorArray[18] = 3;
            colorArray[19] = 3;
            colorArray[20] = 3;
            colorArray[21] = 3;
            colorArray[22] = 3;
            colorArray[23] = 3;

        }

        else if( GameUtilities.gameLevel == 2){
            dot_0.setImageResource(R.drawable.tap_red);
            color_code = 4;

            dot_1[0].setImageResource(R.drawable.tap_blue);
            dot_1[1].setImageResource(R.drawable.tap_blue);
            dot_1[2].setImageResource(R.drawable.tap_red);
            dot_1[3].setImageResource(R.drawable.tap_red);
            dot_1[4].setImageResource(R.drawable.tap_red);
            dot_1[5].setImageResource(R.drawable.tap_red);
            dot_2[0].setImageResource(R.drawable.tap_blue);
            dot_2[1].setImageResource(R.drawable.tap_blue);
            dot_2[2].setImageResource(R.drawable.tap_blue);
            dot_2[3].setImageResource(R.drawable.tap_red);
            dot_2[4].setImageResource(R.drawable.tap_red);
            dot_2[5].setImageResource(R.drawable.tap_red);
            dot_3[0].setImageResource(R.drawable.tap_blue);
            dot_3[1].setImageResource(R.drawable.tap_blue);
            dot_3[2].setImageResource(R.drawable.tap_blue);
            dot_3[3].setImageResource(R.drawable.tap_red);
            dot_3[4].setImageResource(R.drawable.tap_red);
            dot_3[5].setImageResource(R.drawable.tap_red);
            dot_4[0].setImageResource(R.drawable.tap_blue);
            dot_4[1].setImageResource(R.drawable.tap_blue);
            dot_4[2].setImageResource(R.drawable.tap_blue);
            dot_4[3].setImageResource(R.drawable.tap_blue);
            dot_4[4].setImageResource(R.drawable.tap_red);
            dot_4[5].setImageResource(R.drawable.tap_red);

            colorArray[0] = 2;
            colorArray[1] = 2;
            colorArray[2] = 4;
            colorArray[3] = 4;
            colorArray[4] = 4;
            colorArray[5] = 4;
            colorArray[6] = 2;
            colorArray[7] = 2;
            colorArray[8] = 2;
            colorArray[9] = 4;
            colorArray[10] = 4;
            colorArray[11] = 4;
            colorArray[12] = 2;
            colorArray[13] = 2;
            colorArray[14] = 2;
            colorArray[15] = 4;
            colorArray[16] = 4;
            colorArray[17] = 4;
            colorArray[18] = 2;
            colorArray[19] = 2;
            colorArray[20] = 2;
            colorArray[21] = 2;
            colorArray[22] = 4;
            colorArray[23] = 4;

        }

        else if( GameUtilities.gameLevel == 3){
            dot_0.setImageResource(R.drawable.tap_yellow);
            color_code = 1;

            dot_1[0].setImageResource(R.drawable.tap_yellow);
            dot_1[1].setImageResource(R.drawable.tap_blue);
            dot_1[2].setImageResource(R.drawable.tap_yellow);
            dot_1[3].setImageResource(R.drawable.tap_blue);
            dot_1[4].setImageResource(R.drawable.tap_yellow);
            dot_1[5].setImageResource(R.drawable.tap_blue);
            dot_2[0].setImageResource(R.drawable.tap_blue);
            dot_2[1].setImageResource(R.drawable.tap_yellow);
            dot_2[2].setImageResource(R.drawable.tap_blue);
            dot_2[3].setImageResource(R.drawable.tap_yellow);
            dot_2[4].setImageResource(R.drawable.tap_blue);
            dot_2[5].setImageResource(R.drawable.tap_yellow);
            dot_3[0].setImageResource(R.drawable.tap_yellow);
            dot_3[1].setImageResource(R.drawable.tap_blue);
            dot_3[2].setImageResource(R.drawable.tap_yellow);
            dot_3[3].setImageResource(R.drawable.tap_blue);
            dot_3[4].setImageResource(R.drawable.tap_yellow);
            dot_3[5].setImageResource(R.drawable.tap_blue);
            dot_4[0].setImageResource(R.drawable.tap_blue);
            dot_4[1].setImageResource(R.drawable.tap_yellow);
            dot_4[2].setImageResource(R.drawable.tap_blue);
            dot_4[3].setImageResource(R.drawable.tap_yellow);
            dot_4[4].setImageResource(R.drawable.tap_blue);
            dot_4[5].setImageResource(R.drawable.tap_yellow);

            colorArray[0] = 1;
            colorArray[1] = 2;
            colorArray[2] = 1;
            colorArray[3] = 2;
            colorArray[4] = 1;
            colorArray[5] = 2;
            colorArray[6] = 2;
            colorArray[7] = 1;
            colorArray[8] = 2;
            colorArray[9] = 1;
            colorArray[10] = 2;
            colorArray[11] = 1;
            colorArray[12] = 1;
            colorArray[13] = 2;
            colorArray[14] = 1;
            colorArray[15] = 2;
            colorArray[16] = 1;
            colorArray[17] = 2;
            colorArray[18] = 2;
            colorArray[19] = 1;
            colorArray[20] = 2;
            colorArray[21] = 1;
            colorArray[22] = 2;
            colorArray[23] = 1;

        }

        else if( GameUtilities.gameLevel == 4){
            dot_0.setImageResource(R.drawable.tap_green);
            color_code = 3;

            dot_1[0].setImageResource(R.drawable.tap_red);
            dot_1[1].setImageResource(R.drawable.tap_green);
            dot_1[2].setImageResource(R.drawable.tap_yellow);
            dot_1[3].setImageResource(R.drawable.tap_red);
            dot_1[4].setImageResource(R.drawable.tap_green);
            dot_1[5].setImageResource(R.drawable.tap_yellow);
            dot_2[0].setImageResource(R.drawable.tap_red);
            dot_2[1].setImageResource(R.drawable.tap_green);
            dot_2[2].setImageResource(R.drawable.tap_yellow);
            dot_2[3].setImageResource(R.drawable.tap_red);
            dot_2[4].setImageResource(R.drawable.tap_green);
            dot_2[5].setImageResource(R.drawable.tap_yellow);
            dot_3[0].setImageResource(R.drawable.tap_red);
            dot_3[1].setImageResource(R.drawable.tap_green);
            dot_3[2].setImageResource(R.drawable.tap_yellow);
            dot_3[3].setImageResource(R.drawable.tap_red);
            dot_3[4].setImageResource(R.drawable.tap_green);
            dot_3[5].setImageResource(R.drawable.tap_yellow);
            dot_4[0].setImageResource(R.drawable.tap_red);
            dot_4[1].setImageResource(R.drawable.tap_green);
            dot_4[2].setImageResource(R.drawable.tap_yellow);
            dot_4[3].setImageResource(R.drawable.tap_red);
            dot_4[4].setImageResource(R.drawable.tap_green);
            dot_4[5].setImageResource(R.drawable.tap_yellow);

            colorArray[0] = 4;
            colorArray[1] = 3;
            colorArray[2] = 1;
            colorArray[3] = 4;
            colorArray[4] = 3;
            colorArray[5] = 1;

            colorArray[6] = 4;
            colorArray[7] = 3;
            colorArray[8] = 1;
            colorArray[9] = 4;
            colorArray[10] = 3;
            colorArray[11] = 1;

            colorArray[12] = 4;
            colorArray[13] = 3;
            colorArray[14] = 1;
            colorArray[15] = 4;
            colorArray[16] = 3;
            colorArray[17] = 1;

            colorArray[18] = 4;
            colorArray[19] = 3;
            colorArray[20] = 1;
            colorArray[21] = 4;
            colorArray[22] = 3;
            colorArray[23] = 1;

        }

        else if( GameUtilities.gameLevel == 5){
            dot_0.setImageResource(R.drawable.tap_red);
            color_code = 4;

            dot_1[0].setImageResource(R.drawable.tap_green);
            dot_1[1].setImageResource(R.drawable.tap_yellow);
            dot_1[2].setImageResource(R.drawable.tap_red);
            dot_1[3].setImageResource(R.drawable.tap_red);
            dot_1[4].setImageResource(R.drawable.tap_green);
            dot_1[5].setImageResource(R.drawable.tap_yellow);
            dot_2[0].setImageResource(R.drawable.tap_yellow);
            dot_2[1].setImageResource(R.drawable.tap_green);
            dot_2[2].setImageResource(R.drawable.tap_red);
            dot_2[3].setImageResource(R.drawable.tap_red);
            dot_2[4].setImageResource(R.drawable.tap_yellow);
            dot_2[5].setImageResource(R.drawable.tap_green);
            dot_3[0].setImageResource(R.drawable.tap_green);
            dot_3[1].setImageResource(R.drawable.tap_yellow);
            dot_3[2].setImageResource(R.drawable.tap_red);
            dot_3[3].setImageResource(R.drawable.tap_red);
            dot_3[4].setImageResource(R.drawable.tap_green);
            dot_3[5].setImageResource(R.drawable.tap_yellow);
            dot_4[0].setImageResource(R.drawable.tap_yellow);
            dot_4[1].setImageResource(R.drawable.tap_green);
            dot_4[2].setImageResource(R.drawable.tap_red);
            dot_4[3].setImageResource(R.drawable.tap_red);
            dot_4[4].setImageResource(R.drawable.tap_yellow);
            dot_4[5].setImageResource(R.drawable.tap_green);

            colorArray[0] = 3;
            colorArray[1] = 1;
            colorArray[2] = 4;
            colorArray[3] = 4;
            colorArray[4] = 3;
            colorArray[5] = 1;

            colorArray[6] = 1;
            colorArray[7] = 3;
            colorArray[8] = 4;
            colorArray[9] = 4;
            colorArray[10] = 1;
            colorArray[11] = 3;

            colorArray[12] = 3;
            colorArray[13] = 1;
            colorArray[14] = 4;
            colorArray[15] = 4;
            colorArray[16] = 3;
            colorArray[17] = 1;

            colorArray[18] = 1;
            colorArray[19] = 3;
            colorArray[20] = 4;
            colorArray[21] = 4;
            colorArray[22] = 1;
            colorArray[23] = 3;

        }

        else if( GameUtilities.gameLevel == 6){
            dot_0.setImageResource(R.drawable.tap_blue);
            color_code = 2;

            dot_1[0].setImageResource(R.drawable.tap_blue);
            dot_1[1].setImageResource(R.drawable.tap_green);
            dot_1[2].setImageResource(R.drawable.tap_blue);
            dot_1[3].setImageResource(R.drawable.tap_red);
            dot_1[4].setImageResource(R.drawable.tap_green);
            dot_1[5].setImageResource(R.drawable.tap_red);
            dot_2[0].setImageResource(R.drawable.tap_red);
            dot_2[1].setImageResource(R.drawable.tap_blue);
            dot_2[2].setImageResource(R.drawable.tap_green);
            dot_2[3].setImageResource(R.drawable.tap_blue);
            dot_2[4].setImageResource(R.drawable.tap_red);
            dot_2[5].setImageResource(R.drawable.tap_green);
            dot_3[0].setImageResource(R.drawable.tap_green);
            dot_3[1].setImageResource(R.drawable.tap_red);
            dot_3[2].setImageResource(R.drawable.tap_blue);
            dot_3[3].setImageResource(R.drawable.tap_green);
            dot_3[4].setImageResource(R.drawable.tap_blue);
            dot_3[5].setImageResource(R.drawable.tap_red);
            dot_4[0].setImageResource(R.drawable.tap_red);
            dot_4[1].setImageResource(R.drawable.tap_green);
            dot_4[2].setImageResource(R.drawable.tap_red);
            dot_4[3].setImageResource(R.drawable.tap_blue);
            dot_4[4].setImageResource(R.drawable.tap_green);
            dot_4[5].setImageResource(R.drawable.tap_blue);

            colorArray[0] = 2;
            colorArray[1] = 3;
            colorArray[2] = 2;
            colorArray[3] = 4;
            colorArray[4] = 3;
            colorArray[5] = 4;

            colorArray[6] = 4;
            colorArray[7] = 2;
            colorArray[8] = 3;
            colorArray[9] = 2;
            colorArray[10] = 4;
            colorArray[11] = 3;

            colorArray[12] = 3;
            colorArray[13] = 4;
            colorArray[14] = 2;
            colorArray[15] = 3;
            colorArray[16] = 2;
            colorArray[17] = 4;

            colorArray[18] = 4;
            colorArray[19] = 3;
            colorArray[20] = 4;
            colorArray[21] = 2;
            colorArray[22] = 3;
            colorArray[23] = 2;

        }

        else if( GameUtilities.gameLevel == 7){
            dot_0.setImageResource(R.drawable.tap_blue);
            color_code = 2;

            dot_1[0].setImageResource(R.drawable.tap_blue);
            dot_1[1].setImageResource(R.drawable.tap_blue);
            dot_1[2].setImageResource(R.drawable.tap_blue);
            dot_1[3].setImageResource(R.drawable.tap_yellow);
            dot_1[4].setImageResource(R.drawable.tap_red);
            dot_1[5].setImageResource(R.drawable.tap_red);
            dot_2[0].setImageResource(R.drawable.tap_yellow);
            dot_2[1].setImageResource(R.drawable.tap_green);
            dot_2[2].setImageResource(R.drawable.tap_green);
            dot_2[3].setImageResource(R.drawable.tap_green);
            dot_2[4].setImageResource(R.drawable.tap_yellow);
            dot_2[5].setImageResource(R.drawable.tap_red);
            dot_3[0].setImageResource(R.drawable.tap_red);
            dot_3[1].setImageResource(R.drawable.tap_yellow);
            dot_3[2].setImageResource(R.drawable.tap_blue);
            dot_3[3].setImageResource(R.drawable.tap_blue);
            dot_3[4].setImageResource(R.drawable.tap_blue);
            dot_3[5].setImageResource(R.drawable.tap_yellow);
            dot_4[0].setImageResource(R.drawable.tap_red);
            dot_4[1].setImageResource(R.drawable.tap_red);
            dot_4[2].setImageResource(R.drawable.tap_yellow);
            dot_4[3].setImageResource(R.drawable.tap_green);
            dot_4[4].setImageResource(R.drawable.tap_green);
            dot_4[5].setImageResource(R.drawable.tap_green);

            colorArray[0] = 2;
            colorArray[1] = 2;
            colorArray[2] = 2;
            colorArray[3] = 1;
            colorArray[4] = 4;
            colorArray[5] = 4;

            colorArray[6] = 1;
            colorArray[7] = 3;
            colorArray[8] = 3;
            colorArray[9] = 3;
            colorArray[10] = 1;
            colorArray[11] = 4;

            colorArray[12] = 4;
            colorArray[13] = 1;
            colorArray[14] = 2;
            colorArray[15] = 2;
            colorArray[16] = 2;
            colorArray[17] = 1;

            colorArray[18] = 4;
            colorArray[19] = 4;
            colorArray[20] = 1;
            colorArray[21] = 3;
            colorArray[22] = 3;
            colorArray[23] = 3;

        }

        else if( GameUtilities.gameLevel == 8){
            dot_0.setImageResource(R.drawable.tap_red);
            color_code = 4;

            dot_1[0].setImageResource(R.drawable.tap_yellow);
            dot_1[1].setImageResource(R.drawable.tap_blue);
            dot_1[2].setImageResource(R.drawable.tap_yellow);
            dot_1[3].setImageResource(R.drawable.tap_blue);
            dot_1[4].setImageResource(R.drawable.tap_yellow);
            dot_1[5].setImageResource(R.drawable.tap_blue);
            dot_2[0].setImageResource(R.drawable.tap_green);
            dot_2[1].setImageResource(R.drawable.tap_red);
            dot_2[2].setImageResource(R.drawable.tap_green);
            dot_2[3].setImageResource(R.drawable.tap_red);
            dot_2[4].setImageResource(R.drawable.tap_green);
            dot_2[5].setImageResource(R.drawable.tap_red);
            dot_3[0].setImageResource(R.drawable.tap_blue);
            dot_3[1].setImageResource(R.drawable.tap_yellow);
            dot_3[2].setImageResource(R.drawable.tap_blue);
            dot_3[3].setImageResource(R.drawable.tap_yellow);
            dot_3[4].setImageResource(R.drawable.tap_blue);
            dot_3[5].setImageResource(R.drawable.tap_yellow);
            dot_4[0].setImageResource(R.drawable.tap_red);
            dot_4[1].setImageResource(R.drawable.tap_green);
            dot_4[2].setImageResource(R.drawable.tap_red);
            dot_4[3].setImageResource(R.drawable.tap_green);
            dot_4[4].setImageResource(R.drawable.tap_red);
            dot_4[5].setImageResource(R.drawable.tap_green);

            colorArray[0] = 1;
            colorArray[1] = 2;
            colorArray[2] = 1;
            colorArray[3] = 2;
            colorArray[4] = 1;
            colorArray[5] = 2;

            colorArray[6] = 3;
            colorArray[7] = 4;
            colorArray[8] = 3;
            colorArray[9] = 4;
            colorArray[10] = 3;
            colorArray[11] = 4;

            colorArray[12] = 2;
            colorArray[13] = 1;
            colorArray[14] = 2;
            colorArray[15] = 1;
            colorArray[16] = 2;
            colorArray[17] = 1;

            colorArray[18] = 4;
            colorArray[19] = 3;
            colorArray[20] = 4;
            colorArray[21] = 3;
            colorArray[22] = 4;
            colorArray[23] = 3;

        }

        else if( GameUtilities.gameLevel == 9){
            dot_0.setImageResource(R.drawable.tap_yellow);
            color_code = 1;

            dot_1[0].setImageResource(R.drawable.tap_red);
            dot_1[1].setImageResource(R.drawable.tap_green);
            dot_1[2].setImageResource(R.drawable.tap_yellow);
            dot_1[3].setImageResource(R.drawable.tap_yellow);
            dot_1[4].setImageResource(R.drawable.tap_red);
            dot_1[5].setImageResource(R.drawable.tap_blue);
            dot_2[0].setImageResource(R.drawable.tap_green);
            dot_2[1].setImageResource(R.drawable.tap_yellow);
            dot_2[2].setImageResource(R.drawable.tap_red);
            dot_2[3].setImageResource(R.drawable.tap_blue);
            dot_2[4].setImageResource(R.drawable.tap_green);
            dot_2[5].setImageResource(R.drawable.tap_yellow);
            dot_3[0].setImageResource(R.drawable.tap_blue);
            dot_3[1].setImageResource(R.drawable.tap_blue);
            dot_3[2].setImageResource(R.drawable.tap_green);
            dot_3[3].setImageResource(R.drawable.tap_red);
            dot_3[4].setImageResource(R.drawable.tap_yellow);
            dot_3[5].setImageResource(R.drawable.tap_green);
            dot_4[0].setImageResource(R.drawable.tap_yellow);
            dot_4[1].setImageResource(R.drawable.tap_red);
            dot_4[2].setImageResource(R.drawable.tap_blue);
            dot_4[3].setImageResource(R.drawable.tap_green);
            dot_4[4].setImageResource(R.drawable.tap_blue);
            dot_4[5].setImageResource(R.drawable.tap_red);

            colorArray[0] = 4;
            colorArray[1] = 3;
            colorArray[2] = 1;
            colorArray[3] = 1;
            colorArray[4] = 4;
            colorArray[5] = 2;

            colorArray[6] = 3;
            colorArray[7] = 1;
            colorArray[8] = 4;
            colorArray[9] = 2;
            colorArray[10] = 3;
            colorArray[11] = 1;

            colorArray[12] = 2;
            colorArray[13] = 2;
            colorArray[14] = 3;
            colorArray[15] = 4;
            colorArray[16] = 1;
            colorArray[17] = 3;

            colorArray[18] = 1;
            colorArray[19] = 4;
            colorArray[20] = 2;
            colorArray[21] = 3;
            colorArray[22] = 2;
            colorArray[23] = 4;

        }

        for (int x=0; x<=5; x++){
            dot_1[x].setVisibility(View.VISIBLE);
            dot_2[x].setVisibility(View.VISIBLE);
            dot_3[x].setVisibility(View.VISIBLE);
            dot_4[x].setVisibility(View.VISIBLE);
        }

        dot_count = 24;

    }

    //If correct dot touch
    public void correct(){

        dotVisibility();

        if (visible_count == 0 ){
            handler.removeCallbacksAndMessages(null);
            if(GameUtilities.gameLevel != 9)
                nextBtn.setVisibility(View.VISIBLE);

            //Score
            if ( GameUtilities.gameLevel == 1 )
                GameUtilities.score_t = 69.5;
            else if ( GameUtilities.gameLevel == 2 )
                GameUtilities.score_t = 73.0;
            else if ( GameUtilities.gameLevel == 3 )
                GameUtilities.score_t = 76.5;
            else if ( GameUtilities.gameLevel == 4 )
                GameUtilities.score_t = 80.0;
            else if ( GameUtilities.gameLevel == 5 )
                GameUtilities.score_t = 83.5;
            else if ( GameUtilities.gameLevel == 6 )
                GameUtilities.score_t = 87.0;
            else if ( GameUtilities.gameLevel == 7 )
                GameUtilities.score_t = 90.5;
            else if ( GameUtilities.gameLevel == 8 )
                GameUtilities.score_t = 94.0;
            else if ( GameUtilities.gameLevel == 9 )
                GameUtilities.score_t = 100.0;

        }

        else if ( GameUtilities.gameLevel == 1){
            if (visible_count == 12){ dot_0.setImageResource(R.drawable.tap_green); color_code = 3; }
        }

        else if ( GameUtilities.gameLevel == 2){
            if (visible_count == 12){ dot_0.setImageResource(R.drawable.tap_blue); color_code = 2; }
        }

        else if ( GameUtilities.gameLevel == 3){
            if (visible_count == 12){ dot_0.setImageResource(R.drawable.tap_blue); color_code = 2; }
        }

        else if ( GameUtilities.gameLevel == 4){
            if (visible_count == 16){ dot_0.setImageResource(R.drawable.tap_red); color_code = 4; }
            else if (visible_count == 8){ dot_0.setImageResource(R.drawable.tap_yellow); color_code = 1; }
        }

        else if ( GameUtilities.gameLevel == 5){
            if (visible_count == 16){ dot_0.setImageResource(R.drawable.tap_yellow); color_code = 1; }
            else if (visible_count == 8){ dot_0.setImageResource(R.drawable.tap_green); color_code = 3; }
        }

        else if ( GameUtilities.gameLevel == 6){
            if (visible_count == 16){ dot_0.setImageResource(R.drawable.tap_red); color_code = 4; }
            else if (visible_count == 8){ dot_0.setImageResource(R.drawable.tap_green); color_code = 3; }
        }

        else if ( GameUtilities.gameLevel == 7){
            if (visible_count == 18){ dot_0.setImageResource(R.drawable.tap_yellow); color_code = 1; }
            else if (visible_count == 12){ dot_0.setImageResource(R.drawable.tap_red); color_code = 4; }
            else if (visible_count == 6){ dot_0.setImageResource(R.drawable.tap_green); color_code = 3; }
        }

        else if ( GameUtilities.gameLevel == 8){
            if (visible_count == 18){ dot_0.setImageResource(R.drawable.tap_blue); color_code = 2; }
            else if (visible_count == 12){ dot_0.setImageResource(R.drawable.tap_green); color_code = 3; }
            else if (visible_count == 6){ dot_0.setImageResource(R.drawable.tap_yellow); color_code = 1; }
        }

        else if ( GameUtilities.gameLevel == 9){
            if (visible_count == 18){ dot_0.setImageResource(R.drawable.tap_green); color_code = 3; }
            else if (visible_count == 12){ dot_0.setImageResource(R.drawable.tap_blue); color_code = 2; }
            else if (visible_count == 6){ dot_0.setImageResource(R.drawable.tap_red); color_code = 4; }
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

        visibleArray[0] = dot_1[0].getVisibility();
        visibleArray[1] = dot_1[1].getVisibility();
        visibleArray[2] = dot_1[2].getVisibility();
        visibleArray[3] = dot_1[3].getVisibility();
        visibleArray[4] = dot_1[4].getVisibility();
        visibleArray[5] = dot_1[5].getVisibility();
        visibleArray[6] = dot_2[0].getVisibility();
        visibleArray[7] = dot_2[1].getVisibility();
        visibleArray[8] = dot_2[2].getVisibility();
        visibleArray[9] = dot_2[3].getVisibility();
        visibleArray[10] = dot_2[4].getVisibility();
        visibleArray[11] = dot_2[5].getVisibility();
        visibleArray[12] = dot_3[0].getVisibility();
        visibleArray[13] = dot_3[1].getVisibility();
        visibleArray[14] = dot_3[2].getVisibility();
        visibleArray[15] = dot_3[3].getVisibility();
        visibleArray[16] = dot_3[4].getVisibility();
        visibleArray[17] = dot_3[5].getVisibility();
        visibleArray[18] = dot_4[0].getVisibility();
        visibleArray[19] = dot_4[1].getVisibility();
        visibleArray[20] = dot_4[2].getVisibility();
        visibleArray[21] = dot_4[3].getVisibility();
        visibleArray[22] = dot_4[4].getVisibility();
        visibleArray[23] = dot_4[5].getVisibility();

        for(int x=0; x<=23; x++){

            if ( visibleArray[x] == 0 ) {
                visible_count++;
            }

        }

    }

    //Play audio
    public void playSound( String audio) {

        try{
            GameUtilities.stopPlaying();
            GameUtilities.playAudio(getAssets().openFd(audio));
        } catch (Exception e){}

    }

    public void saveData(){

        String score;

        if (GameUtilities.gameLevel == 9)
            score = "90.5";
        else
            score = String.valueOf(GameUtilities.score_t - 3.5);
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
