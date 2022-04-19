package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
    }
}