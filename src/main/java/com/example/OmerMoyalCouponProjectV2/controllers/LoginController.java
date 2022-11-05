package com.example.OmerMoyalCouponProjectV2.controllers;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.OmerMoyalCouponProjectV2.beans.*;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.loginManager.ClientType;
import com.example.OmerMoyalCouponProjectV2.loginManager.LoginManager;
import com.example.OmerMoyalCouponProjectV2.security.JWTutil;
import com.example.OmerMoyalCouponProjectV2.services.AdminService;
import com.example.OmerMoyalCouponProjectV2.services.ClientService;
import com.example.OmerMoyalCouponProjectV2.services.CompanyService;
import com.example.OmerMoyalCouponProjectV2.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class LoginController {
    @Autowired
    private JWTutil jwTutil;
    @Autowired
    private LoginManager loginManager;
    @Autowired
    private HashMap<String, OurSession> sessions;
    @Autowired
    private AdminService adminService;

    /**
     * Login method gets user details from the client checks against the database that the data is correct (hard-coded for admin)
     * create a session and returns a token so the client can use it to send request with their respective session
     * @param user user details (email,password,clientType) for authentication
     * @return Response entity with an error or a tokem
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDetails user) {

        ClientService service = null;
        try {
            service = loginManager.login(user.getEmail(), user.getPassword(), user.getClientType());
        } catch (CustomExceptions e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
        int id = 0;
        if (service instanceof CustomerService)
            id = ((CustomerService) service).getCustomerId();
        else if (service instanceof CompanyService)
            id = ((CompanyService) service).getCompanyId();

        sessions.put(id + "" + user.getClientType(), new OurSession(service, LocalDateTime.now()));
        return ResponseEntity.ok(jwTutil.createToken(user,id));


    }

    /**
     * gets a Customer object from the client and try to add if possible, if not throws an exception
     *
     * @param customer Customer object to try to add
     * @return Response entity with either error or success
     */
    @PostMapping("/registerCustomer")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer customer) {
        Customer customer1 = null;
        try {
            customer1 = adminService.addCustomer(customer);
        } catch (CustomExceptions e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
//        CustomerService service = null;
//        try {
//            service = (CustomerService) loginManager.login(customer1.getEmail(), customer1.getPassword(), ClientType.CUSTOMER);
//        } catch (CustomExceptions e) {
//            return ResponseEntity.status(401).body(e.getMessage());
//        }
//        sessions.put(customer1.getId() + "" + ClientType.CUSTOMER, new OurSession(service, LocalDateTime.now()));
        return ResponseEntity.ok().body("Register succeed");

    }

    /**
     * gets a Company object from the client and try to add if possible, if not throws an exception
     * @param companyInput company object to try to add
     * @return Response entity with either error or success
     */
    @PostMapping("/registerCompany")
    public ResponseEntity<?> registerCompany(@RequestBody Company companyInput) {
        Company company = null;
        try {
            company = adminService.addCompany(companyInput);
        } catch (CustomExceptions e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
//        CompanyService service = null;
//        try {
//            service = (CompanyService) loginManager.login(company.getEmail(), company.getPassword(), ClientType.COMPANY);
//        } catch (CustomExceptions e) {
//            return ResponseEntity.status(401).body(e.getMessage());
//        }
//        sessions.put(company.getId() + "" + ClientType.COMPANY, new OurSession(service, LocalDateTime.now()));
        return ResponseEntity.ok().body("Register succeed");

    }







}
