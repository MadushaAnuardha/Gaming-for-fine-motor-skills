package com.mobiledexterous.gaming;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GamePaintColor extends View implements View.OnTouchListener{

    int x = 0;
    private final int DEFAULT_DOT_SIZE = 20;

    private int mDotSize;
    private int mPenColor;
    float mX, mY, mOldX, mOldY;

    private ArrayList<Path> mPaths;
    private ArrayList<Paint> mPaints;
    private Path mPath;
    private Paint mPaint;

    public GamePaintColor(Context context) {
        super(context);
        this.init();
    }


    public GamePaintColor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public GamePaintColor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this.mDotSize = DEFAULT_DOT_SIZE;

        this.mPaths = new ArrayList<Path>();
        this.mPaints = new ArrayList<Paint>();
        this.mPath = new Path();
        this.addPath(false);
        this.mX = this.mY = this.mOldX = this.mOldY = (float)0.0;
        this.setOnTouchListener(this);

    }

    private  void addPath(boolean fill){
        mPath = new Path();
        mPaths.add(mPath);
        mPaint = new Paint();
        mPaints.add(mPaint);
        mPaint.setColor(mPenColor);
        if(!fill)
            mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mDotSize);
    }

    public void setPenColor(int PenColor) {
        this.mPenColor = PenColor;
    }

    public void reset() {
        GameUtilities.draw_status = true;
        this.init();
        this.invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0; i<mPaths.size(); ++i)
            canvas.drawPath(mPaths.get(i), mPaints.get(i));
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (GameUtilities.draw_status){

            mX = motionEvent.getX();
            mY = motionEvent.getY();

            x = x + this.mPaths.size();

            GameTraceThePath easy = new GameTraceThePath();
            easy.getCordinations(mX, mY, x);

            switch (motionEvent.getAction()){
                case  MotionEvent.ACTION_DOWN:
                    this.addPath(true);
                    this.mPath.addCircle(mX, mY, mDotSize/2, Path.Direction.CW);
                    this.addPath(false);
                    this.mPath.moveTo(mX,mY);

                    GameUtilities.result[0] = false;
                    GameUtilities.result[1] = true;
                    GameUtilities.result[2] = false;

                    break;

                case  MotionEvent.ACTION_MOVE:
                    this.mPath.lineTo(mX,mY);
                    break;

                case MotionEvent.ACTION_UP:
                    this.addPath(true);
                    if (mOldX == mX && mOldY == mY)
                        this.mPath.addCircle(mX, mY, mDotSize/2, Path.Direction.CW);

                    GameUtilities.draw_status = false;
                    x = 0;

                    break;
            }
            this.invalidate();
            mOldX = mX;
            mOldY = mY;
        }

        return true;

    }
}
