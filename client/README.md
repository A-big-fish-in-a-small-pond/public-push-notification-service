# <div align="center"> Public Push Notification Service - Client </div>

<div align="center">

What is PPNS?

It's a cross-platform messaging solution that lets you reliably send messages for free. Inspired by Firebase Cloud Messaging, it was developed for development implementation and customization. Firebase cloud messaging systems implement servers as firebase servers, but they allow you to implement servers directly.

</div>

<br>

## How to use

1. create your Notification Event Class

```java
// for example
package gujc.serviceexample;

import android.content.Context;

import gujc.serviceexample.event.EventListener;
import gujc.serviceexample.event.NotiEvent;

public class NotiEventTest extends NotiEvent  implements EventListener {

    @Override
    public void onEvent(String event, Context context) {
        super.onEvent(event, context);
    }

    @Override
    public String getIp() {
        return "192.168.137.1"; // your ppns server Ip
    }

    @Override
    public String getPort() {
        return "3000";  // your ppns server Port
    }
}
```

2. input your Activity

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    /// ... 
        Index.onCreateMethod(getApplicationContext(), "gujc.serviceexample.NotiEventTest","1234567890"); // put your Notification Event Class and server auth code
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(getApplicationContext()) == false) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1);
            }
        }
    /// ... 
    }
    
    @Override
    protected void onDestroy() {
    /// ...
        Index.onDestroyMethod(getApplicationContext());
    /// ... 
    }
```

3. start build and app

<br>

## Contact

[Junho Kim](libtv@naver.com) <br>
[JongSun Park](ahrl1994@gmail.com)

<br>

## HomePage

Github © [Page](https://github.com/A-big-fish-in-a-small-pond/)

<br>

## License

this is licensed under [MIT LICENSE](https://github.com/A-big-fish-in-a-small-pond/asterisk-visible-ars).
