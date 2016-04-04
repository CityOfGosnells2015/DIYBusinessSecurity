package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

/**
 * Created by Gustavo on 25/09/2015.
 */
public class NotificationAdapter extends ArrayAdapter<Notification>{

    private final Activity context;
    private final ArrayList<Notification> list;
    private SharedPreferences prefs;

    public NotificationAdapter(Activity context, ArrayList<Notification> list) {
        super(context,R.layout.activity_display_notification, list);

        this.list = list;
        this.context = context;
        //Preference file
        prefs = context.getSharedPreferences("Preferences", context.MODE_PRIVATE);
    }

    static class ViewHolder {
        protected CheckBox checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.activity_display_notification,null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.checkbox  = (CheckBox) view.findViewById(R.id.check);
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                //Action when selected an option
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    Notification element = (Notification) viewHolder.checkbox.getTag();
                    element.setSelected(buttonView.isChecked());
                    prefs.edit().putBoolean(element.getId(), true).commit();
                }
            });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));
        } else{
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }

        //Loading preference values
        list.get(position).setSelected(prefs.getBoolean(list.get(position).getId(), false));

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.checkbox.setText(list.get(position).getQuestion());
        holder.checkbox.setChecked(list.get(position).isSelected());
        //Toast.makeText(context, list.get(position).getId(), Toast.LENGTH_SHORT).show();

        return view;
    }
}

