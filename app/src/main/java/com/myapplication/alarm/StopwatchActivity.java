package com.myapplication.alarm;

/*
 * Thins Activity uses chronometer to simulate StopwatchActivity
 * behaviour.
* @author Tumi Sibiya
*
* */

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.SharedPreferences;
import android.content.Intent;

import android.graphics.Color;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;
public class StopwatchActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean running;
    private long pauseOffSet;
    private long chronometerElapsedRealTime;

    //declare buttons
    Button startButton;
    Button pauseButton;
    Button resetButton;

    ProgressBar stopwatchProgressBar;
    int progressBarMaxTime;
    int progressBarCurrentTime;

    String TAG = StopwatchActivity.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.stopwatch_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Stopwatch");

        TextView ignoreThisTextView = findViewById(R.id.textView2);

        //chronometer
        chronometer = findViewById(R.id.simplechronometer);

        //dealing with Button
        startButton = findViewById(R.id.startbutton);
        pauseButton = findViewById(R.id.pausebutton);
        resetButton = findViewById(R.id.resetbutton);

        stopwatchProgressBar = findViewById(R.id.stopwatchProgressBarId);

        //setButtonActions
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startChronometer(view);
                updateInterface();
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseChronometer(view);
                updateInterface();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetChronometer(view);
                updateInterface();
            }
        });

        stopwatchProgressBar.setMax(10);
        stopwatchProgressBar.setProgress(10);


    }

    //chronometer actions
    public void startChronometer(View view) {

        Log.d("PAUSE_OFFSET IS ", ""+ pauseOffSet);
        if (!running) {

            chronometer.setBase( SystemClock.elapsedRealtime() - pauseOffSet);

            chronometerElapsedRealTime = SystemClock.elapsedRealtime() - pauseOffSet;

            startButton.setText("Start");
            chronometer.start();

            running = true;
        }
    }

    public void pauseChronometer(View view) {
        if (running) {

            startButton.setText("Resume");
            pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase();
            chronometer.stop();
            running = false;
        }
    }

    public void resetChronometer(View view) {

        chronometer.stop();

        startButton.setText("sTART");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometerElapsedRealTime = SystemClock.elapsedRealtime();
        pauseOffSet = 0;

        running = false;

    }

    void updateInterface() {

        stopwatchProgressBar.setMax(10);

        if (!running) {

            startButton.setVisibility(View.VISIBLE);
            pauseButton.setVisibility(View.INVISIBLE);
            resetButton.setVisibility(View.VISIBLE);


        } else if (running) {

            startButton.setVisibility(View.INVISIBLE);
            pauseButton.setVisibility(View.VISIBLE);
            resetButton.setVisibility(View.VISIBLE);
        }
        Log.d(TAG, "chronometer ElapsedReal Time " + chronometerElapsedRealTime);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onStop(){
        super.onStop();

        Intent activityIntent;
        PendingIntent pendingActivityIntent;

        Intent actionIntent;
        PendingIntent pendingActionIntent;

        NotificationCompat.Builder builder;

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor simpleEditor = prefs.edit();

        simpleEditor.putLong("chronometerElapsedRealTime", chronometerElapsedRealTime);
        simpleEditor.putLong("pauseOffSet", pauseOffSet);

        simpleEditor.putBoolean("running", running);

        simpleEditor.apply();


        if(running) {

            activityIntent = new Intent(this, StopwatchActivity.class);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            pendingActivityIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

            actionIntent = new Intent(this, ApplicationBroadcastReceiver.class);
            actionIntent.setAction("stopChronometer");
            pendingActionIntent = PendingIntent.getBroadcast(
                    this, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder = new NotificationCompat.Builder(this, getString(R.string.stopwatch_notification_id))

                    .setSmallIcon(R.drawable.ic_stopwatch)
                    .setContentTitle("Stopwatch")
                    .setContentText("Stopwatch running" + " " + chronometer.getText())
                    .setContentIntent(pendingActivityIntent)
                    //.addAction(R.drawable.ic_baseline_stop_icon, getString(R.string.stopButtonPress), pendingActionIntent)
                    //.addAction(R.drawable.ic_baseline_reset_icon, getString(R.string.resetbuttonpress),pendingActionIntent)
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .setOnlyAlertOnce(true)
                    .setColor(Color.RED)
                    .setCategory(Notification.CATEGORY_PROGRESS)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(110, builder.build());
        }


    }

    @Override
    public void onStart(){
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);

        chronometerElapsedRealTime = sharedPreferences.getLong("chronometerElapsedRealTime",
                chronometerElapsedRealTime);
        pauseOffSet = sharedPreferences.getLong("pauseOffSet", pauseOffSet);
        running = sharedPreferences.getBoolean("running", running);

        //just keeping the chronometer on previous time
        chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffSet);

        if(running){

            //actually setting chronometer base onto previous time
            chronometer.setBase(chronometerElapsedRealTime);
            chronometer.start();
            running = true;

        }else {
            chronometer.stop();
            running = false;
        }

        updateInterface();
    }


}