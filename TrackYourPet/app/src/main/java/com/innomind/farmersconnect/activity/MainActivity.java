package com.innomind.farmersconnect.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.innomind.farmersconnect.AppController;
import com.innomind.farmersconnect.R;
import com.innomind.farmersconnect.adapter.FarmerAdapter;
import com.innomind.farmersconnect.service.MakeHttpRequestService;
import com.innomind.farmersconnect.utility.BackgroudProcess;
import com.innomind.farmersconnect.utility.CommonUtil;
import com.innomind.farmersconnect.utility.SQLiteHelper;
import com.skyfishjy.library.RippleBackground;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Context _context;
    private SQLiteHelper sqLiteHelper;
    private CoordinatorLayout layout;
    private static BroadcastReceiver receiver;
   private Button saveanimal;
    private RecyclerView farmerRecyclerView;
    private RecyclerView.Adapter farmerRecyclerAdapter;
    private RecyclerView farmerSearchRecyclerView;
    private RecyclerView.Adapter farmerSearchRecyclerAdapter;

    private  RecyclerView.LayoutManager farmerRecyclerLayoutManager;
    private  RecyclerView.LayoutManager farmerSearchRecyclerLayoutManager;

    private ProgressBar farmerProgressBar;
    private TextView noFarmerTextView;

    private SharedPreferences preferences;
    private SharedPreferences.Editor prefEditor;
    private LinearLayout mylayout;
    private static JSONArray farmers_details = new JSONArray();
    private static JSONArray farmer_search_details = new JSONArray();
    private TextInputEditText deviceId;
    private TextInputEditText input_address;
    private static TextView textview1;
    public static JSONObject animal_data;
    private AppCompatSpinner education_spinner;
    private ArrayAdapter<CharSequence> educationAdapter;
    private static CommonUtil commonUtil = new CommonUtil();
    private static int start= 0,searchStart = 0;
    public String educationSpinnerText;
    private static int end = 10,searchEnd = 10;
    private static boolean isSearch = false;
    private static boolean isQuerySubmitted = false;
    private static String searchString = null;

  private AppCompatTextView heading;
    private ImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "On Create of Main Activity");
        super.onCreate(savedInstanceState);
        _context = this;
        //prev=(Button)findViewById(R.id.previousButton);

        Intent intent_back = new Intent(this, BackgroudProcess.class);
        startService(intent_back);

        sqLiteHelper = new SQLiteHelper(this);



        preferences = getSharedPreferences("INNOMIND_FARMERS",MODE_PRIVATE);
        setContentView(R.layout.fragment_initial);

        education_spinner=(AppCompatSpinner)findViewById(R.id.education_spinner);
mylayout=(LinearLayout)findViewById(R.id.mylayout);
heading=(AppCompatTextView)findViewById(R.id.headingText);
icon=(ImageView)findViewById(R.id.farmer_icon);
        input_address=(TextInputEditText)findViewById(R.id.input_pincode);

       // WebView webView = (WebView) findViewById(R.id.webview);
        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        ImageView imageView=(ImageView)findViewById(R.id.centerImage);

        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(890);
        final String animalId="ANM_TRACK"+randomInt;
        final String device = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //sqLiteHelper.clearLocalAnimalTrackTable();
        JSONObject chkobj=sqLiteHelper.getanimalFromLocalDB(device);
        saveanimal=(Button)findViewById(R.id.button);
        if(chkobj.length()>0){

        //    deviceId.setVisibility(View.INVISIBLE);

            input_address.setVisibility(View.INVISIBLE);
            education_spinner.setVisibility(View.INVISIBLE);
            saveanimal.setVisibility(View.INVISIBLE);
          icon.setVisibility(View.INVISIBLE);
            heading.setText("Enrolment Already Done");
//webView.setVisibility(View.VISIBLE);
            rippleBackground.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);

            //icon.setVisibility(View.INVISIBLE);
            input_address.setVisibility(View.INVISIBLE);
            input_address.setText("");

