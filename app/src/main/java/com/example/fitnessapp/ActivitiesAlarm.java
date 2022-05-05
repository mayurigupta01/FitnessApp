package com.example.fitnessapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ActivitiesAlarm extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String activityName = bundle.getString("activityName");
        String activityDateTime = bundle.getString("activityDate") + " " + bundle.getString("activityTime");

        Intent noti = new Intent(context, activitiesNotification.class);

    }

}