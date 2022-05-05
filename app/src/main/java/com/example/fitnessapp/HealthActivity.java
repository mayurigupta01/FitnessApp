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

public class HealthActivity extends AppCompatActivity {

    FloatingActionButton newActivityButton;
    RecyclerView myRecyclerView;
    ArrayList<ActivitiesModel> myActivities = new ArrayList<ActivitiesModel>();
    ActivitiesAdapter myActivitiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HealthActivity.this);
        //myRecyclerView.setLayoutManager(linearLayoutManager);
        newActivityButton = (FloatingActionButton) findViewById(R.id.new_activity);
        newActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HealthActivity.this, Activities.class);
                startActivity(intent);
            }
        });

        Cursor cursor = new SQLhelper(HealthActivity.this).getActivities();
        while(cursor.moveToNext()) {
            ActivitiesModel activitiesModel = new ActivitiesModel(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            myActivities.add(activitiesModel);
        }

        myActivitiesAdapter = new ActivitiesAdapter(myActivities);
        myRecyclerView.setAdapter((myActivitiesAdapter));
    }
}