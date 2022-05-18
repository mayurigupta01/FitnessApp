package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SQLhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fitnessdb";
    private static final int DB_VERSION = 1;

    // Table and Columns for Customer Health Activities
    private static final String TABLE_ACTIVITIES = "activities";
    private static final String ACTIVITY_ID_COL = "activityID";
    private static final String ACTIVITY_COL = "activity";
    private static final String ACTIVITY_DATE_COL = "activityDate";
    private static final String ACTIVITY_TIME_COL = "activityTime";

    private static final String TABLE_NAME = "customers";
    //define columns for Customer profile
    private static final String ID_COL = "user_id";
    private static final String NAME_COL = "name";
    private static final String EMAIL_COL = "email";
    private static final String GENDER_COL = "gender";
    private static final String WEIGHT_COL = "weight";
    private static final String HEIGHT_COL = "height";
    private static final String AGE_COL = "age";
    private static final String SLEEP_COL = "sleep";
    private static final String WATERINTAKE_COL = "waterintake";
    private static final String PHYSICALCONDITION_COL = "physicalcondition";
    private static final String MENTALCONDITION_COL = "mentalcondition";
    private static final String DAILYCALORIE_COL = "dailycalorie";
    private static final String PASSWORD_COL = "password";
    private static final String BMI_COL = "bmi";
    private static final String DAILY_CALORIE_COL = "dailyCalorieCalculation";

    private static final String TABLE_WEIGHT = "weight";
    private static final String WEIGHT_DATA_COL = "userWeight";
    private static final String CALORIE_DATA_COL = "userCalorie";
    private static final String WEIGHT_DATE_COL = "userWeightDate";

    private static final String TABLE_DIET_DATA = "dietsuggestion";
    private static final String SUGG_ID_COL = "suggestionid";
    private static final String REC_TITLE_COL = "recepietitle";
    private static final String SOURCE_URL_COL = "sourceurl";

    private static final String TABLE_STEPS = "steps";
    private static final String STEPS_COL = "userSteps";
    private static final String STEPS_DATE_COL = "userStepDate";

