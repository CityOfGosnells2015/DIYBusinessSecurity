package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmergencyAdapter extends BaseAdapter {

    Context context;
    int resource;
    HashMap emergencyList;
    ArrayList mData;

    public EmergencyAdapter(Context context, int resource, HashMap<String, String> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
        this.context = context;
        this.resource = resource;
        emergencyList = map;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public HashMap.Entry<String, String> getItem(int position) {
        return (HashMap.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View result;
        //Log.d("Debug", "Oi I am here");

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_display_emergency, parent, false);
        } else {
            result = convertView;
        }

        //HashMap.Entry<String, String> item = getItem(position);

        if (!emergencyList.isEmpty()){
            for (Object key : emergencyList.keySet()){

                TextView txtTitle = (TextView) result.findViewById(R.id.nameEmergency);
                ImageView iconPhone = (ImageView) result.findViewById(R.id.phoneIcon);

                //Set TextView
                txtTitle.setTextColor(Color.BLACK);
                txtTitle.setText(key.toString());

                //Set ImageView
                final String number = emergencyList.get(key.toString()).toString();
                iconPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                        //startActivity(intent);
                    }
                });
            }
        }

        return result;
    }
}
