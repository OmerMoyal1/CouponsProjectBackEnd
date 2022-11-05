package com.example.OmerMoyalCouponProjectV2.controllers;

import com.example.OmerMoyalCouponProjectV2.loginManager.ClientType;
import com.example.OmerMoyalCouponProjectV2.services.ClientService;

public abstract class ClientController {
    public abstract ClientService login(String email, String name,ClientType clientType);
}