String jsurl="file:///android_asset/test1.html?lan="+commonUtil.common_lan+"&lat="+commonUtil.common_lat;

System.out.println("URL...." + jsurl);
             //webView.addJavascriptInterface(new WebAppInterface(this), commonUtil.common_lan + "" + commonUtil.common_lat);
           // webView.loadUrl("javascript:window.MyHandler.setResult("+commonUtil.common_lan + "" + commonUtil.common_lat+")");
            //final int[] isClicked = {0};

            assert imageView != null;
            rippleBackground.startRippleAnimation();
            Toast.makeText(getApplicationContext(), "Tracking Starts...", Toast.LENGTH_LONG).show();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



               //         isClicked[0] = 1;


                }
            });


//            Snackbar snackbar = Snackbar
//                    .make(mylayout, "Message is deleted", Snackbar.LENGTH_LONG)
//                    .setAction("Close", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Snackbar snackbar1 = Snackbar.make(mylayout, "Message is restored!", Snackbar.LENGTH_SHORT);
//                            snackbar1.show();
//                        }
//                    });
//
//            snackbar.show();
 commonUtil.showSnackBar_linear(mylayout, _context, "This Device Already Registered", "ss");
            boolean find_activeNetwork = commonUtil.isNetworkAvailable(_context);
            LocationManager gps_service = (LocationManager) getSystemService(LOCATION_SERVICE);

            boolean find_gps_enabled = gps_service
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

     if(!find_activeNetwork){
         commonUtil.showSnackBar_linear(mylayout, _context, "Please Check Internet Connection", "error");
         rippleBackground.stopRippleAnimation();

     }
        if(!find_gps_enabled){
            commonUtil.showSnackBar_linear(mylayout, _context, "Please Turn on Gps", "error");
            rippleBackground.stopRippleAnimation();
        }

