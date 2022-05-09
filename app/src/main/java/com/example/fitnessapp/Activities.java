package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
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
import java.util.List;
import java.util.UUID;

public class Activities extends AppCompatActivity {

    Button mySubmitButton, myDateButton, myTimeButton;
    private EditText myActivity;
    String activityNotifyTime;

    private List<CustomerModel> customerData = MainActivity.customerData;
    private String customerEmail = MainActivity.customerEmail;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        myActivity = findViewById(R.id.activityName);
        myDateButton = findViewById(R.id.dateButton);
        myTimeButton = findViewById(R.id.timeButton);
        mySubmitButton = findViewById(R.id.submitButton);

        myDateButton.setOnClickListener(view -> setActivityDate());

        myTimeButton.setOnClickListener(view -> setActivityTime());

        mySubmitButton.setOnClickListener(view -> {
            String activityName = myActivity.getText().toString().trim();
            String activityDate = myDateButton.getText().toString().trim();
            String activityTime = myTimeButton.getText().toString().trim();

            for(int i = 0; i < customerData.size(); i++) {
                if(customerData.get(i).customerEmail.equalsIgnoreCase(customerEmail)) {
                    userID = customerData.get(i).user_id;
                }
            }

            if(activityName.isEmpty()) {
                Toast.makeText(Activities.this, "Enter A Activity", Toast.LENGTH_SHORT).show();
            } else {
                if(activityDate.equals("") || activityTime.equals("")) {
                    Toast.makeText(Activities.this, "Enter Date and/or Time", Toast.LENGTH_SHORT).show();
                } else {
                    addActivity(userID, activityName, activityDate, activityTime);
                }
            }
        });
    }

    private void addActivity(int userID, String activityName, String activityDate, String activityTime) {
        String result = new SQLhelper(this).addHealthActivity(userID, activityName, activityDate, activityTime);
        setActivityAlarm(activityName, activityDate, activityTime);
        Log.e("addActivity", String.valueOf(userID));
        Log.e("addActivity", activityName);
        Log.e("addActivity", activityDate);
        Log.e("addActivity", activityTime);
        myActivity.setText("");
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }

    private void setActivityDate() {
        Calendar newActivityDate = Calendar.getInstance();

        DatePickerDialog activityDate = new DatePickerDialog(this, (datePicker, year, month, day) -> myDateButton.setText((newActivityDate.get(Calendar.MONTH) + 1)
                + "-" + newActivityDate.get(Calendar.DAY_OF_MONTH)
                + "-" + newActivityDate.get(Calendar.YEAR)), newActivityDate.get(Calendar.YEAR), newActivityDate.get(Calendar.MONTH), newActivityDate.get(Calendar.DAY_OF_MONTH));
        activityDate.show();
    }

    private void setActivityTime() {
        Calendar newActivityTime = Calendar.getInstance();

        TimePickerDialog activityTime = new TimePickerDialog(this, (timePicker, hour, minute) -> {
            activityNotifyTime = hour + ":" + minute;
            myTimeButton.setText(newActivityTime.get(Calendar.HOUR_OF_DAY) + ":" + newActivityTime.get(Calendar.MINUTE));
        }, newActivityTime.get(Calendar.HOUR_OF_DAY), newActivityTime.get(Calendar.MINUTE), false);
        activityTime.show();
    }

    private void setActivityAlarm(String activityName, String activityDate, String activityTime) {
        AlarmManager activityAlarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), ActivitiesAlarm.class);
        intent.putExtra("activityName", activityName);
        intent.putExtra("activityDate", activityDate);
        intent.putExtra("activityTime", activityTime);
        Log.e("activites", activityName);
        Log.e("activites", activityDate);
        Log.e("activites", activityTime);


        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), UUID.randomUUID().hashCode(), intent, PendingIntent.FLAG_IMMUTABLE);
        }
        else
        {
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), UUID.randomUUID().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        String activityDateTime = activityDate + " " + activityNotifyTime;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        try {
            Date date = dateFormat.parse(activityDateTime);
            activityAlarm.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intentBack = new Intent(getApplicationContext(), HealthActivity.class);
        intentBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentBack);
    }

}