package com.example.OmerMoyalCouponProjectV2.services;

import com.example.OmerMoyalCouponProjectV2.beans.Company;
import com.example.OmerMoyalCouponProjectV2.beans.Customer;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.exceptions.ExceptionsLibrary;
import com.example.OmerMoyalCouponProjectV2.repositories.CompaniesRepository;
import com.example.OmerMoyalCouponProjectV2.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.List;
import java.util.Objects;

@Service
public class AdminService extends ClientService {

    public AdminService() {
    }

    /**
     * With the help of this function you can connect to the system.
     *
     * @param email    input by administrator
     * @param password input by administrator
     * @throws CustomExceptions if one of the params does not match the hard-coded specifications
     * @return this AdminService
     */
    @Override
    public ClientService login(String email, String password) throws CustomExceptions {
       if (email.equals("admin@admin.com") && password.equals("21232f297a57a5a743894a0e4a801fc3"))
           return this;
//        if (email.equals("admin@admin.com") && password.equals("admin")) {
//            System.out.println("Admin Connected.");
//            return true;
//
        throw new CustomExceptions(ExceptionsLibrary.NOT_ADMIN);
    }

    /**
     * this function will add a new company to the database.
     * The function will check if the company does not already exist by id,
     * if there is no company listed with the same name or email.
     *
     * @param company a new object of company entity to be created
     * @throws CustomExceptions in case the method found in database another company by the same name or email address already listed
     */
    public Company addCompany(Company company) throws CustomExceptions {
        if (companiesRepository.existsByName(company.getName())) {
            throw new CustomExceptions(ExceptionsLibrary.NAME_EXIST);
        }
        if (companiesRepository.existsByEmail(company.getEmail())) {
            throw new CustomExceptions(ExceptionsLibrary.EMAIL_EXIST);
        }
       return companiesRepository.save(company);

    }

    /**
     * this function will update company's details in the system.
     *
     * checks that the company exists in d.b
     * checks that name is not changed
     * checks that the email is not changed to the email of another company
     *
     * @param company the object of company entity to update
     * @throws CustomExceptions in case the data to be updated is the company name or the email exist in another company or the company id doesn't exist
     */
    public void updateCompany(Company company) throws CustomExceptions {
        if (!companiesRepository.existsById(company.getId())) {
            throw new CustomExceptions(ExceptionsLibrary.NO_COMPANY_ID);
        }
        if (!Objects.equals(company.getName(), companiesRepository.findById(company.getId()).get().getName())) {
            throw new CustomExceptions(ExceptionsLibrary.CAN_NOT_CHANGE_NAME);

        }
        if (companiesRepository.existsByEmailAndIdNot(company.getEmail(),company.getId())) {
            throw new CustomExceptions(ExceptionsLibrary.EMAIL_EXIST);

        }
//        if (!Objects.equals(company.getEmail(), companiesRepository.findById(company.getId()).get().getEmail()) & companiesRepository.existsByEmail(company.getEmail())) {
//            throw new CustomExceptions(ExceptionsLibrary.EMAIL_EXIST);
//        }

        companiesRepository.save(company);

    }

    /**
     * this function will delete company from the system.(Cascades)
     * The function checks if this company exists in the system
     *
     * @param companyId the company id to be deleted
     * @throws CustomExceptions in case the company id has not been found in database
     */
    public void deleteCompany(int companyId) throws CustomExceptions {
        if (!companiesRepository.existsById(companyId)) {
            throw new CustomExceptions(ExceptionsLibrary.NO_COMPANY_ID);
        }
        companiesRepository.deleteById(companyId);
        System.out.println("Company deleted successfully");
    }


    /**
     * This function returns all the companies listed in database.
     *
     * @throws CustomExceptions if companies table in database is empty
     * @return List of all Companies
     */
    public List<Company> getAllCompanies()  {

        return companiesRepository.findAll();
    }

    /**
     * This function returns a single company.
     *
     *
     * @param companyId the id of the requested company
     * @throws CustomExceptions in case the company id is not found in the database
     * @return Company Object with the given id
     */
    public Company getOneCompany(int companyId) throws CustomExceptions {
        if (!companiesRepository.existsById(companyId)) {
            throw new CustomExceptions(ExceptionsLibrary.NO_COMPANY_ID);
        }
        return companiesRepository.findById(companyId).get();
    }


    /**
     * this function will add a new customer in the system.
     * The function checks if there is no similar customer already in database with the same  email.
     *
     * @param customer new customer object from customer entity
     * @throws CustomExceptions in case a customer object is found in database with the same email
     */
    public Customer addCustomer(Customer customer) throws CustomExceptions {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new CustomExceptions(ExceptionsLibrary.EMAIL_EXIST);
        }
        return customerRepository.save(customer);

    }

    /**
     * this function will update customer's details in the system.
     * The function checks if this customer id exists in the system
     * and checks that the email hasn't been updated to another customer's email
     * @param customer customer object to be updated
     * @throws CustomExceptions if the customer's id is not found in database
     */
    public void updateCustomer(Customer customer) throws CustomExceptions {
        if (!customerRepository.existsById(customer.getId())) {
            throw new CustomExceptions(ExceptionsLibrary.NO_CUSTOMER);
        }
        if (customerRepository.existsByEmailAndIdNot(customer.getEmail(),customer.getId())) {
            throw new CustomExceptions(ExceptionsLibrary.EMAIL_EXIST);
        }

        customerRepository.save(customer);
        System.out.println("Customer updated");

    }


    /**
     * This function returns all the customers in the database.
     *
     * @return List of all customer's
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * this function will delete a customer from the system.
     * The function checks if this customer exists in the system
     *
     * @param customerId the customer to be deleted
     * @throws CustomExceptions if the customer's id is not found in database
     */
    public void deleteCustomer(int customerId) throws CustomExceptions {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomExceptions(ExceptionsLibrary.NO_CUSTOMER);
        } else {
            customerRepository.deleteById(customerId);
            System.out.println("Customer deleted successfully");
        }
    }

    /**
     * This function returns a single customer.
     *
     * @param customerId the id of the customer to be found
     * @throws CustomExceptions if the customer's id is not found in database
     *
     * @return Customer Object with the given id
     */
    public Customer getOneCustomer(int customerId) throws CustomExceptions {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomExceptions(ExceptionsLibrary.NO_CUSTOMER);
        } else {
            return customerRepository.findById(customerId).get();
        }
    }


}
