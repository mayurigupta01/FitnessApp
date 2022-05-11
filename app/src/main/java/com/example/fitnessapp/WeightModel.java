package com.example.fitnessapp;

public class WeightModel {
    String weightValue, calorieValue, weightDateValue;

    public WeightModel() {}

    public WeightModel(String weightValue, String calorieValue, String weightDateValue) {
        this.weightValue = weightValue;
        this.calorieValue = calorieValue;
        this.weightDateValue = weightDateValue;
    }

    public void setWeightValue(String activityName) { this.weightValue = weightValue; }

    public void setCalorieValue(String calorieValue) { this.calorieValue = calorieValue; }

    public void setWeightDateValue(String activityDate) {
        this.weightDateValue = weightDateValue;
    }

    public String getWeightValue() {
        return weightValue;
    }

    public String getCalorieValue() { return calorieValue; }

    public String getWeightDateValue() {
        return weightDateValue;
    }
}
