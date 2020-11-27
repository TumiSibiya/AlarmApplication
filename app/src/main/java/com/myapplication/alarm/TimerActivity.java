package com.myapplication.alarm;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;

import android.content.SharedPreferences;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Build;
import android.os.CountDownTimer;

import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import java.util.Locale;
import java.util.Objects;

import static android.widget.Toast.LENGTH_SHORT;
/*
 *
 *  TimerActivity uses CountDownTimer class to simulate Timer.
 *   this class converts System.currentMillis() to hours, minutes and seconds
 *   then display onto textview
 *
 * program by : @arthur Tumi Sibiya
 *
 * */

public class TimerActivity extends AppCompatActivity {


    public long start_time_in_millis = 0;
    public long time_left_in_millis;//= start_time_in_millis;
    public long endTime;

    EditText hourEditText;
    EditText minuteEditText;
    EditText secondEditText;

    TextView timerTextView;

    Button buttonStartPauseTimer;
    Button buttonResetTimer;
    Button buttonClearSet;
    Button buttonSet;
    Button buttonNewTime;

    CountDownTimer countDownTimer;
    boolean isRunning;

    ProgressBar timerProgressBar;

    final int timerProgressBarDivider = 10000;

    long timerProgressBarMax;
    long timerProgressBarCurrentTime;

    private String TAG = TimerActivity.class.getName();

