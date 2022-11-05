package com.example.OmerMoyalCouponProjectV2.services;

import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.repositories.CompaniesRepository;
import com.example.OmerMoyalCouponProjectV2.repositories.CouponRepository;
import com.example.OmerMoyalCouponProjectV2.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientService {
    @Autowired
    protected CompaniesRepository companiesRepository;
    @Autowired
    protected CouponRepository couponRepository;
    @Autowired
    protected CustomerRepository customerRepository;

    private int id;


    public abstract ClientService login(String mail, String password)throws CustomExceptions;
}