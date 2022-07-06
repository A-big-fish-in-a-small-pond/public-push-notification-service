package gujc.serviceexample.event;

import android.content.Context;

public interface EventListener {
    public void onEvent(String event, Context context);
    public String getIp();
    public String getPort();
}