    long total;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_layout);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Timer");

        Log.d(TAG,"INSIDE CREATE");

        hourEditText = findViewById(R.id.hours_edit_text_id);
        minuteEditText = findViewById(R.id.minute_edit_text_id);
        secondEditText = findViewById(R.id.seconds_edit_text_id);

        timerTextView = findViewById(R.id.textviewfortimer);

        buttonStartPauseTimer = findViewById(R.id.buttonStartPauseTimerId);
        buttonResetTimer = findViewById(R.id.buttonRestTimerId);

        buttonClearSet = findViewById(R.id.button_clear_set);

        buttonSet = findViewById(R.id.button_set);

        buttonNewTime = findViewById(R.id.button_NewTime_id);

        timerProgressBar = findViewById(R.id.progressBar);
        timerProgressBar.setProgress(0);;

        //setButtonActions
        buttonStartPauseTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRunning) {
                    if(time_left_in_millis == 0){
                        Toast.makeText(TimerActivity.this, "Time Up ", Toast.LENGTH_SHORT).show();
                        buttonNewTime.setClickable(true);
                    }else{

                        startCountDownTimer();
                    }

                } else
                    pauseCountDownTimer();
            }
        });

        buttonResetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetCountDownTimer();
                updateCountDownTimerTextView();
                updateInterface();
            }
        });

        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tempHideViewVisibilityChangeNewButtonText();

                String hourString = hourEditText.getText().toString();

                String minuteString = minuteEditText.getText().toString();

                String secondString = secondEditText.getText().toString();


                if(hourString.length() == 0){

                    if (minuteString.length() == 0) {

                        if(secondString.length() == 0) {
                            Toast.makeText(TimerActivity.this, "Invalid Number", LENGTH_SHORT).show();
                            return;
                        }

                        total = Long.parseLong(secondString) * 1000;


                    }else if(secondString.length() !=0){

                        long minute = Long.parseLong(minuteString) * 60000;
                        long second = Long.parseLong(secondString) * 1000;

                        total = minute + second;

                    }else{
                        total = Long.parseLong(minuteString) * 60000;
                    }

                    //total = Long.parseLong(minuteString);

                }else {

                    long hour = Long.parseLong(hourString) *  360_000 * 10;
                    Log.d(TAG, "After parsing hours is now "+ hour);

                    if(minuteString.length() == 0 && secondString.length() == 0) {

                        total = hour;

                    }else if(secondString.length() == 0){

                        long minute = Long.parseLong(minuteString) * 60000;

                        total = minute + hour;

                    }else{

                        long minute = Long.parseLong(minuteString) * 60000;
                        long second = Long.parseLong(secondString) * 1000;

                        total = hour + minute+ second;
                    }

                }
                setTime(total);
                resetCountDownTimer();
                updateCountDownTimerTextView();
                updateInterface();
            }
        });

        buttonClearSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hourEditText.getText().clear();
                minuteEditText.getText().clear();

                Log.d(TAG, "User cleared Edit text");
            }
        });

        buttonNewTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                tempHideViewVisibilityChangeNewButtonText();
            }
        });
    }

    //button actions
    public void setTime(long millis) {
        start_time_in_millis = millis;
        timerProgressBarMax = start_time_in_millis;
        timerProgressBar.setMax((int)start_time_in_millis);
        timerProgressBar.setProgress((int)start_time_in_millis);
    }

    public void startCountDownTimer() {

        endTime = System.currentTimeMillis() + time_left_in_millis;

        countDownTimer = new CountDownTimer(time_left_in_millis, 1000) {
            @Override
            public void onTick(long timeUntilFinish) {
                time_left_in_millis = timeUntilFinish;
                timerProgressBarCurrentTime = timerProgressBar.getProgress();

                updateCountDownTimerTextView();
                updateInterface();

                timerProgressBar.setProgress((int)time_left_in_millis);

            }

            @Override
            public void onFinish() {
                timerProgressBar.setProgress(0);
                isRunning = false;
                updateInterface();
                notifyUser();
            }
        }.start();
        isRunning = true;
    }

    public void pauseCountDownTimer() {
        countDownTimer.cancel();
        isRunning = false;
        updateInterface();
    }

    public void resetCountDownTimer() {

        time_left_in_millis = start_time_in_millis;

        isRunning = false;
        timerProgressBar.setProgress((int)start_time_in_millis);
        updateCountDownTimerTextView();
        updateInterface();
    }

    //udate countdown
    public void updateCountDownTimerTextView() {

        int hours = (int) (time_left_in_millis / 1000) / 3600;
        Log.d(TAG, "Hours returned : " + hours);

        int minutes = (int) ((time_left_in_millis / 1000) % 3600) / 60;
        Log.d(TAG, "Minutes returned :" + minutes);

        int seconds = (int)((time_left_in_millis / 1000) % 60 );
        Log.d(TAG, "Seconds returned : " + seconds);

        String formattedTimeLeftInMillis;

        if (hours == 0) {

            if(minutes ==0){
                formattedTimeLeftInMillis = String.format(Locale.getDefault(), "%02d", seconds);

            }else{
                formattedTimeLeftInMillis = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            }

        } else{

            formattedTimeLeftInMillis = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        }

        timerTextView.setText(formattedTimeLeftInMillis);
    }

    void updateInterface() {

        //if timer is not running do this
        if (!isRunning) {

            //if timer is countdown is not yet finished but paused do this
            if (time_left_in_millis != 0 && countDownTimer != null) {

                buttonStartPauseTimer.setText("Start");
                buttonResetTimer.setVisibility(View.VISIBLE);

            } else {//lest fo this

                buttonStartPauseTimer.setText("Start");
                buttonResetTimer.setVisibility(View.INVISIBLE);

            }

            buttonNewTime.setClickable(true);

        } else {//or else if time is running do this

            buttonStartPauseTimer.setText("Pause");
            buttonResetTimer.setVisibility(View.INVISIBLE);
            buttonNewTime.setClickable(false);

            if (buttonStartPauseTimer.getText().equals("Pause")) {
                //TODO add some cool features on active countdown and buttonStartPause getText(); return Pause and view
            }


        }

        updateCountDownTimerTextView();
        timerProgressBar.setMax((int)timerProgressBarMax);

    }
    protected  void tempHideViewVisibilityChangeNewButtonText(){


        String buttonContent = buttonNewTime.getText().toString();

        if(buttonContent.equalsIgnoreCase("NEW TIME")){

            timerTextView.setVisibility(View.INVISIBLE);
            buttonStartPauseTimer.setVisibility(View.INVISIBLE);
            buttonResetTimer.setVisibility(View.INVISIBLE);

            buttonSet.setVisibility(View.VISIBLE);
            buttonClearSet.setVisibility(View.VISIBLE);


            hourEditText.setVisibility(View.VISIBLE);
            minuteEditText.setVisibility(View.VISIBLE);
            secondEditText.setVisibility(View.VISIBLE);

            buttonNewTime.setText("Cancel");


        }else if(buttonContent.equalsIgnoreCase("Cancel")){

            timerTextView.setVisibility(View.VISIBLE);
            buttonStartPauseTimer.setVisibility(View.VISIBLE);
            buttonResetTimer.setVisibility(View.INVISIBLE);

            buttonSet.setVisibility(View.INVISIBLE);;
            buttonClearSet.setVisibility(View.INVISIBLE);


            hourEditText.setVisibility(View.INVISIBLE);
            minuteEditText.setVisibility(View.INVISIBLE);
            secondEditText.setVisibility(View.INVISIBLE);

            buttonNewTime.setText("NEW TIME");

        }
    }

    public void notifyUser(){

        Intent timerActivityIntent = new Intent(this, TimerActivity.class);
        timerActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingActivityIntent = PendingIntent.getActivity(this, 0, timerActivityIntent, 0);

        Intent actionIntent = new Intent(this, AppBroadcastReceiver.class);
        actionIntent.putExtra("restart", "restartCountdownTimer");
        PendingIntent pendingActionIntent = PendingIntent.getBroadcast(this,
                0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

         final NotificationCompat.Builder notificationCompatBuilder = new NotificationCompat.Builder(
                 this, getString(R.string.timer_time_up_notification_channel_id))

                 .setSmallIcon(R.drawable.ice_timer)
                 .setContentTitle(getString(R.string.timer_notification_content_title))
                 .setContentText(getString(R.string.timer_notification_time_up__text))
                 .setContentIntent(pendingActivityIntent)
                 .addAction(R.drawable.ice_timer,
                         getString(R.string.restartButtonPress), pendingActionIntent)
                 .setChannelId(getString(R.string.timer_time_up_notification_channel_id))
                 .setColor(Color.BLUE)
                 .setCategory(Notification.CATEGORY_ALARM)
                 .setPriority(NotificationCompat.PRIORITY_HIGH);

        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(10, notificationCompatBuilder.build());

    }
    //this feature requires attentions
    //delete android:screenOrientation="portrait" on Manifest for default features or go Auto

    //save values and state before on stop
    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, ".........................................STOPPING");

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor simpleEditor = prefs.edit();

        simpleEditor.putLong("start_time_in_millis", start_time_in_millis);
        simpleEditor.putLong("time_left_in_millis", time_left_in_millis);
        simpleEditor.putLong("timerProgressBarMax", timerProgressBarMax);
        simpleEditor.putLong("timerProgressBarCurrentTime", timerProgressBarCurrentTime);
        simpleEditor.putLong("endTime", endTime);
        simpleEditor.putBoolean("isRunning", isRunning);

        simpleEditor.apply();

    }

    @Override
    public void onRestart(){
        super.onRestart();

        Log.d(TAG, "......................................RESTARTING");
    }
    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, ".........................................STARTING");

        SharedPreferences sharedPrefs = getSharedPreferences("prefs", MODE_PRIVATE);

        start_time_in_millis = sharedPrefs.getLong("start_time_in_millis", 0);
        time_left_in_millis = sharedPrefs.getLong("time_left_in_millis", start_time_in_millis);
        timerProgressBarMax = sharedPrefs.getLong("timerProgressBarMax", timerProgressBarMax);
        timerProgressBarCurrentTime = sharedPrefs.getLong("timerProgressBarCurrentTime", timerProgressBarCurrentTime);
        isRunning = sharedPrefs.getBoolean("isRunning", false);


        updateCountDownTimerTextView();


        if (isRunning) {
            endTime = sharedPrefs.getLong("endTime", 0);
            time_left_in_millis = endTime - System.currentTimeMillis();

            if (time_left_in_millis <= 0) {

                time_left_in_millis = 0;
                isRunning = false;
                timerProgressBar.setProgress(0);
            } else {

                updateCountDownTimerTextView();
                startCountDownTimer();
            }

        }

        updateInterface();
        timerProgressBar.setMax((int)timerProgressBarMax);
        timerProgressBar.setProgress((int)timerProgressBarCurrentTime);

    }
}
