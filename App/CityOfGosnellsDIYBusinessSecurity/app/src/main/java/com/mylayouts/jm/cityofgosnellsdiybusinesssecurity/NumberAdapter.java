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

/**
 * Created by Gustavo on 4/06/2015.
 */
public class NumberAdapter extends ArrayAdapter<Link> {

    private Context context;
    private ArrayList<Link> mData;

    public NumberAdapter(Context context, ArrayList<Link> list) {
        super(context,R.layout.activity_display_number, list);
        mData = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_display_number, parent, false);
        }

        TextView txtTitle = (TextView) view.findViewById(R.id.nameNumber);
        ImageView iconPhone = (ImageView) view.findViewById(R.id.phoneIcon);
        ImageView iconLink = (ImageView) view.findViewById(R.id.linkIcon);
        ImageView iconEdit = (ImageView) view.findViewById(R.id.editIcon);

        //Set TextView
        txtTitle.setTextColor(Color.BLACK);
        txtTitle.setText(mData.get(position).getName());

        //Set ImageView phone
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

        //Set ImageView website
        final String website = mData.get(position).getWebPage();
        iconLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                webIntent.setData(Uri.parse("http://" + website));

                try {
                    context.startActivity(webIntent);
                } catch (Exception ex) {
                    Toast.makeText(getContext(),
                            "Link failed, please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Set ImageView edit
        final int mPosition = position;
        iconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                Intent intent = new Intent(context, EditNumberActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("elemPosition",mPosition);

                try {
                    context.startActivity(intent);
                } catch (Exception ex) {
                    Toast.makeText(getContext(),
                            "Edit failed, please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}
