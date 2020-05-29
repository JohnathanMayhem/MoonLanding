package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

public class Moon {
    float x, y;
    double k;
    Bitmap bitmap;


    public Moon(Bitmap bit, double hight) {
        bitmap = bit;
    }
    public void drawMoon(Canvas c){
        //bitmap = Bitmap.createScaledBitmap(bitmap, (int) ((k*2*1737000-60)), (int)((k*2*1737000-60)), false);
        //bitmap = Bitmap.createScaledBitmap(bitmap, (int) (150), (int)(150), false);
        c.drawBitmap(bitmap,x, y , null );
    }
    public void rotate(Canvas c){

    }
}