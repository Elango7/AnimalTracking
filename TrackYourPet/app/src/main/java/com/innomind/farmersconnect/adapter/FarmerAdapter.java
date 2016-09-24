package com.innomind.farmersconnect.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innomind.farmersconnect.R;
import com.innomind.farmersconnect.utility.CommonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Yokesh on 6/29/2016.
 */
public class FarmerAdapter extends RecyclerView.Adapter<FarmerAdapter.ViewHolder>{
    private static final String TAG = FarmerAdapter.class.getSimpleName();
    private Context _context;

    private JSONArray farmersArray;
    private static final CommonUtil commonUtil = new CommonUtil();

    public FarmerAdapter(JSONArray farmersList,Context context){
        this.farmersArray = farmersList;
        _context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_farmer_list,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            Log.d(TAG,"Posiiton :"+position);
            JSONObject farmerObject = farmersArray.getJSONObject(position);
            holder.farmerIdView.setText(farmerObject.getString("userAadhaarId"));
            holder.farmerNameView.setText(farmerObject.getString("userName"));
            holder.farmerPhoneView.setText(farmerObject.getString("userPhoneNo"));
            String address = farmerObject.getString("userCity");
            if(!address.equals(farmerObject.getString("userDistrict"))){
                address += ","+farmerObject.getString("userDistrict");
            }
            address += "-"+farmerObject.getString("pinCode");
            holder.farmerAddressView.setText(address);
            String createdOn = farmerObject.getString("createdOn");
            Date parsedCreatedDate = commonUtil.parseDate(createdOn);
            Log.d(TAG,"parsedCreatedDate :"+parsedCreatedDate);
            String formattedDate = commonUtil.formatDate(parsedCreatedDate);
            holder.farmerCreatedView.setText(formattedDate);
            String gender = farmerObject.getString("userGender");
            Log.d(TAG,"Gender :"+gender);
            if(gender.equals("F")){
                holder.farmerImageView.setImageDrawable(_context.getResources().getDrawable(R.drawable.female));
            }else if(gender.equals("M")){
                holder.farmerImageView.setImageDrawable(_context.getResources().getDrawable(R.drawable.male));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return this.farmersArray.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
    protected TextView farmerNameView;
    protected TextView farmerAddressView;
    protected TextView farmerPhoneView;
    protected TextView farmerCreatedView;
    protected ImageView farmerImageView;
    protected TextView farmerIdView;
    protected RelativeLayout farmer_list_layout;

    public ViewHolder(View itemView) {
        super(itemView);
        farmer_list_layout = (RelativeLayout) itemView.findViewById(R.id.farmer_list_layout);
        farmerIdView = (TextView) itemView.findViewById(R.id.farmer_id);
        farmerNameView = (TextView) itemView.findViewById(R.id.farmer_name);
        farmerAddressView = (TextView) itemView.findViewById(R.id.farmer_address);
        farmerPhoneView = (TextView) itemView.findViewById(R.id.farmer_phone);
        farmerCreatedView = (TextView) itemView.findViewById(R.id.farmer_created);
        farmerImageView = (ImageView) itemView.findViewById(R.id.farmer_icon);
        farmer_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aadhaarId = farmerIdView.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("aadhaarId", aadhaarId);
                intent.putExtra("action","clicked");
                intent.setAction("refresh");
                v.getContext().sendBroadcast(intent);
            }
        });
    }
}
}
