package  com.myapplications.alarm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
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
 *
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
    String currenDate;

    String timePatten = new Changes().gettPatten();
    String datePatten = new Changes().getdPatten();

    Date date;

    Bundle bundle;
    String request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_layout);
        setContentView(R.layout.activity_clock_layout);

        setTitle("Clock");


        date = new Date();

        simpleTimeFormat = new SimpleDateFormat(timePatten, Locale.getDefault());
        simpleDateFormat = new SimpleDateFormat(datePatten, Locale.getDefault());

        currentTime = simpleTimeFormat.format(date);
        currenDate = simpleDateFormat.format(date);


        digitalTimeTextView = findViewById(R.id.digit_time_textview_id);
        dateTextView = findViewById(R.id.date_textviewid);

        buttonAccessTimer = findViewById(R.id.buttonAccessTimerId);
        buttonAccessStopwatch = findViewById(R.id.buttonAccessStopwatchId);
        //  buttonOptions = findViewById(R.id.option_button_id);

        //switchActivityButtons
 /*       buttonAccessTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity = new Intent(ClockActivity.this, TimerActivity.class);
                startActivity(switchActivity);
                Log.d(TAG, "accessing ClockPressed");
            }
        });

 /*       buttonAccessStopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivity = new Intent(ClockActivity.this, StopwatchActivity.class);
                startActivity(switchActivity);
                Log.d(TAG, "accessSing StopwatchPressed");
            }
        });
*/
        //displaying time and date on TextView
        digitalTimeTextView.setText(String.valueOf(currentTime));
        dateTextView.setText(String.valueOf(currenDate));

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

        //updating time every 1 second / 1000 millis
        SimpleDateFormat instantSimpleDateFormat = new SimpleDateFormat(
                timePatten, Locale.getDefault());

        String instantTime = instantSimpleDateFormat.format(new Date());

        if (digitalTimeTextView != null) {
            digitalTimeTextView.setText(instantTime);
            //Log.d(TAG, "........digitalTextView Set TO : "+ instantTime);
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

            intent = new Intent(ClockActivity.this, SettingActivity.class);
            intent.putExtra("INCLUDE_SECONDS", 0);
            startActivity(intent);

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

        Changes access = new Changes();

        timePatten = access.gettPatten();

        Log.d(TAG, ".......................onStart " + timePatten);

        datePatten = access.getdPatten();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);

        timePatten = sharedPreferences.getString("timePatten", timePatten);

        updateTime();
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor simpleEditor = prefs.edit();

        Log.d(TAG, ".......................onStop " + timePatten);

        simpleEditor.putString("currentTime", currentTime);
        simpleEditor.putString("currentDate", currentTime);
        simpleEditor.putString("timePatten", timePatten);
        simpleEditor.putString("datePatten", datePatten);

        simpleEditor.apply();

        updateTime();
    }/*
    @Override
     public void  onRestart(){
        super.onRestart();;

        SharedPreferences sharedPreferences = getSharedPreferences("prefs" , MODE_PRIVATE);

        timePatten = sharedPreferences.getString("timePatten", timePatten);

        updateTime();

    }*/
}