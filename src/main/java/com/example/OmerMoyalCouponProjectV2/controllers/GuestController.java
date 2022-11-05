package com.example.OmerMoyalCouponProjectV2.controllers;

import com.example.OmerMoyalCouponProjectV2.beans.Coupon;
import com.example.OmerMoyalCouponProjectV2.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/guest")
public class GuestController {
    @Autowired
    private GuestService guestService;

    /**
     * returns all the coupons from the d.b to the client no auth required this is for the home page
     * so client can see it without the need to login
     * @return list of all the Coupons
     */
    @GetMapping
    public List<Coupon> allCoupon(){
        return guestService.getAllCoupons();
    }
}
