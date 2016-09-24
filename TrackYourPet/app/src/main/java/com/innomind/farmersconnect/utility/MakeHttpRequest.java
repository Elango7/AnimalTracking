package com.innomind.farmersconnect.utility;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.innomind.farmersconnect.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Yokesh on 6/30/2016.
 */
public class MakeHttpRequest {
    private static final String TAG = MakeHttpRequest.class.getSimpleName();

    private static final String API_URL = "http://52.90.87.1:8080/farmersconnect/api/farmer";
    private static final String API_URL_animal = "http://192.168.0.102/restexample/tracking/insertAnimalInfo/";

    public void HttpPostJSONArrayRequest(String inTAG, String apiUrl, JSONArray inParams, Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener){
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.POST,API_URL_animal+apiUrl,inParams,successListener,errorListener);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(3000,2,2));
        AppController.getInstance().addToRequest(postRequest,inTAG);
    }

    public void HttpGetJSONArrayRequest(String inTAG, String apiUrl, Response.Listener<JSONArray> successListener, Response.ErrorListener errorListener){
        JsonArrayRequest getRequest = new JsonArrayRequest(API_URL_animal+apiUrl,successListener,errorListener);
        getRequest.setRetryPolicy(new DefaultRetryPolicy(3000,2,2));
        AppController.getInstance().addToRequest(getRequest,inTAG);
    }

    public void HttpJSONObjectRequest(String inTAG,int method, String apiUrl,JSONObject params, Response.Listener<JSONObject> successListner, Response.ErrorListener errorListener) {
        System.out.print("here,,,,,");
        JsonObjectRequest getRequest = new JsonObjectRequest(method,API_URL_animal+apiUrl,params,successListner,errorListener);

        getRequest.setRetryPolicy(new DefaultRetryPolicy(3000,2,2));
        AppController.getInstance().addToRequest(getRequest,inTAG);
    }
}
