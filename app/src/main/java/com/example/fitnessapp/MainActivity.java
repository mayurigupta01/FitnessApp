package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
     EditText name , email , gender , weight , height ,
             age , sleepHrs , waterIntake ,
             physicalCondition ,medicalCondition , dailyCalorieIntake , password;
     Button register;

     String s_name , s_email , s_gender , s_weight , s_height , s_age ,
             s_sleepHrs , s_waterIntake , s_physicalCondition ,
             s_medicalCondition , s_dailyCalorieIntake  , s_bmi,s_password;


     private SQLhelper sqlhelper;

     public static List<CustomerModel> customerData ;
     public static String customerName;
     public static String customerEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("onCreate", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqlhelper = new SQLhelper(MainActivity.this);
        name =(EditText) findViewById(R.id.editTextTextPersonName);
        email = (EditText) findViewById(R.id.editTextTextEmail2);
        gender = (EditText) findViewById(R.id.editTextTextGender);
        weight = (EditText) findViewById(R.id.editTextTextWeight);
        height = (EditText) findViewById(R.id.editTextTextHeight) ;
        age = (EditText) findViewById(R.id.editTextTextAge);
        sleepHrs = (EditText) findViewById(R.id.editTextTextSleep);
        waterIntake = (EditText) findViewById(R.id.editTextTextWaterIntake);
        physicalCondition = (EditText) findViewById(R.id.editTextTextPhysicalCondition);
        medicalCondition = (EditText) findViewById(R.id.editTextTextMentalCondition);
        dailyCalorieIntake = (EditText) findViewById(R.id.editTextTextCalorieIntake);
        password = (EditText) findViewById(R.id.editTextTextPassword);


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
                             s_physicalCondition,s_medicalCondition,s_dailyCalorieIntake,s_bmi, s_password);

        // after adding the data we are displaying a toast message.
        Toast.makeText(MainActivity.this, "You have Registered successfully", Toast.LENGTH_SHORT).show();

        //clear fields
        clearFields();

        //get the customer data
        customerData = ReaderController.getCustomerModel(sqlhelper);

        //direct to login
        loginActivity(view);



    }

    public void loginActivity(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    public void enterUserDetails(){
        s_name = name.getText().toString();
        customerName = s_name;
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
        int bmi =  Integer.parseInt(s_weight) * 703 / ( Integer.parseInt(s_height ) * Integer.parseInt(s_height));
        s_bmi = Integer.toString(bmi);
        s_password = password.getText().toString();


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
        password.setText("");
    }


    public void onClickLoginFromHome(View view){
        customerData = ReaderController.getCustomerModel(sqlhelper);
        Log.e("login", "login from home page");
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);

    }

}
