package com.myapplications.alarm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;
/*
 *
 *  TimerActuvuty useses CoundDownTimer class to similate Timer.
 *   this class converts System.currentMillis() to hours, minutes and seconds
 *   then display onto textview
 *
 * program by : @authur Tumi Sibiya
 *
 * */

public class TimerActivity extends AppCompatActivity {

    public long start_time_in_millis = 0;
    public long time_left_in_millis;//= start_time_in_millis;
    public long endTime;
    EditText minuteEditText;
    TextView timerTextView;
    Button buttonStartPauseTimer;
    Button buttonResetTimer;
    Button buttonCancelSet;
    Button buttonSet;
    CountDownTimer countDownTimer;
    boolean isRunning;
    private String TAG = TimerActivity.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer_layout);
        setTitle("Timer");

        minuteEditText = findViewById(R.id.minute_edit_text_id);
        timerTextView = findViewById(R.id.textviewfortimer);

        buttonStartPauseTimer = findViewById(R.id.buttonStartPauseTimerId);
        buttonResetTimer = findViewById(R.id.buttonRestTimerId);

        buttonCancelSet = findViewById(R.id.button_cancel_set);
        buttonSet = findViewById(R.id.button_set);

        //setButtonActions
        buttonStartPauseTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isRunning) {
                    startCountDownTimer();
                } else
                    pauseCounDownTimer();
            }
        });

        buttonResetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetCountDownTimer();
                updateCoundDownTimerTextView();
                updateInteface();
            }
        });

        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String minuteString = minuteEditText.getText().toString();

                if (minuteString.length() == 0) {
                    Toast.makeText(TimerActivity.this, "Invalid Number", LENGTH_SHORT).show();
                    return;
                }

                long minute = Long.parseLong(minuteString) * 60000;
                if (minute == 0) {
                    Toast.makeText(TimerActivity.this, "Enter positive number", LENGTH_SHORT).show();
                    return;
                }

                setTime(minute);
                resetCountDownTimer();
                updateCoundDownTimerTextView();
                updateInteface();
            }
        });

        buttonCancelSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minuteEditText.getText().clear();
                Log.d(TAG, "User cleared minuteEditText");
            }
        });
    }

    //button actions
    public void setTime(long millis) {
        start_time_in_millis = millis;
    }

    public void startCountDownTimer() {

        endTime = System.currentTimeMillis() + time_left_in_millis;

        countDownTimer = new CountDownTimer(time_left_in_millis, 1000) {
            @Override
            public void onTick(long timeUntillFinish) {
                time_left_in_millis = timeUntillFinish;

                updateCoundDownTimerTextView();
                updateInteface();
            }

            @Override
            public void onFinish() {
                isRunning = false;
                updateInteface();
            }
        }.start();
        isRunning = true;
    }

    public void pauseCounDownTimer() {
        countDownTimer.cancel();
        //buttonStartPauseTimer.setText(R.string.resumeButtonPress);
        isRunning = false;
        updateInteface();
    }

    public void resetCountDownTimer() {

        time_left_in_millis = start_time_in_millis;

        isRunning = false;
        minuteEditText.setVisibility(View.VISIBLE);
        buttonCancelSet.setVisibility(View.VISIBLE);
        buttonSet.setVisibility(View.VISIBLE);
        updateCoundDownTimerTextView();
        updateInteface();
    }

    //udate countdown
    public void updateCoundDownTimerTextView() {
        int hours = (int) (time_left_in_millis / 1000) / 3600;
        Log.d(TAG, "Hourse returned : " + hours);

        int minutes = (int) ((time_left_in_millis / 1000) % 3600) / 60;
        Log.d(TAG, "Minutes returned :" + minutes);

        int seconds = (int) (time_left_in_millis / 1000) % 60;
        Log.d(TAG, "econdes returned : " + seconds);

        String formattedTimeLeftInMillis;

        if (minutes < 60 && hours == 0) {
            formattedTimeLeftInMillis = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            timerTextView.setText(formattedTimeLeftInMillis);
        } else {
            formattedTimeLeftInMillis = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
            timerTextView.setText(formattedTimeLeftInMillis);
        }
    }

    void updateInteface() {

        //if timer is not running do this
        if (!isRunning) {

            //if timer is coundown is not yet finished but paused do this
            if (time_left_in_millis != 0 && countDownTimer != null) {
                buttonStartPauseTimer.setText("Start");
                buttonResetTimer.setVisibility(View.VISIBLE);
            } else {//lest fo this

                buttonStartPauseTimer.setText("Start");
                buttonResetTimer.setVisibility(View.INVISIBLE);

            }


        } else {//or else if time is running do this
            buttonStartPauseTimer.setText("Pause");
            buttonResetTimer.setVisibility(View.INVISIBLE);

            if (buttonStartPauseTimer.getText().equals("Pause")) {
                minuteEditText.setVisibility(View.INVISIBLE);
                buttonCancelSet.setVisibility(View.INVISIBLE);
                buttonSet.setVisibility(View.INVISIBLE);
            }

        }
        updateCoundDownTimerTextView();

    }

    //this feature requires attentions
    //delete android:screenOrientation="portrait" on Manifiest for default features or go Auto

    //save values and state before on stop
    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor simpleEditor = prefs.edit();

        simpleEditor.putLong("start_time_in_millis", start_time_in_millis);
        simpleEditor.putLong("time_left_in_millis", time_left_in_millis);
        simpleEditor.putLong("endTime", endTime);
        simpleEditor.putBoolean("isRunning", isRunning);


        simpleEditor.apply();

    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPrefs = getSharedPreferences("prefs", MODE_PRIVATE);

        start_time_in_millis = sharedPrefs.getLong("start_time_in_millis", 0);
        time_left_in_millis = sharedPrefs.getLong("time_left_in_millis", start_time_in_millis);
        isRunning = sharedPrefs.getBoolean("isRunning", false);

        updateCoundDownTimerTextView();


        if (isRunning) {
            endTime = sharedPrefs.getLong("endTime", 0);
            time_left_in_millis = endTime - System.currentTimeMillis();
            if (time_left_in_millis <= 0) {

                time_left_in_millis = 0;
                isRunning = false;
            } else {
                updateCoundDownTimerTextView();
                startCountDownTimer();
            }
            updateInteface();
        }

    }

}