//Toast.makeText(getApplicationContext(),"Alreadyhave",Toast.LENGTH_SHORT).show();
        }
        else{

            //deviceId=(TextInputEditText)findViewById(R.id.input_address);
            animal_data=new JSONObject();

            //deviceId.setText(device);

            rippleBackground.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            educationAdapter = ArrayAdapter.createFromResource(this, R.array.animal_name, android.R.layout.simple_spinner_item);
            educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            education_spinner.setAdapter(educationAdapter);
            educationSpinnerText = String.valueOf(education_spinner.getSelectedItemPosition());
            education_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        int selectd=parent.getSelectedItemPosition();
                    if(selectd==1)
                    {
                        icon.setImageResource(R.drawable.horse);
                        icon.setVisibility(View.VISIBLE);
                    }
                    if(selectd==2)
                    {
                        icon.setImageResource(R.drawable.bg_goat);
                        icon.setVisibility(View.VISIBLE);
                    }
                    if(selectd==3)
                    {
                        icon.setImageResource(R.drawable.horse_walkinganimated);
                        icon.setVisibility(View.VISIBLE);
                    }
                    if(selectd==4)
                    {
                        icon.setImageResource(R.drawable.bg_goat);
                        icon.setVisibility(View.VISIBLE);
                    }



                    if(selectd==5)
                    {
                        icon.setImageResource(R.drawable.horse_walkinganimated);
                        icon.setVisibility(View.VISIBLE);
                    }

                    if(selectd==0)
                    {
                        icon.setImageResource(R.drawable.horse);
                        icon.setVisibility(View.INVISIBLE);
                    }

                         System.out.println("selected..."+selectd);


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

//
//            education_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String selected_ed = parent.getItemAtPosition(position).toString();
//                    int selectedpos_ed = parent.getSelectedItemPosition();
//                    educationSpinnerText = String.valueOf(selectedpos_ed);
//
//                }
//            });
            if(educationSpinnerText.equals("0"))
            {
                icon.setImageResource(R.drawable.bg_goat);
            }

            if(educationSpinnerText.equals("1"))
            {
                icon.setImageResource(R.drawable.horse);
            }


            try {
                animal_data.put("devid",device);
                animal_data.put("animalname",input_address.getText());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



saveanimal.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        boolean activeNetwork = commonUtil.isNetworkAvailable(_context);

try {
    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    //Toast.makeText(getApplicationContext(),"Version"+currentapiVersion,Toast.LENGTH_SHORT).show();
    if(currentapiVersion>22) {
        final int REQUEST_CODE_ASK_PERMISSIONS = 123;
        /*int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        int hasWriteContactsPermission_loc = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED || hasWriteContactsPermission_loc != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);
            return;

        }*/
    }
}
catch(Exception ex){
    System.out.println("exception...verson"+ex.getMessage());
}
        if(activeNetwork){

                boolean isNetConnected = commonUtil.isNetworkAvailable(_context);
                if(isNetConnected){

                    LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
                    boolean enabled = service
                            .isProviderEnabled(LocationManager.GPS_PROVIDER);
                    if(enabled){

                        commonUtil.showLoadingSnackBar_linear(mylayout, _context, R.string.savingFarmerDetails);

                        sqLiteHelper.insertanimalLocal(animal_data);
                        System.out.println("animal data inserted...");
                        //                        Intent httpIntent = new Intent(_context, MakeHttpRequestService.class);
//                        httpIntent.putExtra("action","insertanimal");
//                        httpIntent.putExtra("animal_array", animal_data.toString());
//                        _context.startService(httpIntent);

                        String url = "http://animaltrack.in/locationtracking/tracking/insertAnimalInfo/";
                        String url_location_inert="http://animaltrack.in/locationtracking/tracking/insertLocation/";
                        Log.d(TAG,"Inside inserting animal infor..");
                        JSONObject animal_json=new JSONObject();
                        try {
                            animal_json.put("device_id", device.toString());
                            animal_json.put("name",input_address.getText());
                            animal_json.put("type",educationSpinnerText);
                           int status=CommonUtil.sendsignalforinsert(device.toString(),input_address.getText().toString(),educationSpinnerText);
                            if(status==1){
                                Toast.makeText(getApplicationContext(),"Data iserted Successfully",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"There is some errors in Animal Data inserting",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG,e.toString());
                        }
                        System.out.println("animallll.,....."+animal_json.toString()+".."+url);
//                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                                url, animal_json,
//                                new Response.Listener<JSONObject>() {
//
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//
//                                        //Log.d(AppController., response.toString());
//
//                                        System.out.println("response..."+response.toString());
//
//
//                                    }
//                                }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                //VolleyLog.d(AppController, "Error: " + error.getMessage());
//                                System.out.println("error...on response"+error);
//                            }
//                        }) {
//                        };
//// Adding request to request queue
//                        AppController.getInstance().addToRequest(jsonObjReq, animalId);
//                        System.out.println("sended");

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {


                                Intent i = getBaseContext().getPackageManager()
                                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);

                            }
                        }, 2000);



                    }

else{

                        commonUtil.showSnackBar_linear(mylayout, _context, "Please Turn on Your GPS..", "error");
                    }
                }
                }else{
                    commonUtil.showSnackBar_linear(mylayout, _context, "Please Check Internet Connection", "error");



        }
            }

//        Toast.makeText(getApplicationContext(),input_address.getText().toString(),Toast.LENGTH_SHORT).show();
});

//        Toast.makeText(getApplicationContext(),input_address.getText().toString()+".."+educationSpinnerText,Toast.LENGTH_SHORT).show();


        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addFarmer);
