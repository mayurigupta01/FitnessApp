package com.example.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MainActivity extends AppCompatActivity {
     EditText email , password;

     String  s_email , s_password;

     private SQLhelper sqlhelper;


     public static String userID;
    public static DatabaseReference mDatabase;
    public static FirebaseDatabase database;

     public static List<CustomerModel> customerData ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("onCreate", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqlhelper = new SQLhelper(MainActivity.this);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("userProfile");

    }

    public void profilecreationActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ProfileCreation.class);
        startActivity(intent);
    }


    public void enterUserDetails(){
        s_email = email.getText().toString();
        s_password = password.getText().toString();
    }


    public void onClickLoginFromHome(View view){
        customerData = ReaderController.getCustomerModel(sqlhelper);
        Log.e("login", "login from home page");
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void onClickSignup(View view){
        enterUserDetails();
        userID = mDatabase.push().getKey();
        //creating customer object
        userProfile user = new userProfile(s_email, s_password);
        //pushing user to 'userprofile' node using the userId
        mDatabase.child(userID).setValue(user);
        Toast.makeText(MainActivity.this, "You have Registered successfully", Toast.LENGTH_SHORT).show();
        //direct to profile creation
        profilecreationActivity(view);
    }


}
