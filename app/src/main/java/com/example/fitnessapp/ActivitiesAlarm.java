package com.example.fitnessapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import java.util.UUID;

public class ActivitiesAlarm extends BroadcastReceiver {

    String CHANNEL_ID ="277";
    NotificationCompat.Builder notifyBuilder;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String activityName = bundle.getString("activityName");
        String activityDateTime = bundle.getString("activityDate") + " " + bundle.getString("activityTime");
        Log.e("activityName", activityName);
        Log.e("activityDateTime", activityDateTime);

        Intent newIntent = new Intent(context, ActivitiesNotification.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        newIntent.putExtra("activityName", activityName);
        newIntent.putExtra("activityDate", bundle.getString("activityDate"));
        newIntent.putExtra("activityTime", bundle.getString("activityTime"));

        PendingIntent pendingIntent = PendingIntent.getActivity(context, UUID.randomUUID().hashCode(), newIntent, PendingIntent.FLAG_IMMUTABLE);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifyBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.activity_alarm);
        remoteViews.setImageViewResource(R.id.icon, R.drawable.ic_baseline_directions_run_100);
        remoteViews.setTextViewText(R.id.activityName, activityName);
        remoteViews.setTextViewText(R.id.activityDateTime, activityDateTime);
        notifyBuilder.setSmallIcon(R.drawable.alarm);
        notifyBuilder.setAutoCancel(true);
        notifyBuilder.setOngoing(true);
        notifyBuilder.setPriority(Notification.PRIORITY_HIGH);
        notifyBuilder.setOnlyAlertOnce(true);
        notifyBuilder.build().flags = Notification.FLAG_NO_CLEAR | Notification.PRIORITY_HIGH;
        notifyBuilder.setContent(remoteViews);
        notifyBuilder.setContentIntent(pendingIntent);
        notifyBuilder.setContentTitle(activityName);
        notifyBuilder.setContentText(activityDateTime);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
            notifyBuilder.setChannelId(CHANNEL_ID);
        }

        notificationManager.notify(0, notifyBuilder.build());


    }
}