//        fab.setOnClickListener(this);
//
//        farmerRecyclerLayoutManager= new LinearLayoutManager(_context);
//        farmerSearchRecyclerLayoutManager = new LinearLayoutManager(_context);
//
//        farmerRecyclerView = (RecyclerView) findViewById(R.id.farmer_recycler);
//        farmerRecyclerView.setLayoutManager(farmerRecyclerLayoutManager);
//        farmerSearchRecyclerView = (RecyclerView) findViewById(R.id.farmer_search_recycler);
//        farmerSearchRecyclerView.setLayoutManager(farmerSearchRecyclerLayoutManager);
//
//        farmerRecyclerAdapter = new FarmerAdapter(farmers_details,_context);
//        farmerRecyclerView.setAdapter(farmerRecyclerAdapter);
//        farmerRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(farmerRecyclerLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                start = start+10;
//                end = end+10;
//                getFarmerDetailsFromServer();
//            }
//        });
//        farmerSearchRecyclerAdapter = new FarmerAdapter(farmer_search_details,_context);
//        farmerSearchRecyclerView.setAdapter(farmerSearchRecyclerAdapter);
//        farmerSearchRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(farmerRecyclerLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                searchStart = searchStart+10;
//                searchEnd = searchEnd+10;
//                isQuerySubmitted = false;
//                getSearchFarmerDetailsFromServer(searchString);
//            }
//        });
//
//
//        farmerProgressBar = (ProgressBar) findViewById(R.id.farmer_loading);
//        noFarmerTextView = (TextView) findViewById(R.id.no_farmer_text_view);
      //  layout = (CoordinatorLayout) findViewById(R.id.main_coordinator);
