package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activities extends AppCompatActivity {

    Button mySubmitButton, myDateButton, myTimeButton;
    private EditText myActivity;

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
                setDate();
            }
        });

        myTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime();
            }
        });

        mySubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String activity = myActivity.getText().toString().trim();
                String date = myDateButton.getText().toString().trim();
                String time = myTimeButton.getText().toString().trim();

                if(activity.isEmpty()) {
                    Toast.makeText(Activities.this, "Enter A Activity", Toast.LENGTH_SHORT).show();
                } else {
                    if(date.equals("") || time.equals("")) {
                        Toast.makeText(Activities.this, "Enter Date and/or Time", Toast.LENGTH_SHORT).show();
                    } else {
                        addActivity(activity, date, time);
                    }
                }
            }
        });
    }

    private void addActivity(String activityName, String date, String time) {
        String result = new SQLhelper(this).addHealthActivity(activityName, date, time);
        setAlarm(activityName, date, time);
        myActivity.setText("");
        Toast.makeText(Activities.this, result, Toast.LENGTH_SHORT).show();
    }

    private void setDate() {

    }

    private void setTime() {

    }

    private void setAlarm(String activityName, String date, String time) {

    }

}