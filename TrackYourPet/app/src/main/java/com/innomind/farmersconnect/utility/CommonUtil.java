package com.innomind.farmersconnect.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.innomind.farmersconnect.AppController;
import com.innomind.farmersconnect.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Yokesh on 6/26/2016.
 */
public class CommonUtil {
    private static final String TAG = CommonUtil.class.getSimpleName();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static final String PHONE_PATTERN = "^[789]\\d{9}$";
    public static final String NUMBER_PATTERN = "\\d+";
    public static final String TEXT_PATTERN = "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$";
    public static double location;
    public static boolean updated=false;
   public static double common_lat;
    public static double common_lan;
  public static String insert_animalname="";
    public static String insert_devid="";
    public static String insert_type="";


    public static Document loadXmlFromString(String xmlContent){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlContent));
            return builder.parse(is);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getToday() {
        Date date = new Date();
        /*String formattedDate = formatDate(date);*/
        return date;
    }

    public Date parseDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            Date formattedDate = (Date) dateFormat.parse(date);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String formatDate(Date date){
        return dateFormat.format(date);
    }

    public Date parseDobDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date formattedDate = (Date) dateFormat.parse(date);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    //to check whethert the passed string is null or not
    public boolean isNull(String nullValue) {
        if(nullValue != null && !nullValue.equals("") && !nullValue.isEmpty() && !nullValue.equals("null")){
            return false;
        }
        return true;
    }

    public int checkNull(View v, String textString, int layout_id) {
        TextInputLayout inputLayout = (TextInputLayout) v.findViewById(layout_id);
        int canProceed = 0;
        if(isNull(textString)){
            setErrorToLayout(true,inputLayout,v.getResources().getString(R.string.pleaseEnterValue));
            canProceed++;
        }else{
            setErrorToLayout(false,inputLayout,null);
        }
        return canProceed;
    }

    public int checkFormat(View v,String textString,int layout_id,String format){
        Log.d(TAG,"In Check String :"+textString);
        TextInputLayout inputLayout = (TextInputLayout) v.findViewById(layout_id);
        int canProceed = 0;
        if(!isNull(textString)){
            Pattern pattern = Pattern.compile(TEXT_PATTERN);
            String errorMessage = v.getResources().getString(R.string.onlyString);
            if(format.equals("number")){
                pattern = Pattern.compile(NUMBER_PATTERN);
                errorMessage = v.getResources().getString(R.string.onlyNumber);
            }else if(format.equals("phone")){
                pattern = Pattern.compile(PHONE_PATTERN);
                errorMessage = v.getResources().getString(R.string.invalidPhone);
            }
            Matcher matcher = pattern.matcher(textString);
            if(!matcher.matches()){
                setErrorToLayout(true,inputLayout,errorMessage);
                canProceed++;
            }else{
                setErrorToLayout(false,inputLayout,null);
            }
        }
        return canProceed;
    }
    //to set Error message to layout of TextInputLayout
    public void setErrorToLayout(boolean enabled, TextInputLayout inputLayout, String errorMessage) {
        inputLayout.setErrorEnabled(enabled);
        inputLayout.setError(errorMessage);
    }

    public boolean isNetworkAvailable(Context _context){
        ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if(netInfo != null){
            return true;
        }else{
            return false;
        }
    }

    public void showSnackBar(CoordinatorLayout layout, Context _context, int msgID, String type) {
        Snackbar snackBar = Snackbar.make(layout,msgID,Snackbar.LENGTH_SHORT);
        snackBar.getView().setBackgroundColor(_context.getResources().getColor(R.color.colorAccent));
        //snackBarView.setActionTextColor(actionTextColor);
        TextView tv = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_text);
        if(type == null)
            tv.setTextColor(_context.getResources().getColor(R.color.colorWhite));
        else if(type.equals("error"))
            tv.setTextColor(_context.getResources().getColor(android.R.color.holo_red_dark));
        else if(type.equals("warning"))
            tv.setTextColor(_context.getResources().getColor(android.R.color.holo_orange_dark));
        snackBar.show();
    }


    public void showSnackBar_linear(LinearLayout layout, Context _context, String message, String type) {
        Snackbar snackBar = Snackbar.make(layout,message,Snackbar.LENGTH_SHORT);
        snackBar.getView().setBackgroundColor(_context.getResources().getColor(R.color.colorPrimaryDark));
        //snackBarView.setActionTextColor(actionTextColor);
        TextView tv = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_text);
        if(type == null)
            tv.setTextColor(_context.getResources().getColor(R.color.colorWhite));
        else if(type.equals("error"))
            tv.setTextColor(_context.getResources().getColor(android.R.color.holo_red_dark));
        else if(type.equals("warning"))
            tv.setTextColor(_context.getResources().getColor(android.R.color.holo_orange_dark));
        snackBar.show();
    }


    public void showSnackBar(CoordinatorLayout layout, Context _context, String message, String type) {
        Snackbar snackBar = Snackbar.make(layout,message,Snackbar.LENGTH_SHORT);
        snackBar.getView().setBackgroundColor(_context.getResources().getColor(R.color.colorPrimaryDark));
        //snackBarView.setActionTextColor(actionTextColor);
        TextView tv = (TextView) snackBar.getView().findViewById(android.support.design.R.id.snackbar_text);
        if(type == null)
            tv.setTextColor(_context.getResources().getColor(R.color.colorWhite));
        else if(type.equals("error"))
            tv.setTextColor(_context.getResources().getColor(android.R.color.holo_red_dark));
        else if(type.equals("warning"))
            tv.setTextColor(_context.getResources().getColor(android.R.color.holo_orange_dark));
        snackBar.show();
    }

    public  static void  insertData(JSONObject req){

        String tag_json_obj = "json_obj_req";

        String url = "http://animaltrack.in/restexample/tracking/insertLocation/";

        JSONObject params = new JSONObject();
        try {
            params.put("device_id",req.get("devid"));
            params.put("name",req.get("animalnane"));
            params.put("type","1");



        } catch (Exception e) {

        }

        System.out.println("params......" + params);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, params,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {





                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {


        };

// Adding request to request queue


     //   AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


    }





    public static  void sendsignal(String devid,double lat,double lan){
        //GPSTracker gpsTracker=new GPSTracker(CommonUtil);

        String url_location_inert="http://animaltrack.in/locationtracking/tracking/insertLocation/";
        JSONObject animal_json=new JSONObject();
        try {
            animal_json.put("device_id", devid.toString());
            animal_json.put("lan",lan);
            animal_json.put("lat",lat);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("animallll.,....."+animal_json.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url_location_inert, animal_json,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        //Log.d(AppController., response.toString());

                        System.out.println("response..."+response);


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(AppController, "Error: " + error.getMessage());
                System.out.println("error...on response"+error);
            }
        }) {


        };

// Adding request to request queue


        AppController.getInstance().addToRequest(jsonObjReq,"ARM");

    }


    public static  int sendsignalforinsert(String devid,String  name,String type){
        //GPSTracker gpsTracker=new GPSTracker(CommonUtil);

        String url_location_inert="http://animaltrack.in/locationtracking/tracking/insertAnimalInfo/";
        JSONObject animal_json=new JSONObject();
        try {
            animal_json.put("device_id", devid.toString());
            animal_json.put("name",name);
            animal_json.put("type",type);
            CommonUtil.insert_devid=devid.toString();
            CommonUtil.insert_animalname=name;
            CommonUtil.insert_type=type;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final int status=0;
        try{
           /* Log.d(TAG,"Inside inserting JSON Object..");
            StringRequest strRequest = new StringRequest(Request.Method.POST, url_location_inert,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            Log.d(TAG," response" + response.toString());
                            System.out.println(" response" + response.toString());
                            //      Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            System.out.println("error...on response"+error.toString());
                            //    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("device_id", CommonUtil.insert_devid);
                    params.put("name",CommonUtil.insert_animalname);
                    params.put("type",CommonUtil.insert_type);

                    return params;
                }
            };



            AppController.getInstance().addToRequest(strRequest,"ARM");*/
            System.out.println("animallll.,....."+animal_json.toString());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    url_location_inert, animal_json,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            //Log.d(AppController., response.toString());

                            System.out.println("response..."+response);

                            Log.d(TAG,"Animal Information inserted successfully..");
                         }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    //VolleyLog.d(AppController, "Error: " + error.getMessage());
                    System.out.println("error...on response"+error);
                }
            }) {

            };

