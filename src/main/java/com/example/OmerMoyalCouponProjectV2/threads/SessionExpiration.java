package com.example.OmerMoyalCouponProjectV2.threads;

import com.example.OmerMoyalCouponProjectV2.beans.OurSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;

@Component
@Configuration
@EnableScheduling

public class SessionExpiration {

    @Autowired
    private HashMap<String, OurSession> sessions;

    @Scheduled(fixedDelayString = "PT20S")
    private void SessionDelete() {
        System.out.println(sessions);
        //For Testing
        sessions.entrySet().removeIf(f -> f.getValue().getLastActive().plusMinutes(2).isBefore(LocalDateTime.now()));
        //For build
//        sessions.entrySet().removeIf(f -> f.getValue().getLastActive().plusMinutes(30).isBefore(LocalDateTime.now()));
        System.out.println(sessions);


    }
}
