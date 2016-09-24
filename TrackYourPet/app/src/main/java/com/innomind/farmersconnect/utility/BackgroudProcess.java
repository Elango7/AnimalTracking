package com.innomind.farmersconnect.utility;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

public class BackgroudProcess extends Service  {

    private static final String TAG = "HelloService";

    private boolean isRunning  = false;
    private SQLiteHelper sqLiteHelper;
    public GPSTracker  gps_tr;
    public int available=0;
    public String device,provider;
    public LocationManager locationManager;
    public double updated_lan,updated_lat;

    @Override
    public void onCreate() {
        sqLiteHelper = new SQLiteHelper(this);
         device = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        gps_tr=new GPSTracker(BackgroudProcess.this);

        JSONObject chkobj=sqLiteHelper.getanimalFromLocalDB(device);
        if(chkobj.length()>0){
          available=1;
        }

        Log.i(TAG, "Service onCreate");
        System.out.println("background.....");
        isRunning = true;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "Service onStartCommand");
        Toast.makeText(getApplicationContext(),"Service onStartCommand",Toast.LENGTH_SHORT).show();
        //Creating new thread for my service
        //Always write your long running tasks in a separate thread, to avoid ANR
        new Thread(new Runnable() {
            @Override
            public void run() {


                //Your logic that service will perform will be placed here
                //In this example we are just looping and waits for 1000 milliseconds in each loop.
                    while(true){
                    try {
                        Thread.sleep(6000);

                          System.out.println("thread execution,,,"+available);

                        // System.out.println("Obj creation.."+gps_tr.getLatitude());

                        if(available==1){
                            //CommonUtil.location=gps_tr.getLatitude();

                            System.out.println("Gps truning on...");
                                    LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                                    boolean enabled = service
                                            .isProviderEnabled(LocationManager.GPS_PROVIDER);

                            if(enabled) {
                                System.out.println("Gps truned.. on..."+gps_tr.getLocation().getLongitude()+".."+gps_tr.getLocation().getLatitude()+".."+updated_lan+".."+updated_lat);
                                System.out.println("updated status"+CommonUtil.updated);

                                  //  gps_tr.showSettingsAlert();

if(CommonUtil.updated==true) {
    System.out.println("updated status"+CommonUtil.updated);
    CommonUtil.sendsignal(device, gps_tr.getLocation().getLatitude(), gps_tr.getLocation().getLongitude());
    CommonUtil.common_lat=gps_tr.getLocation().getLatitude();
    CommonUtil.common_lan=gps_tr.getLocation().getLatitude();
    CommonUtil.updated=false;
}

                                    System.out.println("No location change..!");

                            }
                            else{
                      //          gps_tr.showSettingsAlert();
                            }
//Log.i(TAG, "Background process running.. " + gps_tr.getLocation().getLatitude());

                        }
                       // GPSTracker s=new GPSTracker(getApplicationContext());
                        //s.getLatitude();
                        //CommonUtil.location=s.getLatitude();
                        //System.out.println("locatoion..." + s.getLocation().toString());



                    } catch (Exception e) {
                       e.printStackTrace();
                    }

                    if(isRunning){


                       // Toast.makeText(getApplicationContext(),"Service onStartCommand",Toast.LENGTH_SHORT).show();

                          //
                       // Toast.makeText(getApplicationContext(),"Service Running"+s.toString(),Toast.LENGTH_SHORT).show();

                    }
                }

                //Stop service once it finishes its task
             }
        }).start();

        return Service.START_STICKY;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public IBinder onBind(Intent arg0) {
        Toast.makeText(getApplicationContext(),"Service On bind",Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Service onBind");
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(),"Service has been destroyed...",Toast.LENGTH_SHORT).show();
        isRunning = false;

        Log.i(TAG, "Service onDestroy");
    }
}