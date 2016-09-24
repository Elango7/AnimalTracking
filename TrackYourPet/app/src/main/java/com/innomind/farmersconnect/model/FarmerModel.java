package com.innomind.farmersconnect.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Yokesh on 6/26/2016.
 */
public class FarmerModel {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        this.putFarmer_object("id",id);
    }

    private int id;
    private long user_aadhaar_id;
    private String user_name;
    private String user_gender;
    private String user_dob;
    private String user_father_name;
    private String user_address;
    private String user_city;
    private long pin_code;
    private String user_state;
    private String user_district;
    private long user_phone_no;
    private long user_alt_phone_no;
    private String user_marital_status;
    private String user_family_count;
    private String user_mother_name;
    private String user_landmark;
    private String user_occupation;
    private String user_education;
    private String user_gross;
    private String user_occupation_details;
    private JSONObject farmer_object = new JSONObject();


    public FarmerModel(){
        this.farmer_object = new JSONObject();
    }
    public JSONObject getFarmer_object() {
        return farmer_object;
    }

    public String getUser_mother_name() {
        return user_mother_name;
    }

    public void setUser_mother_name(String user_mother_name) {
        this.user_mother_name = user_mother_name;
        this.putFarmer_object("userMotherName",user_mother_name);
    }

    public void setFarmer_object(JSONObject farmer_object) {
        this.farmer_object = farmer_object;
    }

    public void putFarmer_object(String index,Object value){
        try {
            this.farmer_object.put(index,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public long getUser_phone_no() {
        return user_phone_no;
    }

    public void setUser_phone_no(long user_phone_no) {
        this.user_phone_no = user_phone_no;
        this.putFarmer_object("userPhoneNo",user_phone_no);
    }

    public long getUser_alt_phone_no() {
        return user_alt_phone_no;
    }

    public void setUser_alt_phone_no(long user_alt_phone_no) {
        this.user_alt_phone_no = user_alt_phone_no;
        this.putFarmer_object("userAltPhoneNo",user_alt_phone_no);
    }

    public String getUser_family_count() {
        return user_family_count;
    }

    public void setUser_family_count(String user_family_count) {
        this.user_family_count = user_family_count;
        this.putFarmer_object("userFamilyCount",user_family_count);
    }

    public String getUser_marital_status() {
        return user_marital_status;
    }

    public void setUser_marital_status(String user_marital_status) {
        this.user_marital_status = user_marital_status;
        this.putFarmer_object("userMaritalStatus",user_marital_status);
    }

    public String getUser_district() {
        return user_district;
    }

    public void setUser_district(String user_district) {
        this.user_district = user_district;
        this.putFarmer_object("userDistrict",user_district);
    }

    public long getUser_aadhaar_id() {
        return user_aadhaar_id;
    }

    public void setUser_aadhaar_id(long user_aadhaar_id) {
        this.user_aadhaar_id = user_aadhaar_id;
        this.putFarmer_object("userAadhaarId",user_aadhaar_id);
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
        this.putFarmer_object("userName",user_name);
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
        this.putFarmer_object("userGender",user_gender);
    }

    public String getUser_dob() {
        return user_dob;
    }

    public void setUser_dob(String user_dob) {
        this.user_dob = user_dob;
        this.putFarmer_object("userDob",user_dob);
    }

    public String getUser_father_name() {
        return user_father_name;
    }

    public void setUser_father_name(String user_father_name) {
        this.user_father_name = user_father_name;
        this.putFarmer_object("userFatherName",user_father_name);
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
        this.putFarmer_object("userAddress",user_address);
    }

    public void appendUser_address(String append_address){
        String address = this.user_address+","+append_address;
        setUser_address(address);
    }
    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
        this.putFarmer_object("userCity",user_city);
    }

    public long getPin_code() {
        return pin_code;
    }

    public void setPin_code(long pin_code) {
        this.pin_code = pin_code;
        this.putFarmer_object("pinCode",pin_code);
    }

    public String getUser_state() {
        return user_state;
    }

    public void setUser_state(String user_state) {
        this.user_state = user_state;
        this.putFarmer_object("userState",user_state);
    }

    public String getUser_landmark() {
        return user_landmark;
    }

    public void setUser_landmark(String user_landmark) {
        this.user_landmark = user_landmark;
        this.putFarmer_object("userLandmark",user_landmark);
    }

    public String getUser_occupation_details() {
        return user_occupation_details;
    }

    public void setUser_occupation_details(String user_occupation_details) {
        this.user_occupation_details = user_occupation_details;
        this.putFarmer_object("userOccupationDetails",user_occupation_details);
    }

    public String getUser_gross() {
        return user_gross;
    }

    public void setUser_gross(String user_gross) {
        this.user_gross = user_gross;
        this.putFarmer_object("userGross",user_gross);
    }

    public String getUser_education() {
        return user_education;
    }

    public void setUser_education(String user_education) {
        this.user_education = user_education;
        this.putFarmer_object("userEducation",user_education);
    }

    public String getUser_occupation() {
        return user_occupation;
    }

    public void setUser_occupation(String user_occupation) {
        this.user_occupation = user_occupation;
        this.putFarmer_object("userOccupation",user_occupation);
    }
}
