package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.TreeMap;

public class Diogamm extends AppCompatActivity {
    public int mass, rate, fuelSpeed, hight;
    public float speed;
    Diogram v;
    float [] lis = new float[100];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diogamm);
        mass = (int) getIntent().getSerializableExtra("Mass");
        rate = (int) getIntent().getSerializableExtra("GasRate");
        fuelSpeed = (int) getIntent().getSerializableExtra("FuelSpeed");
        hight = (int) getIntent().getSerializableExtra("Hight");
        speed = (float) getIntent().getSerializableExtra("Speed");
        v = findViewById(R.id.v);
        Log.i("Res", mass+" "+ rate);
    }

    public void onClick(View view) {
        Intent intent = new Intent(Diogamm.this, Landing.class);
        intent.putExtra("Mass", mass);
        intent.putExtra("GasRate", rate);
        intent.putExtra("FuelSpeed", fuelSpeed);
        intent.putExtra("Hight", hight);
        intent.putExtra("Speed", speed);
        ArrayList<Float> keylist = new ArrayList<>();
        int k = 0;
        for (float i: v.list.values()){
            keylist.add((100 - i)/100);
        }
        for (int i = 0; i < 100; i++) {
            lis[i] =keylist.get(i);
        }
        intent.putExtra("list", lis);
        startActivity(intent);
    }
}
