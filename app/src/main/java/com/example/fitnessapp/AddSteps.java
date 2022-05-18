package com.example.fitnessapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class AddSteps extends AppCompatActivity {

    Button mySubmitButton, myDateButton;
    private EditText stepsValue;

    private List<CustomerModel> customerData = MainActivity.customerData;
    private String customerEmail = MainActivity.customerEmail;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_steps);

        stepsValue = findViewById(R.id.stepsValue);
        myDateButton = findViewById(R.id.stepsDateButton);
        mySubmitButton = findViewById(R.id.submitButton);

        myDateButton.setOnClickListener(view -> setStepsDate());
        mySubmitButton.setOnClickListener(view -> {
            String stepsValueStr = stepsValue.getText().toString().trim();
            String stepsDateStr = myDateButton.getText().toString().trim();

            for(int i = 0; i < customerData.size(); i++) {
                if(customerData.get(i).customerEmail.equalsIgnoreCase(customerEmail)) {
                    userID = customerData.get(i).user_id;
                }
            }

            if(stepsValueStr.isEmpty()) {
                Toast.makeText(AddSteps.this, "Enter Your Steps", Toast.LENGTH_SHORT).show();
            } else {
                if(stepsDateStr.equals("")) {
                    Toast.makeText(AddSteps.this, "Enter Date", Toast.LENGTH_SHORT).show();
                } else {
                    addSteps(userID, stepsValueStr, stepsDateStr);
                }
            }
        });
    }

    private void addSteps(int userID, String userSteps, String userStepsDate) {
        String result = new SQLhelper(this).addSteps(userID, userSteps, userStepsDate);
        stepsValue.setText("");
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }

    private void setStepsDate() {
        Calendar newStepsDate = Calendar.getInstance();
        int year = newStepsDate.get(Calendar.YEAR);
        int month = newStepsDate.get(Calendar.MONTH);
        int day = newStepsDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog stepsDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myDateButton.setText((month + 1) + "-" + day + "-" + year);
            }
        }, year, month, day);
        stepsDate.show();
    }
}
