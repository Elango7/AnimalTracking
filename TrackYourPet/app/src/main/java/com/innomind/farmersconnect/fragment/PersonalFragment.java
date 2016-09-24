package com.innomind.farmersconnect.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.innomind.farmersconnect.R;
import com.innomind.farmersconnect.utility.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class PersonalFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TextWatcher {
    private static final String TAG = PersonalFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private static View v;

    private TextInputEditText nameText;
    private TextInputEditText dobText;
    private TextInputEditText aadhaarText;
    private TextInputEditText phoneNumberText;
    private AppCompatRadioButton maleRadioButton;
    private AppCompatRadioButton femaleRadioButton;
    private AppCompatSpinner input_marital;
    private TextInputEditText fatherText;
    private TextInputEditText motherText;
    private TextInputEditText altNumberText;
    private TextInputEditText familyCountText;

    private ArrayAdapter<CharSequence> maritalAdapter;

    private static CommonUtil commonUtil = new CommonUtil();

    public PersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v = inflater.inflate(R.layout.fragment_personal, container, false);

        nameText = (TextInputEditText) v.findViewById(R.id.input_name);
        fatherText = (TextInputEditText) v.findViewById(R.id.input_father_name);
        motherText = (TextInputEditText) v.findViewById(R.id.input_mother_name);
        dobText = (TextInputEditText) v.findViewById(R.id.input_dob);
        aadhaarText = (TextInputEditText) v.findViewById(R.id.input_aadhar_no);
        phoneNumberText = (TextInputEditText) v.findViewById(R.id.input_number);
        altNumberText = (TextInputEditText) v.findViewById(R.id.input_alt_number);
        maleRadioButton = (AppCompatRadioButton) v.findViewById(R.id.input_gender_male);
        femaleRadioButton = (AppCompatRadioButton) v.findViewById(R.id.input_gender_female);
        familyCountText = (TextInputEditText) v.findViewById(R.id.input_no_family_memebers);
        input_marital = (AppCompatSpinner) v.findViewById(R.id.input_marital);
        AppCompatTextView headingText = (AppCompatTextView) v.findViewById(R.id.headingText);

        dobText.setOnClickListener(this);

        Typeface tf = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "OpenSans-Semibold.ttf");
        headingText.setTypeface(tf);

        AppCompatButton nextButton = (AppCompatButton) v.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);

        maritalAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.marital_array,android.R.layout.simple_spinner_item);
        maritalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input_marital.setAdapter(maritalAdapter);

        if(getArguments().containsKey("farmer_object")){
            String farmerObjectString =getArguments().getString("farmer_object");
            try {
                JSONObject farmer_object=new JSONObject(farmerObjectString);
                setInputValues(farmer_object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            JSONObject farmer_object=new JSONObject();
            setInputValues(farmer_object);
        }
      //  getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return v;
    }

    private void setInputValues(JSONObject farmer_object) {
        try {
            if(farmer_object.has("userName") && !commonUtil.isNull(farmer_object.getString("userName"))){
                nameText.setText(farmer_object.getString("userName"));
            }else{
                nameText.setText("");
            }

            if(farmer_object.has("userAadhaarId") && !commonUtil.isNull(farmer_object.getString("userAadhaarId"))){
                aadhaarText.setText(farmer_object.getString("userAadhaarId"));
            }else{
                aadhaarText.setText("");
            }

            if(farmer_object.has("userGender") && !commonUtil.isNull(farmer_object.getString("userGender"))){
                String gender = farmer_object.getString("userGender");
                if(gender.equals("M")){
                    maleRadioButton.setChecked(true);
                }else if(gender.equals("F")){
                    femaleRadioButton.setChecked(true);
                }
            }else{
                maleRadioButton.setChecked(false);
                femaleRadioButton.setChecked(false);
            }
            if(farmer_object.has("userDob") && !commonUtil.isNull(farmer_object.getString("userDob"))){
                String dob = farmer_object.getString("userDob");
                Log.d(TAG,"DOB :"+dob + "dob.contains(\"/\") "+dob.contains("/"));
                if(!dob.contains("/")){
                    Date dobDate = commonUtil.parseDate(dob);
                    String formatDate = commonUtil.formatDate(dobDate);
                    dobText.setText(formatDate);
                }else{
                    dobText.setText(dob);
                }
            }else{
                dobText.setText("");
            }


            if(farmer_object.has("userFatherName") && farmer_object.getString("userFatherName") != null){
                fatherText.setText(farmer_object.getString("userFatherName"));
            }else{
                fatherText.setText("");
            }

            if(farmer_object.has("userMotherName") && !commonUtil.isNull(farmer_object.getString("userMotherName"))){
                motherText.setText(farmer_object.getString("userMotherName"));
            }else{
                motherText.setText("");
            }

            if(farmer_object.has("userPhoneNo") && !commonUtil.isNull(farmer_object.getString("userPhoneNo"))){
                phoneNumberText.setText(farmer_object.getString("userPhoneNo"));
            }else{
                phoneNumberText.setText("");
            }

            if(farmer_object.has("userAltPhoneNo") && !commonUtil.isNull(farmer_object.getString("userAltPhoneNo"))){
                altNumberText.setText(farmer_object.getString("userAltPhoneNo"));
            }else{
                altNumberText.setText("");
            }

            if(farmer_object.has("userFamilyCount") && !commonUtil.isNull(farmer_object.getString("userFamilyCount"))){
                familyCountText.setText(farmer_object.getString("userFamilyCount"));
            }else{
                familyCountText.setText("");
            }

            if(farmer_object.has("userMaritalStatus") && !commonUtil.isNull(farmer_object.getString("userMaritalStatus"))){
                String married = farmer_object.getString("userMaritalStatus");
                input_marital.setSelection(maritalAdapter.getPosition(married));
            }else{
                input_marital.setSelection(0);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        nameText.addTextChangedListener(this);
        aadhaarText.addTextChangedListener(this);
        phoneNumberText.addTextChangedListener(this);
        altNumberText.addTextChangedListener(this);
        fatherText.addTextChangedListener(this);
        motherText.addTextChangedListener(this);
        familyCountText.addTextChangedListener(this);
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG,"on attach");
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            Log.d(TAG,"in if");
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.input_dob:
                showDatePickerDialog();
                break;
            case R.id.nextButton:
                doValidation();
                break;
        }
    }

    private void doValidation() {
        int canProceed = 0;
        JSONObject inputJSONObject = new JSONObject();

        String nameTextString = nameText.getText().toString();
        String dobTextString = dobText.getText().toString();
        String aadhaarTextString = aadhaarText.getText().toString();
        String phoneNumberTextString = phoneNumberText.getText().toString();

        int localProceed = 0;
        try {
            if(!maleRadioButton.isChecked() && !femaleRadioButton.isChecked()){
                canProceed++;
                femaleRadioButton.setError(getResources().getString(R.string.pleaseSelectValue));
            }else if(maleRadioButton.isChecked()){
                inputJSONObject.put("gender", "M");
            }else if(femaleRadioButton.isChecked()){
                inputJSONObject.put("gender", "F");
            }

            localProceed += commonUtil.checkNull(v, nameTextString, R.id.input_name_layout);
            if (localProceed == 0) {
                localProceed += commonUtil.checkFormat(v, nameTextString, R.id.input_name_layout, "text");
                if (localProceed != 0) {
                    canProceed += localProceed;
                } else {
                    inputJSONObject.put("name", nameTextString);
                }
            } else {
                canProceed += localProceed;
            }
            canProceed += commonUtil.checkNull(v, dobTextString, R.id.input_dob_layout);
            if(canProceed == 0){
                inputJSONObject.put("dob", dobTextString);
            }
            localProceed = 0;
            localProceed += commonUtil.checkNull(v, aadhaarTextString, R.id.input_aadhaar_no_layout);
            if (localProceed == 0) {
                localProceed += commonUtil.checkFormat(v, aadhaarTextString, R.id.input_aadhaar_no_layout, "number");
                if (localProceed != 0) {
                    canProceed += localProceed;
                }else{
                    inputJSONObject.put("uid", aadhaarTextString);
                }
            } else {
                canProceed += localProceed;
            }
            localProceed = 0;
            localProceed += commonUtil.checkNull(v, phoneNumberTextString, R.id.input_number_layout);
            if (localProceed == 0) {
                localProceed += commonUtil.checkFormat(v, phoneNumberTextString, R.id.input_number_layout, "phone");
                if (localProceed != 0) {
                    canProceed += localProceed;
                }else{
                    inputJSONObject.put("phone", phoneNumberTextString);
                }
            } else {
                canProceed += localProceed;
            }

            String altNumberTextString = altNumberText.getText().toString();
            String fatherNameTextString = fatherText.getText().toString();
            String motherNameTextString = motherText.getText().toString();
            String familyCountTextString = familyCountText.getText().toString();
            if (!commonUtil.isNull(altNumberTextString)) {
                canProceed += commonUtil.checkFormat(v, altNumberTextString, R.id.input_alt_number_layout, "phone");
                if(canProceed ==0){
                    inputJSONObject.put("aphone", altNumberTextString);
                }
            }
            if (!commonUtil.isNull(fatherNameTextString)) {
                canProceed += commonUtil.checkFormat(v, fatherNameTextString, R.id.input_father_name_layout, "text");
                if(canProceed ==0){
                    inputJSONObject.put("gname", fatherNameTextString);
                }
            }
            if (!commonUtil.isNull(motherNameTextString)) {
                canProceed += commonUtil.checkFormat(v, motherNameTextString, R.id.input_mother_name_layout, "text");
                if(canProceed ==0){
                    inputJSONObject.put("mname", motherNameTextString);
                }
            }
            if (!commonUtil.isNull(familyCountTextString)) {
                canProceed += commonUtil.checkFormat(v, familyCountTextString, R.id.input_no_family_memebers_layout, "number");
                inputJSONObject.put("fcount", familyCountTextString);
            }
            String maritalTextString = input_marital.getSelectedItem().toString();
            inputJSONObject.put("mstatus",maritalTextString);
        }catch(Exception e){

        }

        if(canProceed == 0){
            if (mListener != null) {
                mListener.goNext(inputJSONObject);
            }
        }
    }

    private void showDatePickerDialog() {
        String dobString = dobText.getText().toString();
        Calendar newCalendar = Calendar.getInstance();
        if(!commonUtil.isNull(dobString)){
            Date dobDate = commonUtil.parseDobDate(dobString);
            newCalendar.setTime(dobDate);
        }
        DatePickerDialog dobDatePickerDailog = new DatePickerDialog(getActivity(),this,newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));
        dobDatePickerDailog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar newDate = Calendar.getInstance();
        newDate.set(year, monthOfYear, dayOfMonth);
        dobText.setText(commonUtil.formatDate(newDate.getTime()));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        View focussedView = getActivity().getCurrentFocus();
        if(focussedView != null){
            int id = focussedView.getId();
            switch(id){
                case R.id.input_name:
                    int canProceed = commonUtil.checkNull(v,s.toString(),R.id.input_name_layout);
                    if(canProceed == 0){
                        commonUtil.checkFormat(v,s.toString(),R.id.input_name_layout,"text");
                    }
                    break;
                case R.id.input_aadhar_no:
                    canProceed = commonUtil.checkNull(v,s.toString(),R.id.input_aadhaar_no_layout);
                    if(canProceed == 0){
                        commonUtil.checkFormat(v,s.toString(),R.id.input_aadhaar_no_layout,"number");
                    }
                    break;
                case R.id.input_dob:
                    commonUtil.checkNull(v,s.toString(),R.id.input_dob_layout);
                    break;
                case R.id.input_number:
                    canProceed = commonUtil.checkNull(v,s.toString(),R.id.input_number_layout);
                    if(canProceed == 0){
                        commonUtil.checkFormat(v,s.toString(),R.id.input_number_layout,"phone");
                    }
                    break;
                case R.id.input_alt_number:
                    if(!commonUtil.isNull(s.toString())){
                        commonUtil.checkFormat(v,s.toString(),R.id.input_alt_number_layout,"phone");
                    }else{
                        TextInputLayout textLayout = (TextInputLayout) v.findViewById(R.id.input_alt_number_layout);
                        commonUtil.setErrorToLayout(false,textLayout,null);
                    }
                    break;
                case R.id.input_father_name:
                    if(!commonUtil.isNull(s.toString())){
                        commonUtil.checkFormat(v,s.toString(),R.id.input_father_name_layout,"text");
                    }else{
                        TextInputLayout textLayout = (TextInputLayout) v.findViewById(R.id.input_father_name_layout);
                        commonUtil.setErrorToLayout(false,textLayout,null);
                    }
                    break;
                case R.id.input_mother_name:
                    if(!commonUtil.isNull(s.toString())){
                        commonUtil.checkFormat(v,s.toString(),R.id.input_mother_name_layout,"text");
                    }else{
                        TextInputLayout textLayout = (TextInputLayout) v.findViewById(R.id.input_mother_name_layout);
                        commonUtil.setErrorToLayout(false,textLayout,null);
                    }
                    break;
                case R.id.input_no_family_memebers:
                    if(!commonUtil.isNull(s.toString())){
                        commonUtil.checkFormat(v,s.toString(),R.id.input_no_family_memebers_layout,"number");
                    }else{
                        TextInputLayout textLayout = (TextInputLayout) v.findViewById(R.id.input_no_family_memebers_layout);
                        commonUtil.setErrorToLayout(false,textLayout,null);
                    }
                    break;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void goNext(JSONObject inputObject);
    }
}