//
//        prefEditor = preferences.edit();
//        prefEditor.putBoolean("refresh_receiver",true);
//        prefEditor.putString("active_screen",TAG);
//        prefEditor.commit();

      //  registerLocalDbPushReceiver();
    }


    private void registerLocalDbPushReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refresh");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG,"In on receive of Intent "+intent.getStringExtra("action"));
                if(intent.hasExtra("action")){
                    String action = intent.getStringExtra("action");
                    if(action.equals("pushed")){
                        commonUtil.showSnackBar(layout,_context,R.string.pushSuccess,null);
                        getLocalFarmerListData();
                    }else if(action.equals("conflict")){
                        String message = intent.getStringExtra("message");
                        commonUtil.showSnackBar(layout,_context,message,"error");
                        getLocalFarmerListData();
                    }else if(action.equals("newfarmer")){
                        String farmer_details_string = intent.getStringExtra("farmer_details");
                        try {
                            JSONArray new_farmer_details = new JSONArray(farmer_details_string);
                            if(!isSearch){
                                int curSize = farmerRecyclerAdapter.getItemCount();
                                if(farmers_details.length() == 0){
                                    farmers_details = new_farmer_details;
                                    farmerRecyclerAdapter = new FarmerAdapter(farmers_details,_context);
                                    farmerRecyclerView.setAdapter(farmerRecyclerAdapter);
                                }else{
                                    for(int i=0;i<new_farmer_details.length();i++){
                                        farmers_details.put(new_farmer_details.get(i));
                                    }
                                    int length  = new_farmer_details.length();
                                    if(length > 0){
                                        farmerRecyclerAdapter.notifyItemRangeInserted(curSize, farmers_details.length() - 1);
                                    }
                                }
                            }else{
                                int curSize = farmerSearchRecyclerAdapter.getItemCount();
                                if(isQuerySubmitted){
                                    farmer_search_details = new_farmer_details;
                                    farmerSearchRecyclerAdapter = new FarmerAdapter(farmer_search_details,_context);
                                    farmerSearchRecyclerView.setAdapter(farmerSearchRecyclerAdapter);
                                }else{
                                    for(int i=0;i<new_farmer_details.length();i++){
                                        farmer_search_details.put(new_farmer_details.get(i));
                                    }
                                    int length  = new_farmer_details.length();
                                    if(length > 0){
                                        farmerSearchRecyclerAdapter.notifyItemRangeInserted(curSize, farmer_search_details.length() - 1);
                                    }
                                }
                            }
                            setUtilTextAndProgress();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else if(action.equals("clicked")){
                        String aadhaarId = intent.getStringExtra("aadhaarId");
                        ViewFarmerActivity.start(_context,aadhaarId);
                    }
                }
            }
        };
        _context.registerReceiver(receiver, intentFilter);
        Boolean isreceiverRegistered = preferences.getBoolean("refresh_receiver",false);
        Log.d(TAG,"is receiver registered :"+isreceiverRegistered);
        getLocalFarmerListData();
    }

    private void getLocalFarmerListData() {
       farmers_details = sqLiteHelper.getFarmersFromLocalDB();
        if(farmers_details.length() > 0){
            uploadLocalFarmerListData(farmers_details);
        }
        start =0;
        end = 10;
        getFarmerDetailsFromServer();
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
        }else{
            if(farmerArray.length() > 0){
                commonUtil.showSnackBar(layout,_context,R.string.cantUpload,"error");
            }
        }
    }

    private void getFarmerDetailsFromServer() {
        boolean isNetConnected = commonUtil.isNetworkAvailable(_context);
        if(isNetConnected) {
            Intent httpIntent = new Intent(_context, MakeHttpRequestService.class);
            httpIntent.putExtra("action", "get_farmer_details");
            httpIntent.putExtra("start", start);
            httpIntent.putExtra("end", end);
            _context.startService(httpIntent);
        }else{
            commonUtil.showSnackBar(layout,_context,R.string.pleaseCheckInternetConnection,"error");
            setUtilTextAndProgress();
        }
    }

    private void getSearchFarmerDetailsFromServer(String searchString) {
        Intent httpIntent = new Intent(_context, MakeHttpRequestService.class);
        httpIntent.putExtra("action","get_farmer_details");
        httpIntent.putExtra("start",searchStart);
        httpIntent.putExtra("end",searchEnd);
        Pattern text_pattern = Pattern.compile(commonUtil.TEXT_PATTERN);
        Pattern number_pattern = Pattern.compile(commonUtil.NUMBER_PATTERN);
        Matcher text_matcher = text_pattern.matcher(searchString);
        Matcher number_matcher = number_pattern.matcher(searchString);
        if(!commonUtil.isNull(searchString)){
            if(text_matcher.matches() || number_matcher.matches()){
                boolean isNetConnected = commonUtil.isNetworkAvailable(_context);
                if(isNetConnected) {
                    httpIntent.putExtra("search", searchString);
                    _context.startService(httpIntent);
                }else{
                    commonUtil.showSnackBar(layout,_context,R.string.pleaseCheckInternetConnection,"error");
                }
            }
        }
    }

    private void setUtilTextAndProgress() {
        if(farmers_details.length() > 0){
            farmerProgressBar.setVisibility(View.INVISIBLE);
            noFarmerTextView.setVisibility(View.INVISIBLE);
        }else{
            farmerProgressBar.setVisibility(View.INVISIBLE);
            noFarmerTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItemCompat.OnActionExpandListener expandListener = new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                isSearch = true;
                farmerRecyclerView.setVisibility(View.INVISIBLE);
                farmerSearchRecyclerView.setVisibility(View.VISIBLE);
                farmer_search_details = new JSONArray();
                farmerSearchRecyclerAdapter = new FarmerAdapter(farmer_search_details,_context);
                farmerSearchRecyclerView.setAdapter(farmerSearchRecyclerAdapter);
                return true; //returing true to expand action item
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                isSearch = false;
                farmerRecyclerView.setVisibility(View.VISIBLE);
                farmerSearchRecyclerView.setVisibility(View.INVISIBLE);
                return true; //returning true on collapse action item
            }
        };
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem,expandListener);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchString = query;
                searchStart = 0;
                searchEnd = 10;
                isQuerySubmitted = true;
                getSearchFarmerDetailsFromServer(searchString);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Called When Query text is changed
                searchString = newText;
                searchStart = 0;
                searchEnd = 10;
                isQuerySubmitted = true;
                getSearchFarmerDetailsFromServer(searchString);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.addFarmer:
                AddFarmerActivity.start(_context);
                break;
        }
    }

    @Override
    public void onDestroy(){
        Log.d(TAG,"On Destroy of Main Activity");
        super.onDestroy();
        if(receiver != null){
            unregisterReceiver(receiver);
            prefEditor = preferences.edit();
            prefEditor.remove("refresh_receiver");
            prefEditor.commit();
        }
    }
}
