package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SQLhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fitnessdb";
    private static final int DB_VERSION = 1;
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
                    + DAILYCALORIE_COL + " TEXT)";

            // at last we are calling a exec sql
            // method to execute above sql query
            db.execSQL(query);
        }


    // this method is use to add new user to our SQLite Database.
    public void addNewUser(String name , String email , String gender , String weight , String height ,
                           String age , String sleepHrs ,String waterIntake , String physicalCondition ,
                           String mentalCondition, String dailyCalorieIntake) {

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

        // after adding all values we are passing content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
