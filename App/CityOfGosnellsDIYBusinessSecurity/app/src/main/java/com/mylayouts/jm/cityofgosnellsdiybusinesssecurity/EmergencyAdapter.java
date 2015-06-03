package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EmergencyAdapter extends ArrayAdapter<Link> {

    private Context context;
    private ArrayList<Link> mData;

    public EmergencyAdapter(Context context, ArrayList<Link> list) {
        super(context,R.layout.activity_display_emergency, list);
        mData = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_display_emergency, parent, false);
        }

        TextView txtTitle = (TextView) view.findViewById(R.id.nameEmergency);
        ImageView iconPhone = (ImageView) view.findViewById(R.id.phoneIcon);

        //Set TextView
        txtTitle.setTextColor(Color.BLACK);
        txtTitle.setText(mData.get(position).getName());

        //Set ImageView
        final String number = mData.get(position).getPhone();
        iconPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                callIntent.setData(Uri.parse("tel:" + number));

                try {
                    context.startActivity(callIntent);
                } catch (Exception ex) {
                    Toast.makeText(getContext(),
                            "Call failed, please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return view;
    }
}