//    private static final String TABLE_DAILY_CALORIE = "calorie";
//    private static final String DAILY_CALORIE_COL = "dailyCalorie";


    public SQLhelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    //create a Database


    @Override
    public void onCreate(SQLiteDatabase db) {
            // on below line we are creating
            // an sqlite query and we are
            // setting our column names
            // along with their data types.
            String query = "CREATE TABLE " + TABLE_NAME + " ("
                    + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME_COL + " TEXT,"
                    + EMAIL_COL + " TEXT,"
                    + GENDER_COL + " TEXT,"
                    + WEIGHT_COL + " TEXT,"
                    + HEIGHT_COL + " TEXT,"
                    + AGE_COL + " TEXT,"
                    + SLEEP_COL + " TEXT,"
                    + WATERINTAKE_COL + " TEXT,"
                    + PHYSICALCONDITION_COL + " TEXT,"
                    + MENTALCONDITION_COL + " TEXT,"
                    + DAILYCALORIE_COL + " TEXT,"
                    + BMI_COL + " TEXT,"
                    + PASSWORD_COL +" TEXT,"
                    + DAILY_CALORIE_COL + " INTEGER DEFAULT 0)";

            String activityQuery = "CREATE TABLE " + TABLE_ACTIVITIES + " ("
                    //+ ACTIVITY_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ID_COL + " TEXT,"
                    + ACTIVITY_COL + " TEXT,"
                    + ACTIVITY_DATE_COL + " TEXT,"
                    + ACTIVITY_TIME_COL + " TEXT,"
                    + " FOREIGN KEY(" + ID_COL + ") REFERENCES " + TABLE_NAME + "(" + ID_COL + "))";

            String weightCalorieQuery = "CREATE TABLE " + TABLE_WEIGHT + " ("
                    + ID_COL + " TEXT,"
                    + WEIGHT_DATA_COL + " TEXT,"
                    + CALORIE_DATA_COL + " TEXT,"
                    + WEIGHT_DATE_COL + " TEXT,"
                    + " FOREIGN KEY(" + ID_COL + ") REFERENCES " + TABLE_NAME + "(" + ID_COL + "))";

            String dietSuggestionQuery = "CREATE TABLE " + TABLE_DIET_DATA + " ("
                + SUGG_ID_COL + " TEXT,"
                + REC_TITLE_COL + " TEXT,"
                + SOURCE_URL_COL + " TEXT)";

            String stepQuery = "CREATE TABLE " + TABLE_STEPS + " ("
                + ID_COL + " TEXT,"
                + STEPS_COL + " TEXT,"
                + STEPS_DATE_COL + " TEXT,"
                + " FOREIGN KEY(" + ID_COL + ") REFERENCES " + TABLE_NAME + "(" + ID_COL + "))";

//            String dailyCalorieQuery = "CREATE TABLE " + TABLE_DAILY_CALORIE + " ("
//                    + ID_COL + " TEXT,"
//                    + DAILY_CALORIE_COL + " INTEGER DEFAULT 0,"
//                    + " FOREIGN KEY(" + ID_COL + ") REFERENCES " + TABLE_NAME + "(" + ID_COL + "))";

            // at last we are calling a exec sql
            // method to execute above sql query
            db.execSQL(query);
            db.execSQL(activityQuery);
            db.execSQL(weightCalorieQuery);
            db.execSQL(dietSuggestionQuery);
            db.execSQL(stepQuery);
//            db.execSQL(dailyCalorieQuery);
        }


    // this method is use to add new user to our SQLite Database.
    public void addNewUser(String name , String email , String gender , String weight , String height ,
                           String age , String sleepHrs ,String waterIntake , String physicalCondition ,
                           String mentalCondition, String dailyCalorieIntake , String bmi , String pass) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method as we update data to db.
        SQLiteDatabase db = this.getWritableDatabase();

        // creating a variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all value along with its key and value pair.
        values.put(NAME_COL, name);
        values.put(EMAIL_COL, email);
        values.put(GENDER_COL, gender);
        values.put(WEIGHT_COL, weight);
        values.put(HEIGHT_COL, height);
        values.put(AGE_COL, age);
        values.put(SLEEP_COL, sleepHrs);
        values.put(WATERINTAKE_COL, waterIntake);
        values.put(PHYSICALCONDITION_COL, physicalCondition);
        values.put(MENTALCONDITION_COL, mentalCondition);
        values.put(DAILYCALORIE_COL, dailyCalorieIntake);
        values.put(BMI_COL,bmi);
        values.put(PASSWORD_COL,pass);

        // after adding all values we are passing content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }


    //add diet suggestion data to DB

    // Method to add a new health activity
    public String addHealthActivity(int userID, String activityName, String activityDate, String activityTime) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ID_COL, userID);
        values.put(ACTIVITY_COL, activityName);
        values.put(ACTIVITY_DATE_COL, activityDate);
        values.put(ACTIVITY_TIME_COL, activityTime);

        long result = db.insert(TABLE_ACTIVITIES, null, values);

        db.close();

        if(result == -1) {
            return "Failed to add activity";
        } else {
            return "Successfully added activity";
        }
    }

    public Cursor getActivities(int userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String activityQuery = "SELECT * FROM " + TABLE_ACTIVITIES + " WHERE user_id = " + userID;
        Cursor cursor = db.rawQuery(activityQuery, null);
        return cursor;
    }

    // Method to add a new weight, calories and date
    public String addWeight(int userID, String userWeight, String userCalorie, String userWeightDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ID_COL, userID);
        values.put(WEIGHT_DATA_COL, userWeight);
        values.put(CALORIE_DATA_COL, userCalorie);
        values.put(WEIGHT_DATE_COL, userWeightDate);

        long result = db.insert(TABLE_WEIGHT, null, values);

        db.close();

        if(result == -1) {
            return "Failed to add weight/calorie data";
        } else {
            return "Successfully added weight/calorie data";
        }
    }

    public Cursor getWeightData(int userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String weightQuery = "SELECT * FROM " + TABLE_WEIGHT + " WHERE user_id = " + userID + " ORDER BY " + WEIGHT_DATE_COL + " ASC";
        Cursor cursor = db.rawQuery(weightQuery, null);
        return cursor;
    }

    public String updateDailyCalorie(int userID, int dailyCalorie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Log.e("dailycalorie", String.valueOf(dailyCalorie));

        values.put(DAILY_CALORIE_COL, dailyCalorie);

        long result = db.update(TABLE_NAME, values, ID_COL + " = ?", new String[]{String.valueOf(userID)});

        db.close();

        if(result == -1) {
            return "Failed to update daily calorie data";
        } else {
            return "Successfully updated daily calorie data";
        }
    }

    // Method to add a steps and date
    public String addSteps(int userID, String userSteps, String userStepsDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ID_COL, userID);
        values.put(STEPS_COL, userSteps);
        values.put(STEPS_DATE_COL, userStepsDate);

        long result = db.insert(TABLE_STEPS, null, values);

        db.close();

        if(result == -1) {
            return "Failed to add steps data";
        } else {
            return "Successfully added steps data";
        }
    }

    public Cursor getStepData(int userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        String stepsQuery = "SELECT * FROM " + TABLE_STEPS + " WHERE user_id = " + userID + " ORDER BY " + STEPS_DATE_COL + " ASC";
        Cursor cursor = db.rawQuery(stepsQuery, null);
        return cursor;
    }

//    public String addDailyCalorie(int userID, int dailyCalorie) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(ID_COL, userID);
//        values.put(DAILY_CALORIE_COL, dailyCalorie);
//
//        long result = db.insert(TABLE_DAILY_CALORIE, null, values);
//
//        db.close();
//
//        if(result == -1) {
//            return "Failed to add daily calorie data";
//        } else {
//            return "Successfully added daily calorie data";
//        }
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIET_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEPS);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILY_CALORIE);
        onCreate(db);
    }


    public ArrayList<CustomerModel> readCustomerData(){
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println(db);
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME
                , null);
        // on below line we are creating a new array list.
        ArrayList<CustomerModel> customerModelArrayList = new ArrayList<>();
        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                customerModelArrayList.add(new CustomerModel(cursorCourses.getInt(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4),
                        cursorCourses.getString(5),
                        cursorCourses.getString(6),
                        cursorCourses.getString(7),
                        cursorCourses.getString(8),
                        cursorCourses.getString(9),
                        cursorCourses.getString(10),
                        cursorCourses.getString(11),
                        cursorCourses.getString(12),
                        cursorCourses.getString(13),
                        cursorCourses.getInt(14)));
            }
            while (cursorCourses.moveToNext()) ;
        }
        // at last closing our cursor
        // and returning our array list.
        System.out.println(customerModelArrayList);
        cursorCourses.close();
        return customerModelArrayList;

    }



}
