package com.innomind.farmersconnect.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Iterator;

/**
 * Created by Yokesh on 6/28/2016.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHelper.class.getSimpleName();
    private static CommonUtil commonUtil = new CommonUtil();
    //Database Version
    private static final int DB_VERSION = 1;

    //Database Name
    private static final String DB_NAME = "INNOMIND_FARMERS";

    //TABLE_NAME
    private static final String TABLE_FARMER_NAME = "innomind_farmers_details";
    private static final String table_animal="animal";
    //Column_Name
    private static String[] FARMER_COLUMNS = new String[]{"userAadhaarId","userName","userGender","userFatherName","userAddress",
    "userCity","userMotherName","userDistrict","userState","pinCode","userDob","userLandmark","userPhoneNo","userAltPhoneNo",
    "userMaritalStatus","userEducation","userGross","userOccupation","userOccupationDetails","userFamilyCount"};
    private static String[] animal_column=new String[]{"devid","animalname"};

    public SQLiteHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("db check");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FARMER_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+table_animal);
        int column_length = FARMER_COLUMNS.length;
        int col_animal_lengt=animal_column.length;
        String create_farmer_table = "CREATE TABLE "+TABLE_FARMER_NAME+" ( ";
        String create_animal_table = "CREATE TABLE "+table_animal+" ( ";

        for(int i =0 ;i<column_length;i++){
            String farmer_column = FARMER_COLUMNS[i];
            create_farmer_table += " "+farmer_column+" TEXT,";
        }
        create_farmer_table += " createdOn Text)";
        db.execSQL(create_farmer_table);


        for(int i =0 ;i<col_animal_lengt;i++){
            String animal_col = animal_column[i];
            create_animal_table += " "+animal_col+" TEXT,";
        }
        create_animal_table += " createdOn Text)";
        db.execSQL(create_animal_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FARMER_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+table_animal);
        onCreate(db);
    }
    public void insertanimalLocal(JSONObject animal_data){
        System.out.println("here......");
        SQLiteDatabase db_animal = this.getWritableDatabase();
        ContentValues contentValues_animal = new ContentValues();
        Iterator<String> iterator_animal = animal_data.keys();
        while(iterator_animal.hasNext()){
            String key = iterator_animal.next();
            try {
                String value = animal_data.getString(key);
                contentValues_animal.put(key,value);
            } catch (JSONException e) {
                System.out.println(e.getMessage().toString());
                e.printStackTrace();
            }
        }
        long id_animal;
        try{
            id_animal= db_animal.insert(table_animal,null,contentValues_animal);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage().toString());
        }

    }
    public void insertFarmersIntoLocalDB(JSONObject farmer_object) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Iterator<String> iterator = farmer_object.keys();
        while(iterator.hasNext()){
            String key = iterator.next();
            try {
                String value = farmer_object.getString(key);
                contentValues.put(key,value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Date date = commonUtil.getToday();
        contentValues.put("createdOn",commonUtil.getToday().toString());
        long id = db.insert(TABLE_FARMER_NAME,null,contentValues);
        Log.d(TAG,"New Farmer Details added to offline :"+id);


    }


    public JSONArray getFarmersFromLocalDB(){
        JSONArray farmersList = new JSONArray();
        String select_query = "Select * from "+TABLE_FARMER_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select_query, null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            while(!cursor.isAfterLast()){
                int columnCount = cursor.getColumnCount();
                JSONObject farmerObject = new JSONObject();
                for(int i=0;i<columnCount;i++){
                    String columnName = cursor.getColumnName(i);
                    String columnValue = cursor.getString(i);
                    try{
                        if(columnName.equals("userDob")){
                            Date parsedDobDate = commonUtil.parseDobDate(columnValue);
                            farmerObject.put(columnName,parsedDobDate);
                        }else{
                            farmerObject.put(columnName,columnValue);
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
                farmersList.put(farmerObject);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return farmersList;
    }

    public void clearLocalDB(JSONArray farmersArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i=0;i<farmersArray.length();i++){
            try {
                JSONObject farmerObject = farmersArray.getJSONObject(i);
                String userAadhaarId = farmerObject.getString("userAadhaarId");
                // Delete a row
                db.delete(TABLE_FARMER_NAME, "userAadhaarId = "+userAadhaarId, null);
                //db.close();
                Log.d(TAG, "Deleted Contact info from sqlite");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void clearLocalAnimalTrackTable() {
        SQLiteDatabase db = this.getWritableDatabase();
            try {
                db.delete(table_animal,null, null);
                Log.d(TAG, "Delete all information from animal table");
            } catch (Exception e) {
                e.printStackTrace();
            }

    }


    public JSONObject getanimalFromLocalDB(String devid) {
        JSONObject farmerObject = new JSONObject();
        String select_query = "Select * from "+table_animal+" WHERE devid='"+devid+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select_query,null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            while(!cursor.isAfterLast()){
                int columnCount = cursor.getColumnCount();
                farmerObject = new JSONObject();
                for(int i=0;i<columnCount;i++){
                    String columnName = cursor.getColumnName(i);
                    String columnValue = cursor.getString(i);
                    try{

                            farmerObject.put(columnName,columnValue);
                        }
                    catch(JSONException e){
                        e.printStackTrace();
                    }
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return farmerObject;
    }

    public JSONObject getFarmersFromLocalDB(String aadhaarId) {
        JSONObject farmerObject = new JSONObject();
        String select_query = "Select * from "+TABLE_FARMER_NAME+" WHERE userAadhaarId='"+aadhaarId+"'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(select_query,null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            while(!cursor.isAfterLast()){
                int columnCount = cursor.getColumnCount();
                farmerObject = new JSONObject();
                for(int i=0;i<columnCount;i++){
                    String columnName = cursor.getColumnName(i);
                    String columnValue = cursor.getString(i);
                    try{
                        if(columnName.equals("userDob")){
                            Date parsedDobDate = commonUtil.parseDobDate(columnValue);
                            farmerObject.put(columnName,parsedDobDate);
                        }else{
                            farmerObject.put(columnName,columnValue);
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return farmerObject;
    }
}
