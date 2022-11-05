package com.example.OmerMoyalCouponProjectV2.controllers;


import com.auth0.jwt.JWT;
import com.example.OmerMoyalCouponProjectV2.beans.Category;
import com.example.OmerMoyalCouponProjectV2.beans.Coupon;
import com.example.OmerMoyalCouponProjectV2.beans.OurSession;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.security.JWTutil;
import com.example.OmerMoyalCouponProjectV2.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    private JWTutil jwTutil;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HashMap<String, OurSession> sessions;

    @Autowired
    private CustomerService customerService;
    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to get all coupons of the customer
     * see the corresponding service for all possible CustomExceptions
     * @return ResponseEntity with error or the coupons
     */
    @GetMapping
    public ResponseEntity<?> getAllCoupons() {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);

        if (session != null) {
            CustomerService service = (CustomerService) session.getService();
            session.setLastActive(LocalDateTime.now());
            return ResponseEntity.status(200).body(service.getAllCustomerCoupons());
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");
    }
    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to get all coupons under the input price
     * see the corresponding service for all possible CustomExceptions
     * @param maxPrice the max price of the coupons to return
     * @return ResponseEntity with error or the coupons
     */
    @GetMapping(path = "/{maxPrice}")
    public ResponseEntity<?> getAllCouponsByCategory(@PathVariable int maxPrice) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);

        if (session != null) {
            CustomerService service = (CustomerService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                return ResponseEntity.status(200).body(service.getCustomerCoupons(maxPrice));
            } catch (CustomExceptions e) {
                return ResponseEntity.status(500).body(e.getMessage());
            }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");
    }

    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to get all coupons with that category
     * see the corresponding service for all possible CustomExceptions
     * @param category the category of the coupons to get
     * @return ResponseEntity with error or the coupons
     */
    @GetMapping(path = "/{category}")
    public ResponseEntity<?> getAllCouponsByCategory(@PathVariable Category category) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);

        if (session != null) {
            CustomerService service = (CustomerService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                return ResponseEntity.status(200).body(service.getCustomerCoupons(category));
            } catch (CustomExceptions e) {
                return ResponseEntity.status(500).body(e.getMessage());
            }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");
    }

    // if (session != null) {
//        }
//        return null;

    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to add coupon to the customer
     * see the corresponding service for all possible CustomExceptions
     * @param couponId the id of the coupon to purchase
     * @return ResponseEntity with error or success
     */
    @PostMapping("/purchaseCoupon/{couponId}")
    public ResponseEntity<?> purchaseCoupon(@PathVariable int couponId) {

        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);


        if (session != null) {
            CustomerService service = (CustomerService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                service.purchaseCoupon(couponId);
                return ResponseEntity.ok("Coupon purchased");
            } catch (CustomExceptions e) {
                return ResponseEntity.status(500).body(e.getMessage());
            }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");
    }

    /**
     * ***Not Used in Client***
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to get all details of the customer
     * see the corresponding service for all possible CustomExceptions
     * @return ResponseEntity with error or the customer
     */
    @GetMapping(path = "/getCustomerDetail")
    public ResponseEntity<?> getCustomerDetail() {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);

        if (session != null) {
            CustomerService service = (CustomerService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                return ResponseEntity.ok(service.getCustomerDetails());
            } catch (CustomExceptions e) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");
    }
}

