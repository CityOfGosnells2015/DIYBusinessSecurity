package com.mylayouts.jm.cityofgosnellsdiybusinesssecurity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Gustavo Dias
 * Class to handle notifications receivers
 */
public class NotificationReceiver extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent) {
        String type = null;

        if (intent.getExtras() != null && intent.getExtras().containsKey("type")) {
            type = intent.getExtras().getString("type");
        }

        switch(type){
            case "daily":
                showNotificationDaily(context);
                break;
            case "weekly":
                showNotificationWeekly(context);
                break;
            case "monthly":
                showNotificationMonthly(context);
                break;

        }
    }

    public void showNotificationDaily(Context context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 1, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Daily Notification")
                .setContentText("Please, checking yours daily notifications.");
        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

    public void showNotificationWeekly(Context context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 2, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Weekly Notification")
                .setContentText("Please, checking yours weekly notifications.");
        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(2, mBuilder.build());
    }

    public void showNotificationMonthly(Context context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 3, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("Monthly Notification")
                .setContentText("Please, checking yours monthly notifications.");
        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(3, mBuilder.build());
    }
}
