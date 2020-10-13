package com.myapplications.alarm;

/**
 * Thins Activity uses chronometer to simulate StopwatchActivity
 * behaviour.
* @author Tumi Sibiya
*
* */

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class StopwatchActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean running;
    private long pauseOffSet;

    //declare buttons
    Button startButton;
    Button pauseButton;
    Button resetButton;

    String TAG = StopwatchActivity.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stopwatch_layout);

        setTitle("Stopwatch");

        TextView ignoreThisTextView = findViewById(R.id.textView2);
        //chronometer
        chronometer = findViewById(R.id.simplechronometer);

        //dealing with Button
        startButton = findViewById(R.id.startbutton);
        pauseButton = findViewById(R.id.pausebutton);
        resetButton = findViewById(R.id.resetbutton);


        //setButtonActions
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChronometer(view);
                handleButtonFeatures();
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseChronometer(view);
                handleButtonFeatures();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetChronometer(view);
                handleButtonFeatures();
            }
        });

    }

    //chronometer actions
    public void startChronometer(View view) {

        Log.d("PAUSE_OFFSET IS ", ""+ pauseOffSet);
        if (!running) {
            chronometer.setBase( SystemClock.elapsedRealtime() - pauseOffSet);

            startButton.setText("Start");
            chronometer.start();
            running = true;
        }
        handleButtonFeatures();
    }

    public void pauseChronometer(View view) {
        if (running) {

            startButton.setText("Resume");

            pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase();

            chronometer.stop();
            running = false;
        }

        handleButtonFeatures();
    }

    public void resetChronometer(View view) {

        chronometer.stop();

        startButton.setText("sTART");

        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffSet = 0;

        running = false;

        handleButtonFeatures();
    }

    void handleButtonFeatures() {

        if (!running) {

            startButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.INVISIBLE);
            resetButton.setVisibility(View.VISIBLE);

        } else if (running) {

            startButton.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
        }
    }
/*
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong("pauseOffSetKey", pauseOffSet);
        outState.putBoolean("runningKey", running);

        handleButtonFeatures();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        pauseOffSet = savedInstanceState.getLong("pauseOffSetKey");
        running = savedInstanceState.getBoolean("runningKey");


        handleButtonFeatures();
    }*/


    long base;

    @Override
    public void onStop(){
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor simpleEditor = prefs.edit();

        base = chronometer.getBase();

        simpleEditor.putLong("base", base);
        simpleEditor.putLong("pauseOffSet",pauseOffSet);
        simpleEditor.putBoolean("running", running);

        handleButtonFeatures();
        simpleEditor.apply();
    }

    @Override
    public void onStart(){
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);

        base = sharedPreferences.getLong("'base", base);

        pauseOffSet = sharedPreferences.getLong("pauseOffSet", pauseOffSet);
        running = sharedPreferences.getBoolean("running", running);

        handleButtonFeatures();
    }
    @Override
    public void onRestart(){
        super.onRestart();
    }
}