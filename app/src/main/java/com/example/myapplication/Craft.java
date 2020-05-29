package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import java.util.ArrayList;



public class Craft {
    double angle1,k, speedOrbit, speedDawn, angle0,  hight, mass, hightChange, force, rH, gM;
    float  x, y, coef;
    ArrayList <Double> diogram;
    Bitmap bitmap;
    Moon moon;
    int time;
    Matrix matrix;
    double angelespeed;
    boolean changeleft,  changeright ;


    public Craft(int mass,  float hight, float force, Bitmap bitmap) {
        this.mass = mass*1000;
        this.hight = hight;
        hightChange=hight;
        this.force = force;
        rH= 1737000+hightChange;
        gM =6.674*7.55*Math.pow(10, 11);
        this.bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/30, bitmap.getHeight()/30, false);
        speedOrbit = Math.sqrt(gM/rH);
        angle0 = 0;
        angle1=Math.PI/2;
        x = 0;
        y = 0;
        speedDawn = 0;
        angelespeed = speedOrbit/(rH);
        coef = 1;
        time = 0;
    }

    public void drawCraft(Canvas c){
        c.drawBitmap(bitmap,matrix(angle1, x, y), null);
    }

    public Matrix matrix(double angle, float x, float y) {
        matrix = new Matrix();
        matrix.setTranslate(x-bitmap.getWidth()/2, y-bitmap.getHeight()/2);
        matrix.preRotate((float)((-angle1+Math.PI/2)*180/Math.PI), bitmap.getWidth()/2, bitmap.getHeight()/2);//поворт вокруг точки центра корабля
        return matrix;
    }

    public void lend(Canvas c){
        time++;
        rH= 1737000+hightChange;
        drawCraft(c);
        Force mg = new Force( (gM/Math.pow(rH, 2)), angle0);
        Force force = new Force((this.force*coef / mass), (angle1+Math.PI));
        angle0 += angelespeed;
        while (angle0>Math.PI*2){
            angle0-=Math.PI*2;
            if (angle1>Math.PI*2){
                angle1 -= Math.PI*2;
            }
        }
        while (angle0<0){
            angle0+=Math.PI*2;
            if (angle1<0){
                angle1 += Math.PI*2;
            }
        }
        double angle = (angle0+Math.PI/2-angle1-Math.PI);
        speedOrbit += -force.value*Math.cos(angle);
        angelespeed = speedOrbit/(rH);
        speedDawn += -force.value*Math.sin(angle) - mg.value + Math.pow(speedOrbit, 2)/rH;
        hightChange += speedDawn;
        x = moon.x + (float)(hightChange*k*Math.cos(angle0)+moon.bitmap.getWidth()*Math.cos(angle0));
        y = moon.y+(float)((hightChange*k)*Math.sin(-angle0)+moon.bitmap.getWidth()*Math.sin(-angle0));
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        drawWay(c);
        Log.i("MyT", hightChange+" "+force.value*Math.sin(angle)+" "+speedOrbit+" " + speedDawn+" angle "+ angle);
    }
    public void drawWay(Canvas c){
        double hightchange1 = hightChange;
        float x1 = x;
        float y1=y;
        double speedorbit1 = speedOrbit;
        double anglespeed1 = angelespeed;
        double rH1 = rH;
        double angle01 = angle0;
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(x, y);
        for (int i = 0; i < 8000; i++) {
            hightchange1+=speedDawn;
            rH1 = 1737000 + hightchange1;
            anglespeed1 = speedOrbit/rH1;
            angle01+=anglespeed1;
            x1 = moon.x + (float)(hightchange1*k*Math.cos(angle01)+moon.bitmap.getWidth()*Math.cos(angle01));
            y1 = moon.y+(float)((hightchange1*k)*Math.sin(-angle01)+moon.bitmap.getWidth()*Math.sin(-angle01));
            path.lineTo(x1, y1);
        }
        c.drawPath(path, paint);
    }

}

