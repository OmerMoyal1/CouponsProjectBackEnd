package com.example.OmerMoyalCouponProjectV2.loginManager;

import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.exceptions.ExceptionsLibrary;
import com.example.OmerMoyalCouponProjectV2.repositories.CouponRepository;
import com.example.OmerMoyalCouponProjectV2.services.AdminService;
import com.example.OmerMoyalCouponProjectV2.services.ClientService;
import com.example.OmerMoyalCouponProjectV2.services.CompanyService;
import com.example.OmerMoyalCouponProjectV2.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;

@Service
public class LoginManager {

    private static LoginManager instance = null;

    private static ApplicationContext applicationContext;

    private LoginManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager(applicationContext);
        }
        return instance;
    }


    public ClientService login(String email, String password, ClientType type) throws CustomExceptions {
        switch (type) {
            case ADMINISTRATOR:
                AdminService adminF = applicationContext.getBean(AdminService.class);
                return (adminF.login(email, password));
//                    throw new CustomExceptions(ExceptionsLibrary.NOT_ADMIN);
//                return adminF;

            case CUSTOMER:
                CustomerService customerService = applicationContext.getBean(CustomerService.class);
                return (customerService.login(email, password));
            // throw new CustomExceptions(ExceptionsLibrary.INVALID_PERMISSION);
            //return customerService;

            case COMPANY:
                CompanyService companyService = applicationContext.getBean(CompanyService.class);
                return (companyService.login(email, password));

            default:
                System.out.println("wrong client type");
                return null;
        }
    }
}
