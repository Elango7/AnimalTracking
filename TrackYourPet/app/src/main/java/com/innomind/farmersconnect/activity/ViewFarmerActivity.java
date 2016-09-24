package com.innomind.farmersconnect.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.innomind.farmersconnect.R;
import com.innomind.farmersconnect.service.MakeHttpRequestService;
import com.innomind.farmersconnect.utility.CommonUtil;
import com.innomind.farmersconnect.utility.SQLiteHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewFarmerActivity extends AppCompatActivity {
    private static final String TAG = ViewFarmerActivity.class.getSimpleName();

    private static Context _context;
    private static CommonUtil commonUtil = new CommonUtil();
    private CoordinatorLayout layout;
    private SharedPreferences preferences;
    private SharedPreferences.Editor prefEditor;
    private static BroadcastReceiver receiver;
    private SQLiteHelper sqLiteHelper;
    private static  String aadhaarId ;
    private static JSONObject farmerObject;

    private TextView farmerNameTextView;
    private TextView farmerAadhaarTextView;
    private TextView farmerDobTextView;
    private TextView farmerParentsTextView;
    private TextView farmerAddressTextView;
    private TextView farmerDistrictTextView;
    private TextView farmerOccuDetailsTextView;
    private TextView farmerPhoneTextView;
    private TextView farmerAltPhoneTextView;
    private TextView farmerEducationTextView;
    private TextView farmerOccupationTextView;
    private TextView farmerGrossView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_farmer);
        _context = this;
        sqLiteHelper = new SQLiteHelper(_context);
        preferences = getSharedPreferences("INNOMIND_FARMERS",MODE_PRIVATE);
        layout = (CoordinatorLayout) findViewById(R.id.viewFarmerCoordinatorLayout);
        if(getIntent().hasExtra("aadhaarId")){
            aadhaarId = getIntent().getStringExtra("aadhaarId");
            checkLocalDb();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.addFarmerToolBar);
        setSupportActionBar(toolbar);
        if(toolbar != null){
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        farmerNameTextView = (TextView) findViewById(R.id.farmer_name);
        farmerAadhaarTextView = (TextView) findViewById(R.id.farmer_aadhaar);
        farmerDobTextView = (TextView) findViewById(R.id.farmer_dob);
        farmerParentsTextView = (TextView) findViewById(R.id.farmer_parents);
        farmerAddressTextView = (TextView) findViewById(R.id.farmer_address);
        farmerDistrictTextView = (TextView) findViewById(R.id.farmer_district);
        farmerOccuDetailsTextView = (TextView) findViewById(R.id.farmer_occupation_details);
        farmerPhoneTextView = (TextView) findViewById(R.id.farmer_phone);
        farmerAltPhoneTextView = (TextView) findViewById(R.id.farmer_alt_phone);
        farmerEducationTextView = (TextView) findViewById(R.id.farmer_education);
        farmerOccupationTextView = (TextView) findViewById(R.id.farmer_occupation);
        farmerGrossView = (TextView) findViewById(R.id.gross_details);

        prefEditor = preferences.edit();
        prefEditor.putBoolean("refresh_receiver",true);
        prefEditor.putString("active_screen",TAG);
        prefEditor.commit();

        registerRefreshReceiver();

        setTitle(R.string.farmer);
    }

    private void registerRefreshReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refresh");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.hasExtra("action")){
                    String action = intent.getStringExtra("action");
                    if(action.equals("get_farmer")){
                        String farmer_detail_string = intent.getStringExtra("farmer_detail");
                        try {
                            farmerObject = new JSONObject(farmer_detail_string);
                            assignObjectToUi();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else if(action.equals("delete_farmer")){
                        commonUtil.showSnackBar(layout,_context,R.string.deleteSuccess,null);
                        onBackPressed();
                    }else if(action.equals("not_found")){
                        String message = intent.getStringExtra("message");
                        commonUtil.showSnackBar(layout,_context,message,"error");
                        onBackPressed();
                    }
                }
            }
        };
        _context.registerReceiver(receiver, intentFilter);
        prefEditor = preferences.edit();
        prefEditor.putBoolean("refresh_receiver",true);
        prefEditor.putString("active_screen",TAG);
        prefEditor.commit();
    }

    private void assignObjectToUi() {
        try {
            if(farmerObject.has("userName") && farmerObject.getString("userName") != null){
                farmerNameTextView.setText(farmerObject.getString("userName"));
            }else{
                farmerNameTextView.setText("");
            }
            if(farmerObject.has("userAadhaarId") && farmerObject.getString("userAadhaarId") != null){
                farmerAadhaarTextView.setText(farmerObject.getString("userAadhaarId"));
            }else{
                farmerAadhaarTextView.setText("");
            }

            if(farmerObject.has("userDob") && farmerObject.getString("userDob") != null){
                farmerDobTextView.setText(farmerObject.getString("userDob"));
            }else{
                farmerDobTextView.setText("");
            }

            if(farmerObject.has("userGender") && farmerObject.getString("userGender") != null){
                String gender = farmerObject.getString("userGender");
                String parents = "S/o";
                if(gender.equals("F")){
                    parents = "D/o";
                }
                if(farmerObject.has("userFatherName") && farmerObject.getString("userFatherName") != null){
                    parents += farmerObject.getString("userFatherName");
                    if(farmerObject.has("userMotherName") && farmerObject.getString("userMotherName") != null){
                        parents += ", "+farmerObject.getString("userMotherName");
                    }
                    farmerParentsTextView.setText(parents);
                }else if(farmerObject.has("userMotherName") && farmerObject.getString("userMotherName") != null){
                    parents += farmerObject.getString("userMotherName");
                    farmerParentsTextView.setText(parents);
                }else{
                    farmerParentsTextView.setText("");
                }
            }
            if(farmerObject.has("userAddress") && farmerObject.getString("userAddress") != null){
                String address = farmerObject.getString("userAddress");
                String city = "";
                if(farmerObject.has("userCity") && farmerObject.getString("userCity") != null){
                    city = farmerObject.getString("userCity");
                }
                String district ="";
                String districtView = "";
                if(farmerObject.has("userAddress") && farmerObject.getString("userAddress") != null){
                    district = farmerObject.getString("userDistrict");
                    districtView = district;
                }

                if(!city.equals(district)){
                    if(!address.contains(city)){
                        address += ","+city;
                    }
                }
                farmerAddressTextView.setText(address);
                if(farmerObject.has("pinCode") && farmerObject.getString("pinCode") != null){
                    if(districtView != "")
                        districtView += " - "+farmerObject.getString("pinCode");
                    else
                        districtView += farmerObject.getString("pinCode");
                }
                farmerDistrictTextView.setText(districtView);
            }else{
                String city = "";
                if(farmerObject.has("userCity") && farmerObject.getString("userCity") != null){
                    city = farmerObject.getString("userCity");
                }
                String district = "";
                String districtView = "";
                if(farmerObject.has("userAddress") && farmerObject.getString("userAddress") != null){
                    district = farmerObject.getString("userDistrict");
                    districtView = district;
                }

                farmerAddressTextView.setText(city);
                if(farmerObject.has("pinCode") && farmerObject.getString("pinCode") != null){
                    if(districtView != "")
                        districtView += " - "+farmerObject.getString("pinCode");
                    else
                        districtView += farmerObject.getString("pinCode");
                }
                farmerDistrictTextView.setText(districtView);
            }

            if(farmerObject.has("userPhone") && farmerObject.getString("userPhone") != null){
                farmerPhoneTextView.setText(farmerObject.getString("userPhone"));
            }else{
                farmerPhoneTextView.setText("");
            }

            if(farmerObject.has("userAltPhone") && farmerObject.getString("userAltPhone") != null){
                farmerAltPhoneTextView.setText(farmerObject.getString("userAltPhone"));
            }else{
                farmerAltPhoneTextView.setText("");
            }

            if(farmerObject.has("userEducation") && farmerObject.getString("userEducation") != null){
                farmerEducationTextView.setText(farmerObject.getString("userEducation"));
            }else{
                farmerEducationTextView.setText("");
            }

            if(farmerObject.has("userOccupation") && farmerObject.getString("userOccupation") != null){
                farmerOccupationTextView.setText(farmerObject.getString("userOccupation"));
            }else{
                farmerOccupationTextView.setText("");
            }

            if(farmerObject.has("userOccupationDetails") && farmerObject.getString("userOccupationDetails") != null){
                farmerOccuDetailsTextView.setText(farmerObject.getString("userOccupationDetails"));
            }else{
                farmerOccuDetailsTextView.setText("");
            }

            if(farmerObject.has("userGross") && farmerObject.getString("userGross") != null){
                farmerGrossView.setText(farmerObject.getString("userGross"));
            }else{
                farmerGrossView.setText("");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkLocalDb() {
        farmerObject= sqLiteHelper.getFarmersFromLocalDB(aadhaarId);
        if(farmerObject.length() == 0){
            getFromServer();
        }
    }

    private void getFromServer() {
        boolean isNetConnected = commonUtil.isNetworkAvailable(_context);
        if(isNetConnected) {
            Intent httpIntent = new Intent(_context, MakeHttpRequestService.class);
            httpIntent.putExtra("action", "get_farmer");
            httpIntent.putExtra("aadhaarId", aadhaarId);
            _context.startService(httpIntent);
        }else{
            commonUtil.showSnackBar(layout,_context,R.string.pleaseCheckInternetConnection,"error");
        }
    }

    public static void start(Context c,String aadhaarId) {
        Intent intent = new Intent(c, ViewFarmerActivity.class);
        if(aadhaarId != null){
            intent.putExtra("aadhaarId",aadhaarId);
        }
        c.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_farmer_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.deleteFarmer:
                showDeleteDialog();
                break;
            case R.id.editFarmer:
                AddFarmerActivity.start(_context,farmerObject);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.deleteFarmer);
        builder.setMessage(getResources().getString(R.string.deleteQuestion));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                commonUtil.showLoadingSnackBar(layout,_context,R.string.deletingFarmerDetails);
                Intent httpIntent = new Intent(_context, MakeHttpRequestService.class);
                httpIntent.putExtra("action","delete_farmer");
                httpIntent.putExtra("aadhaarId",aadhaarId);
                _context.startService(httpIntent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"On destroy :");
        if(receiver != null){
            unregisterReceiver(receiver);
            prefEditor = preferences.edit();
            String activeScreen = preferences.getString("active_screen",null);
            if(activeScreen != null && activeScreen.equals(TAG) ){
                prefEditor.remove("refresh_receiver");
                prefEditor.commit();
            }
        }
    }
}
