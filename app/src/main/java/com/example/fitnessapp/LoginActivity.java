package com.example.fitnessapp;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;

    String s_email, s_pass;

    private List<CustomerModel> customerDetails = ProfileCreation.customerData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("onCreate", "onCreate Login");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //login and verify user details.
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);


    }

    //Login and see the Health DashBoard
    public void onClickLogin(View view) {
        Log.e("loginDetails", "login into app");
        s_email = email.getText().toString();
        ProfileCreation.customerEmail = s_email;
        s_pass = password.getText().toString();

        // validating if email is correct


        Log.e("loginDetails", "Validating login data");
        for (int i = 0; i < customerDetails.size(); i++) {
            System.out.println("Reading customer data");
            //set the customer name
            ProfileCreation.customerName = customerDetails.get(i).customerName;
            System.out.println(ProfileCreation.customerName);
            String email = customerDetails.get(i).customerEmail;
            System.out.println(email);

            //validate Login with Firebase authentication


            if (email.equalsIgnoreCase(s_email)) {
                Toast.makeText(LoginActivity.this, "You have successfully logged In", Toast.LENGTH_SHORT).show();
                showHealthDashBoard(view);
            } else {

                Toast.makeText(LoginActivity.this, "Invalid email and password combination , please try again", Toast.LENGTH_LONG).show();
                GobackToMain(view);
            }

        }

    }


    public void showHealthDashBoard(View view ) {
        email.setText("");
        password.setText("");
        Intent intent = new Intent(LoginActivity.this, HealthDashBoard.class);
        startActivity(intent);
    }


    public void GobackToMain(View view ) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }


}

