package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends Activity {
    public int mass,  tractionforce,  hight;
    public float speed;
    private SeekBar mass1, force, hight1;
    private TextView massText, forcetext,  hightText, fuelspeedText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mass1 = findViewById(R.id.mass);
        hight1 = findViewById(R.id.hight);
        force = findViewById(R.id.force);
        massText = findViewById(R.id.massText);
        hightText=findViewById(R.id.hightText);
        forcetext=findViewById(R.id.forcetext);
        mass1.setOnSeekBarChangeListener(seekBarChangeListener);
        force.setOnSeekBarChangeListener(seekBarChangeListener);
        hight1.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    public void onClickControl(View view){
        Intent intent = new Intent(MainActivity.this, Landing.class);
        intent.putExtra("Mass", mass);
        intent.putExtra("Force", tractionforce);
        intent.putExtra("Hight", hight);
        Log.i("MyT", mass+" "+ tractionforce+" "+ hight);
        startActivity(intent);
    }
    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(seekBar == mass1){
                massText.setText("Mass of ship: "+seekBar.getProgress()+" tons");
            }
            if(seekBar == force){
                forcetext.setText("Traction force: "+seekBar.getProgress()+ "N");
            }
            if(seekBar==hight1){
                hightText.setText("Hight: "+seekBar.getProgress()+"m");
            }
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if(seekBar == mass1){
                mass=seekBar.getProgress();
                Log.i("myT", mass+"t");
            }
            if(seekBar == force){
                tractionforce = seekBar.getProgress();
                Log.i("myT", tractionforce+"kN");
            }
            if(seekBar==hight1){
                hight=seekBar.getProgress();
                Log.i("myT", hight+"m");
            }
        }


    };

}
