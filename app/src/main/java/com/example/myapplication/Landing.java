package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class Landing extends AppCompatActivity {
    private SurfaceView v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        v = findViewById(R.id.view);
        v.massC = (int) getIntent().getSerializableExtra("Mass");
        v.forceC = (int) getIntent().getSerializableExtra("Force");
        v.hightC = (int) getIntent().getSerializableExtra("Hight");
        Log.i("MyTag", "sended"+ v.massC+" "+v.forceC+" "+v.hightC+" craft");

    }
}
