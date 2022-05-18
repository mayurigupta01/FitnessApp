package com.example.fitnessapp;

public class userProfile {

    public String email ;
    public String password;

    public userProfile(){
        //no arg const
    }

    public userProfile(String email , String password){
        this.email = email;
        this.password= password;
    }
}
