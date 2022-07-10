package gujc.serviceexample;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;

import gujc.serviceexample.service.MyService;

public class Index {
    private static Intent serviceIntent;

    public static void onCreateMethod(Context context, String serviceClassName, String auth) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        boolean isWhiteListing = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            isWhiteListing = pm.isIgnoringBatteryOptimizations(context.getPackageName());
        }
        if (!isWhiteListing) {
            Intent intent = new Intent();
            intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        }

        if (MyService.serviceIntent==null) {
            serviceIntent = new Intent(context, MyService.class);
            serviceIntent.putExtra("class",serviceClassName);
            serviceIntent.putExtra("auth",auth);
            context.startService(serviceIntent);
        } else {
            serviceIntent = MyService.serviceIntent;
        }
    }

    public static void onDestroyMethod(Context context) {
        if (serviceIntent!=null) {
            context.stopService(serviceIntent);
            serviceIntent = null;
        }
    }
}
