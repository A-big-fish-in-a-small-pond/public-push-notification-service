package gujc.serviceexample.thread;

import android.os.Handler;
import android.util.Log;

import java.net.InetSocketAddress;
import java.net.Socket;

import gujc.serviceexample.service.MyService;

public class TimeOutThread extends Thread {
    boolean isRun = true;
    private Integer ms;
    private MyService myService;
    private static TimeOutThread instance;

    public TimeOutThread(MyService myService, Integer ms) {
        this.myService = myService;
        this.ms = ms;
    }

    public static synchronized TimeOutThread getInstance(MyService myService, Integer ms) {
        if (instance == null) {
            instance = new TimeOutThread(myService, ms);
        }
        return instance;
    }

    public void setMs(Integer ms) {
        this.ms = ms;
    }

    public void setMyService(MyService myService) {
        this.myService = myService;
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }


    public void run() {
        try {
            Thread.sleep(ms);
            myService.myStopService();
        } catch (Exception e) {
        }
    }
}

