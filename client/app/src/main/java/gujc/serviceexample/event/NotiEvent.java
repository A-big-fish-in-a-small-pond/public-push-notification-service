package gujc.serviceexample.event;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;


import gujc.serviceexample.MainActivity;
import gujc.serviceexample.R;
import gujc.serviceexample.service.NotiService;
import gujc.serviceexample.service.RestartService;

public class NotiEvent implements EventListener {
    //Instance
    private static NotiEvent instance;
    private String PRIMARY_CHANNEL_ID = "fcm_default_channel";
    private static String channelName = "ppsm";
    private Integer NOTIFICATION_ID = 9;

    public NotiEvent() {}

    public static synchronized NotiEvent getInstance() {
        if (instance == null) {
            instance = new NotiEvent();
        }
        return instance;
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onEvent(String message, Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1 , intent, PendingIntent.FLAG_ONE_SHOT);

        String str;

        if (message != null) {
            str = message;
        } else {
            str = "Test Message";
        }

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)//drawable.splash)
                        .setContentTitle("PPNS Service")
                        .setContentText(str)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID , notificationBuilder.build());



        Intent intents = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("https://naver.com");
        intents.setData(uri);
        context.startActivity(intents);

//        Intent in = new Intent(context, NotiService.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context.startForegroundService(in);
//        } else {
//            context.startService(in);
//        }
    }


    @Override
    public String getIp() {
        return "192.168.163.205";
    }

    @Override
    public String getPort() {
        return "3000";
    }
}
