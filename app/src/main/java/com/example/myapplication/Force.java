package com.example.myapplication;
public class Force {
    double value,  angle;
    public Force(double value, double angle) {
        this.value = value;
        this.angle = angle;
        while (this.angle>Math.PI*2){
            this.angle-=Math.PI*2;
        }
        while (this.angle<Math.PI*-2){
            this.angle+=Math.PI*2;
        }
    }
}