// Adding request to request queue

            AppController.getInstance().addToRequest(jsonObjReq,"ARM");

        }catch(Exception ex){
            Log.d(TAG,"There is some error inside inserting animal insert..");
            Log.d(TAG,ex.toString());
            ex.printStackTrace();
            return 0;

        }

        return 1;
    }

    public void showLoadingSnackBar_linear(final LinearLayout layout,final  Context _context,int stringId) {

        final Snackbar bar = Snackbar.make(layout, stringId, Snackbar.LENGTH_INDEFINITE);
        bar.getView().setBackgroundColor(_context.getResources().getColor(R.color.colorPrimary));
        Snackbar.SnackbarLayout snack_view = (Snackbar.SnackbarLayout) bar.getView();
        snack_view.addView(new ProgressBar(_context));

        TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(_context.getResources().getColor(R.color.colorWhite));
        bar.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                bar.dismiss();
                showSnackBar_linear(layout, _context, "Saved..Successfully..!", "ss");
            }
        }, 2000);

    }
    public void showLoadingSnackBar_linear_2(final LinearLayout layout,final  Context _context,int stringId) {

        final Snackbar bar = Snackbar.make(layout, stringId, Snackbar.LENGTH_INDEFINITE);
        bar.getView().setBackgroundColor(_context.getResources().getColor(R.color.colorPrimary));
        Snackbar.SnackbarLayout snack_view = (Snackbar.SnackbarLayout) bar.getView();
        snack_view.addView(new ProgressBar(_context));

        TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(_context.getResources().getColor(R.color.colorWhite));
        bar.show();

    }
    public void showLoadingSnackBar(CoordinatorLayout layout, Context _context,int stringId) {
        Snackbar bar = Snackbar.make(layout, stringId, Snackbar.LENGTH_INDEFINITE);
        bar.getView().setBackgroundColor(_context.getResources().getColor(R.color.colorPrimary));
        Snackbar.SnackbarLayout snack_view = (Snackbar.SnackbarLayout) bar.getView();
        snack_view.addView(new ProgressBar(_context));

        TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(_context.getResources().getColor(R.color.colorWhite));
        bar.show();
    }
}
