package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class EmergencyAdapter extends ArrayAdapter<Link> {

    Context context;
    int resource;
   ArrayList<Link> mData;

    public EmergencyAdapter(Context context, int resource, ArrayList<Link> list) {
        super(context,resource, list);
        mData = new ArrayList();
        mData = list;
        this.context = context;
        this.resource = resource;
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(resource, null);
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
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
                //startActivity(intent);
            }
        });



        return view;
    }*/


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.activity_display_emergency, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.nameEmergency);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.phoneIcon);

        textView.setText(mData.get(position).getName());


        position++;
        return rowView;
    }
}
