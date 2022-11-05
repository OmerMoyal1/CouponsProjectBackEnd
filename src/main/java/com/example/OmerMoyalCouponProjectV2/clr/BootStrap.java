package com.example.OmerMoyalCouponProjectV2.clr;

import com.example.OmerMoyalCouponProjectV2.beans.Category;
import com.example.OmerMoyalCouponProjectV2.beans.Company;
import com.example.OmerMoyalCouponProjectV2.beans.Coupon;
import com.example.OmerMoyalCouponProjectV2.beans.Customer;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.loginManager.ClientType;
import com.example.OmerMoyalCouponProjectV2.loginManager.LoginManager;
import com.example.OmerMoyalCouponProjectV2.repositories.CompaniesRepository;
import com.example.OmerMoyalCouponProjectV2.repositories.CouponRepository;
import com.example.OmerMoyalCouponProjectV2.repositories.CustomerRepository;
import com.example.OmerMoyalCouponProjectV2.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
//Not for use in Version 3
//@Order(1)
//@Component
public class BootStrap implements CommandLineRunner {

    public static Company[] companies = {
            new Company("Oshem", "oshemCom@gmail.com", "oshemPass", null),
            new Company("Electron", "electronCom@gmaill.com", "electronPass", null),
            new Company("quickFood", "quickFoodCom@gmail.com", "quickFoodPass", null)
    };
    public static Customer[] customers = {
            new Customer("Omer", "Moyal", "omer@gmail.com", "omerPass", null),
            new Customer("Matan", "Moyal", "Matan@gmail.com", "matanPass", null),
            new Customer("Shun", "cohen", "shun@gmail.com", "shunPass", null)
    };
    public static Coupon[] coupons = {
            new Coupon(Category.Food, "Bamba", "30%off", Date.valueOf("2022-01-01"), Date.valueOf("2024-10-01"), 5, 46, "asdij.com"),
            new Coupon(Category.Electricity, "Toaster", "30%off", Date.valueOf("2022-01-01"), Date.valueOf("2024-09-08"), 0, 46, "asdij.com"),
            new Coupon(Category.Food, "Burger", "30%off", Date.valueOf("2022-01-01"), Date.valueOf(LocalDate.now()), 5, 21, "asdij.com"),
            new Coupon(Category.Electricity, "Laptop", "30%off", Date.valueOf("2022-01-01"), Date.valueOf("2024-09-07"), 5, 300, "asdij.com"),
            new Coupon(Category.Other, "TestExpired", "30%off", Date.valueOf("2022-01-01"), Date.valueOf("2022-09-07"), 5, 300, "asdij.com")

    };
    public static Company company =
            new Company("TestCompany", "TestCom@gmail.com", "testPass", null);
    public static Customer customer =
            new Customer("test", "tester", "test@gmail.com", "testPass", null);

    public static Coupon coupon =
            new Coupon(Category.values()[1], "test", "10%off", Date.valueOf("2022-01-01"), Date.valueOf("2022-10-01"), 0, 13, "asdijfrtgh.com");
    public static Coupon expiredCoupon =
            new Coupon(Category.values()[3], "testExpired", "10%off", Date.valueOf("2022-01-01"), Date.valueOf("2022-01-01"), 0, 13, "asdijfrtgh.com");


    @Autowired
    private LoginManager loginManager;

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CompaniesRepository companiesRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public void run(String... args) {

        List<Company> companyList = List.of(companies);
        List<Customer> customerList = List.of(customers);


        CompanyService companyService = null;
        CompanyService companyService1 = null;
        CompanyService companyService2 = null;





        System.out.println("===========================Initialize Data=============================");


        try {
            companiesRepository.saveAll(companyList);
            System.out.println("Added Companies");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            customerRepository.saveAll(customerList);
            System.out.println("Added Customers");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {

            companyService = (CompanyService) LoginManager.getInstance().login("oshemCom@gmail.com", "oshemPass", ClientType.COMPANY);
            companyService.addCoupon(coupons[0]);
            companyService = (CompanyService) LoginManager.getInstance().login("electronCom@gmaill.com", "electronPass", ClientType.COMPANY);
            companyService.addCoupon(coupons[1]);
            companyService.addCoupon(coupons[3]);
            companyService = (CompanyService) LoginManager.getInstance().login("quickFoodCom@gmail.com", "quickFoodPass", ClientType.COMPANY);
            companyService.addCoupon(coupons[2]);
            companyService.addCoupon(coupons[4]);


        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

    }
}
