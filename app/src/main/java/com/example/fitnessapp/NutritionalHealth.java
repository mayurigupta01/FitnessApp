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


    private List<CustomerModel> customerData = ProfileCreation.customerData;
    private String customerEmail = ProfileCreation.customerEmail;

    // Async Task required to do HTTP calls, you can't do HTTP calls on main/UI thread in newer APIs
    MyAsyncTask1 myAsyncTask1 = new MyAsyncTask1();
    String receipeTitle ;
    String sourceUrl ;
    double calorie ;
    double protien ;
    double fats ;
    double carbohydrates;

    TextView cal, prot , fat , carbs;
    TextView recipe1 , receipe2 , receipe3 , url1 , url2 , url3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutritional_suggestion);

        int dailyCalorie = 0;
        double bmi = 0.0;

        for(int i = 0; i < customerData.size(); i++) {
            if(customerData.get(i).customerEmail.equalsIgnoreCase(customerEmail)) {
                dailyCalorie = customerData.get(i).dailyCalorie;
                bmi = Double.valueOf(customerData.get(i).customerbmi);
            }
        }

        cal = (TextView) findViewById(R.id.textsugg1);
        prot = (TextView) findViewById(R.id.textsugg2);
        fat = (TextView) findViewById(R.id.textsugg3);
        carbs = (TextView)  findViewById(R.id.textsugg4);

        recipe1 = (TextView) findViewById((R.id.textViewrecipe1));
        url1 = (TextView) findViewById(R.id.textViewurl1);

        receipe2 = (TextView) findViewById(R.id.textViewrecipe2);
        url2 = (TextView) findViewById(R.id.textViewurl2);

        receipe3 = (TextView) findViewById(R.id.textViewrecipe3);
        url3 = (TextView) findViewById(R.id.textViewurl3);



        for (int i = 0; i < customerData.size(); i++) {
            if (customerEmail.equalsIgnoreCase(customerData.get(i).customerEmail)) {
                try {
                    showData(bmi, dailyCalorie);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void showData(double bmi, int dailyCalorie) throws IOException, JSONException {
        String api_url = null;
        Log.e("NutritionActivity", String.valueOf(dailyCalorie));
        if (bmi < 18) {
            api_url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/mealplans/generate?timeFrame=day&targetCalories=" + dailyCalorie;
            Log.e("api_url", api_url);
            myAsyncTask1.execute(api_url);
        } else if (bmi >= 18 && bmi <= 25) {
            api_url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/mealplans/generate?timeFrame=day&targetCalories=" + dailyCalorie;
            Log.e("api_url", api_url);
            myAsyncTask1.execute(api_url);
        } else {
            api_url = "https://spoonacular-recipe-food-nutrition-v1.p.rapidapi.com/recipes/mealplans/generate?timeFrame=day&targetCalories=" + dailyCalorie;
            Log.e("api_url", api_url);
            myAsyncTask1.execute(api_url);
        }
    }


    public class MyAsyncTask1 extends AsyncTask<String, Void, Boolean> {

        private final OkHttpClient httpClient = new OkHttpClient();
        Response response = null;

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
            ArrayList<String> data = new ArrayList<>();
               try{
                String res = response.body() != null ? response.body().string() : null;
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("meals");

                for (int i = 0; i < jsonArray.length() ; i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                     receipeTitle = (String) object.get("title");
                     sourceUrl = (String) object.get("sourceUrl");
                     data.add(receipeTitle);
                     data.add(sourceUrl);
                }
                     JSONObject nutrientObject = (JSONObject) jsonObject.get("nutrients");

                     calorie = (double) nutrientObject.get("calories");
                     protien = (double) nutrientObject.get("protein");
                     fats = (double) nutrientObject.get("fat");
                     carbohydrates = (double) nutrientObject.get("carbohydrates");
                     System.out.println(data.toString());
                     System.out.println(calorie);
                     System.out.println(protien);
                     System.out.println(fats);
                     System.out.println(carbohydrates);

                     //show on UI
                     cal.setText(Double.toString(calorie));
                     prot.setText(Double.toString(protien));
                     fat.setText(Double.toString(fats));
                     carbs.setText(Double.toString(carbohydrates));

                     //show recipes on UI
                       recipe1.setText(data.get(0));
                       String urlrec1 = data.get(1);
                       url1.setText(urlrec1);



                        String urlrec2 = data.get(3);
                       receipe2.setText(data.get(2));
                       url2.setText(data.get(3));


                       String urlrec3 = data.get(5);
                       receipe3.setText(data.get(4));
                       url3.setText(data.get(5));


            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}



