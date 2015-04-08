package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

/**
 * Gustavo Dias
 * Class for manager all notifications methods
 */
public class NotificationActivity extends ActionBarActivity {

    SharedPreferences prefs;
    boolean allowNotification;
    Switch switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //Preference file
        prefs = getSharedPreferences("Preferences",MODE_PRIVATE);
        allowNotification = prefs.getBoolean("allowNotification", false);

        switchButton = (Switch) findViewById(R.id.switchButton);

        if(allowNotification){
            switchButton.setChecked(true);
        }else{
            switchButton.setChecked(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSwitchChange(View view) {
        boolean on = ((Switch) view).isChecked();

        if (on) {
            prefs.edit().putBoolean("allowNotification", true).commit();
            Intent intent = new Intent(NotificationActivity.this, NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 1, intent, 0);
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), am.INTERVAL_DAY * 7, pendingIntent);

        } else {
            if (Context.NOTIFICATION_SERVICE!=null) {
                prefs.edit().putBoolean("allowNotification", false).commit();
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(1);
            }
        }
    }

}

