package com.innomind.farmersconnect.utility;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by @Elan on 7/17/2016.
 */
public class BackgroundService extends Service {

    private boolean isRunning;
    private Context context;
    Thread backgroundThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;

        this.backgroundThread = new Thread(myTask);
    }

    private Runnable myTask = new Runnable() {
        public void run() {
            // Do something here
            Toast.makeText(getApplicationContext(),"Works fine for mee...",Toast.LENGTH_LONG).show();
            Log.i("Service:", "Service works fine...");
            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(),"Inside onstart command...",Toast.LENGTH_LONG).show();
        Log.i("Service:","Service has worked..");
        if (!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }
}