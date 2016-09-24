package com.innomind.farmersconnect.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.innomind.farmersconnect.service.MakeHttpRequestService;
import com.innomind.farmersconnect.utility.CommonUtil;
import com.innomind.farmersconnect.utility.SQLiteHelper;

import org.json.JSONArray;

public class BootInternetChangeReceiver extends BroadcastReceiver {
    private static final String TAG = BootInternetChangeReceiver.class.getSimpleName();

    private SQLiteHelper sqLiteHelper;
    private Context _context;

    private static final CommonUtil commonUtil = new CommonUtil();

    public BootInternetChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        _context = context;
        sqLiteHelper = new SQLiteHelper(_context);
        boolean isNetConnected = commonUtil.isNetworkAvailable(_context);
        if(isNetConnected){
            JSONArray farmerArray = sqLiteHelper.getFarmersFromLocalDB();
            if(farmerArray.length() > 0){
                Intent httpIntent = new Intent(_context, MakeHttpRequestService.class);
                httpIntent.putExtra("action","insert");
                httpIntent.putExtra("farmer_array",farmerArray.toString());
                _context.startService(httpIntent);
            }
        }
    }
}
