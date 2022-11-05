package com.example.OmerMoyalCouponProjectV2;

import com.example.OmerMoyalCouponProjectV2.beans.*;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.loginManager.ClientType;
import com.example.OmerMoyalCouponProjectV2.loginManager.LoginManager;
import com.example.OmerMoyalCouponProjectV2.repositories.CouponRepository;
import com.example.OmerMoyalCouponProjectV2.services.AdminService;
import com.example.OmerMoyalCouponProjectV2.services.CompanyService;
import com.example.OmerMoyalCouponProjectV2.services.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class OmerMoyalCouponProjectV2Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(OmerMoyalCouponProjectV2Application.class, args);

    }
    @Bean
    public HashMap<String, OurSession> sessions(){

        return new HashMap<String, OurSession>();
    }
}
