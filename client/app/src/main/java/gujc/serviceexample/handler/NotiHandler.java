package gujc.serviceexample.handler;

import android.os.Handler;
import android.os.Message;


import gujc.serviceexample.event.EventListener;
import gujc.serviceexample.service.MyService;


public class NotiHandler extends Handler {
    EventListener listener = null;
    MyService myService = null;

    //Instance
    private static NotiHandler instance;

    private NotiHandler() {
    }

    public static synchronized NotiHandler getInstance() {
        if (instance == null) {
            instance = new NotiHandler();
        }
        return instance;
    }

    public void setListener(EventListener listener) {
        this.listener = listener;
    }

    public EventListener getListener() {
        return this.listener;
    }

    public void setMyService(MyService myService) { this.myService = myService;}

    @Override
    public void handleMessage(Message msg) {
        if (listener != null && myService != null) listener.onEvent(msg.obj.toString(), myService.getApplication());
    }
}