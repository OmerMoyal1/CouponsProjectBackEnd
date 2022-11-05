package com.example.OmerMoyalCouponProjectV2.clr;

import com.example.OmerMoyalCouponProjectV2.beans.Category;
import com.example.OmerMoyalCouponProjectV2.beans.Coupon;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.loginManager.ClientType;
import com.example.OmerMoyalCouponProjectV2.loginManager.LoginManager;
import com.example.OmerMoyalCouponProjectV2.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
//Not for use in Version 3
//@Order(3)
//@Component
public class CompanyTest implements CommandLineRunner {

    Coupon testCoupon = new Coupon(Category.Food, "bamba", "40%off", Date.valueOf("2022-01-01"), Date.valueOf("2024-10-01"), 6, 46, "asdij.com");

    @Autowired
    LoginManager loginManager;


    @Override
    public void run(String... args)  {
        CompanyService companyService = null;

        System.out.println("======================================== Company Service Tests==========================================");
        System.out.println("===============Test Login===============");
        System.out.println("Should Fail wrong credentials(Email)");
        try {
            companyService = (CompanyService) loginManager.login("oshemCom@gmail.com1", "oshemPass", ClientType.COMPANY);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());

        }
        System.out.println("\nShould Fail wrong credentials(Password)");
        try {
            companyService = (CompanyService) loginManager.login("oshemCom@gmail.com", "oshemPass1", ClientType.COMPANY);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nShould succeed");
        try {
            companyService = (CompanyService) loginManager.login("oshemCom@gmail.com", "oshemPass", ClientType.COMPANY);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("==================== Test addCoupon =================");
        System.out.println("\nShould Fail coupon with the same title exist");
        try {
            companyService.addCoupon(testCoupon);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nShould fail coupon of another company");
        try {
            companyService.addCoupon(companyService.getOneCoupon(2));
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nShould Succeed coupon with existing title but of another company");
        testCoupon.setTitle("burger");
        try {
            companyService.addCoupon(testCoupon);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("=============== Test updateCoupon =================");
        Coupon updateCoupon=null;
        try {
            updateCoupon = companyService.getOneCoupon(6);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }


        System.out.println("\nShould Succeed change amount ");
        updateCoupon.setAmount(10);
        try {
            companyService.updateCoupon(updateCoupon);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nShould Fail Title Exist in Company ");
        updateCoupon.setTitle("bamba");
        try {
            companyService.updateCoupon(updateCoupon);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould Succeed Title Exist but in other Company ");

        updateCoupon.setTitle("burger");
        updateCoupon.setPrice(11);
        updateCoupon.setCategory(Category.Restaurant);
        try {
            companyService.updateCoupon(updateCoupon);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("=================Test getAllCompanyCoupons===================== ");

        System.out.println("\nShould show 2 coupons\n");

            System.out.println(companyService.getAllCompanyCoupons());




        System.out.println("===================Test getCompanyByCategory=================");
        System.out.println("\nShould show only the Food Coupon");
        try {
            System.out.println(companyService.getCompanyCouponsByCategory(Category.Food));
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }



        System.out.println("=====================Test getCompanyCouponByMaxPrice===============");
        System.out.println("\nShould snow only the Restaurant Coupon");
        try {
            System.out.println(companyService.getCompanyCouponByMaxPrice(21));
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }


        System.out.println("====================Test getCompanyDetails=================\n");
        try {
            System.out.println(companyService.getCompanyDetails());
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n====================Test getOneCoupon =================");
        System.out.println("\nShould fail coupon not owned by company");
        try {
            System.out.println(companyService.getOneCoupon(2));
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould fail coupon doesn't exist");
        try {
            System.out.println(companyService.getOneCoupon(9));
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould succeed");
        try {
            System.out.println(companyService.getOneCoupon(1));
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }




        System.out.println("====================Test deleteCoupon =====================\n");

        System.out.println("\nShould fail cannot delete a coupon that doesnt exist");
        try {
            companyService.deleteCoupon(9);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nShould fail cannot delete a coupon not owned by the company");
        try {
            companyService.deleteCoupon(2);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould delete coupon and the purchase made by customer 1");
        try {
            companyService.deleteCoupon(1);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }


    }
}
