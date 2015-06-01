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
    boolean weeklyNotification, dailyNotification, monthlyNotification;
    Switch switchWeeklyButton,switchDailyButton, switchMonthlyButton;
    int themeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Loading preferences
        prefs = getSharedPreferences("Preferences",MODE_PRIVATE);
        themeValue = prefs.getInt("textSize",0);

        //Loading the correct theme application
        ChangeTheme.onActivityCreateSetTheme(this,themeValue);

        //Set the back button at ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set layout for activity
        setContentView(R.layout.activity_notification);

        //Preference file
        prefs = getSharedPreferences("Preferences",MODE_PRIVATE);

        dailyNotification = prefs.getBoolean("dailyNotification", false);
        weeklyNotification = prefs.getBoolean("weeklyNotification", false);
        monthlyNotification = prefs.getBoolean("monthlyNotification", false);

        switchDailyButton = (Switch) findViewById(R.id.switchDailyButton);
        switchWeeklyButton = (Switch) findViewById(R.id.switchWeeklyButton);
        switchMonthlyButton = (Switch) findViewById(R.id.switchMonthlyButton);

        if(dailyNotification){
            switchDailyButton.setChecked(true);
        }else{
            switchDailyButton.setChecked(false);
        }

        if(weeklyNotification){
            switchWeeklyButton.setChecked(true);
        }else{
            switchWeeklyButton.setChecked(false);
        }

        if(monthlyNotification){
            switchMonthlyButton.setChecked(true);
        }else{
            switchMonthlyButton.setChecked(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        Intent intent;
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                intent = new Intent(this, MenuActivity.class);
                intent.putExtra("textValue",themeValue);
                startActivity(intent);
                return true;

            case R.id.action_about:
                intent = new Intent(this, About_Activity.class);
                intent.putExtra("textValue",themeValue);
                startActivity(intent);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    public void onDailyNotification(View view) {
        boolean on = ((Switch) view).isChecked();

        if (on) {
            prefs.edit().putBoolean("dailyNotification", true).commit();
            Intent intent = new Intent(NotificationActivity.this, NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 1, intent, 0);
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), am.INTERVAL_DAY, pendingIntent);

        } else {
            if (Context.NOTIFICATION_SERVICE!=null) {
                prefs.edit().putBoolean("dailyNotification", false).commit();
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(1);
            }
        }
    }

    public void onWeeklyNotification(View view) {
        boolean on = ((Switch) view).isChecked();

        if (on) {
            prefs.edit().putBoolean("weeklyNotification", true).commit();
            Intent intent = new Intent(NotificationActivity.this, NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 2, intent, 0);
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), am.INTERVAL_DAY * 7, pendingIntent);

        } else {
            if (Context.NOTIFICATION_SERVICE!=null) {
                prefs.edit().putBoolean("weeklyNotification", false).commit();
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(2);
            }
        }
    }

    public void onMonthlyNotification(View view) {
        boolean on = ((Switch) view).isChecked();

        if (on) {
            prefs.edit().putBoolean("switchMonthlyButton", true).commit();
            Intent intent = new Intent(NotificationActivity.this, NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 3, intent, 0);
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.setRepeating(am.RTC_WAKEUP, System.currentTimeMillis(), am.INTERVAL_DAY * 30, pendingIntent);

        } else {
            if (Context.NOTIFICATION_SERVICE!=null) {
                prefs.edit().putBoolean("switchMonthlyButton", false).commit();
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(3);
            }
        }
    }

}

