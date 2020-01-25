package com.mobiledexterous.gaming;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameDragAndDropHard extends AppCompatActivity {

    View tmpV = null;
    Handler handler = new Handler();
    ConstraintLayout parentLayout;

    boolean status = true;
    boolean time = true;

    int tag_lstn = -1;
    int correct_count = 0;
    int no_of_shapes = 0;
    int color = 0;
    int failCount = 0;

    TextView timer;

    ImageView inTray[] = new ImageView[57];
    ImageView outTray[] = new ImageView[22];
    ImageView bg_pics,mask_img,mask_btn,nextBtn,help_view;

    boolean array[] = new boolean[24];

    DatabaseReference databaseGameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_drag_and_drop_hard);

        String ref = "User/Gaming/"+GameUtilities.patientId;

        databaseGameData = FirebaseDatabase.getInstance().getReference(ref);

        bg_pics = (ImageView) findViewById(R.id.bg_pics);
        mask_img = (ImageView) findViewById(R.id.mask_img);
        mask_btn = (ImageView) findViewById(R.id.mask_btn);
        nextBtn = (ImageView) findViewById(R.id.nextBtn);
        help_view = (ImageView) findViewById(R.id.help_view);

        timer = (TextView) findViewById(R.id.timer);

        //Out Tray
        outTray[0] = (ImageView) findViewById(R.id.lvl_1_triangle_1);
        outTray[1] = (ImageView) findViewById(R.id.lvl_1_square);

        outTray[2] = (ImageView) findViewById(R.id.lvl_2_rectangle);
        outTray[3] = (ImageView) findViewById(R.id.lvl_2_triangle);
        outTray[4] = (ImageView) findViewById(R.id.lvl_2_1_triangle);

        outTray[5] = (ImageView) findViewById(R.id.lvl_3_circle);

        outTray[6] = (ImageView) findViewById(R.id.lvl_4_ellipse_1);
        outTray[7] = (ImageView) findViewById(R.id.lvl_4_ellipse_2);
        outTray[8] = (ImageView) findViewById(R.id.lvl_4_ellipse_3);
        outTray[9] = (ImageView) findViewById(R.id.lvl_4_ellipse_4);

        outTray[10] = (ImageView) findViewById(R.id.lvl_5_rectangle_1);
        outTray[11] = (ImageView) findViewById(R.id.lvl_5_rectangle_2);
        outTray[12] = (ImageView) findViewById(R.id.lvl_5_rectangle_3);

        outTray[13] = (ImageView) findViewById(R.id.lvl_6_rectangle);
        outTray[14] = (ImageView) findViewById(R.id.lvl_6_square);
        outTray[15] = (ImageView) findViewById(R.id.lvl_6_circle);

        outTray[16] = (ImageView) findViewById(R.id.lvl_7_square);

        outTray[17] = (ImageView) findViewById(R.id.lvl_8_rectangle);

        outTray[18] = (ImageView) findViewById(R.id.lvl_8_triangle);

        outTray[19] = (ImageView) findViewById(R.id.lvl_9_triangle_1);
        outTray[20] = (ImageView) findViewById(R.id.lvl_9_triangle_2);
        outTray[21] = (ImageView) findViewById(R.id.lvl_9_square);

        //In Tray
        inTray[0] = (ImageView) findViewById(R.id.lvl_1_triangle_1_in);
        inTray[1] = (ImageView) findViewById(R.id.lvl_1_triangle_2_in);
        inTray[2] = (ImageView) findViewById(R.id.lvl_1_triangle_3_in);
        inTray[3] = (ImageView) findViewById(R.id.lvl_1_square_in);

        inTray[4] = (ImageView) findViewById(R.id.lvl_2_triangle_in);
        inTray[5] = (ImageView) findViewById(R.id.lvl_2_1_triangle_in);
        inTray[6] = (ImageView) findViewById(R.id.lvl_2_2_triangle_in);
        inTray[7] = (ImageView) findViewById(R.id.lvl_2_3_triangle_in);
        inTray[8] = (ImageView) findViewById(R.id.lvl_2_rectangle_in);

        inTray[9] = (ImageView) findViewById(R.id.lvl_3_circle_1_in);
        inTray[10] = (ImageView) findViewById(R.id.lvl_3_circle_2_in);
        inTray[11] = (ImageView) findViewById(R.id.lvl_3_circle_3_in);
        inTray[12] = (ImageView) findViewById(R.id.lvl_3_circle_4_in);
        inTray[13] = (ImageView) findViewById(R.id.lvl_3_circle_5_in);
        inTray[14] = (ImageView) findViewById(R.id.lvl_3_circle_6_in);
        inTray[15] = (ImageView) findViewById(R.id.lvl_3_circle_7_in);

        inTray[16] = (ImageView) findViewById(R.id.lvl_4_ellipse_1_in);
        inTray[17] = (ImageView) findViewById(R.id.lvl_4_ellipse_2_1_in);
        inTray[18] = (ImageView) findViewById(R.id.lvl_4_ellipse_2_2_in);
        inTray[19] = (ImageView) findViewById(R.id.lvl_4_ellipse_3_1_in);
        inTray[20] = (ImageView) findViewById(R.id.lvl_4_ellipse_3_2_in);
        inTray[21] = (ImageView) findViewById(R.id.lvl_4_ellipse_4_in);

        inTray[22] = (ImageView) findViewById(R.id.lvl_5_rectangle_1_in);
        inTray[23] = (ImageView) findViewById(R.id.lvl_5_rectangle_2_in);
        inTray[24] = (ImageView) findViewById(R.id.lvl_5_rectangle_3_1_in);
        inTray[25] = (ImageView) findViewById(R.id.lvl_5_rectangle_3_2_in);
        inTray[26] = (ImageView) findViewById(R.id.lvl_5_rectangle_3_3_in);
        inTray[27] = (ImageView) findViewById(R.id.lvl_5_rectangle_3_4_in);

        inTray[28] = (ImageView) findViewById(R.id.lvl_6_rectangle_1_in);
        inTray[29] = (ImageView) findViewById(R.id.lvl_6_rectangle_2_in);
        inTray[30] = (ImageView) findViewById(R.id.lvl_6_square_1_in);
        inTray[31] = (ImageView) findViewById(R.id.lvl_6_square_2_in);
        inTray[32] = (ImageView) findViewById(R.id.lvl_6_square_3_in);
        inTray[33] = (ImageView) findViewById(R.id.lvl_6_circle_1_in);
        inTray[34] = (ImageView) findViewById(R.id.lvl_6_circle_2_in);

        inTray[35] = (ImageView) findViewById(R.id.lvl_7_square_1_in);
        inTray[36] = (ImageView) findViewById(R.id.lvl_7_square_2_in);
        inTray[37] = (ImageView) findViewById(R.id.lvl_7_square_3_in);
        inTray[38] = (ImageView) findViewById(R.id.lvl_7_square_4_in);
        inTray[39] = (ImageView) findViewById(R.id.lvl_7_square_5_in);
        inTray[40] = (ImageView) findViewById(R.id.lvl_7_square_6_in);
        inTray[41] = (ImageView) findViewById(R.id.lvl_7_square_7_in);
        inTray[42] = (ImageView) findViewById(R.id.lvl_7_square_8_in);
        inTray[43] = (ImageView) findViewById(R.id.lvl_7_square_9_in);

        inTray[44] = (ImageView) findViewById(R.id.lvl_8_rectangle_1_in);
        inTray[45] = (ImageView) findViewById(R.id.lvl_8_rectangle_2_in);
        inTray[46] = (ImageView) findViewById(R.id.lvl_8_rectangle_3_in);
        inTray[47] = (ImageView) findViewById(R.id.lvl_8_rectangle_4_in);
        inTray[48] = (ImageView) findViewById(R.id.lvl_8_rectangle_5_in);
        inTray[49] = (ImageView) findViewById(R.id.lvl_8_rectangle_6_in);
        inTray[50] = (ImageView) findViewById(R.id.lvl_8_triangle_in);

        inTray[51] = (ImageView) findViewById(R.id.lvl_9_triangle_1_in);
        inTray[52] = (ImageView) findViewById(R.id.lvl_9_triangle_2_in);
        inTray[53] = (ImageView) findViewById(R.id.lvl_9_triangle_3_in);
        inTray[54] = (ImageView) findViewById(R.id.lvl_9_square_1_in);
        inTray[55] = (ImageView) findViewById(R.id.lvl_9_square_2_in);
        inTray[56] = (ImageView) findViewById(R.id.lvl_9_square_3_in);

        mask_btn.setOnTouchListener(new MyButtonTouchListener());

        parentLayout = (ConstraintLayout)findViewById(R.id.parent);
        parentLayout.setOnDragListener(new GameDragAndDropHard.MyDragListener());

        //Set array all elements to false
        for(int xx = 0; xx<=23; xx++)
            array[xx] = false;

        loadLevel();

    }//End of OnCreate

    //Button touch listener
    private final class MyButtonTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            final int evX = (int) motionEvent.getX();
            final int evY = (int) motionEvent.getY();
            int touchColor = GameUtilities.getHotspotColor((ImageView) findViewById(R.id.mask_btn), evX, evY);
            GameColorTool ct = new GameColorTool();
            int tolerance = 20;

            int nxtBtnAct = (Integer) nextBtn.getVisibility();
            int helpBtnAct = (Integer) help_view.getVisibility();

            if (helpBtnAct == 0)
                help_view.setVisibility(View.INVISIBLE);

            //Help
            if (ct.closeMatch(Color.parseColor("#5b1e09"), touchColor, tolerance) && (helpBtnAct == 4)){

                help_view.setVisibility(View.VISIBLE);
            }

            //Retry
            if (ct.closeMatch(Color.parseColor("#1b0abe"), touchColor, tolerance)) {
                handler.removeCallbacksAndMessages(null);
                timer.setText("10");
                status = true;
                time = true;
                for (int x=0; x<=23; x++)
                    array[x] = false;
                loadLevel();
            }

            //Next
            if (ct.closeMatch(Color.parseColor("#7419ae"), touchColor, tolerance) && nxtBtnAct == 0 ){

                failCount = 0;
                if ( GameUtilities.gameLevel == 9 ){

                }

                else{

                    nextBtn.setVisibility(View.INVISIBLE);
                    handler.removeCallbacksAndMessages(null);
                    timer.setText("10");
                    status = true;
                    time = true;
                    for (int x=0; x<=23; x++)
                        array[x] = false;
                    GameUtilities.gameLevel++;
                    loadLevel();

                }
            }

            return false;
        }
    }

    //Touch Listener for shapes
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            tmpV = view;

            if (status) {
                tag_lstn = (Integer) view.getTag();

                if (time) {
                    time = false;
                    startTime();
                }

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.VISIBLE);
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

            if(dragView==null) {
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

                    int touchColor = GameUtilities.getHotspotColor((ImageView) findViewById(R.id.mask_img), evX, evY);
                    GameColorTool ct = new GameColorTool();
                    int tolerance = 50;

                    //Dark Red
                    if ( ct.closeMatch(Color.parseColor("#f21111"), touchColor, tolerance) && tag_lstn == 0 ){
                        color = 0;
                        array[0] = true;
                        correct();
                        return true;
                    }
                    //Pink
                    if( ct.closeMatch(Color.parseColor("#ff00f6"), touchColor, tolerance) && tag_lstn == 0 ){
                        color = 1;
                        array[1] = true;
                        correct();
                        return true;
                    }
                    //Dark purple
                    else if( ct.closeMatch(Color.parseColor("#8c0ade"), touchColor, tolerance) && tag_lstn == 0 ){
                        color = 2;
                        array[2] = true;
                        correct();
                        return true;
                    }

                    //Blue
                    else if( ct.closeMatch(Color.parseColor("#2f02f1"), touchColor, tolerance) && tag_lstn == 1 ) {
                        color = 3;
                        array[3] = true;
                        correct();
                        return true;
                    }

                    //Cyan
                    else if( ct.closeMatch(Color.parseColor("#02d1ff"), touchColor, tolerance) && tag_lstn == 2 ) {
                    color = 4;
                    array[4] = true;
                    correct();
                    return true;
                }

                    //Light blue
                    else if( ct.closeMatch(Color.parseColor("#009cff"), touchColor, tolerance) && tag_lstn == 0 ){
                        color = 5;
                        array[5] = true;
                        correct();
                        return true;
                    }

                    //Light Green
                    else if( ct.closeMatch(Color.parseColor("#05e64f"), touchColor, tolerance) && tag_lstn == 0 ){
                        color = 6;
                        array[6] = true;
                        correct();
                        return true;
                    }

                    //Orange
                    else if( ct.closeMatch(Color.parseColor("#ff9600"), touchColor, tolerance) && tag_lstn == 0 ){
                        color = 7;
                        array[7] = true;
                        correct();
                        return true;
                    }

                    //Yellow
                    else if( ct.closeMatch(Color.parseColor("#f7e300"), touchColor, tolerance) && tag_lstn == 0 ){
                        color = 8;
                        array[8] = true;
                        correct();
                        return true;
                    }

                    //Dark Yellow
                    else if( ct.closeMatch(Color.parseColor("#907806"), touchColor, tolerance) && tag_lstn == 3 ){
                        color = 9;
                        array[9] = true;
                        correct();
                        return true;
                    }

                    //Dark Green
                    else if( ct.closeMatch(Color.parseColor("#3d5b0f"), touchColor, tolerance) && tag_lstn == 3 ){
                        color = 10;
                        array[10] = true;
                        correct();
                        return true;
                    }

                    //Dark Pink
                    else if( ct.closeMatch(Color.parseColor("#b10047"), touchColor, tolerance) && tag_lstn == 4 ){
                        color = 11;
                        array[11] = true;
                        correct();
                        return true;
                    }

                    //Green
                    else if( touchColor == -16735841 && tag_lstn == 4 ){
                        color = 12;
                        array[12] = true;
                        correct();
                        return true;
                    }

                    //
                    else if( ct.closeMatch(Color.parseColor("#9c0f55"), touchColor, tolerance) && tag_lstn == 0 ){
                        color = 13;
                        array[13] = true;
                        correct();
                        return true;
                    }

                    //
                    else if( ct.closeMatch(Color.parseColor("#895da5"), touchColor, tolerance) && tag_lstn == 0 ){
                        color = 14;
                        array[14] = true;
                        correct();
                        return true;
                    }

                    //
                    else if( ct.closeMatch(Color.parseColor("#34586f"), touchColor, tolerance) && tag_lstn == 0 ){
                        color = 15;
                        array[15] = true;
                        correct();
                        return true;
                    }

                    //
                    else if( ct.closeMatch(Color.parseColor("#077b2d"), touchColor, tolerance) && tag_lstn == 0 ){
                        color = 16;
                        array[16] = true;
                        correct();
                        return true;
                    }

                    //
                    else if( ct.closeMatch(Color.parseColor("#8e601e"), touchColor, tolerance) && tag_lstn == 0 ){
                        color = 17;
                        array[17] = true;
                        correct();
                        return true;
                    }

                case DragEvent.ACTION_DRAG_ENDED:
                    dragView.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }

            return true;
        }
    }

    //Correct
    public void correct(){

        if (GameUtilities.gameLevel == 1 ) {

            if (color == 0)
                inTray[0].setVisibility(View.VISIBLE);
            else if (color == 1)
                inTray[1].setVisibility(View.VISIBLE);
            else if (color == 2)
                inTray[2].setVisibility(View.VISIBLE);
            else if (color == 3)
                inTray[3].setVisibility(View.VISIBLE);

        }

        else if (GameUtilities.gameLevel == 3 ) {

            if (color == 0)
                inTray[4].setVisibility(View.VISIBLE);
            else if (color == 1)
                inTray[5].setVisibility(View.VISIBLE);
            else if (color == 2)
                inTray[6].setVisibility(View.VISIBLE);
            else if (color == 3)
                inTray[8].setVisibility(View.VISIBLE);
            else if (color == 4)
                inTray[7].setVisibility(View.VISIBLE);

        }

        else if (GameUtilities.gameLevel == 4 ) {

            if (color == 0)
                inTray[9].setVisibility(View.VISIBLE);
            else if (color == 1)
                inTray[14].setVisibility(View.VISIBLE);
            else if (color == 2)
                inTray[10].setVisibility(View.VISIBLE);
            else if (color == 5)
                inTray[15].setVisibility(View.VISIBLE);
            else if (color == 6)
                inTray[13].setVisibility(View.VISIBLE);
            else if (color == 7)
                inTray[11].setVisibility(View.VISIBLE);
            else if (color == 8)
                inTray[12].setVisibility(View.VISIBLE);

        }

        else if (GameUtilities.gameLevel == 5 ) {

            if (color == 0)
                inTray[17].setVisibility(View.VISIBLE);
            else if (color == 1)
                inTray[18].setVisibility(View.VISIBLE);
            else if (color == 3)
                inTray[16].setVisibility(View.VISIBLE);
            else if (color == 4)
                inTray[21].setVisibility(View.VISIBLE);
            else if (color == 9)
                inTray[20].setVisibility(View.VISIBLE);
            else if (color == 10)
                inTray[19].setVisibility(View.VISIBLE);
        }

        else if (GameUtilities.gameLevel == 6 ) {

            if (color == 0)
                inTray[24].setVisibility(View.VISIBLE);
            else if (color == 1)
                inTray[25].setVisibility(View.VISIBLE);
            else if (color == 2)
                inTray[26].setVisibility(View.VISIBLE);
            else if (color == 3)
                inTray[23].setVisibility(View.VISIBLE);
            else if (color == 4)
                inTray[22].setVisibility(View.VISIBLE);
            else if (color == 5)
                inTray[27].setVisibility(View.VISIBLE);
        }

        else if (GameUtilities.gameLevel == 8 ) {

            if (color == 0)
                inTray[30].setVisibility(View.VISIBLE);
            else if (color == 1)
                inTray[31].setVisibility(View.VISIBLE);
            else if (color == 2)
                inTray[32].setVisibility(View.VISIBLE);
            else if (color == 9)
                inTray[28].setVisibility(View.VISIBLE);
            else if (color == 10)
                inTray[29].setVisibility(View.VISIBLE);
            else if (color == 11)
                inTray[33].setVisibility(View.VISIBLE);
            else if (color == 12)
                inTray[34].setVisibility(View.VISIBLE);
        }

        else if (GameUtilities.gameLevel == 9 ) {

            if (color == 0)
                inTray[35].setVisibility(View.VISIBLE);
            else if (color == 1)
                inTray[36].setVisibility(View.VISIBLE);
            else if (color == 2)
                inTray[37].setVisibility(View.VISIBLE);
            else if (color == 5)
                inTray[38].setVisibility(View.VISIBLE);
            else if (color == 6)
                inTray[39].setVisibility(View.VISIBLE);
            else if (color == 7)
                inTray[40].setVisibility(View.VISIBLE);
            else if (color == 8)
                inTray[41].setVisibility(View.VISIBLE);
            else if (color == 13)
                inTray[42].setVisibility(View.VISIBLE);
            else if (color == 14)
                inTray[43].setVisibility(View.VISIBLE);
        }

        else if (GameUtilities.gameLevel == 2 ) {

            if (color == 0)
                inTray[44].setVisibility(View.VISIBLE);
            else if (color == 1)
                inTray[45].setVisibility(View.VISIBLE);
            else if (color == 2)
                inTray[46].setVisibility(View.VISIBLE);
            else if (color == 3)
                inTray[50].setVisibility(View.VISIBLE);
            else if (color == 5)
                inTray[47].setVisibility(View.VISIBLE);
            else if (color == 6)
                inTray[48].setVisibility(View.VISIBLE);
            else if (color == 7)
                inTray[49].setVisibility(View.VISIBLE);

        }

        else if (GameUtilities.gameLevel == 7 ) {

            if (color == 0)
                inTray[54].setVisibility(View.VISIBLE);
            else if (color == 1)
                inTray[55].setVisibility(View.VISIBLE);
            else if (color == 2)
                inTray[56].setVisibility(View.VISIBLE);
            else if (color == 3)
                inTray[52].setVisibility(View.VISIBLE);
            else if (color == 9)
                inTray[51].setVisibility(View.VISIBLE);
            else if (color == 10)
                inTray[53].setVisibility(View.VISIBLE);

        }

        //get correct count
        correct_count = 0;
        for(int xx=0; xx<=23; xx++ ){
            if( array[xx]==true)
                correct_count++;
        }

        //If level complete
        if (correct_count == no_of_shapes){
            handler.removeCallbacksAndMessages(null);
            if (GameUtilities.gameLevel != 9){
                nextBtn.setVisibility(View.VISIBLE);

                //Score
                if ( GameUtilities.gameLevel == 1 )
                    GameUtilities.score_dad = 55.0;
                else if ( GameUtilities.gameLevel == 2 )
                    GameUtilities.score_dad = 60.0;
                else if ( GameUtilities.gameLevel == 3 )
                    GameUtilities.score_dad = 65.0;
                else if ( GameUtilities.gameLevel == 4 )
                    GameUtilities.score_dad = 70.0;
                else if ( GameUtilities.gameLevel == 5 )
                    GameUtilities.score_dad = 75.0;
                else if ( GameUtilities.gameLevel == 6 )
                    GameUtilities.score_dad = 80.0;
                else if ( GameUtilities.gameLevel == 7 )
                    GameUtilities.score_dad = 85.0;
                else if ( GameUtilities.gameLevel == 8 )
                    GameUtilities.score_dad = 90.0;
                else if ( GameUtilities.gameLevel == 9 )
                    GameUtilities.score_dad = 100.0;

            }
            status = false;
            for (int y=0; y<=21; y++)
                outTray[y].setImageResource(0);
        }
    }

    //Load the level
    public void loadLevel() {

        bg_pics.setImageResource(0);
        mask_img.setImageResource(0);
        for (int in=0; in<=inTray.length-1; in++)
            inTray[in].setVisibility(View.INVISIBLE);

        for (int in=0; in<=outTray.length-1; in++)
            outTray[in].setVisibility(View.INVISIBLE);

        status = true;


         if (GameUtilities.gameLevel == 1){

             bg_pics.setImageResource(R.drawable.dad_h_b_1);
             mask_img.setImageResource(R.drawable.dad_h_mask_1);
             outTray[0].setImageResource(R.drawable.dad_h_t_1);
             outTray[1].setImageResource(R.drawable.dad_h_s_1);
            for(int x=0; x<=1; x++){
                outTray[x].setVisibility(View.VISIBLE);
                outTray[x].setOnTouchListener(new MyTouchListener());
            }
            outTray[0].setTag(0);
            outTray[1].setTag(1);

             no_of_shapes = 4;
         }

        else if (GameUtilities.gameLevel == 3){

            bg_pics.setImageResource(R.drawable.dad_h_b_2);
            mask_img.setImageResource(R.drawable.dad_h_mask_2);
            outTray[2].setImageResource(R.drawable.dad_h_r_2);
            outTray[3].setImageResource(R.drawable.dad_h_t_2);
            outTray[4].setImageResource(R.drawable.dad_h_t_2_1);
            for(int x=2; x<=4; x++){
                outTray[x].setVisibility(View.VISIBLE);
                outTray[x].setOnTouchListener(new MyTouchListener());
            }
            outTray[2].setTag(1);
            outTray[3].setTag(0);
            outTray[4].setTag(2);

            no_of_shapes = 5;
        }

         else if (GameUtilities.gameLevel == 4){

             bg_pics.setImageResource(R.drawable.dad_h_b_3);
             mask_img.setImageResource(R.drawable.dad_h_mask_3);
             outTray[5].setImageResource(R.drawable.dad_h_c_3);
             outTray[5].setVisibility(View.VISIBLE);
             outTray[5].setOnTouchListener(new MyTouchListener());
             outTray[5].setTag(0);

             no_of_shapes = 7;
         }

         else if (GameUtilities.gameLevel == 5){

             bg_pics.setImageResource(R.drawable.dad_h_b_4);
             mask_img.setImageResource(R.drawable.dad_h_mask_4);
             outTray[6].setImageResource(R.drawable.dad_h_e_4);
             outTray[7].setImageResource(R.drawable.dad_h_e_4_1);
             outTray[8].setImageResource(R.drawable.dad_h_e_4_2);
             outTray[9].setImageResource(R.drawable.dad_h_e_4_3);
             for(int x=6; x<=9; x++){
                 outTray[x].setVisibility(View.VISIBLE);
                 outTray[x].setOnTouchListener(new MyTouchListener());
             }
             outTray[6].setTag(1);
             outTray[7].setTag(0);
             outTray[8].setTag(3);
             outTray[9].setTag(2);

             no_of_shapes = 6;
         }

         else if (GameUtilities.gameLevel == 6){

             bg_pics.setImageResource(R.drawable.dad_h_b_5);
             mask_img.setImageResource(R.drawable.dad_h_mask_5);
             outTray[10].setImageResource(R.drawable.dad_h_r_5);
             outTray[11].setImageResource(R.drawable.dad_h_r_5_1);
             outTray[12].setImageResource(R.drawable.dad_h_r_5_2);
             for(int x=10; x<=12; x++){
                 outTray[x].setVisibility(View.VISIBLE);
                 outTray[x].setOnTouchListener(new MyTouchListener());
             }
             outTray[10].setTag(2);
             outTray[11].setTag(1);
             outTray[12].setTag(0);

             no_of_shapes = 6;
         }

         else if (GameUtilities.gameLevel == 8){

             bg_pics.setImageResource(R.drawable.dad_h_b_6);
             mask_img.setImageResource(R.drawable.dad_h_mask_6);
             outTray[13].setImageResource(R.drawable.dad_h_r_6);
             outTray[14].setImageResource(R.drawable.dad_h_s_6);
             outTray[15].setImageResource(R.drawable.dad_h_c_6);
             for(int x=13; x<=15; x++){
                 outTray[x].setVisibility(View.VISIBLE);
                 outTray[x].setOnTouchListener(new MyTouchListener());
             }
             outTray[13].setTag(3);
             outTray[14].setTag(0);
             outTray[15].setTag(4);

             no_of_shapes = 7;
         }

         else if (GameUtilities.gameLevel == 9){

             bg_pics.setImageResource(R.drawable.dad_h_b_7);
             mask_img.setImageResource(R.drawable.dad_h_mask_7);
             outTray[16].setImageResource(R.drawable.dad_h_s_7);
                 outTray[16].setVisibility(View.VISIBLE);
                 outTray[16].setOnTouchListener(new MyTouchListener());
             outTray[16].setTag(0);

             no_of_shapes = 9;
         }

         else if (GameUtilities.gameLevel == 2){

             bg_pics.setImageResource(R.drawable.dad_h_b_8);
             mask_img.setImageResource(R.drawable.dad_h_mask_8);
             outTray[17].setImageResource(R.drawable.dad_h_r_8);
             outTray[18].setImageResource(R.drawable.dad_h_t_8);
             for(int x=17; x<=18; x++) {
                 outTray[x].setVisibility(View.VISIBLE);
                 outTray[x].setOnTouchListener(new MyTouchListener());
             }
             outTray[17].setTag(0);
             outTray[18].setTag(1);

             no_of_shapes = 7;
         }

         else if (GameUtilities.gameLevel == 7){

             bg_pics.setImageResource(R.drawable.dad_h_b_9);
             mask_img.setImageResource(R.drawable.dad_h_mask_9);
             outTray[19].setImageResource(R.drawable.dad_h_t_9_1);
             outTray[20].setImageResource(R.drawable.dad_h_t_9_2);
             outTray[21].setImageResource(R.drawable.dad_h_s_9);
             for(int x=19; x<=21; x++) {
                 outTray[x].setVisibility(View.VISIBLE);
                 outTray[x].setOnTouchListener(new MyTouchListener());
             }
             outTray[19].setTag(3);
             outTray[20].setTag(1);
             outTray[21].setTag(0);

             no_of_shapes = 6;
         }

    }

    //Timer
    public void startTime(){

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

                    saveData();
                    --GameUtilities.gameLevel;
                    loadLevel();
                    failCount = 0;
                }


            }
        },10000);

    }

    public void saveData(){

        String score;

        if ( GameUtilities.gameLevel == 9)
            score = "85.0";
        else
            score = String.valueOf(GameUtilities.score_dad - 5);
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
