package gujc.serviceexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import gujc.serviceexample.service.MyService;

public class MainActivity extends AppCompatActivity {
    private Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
        boolean isWhiteListing = false;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            isWhiteListing = pm.isIgnoringBatteryOptimizations(getApplicationContext().getPackageName());
        }
        if (!isWhiteListing) {
            Intent intent = new Intent();
            intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
            startActivity(intent);
        }

        if (MyService.serviceIntent==null) {
            serviceIntent = new Intent(this, MyService.class);
            serviceIntent.putExtra("class","gujc.serviceexample.event.NotiEvent");
            serviceIntent.putExtra("auth","1234567890");
            startService(serviceIntent);
        } else {
            serviceIntent = MyService.serviceIntent;
            Toast.makeText(getApplicationContext(), "already", Toast.LENGTH_LONG).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//마시멜로우 권한 처리

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceIntent!=null) {
            stopService(serviceIntent);
            serviceIntent = null;
        }
    }
}
