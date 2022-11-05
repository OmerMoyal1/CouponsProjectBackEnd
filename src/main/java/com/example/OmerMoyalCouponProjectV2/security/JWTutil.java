package com.example.OmerMoyalCouponProjectV2.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.OmerMoyalCouponProjectV2.beans.Company;
import com.example.OmerMoyalCouponProjectV2.beans.Customer;
import com.example.OmerMoyalCouponProjectV2.beans.OurSession;
import com.example.OmerMoyalCouponProjectV2.beans.UserDetails;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.services.AdminService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service


public class JWTutil {

    //type of encryption
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    //secret key
    private final String secretKey = "OmerMoyalSuperDuperSecretKeY";
    //private key
    private Key decodedSecretKey = new SecretKeySpec
            (Base64.getDecoder().decode(secretKey), this.signatureAlgorithm);

    @Autowired
    private
    AdminService adminService;

    @Autowired
    private HashMap<String, OurSession> sessions;

    @Autowired
    private HttpServletRequest request;
    private SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), signatureAlgorithm);

    /**
     * This method Verifies the Token signature is correct (The token hasnt been forged or altered)
     * by breaking the Token to 3 strings (header,payload,signature) and then checking to see if the
     * secret key matches The signture Jwt generate if the secret key was the secret key input
     *
     * @param token the token we get back from the client
     * @throws Exception if the token signature is invalid
     */
    public void verifyToken(String token) throws Exception {

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String[] chunks = token.split("\\.");

        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(SignatureAlgorithm.HS256, secretKeySpec);

//        System.out.println("Chunk0");
//        System.out.println(chunks[0]);
//        System.out.println("Chunk1");
//        System.out.println(chunks[1]);
//        System.out.println("Token");
//        System.out.println(token);


        if (!validator.isValid(tokenWithoutSignature, signature)) {
            throw new Exception("The Token is invalid");
        }

    }

    /**
     * generate a Jwt token with the secret private key get userDetails from the client to put claims in the token from the userDetails
     * get id too to get the name of the Customer/Company
     *
     * @param userDetails get userDetails to put in the token
     * @param id to get the name of the Customer/Company
     * @return String of the token
     */
    public String createToken(UserDetails userDetails, int id) {
        switch (userDetails.getClientType()) {
            case CUSTOMER:
                Customer customer = null;
                try {
                    customer = adminService.getOneCustomer(id);
                } catch (CustomExceptions e) {
                    System.out.println(e.getMessage());
                }
                String customerToken = JWT.create().withIssuer("Couponiada LTD").withIssuedAt(new java.util.Date())
                        //.withExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                        .withClaim("id", id)
                        .withClaim("email", userDetails.getEmail())
                        .withClaim("name", customer.getFirstName() + " " + customer.getLastName())
                        .withClaim("clientType", userDetails.getClientType().toString())
                        .sign(Algorithm.HMAC256(secretKey));

                return customerToken;
            case COMPANY:
                Company company = null;
                try {
                    company = adminService.getOneCompany(id);
                } catch (CustomExceptions e) {
                    System.out.println(e.getMessage());
                }
                String companyToken = JWT.create().withIssuer("Couponiada LTD").withIssuedAt(new java.util.Date())
                        //.withExpiresAt(Instant.now().plus(5, ChronoUnit.MINUTES))
                        .withClaim("id", id)
                        .withClaim("email", userDetails.getEmail())
                        .withClaim("name", company.getName())
                        .withClaim("clientType", userDetails.getClientType().toString())
                        .sign(Algorithm.HMAC256(secretKey));
                return companyToken;
            case ADMINISTRATOR:
                String adminToken = JWT.create().withIssuer("Couponiada LTD").withIssuedAt(new Date())
                        //.withExpiresAt(Instant.now().plus(5, ChronoUnit.MINUTES))
                        .withClaim("id", id)
                        .withClaim("email", userDetails.getEmail())
                        .withClaim("name", "Admin")
                        .withClaim("clientType", userDetails.getClientType().toString())
                        .sign(Algorithm.HMAC256(secretKey));
                return adminToken;
        }

        return null;
    }

    /**
     * find the session (who has the ClientService) that was in the session HashMap with the id and client type as Key
     * (because there are multiple of the same id company/customer are in different tables)
     *
     * @param token the token from the client after verification to get the session
     * @return OurSession that has the ClientService
     */
    public OurSession findSession(String token) {
        int id = JWT.decode(token).getClaim("id").asInt();
        String temp = id + "" + JWT.decode(token).getClaim("clientType").asString();
        return sessions.get(temp);
    }

}
