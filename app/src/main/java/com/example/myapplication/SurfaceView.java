package com.example.myapplication;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;



public class SurfaceView extends android.view.SurfaceView implements SurfaceHolder.Callback {
    DrawThread thread;
    Canvas canvas;
    Bitmap left = BitmapFactory.decodeResource(getResources(), R.drawable.left);
    Bitmap right = BitmapFactory.decodeResource(getResources(), R.drawable.right);
    Bitmap range = BitmapFactory.decodeResource(getResources(), R.drawable.colorrange);
    Bitmap pointer = BitmapFactory.decodeResource(getResources(), R.drawable.pointer);

    ButtonView leftB = new ButtonView(left);
    ButtonView rightB = new ButtonView(right);
    ButtonView rate = new ButtonView(range);
    int hightC, massC, forceC;

    public SurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    public boolean onTouchEvent(MotionEvent event) {
        float touchX, touchY;
        touchX = event.getX();
        touchY = event.getY();
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if ((touchX<200 && touchY>(canvas.getHeight()-400)&& touchY<(canvas.getHeight()-200))){
            leftB.left = true;
            leftB.execute(thread.landingCraft, canvas, touchX);
        }
        if ((touchX>=canvas.getWidth()-200 && touchY>(canvas.getHeight()-400))&& touchY<(canvas.getHeight()-200)) {
            rightB.right = true;
            rightB.execute(thread.landingCraft, canvas, touchX);
        }
        if ((touchY>(canvas.getHeight()-110)&& touchY<=(canvas.getHeight()))){
            rate.rate = true;
            rate.execute(thread.landingCraft, canvas, touchX);
        }
        return true;
    }
    class DrawThread extends Thread {
        boolean runFlag = true;
        SurfaceHolder holder;
        Paint p;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.lend);
        Craft landingCraft = new Craft(massC, hightC, forceC, b);
        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.mon);
        Bitmap backgr = BitmapFactory.decodeResource(getResources(), R.drawable.space);
        public DrawThread (SurfaceHolder holder){
            this.holder = holder;
        }
        Moon moon = new Moon(b1, landingCraft.hight);
        @Override
        public void run() {
            super.run();
            pointer = Bitmap.createScaledBitmap(pointer, 15, 90, false);
            landingCraft.moon=moon;
            while (runFlag){
                Canvas c = holder.lockCanvas();
                canvas = c;
                moon.bitmap = Bitmap.createScaledBitmap(moon.bitmap, c.getWidth()/7, c.getWidth()/7, false);
                if (c!=null){
                    double k = (c.getWidth()/2-20-moon.bitmap.getWidth())/((landingCraft.hight));
                    backgr = Bitmap.createScaledBitmap(backgr, (int)(c.getWidth()*1.5), c.getHeight(), false);
                    c.drawBitmap(backgr, 0, 0, null);
                    moon.k = k;
                    landingCraft.k = k;
                    moon.x = c.getWidth()/2;
                    moon.y = c.getHeight()/2;
                    moon.drawMoon(c);
                    landingCraft.drawCraft(c);
                    landingCraft.lend(c);
                    p = new Paint();
                    p.setStyle(Paint.Style.FILL);
                    p.setColor(Color.BLUE);
                    rightB.x = canvas.getWidth()-200; rightB.y = canvas.getHeight()-400;
                    leftB.x = 0; leftB.y = canvas.getHeight()-400;
                    rightB.drawButton(c);
                    leftB.drawButton(c);
                    rate.x = 5; rate.y = canvas.getHeight()-95;
                    rate.b = Bitmap.createScaledBitmap(range, c.getWidth()-10, 80, false);
                    range = Bitmap.createScaledBitmap(range, c.getWidth()-20, 90, false);
                    pointer = Bitmap.createScaledBitmap(pointer, 30, 100, false);
                    c.drawBitmap(range, 10, c.getHeight()-100, null);
                    c.drawBitmap(pointer, 5+(c.getWidth()-10)*landingCraft.coef, c.getHeight()-100, null);
                    // отрисовка показателя тяги
                    String text = Integer.toString((int)(landingCraft.coef*100))+"%";
                    Rect mTextBoundRect = new Rect();
                    p.setColor(Color.GRAY);
                    p.setStyle(Paint.Style.FILL);
                    p.setAntiAlias(true);
                    p.setTextSize(50);
                    p.getTextBounds(text, 0, text.length(), mTextBoundRect);
                    float mTextWidth = p.measureText(text);
                    float mTextHeight = mTextBoundRect.height();
                    canvas.drawText(text, landingCraft.coef*c.getWidth()+10 - (mTextWidth / 2f), c.getHeight()-100 - (mTextHeight), p);
                    //отрисовка мин
                    text = "0%";
                    p.getTextBounds(text, 0, text.length(), mTextBoundRect);
                    mTextWidth = p.measureText(text);
                    mTextHeight = mTextBoundRect.height();
                    canvas.drawText(text, 10, c.getHeight()-80 + (mTextHeight), p);
                    //отрисовка макс
                    text = "100%";
                    p.getTextBounds(text, 0, text.length(), mTextBoundRect);
                    mTextWidth = p.measureText(text);
                    mTextHeight = mTextBoundRect.height();
                    canvas.drawText(text, c.getWidth()-(mTextWidth), c.getHeight()-80 + (mTextHeight), p);
                    //forse
                    text = "tracing force";
                    p.getTextBounds(text, 0, text.length(), mTextBoundRect);
                    mTextWidth = p.measureText(text);
                    mTextHeight = mTextBoundRect.height();
                    canvas.drawText(text, c.getWidth()/2-(mTextWidth)/2, c.getHeight()-80 + (mTextHeight), p);
                    //time
                    text = "TIME: "+landingCraft.time+" sec";
                    mTextBoundRect = new Rect();
                    p.setTextSize(40);
                    p.getTextBounds(text, 0, text.length(), mTextBoundRect);
                    mTextWidth = p.measureText(text);
                    mTextHeight = mTextBoundRect.height();
                    canvas.drawText(text, 10+mTextHeight/2, mTextHeight+10, p);
                    //hight
                    text = "HIGHT: "+landingCraft.hightChange+" meters";
                    mTextBoundRect = new Rect();
                    p.getTextBounds(text, 0, text.length(), mTextBoundRect);
                    mTextWidth = p.measureText(text);
                    mTextHeight = mTextBoundRect.height();
                    canvas.drawText(text, 10+mTextHeight/2, mTextHeight*2+15, p);
                    //speed
                    text = "SPEED: "+Math.sqrt(Math.pow(landingCraft.speedOrbit,2)+Math.pow(landingCraft.speedDawn,2))+" mps";
                    mTextBoundRect = new Rect();
                    p.getTextBounds(text, 0, text.length(), mTextBoundRect);
                    mTextWidth = p.measureText(text);
                    mTextHeight = mTextBoundRect.height();
                    canvas.drawText(text, 10+mTextHeight/2, mTextHeight*3, p);
                    holder.unlockCanvasAndPost(c);
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(landingCraft.hightChange<=0){
                        text = "LENDED";
                        mTextBoundRect = new Rect();
                        p.setColor(Color.GREEN);
                        p.setTextSize(100);
                        p.getTextBounds(text, 0, text.length(), mTextBoundRect);
                        mTextWidth = p.measureText(text);
                        mTextHeight = mTextBoundRect.height();
                        canvas.drawText(text, c.getWidth()/2-mTextWidth, c.getHeight()/2-mTextHeight, p);
                        runFlag = false;
                    }
                }
            }
        }
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new DrawThread(holder);
        thread.start();
        if(thread.isAlive()){
            if (thread.landingCraft.hightChange<=0){
                thread.runFlag = false;
            }
        }
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread.runFlag = false;
        thread = new DrawThread(holder);
        thread.start();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.runFlag = false;
    }
}
