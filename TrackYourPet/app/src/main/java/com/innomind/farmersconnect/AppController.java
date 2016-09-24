package com.innomind.farmersconnect;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.innomind.farmersconnect.utility.CommonUtil;
import com.innomind.farmersconnect.utility.SQLiteHelper;

/**
 * Created by Yokesh on 6/30/2016.
 */
public class AppController extends Application{
    private static final String TAG = AppController.class.getSimpleName();
    private static RequestQueue mRequestQueue;
    private static AppController mInstance;
    private static final CommonUtil commonUtil = new CommonUtil();
    private static SQLiteHelper sqLiteHelper;
    private static Context _context;

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "On Create of APP COntroller");
        mInstance = this;
        _context = this;
        sqLiteHelper = new SQLiteHelper(this);
    }

    public static synchronized AppController getInstance(){
        return  mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(_context);
        }
        return mRequestQueue;
    }

    public <T> void addToRequest(Request<T> req, String tag){
        //setCookie();
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);

    }

    public void cancelPendingRequests(String tag){
        if(mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }

}
