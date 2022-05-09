package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ActivitiesNotification extends AppCompatActivity {
    TextView textView1, textView2, textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_activities_notification);

        textView1 = findViewById(R.id.activity_name_notification);
        textView2 = findViewById(R.id.activity_date_notification);
        textView3 = findViewById(R.id.activity_time_notification);
        Bundle bundle = getIntent().getExtras();
        textView1.setText("Activity: " + bundle.getString("activityName"));
        textView2.setText("Date: " + bundle.getString("activityDate"));
        textView3.setText("Time: " + bundle.getString("activityTime"));
    }
}