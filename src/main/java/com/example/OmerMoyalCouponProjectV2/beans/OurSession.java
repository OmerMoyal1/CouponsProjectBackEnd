package com.example.OmerMoyalCouponProjectV2.beans;

import com.example.OmerMoyalCouponProjectV2.services.ClientService;

import java.time.LocalDateTime;

public class OurSession {
    private ClientService service;
    private LocalDateTime lastActive;

    public OurSession(ClientService service, LocalDateTime lastActive) {
        this.service = service;
        this.lastActive = lastActive;

    }

    public ClientService getService() {
        return service;
    }

    public void setService(ClientService service) {
        this.service = service;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }
}
