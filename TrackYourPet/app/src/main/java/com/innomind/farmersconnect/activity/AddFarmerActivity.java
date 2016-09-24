package com.innomind.farmersconnect.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.innomind.farmersconnect.R;
import com.innomind.farmersconnect.fragment.CommunicationFragment;
import com.innomind.farmersconnect.fragment.PersonalFragment;
import com.innomind.farmersconnect.fragment.ProfileFragment;
import com.innomind.farmersconnect.model.FarmerModel;
import com.innomind.farmersconnect.service.MakeHttpRequestService;
import com.innomind.farmersconnect.utility.CommonUtil;
import com.innomind.farmersconnect.utility.SQLiteHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Date;
import java.util.Iterator;

public class AddFarmerActivity extends AppCompatActivity implements PersonalFragment.OnFragmentInteractionListener,
        CommunicationFragment.OnFragmentInteractionListener,ProfileFragment.OnFragmentInteractionListener{
    private static final String TAG = AddFarmerActivity.class.getSimpleName();

    private Context _context;
    private String currentFragment;
    private CoordinatorLayout layout;
    private SharedPreferences preferences;
    private SharedPreferences.Editor prefEditor;
    private static BroadcastReceiver receiver;

    private boolean fromCamera = false;
    private static CommonUtil commonUtil = new CommonUtil();
    private static FarmerModel farmerModel = new FarmerModel();
    private static SQLiteHelper sqLiteHelper;
    private static String action = "";

    private static final String[] possibleAadharValues = new String[]{"uid","name","gender","gname","co","house","street","subdist","vtc","po","dist","state","pc","dob"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farmer);
        _context = this;
        sqLiteHelper = new SQLiteHelper(_context);
        farmerModel = new FarmerModel();
        if(getIntent().hasExtra("action")){
            action = getIntent().getStringExtra("action");
        }else{
            action = "";
        }
        if(getIntent().hasExtra("farmer_object")){
            String farmerObjectString = getIntent().getStringExtra("farmer_object");
            try {
                JSONObject farmer_object = new JSONObject(farmerObjectString);
                farmerModel.setFarmer_object(farmer_object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(action.equals("edit")){
                setTitle(R.string.editFarmer);
            }
        }
        preferences = getSharedPreferences("INNOMIND_FARMERS",MODE_PRIVATE);
        layout = (CoordinatorLayout) findViewById(R.id.addFarmerCoordinatorLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.addFarmerToolBar);
        setSupportActionBar(toolbar);
        Log.d(TAG,"Action Value :"+action);
        if(action.equals("")){
            setTitle(R.string.addFarmer);
        }
        if(toolbar != null){
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        showFragment("personal");
        registerLocalDbPushReceiver();

    }

    private void registerLocalDbPushReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refresh");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.hasExtra("action")){
                    String action = intent.getStringExtra("action");
                    if(action.equals("pushed")){
                        commonUtil.showSnackBar(layout,_context,R.string.addSuccess,null);
                    }else if(action.equals("conflict")){
                        String message = intent.getStringExtra("message");
                        //commonUtil.showSnackBar(layout,_context,message,"error");
                    }else if(action.equals("update_farmer")){
                        commonUtil.showSnackBar(layout,_context,R.string.saveSuccess,null);
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

    @Override
    public void onBackPressed(){
        takeUserBack();
    }

    private void takeUserBack() {
        if(getFragmentManager().getBackStackEntryCount() > 0){
            Log.d(TAG, "In If");
            if(currentFragment.equals("personal")){
                NavUtils.navigateUpFromSameTask(this);
                //super.onBackPressed();
            }else{
                if(currentFragment.equals("communication")){
                    currentFragment = "personal";
                }else if(currentFragment.equals("profile")){
                    currentFragment = "communication";
                }
                //getFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_right,R.anim.exit_to_left);
                getFragmentManager().popBackStack();
            }
        }else{
            Log.d(TAG,"In else If");
           // NavUtils.navigateUpFromSameTask(this);
            super.onBackPressed();
        }
    }

    private void showFragment(String fragmentState) {
        boolean replace = false;
        if((currentFragment != null && currentFragment.equals(fragmentState)) || fromCamera){
            replace = true;
            fromCamera = false;
        }
        currentFragment = fragmentState;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        Bundle args = new Bundle();
        JSONObject passingArguments = farmerModel.getFarmer_object();
        if(passingArguments != null && passingArguments.length() > 0) {
            args.putString("farmer_object",passingArguments.toString());
        }
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right);
        switch(fragmentState){
            case "personal":
                PersonalFragment personalFragment = new PersonalFragment();
                personalFragment.setArguments(args);
                Log.d(TAG,"replace :"+replace);
                if(!replace){
                    fragmentTransaction.add(R.id.addFarmerFrameLayout, personalFragment);
                }else{
                    fragmentTransaction.replace(R.id.addFarmerFrameLayout,personalFragment);
                    fragmentTransaction.setCustomAnimations(0,0,0,0);
                }
                break;
            case "communication":
                CommunicationFragment communicationFragment = new CommunicationFragment();
                communicationFragment.setArguments(args);
                fragmentTransaction.replace(R.id.addFarmerFrameLayout,communicationFragment);
                fragmentTransaction.addToBackStack(null);
                break;
            case "profile":
                ProfileFragment profileFragment = new ProfileFragment();
                profileFragment.setArguments(args);
                fragmentTransaction.replace(R.id.addFarmerFrameLayout,profileFragment);
                fragmentTransaction.addToBackStack(null);
                break;
        }
        fragmentTransaction.commit();
    }

    public static void start(Context c) {
        c.startActivity(new Intent(c, AddFarmerActivity.class));
    }

    public static void start(Context _context, JSONObject farmerObject) {
        Intent intent = new Intent(_context,AddFarmerActivity.class);
        intent.putExtra("farmer_object",farmerObject.toString());
        intent.putExtra("action","edit");
        _context.startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void goNext(JSONObject inputObject) {
        loopFarmerValues(inputObject);
        switch(currentFragment){
            case "personal":
                showFragment("communication");
                break;
            case "communication":
                showFragment("profile");
                break;
        }
    }

    @Override
    public void goPrevious(JSONObject inputObject) {
        loopFarmerValues(inputObject);
        switch(currentFragment){
            case "communication":
                onBackPressed();
                break;
            case "profile":
                onBackPressed();
                break;
        }
    }

    private void loopFarmerValues(JSONObject inputObject) {
        Iterator<String> iterator = inputObject.keys();
        while(iterator.hasNext()){
            String key = iterator.next();
            try {
                String value = inputObject.getString(key);
                assignFarmerModelValues(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveDetails(JSONObject inputJsonObject) {
        loopFarmerValues(inputJsonObject);
        JSONObject farmer_object = farmerModel.getFarmer_object();
        boolean activeNetwork = commonUtil.isNetworkAvailable(_context);
        if(action.equals("")){
            sqLiteHelper.insertFarmersIntoLocalDB(farmer_object);
        }
        if(activeNetwork){
            if(action.equals("")){
                commonUtil.showLoadingSnackBar(layout,_context,R.string.addFarmerDetails);
                JSONArray farmers_details = sqLiteHelper.getFarmersFromLocalDB();
                uploadLocalFarmerListData(farmers_details);
            }else{
                commonUtil.showLoadingSnackBar(layout,_context,R.string.savingFarmerDetails);
                boolean isNetConnected = commonUtil.isNetworkAvailable(_context);
                if(isNetConnected){
                    if(farmer_object.length() > 0){
                        try {
                            Date parsedDobDate = commonUtil.parseDobDate(farmer_object.getString("userDob"));
                            farmer_object.put("userDob",parsedDobDate);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent httpIntent = new Intent(_context, MakeHttpRequestService.class);
                        httpIntent.putExtra("action","update");
                        httpIntent.putExtra("farmer_array",farmer_object.toString());
                        _context.startService(httpIntent);
                    }
                }else{
                    commonUtil.showSnackBar(layout,_context,R.string.pleaseCheckInternetConnection,"error");
                }
            }
        }else{
            if(action.equals("")){
                commonUtil.showSnackBar(layout,_context,R.string.savedlocal,"error");
            }else if(action.equals("edit")){
                commonUtil.showSnackBar(layout,_context,R.string.pleaseCheckInternetConnection,"error");
            }
        }
        if(action.equals("")){
            farmerModel = new FarmerModel();
        }
        fromCamera = true;
        showFragment("personal");
    }

    private void uploadLocalFarmerListData(JSONArray farmerArray) {
        boolean isNetConnected = commonUtil.isNetworkAvailable(_context);
        if(isNetConnected){
            if(farmerArray.length() > 0){
                Intent httpIntent = new Intent(_context, MakeHttpRequestService.class);
                httpIntent.putExtra("action","insert");
                httpIntent.putExtra("farmer_array",farmerArray.toString());
                _context.startService(httpIntent);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_farmer,menu);
        MenuItem item = menu.findItem(R.id.scanbarCode);
        if(action.equals("edit")){
            item.setVisible(false);
        }else if(action.equals("")){
            item.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.scanbarCode:
                new IntentIntegrator(this).initiateScan();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                commonUtil.showSnackBar(layout,_context,R.string.cancelScan,"warning");
            } else {
                String aadhaarContent = result.getContents();
                if(aadhaarContent != null){
                    Document aadhaarXmlContent = commonUtil.loadXmlFromString(aadhaarContent);
                    if(aadhaarXmlContent != null){
                        parseXmlContent(aadhaarXmlContent);
                    }else{
                        commonUtil.showSnackBar(layout,_context,R.string.errorScan,"error");
                    }
                }else{
                    commonUtil.showSnackBar(layout,_context,R.string.errorScan,"error");
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void parseXmlContent(Document aadhaarXmlContent) {
        aadhaarXmlContent.getDocumentElement().normalize();
        Log.d(TAG,"Aadhaar Xml Content :"+aadhaarXmlContent);
        Element root = aadhaarXmlContent.getDocumentElement();
        int possibleAttributeLength = possibleAadharValues.length;
        farmerModel = new FarmerModel();
        for(int i=0;i<possibleAttributeLength;i++){
            String nodeName = possibleAadharValues[i];
            String attributeValue = root.getAttribute(nodeName);
            assignFarmerModelValues(nodeName,attributeValue);
        }
        fromCamera = true;
        showFragment("personal");
    }

    private void assignFarmerModelValues(String nodeName, String attributeValue) {
        Log.d(TAG,"Node Name :"+nodeName+",Node Value :"+attributeValue);
        switch(nodeName){
            case "uid":
                farmerModel.setUser_aadhaar_id(Long.parseLong(attributeValue));
                break;
            case "name":
                farmerModel.setUser_name(attributeValue);
                break;
            case "gender":
                farmerModel.setUser_gender(attributeValue);
                break;
            case "gname":
                farmerModel.setUser_father_name(attributeValue);
                String userName = farmerModel.getUser_name();
                userName = userName.replace(attributeValue,"");
                userName = userName.trim();
                farmerModel.setUser_name(userName);
                break;
            case "mname":
                farmerModel.setUser_mother_name(attributeValue);
                break;
            case "co":
                String[] spliValueArray = attributeValue.split(":");
                if(spliValueArray.length == 2){
                    String fatherName = spliValueArray[1].trim();
                    farmerModel.setUser_father_name(fatherName);
                    String user_name = farmerModel.getUser_name();
                    user_name = user_name.replace(fatherName,"");
                    user_name = user_name.trim();
                    farmerModel.setUser_name(user_name);
                }
                break;
            case "house":
                farmerModel.setUser_address(attributeValue);
                break;
            case "street":
                farmerModel.appendUser_address(attributeValue);
                break;
            case "subdist":
                farmerModel.setUser_city(attributeValue);
                break;
            case "vtc":
                String city = farmerModel.getUser_city();
                if(!city.equals(attributeValue)){
                    farmerModel.appendUser_address(attributeValue);
                }
                break;
            case "po":
                String user_city = farmerModel.getUser_city();
                boolean can_add = true;
                if(user_city.equals(attributeValue)){
                    can_add = false;
                }
                String user_address = farmerModel.getUser_address();
                if(user_address.contains(attributeValue)){
                    can_add = false;
                }
                if(can_add){
                    farmerModel.appendUser_address(attributeValue);
                }
                break;
            case "dist":
                farmerModel.setUser_district(attributeValue);
                break;
            case "state":
                farmerModel.setUser_state(attributeValue);
                break;
            case "pc":
                farmerModel.setPin_code(Long.parseLong(attributeValue));
                break;
            case "dob":
                farmerModel.setUser_dob(attributeValue);
                break;
            case "phone":
                farmerModel.setUser_phone_no(Long.parseLong(attributeValue));
                break;
            case "aphone":
                farmerModel.setUser_alt_phone_no(Long.parseLong(attributeValue));
                break;
            case "fcount":
                farmerModel.setUser_family_count(attributeValue);
                break;
            case "mstatus":
                farmerModel.setUser_marital_status(attributeValue);
                break;
            case "lm":
                farmerModel.setUser_landmark(attributeValue);
                break;
            case "education":
                farmerModel.setUser_education(attributeValue);
                break;
            case "occupation":
                farmerModel.setUser_occupation(attributeValue);
                break;
            case "occupation_details":
                farmerModel.setUser_occupation_details(attributeValue);
                break;
            case "gross":
                farmerModel.setUser_gross(attributeValue);
                break;
        }
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

