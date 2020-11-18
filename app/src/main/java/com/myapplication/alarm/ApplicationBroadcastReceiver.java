package com.myapplication.alarm;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.widget.Toast;
public class ApplicationBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent){
        Log.d(ApplicationBroadcastReceiver.class.getName(), "//////////////////////////      ////////  IM HERE");
        String message = intent.getStringExtra("restart");

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();


    }
}
