package gujc.serviceexample.service;

        import android.app.Notification;
        import android.app.NotificationChannel;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.app.Service;
        import android.content.Intent;
        import android.graphics.Color;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.IBinder;
        import android.support.annotation.Nullable;
        import android.support.annotation.RequiresApi;
        import android.support.v4.app.NotificationCompat;
        import android.util.Log;

        import gujc.serviceexample.MainActivity;

public class NotiService extends Service {
    private Integer NOTIFICATION_ID = 8;
    private String PRIMARY_CHANNEL_ID = "primary_notification_channel_test";

    public NotiService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 10, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("MyService is running")
                .setContentText("MyService is running")
                .setContentIntent(pendingIntent)
                .setChannelId(PRIMARY_CHANNEL_ID)
                .build();

        startForeground(NOTIFICATION_ID, notification);

        Intent intents = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("https://naver.com");
        intents.setData(uri);
        intents.setPackage("com.android.chrome");
        startActivity(intents);

        stopForeground(true);
        stopSelf();

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Stand up notification", NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("Stand up notification");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
    }

}
