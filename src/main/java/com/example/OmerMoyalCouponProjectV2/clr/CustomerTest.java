package com.example.OmerMoyalCouponProjectV2.clr;

import com.example.OmerMoyalCouponProjectV2.beans.Category;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.loginManager.ClientType;
import com.example.OmerMoyalCouponProjectV2.loginManager.LoginManager;
import com.example.OmerMoyalCouponProjectV2.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
//Not for use in Version 3
//@Order(2)
//@Component
public class CustomerTest implements CommandLineRunner {
    @Autowired
    LoginManager loginManager;

    @Override
    public void run(String... args) {

        CustomerService customerService = null;

        System.out.println("======================================== Customer Service Tests==========================================");
        System.out.println("===============Test Login===============");
        System.out.println("Should Fail wrong credentials(Email)");
        try {
            customerService = (CustomerService) loginManager.login("Omer@gmail.com1", "omerPass", ClientType.CUSTOMER);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        } System.out.println("\nShould Fail wrong credentials(Password)");
        try {
            customerService = (CustomerService) loginManager.login("Omer@gmail.com", "omerPass1", ClientType.CUSTOMER);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nShould succeed");
        try {
            customerService = (CustomerService) loginManager.login("Omer@gmail.com", "omerPass", ClientType.CUSTOMER);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("=============Test purchaseCoupon ===================");
        System.out.println("Should Fail no coupon with that id");
        try {
            customerService.purchaseCoupon(80);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould fail coupon out of stock");
        try {
            customerService.purchaseCoupon(2);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("\nShould fail expired coupon ");
        try {
            customerService.purchaseCoupon(4);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nShould Succeed");
        try {
            customerService.purchaseCoupon(1);
            customerService.purchaseCoupon(3);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nShould fail coupon already purchased");
        try {
            customerService.purchaseCoupon(1);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("================ Test getAllCustomerCoupons ===================");
        System.out.println(customerService.getAllCustomerCoupons());
        System.out.println("================ Test getAllCustomerCoupons (Category)===================");
        System.out.println("Should show only the Food Coupon");
        try {
            System.out.println(customerService.getCustomerCoupons(Category.Food));
        }catch (CustomExceptions e){
            System.out.println(e.getMessage());
        }

        System.out.println("================ Test getAllCustomerCoupons (Price) ===================");
        System.out.println("Should show only the Food Coupon");
        try {
            System.out.println(customerService.getCustomerCoupons(150));
        }catch (CustomExceptions e){
            System.out.println(e.getMessage());
        }

        System.out.println("===================Test getCustomerDetails=============================");
        try {
            System.out.println(customerService.getCustomerDetails());
        }catch (CustomExceptions e){
            System.out.println(e.getMessage());
        }






    }
}
