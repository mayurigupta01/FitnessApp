//package com.example.fitnessapp;
//
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.TextView;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Objects;
//
//import okhttp3.Call;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class MyAsyncTask extends AsyncTask<String, Void, Boolean> {
//
//    private final OkHttpClient httpClient = new OkHttpClient();
//    Response response = null;
//    ArrayList<Object> nutritionList = new ArrayList<>();
//
//    @Override
//    protected Boolean doInBackground(String... url) {
//        Request request = new Request.Builder()
//                .url(url[0])
//                .get()
//                .addHeader("X-RapidAPI-Host", "spoonacular-recipe-food-nutrition-v1.p.rapidapi.com")
//                .addHeader("X-RapidAPI-Key", "8fdcd02512mshb6e76ebcbf3ac79p190bc4jsnf0a5ef37367a")
//                .build();
//        Call call = httpClient.newCall(request);
//        try {
//            response = call.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if(response.code() == 200) {
//            Log.e("success", "200");
//            return true;
//        }
//        return false;
//    }
//
//    protected void onPostExecute(Boolean result) {
//        try {
//            String res = response.body().string();
//            JSONObject jsonObject = new JSONObject(res);
//            JSONArray jsonArray = jsonObject.getJSONArray("meals");
//            for(int i = 0; i < jsonArray.length(); i++) {
//                JSONObject object = jsonArray.getJSONObject(i);
//                System.out.println(object.get("id"));
//            }
//            //System.out.println(jsonObject.get("id"));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
