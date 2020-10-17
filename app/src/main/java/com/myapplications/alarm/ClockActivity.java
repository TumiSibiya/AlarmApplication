package  com.myapplications.alarm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/*
* @Author Tumi Sibiya
*
*
* */

public class ClockActivity extends AppCompatActivity {

    private final String TAG = ClockActivity.class.getName();

    CountDownTimer countDownTimer;

    TextView digitalTimeTextView;
    TextView dateTextView;

    Button buttonAccessTimer;
    Button buttonAccessStopwatch;
    //Button buttonOptions;

    Intent switchActivity;


    SimpleDateFormat simpleTimeFormat;
    SimpleDateFormat simpleDateFormat;

    String currentTime;
    String currentDate;

    Changes accessingChanges = new Changes();

    String timePatten = accessingChanges.gettPatten();
    String datePatten = accessingChanges.getdPatten();

    Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_layout);

        setTitle("Clock");

        date = new Date();

        simpleTimeFormat = new SimpleDateFormat(timePatten, Locale.getDefault());
        simpleDateFormat = new SimpleDateFormat(datePatten, Locale.getDefault());

        currentTime = simpleTimeFormat.format(date);
        currentDate = simpleDateFormat.format(date);


        digitalTimeTextView = findViewById(R.id.digit_time_textview_id);
        dateTextView = findViewById(R.id.date_textviewid);

        buttonAccessTimer = findViewById(R.id.buttonAccessTimerId);
        buttonAccessStopwatch = findViewById(R.id.buttonAccessStopwatchId);


        //switchActivityButtons
       buttonAccessTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity = new Intent(ClockActivity.this, TimerActivity.class);
                startActivity(switchActivity);
                Log.d(TAG, "accessing ClockPressed");
            }
        });

        buttonAccessStopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity = new Intent(ClockActivity.this, StopwatchActivity.class);
                startActivity(switchActivity);
                Log.d(TAG, "accessSing StopwatchPressed");
            }
        });

        //displaying time and date on TextView
        digitalTimeTextView.setText(String.valueOf(currentTime));
        dateTextView.setText(String.valueOf(currentDate));

        //updaters and CountDownTimer
        countDownTimer = new CountDownTimer(System.currentTimeMillis(), 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTime();
            }

            @Override
            public void onFinish() {
                updateTime();
            }

        }.start();

    }

    public void updateTime() {

        //String instantPatten = request);
        String instantPattan = accessingChanges.gettPatten();

        //updating time every 1 second / 1000 millis
        SimpleDateFormat instantSimpleDateFormat = new SimpleDateFormat(
                instantPattan, Locale.getDefault());

        String instantTime = instantSimpleDateFormat.format(new Date());

        if (digitalTimeTextView != null) {

            digitalTimeTextView.setText(instantTime);

        }

    }

    //on bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem items) {

        Intent intent;

        if ("Settings".equalsIgnoreCase(String.valueOf(items.getTitle()))) {
            Toast.makeText(this, "Settings under productions ", Toast.LENGTH_SHORT).show();

        } else if ("About".equalsIgnoreCase(String.valueOf(items.getTitle()))) {

            Toast.makeText(this, "about clicked ", Toast.LENGTH_SHORT).show()
            ;
        } else if ("Exit".equals(String.valueOf(items.getTitle()))) {
            Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show();
        }

        return true;
    }


    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);

        timePatten = sharedPreferences.getString("timePatten", timePatten);

        updateTime();
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor simpleEditor = prefs.edit();

        simpleEditor.putString("currentTime", currentTime);
        simpleEditor.putString("currentDate", currentTime);
        simpleEditor.putString("timePatten", timePatten);
        simpleEditor.putString("datePatten", datePatten);

        simpleEditor.apply();

        updateTime();
    }
}