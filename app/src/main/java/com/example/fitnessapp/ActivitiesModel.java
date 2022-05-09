package com.example.fitnessapp;

public class ActivitiesModel {
    String activityName, activityDate, activityTime;

    public ActivitiesModel() {}

    public ActivitiesModel(String activityName, String activityDate, String activityTime) {
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.activityTime = activityTime;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivity() {
        return activityName;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public String getActivityTime() {
        return activityTime;
    }
}
