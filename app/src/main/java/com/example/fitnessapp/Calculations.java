package com.example.fitnessapp;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Calculations extends AppCompatActivity {

    private List<CustomerModel> customerData = MainActivity.customerData;
    private String customerEmail = ProfileCreation.customerEmail;
    int userID;

    Spinner activityLevelSpinner, genderSpinner;
    String[] activityLevel = {"Select Your Activity Level", "Sedentary", "Lightly Active", "Moderately Active", "Active", "Very Active"};
    String[] gender = {"Select your Gender", "Male", "Female"};
    int activityLevelSpinnerSelection, genderSpinnerSelection;

    EditText getHeightFeet, getHeightInches, getWeight, getAge;
    TextView caloricNeedResult;
    int heightFeetValue, heightInchesValue, ageValue, weightValue;
    Button calculateButton;
    String placeholder;

    double caloricNeed = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculations);

        for(int i = 0; i < customerData.size(); i++) {
            if(customerData.get(i).customerEmail.equalsIgnoreCase(customerEmail)) {
                userID = customerData.get(i).user_id;
            }
        }

        activityLevelSpinner = (Spinner) findViewById(R.id.activityLevelSpinner);
        ArrayAdapter<String> activityLevelAdapter = new ArrayAdapter<String>(Calculations.this,
                android.R.layout.simple_spinner_item, activityLevel);
        activityLevelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevelSpinner.setAdapter(activityLevelAdapter);
        //spinner.setOnItemSelectedListener(this);
        activityLevelSpinner.setOnItemSelectedListener(new ActivityLevelSpinnerClass());

        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(Calculations.this,
                android.R.layout.simple_spinner_item, gender);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        //spinner.setOnItemSelectedListener(this);
        genderSpinner.setOnItemSelectedListener(new GenderSpinnerClass());

        getHeightFeet = (EditText) findViewById(R.id.heightFeet);
        getHeightInches = (EditText) findViewById(R.id.heightInches);
        getWeight = (EditText) findViewById(R.id.weightPounds);
        getAge = (EditText) findViewById(R.id.age);

        caloricNeedResult = findViewById(R.id.calorieNeed);
        caloricNeedResult.setText("0");
        calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double activityLevelValue = 0.0;

                placeholder = getHeightFeet.getText().toString();
                heightFeetValue = Integer.parseInt(placeholder);
                placeholder = getHeightInches.getText().toString();
                heightInchesValue = Integer.parseInt(placeholder);
                placeholder = getWeight.getText().toString();
                weightValue = Integer.parseInt(placeholder);
                placeholder = getAge.getText().toString();
                ageValue = Integer.parseInt(placeholder);;

                Log.e("height feet", String.valueOf(heightFeetValue));
                Log.e("height inch", String.valueOf(heightInchesValue));
                Log.e("weight", String.valueOf(weightValue));
                Log.e("age", String.valueOf(ageValue));

                switch(activityLevelSpinnerSelection) {
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
                switch(genderSpinnerSelection) {
                    case 1: caloricNeed = (66 + (6.3 * weightValue) + (12.9 * ((heightFeetValue * 12) + heightInchesValue)) - (6.8 * ageValue)) * activityLevelValue;
                        break;
                    case 2: caloricNeed = (655 + (4.3 * weightValue) + (4.7 * ((heightFeetValue * 12) + heightInchesValue)) - (4.7 * ageValue)) * activityLevelValue;
                        break;
                }



                Log.e("calories", Integer.toString((int) caloricNeed));
                updateDailyCalorieSQL(userID,((int) caloricNeed));
                caloricNeedResult.setText(Integer.toString((int) caloricNeed));
            }
        });
    }

    private void updateDailyCalorieSQL(int userID, int caloricNeed) {
        Log.e("caloricNeedUpdate", Integer.toString((int) caloricNeed));
        String result = new SQLhelper(this).updateDailyCalorie(userID, caloricNeed);
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }


    class ActivityLevelSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            activityLevelSpinnerSelection = position;
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
    class GenderSpinnerClass implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            genderSpinnerSelection = position;
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}















