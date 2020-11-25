package com.myapplication.alarm;
import com.myapplication.alarm.TimerActivity;
import com.myapplication.alarm.StopwatchActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.media.Ringtone;
import android.media.RingtoneManager;


import android.os.SystemClock;
import android.util.Log;

public class ApplicationBroadcastReceiver extends BroadcastReceiver{

    Intent intent;
    @Override
    public void onReceive(Context context, Intent intent) {
        String name = intent.getAction();
            Log.d(StopwatchActivity.class.getName(), "..............."+ name);

            Intent go = new Intent(context, StopwatchActivity.class);

            if("alert".equalsIgnoreCase(intent.getAction())){

                Ringtone ringtone = RingtoneManager.getRingtone(
                        context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));

                ringtone.play();
                SystemClock.sleep(200);

            }
    }

}
