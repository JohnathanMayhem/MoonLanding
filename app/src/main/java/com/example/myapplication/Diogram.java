package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.TreeMap;

public class Diogram extends SurfaceView implements SurfaceHolder.Callback {
    DrawThread thread;
    public TreeMap<Float, Float> list = new TreeMap<>();
    float canvasX, canvasY;

    public Diogram(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX, touchY;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                touchY = event.getY();
                for (float i = 0; i < 100; i++) {
                    for (float j = 0; j < 100; j++) {
                        if (touchY>(i*canvasY/100) && (touchY < (i+1)*canvasY/100) && ((j*canvasX/100)<touchX) && ((j+1)*canvasX/100)>touchX){
                            list.put(j, i);
                        }
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchX = event.getX();
                touchY = event.getY();
                for (float i = 0; i < 100; i++) {
                    for (float j = 0; j < 100; j++) {
                        if (touchY>(i*canvasY/100) && (touchY < (i+1)*canvasY/100) && ((j*canvasX/100)<touchX) && ((j+1)*canvasX/100)>touchX){
                            list.put(j, i);
                        }
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    class DrawThread extends Thread{
        boolean runFlag = true;
        Paint p = new Paint();

        public DrawThread (SurfaceHolder holder){
            this.holder = holder;
        }
        SurfaceHolder holder;
        @Override
        public void run() {
            super.run();
            while (runFlag){
                Canvas c = holder.lockCanvas();
                canvasX = c.getWidth();
                canvasY = c.getHeight();
                float x = c.getWidth()/100;
                float y = c.getHeight()/100;
                float a = x*100;
                float b = y*100;
                Paint p = new Paint();
                if (c!= null){
                    c.drawColor(Color.BLACK);
                    p.setColor(Color.BLUE);
                    p.setStyle(Paint.Style.STROKE);
                    for (float i = 0; i < 100; i++) {
                        for (float j = 0; j <100; j++) {
                            c.drawRect(j*x, i*y, j*x + x, i*y + y, p);
                        }
                    }
                    for (float i: list.keySet()){
                        p.setColor(Color.YELLOW);
                        p.setStyle(Paint.Style.FILL);
                        float h = list.get(i);
                        c.drawRect(i*x, h*y, i*x+x, h*y + y, p);
                    }

                    holder.unlockCanvasAndPost(c);
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("MyT", list.toString());
                }else {
                    Log.i("MyT", "Случилась беда");
                }
            }
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        for (float i = 0; i < 100; i++) {
            list.put(i, 100f);
        }
        thread = new DrawThread(holder);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.runFlag=false;
        thread = new DrawThread(holder);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.runFlag = false;
    }
}

