package com.example.OmerMoyalCouponProjectV2.threads;

import com.example.OmerMoyalCouponProjectV2.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
public class CouponExpirationDailyJob {
    @Autowired
    private CouponRepository couponRepository;


    @Scheduled(fixedDelayString = "PT30S")
    private void expireCouponDelete() {
        System.out.println("Started daily job");
        couponRepository.deleteCouponsByDate();

    }
}
