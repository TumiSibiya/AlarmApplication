package com.myapplications.alarm;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.os.Build;

public class ClockBaseApplication extends Application{

     final String TIMER_CHANNEL_ID = "timerTimeUpChannelIdz";
     final String STOPWATCH_CHANNEL_ID = "stopwatchChannlId";


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
        }
    }
}
