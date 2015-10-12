package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.app.Activity;
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

    public NotificationAdapter(Activity context, ArrayList<Notification> list) {
        super(context,R.layout.activity_display_notification, list);

        this.list = list;
        this.context = context;
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
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    Notification element = (Notification) viewHolder.checkbox.getTag();
                    element.setSelected(buttonView.isChecked());
                }
            });
            view.setTag(viewHolder);
            viewHolder.checkbox.setTag(list.get(position));
        } else{
            view = convertView;
            ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.checkbox.setText(list.get(position).getQuestion());
        holder.checkbox.setChecked(list.get(position).isSelected());

        return view;
    }
}

