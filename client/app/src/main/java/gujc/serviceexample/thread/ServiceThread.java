package gujc.serviceexample.thread;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import gujc.serviceexample.service.MyService;

public class ServiceThread extends Thread {
    MyService service;
    Handler handler;
    boolean isRun = true;
    String auth = null;

    public ServiceThread(Handler handler, MyService service, String auth) {
        this.handler = handler;
        this.service = service;
        this.auth = auth;
    }

    public void setService(MyService service) {
        this.service = service;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void stopForever() {
        synchronized (this) {
            this.isRun = false;
        }
    }

    public void run() {
        BufferedReader socketInput = null;
        BufferedWriter socketOutput = null;
        Socket socket  = new Socket();
        String ACK_STR = "{\"auth\": \"" + this.auth + "\", \"result\": \"success\"}\n";
        String str = "";
        int i = 0;

        try {
            InetSocketAddress socketAddress = new InetSocketAddress("192.168.163.205", 3000);
            socket.connect(socketAddress);

            socketInput =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
            socketOutput = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            Thread.sleep(1000);
            socketOutput.write(ACK_STR);
            socketOutput.flush();

            while(socket.isClosed() == false && (str = socketInput.readLine()) != null ) {
                Message message = handler.obtainMessage();
                i++;

                message.what = 0;
                message.arg1 = i;
                message.obj = str;

                handler.sendMessage(message);
//                handler.sendEmptyMessage(0);

                socketOutput.write(ACK_STR);
                socketOutput.flush();
            }
            run();

        } catch (Exception e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            run();
        }
    }
}

