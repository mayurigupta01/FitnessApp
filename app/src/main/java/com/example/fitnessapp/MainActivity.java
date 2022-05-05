package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
     EditText name , email , gender , weight , height ,
             age , sleepHrs , waterIntake ,
             physicalCondition ,medicalCondition , dailyCalorieIntake;
     Button register;

     String s_name , s_email , s_gender , s_weight , s_height , s_age ,
             s_sleepHrs , s_waterIntake , s_physicalCondition ,
             s_medicalCondition , s_dailyCalorieIntake;

     private SQLhelper sqlhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("onCreate", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqlhelper = new SQLhelper(MainActivity.this);
        name =(EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        gender = (EditText) findViewById(R.id.gender);
        weight = (EditText) findViewById(R.id.weight);
        height = (EditText) findViewById(R.id.height) ;
        age = (EditText) findViewById(R.id.age);
        sleepHrs = (EditText) findViewById(R.id.sleepHours);
        waterIntake = (EditText) findViewById(R.id.water);
        physicalCondition = (EditText) findViewById(R.id.physicalCondition);
        medicalCondition = (EditText) findViewById(R.id.mentalCondition);
        dailyCalorieIntake = (EditText) findViewById(R.id.calorie);



    }

    public void onClickRegisterProfile(View view){
        Log.e("userDetails", "Enter user details");
        enterUserDetails();
        // validating if the text fields are empty or not.
        Log.e("userDetails","Validating Data");
        if (s_name.isEmpty() && s_email.isEmpty() && s_gender.isEmpty() && s_weight.isEmpty()
                && s_weight.isEmpty() && s_height.isEmpty() && s_age.isEmpty()
                && s_sleepHrs.isEmpty() && s_waterIntake.isEmpty() && s_dailyCalorieIntake.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter all the data ", Toast.LENGTH_SHORT).show();
            return;
        }
         else if(s_physicalCondition.isEmpty() && s_medicalCondition.isEmpty()  ) {
            Toast.makeText(MainActivity.this, "Please enter physical or mental condition orenter NA ", Toast.LENGTH_SHORT).show();
            return;
         }
        Log.e("userDetails", "Successful entry on form");
        Log.e("DBUpdate", "Store into DB");
        sqlhelper.addNewUser(s_name,s_email,s_gender,s_weight,s_height,s_age,s_sleepHrs,s_waterIntake,
                             s_physicalCondition,s_medicalCondition,s_dailyCalorieIntake);

        // after adding the data we are displaying a toast message.
        Toast.makeText(MainActivity.this, "You have Registered successfully", Toast.LENGTH_SHORT).show();

        //clear fields
        clearFields();

        //direct to login
        loginActivity(view);

    }

    public void loginActivity(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    public void enterUserDetails(){
        s_name = name.getText().toString();
        s_email = email.getText().toString();
        s_gender = gender.getText().toString();
        s_weight = weight.getText().toString();
        s_height = height.getText().toString();
        s_age = age.getText().toString();
        s_sleepHrs = sleepHrs.getText().toString();
        s_waterIntake = waterIntake.getText().toString();
        s_medicalCondition = medicalCondition.getText().toString();
        s_physicalCondition = physicalCondition.getText().toString();
        s_dailyCalorieIntake = dailyCalorieIntake.getText().toString();

    }

    public void clearFields(){
        name.setText("");
        email.setText("");
        gender.setText("");
        weight.setText("");
        height.setText("");
        age.setText("");
        waterIntake.setText("");
        sleepHrs.setText("");
        physicalCondition.setText("");
        medicalCondition.setText("");
        dailyCalorieIntake.setText("");
    }

}
