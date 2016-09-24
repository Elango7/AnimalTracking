package com.innomind.farmersconnect.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.innomind.farmersconnect.R;
import com.innomind.farmersconnect.utility.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CommunicationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class CommunicationFragment extends Fragment implements View.OnClickListener, TextWatcher {
    private static final String TAG = CommunicationFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private static  View v;
    private TextInputEditText input_address;
    private TextInputEditText input_pincode;
    private AutoCompleteTextView input_district;
    private AutoCompleteTextView input_city;
    private AutoCompleteTextView input_state;
    private TextInputEditText input_landmark;

    private CommonUtil commonUtil = new CommonUtil();

    public CommunicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_communication, container, false);
        AppCompatTextView headingText = (AppCompatTextView) v.findViewById(R.id.headingText);
        input_address = (TextInputEditText) v.findViewById(R.id.input_address);
        input_pincode = (TextInputEditText) v.findViewById(R.id.input_pincode);
        input_district = (AutoCompleteTextView) v.findViewById(R.id.input_district);
        input_city = (AutoCompleteTextView) v.findViewById(R.id.input_city);
        input_state = (AutoCompleteTextView) v.findViewById(R.id.input_state);
        input_landmark = (TextInputEditText) v.findViewById(R.id.input_landmark);

        Typeface tf = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "OpenSans-Semibold.ttf");
        headingText.setTypeface(tf);

        AppCompatButton nextButton = (AppCompatButton) v.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);

        AppCompatButton previousButton = (AppCompatButton) v.findViewById(R.id.previousButton);
        previousButton.setOnClickListener(this);

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
        return v;
    }

    private void setInputValues(JSONObject farmer_object) {
        try {
            if(farmer_object.has("userAddress") && !commonUtil.isNull(farmer_object.getString("userDob"))){
                input_address.setText(farmer_object.getString("userAddress"));
            }else{
                input_address.setText("");
            }
            if(farmer_object.has("userCity") && !commonUtil.isNull(farmer_object.getString("userCity"))){
                input_city.setText(farmer_object.getString("userCity"));
            }else{
                input_city.setText("");
            }
            if(farmer_object.has("userDistrict") && !commonUtil.isNull(farmer_object.getString("userDistrict"))){
                input_district.setText(farmer_object.getString("userDistrict"));
            }else{
                input_district.setText("");
            }
            if(farmer_object.has("pinCode") && !commonUtil.isNull(farmer_object.getString("pinCode"))){
                input_pincode.setText(farmer_object.getString("pinCode"));
            }else{
                input_pincode.setText("");
            }
            if(farmer_object.has("userState") && !commonUtil.isNull(farmer_object.getString("userState"))){
                input_state.setText(farmer_object.getString("userState"));
            }else{
                input_state.setText("");
            }

            if(farmer_object.has("userLandmark") && !commonUtil.isNull(farmer_object.getString("userLandmark"))){
                input_landmark.setText(farmer_object.getString("userLandmark"));
            }else{
                input_landmark.setText("");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        input_address.addTextChangedListener(this);
        input_pincode.addTextChangedListener(this);
        input_district.addTextChangedListener(this);
        input_city.addTextChangedListener(this);
        input_state.addTextChangedListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
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
            case R.id.nextButton:
                doValidation("next");
                break;
            case R.id.previousButton:
                doValidation("previous");
                break;
        }
    }

    private void doValidation(String stage) {
        int canProceed = 0;
        JSONObject inputJSONObject = new JSONObject();

        String addressString = input_address.getText().toString();
        String pincodeString = input_pincode.getText().toString();
        String districtString = input_district.getText().toString();
        String cityString = input_city.getText().toString();
        String stateString = input_state.getText().toString();
        String landmarkString = input_landmark.getText().toString();

        try{
            canProceed += commonUtil.checkNull(v,addressString,R.id.input_address_layout);
            if(canProceed == 0){
                inputJSONObject.put("house",addressString);
            }

            int localProceed = 0;
            localProceed += commonUtil.checkNull(v,pincodeString,R.id.input_pincode_layout);
            if(localProceed ==0){
                localProceed += commonUtil.checkFormat(v,pincodeString,R.id.input_pincode_layout,"number");
                if(localProceed != 0){
                    canProceed += localProceed;
                }else{
                    inputJSONObject.put("pc",pincodeString);
                }
            }else{
                canProceed += localProceed;
            }

            localProceed = 0;
            localProceed += commonUtil.checkNull(v,districtString,R.id.input_district_layout);
            if(localProceed ==0){
                localProceed += commonUtil.checkFormat(v,districtString,R.id.input_district_layout,"text");
                if(localProceed != 0){
                    canProceed += localProceed;
                }else{
                    inputJSONObject.put("dist",districtString);
                }
            }else{
                canProceed += localProceed;
            }

            localProceed = 0;
            localProceed += commonUtil.checkNull(v,stateString,R.id.input_state_layout);
            if(localProceed ==0){
                localProceed += commonUtil.checkFormat(v,stateString,R.id.input_state_layout,"text");
                if(localProceed != 0){
                    canProceed += localProceed;
                }else{
                    inputJSONObject.put("state",stateString);
                }
            }else{
                canProceed += localProceed;
            }

            localProceed = 0;
            localProceed += commonUtil.checkNull(v,cityString,R.id.input_city_layout);
            if(localProceed ==0){
                localProceed += commonUtil.checkFormat(v,cityString,R.id.input_city_layout,"text");
                if(localProceed != 0){
                    canProceed += localProceed;
                }else{
                    inputJSONObject.put("subdist",cityString);
                }
            }else{
                canProceed += localProceed;
            }

            if(!commonUtil.isNull(landmarkString)){
                inputJSONObject.put("lm",landmarkString);
            }
        }catch(Exception e){

        }

        if(canProceed == 0){
            if(mListener != null) {
                if(stage.equals("next"))
                    mListener.goNext(inputJSONObject);
                else if(stage.equals("previous"))
                    mListener.goPrevious(inputJSONObject);
            }
        }
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
                case R.id.input_pincode:
                    int canProceed = commonUtil.checkNull(v,s.toString(),R.id.input_pincode_layout);
                    if(canProceed == 0){
                        commonUtil.checkFormat(v,s.toString(),R.id.input_pincode_layout,"number");
                    }
                    break;
                case R.id.input_district:
                    canProceed = commonUtil.checkNull(v,s.toString(),R.id.input_district_layout);
                    if(canProceed == 0){
                        commonUtil.checkFormat(v,s.toString(),R.id.input_district_layout,"text");
                    }
                    break;
                case R.id.input_state:
                    canProceed = commonUtil.checkNull(v,s.toString(),R.id.input_state_layout);
                    if(canProceed == 0){
                        commonUtil.checkFormat(v,s.toString(),R.id.input_state_layout,"text");
                    }
                    break;
                case R.id.input_city:
                    canProceed = commonUtil.checkNull(v,s.toString(),R.id.input_city_layout);
                    if(canProceed == 0){
                        commonUtil.checkFormat(v,s.toString(),R.id.input_city_layout,"text");
                    }
                    break;
                case R.id.input_address:
                    commonUtil.checkNull(v,s.toString(),R.id.input_address_layout);
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        void goNext(JSONObject inputJSONObject);
        void goPrevious(JSONObject inputJSONObject);
    }
}
