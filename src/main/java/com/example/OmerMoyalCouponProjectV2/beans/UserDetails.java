package com.example.OmerMoyalCouponProjectV2.beans;

import com.example.OmerMoyalCouponProjectV2.loginManager.ClientType;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;



@Component


public class UserDetails {

    private int id;
    @Enumerated (value = EnumType.STRING)
    private ClientType clientType;
    private String email;
    private String password;

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDetails() {
    }

    public UserDetails(ClientType clientType, String email, String password) {
        this.clientType = clientType;
        this.email = email;
        this.password = password;
    }

    public UserDetails(int id, ClientType clientType, String email, String password) {
        this.id = id;
        this.clientType = clientType;
        this.email = email;
        this.password = password;
    }
}

