package com.example.OmerMoyalCouponProjectV2.exceptions;

public class CustomExceptions extends Exception{

    public CustomExceptions(ExceptionsLibrary enumExceptions){

        super(enumExceptions.getMessage());
    }

}
