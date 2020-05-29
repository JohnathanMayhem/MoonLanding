package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ButtonView {
    boolean onTouched = false;
    boolean left = false;
    boolean right=false;
    boolean inc = false;
    boolean dec = false;
    boolean rate = false;
    float x, y;
    Bitmap b;
    Craft c;
    public ButtonView(Bitmap bitmap) {
        b = Bitmap.createScaledBitmap(bitmap, 200, 200, false);
    }
    public void execute(Craft craft, Canvas c, float x){
        Paint p = new Paint();
        if (left){
            craft.angle1+=Math.PI/360;
        }
        if (right){
            craft.angle1-=Math.PI/360;
        }
        if (rate){
           craft.coef = (x-5)/(c.getWidth()-10);
        }
    }
    public void drawButton(Canvas c){
        c.drawBitmap(b, x, y, null);
    }
}
