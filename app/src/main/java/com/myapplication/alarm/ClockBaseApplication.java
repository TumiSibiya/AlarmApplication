package com.myapplication.alarm;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.os.Build;
import android.util.Log;

public class ClockBaseApplication extends Application {

     private static final String TIMER_CHANNEL_ID = "timerTimeUpChannelId";
     private final String STOPWATCH_CHANNEL_ID = "stopwatchChannlId";
     private final String ALARM_CHANNEL_ID = "alarmChannelId";//String.valueOf(R.string.alarm_notification_channel_id);""

     @Override
    public void onCreate(){
       super.onCreate();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

           //Time up channel
            CharSequence timerChannelName =getString(R.string.timer_notification_content_title);
            String timerDesription = getString(R.string.timer_notification_time_up__text);
            int timerImportance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel timeUpChannel = new NotificationChannel(
                    TIMER_CHANNEL_ID, timerChannelName , timerImportance);
            timeUpChannel.setDescription(timerDesription);

            NotificationManager timerManager = getSystemService(NotificationManager.class);
            timerManager.createNotificationChannel(timeUpChannel);


            //Stopwatwatch running channel id

            CharSequence stopwatchChannelName = getString(R.string.stopwatch_notification_content_title_);
            String stopwatchDescription = getString(R.string.stopwatch_notification_content_text);
            int stopwatchImportance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel stopwatchBackgroundRunningChannel = new NotificationChannel(
                    STOPWATCH_CHANNEL_ID,
                    stopwatchChannelName,
                    stopwatchImportance);
            stopwatchBackgroundRunningChannel.setDescription(stopwatchDescription);

            NotificationManager stopwatchManager = getSystemService(NotificationManager.class);
            stopwatchManager.createNotificationChannel(stopwatchBackgroundRunningChannel);


            //alarm channel registration

            CharSequence alarmChannelName = getString(R.string.alarm_notification_content_title);
            String alarmDescription = getString(R.string.alarm_notification_content_text);
            int alarmImpartance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel alarmNotificationChannel = new NotificationChannel(
                    ALARM_CHANNEL_ID, alarmChannelName, alarmImpartance);
            alarmNotificationChannel.setDescription(alarmDescription);

            NotificationManager alarmNotificationManager = getSystemService(NotificationManager.class);
            Log.d(getApplicationContext().getApplicationContext().getPackageName(), "........................testing......"+ alarmNotificationManager);
            alarmNotificationManager.createNotificationChannel(alarmNotificationChannel);

        }
    }
}
