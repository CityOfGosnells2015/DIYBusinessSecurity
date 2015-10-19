package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Gustavo Dias
 * Class for manager all notifications methods
 */
public class NotificationActivity extends ActionBarActivity implements OnMenuItemClickListener {

    SharedPreferences prefs;
    boolean weeklyNotification, dailyNotification, monthlyNotification;
    Switch switchWeeklyButton;
    Switch switchDailyButton;
    Switch switchMonthlyButton;
    ListView listViewDaily;
    ListView listViewMonthly;
    ListView listViewWeekly;
    int themeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Loading preferences
        prefs = getSharedPreferences("Preferences",MODE_PRIVATE);
        themeValue = prefs.getInt("textSize",0);

        //Loading the correct theme application
        ChangeTheme.onActivityCreateSetTheme(this, themeValue);

        //Set layout for activity
        setContentView(R.layout.activity_notification);

        //Get objects from XML
        switchDailyButton = (Switch) findViewById(R.id.switchDailyButton);
        switchWeeklyButton = (Switch) findViewById(R.id.switchWeeklyButton);
        switchMonthlyButton = (Switch) findViewById(R.id.switchMonthlyButton);
        listViewDaily = (ListView) findViewById(R.id.listDaily);
        listViewWeekly = (ListView) findViewById(R.id.listWeekly);
        listViewMonthly = (ListView) findViewById(R.id.listMonthly);

        //Preference file
        prefs = getSharedPreferences("Preferences",MODE_PRIVATE);

        //Loading preference values
        dailyNotification = prefs.getBoolean("dailyNotification", false);
        weeklyNotification = prefs.getBoolean("weeklyNotification", false);
        monthlyNotification = prefs.getBoolean("monthlyNotification", false);

        //Testing notification switches
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

        //Load notification from the Global variables
        GlobalChecklist globalChecklist = (GlobalChecklist) getApplication();
        ArrayList<Notification> notificationList = globalChecklist.getNotifications();

        //Loading daily notification list
        NotificationAdapter adapter = new NotificationAdapter(this, globalChecklist.getTheOneChecklist().getNotificationByPeriod("Daily",notificationList));
        listViewDaily.setAdapter(adapter);

        //Loading weekly notification list
        adapter = new NotificationAdapter(this, globalChecklist.getTheOneChecklist().getNotificationByPeriod("Weekly",notificationList));
        listViewWeekly.setAdapter(adapter);

        //Loading monthly notification list
        adapter = new NotificationAdapter(this, globalChecklist.getTheOneChecklist().getNotificationByPeriod("Monthly",notificationList));
        listViewMonthly.setAdapter(adapter);

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

    public void showMenu(View v){
        Intent intent;
        intent = new Intent(this, MenuActivity.class);
        intent.putExtra("textValue",themeValue);
        startActivity(intent);
    }


    public void onDailyNotification(View view) {
        boolean on = ((Switch) view).isChecked();

        if (on) {
            prefs.edit().putBoolean("dailyNotification", true).commit();
            Intent intent = new Intent(NotificationActivity.this, NotificationReceiver.class);
            intent.putExtra("type", "daily");

            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 1, intent, 0);

            //Setting the alarm - time and frequency
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 17);
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.SECOND, 00);
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.setRepeating(am.RTC_WAKEUP, calendar.getTimeInMillis(), am.INTERVAL_DAY, pendingIntent1);

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
            intent.putExtra("type","weekly");

            PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 2, intent, 0);

            //Setting the alarm - time and frequency
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 17);
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.SECOND, 00);
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.setRepeating(am.RTC_WAKEUP, calendar.getTimeInMillis(), am.INTERVAL_DAY * 7, pendingIntent);

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
            prefs.edit().putBoolean("monthlyNotification", true).commit();
            Intent intent = new Intent(NotificationActivity.this, NotificationReceiver.class);
            intent.putExtra("type","monthly");

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 3, intent, 0);

            //Setting the alarm - time and frequency
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 17);
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.SECOND, 00);
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.setRepeating(am.RTC_WAKEUP, calendar.getTimeInMillis(), am.INTERVAL_DAY * 30, pendingIntent);

        } else {
            if (Context.NOTIFICATION_SERVICE!=null) {
                prefs.edit().putBoolean("monthlyNotification", false).commit();
                String ns = Context.NOTIFICATION_SERVICE;
                NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
                nMgr.cancel(3);
            }
        }
    }

    /**
     * This method is invoke to set the listview daily visible
     */
    public void setDailyVisible(View view){
        //Test if list is invisible
        if(!listViewDaily.isShown()){
            listViewDaily.setVisibility(View.VISIBLE);
            listViewWeekly.setVisibility(View.GONE);
            listViewMonthly.setVisibility(View.GONE);
        }else{
            listViewDaily.setVisibility(View.GONE);
            listViewWeekly.setVisibility(View.GONE);
            listViewMonthly.setVisibility(View.GONE);
        }
    }

    /**
     * This method is invoke to set the listview weekly visible
     */
    public void setWeeklyVisible(View view){
        //Test if list is invisible
        if(!listViewWeekly.isShown()){
            listViewDaily.setVisibility(View.GONE);
            listViewWeekly.setVisibility(View.VISIBLE);
            listViewMonthly.setVisibility(View.GONE);
        }else{
            listViewDaily.setVisibility(View.GONE);
            listViewWeekly.setVisibility(View.GONE);
            listViewMonthly.setVisibility(View.GONE);
        }
    }

    /**
     * This method is invoke to set the listview monthly visible
     */
    public void setMonthlyVisible(View view){
        //Test if list is invisible
        if(!listViewMonthly.isShown()){
            listViewDaily.setVisibility(View.GONE);
            listViewWeekly.setVisibility(View.GONE);
            listViewMonthly.setVisibility(View.VISIBLE);
        }else{
            listViewDaily.setVisibility(View.GONE);
            listViewWeekly.setVisibility(View.GONE);
            listViewMonthly.setVisibility(View.GONE);
        }
    }

    /**
     * This method will be invoked when a menu item is clicked if the item itself did
     * not already handle the event.
     *
     * @param item {@link MenuItem} that was clicked
     * @return <code>true</code> if the event was handled, <code>false</code> otherwise.
     */
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}

