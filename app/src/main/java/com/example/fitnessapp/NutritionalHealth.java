package com.example.fitnessapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;



import java.io.IOException;

import java.net.HttpURLConnection;

import java.net.URL;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NutritionalHealth extends AppCompatActivity {

    private List<CustomerModel> customerData = MainActivity.customerData;
    private String customerEmail = MainActivity.customerEmail;
    private final OkHttpClient httpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nutrional_suggestion);
        for(int i =0 ; i<customerData.size();i++){
            if(customerEmail.equalsIgnoreCase(customerData.get(i).customerEmail)) {
                    showData(customerData.get(i).customerbmi);
                     break;
                }
            }
        }

    public void showData(String bmi) {
        System.out.println(bmi);
        int BMI = Integer.parseInt(bmi);
        System.out.println(BMI);
        String api_url = null;
        if (BMI < 18) {
            api_url = "https://api.spoonacular.com/recipes/findByNutrients?apiKey=5eea77c2389843fb9f350f1988193b27&minCarbs=40";
            Request request = new Request.Builder()
                    .url(api_url)
                    .build();
            try (Response response = httpClient.newCall(request).execute()) {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                // Get response body
                System.out.println(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else if (BMI >= 18 && BMI <= 25) {
            api_url = "https://api.spoonacular.com/recipes/findByNutrients?apiKey=5eea77c2389843fb9f350f1988193b27&minProtein=50";
            Request request = new Request.Builder()
                    .url(api_url)
                    .build();
            try (Response response = httpClient.newCall(request).execute()) {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                // Get response body
                System.out.println(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    else {
        api_url = "https://api.spoonacular.com/recipes/findByNutrients?apiKey=5eea77c2389843fb9f350f1988193b27&maxFat=40";
            Request request = new Request.Builder()
                    .url(api_url)
                    .build();
            try (Response response = httpClient.newCall(request).execute()) {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                // Get response body
                System.out.println(response.body().string());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        }
    }



