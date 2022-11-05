package com.example.OmerMoyalCouponProjectV2.clr;

import com.example.OmerMoyalCouponProjectV2.beans.Company;
import com.example.OmerMoyalCouponProjectV2.beans.Customer;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.loginManager.ClientType;
import com.example.OmerMoyalCouponProjectV2.loginManager.LoginManager;
import com.example.OmerMoyalCouponProjectV2.services.AdminService;
import org.springframework.boot.CommandLineRunner;
//Not for use in Version 3
//@Order(4)
//@Component
public class AdminTest implements CommandLineRunner {

    Company companyTest = new Company("Test", "testCom@test.com", "ad31567", null);

    Customer customerTest = new Customer("test", "tester", "test@gmail.com", "testPass", null);


    @Override
    public void run(String... args) {
        AdminService adminService = null;

        System.out.println("==================================== AdminService Tests=======================================");

        System.out.println("================Test Login==============");
        System.out.println("Should fail wrong credentials");
        try {
            adminService = (AdminService) LoginManager.getInstance().login("admin1", "admin@admin.com", ClientType.ADMINISTRATOR);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Should succeed");
        try {
            adminService = (AdminService) LoginManager.getInstance().login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("================== Test addCompany ================");
        System.out.println("\nShould Fail name exist");
        companyTest.setName("Electron");
        try {
            adminService.addCompany(companyTest);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould Fail email exist");
        companyTest.setName("Test");
        companyTest.setEmail("electronCom@gmaill.com");
        try {
            adminService.addCompany(companyTest);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould succeed");

        companyTest.setEmail("TestCom@gmaill.com");
        try {
            adminService.addCompany(companyTest);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("================== Test updateCompany ================");
        Company companyTest = null;

        try {

            companyTest = adminService.getOneCompany(4);

        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nShould fail cant change name");
        companyTest.setName("bamba");
        try {
            adminService.updateCompany(companyTest);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould fail email exist");
        companyTest.setName("Test");
        companyTest.setEmail("oshemCom@gmail.com");
        try {
            adminService.updateCompany(companyTest);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould succeed change password");
        companyTest.setName("Test");
        companyTest.setEmail("testCom@test.com");
        companyTest.setPassword("123456test");
        try {
            adminService.updateCompany(companyTest);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("=================== Test getAllCompanies and deleteCompany ==============");
//        System.out.println("Note that company 3 doesn't have the TestExpired coupon added in bootStrap it was deleted by the daily job");
//        try {
//            System.out.println(adminService.getAllCompanies());
//        } catch (CustomExceptions e) {
//            System.out.println(e.getMessage());
//        }

        System.out.println("\nDelete Should succeed company 3");

        try {
            adminService.deleteCompany(3);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould show all companies except company 3");
//        try {
//            System.out.println(adminService.getAllCompanies());
//        } catch (CustomExceptions e) {
//            System.out.println(e.getMessage());
//        }

        System.out.println("\nDelete Should fail no company with that id ");

        try {
            adminService.deleteCompany(3);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("======================== Test getOneCompany  ====================");
        System.out.println("\nShould fail company was deleted");
        try {
            System.out.println(adminService.getOneCompany(3));
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould succeed");
        try {
            System.out.println(adminService.getOneCompany(1));
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("======================== Test addCustomer  ====================");
        System.out.println("\nShould fail email exists");
        customerTest.setEmail("omer@gmail.com");
        try {
            adminService.addCustomer(customerTest);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould succeed only email is different");
        customerTest.setEmail("testCom@test.com");
        customerTest.setFirstName("Omer");
        customerTest.setLastName("Moyal");
        customerTest.setPassword("omerPass");
        try {
            adminService.addCustomer(customerTest);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("==================== Test updateCustomer =================");
        Customer customerTest = null;
        try {
            customerTest = adminService.getOneCustomer(4);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Should succeed only changed name");
        customerTest.setLastName("Testta");
        try {
            adminService.updateCustomer(customerTest);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould Fail another customer has that email");

        customerTest.setEmail("Matan@gmail.com");
        try {
            adminService.updateCustomer(customerTest);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould succeed name and password match another customer but email is different");

        customerTest.setEmail("Test@gmail.com");
        customerTest.setFirstName("Matan");
        customerTest.setLastName("Moyal");
        customerTest.setPassword("matanPass");
        try {
            adminService.updateCustomer(customerTest);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("================= Test getAllCustomers ================");

        System.out.println(adminService.getAllCustomers());

        System.out.println("================ Test deleteCustomer ==================");
        System.out.println("\n Should fail id doesnt exist");
        try {
            adminService.deleteCustomer(8);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould succeed and delete Customer 1 and its purchase");
        try {
            adminService.deleteCustomer(1);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("================= Test getOneCustomer ===============");
        System.out.println("\n Should fail id doesnt exist");
        try {
            adminService.getOneCustomer(1);
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nShould succeed");
        try {
            System.out.println(adminService.getOneCustomer(2));
        } catch (CustomExceptions e) {
            System.out.println(e.getMessage());
        }


    }
}
