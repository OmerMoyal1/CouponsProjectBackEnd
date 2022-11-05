package com.example.OmerMoyalCouponProjectV2.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.example.OmerMoyalCouponProjectV2.beans.Category;
import com.example.OmerMoyalCouponProjectV2.beans.Coupon;
import com.example.OmerMoyalCouponProjectV2.beans.OurSession;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.security.JWTutil;
import com.example.OmerMoyalCouponProjectV2.services.CompanyService;
import com.example.OmerMoyalCouponProjectV2.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "/company")
@CrossOrigin
public class CompanyController {

    @Autowired
    private JWTutil jwTutil;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HashMap<String, OurSession> sessions;

    @Autowired
    private CompanyService companyService;

    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to add coupon
     * see the corresponding service for all possible CustomExceptions
     * @param coupon input Coupon from the client
     * @return ResponseEntity with error or the coupon
     */
    @PostMapping(path = "/addCoupon")
    public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon) {

        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session = jwTutil.findSession(token);

        if (session != null) {
            CompanyService service = (CompanyService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                return ResponseEntity.ok().body(service.addCoupon(coupon));
            } catch (CustomExceptions e) {
                return ResponseEntity.status(500).body(e.getMessage());
            }

        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");
    }

    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to update coupon
     * see the corresponding service for all possible CustomExceptions
     * @param coupon input Coupon from the client
     * @return ResponseEntity with error or the coupon
     */

    @PostMapping("/updateCoupon")
    public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session = jwTutil.findSession(token);

        if (session != null) {
            CompanyService service = (CompanyService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                return ResponseEntity.ok().body(service.updateCoupon(coupon));
            } catch (CustomExceptions e) {
                return ResponseEntity.status(500).body(e.getMessage());
            }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");


    }

    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to delete coupon
     * see the corresponding service for all possible CustomExceptions
     * @param couponId input Coupon id from the client
     * @return ResponseEntity with error or the coupon
     */
    @DeleteMapping("/{couponId}")
    public ResponseEntity<?> deleteCoupon(@PathVariable int couponId) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session = jwTutil.findSession(token);

        if (session != null) {
            CompanyService service = (CompanyService) session.getService();
            session.setLastActive(LocalDateTime.now());

            try {
                service.deleteCoupon(couponId);
                return ResponseEntity.ok("Coupon deleted");
            } catch (CustomExceptions e) {
               return ResponseEntity.status(404).body(e.getMessage());
            }

        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");

    }
    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to get all the coupons
     * @return ResponseEntity with error or the coupons of the company
     */
    @GetMapping("/getAllCompanyCoupons")
    public ResponseEntity<?> getAllCoupons() {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session = jwTutil.findSession(token);

        if (session != null) {
            CompanyService service = (CompanyService) session.getService();
            session.setLastActive(LocalDateTime.now());
            return ResponseEntity.status(200).body(service.getAllCompanyCoupons());
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");

    }
    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to get one coupon
     * see the corresponding service for all possible CustomExceptions
     * @param couponId input Coupon id from the client
     * @return ResponseEntity with error or the coupon
     */
    @GetMapping("/getOneCoupon/{couponId}")
    public ResponseEntity<?> getOneCoupon(@PathVariable int couponId) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session = jwTutil.findSession(token);

        if (session != null) {
            CompanyService service = (CompanyService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                return ResponseEntity.ok(service.getOneCoupon(couponId));
            } catch (CustomExceptions e) {
                return ResponseEntity.status(404).body(e.getMessage());

            }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");

    }
    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to get all coupon of the company with a specific category
     * see the corresponding service for all possible CustomExceptions
     * @param category the category of the coupons to get
     * @return ResponseEntity with error or the coupons
     */
    @GetMapping("/getCouponsByCategory")
    public ResponseEntity<?> getCouponsByCategory(Category category) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session = jwTutil.findSession(token);

        if (session != null) {
            CompanyService service = (CompanyService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                return ResponseEntity.ok(service.getCompanyCouponsByCategory(category));
            } catch (CustomExceptions e) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
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
    @GetMapping("/getCouponsByPrice")
    public ResponseEntity<?> getCouponsByPrice(double maxPrice) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session = jwTutil.findSession(token);

        if (session != null) {
            CompanyService service = (CompanyService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                return ResponseEntity.ok(service.getCompanyCouponByMaxPrice(maxPrice));
            } catch (CustomExceptions e) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");


    }
}
