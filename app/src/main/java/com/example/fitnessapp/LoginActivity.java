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

    private List<CustomerModel> customerDetails = MainActivity.customerData;


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
        MainActivity.customerEmail = s_email;
        s_pass = password.getText().toString();

        // validating if email is correct
        Log.e("loginDetails", "Validating login data");
        for (int i = 0; i < customerDetails.size(); i++) {
            System.out.println("Reading customer data");
            //set the customer name
            MainActivity.customerName = customerDetails.get(i).customerName;
            System.out.println(MainActivity.customerName);
            String email = customerDetails.get(i).customerEmail;
            System.out.println(email);
            String pass = customerDetails.get(i).customerPassword;
            System.out.println(pass);
            if (email.equalsIgnoreCase(s_email) && pass.equalsIgnoreCase(s_pass)) {
                Toast.makeText(LoginActivity.this, "You have successfully logged In", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(LoginActivity.this, "Invalid email and password combination , please try again", Toast.LENGTH_SHORT).show();
            }

        }
        //clear field and go to Health DashBoard
        email.setText("");
        password.setText("");
        showHealthDashBoard(view);


    }


    public void showHealthDashBoard(View view ) {
        Intent intent = new Intent(LoginActivity.this, HealthDashBoard.class);
        startActivity(intent);
    }
}

