package com.mobiledexterous.gaming;

import android.content.Intent;
import android.graphics.Color;
import android.icu.util.UniversalTimeScale;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameTraceThePath extends AppCompatActivity implements View.OnClickListener{

    ImageView bg_mask,level_img, next_btn, try_again, help;

    Handler handler = new Handler();

    int drawX,drawY,count;

    private GamePaintColor v_drawingPad;
    private Button reset_btn;
    boolean status = true;

    int failCount = 0;

    DatabaseReference databaseGameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_trace_the_path);
        this.init();

        String ref = "User/Gaming/"+GameUtilities.patientId;

        databaseGameData = FirebaseDatabase.getInstance().getReference(ref);

        GameUtilities.draw_status = true;

        bg_mask = findViewById(R.id.bg_mask);
        level_img = findViewById(R.id.level_img1);
        next_btn = findViewById(R.id.next_btn);
        try_again = findViewById(R.id.try_again);
        help = findViewById(R.id.help);

        v_drawingPad.setPenColor(Color.parseColor("#38dfdd"));

        bg_mask.setOnTouchListener(new MyButtonTouchListener());

        loadLevel();
        LevelPass();

    }//onCreate

    public void LevelPass(){

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                LevelPass();

            }
        },1000);

        if ( status && !GameUtilities.draw_status ) {

            if (GameUtilities.result[0] && GameUtilities.result[1] && GameUtilities.result[2]) {
                status = false;

                //Score
                if ( GameUtilities.gameDifficulty == 0 ){
                    if ( GameUtilities.gameLevel == 1 )
                        GameUtilities.score_ttp = 5.0;
                    else if ( GameUtilities.gameLevel == 2 )
                        GameUtilities.score_ttp = 10.0;
                    else if ( GameUtilities.gameLevel == 3 )
                        GameUtilities.score_ttp = 15.0;
                    else if ( GameUtilities.gameLevel == 4 )
                        GameUtilities.score_ttp = 20.0;
                    else if ( GameUtilities.gameLevel == 5 )
                        GameUtilities.score_ttp = 25.0;
                    else if ( GameUtilities.gameLevel == 6 )
                        GameUtilities.score_ttp = 30.0;
                    else if ( GameUtilities.gameLevel == 7 )
                        GameUtilities.score_ttp = 35.0;
                    else if ( GameUtilities.gameLevel == 8 )
                        GameUtilities.score_ttp = 40.0;
                    else if ( GameUtilities.gameLevel == 9 )
                        GameUtilities.score_ttp = 45.0;
                }

                else{
                    if ( GameUtilities.gameLevel == 1 )
                        GameUtilities.score_ttp = 55.0;
                    else if ( GameUtilities.gameLevel == 2 )
                        GameUtilities.score_ttp = 60.0;
                    else if ( GameUtilities.gameLevel == 3 )
                        GameUtilities.score_ttp = 65.0;
                    else if ( GameUtilities.gameLevel == 4 )
                        GameUtilities.score_ttp = 70.0;
                    else if ( GameUtilities.gameLevel == 5 )
                        GameUtilities.score_ttp = 75.0;
                    else if ( GameUtilities.gameLevel == 6 )
                        GameUtilities.score_ttp = 80.0;
                    else if ( GameUtilities.gameLevel == 7 )
                        GameUtilities.score_ttp = 85.0;
                    else if ( GameUtilities.gameLevel == 8 )
                        GameUtilities.score_ttp = 90.0;
                    else if ( GameUtilities.gameLevel == 9 )
                        GameUtilities.score_ttp = 100.0;
                }

                if (GameUtilities.gameLevel == 9) {

                    if (GameUtilities.gameDifficulty == 0) {
                        GameUtilities.gameDifficulty = 2;
                        GameUtilities.gameLevel = 1;
                        next_btn.setVisibility(View.VISIBLE);
                    } else {

                    }

                } else {


                    GameUtilities.gameLevel++;
                    next_btn.setVisibility(View.VISIBLE);
                }

            }

            else {
                try_again.setVisibility(View.VISIBLE);
                ++failCount;

                if ( failCount == 5 && GameUtilities.gameLevel > 1 ){

                    --GameUtilities.gameLevel;
                    loadLevel();
                    failCount = 0;
                    saveData();
                }

            }

        }

    }

    //Button touch listener
    public class MyButtonTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            final int evX = (int) motionEvent.getX();
            final int evY = (int) motionEvent.getY();
            int touchColor = GameUtilities.getHotspotColor((ImageView) findViewById(R.id.bg_mask), evX, evY);
            GameColorTool ct = new GameColorTool();
            int tolerance = 20;

            int c = next_btn.getVisibility();
            int hlp = help.getVisibility();

            if ( hlp == 0)
                help.setVisibility(View.INVISIBLE);

            //Reset
            if (ct.closeMatch(Color.parseColor("#1b0abe"), touchColor, tolerance) && c == 4){

                try_again.setVisibility(View.INVISIBLE);
                v_drawingPad.reset();
                GameUtilities.draw_status = true;

            }

            //Next
            if ( touchColor == -3553792 && c == 0 ){
                GameUtilities.result[0] = false;
                GameUtilities.result[1] = true;
                GameUtilities.result[2] = false;
                GameUtilities.draw_status = true;
                status = true;
                failCount = 0;
                v_drawingPad.reset();
                loadLevel();


            }

            //Help
            if (ct.closeMatch(Color.parseColor("#5b1e09"), touchColor, tolerance) && c == 4){

                if ( hlp == 0)
                    help.setVisibility(View.INVISIBLE);

                else
                help.setVisibility(View.VISIBLE);
            }

            return false;
        }
    }

    public void getCordinations(float x, float y, int no){

        drawX = (int) x;
        drawY = (int) y;
        count = no;

        //Easy Level
        if (GameUtilities.gameDifficulty == 0 ){

            if(GameUtilities.gameLevel == 1){

                if ( drawY > 509 || drawY < 437 || drawX < 60 || drawX > 1382 ) {
                    GameUtilities.result[1] = false;
                }

                if ( drawX < 133 ) {
                    GameUtilities.result[0] = true;
                }

                if ( GameUtilities.result[0] ){

                    if ( drawX > 1310 ){
                        GameUtilities.result[2] = true;
                    }

                }

            }

            else if(GameUtilities.gameLevel == 2){

                Log.e("logg","get cor");

                if ( drawY > 509 || drawY < 437 || drawX < 60 || drawX > 1382 ) {
                    GameUtilities.result[1] = false;
                }

                if ( drawX > 1310 ) {
                    GameUtilities.result[0] = true;
                }

                if ( GameUtilities.result[0] ){

                    if ( drawX < 133 ){
                        GameUtilities.result[2] = true;
                    }

                }

            }

            else if(GameUtilities.gameLevel == 3){

                if ( drawY < 55 || drawY > 869 || drawX < 687 || drawX > 758 ) {
                    GameUtilities.result[1] = false;
                }

                if ( drawY < 133 ) {
                    GameUtilities.result[0] = true;
                }

                if ( GameUtilities.result[0] ){

                    if ( drawY > 793 ){
                        GameUtilities.result[2] = true;
                    }

                }

            }

            else if(GameUtilities.gameLevel == 4){

                if ( drawY < 55 || drawY > 869 || drawX < 687 || drawX > 758 ) {
                    GameUtilities.result[1] = false;
                }

                if ( drawY > 793 ) {
                    GameUtilities.result[0] = true;
                }

                if ( GameUtilities.result[0] ){

                    if ( drawY < 133 ){
                        GameUtilities.result[2] = true;
                    }

                }

            }

            else if(GameUtilities.gameLevel == 5){

                if ( drawY < 109 || drawY > 182 || drawX < 338 || drawX > 1066 ) {
                    if ( drawY < 109 || drawY > 838 || drawX < 994 || drawX > 1066 )
                        GameUtilities.result[1] = false;
                }

                if ( drawX < 421 ) {
                    GameUtilities.result[0] = true;
                }

                if ( GameUtilities.result[0] ){

                    if ( drawY > 753 ){
                        GameUtilities.result[2] = true;
                    }

                }

            }

            else if(GameUtilities.gameLevel == 6) {

                if (drawY < 695 || drawY > 768 || drawX < 217 || drawX > 944) {
                    if (drawY < 42 || drawY > 768 || drawX < 871 || drawX > 944)
                        GameUtilities.result[1] = false;
                }

                if (drawX < 299) {
                    GameUtilities.result[0] = true;
                }

                if (GameUtilities.result[0]) {

                    if (drawY < 123)
                        GameUtilities.result[2] = true;

                }

            }

            else if(GameUtilities.gameLevel == 7) {

                if (drawY < 40 || drawY > 768 || drawX < 216 || drawX > 287) {
                    if (drawY < 696 || drawY > 768 || drawX < 216 || drawX > 943)
                        GameUtilities.result[1] = false;
                }

                if (drawY < 123) {
                    GameUtilities.result[0] = true;
                }

                if (GameUtilities.result[0]) {

                    if (drawX > 859)
                        GameUtilities.result[2] = true;

                }

            }

            else if(GameUtilities.gameLevel == 8) {

                if (drawY < 40 || drawY > 113 || drawX < 216 || drawX > 942) {
                    if (drawY < 40 || drawY > 768 || drawX < 216 || drawX > 288)
                        GameUtilities.result[1] = false;
                }

                if (drawX > 859) {
                    GameUtilities.result[0] = true;
                }

                if (GameUtilities.result[0]) {

                    if (drawY > 683)
                        GameUtilities.result[2] = true;

                }

            }

            else if(GameUtilities.gameLevel == 9) {

                if (drawY < 696 || drawY > 768 || drawX < 216 || drawX > 942) {
                    if (drawY < 40 || drawY > 768 || drawX < 216 || drawX > 288)
                        GameUtilities.result[1] = false;
                }

                if (drawX > 859) {
                    GameUtilities.result[0] = true;
                }

                if (GameUtilities.result[0]) {

                    if (drawY < 124)
                        GameUtilities.result[2] = true;

                }

            }

        }

        //Hard Level
        else{

            if(GameUtilities.gameLevel == 1){

                if ( drawY > 699 || drawY < 160 || drawX < 188 || drawX > 1256 )
                    GameUtilities.result[1] = false;

                else{

                    if ( drawY > 232 && drawX < 1184 && drawX > 260 )
                        GameUtilities.result[1] = false;

                }

                if ( drawY > 613 ) {

                    if ( drawX < 750 )
                        GameUtilities.result[0] = true;

                    else if ( GameUtilities.result[0] )
                        GameUtilities.result[2] = true;

                }

            }

            else if(GameUtilities.gameLevel == 2){

                if ( drawY > 699 || drawY < 160 || drawX < 188 || drawX > 1256 )
                    GameUtilities.result[1] = false;

                else{
                    if ( drawY < 625 && drawX < 1184 && drawX > 260 )
                        GameUtilities.result[1] = false;
                }

                if ( drawY < 244 ) {

                    if ( drawX < 750 )
                        GameUtilities.result[0] = true;

                    else if ( GameUtilities.result[0] )
                        GameUtilities.result[2] = true;

                }

            }

            else if(GameUtilities.gameLevel == 3){

                if ( drawY > 806 || drawY < 178 || drawX < 74 || drawX > 1360 )
                    GameUtilities.result[1] = false;

                else{
                    if ( drawY > 250 && drawX > 146 && drawY < 734 )
                        GameUtilities.result[1] = false;
                }

                if ( drawX > 1276 ) {

                    if ( drawY < 500 )
                        GameUtilities.result[0] = true;

                    else if ( GameUtilities.result[0] )
                        GameUtilities.result[2] = true;

                }

            }

            else if(GameUtilities.gameLevel == 4){

                if ( drawY > 806 || drawY < 178 || drawX < 74 || drawX > 1360 )
                    GameUtilities.result[1] = false;

                else{
                    if ( drawY > 250 && drawX > 146 && drawY < 734 )
                        GameUtilities.result[1] = false;
                }

                if ( drawX > 1276 ) {

                    if ( drawY > 500 )
                        GameUtilities.result[0] = true;

                    else if ( GameUtilities.result[0] )
                        GameUtilities.result[2] = true;

                }

            }

            else if(GameUtilities.gameLevel == 5){

                if ( drawY > 699 || drawY < 160 || drawX < 188 || drawX > 1256 )
                    GameUtilities.result[1] = false;

                else{
                    if ( drawY < 625 && drawX < 1184 && drawX > 260 )
                        GameUtilities.result[1] = false;
                }

                if ( drawY < 244 ) {

                    if ( drawX > 750 )
                        GameUtilities.result[0] = true;

                    else if ( GameUtilities.result[0] )
                        GameUtilities.result[2] = true;

                }

            }

            else if(GameUtilities.gameLevel == 6) {

                if ( drawY > 699 || drawY < 160 || drawX < 188 || drawX > 1256 )
                    GameUtilities.result[1] = false;

                else{

                    if ( drawY > 232 && drawX < 1184 && drawX > 260 )
                        GameUtilities.result[1] = false;

                }

                if ( drawY > 613 ) {

                    if ( drawX > 750 )
                        GameUtilities.result[0] = true;

                    else if ( GameUtilities.result[0] )
                        GameUtilities.result[2] = true;

                }

            }

            else if(GameUtilities.gameLevel == 7) {

                if ( drawY > 750 || drawY < 124 || drawX < 100 || drawX > 1385 )
                    GameUtilities.result[1] = false;

                else{
                    if ( drawY > 195 && drawX < 1313 && drawY < 679 )
                        GameUtilities.result[1] = false;
                }

                if ( drawX < 185 ) {

                    if ( drawY > 400 )
                        GameUtilities.result[0] = true;

                    else if ( GameUtilities.result[0] )
                        GameUtilities.result[2] = true;

                }

            }

            else if(GameUtilities.gameLevel == 8) {

                if ( drawY > 836 || drawY < 69 || drawX < 89 || drawX > 1342 )
                    GameUtilities.result[1] = false;

                else{
                    if ( drawY > 142 && drawY < 764 && drawX < 1271 && drawX > 161)
                        GameUtilities.result[1] = false;
                }

                if ( drawX > 1181 && drawY < 1283 && drawY > 745 && drawY < 856 ) {
                    GameUtilities.result[0] = true;

                if ( GameUtilities.result[0] && drawY < 791 && drawY > 705 && drawX < 1363 && drawX > 1250 && count > 260 )
                    GameUtilities.result[2] = true;

                }

            }

            else if(GameUtilities.gameLevel == 9) {

                if ( drawY > 836 || drawY < 69 || drawX < 89 || drawX > 1342 )
                    GameUtilities.result[1] = false;

                else{
                    if ( drawY > 142 && drawY < 764 && drawX < 1271 && drawX > 161)
                        GameUtilities.result[1] = false;
                }

                if ( drawY < 805 && drawY > 701 && drawX < 1363 && drawX > 1250 )
                    GameUtilities.result[0] = true;

                else if ( GameUtilities.result[0] && drawX < 1277 && drawX > 1173 && drawY > 745 && drawY < 856 && count > 260 )
                    GameUtilities.result[2] = true;

            }

        }

    }

    public void loadLevel(){

        next_btn.setVisibility(View.INVISIBLE);
        try_again.setVisibility(View.INVISIBLE);
        v_drawingPad.reset();

        if ( GameUtilities.gameDifficulty == 0 ){

            if (GameUtilities.gameLevel == 1){
                level_img.setImageResource(R.drawable.ttp_e_1_bg);
            }

            else if (GameUtilities.gameLevel == 2){
                level_img.setImageResource(R.drawable.ttp_e_2_bg);
            }

            else if (GameUtilities.gameLevel == 3){
                level_img.setImageResource(R.drawable.ttp_e_3_bg);
            }

            else if (GameUtilities.gameLevel == 4){
                level_img.setImageResource(R.drawable.ttp_e_4_bg);
            }

            else if (GameUtilities.gameLevel == 5){
                level_img.setImageResource(R.drawable.ttp_e_5_bg);
            }

            else if (GameUtilities.gameLevel == 6){
                level_img.setImageResource(R.drawable.ttp_e_6_bg);
            }

            else if (GameUtilities.gameLevel == 7){
                level_img.setImageResource(R.drawable.ttp_e_7_bg);
            }

            else if (GameUtilities.gameLevel == 8){
                level_img.setImageResource(R.drawable.ttp_e_8_bg);
            }

            else if (GameUtilities.gameLevel == 9){
                level_img.setImageResource(R.drawable.ttp_e_9_bg);
            }

        }

        else{

            if (GameUtilities.gameLevel == 1){
                level_img.setImageResource(R.drawable.ttp_h_1_bg);
            }

            else if (GameUtilities.gameLevel == 2){
                level_img.setImageResource(R.drawable.ttp_h_2_bg);
            }

            else if (GameUtilities.gameLevel == 3){
                level_img.setImageResource(R.drawable.ttp_h_3_bg);
            }

            else if (GameUtilities.gameLevel == 4){
                level_img.setImageResource(R.drawable.ttp_h_4_bg);
            }

            else if (GameUtilities.gameLevel == 5){
                level_img.setImageResource(R.drawable.ttp_h_5_bg);
            }

            else if (GameUtilities.gameLevel == 6){
                level_img.setImageResource(R.drawable.ttp_h_6_bg);
            }

            else if (GameUtilities.gameLevel == 7){
                level_img.setImageResource(R.drawable.ttp_h_7_bg);
            }

            else if (GameUtilities.gameLevel == 8){
                level_img.setImageResource(R.drawable.ttp_h_8_bg);
            }

            else if (GameUtilities.gameLevel == 9){
                level_img.setImageResource(R.drawable.ttp_h_9_bg);
            }

        }
    }

    private void init() {

        reset_btn = (Button)findViewById(R.id.reset_btn);
        reset_btn.setOnClickListener(this);

        v_drawingPad = (GamePaintColor)findViewById(R.id.drawingPad_v);
    }

    public void onClick(View view){

        handler.removeCallbacksAndMessages(null);
        v_drawingPad.reset();

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

        if ( GameUtilities.gameLevel == 9)
            score = "85.0";
        else
            score = String.valueOf(GameUtilities.score_ttp - 5);
        String attempt = String.valueOf(++GameUtilities.attemptNo);
        String gameId = String.valueOf(GameUtilities.gameNumber);

        String temp_id = databaseGameData.push().getKey();

        GameData game_data = new GameData( attempt, gameId, score );

        databaseGameData.child(temp_id).setValue(game_data);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        GameUtilities.stopPlaying();
//        GameUtilities.mediaPlayer=null;

        String score = String.valueOf(GameUtilities.score_ttp);
        String attempt = String.valueOf(++GameUtilities.attemptNo);
        String gameId = String.valueOf(GameUtilities.gameNumber);

        String temp_id = databaseGameData.push().getKey();

        GameData game_data = new GameData( attempt, gameId, score );

        databaseGameData.child(temp_id).setValue(game_data);

        Intent intent = new Intent(getApplicationContext(), GameLevel.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
