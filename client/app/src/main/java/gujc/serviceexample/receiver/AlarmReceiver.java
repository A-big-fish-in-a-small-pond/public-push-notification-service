package gujc.serviceexample.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import gujc.serviceexample.service.MyService;
import gujc.serviceexample.service.RestartService;


public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String CLASS_NAME = extras.getString("class");
        String AUTH_NAME = extras.getString("auth");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent in = new Intent(context, RestartService.class);
            in.putExtra("class", CLASS_NAME);
            in.putExtra("auth", AUTH_NAME);
            context.startForegroundService(in);
        } else {
            Intent in = new Intent(context, MyService.class);
            in.putExtra("class", CLASS_NAME);
            in.putExtra("class", AUTH_NAME);
            context.startService(in);
        }
    }
}
