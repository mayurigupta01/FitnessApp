package com.example.fitnessapp;

public class ActivitiesModel {
    String activity, date, time;

    public ActivitiesModel(String activity, String date, String time) {
        this.activity = activity;
        this.date = date;
        this.time = time;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getActivity() {
        return activity;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
