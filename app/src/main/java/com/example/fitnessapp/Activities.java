package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Activities extends AppCompatActivity {

    Button mySubmitButton, myDateButton, myTimeButton;
    private EditText myActivity;
    String activityNotifyTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        myActivity = (EditText) findViewById(R.id.activityName);
        myDateButton = (Button) findViewById(R.id.datebutton);
        myTimeButton = (Button) findViewById(R.id.timeButton);
        mySubmitButton = (Button) findViewById(R.id.submitbutton);

        myDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActivityDate();
            }
        });

        myTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActivityTime();
            }
        });

        mySubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activityName = myActivity.getText().toString().trim();
                String activityDate = myDateButton.getText().toString().trim();
                String activityTime = myTimeButton.getText().toString().trim();

                if(activityName.isEmpty()) {
                    Toast.makeText(Activities.this, "Enter A Activity", Toast.LENGTH_SHORT).show();
                } else {
                    if(activityDate.equals("") || activityTime.equals("")) {
                        Toast.makeText(Activities.this, "Enter Date and/or Time", Toast.LENGTH_SHORT).show();
                    } else {
                        addActivity(activityName, activityDate, activityTime);
                    }
                }
            }
        });
    }

    private void addActivity(String activityName, String activityDate, String activityTime) {
        String result = new SQLhelper(this).addHealthActivity(activityName, activityDate, activityTime);
        setActivityAlarm(activityName, activityDate, activityTime);
        myActivity.setText("");
        Toast.makeText(Activities.this, result, Toast.LENGTH_SHORT).show();
    }

    private void setActivityDate() {
        final Calendar newActivityDate = Calendar.getInstance();

        DatePickerDialog activityDate = new DatePickerDialog(Activities.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, final int year, final int month, final int day) {
                myDateButton.setText(newActivityDate.get(Calendar.MONTH)
                        + "-" + newActivityDate.get(Calendar.DAY_OF_MONTH)
                        + "-" + newActivityDate.get(Calendar.YEAR));
            }
        }, newActivityDate.get(Calendar.YEAR), newActivityDate.get(Calendar.MONTH), newActivityDate.get(Calendar.DAY_OF_MONTH));
        activityDate.show();
    }

    private void setActivityTime() {
        final Calendar newActivityTime = Calendar.getInstance();

        TimePickerDialog activityTime = new TimePickerDialog(Activities.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, final int hour, final int minute) {
                activityNotifyTime = hour + ":" + minute;
                myTimeButton.setText(newActivityTime.get(Calendar.HOUR_OF_DAY) + ":" + newActivityTime.get(Calendar.MINUTE));
            }
        }, newActivityTime.get(Calendar.HOUR_OF_DAY), newActivityTime.get(Calendar.MINUTE), false);
        activityTime.show();
    }

    private void setActivityAlarm(String activityName, String activityDate, String activityTime) {
        AlarmManager activityAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(Activities.this, ActivitiesAlarm.class);
        intent.putExtra("activityName", activityName);
        intent.putExtra("activityDate", activityDate);
        intent.putExtra("activityTime", activityTime);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(Activities.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String activityDateTime = activityDate + " " + activityNotifyTime;
        DateFormat dateFormat = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date = dateFormat.parse(activityDateTime);
            activityAlarm.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
            Toast.makeText(Activities.this, "Activity Alarm", Toast.LENGTH_SHORT).show();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intentBack = new Intent(Activities.this, HealthActivity.class);
        intentBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentBack);
    }

}