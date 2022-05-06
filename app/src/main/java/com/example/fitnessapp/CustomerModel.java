package com.example.fitnessapp;

public class CustomerModel {

    public int user_id;
    public String customerName;
    public String customerEmail;
    public String customerGender;
    public String customerWeight;
    public String customerHeight;
    public String customerAge;
    public String customerSleep;
    public String customerWaterIntake;
    public String customerPhysicalCondition;
    public String customerMedicalCondition;
    public String customerDailyCalorieIntake;
    public String customerPassword;




    public CustomerModel(int user_id, String customerName, String customerEmail,
                         String customerGender, String customerWeight, String customerHeight,
                         String customerAge, String customerSleep, String customerWaterIntake,
                         String customerPhysicalCondition, String customerMedicalCondition, String customerDailyCalorieIntake, String customerPassword) {
        this.user_id = user_id;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerGender = customerGender;
        this.customerWeight = customerWeight;
        this.customerHeight = customerHeight;
        this.customerAge = customerAge;
        this.customerSleep = customerSleep;
        this.customerWaterIntake = customerWaterIntake;
        this.customerPhysicalCondition = customerPhysicalCondition;
        this.customerMedicalCondition = customerMedicalCondition;
        this.customerDailyCalorieIntake = customerDailyCalorieIntake;
        this.customerPassword = customerPassword;
    }



}

