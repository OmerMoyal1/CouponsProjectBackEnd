package com.example.OmerMoyalCouponProjectV2.services;

import com.example.OmerMoyalCouponProjectV2.beans.Coupon;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService extends ClientService{
    @Override
    public ClientService login(String mail, String password) throws CustomExceptions {
        return null;
    }

    public GuestService() {
    }

    public List<Coupon>getAllCoupons(){
        return couponRepository.findAll();
    }
}
