package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.List;

public class HealthDashBoard extends AppCompatActivity {
     private List<CustomerModel> customerData = ProfileCreation.customerData;
     private String customerName = ProfileCreation.customerName;
     private String customerEmail = ProfileCreation.customerEmail;
     private String healthIndex;
     TextView showIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_dashboard);
        String setname = customerName.substring(0,1).toUpperCase()+customerName.substring(1).toLowerCase();
        String welcomeMessage = "Your Health DashBoard!" + " "+setname;

        TextView show = (TextView) findViewById(R.id.textViewShowWelcome);
        show.setText(welcomeMessage);

        showIndex = (TextView) findViewById(R.id.textViewShowIndex);

        showHealthIndex(customerEmail);


    }

     public void showHealthIndex(String email){

         for(int i = 0 ; i<customerData.size() ; i++){
             if(customerData.get(i).customerEmail.equalsIgnoreCase(email)){
               String sleepHours = customerData.get(i).customerAge;
               String waterConsumption = customerData.get(i).customerWaterIntake;
               String physicalCondition = customerData.get(i).customerPhysicalCondition;
               String mentalCondition = customerData.get(i).customerMedicalCondition;
               //logic to calculate poor health
               if(Integer.parseInt(sleepHours)<5 && Integer.parseInt(waterConsumption)<2
                 || (!physicalCondition.equalsIgnoreCase("NA")) ||
                       (!mentalCondition.equalsIgnoreCase("NA"))){
                  healthIndex = "Poor Health";
                  showIndex.setText(healthIndex);
               }
               else if((Integer.parseInt(sleepHours)>5 && (Integer.parseInt(sleepHours)<8))
                       || (Integer.parseInt(waterConsumption)>=3 && Integer.parseInt(waterConsumption)<=6)
                       || (physicalCondition.equalsIgnoreCase("NA") && (!mentalCondition.equalsIgnoreCase("NA")))){
                   healthIndex = "Good Health";
                   showIndex.setText(healthIndex);
               }
               else if((Integer.parseInt(sleepHours)>=7)
                       || (Integer.parseInt(waterConsumption)>6)
                       || ((physicalCondition.equalsIgnoreCase("NA") && (mentalCondition.equalsIgnoreCase("NA"))))){
                   healthIndex = "Excellent Health";
                   showIndex.setText(healthIndex);
               }
             }
         }
    }


    public void showWeightLossSuggestions(View v){
        Log.e("weight loss" , "going to weight loss suggestions");
        Intent intent = new Intent(HealthDashBoard.this, WeightLoss.class);
        startActivity(intent);
    }

    public void showNutritionalFitness(View v){
        Log.e("nutritional fitness" , "getting nutritional health data ");
        Intent intent = new Intent(HealthDashBoard.this, NutritionalHealth.class);
        startActivity(intent);
    }

    public void healthActivity(View view) {
        Intent intent = new Intent(HealthDashBoard.this, HealthActivity.class);
        startActivity(intent);
    }

    public void weightActivity(View view) {
        Intent intent = new Intent(HealthDashBoard.this, WeightActivity.class);
        startActivity(intent);
    }

    public void calculationActivity(View view) {
        Intent intent = new Intent(HealthDashBoard.this, Calculations.class);
        startActivity(intent);
    }

    public void stepSensorActivity(View view) {
        Intent intent = new Intent(HealthDashBoard.this, StepSensor.class);
        startActivity(intent);
    }
}
