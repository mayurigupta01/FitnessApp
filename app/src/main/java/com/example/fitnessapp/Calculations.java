package com.example.fitnessapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Calculations extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    String[] activityLevel = {"Select Your Activity Level", "Sedentary", "Lightly Active", "Moderately Active", "Active", "Very Active"};
    int spinnerSelection;

    EditText heightFeetValue, heightInchesValue, weightValue, ageValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculations);

        spinner = (Spinner) findViewById(R.id.activityLevelSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Calculations.this,
                android.R.layout.simple_spinner_item, activityLevel);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        spinnerSelection = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    public double calculateCaloricNeed() {
        double activityLevelValue;
        double caloricNeed;
        double genderValue;

        switch(spinnerSelection) {
            case 1: activityLevelValue = 1.2;
                    break;
            case 2: activityLevelValue = 1.375;
                break;
            case 3: activityLevelValue = 1.55;
                break;
            case 4: activityLevelValue = 1.725;
                break;
            case 5: activityLevelValue = 1.9;
                break;
        }

        return caloricNeed;
    }
}
