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
public class LinkAdapter extends ArrayAdapter<Link> {

    private Context context;
    private ArrayList<Link> mData;

    public LinkAdapter(Context context, ArrayList<Link> list) {
        super(context,R.layout.activity_display_link, list);
        mData = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_display_link, parent, false);
        }

        TextView txtTitle = (TextView) view.findViewById(R.id.nameLink);
        ImageView iconLink = (ImageView) view.findViewById(R.id.linkIcon);

        //Set TextView
        txtTitle.setText(mData.get(position).getName());

        //Set ImageView website
        final String website = mData.get(position).getWebPage();
        iconLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                webIntent.setData(Uri.parse(website));

                try {
                    context.startActivity(webIntent);
                } catch (Exception ex) {
                    Toast.makeText(getContext(),
                            "Link failed, please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
