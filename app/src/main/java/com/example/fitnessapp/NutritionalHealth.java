package com.example.fitnessapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NutritionalHealth extends AppCompatActivity {

    private List<CustomerModel> customerData = MainActivity.customerData;
    private String customerEmail = MainActivity.customerEmail;
    // Async Task required to do HTTP calls, you can't do HTTP calls on main/UI thread in newer APIs
    MyAsyncTask1 myAsyncTask1 = new MyAsyncTask1();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrional_suggestion);
        for (int i = 0; i < customerData.size(); i++) {
            if (customerEmail.equalsIgnoreCase(customerData.get(i).customerEmail)) {
                try {
                    showData(customerData.get(i).customerbmi);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void showData(String bmi) throws IOException, JSONException {
        int BMI = 5;
        String api_url = null;
        if (BMI < 18) {
            api_url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/mealplans/generate?timeFrame=day&targetCalories=2000&diet=vegan";
            myAsyncTask1.execute(api_url);
        } else if (BMI >= 18 && BMI <= 25) {
            api_url = "";
        } else {
            api_url = "";
        }
    }


    public class MyAsyncTask1 extends AsyncTask<String, Void, Boolean> {

        private final OkHttpClient httpClient = new OkHttpClient();
        Response response = null;
        ArrayList<Object> nutritionList = new ArrayList<>();

        @Override
        protected Boolean doInBackground(String... url) {
            Request request = new Request.Builder()
                    .url(url[0])
                    .get()
                    .addHeader("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
                    .addHeader("X-RapidAPI-Key", "8fdcd02512mshb6e76ebcbf3ac79p190bc4jsnf0a5ef37367a")
                    .build();
            Call call = httpClient.newCall(request);
            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.code() == 200) {
                Log.e("success", "200");
                return true;
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            try {
                String res = response.body().string();
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("meals");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    System.out.println(object);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



