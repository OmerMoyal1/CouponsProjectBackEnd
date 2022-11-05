package com.example.OmerMoyalCouponProjectV2.controllers;

import com.auth0.jwt.JWT;
import com.example.OmerMoyalCouponProjectV2.beans.Company;
import com.example.OmerMoyalCouponProjectV2.beans.Customer;
import com.example.OmerMoyalCouponProjectV2.beans.OurSession;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.security.JWTutil;
import com.example.OmerMoyalCouponProjectV2.services.AdminService;
import com.example.OmerMoyalCouponProjectV2.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@CrossOrigin
public class AdminController {
    @Autowired
    private JWTutil jwTutil;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HashMap<String, OurSession> sessions;
    @Autowired
    private AdminService adminService;


    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then add the input customer
     * see the service for all possible CustomExceptions
     * @param customer input customer from the client
     * @return ResponseEntity with error or the customer
     */
    @PostMapping("/addCustomer")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);
        if (session != null) {
        try {
            return ResponseEntity.ok(adminService.addCustomer(customer));
        } catch (CustomExceptions e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");

    }


//    @GetMapping(path = "/getAllCustomers")
//
//    public List<Customer> getAllCustomers() {
//        String token = request.getHeader("authorization").replace("Bearer ", "");
//        int id = JWT.decode(token).getClaim("id").asInt();
//        String temp = id + "" + JWT.decode(token).getClaim("clientType").asString();
//        OurSession session = sessions.get(temp);
//        AdminService service = (AdminService) session.getService();
//        session.setLastActive(LocalDateTime.now());
//
//        return service.getAllCustomers();
//    }

    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * get all the companies from the D.B
     * see the service for all possible CustomExceptions
     * @return ResponseEntity with all the companies or an error
     */
    @GetMapping(path = "/getAllCompanies")
    public ResponseEntity<?> getAllCompanies() {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);

        if (session != null) {

            AdminService service = (AdminService) session.getService();
            session.setLastActive(LocalDateTime.now());
            return ResponseEntity.ok(service.getAllCompanies());
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");

    }

    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then delete the company with input id
     * see the service for all possible CustomExceptions
     * @param companyId input customer from the client
     * @return ResponseEntity with error or success
     */
    @DeleteMapping("/deleteCompany/{companyId}")
    public ResponseEntity<?> deleteCompany(@PathVariable int companyId) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);

        if (session != null) {
            AdminService service = (AdminService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                adminService.deleteCompany(companyId);
                return ResponseEntity.ok("Company deleted");
            } catch (CustomExceptions e) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");
    }

    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then get the company with the input id
     * see the service for all possible CustomExceptions
     * @param companyId input company id from the client
     * @return ResponseEntity with error or the company
     */
    @GetMapping("/getOneCompany/{companyId}")
    public ResponseEntity<?> getOneCompany(@PathVariable int companyId) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);

        if (session != null) {
            AdminService service = (AdminService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                return ResponseEntity.status(200).body(adminService.getOneCompany(companyId));
            } catch (CustomExceptions e) {
               return ResponseEntity.status(404).body(e.getMessage());
            }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");
    }

    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to update the company
     * see the service for all possible CustomExceptions
     * @param company input company from the client
     * @return ResponseEntity with error or success
     */
    @PostMapping("/updateCompany")
    public ResponseEntity<?> updateCompany(@RequestBody Company company) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);
        if (session != null) {
            AdminService service = (AdminService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                adminService.updateCompany(company);
                return ResponseEntity.ok().body("Company updated");
            } catch (CustomExceptions e) {
                return ResponseEntity.status(500).body(e.getMessage());
            }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");

    }

    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then gets all the Customers from the D.B
     * see the service for all possible CustomExceptions
     * @return ResponseEntity with error or all the Customers
     */
    @GetMapping(path = "/getAllCustomers")
    public ResponseEntity<?> getAllCustomers() {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);

        if (session != null) {
            AdminService service = (AdminService) session.getService();
            session.setLastActive(LocalDateTime.now());
            return ResponseEntity.ok(service.getAllCustomers());
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");

    }

    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to delete the customer with the input id
     * see the service for all possible CustomExceptions
     * @param customerId input customer id from the client
     * @return ResponseEntity with error or success
     */
    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int customerId) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);

        if (session != null) {
            AdminService service = (AdminService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                adminService.deleteCustomer(customerId);
                return ResponseEntity.ok("Customer deleted");
            } catch (CustomExceptions e) {
                return ResponseEntity.status(404).body(e.getMessage());
            }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");

    }

    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to update the customer
     * see the corresponding service for all possible CustomExceptions
     * @param customer input customer from the client
     * @return ResponseEntity with error or success
     */
    @PostMapping("/updateCustomer")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);

        if (session != null) {
            AdminService service = (AdminService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                adminService.updateCustomer(customer);
                return ResponseEntity.status(200).body("Customer updated");
            } catch (CustomExceptions e) {
                return ResponseEntity.status(500).body(e.getMessage());
            }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");
    }

    /**
     * Verify if token signature from the client is valid,try to get the session with the token claims
     * and checks if the session is not expired then update the last time active
     * then try to get the customer
     * see the corresponding service for all possible CustomExceptions
     * @param customerId input customer ID from the client
     * @return ResponseEntity with error or the customer
     */
    @GetMapping("/getOneCustomer/{customerId}")
    public ResponseEntity<?> getOneCustomer(@PathVariable int customerId) {
        String token = request.getHeader("authorization").replace("Bearer ", "");
        try {
            jwTutil.verifyToken(token);
        } catch (Exception e) {
            return ResponseEntity.status(498).body(e.getMessage());
        }
        OurSession session=jwTutil.findSession(token);

        if (session != null) {
            AdminService service = (AdminService) session.getService();
            session.setLastActive(LocalDateTime.now());
            try {
                return ResponseEntity.status(200).body(adminService.getOneCustomer(customerId));
            } catch (CustomExceptions e) {
              return   ResponseEntity.status(404).body(e.getMessage());
            }
        } else
            return ResponseEntity.status(440).body("You have been Away for too long please Login again");


    }


}

