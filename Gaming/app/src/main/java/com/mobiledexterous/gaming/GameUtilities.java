package com.mobiledexterous.gaming;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class GameUtilities {

    public static boolean result[] = {false,true,false};

    public static boolean draw_status = true;


    //Drag and drop     = 0
    //Trace the path    = 2
    //Tapping           = 3
    public static int gameNumber = -1;

    //Level number 0 - 9
    public static int gameLevel = 1;

    //Difficulty 0 - easy , 1 - normal , 2 - hard
    public static int gameDifficulty = 0;

    //colors
    public static String[] color = {
            "#2f02f1"
    };

    //Recover Dissappearing Views
    public static void recoverDissappearingViews(View tmpV){
        try{
            tmpV.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis(),
                    (int)MotionEvent.ACTION_UP,0,0,0));

        }catch(Exception e)
        {
            Log.e("Null Images",e+"");
        }
    }

    static MediaPlayer mediaPlayer;

    public static void playAudio(AssetFileDescriptor afd) {
        try {
            //AssetFileDescriptor afd = getAssets().openFd(audioFile);
            stopPlaying();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }

            });

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void stopPlaying() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        } catch (Exception e) {
            Log.e("Exception", e + "");
        }

    }

    public static int getHotspotColor(ImageView img, int x, int y) {
        int returnBVal = 0;
        // ImageView img = (ImageView) findViewById(hotspotId);
        if (img == null) {
            Log.d("ImageAreasActivity", "Hot spot image not found");
            return returnBVal;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d("ImageAreasActivity", "Hot spot bitmap was not created");
                return returnBVal;
            } else {
                img.setDrawingCacheEnabled(false);
                try {
                    returnBVal = hotspots.getPixel(x, y);
                    Log.d("returnBVal", "returnBVal is " + returnBVal + "");

                } catch (Exception e) {
                    Log.e("Inside Catch", "Clicked on Two Colors.");
                }
                return returnBVal;
            }

        }
    }

    ///////////////////// MongoDB///////////////////

    public static String patientId = "0776144495";

    public static Double score_ttp = 0.0;
    public static Double score_t = 0.0;
    public static Double score_dad = 0.0;

    public static int attemptNo = 0;
    public static int attemptNo_dad = 0;
    public static int attemptNo_ttp = 0;
    public static int attemptNo_t = 0;
    //GameId is on the top as gameNumber

}
