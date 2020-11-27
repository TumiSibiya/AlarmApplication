package  com.myapplication.alarm;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;

import android.media.MediaPlayer;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.util.Log;

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
    long start_time_in_millis = (10) * 1000;// (minutes a typical day) * milliseconds
    long time_left_in_millis;

    TextView digitalTimeTextView;
    TextView dateTextView;

    ImageButton buttonAccessTimer;
    ImageButton buttonAccessStopwatch;
    //Button buttonOptions;

    Intent switchActivity;

    ProgressBar clockProgressBar;

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

        clockProgressBar = findViewById(R.id.clock_progressBar_id);
        clockProgressBar.setMax((int)start_time_in_millis);


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
        countDownTimer = new CountDownTimer(SystemClock.elapsedRealtime(), 1000) {


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
        String instantPatten = accessingChanges.gettPatten();

        //updating time every 1 second / 1000 millis
        SimpleDateFormat instantSimpleDateFormat = new SimpleDateFormat(
                instantPatten, Locale.getDefault());

        String instantTime = instantSimpleDateFormat.format(new Date());

        if (digitalTimeTextView != null) {

            digitalTimeTextView.setText(instantTime);

        }


        //Log.d(TAG, ".....................Nothing here");

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

            intent = new Intent(this, SettingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if ("About".equalsIgnoreCase(String.valueOf(items.getTitle()))) {

            Intent sintent = new Intent(ClockActivity.this, About.class);
            startActivity(sintent);

        } else if ("Exit".equals(String.valueOf(items.getTitle()))) {

        }

        return true;
    }
    @Override
    public void onStart(){
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);

        timePatten = sharedPreferences.getString("timePatten", timePatten);

        updateTime();

        //alarm setup
        //
        /*
        Intent activityIntent = new Intent(this, ClockActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingActivityIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Notification alarmNotificationBuilder = new NotificationCompat.Builder(
                this, String.valueOf(R.string.alarm_notification_channel_id))
                .setSmallIcon(R.drawable.ic_baseline_reset_icon)
                .setContentTitle(getString(R.string.alarm_notification_content_title))
                .setContentText((getString(R.string.alarm_notification_content_text)))
                .setContentIntent(pendingActivityIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        Log.d(TAG,".....ALARM ID RETURNED............."+ R.string.alarm_notification_channel_id);

        NotificationManagerCompat alarmNotificationManagetCompat = NotificationManagerCompat.from(this);
        alarmNotificationManagetCompat.notify(987, alarmNotificationBuilder);
*/

        Intent alarmActivityIntent = new Intent(this, AppBaseApplication.class);
        alarmActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent alarmPendingIntent = PendingIntent.getActivity(this, 0, alarmActivityIntent, 0);

        Intent alarmActionIntent = new Intent(this, AppBroadcastReceiver.class);
        alarmActionIntent.setAction("stopRinging");

        PendingIntent alarmActionPendingIntent = PendingIntent.getBroadcast(this, 0, alarmActionIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.alarm_notification_channel_id))
                .setSmallIcon(R.drawable.ic_baseline_reset_icon)
                .setContentTitle(getString(R.string.alarm_notification_content_title))
                .setContentText(getString(R.string.alarm_notification_content_text))
                .setContentIntent(alarmPendingIntent)
                .addAction(R.drawable.ic_baseline_reset_icon, "SHITY", alarmPendingIntent)
                .setOngoing(true)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(77, builder.build());

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