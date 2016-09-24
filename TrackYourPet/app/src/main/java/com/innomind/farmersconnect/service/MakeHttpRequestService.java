package com.innomind.farmersconnect.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.innomind.farmersconnect.AppController;
import com.innomind.farmersconnect.utility.CommonUtil;
import com.innomind.farmersconnect.utility.MakeHttpRequest;
import com.innomind.farmersconnect.utility.SQLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MakeHttpRequestService extends IntentService {
    private static final String TAG = MakeHttpRequestService.class.getSimpleName();

    private static SQLiteHelper sqLiteHelper;
    private static Context _context;

    private static  final MakeHttpRequest makeHttpRequest = new MakeHttpRequest();
    private static final CommonUtil utilFunctions = new CommonUtil();
    private static JSONArray farmersArray;
    private static SharedPreferences preferences;

    public MakeHttpRequestService() {
        super("MakeHttpRequestService");
    }

    @Override
    public  void onCreate(){
        super.onCreate();
        _context = this;
        sqLiteHelper = new SQLiteHelper(_context);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        preferences = getSharedPreferences("INNOMIND_FARMERS",MODE_PRIVATE);
        String action = intent.getStringExtra("action");
        switch(action){
            case "insert":
                String farmersArrayString = intent.getStringExtra("farmer_array");
                Log.d(TAG,"Farmer Array :"+farmersArrayString);
                try {
                    farmersArray = new JSONArray(farmersArrayString);
                    Log.d(TAG,"Farmer Array :"+farmersArray);
                    makeHttpRequest.HttpPostJSONArrayRequest(TAG,"/",farmersArray,this.afterInsertingFarmer(),this.errorLister());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "get_farmer_details":
                Log.d(TAG,"in get farmer details from serve");
                int start = intent.getIntExtra("start",0);
                int end = intent.getIntExtra("end",10);
                String url = "/?start="+start+"&end="+end;
                if(intent.hasExtra("search")){
                    url += "&search="+intent.getStringExtra("search");
                }
                makeHttpRequest.HttpGetJSONArrayRequest(TAG,url,this.afterGettingFarmerDetails(),this.errorLister());
                break;
            case "get_farmer":
                String aadhaarId = intent.getStringExtra("aadhaarId");
                makeHttpRequest.HttpJSONObjectRequest(TAG, Request.Method.GET,"/"+aadhaarId,null,this.afterGettingFarmerDetail(),this.errorLister());
                break;
            case "delete_farmer":
                aadhaarId = intent.getStringExtra("aadhaarId");
                makeHttpRequest.HttpJSONObjectRequest(TAG, Request.Method.DELETE,"/"+aadhaarId,null,this.afterDeletingFarmerDetail(),this.errorLister());
                break;
            case "update":
                farmersArrayString = intent.getStringExtra("farmer_array");
                try{
                    JSONObject farmerObject = new JSONObject(farmersArrayString);
                    Log.d(TAG,"Farmer Object :"+farmerObject);
                    makeHttpRequest.HttpJSONObjectRequest(TAG, Request.Method.PUT,"/",farmerObject,this.afterUpdatingFarmerDetails(),this.errorLister());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "insertanimal":
                System.out.println("inserting animal========");

                farmersArrayString = intent.getStringExtra("animal_array");
                try{
                    JSONObject farmerObject = new JSONObject(farmersArrayString);
                    Log.d(TAG,"Farmer Object :"+farmerObject);
                    makeHttpRequest.HttpJSONObjectRequest(TAG, Request.Method.POST, "/", farmerObject, this.afterUpdatingFarmerDetails(), this.errorLister());
                 System.out.print("here.......");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        return Service.START_REDELIVER_INTENT;
    }

    private Response.Listener<JSONObject> afterUpdatingFarmerDetails() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent intent = new Intent();
                intent.setAction("refresh");
                intent.putExtra("action","update_farmer");
                sendBroadcast(intent);
            }
        };
    }

    private Response.Listener<JSONObject> afterDeletingFarmerDetail() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Intent intent = new Intent();
                intent.setAction("refresh");
                intent.putExtra("action","delete_farmer");
                sendBroadcast(intent);
            }
        };
    }

    private Response.Listener<JSONObject> afterGettingFarmerDetail() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG,"Response :"+response);
                Intent intent = new Intent();
                intent.setAction("refresh");
                intent.putExtra("action","get_farmer");
                intent.putExtra("farmer_detail",response.toString());
                sendBroadcast(intent);
            }
        };
    }


    private Response.Listener<JSONArray> afterGettingFarmerDetails() {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Intent intent = new Intent();
                intent.setAction("refresh");
                intent.putExtra("action","newfarmer");
                intent.putExtra("farmer_details",response.toString());
                sendBroadcast(intent);
            }
        };
    }

    private Response.ErrorListener errorLister() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null){
                    try{
                        int statusCode = error.networkResponse.statusCode;
                        String errorMessage = new String(error.networkResponse.data,"UTF-8");
                        switch (statusCode){
                            case 409:
                                sqLiteHelper.clearLocalDB(farmersArray);
                                Boolean isReceiverRegistered = preferences.getBoolean("refresh_receiver",false);
                                Log.d(TAG,"is received registered :"+isReceiverRegistered);
                                if(isReceiverRegistered){
                                    Intent intent = new Intent();
                                    intent.setAction("refresh");
                                    intent.putExtra("action","conflict");
                                    intent.putExtra("message",errorMessage);
                                    sendBroadcast(intent);
                                }
                                break;
                            case 404:
                                Intent intent = new Intent();
                                intent.setAction("refresh");
                                intent.putExtra("action","not_found");
                                intent.putExtra("message",errorMessage);
                                sendBroadcast(intent);
                        }
                    }catch(Exception e){

                    }
                }
                stopSelf();
            }
        };
    }

    private Response.Listener<JSONArray> afterInsertingFarmer() {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    String responseString = response.getString(0);
                    if(responseString.equals("Success")){
                        sqLiteHelper.clearLocalDB(farmersArray);
                    }
                    Boolean isReceiverRegistered = preferences.getBoolean("refresh_receiver",false);
                    if(isReceiverRegistered){
                        Intent intent = new Intent();
                        intent.setAction("refresh");
                        intent.putExtra("action","pushed");
                        sendBroadcast(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                stopSelf();
            }
        };
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onDestroy(){
        // AppController.getInstance().sendNotification("Location Service","service Destroyed");
        super.onDestroy();
        AppController.getInstance().cancelPendingRequests(TAG);
    }
}
