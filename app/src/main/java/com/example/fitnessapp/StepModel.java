package com.example.fitnessapp;

public class StepModel {
    String stepsValue, stepsDateValue;

    public StepModel() {}

    public StepModel(String stepsValue, String stepsDateValue) {
        this.stepsValue = stepsValue;
        this.stepsDateValue = stepsDateValue;
    }

    public void setStepsValue(String stepsValue) { this.stepsValue = stepsValue; }

    public void setStepsDateValue(String stepsDateValue) { this.stepsDateValue = stepsDateValue; }

    public String getStepsValue() {
        return stepsValue;
    }

    public String getStepsDateValue() { return stepsDateValue; }
}

