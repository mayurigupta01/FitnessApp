// Health activity and notification was created with help from the following source:
// https://data-flair.training/blogs/android-task-reminder-app/

package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HealthActivity extends AppCompatActivity {

    FloatingActionButton newActivityButton;
    RecyclerView myRecyclerView;
    ArrayList<ActivitiesModel> myActivities = new ArrayList<ActivitiesModel>();
    ActivitiesAdapter myActivitiesAdapter;

    private List<CustomerModel> customerData = MainActivity.customerData;
    private String customerEmail = ProfileCreation.customerEmail;
    int userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        myRecyclerView = findViewById(R.id.recyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        newActivityButton = findViewById(R.id.new_activity);
        newActivityButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Activities.class);
            startActivity(intent);
        });

        for(int i = 0; i < customerData.size(); i++) {
            if(customerData.get(i).customerEmail.equalsIgnoreCase(customerEmail)) {
                userID = customerData.get(i).user_id;
            }
        }

        Cursor cursor = new SQLhelper(getApplicationContext()).getActivities(userID);
        while(cursor.moveToNext()) {
            ActivitiesModel activitiesModel = new ActivitiesModel(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            myActivities.add(activitiesModel);
        }

        myActivitiesAdapter = new ActivitiesAdapter(myActivities);
        myRecyclerView.setAdapter((myActivitiesAdapter));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}