package com.example.fitnessapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ProfileCreation  extends AppCompatActivity {

    EditText name , em, gender , weight , height ,
            age , sleepHrs , waterIntake ,
            physicalCondition ,medicalCondition , dailyCalorieIntake , ps;


    String s_name , s_email , s_gender , s_weight , s_height , s_age ,
            s_sleepHrs , s_waterIntake , s_physicalCondition ,
            s_medicalCondition , s_dailyCalorieIntake  , s_bmi,s_password;

    public static BMI b;
    MyAsyncTask2 myAsyncTask2 = new MyAsyncTask2();

    private FirebaseAuth mAuth;
    private SQLhelper sqlhelper;

    public static List<CustomerModel> customerData ;
    public static String customerName;
    public static String customerEmail;
    public static DatabaseReference mDatabase;
    public static FirebaseDatabase database;
    public static FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("onCreate", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sqlhelper = new SQLhelper(ProfileCreation.this);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("CustomerModel");
        mAuth = FirebaseAuth.getInstance();
        name =(EditText) findViewById(R.id.editTextTextPersonName);
        em = (EditText) findViewById(R.id.editTextTextEmail2);
        gender = (EditText) findViewById(R.id.editTextTextGender);
        weight = (EditText) findViewById(R.id.editTextTextWeight);
        height = (EditText) findViewById(R.id.editTextTextHeight) ;
        age = (EditText) findViewById(R.id.editTextTextAge);
        sleepHrs = (EditText) findViewById(R.id.editTextTextSleep);
        waterIntake = (EditText) findViewById(R.id.editTextTextWaterIntake);
        physicalCondition = (EditText) findViewById(R.id.editTextTextPhysicalCondition);
        medicalCondition = (EditText) findViewById(R.id.editTextTextMentalCondition);
        dailyCalorieIntake = (EditText) findViewById(R.id.editTextTextCalorieIntake);


    }


    public void onClickRegisterProfile(View view){
        Log.e("userDetails", "Enter user details");
        enterUserDetails();
        // validating if the text fields are empty or not.
        Log.e("userDetails","Validating Data");
        if (s_name.isEmpty() && s_email.isEmpty() && s_gender.isEmpty() && s_weight.isEmpty()
                && s_weight.isEmpty() && s_height.isEmpty() && s_age.isEmpty()
                && s_sleepHrs.isEmpty() && s_waterIntake.isEmpty() && s_dailyCalorieIntake.isEmpty()) {
            Toast.makeText(ProfileCreation.this, "Please enter all the data ", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(s_physicalCondition.isEmpty() && s_medicalCondition.isEmpty()  ) {
            Toast.makeText(ProfileCreation.this, "Please enter physical or mental condition orenter NA ", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("userDetails", "Successful entry on form");
        Log.e("DBUpdate", "Store into DB");
        sqlhelper.addNewUser(s_name,s_email,s_gender,s_weight,s_height,s_age,s_sleepHrs,s_waterIntake,
                s_physicalCondition,s_medicalCondition,s_dailyCalorieIntake,s_bmi, "test123");
        String userId = mDatabase.push().getKey();
        //creating customer object
        CustomerModel customerModel = new CustomerModel(s_name,s_email,s_gender,s_weight,s_height,s_age,s_sleepHrs,s_waterIntake,
                s_physicalCondition,s_medicalCondition,s_dailyCalorieIntake,s_bmi);
        //pushing user to 'customer' node using the userId
        mDatabase.child(userId).setValue(customerModel);

        // after adding the data we are displaying a toast message.
        Toast.makeText(ProfileCreation.this, "You have created profile successfully", Toast.LENGTH_SHORT).show();

        //clear fields
        clearFields();

        //get the customer data
        customerData = ReaderController.getCustomerModel(sqlhelper);

        //direct to health dashboard
        showHealthDashBoard(view);

    }


    public void enterUserDetails(){
        s_name = name.getText().toString();
        customerName = s_name;
        s_email = em.getText().toString();
        customerEmail = s_email;
        s_gender = gender.getText().toString();
        s_weight = weight.getText().toString();
        s_height = height.getText().toString();
        s_age = age.getText().toString();
        s_sleepHrs = sleepHrs.getText().toString();
        s_waterIntake = waterIntake.getText().toString();
        s_medicalCondition = medicalCondition.getText().toString();
        s_physicalCondition = physicalCondition.getText().toString();
        s_dailyCalorieIntake = dailyCalorieIntake.getText().toString();

        //find bmi
        myAsyncTask2.execute();

        //int bmi =  Integer.parseInt(s_weight) * 703 / ( Integer.parseInt(s_height ) * Integer.parseInt(s_height));
        s_bmi = Float.toString(b.bmi);


    }

    public void showHealthDashBoard(View view ) {
        Intent intent = new Intent(ProfileCreation.this, HealthDashBoard.class);
        startActivity(intent);
    }

    public void clearFields(){
        name.setText("");
        em.setText("");
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

    public class MyAsyncTask2 extends AsyncTask<String, Void, Boolean> {

        private final OkHttpClient httpClient = new OkHttpClient();
        ResponseBody response = null;

        @Override
        protected Boolean doInBackground(String... url) {

            String json = "{\"height\":163,\"weight\":155}";
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json);
            Request request = new Request.Builder()
                    .url("https://kb3xg6iphe.execute-api.us-west-2.amazonaws.com/dev/bmi")
                    .post(body)
                    .build();
            Call call = httpClient.newCall(request);
            try {
                response = call.execute().body();
                ObjectMapper objectMapper = new ObjectMapper();
                b = objectMapper.readValue(response.string() , BMI.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

}
