package com.example.fitnessapp;

import java.util.List;

public class ReaderController {

    public static List<CustomerModel> getCustomerModel(SQLhelper sqlHelper) {
         List<CustomerModel> getCustomerData = null;
         try{
             getCustomerData = sqlHelper.readCustomerData();
         }
         catch (Exception e) {
        //log the exception
          }
         return getCustomerData;
    }



}
