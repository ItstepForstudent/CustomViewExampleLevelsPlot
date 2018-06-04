package com.itstep.customviewdemo.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.itstep.customviewdemo.R;

import java.util.ArrayList;
import java.util.List;

public class AnalititicsView extends View {
    public AnalititicsView(Context context) {
        super(context);
        initGester(context);
    }
    public AnalititicsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AnalititicsView, 0, 0);
        try {
            mColor = typedArray.getColor(R.styleable.AnalititicsView_color, Color.BLACK);
            mOrientation = typedArray.getInteger(R.styleable.AnalititicsView_orientation, 0);
        } finally {
            typedArray.recycle();
        }
        initGester(context);
    }

    int mColor = 0;
    int mOrientation = 0;

    int mHeight = 0;
    int mWidth = 0;
    GestureDetector gestureDetector;

    void initGester(Context ctx) {
        gestureDetector = new GestureDetector(ctx,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                try {
                    float dx = e2.getX()-e1.getX();
                    float dy = e2.getY()-e1.getY();
                    Log.v("----","dx "+dx);
                    if(Math.abs(dx)>Math.abs(dy)){
                        if(dx>0){
                            mOrientation=1;
                        }
                        else{
                            mOrientation=0;
                        }
                        invalidate();
                    }
                    return true;
                }catch (Exception e){

                }
                return false;
            }
        });

    }




    List<Integer> levels = new ArrayList<>();
    Paint mPaint = new Paint();



    List<Integer> getDrawableLevels() {
        int max = levels.size() > 0 ? levels.get(0) : 0;
        int min = max;

        int normalBase = mOrientation == 1 ? mHeight : mWidth;

        int v = (int) (0.7 * normalBase);
        int addaed = (int) (0.1 * normalBase);

        for (int i : levels) {
            if (i > max) max = i;
            else if (i < min) min = i;
        }

        List<Integer> drawableLevels = new ArrayList<>();
        for (int i : levels) {
            drawableLevels.add((i - min) * v / (max - min) + addaed);
        }
        return drawableLevels;
    }


    public void setLevels(List<Integer> levels) {
        this.levels = levels;
        invalidate();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        switch (withMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                width = width > 100 ? 100 : width;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                height = height > 100 ? 100 : height;
        }
        mHeight = height;
        mWidth = width;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(mColor);
        List<Integer> lvs = getDrawableLevels();
        if (lvs.size() > 0) {
            int w = ((mOrientation == 1) ? mWidth : mHeight) / lvs.size();

            for (int pos = 0; pos < lvs.size(); pos++) {
                if (mOrientation == 1)
                    canvas.drawRect(new Rect(pos * w + (int) (w * 0.2), mHeight - lvs.get(pos), pos * w + (int) (w * 0.8), mHeight), mPaint);
                else
                    canvas.drawRect(new Rect(0, pos * w + (int) (w * 0.2), lvs.get(pos), pos * w + (int) (w * 0.8)), mPaint);
            }

        }

        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }
}
