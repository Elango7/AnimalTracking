package com.innomind.farmersconnect.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.innomind.farmersconnect.R;
import com.innomind.farmersconnect.utility.CommonUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = ProfileFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private  View v;
    private AppCompatSpinner education_spinner;
    private AppCompatSpinner occupation_spinner;
    private AppCompatSpinner gross_spinner;
    private AppCompatSpinner occupation_details_spinner;
    private  ArrayAdapter<CharSequence> educationAdapter,occupationAdapter,grossAdapter,occupationDetailsAdapter;
    private static CommonUtil commonUtil = new CommonUtil();

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        AppCompatTextView headingText = (AppCompatTextView) v.findViewById(R.id.headingText);
        education_spinner = (AppCompatSpinner) v.findViewById(R.id.education_spinner);
        occupation_spinner = (AppCompatSpinner) v.findViewById(R.id.occupation_spinner);
        gross_spinner = (AppCompatSpinner) v.findViewById(R.id.gross_salary_spinner);
        occupation_details_spinner = (AppCompatSpinner) v.findViewById(R.id.occupation_details_spinner);


        Typeface tf = Typeface.createFromAsset(getActivity().getApplicationContext().getAssets(), "OpenSans-Semibold.ttf");
        headingText.setTypeface(tf);

        educationAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.education_array,android.R.layout.simple_spinner_item);
        educationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        education_spinner.setAdapter(educationAdapter);

        occupationAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.occupation_array,android.R.layout.simple_spinner_item);
        occupationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        occupation_spinner.setAdapter(occupationAdapter);
        occupation_spinner.setOnItemSelectedListener(this);

        grossAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.gross_income_array,android.R.layout.simple_spinner_item);
        grossAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gross_spinner.setAdapter(grossAdapter);

        loadOccupationDetails(R.array.agri_array);

        AppCompatButton save_button = (AppCompatButton) v.findViewById(R.id.saveButton);
        save_button.setOnClickListener(this);

        AppCompatButton previous_button = (AppCompatButton) v.findViewById(R.id.previousButton);
        previous_button.setOnClickListener(this);

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
            if(farmer_object.has("userEducation") && !commonUtil.isNull(farmer_object.getString("userEducation"))){
                String education = farmer_object.getString("userEducation");
                education_spinner.setSelection(educationAdapter.getPosition(education));
            }else{
                education_spinner.setSelection(0);
            }

            if(farmer_object.has("userOccupation") && !commonUtil.isNull(farmer_object.getString("userOccupation"))){
                String occupation = farmer_object.getString("userOccupation");
                occupation_spinner.setSelection(occupationAdapter.getPosition(occupation));
            }else{
                occupation_spinner.setSelection(0);
            }

            if(farmer_object.has("userGross") && !commonUtil.isNull(farmer_object.getString("userGross"))){
                String gross = farmer_object.getString("userGross");
                gross_spinner.setSelection(grossAdapter.getPosition(gross));
            }else{
                gross_spinner.setSelection(0);
            }

            if(farmer_object.has("userOccupationDetails") && !commonUtil.isNull(farmer_object.getString("userOccupationDetails"))){
                String gross = farmer_object.getString("userOccupationDetails");
                if(occupationDetailsAdapter != null){
                    occupation_details_spinner.setSelection(occupationDetailsAdapter.getPosition(gross));
                }
            }else{
                occupation_details_spinner.setSelection(0);
            }
        }catch(JSONException e){
            e.printStackTrace();
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.previousButton:
                doValidation("previous");
                break;
            case R.id.saveButton:
                doValidation("next");
                break;
        }
    }

    private void doValidation(String stage) {
        JSONObject inputJsonObject = new JSONObject();
        String educationSpinnerText = education_spinner.getSelectedItem().toString();
        String grossSpinnerText = gross_spinner.getSelectedItem().toString();
        String occupationSpinnerText = occupation_spinner.getSelectedItem().toString();
        String occupationDetailsSpinnerText = occupation_details_spinner.getSelectedItem().toString();
        try {
            inputJsonObject.put("education",educationSpinnerText);
            inputJsonObject.put("gross",grossSpinnerText);
            inputJsonObject.put("occupation",occupationSpinnerText);
            inputJsonObject.put("occupation_details",occupationDetailsSpinnerText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(mListener != null){
            if(stage.equals("next"))
                mListener.saveDetails(inputJsonObject);
            else if(stage.equals("previous"))
                mListener.goPrevious(inputJsonObject);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        switch(selectedItem){
            case "Agriculture":
                loadOccupationDetails(R.array.agri_array);
                break;
            case "Salaried":
                loadOccupationDetails(R.array.salaried_array);
                break;
            case "Self Employed":
                loadOccupationDetails(R.array.self_employed_array);
                break;
            case "Others":
                loadOccupationDetails(R.array.other_array);
                break;
        }
    }

    private void loadOccupationDetails(int load_array) {
        occupationDetailsAdapter = ArrayAdapter.createFromResource(getActivity(),load_array,android.R.layout.simple_spinner_item);
        occupationDetailsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        occupation_details_spinner.setAdapter(occupationDetailsAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        void goPrevious(JSONObject inputJsonObject);
        void saveDetails(JSONObject inputJsonObject);
    }
}
