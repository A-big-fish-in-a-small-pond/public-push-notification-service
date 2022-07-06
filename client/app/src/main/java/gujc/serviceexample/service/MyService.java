package gujc.serviceexample.service;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import gujc.serviceexample.event.EventListener;
import gujc.serviceexample.event.NotiEvent;
import gujc.serviceexample.handler.NotiHandler;
import gujc.serviceexample.receiver.AlarmReceiver;
import gujc.serviceexample.thread.ServiceThread;
import gujc.serviceexample.thread.TimeOutThread;
import gujc.serviceexample.utils.HoUtil;


public class MyService extends Service {
    private ServiceThread serviceThread;
    private TimeOutThread timeOutThread;
    public static Intent serviceIntent = null;

    public EventListener listener;
    NotiHandler handler = null;

    public String CLASS_NAME = null;
    public String AUTH_NAME = null;

    public MyService() {}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serviceIntent = intent;

        Bundle extras = intent.getExtras();

        CLASS_NAME = extras.getString("class");
        AUTH_NAME = extras.getString("auth");

        if (CLASS_NAME == null) {
            CLASS_NAME = "gujc.serviceexample.event.NotiEvent";
        }

        if (AUTH_NAME == null) {
            AUTH_NAME = "1234567890";
        }

        try {
            listener = (EventListener) Class.forName(CLASS_NAME).newInstance();
        } catch (Exception e) {
            listener = NotiEvent.getInstance();
            e.printStackTrace();
        }

        handler = NotiHandler.getInstance();
        handler.setListener(listener);
        handler.setMyService(this);

        serviceThread =  new ServiceThread(handler, this, AUTH_NAME);
        serviceThread.start();

        timeOutThread = new TimeOutThread(this, 1000 * 60 * 30);
        timeOutThread.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopThread();
        serviceIntent = null;
        setAlarmTimer();
    }

    public void stopThread() {
        Thread.currentThread().interrupt();

        if (serviceThread != null) {
            serviceThread.stopForever();
            serviceThread.interrupt();
            serviceThread = null;
        }

        if (timeOutThread != null) {
            timeOutThread.stopForever();
            timeOutThread.interrupt();
            timeOutThread = null;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void showToast(final Application application, final String msg) {
        Handler h = new Handler(application.getMainLooper());
        h.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(application, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void setAlarmTimer() {
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        c.add(Calendar.SECOND, 5);
        Intent intent = new Intent(this, AlarmReceiver.class);

        intent.putExtra("class", CLASS_NAME);
        intent.putExtra("auth", AUTH_NAME);

        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), sender);

//        showToast(getApplication(), "Alarm Manager Start : " + CLASS_NAME );
    }

    public void myStopService() {
        stopService(serviceIntent);
    }

}