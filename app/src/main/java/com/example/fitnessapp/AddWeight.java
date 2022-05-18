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

public class AddWeight extends AppCompatActivity {
    Button mySubmitButton, myDateButton;
    private EditText weightValue, calorieValue;

    private List<CustomerModel> customerData = ProfileCreation.customerData;
    private String customerEmail = ProfileCreation.customerEmail;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);

        weightValue = findViewById(R.id.weightValue);
        calorieValue = findViewById(R.id.calorieValue);
        myDateButton = findViewById(R.id.weightDateButton);
        mySubmitButton = findViewById(R.id.submitButton);

        myDateButton.setOnClickListener(view -> setWeightDate());
        mySubmitButton.setOnClickListener(view -> {
            String weightValueStr = weightValue.getText().toString().trim();
            String calorieValueStr = calorieValue.getText().toString().trim();
            String weightDataStr = myDateButton.getText().toString().trim();

            for(int i = 0; i < customerData.size(); i++) {
                if(customerData.get(i).customerEmail.equalsIgnoreCase(customerEmail)) {
                    userID = customerData.get(i).user_id;
                }
            }

            if(weightValueStr.isEmpty() || calorieValueStr.isEmpty()) {
                Toast.makeText(AddWeight.this, "Enter Your Weight/Calories", Toast.LENGTH_SHORT).show();
            } else {
                if(weightDataStr.equals("")) {
                    Toast.makeText(AddWeight.this, "Enter Date", Toast.LENGTH_SHORT).show();
                } else {
                    addWeight(userID, weightValueStr, calorieValueStr, weightDataStr);
                }
            }
        });
    }

    private void addWeight(int userID, String userWeight, String userCalorie, String userWeightDate) {
        String result = new SQLhelper(this).addWeight(userID, userWeight, userCalorie, userWeightDate);
        weightValue.setText("");
        calorieValue.setText("");
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
    }

//    private void setWeightDate() {
//        Calendar newWeightDate = Calendar.getInstance();
//
//        DatePickerDialog weightDate = new DatePickerDialog(this, (datePicker, year, month, day) -> myDateButton.setText((newWeightDate.get(Calendar.MONTH) + 1)
//                + "-" + newWeightDate.get(Calendar.DAY_OF_MONTH)
//                + "-" + newWeightDate.get(Calendar.YEAR)), newWeightDate.get(Calendar.YEAR), newWeightDate.get(Calendar.MONTH), newWeightDate.get(Calendar.DAY_OF_MONTH));
//        weightDate.show();
//    }

    private void setWeightDate() {
        Calendar newWeightDate = Calendar.getInstance();
        int year = newWeightDate.get(Calendar.YEAR);
        int month = newWeightDate.get(Calendar.MONTH);
        int day = newWeightDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog weightDate = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myDateButton.setText((month + 1) + "-" + day + "-" + year);
            }
        }, year, month, day);
        weightDate.show();
    }
}
