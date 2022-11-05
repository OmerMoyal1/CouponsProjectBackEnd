package com.example.OmerMoyalCouponProjectV2.services;

import com.example.OmerMoyalCouponProjectV2.beans.Category;
import com.example.OmerMoyalCouponProjectV2.beans.Coupon;
import com.example.OmerMoyalCouponProjectV2.beans.Customer;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.exceptions.ExceptionsLibrary;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Set;

@Service
public class CustomerService extends ClientService {
    private int customerId;

    /**
     * With the help of this function the customers can connect to the server.
     *
     * @param email    customer email input
     * @param password customer password input
     * @throws CustomExceptions if one of the params is wrong.
     * @return this Customer object with the customerId set to the logged in customer id
     */
    @Override
    public ClientService login(String email, String password) throws CustomExceptions {
        boolean connect = customerRepository.existsByEmailAndPassword(email, password);
        if (connect) {
            this.customerId = customerRepository.findByEmailAndPassword(email, password).getId();
            return this;
        } else {
            throw new CustomExceptions(ExceptionsLibrary.FAIL_2_CONNECT);
        }
    }

    /**
     * With the help of this function the customer can purchase coupons.
     * The function checks if the coupon has already been purchased by the customer and if the coupon exists in the system.
     * And when it is purchased the amount of coupons listed in database decreases by 1.
     *
     * @param couponId the id of the coupon that the customer want to purchase.
     * @throws CustomExceptions if the coupon is not in the database,coupon out of stock(Amount==0),
     */
    public void purchaseCoupon(int couponId) throws CustomExceptions {
        Date date = new Date(System.currentTimeMillis());
        if (!couponRepository.existsById(couponId)) {
            throw new CustomExceptions(ExceptionsLibrary.NO_COUPON_ID);
        }
        Coupon coupon = couponRepository.findById(couponId).get();
        if (coupon.getAmount() == 0) {
            throw new CustomExceptions(ExceptionsLibrary.COUPONS_OUT_OF_STOCK);
        }
        if (coupon.getEndDate().before(date)) {
            throw new CustomExceptions(ExceptionsLibrary.COUPON_EXPIRED);
        }

        if (customerRepository.isCouponPurchased(customerId, coupon.getId()) != 0) {
            throw new CustomExceptions(ExceptionsLibrary.COUPON_PURCHASED);
        }
        customerRepository.addCouponToCustomer(coupon.getId(), customerId);
        coupon.setAmount(coupon.getAmount() - 1);
        couponRepository.save(coupon);
        System.out.println("Coupon purchased.");
    }


    /**
     * This function returns all the coupons of a particular customer
     */
    public Set<Coupon> getAllCustomerCoupons() {
        return customerRepository.findAllCustomerCoupons(customerId);
    }

    /**
     * This function returns all the coupons of a particular customer by category.
     *
     * @param category the requested category
     * @throws CustomExceptions if there was no coupons find by requested category
     */
    public Set<Coupon> getCustomerCoupons(Category category) throws CustomExceptions {
        Set<Coupon> coupons = customerRepository.findAllCustomerCouponsByCategory(customerId, category);
        if (coupons.isEmpty()) {
            throw new CustomExceptions(ExceptionsLibrary.NO_COUPONS_BY_CATEGORY);
        }
        return coupons;
    }

    /**
     * This function returns all the coupons of a particular customer by a defined maximum price.
     *
     * @param maxPrice defines the maximum price of the requested coupons.
     * @throws CustomExceptions if there are no coupons under the specified max price
     */
    public Set<Coupon> getCustomerCoupons(double maxPrice) throws CustomExceptions {
        Set<Coupon> coupons = customerRepository.findAllCustomerCouponsMaxPrice(customerId, maxPrice);
        if (coupons.isEmpty()) {
            throw new CustomExceptions(ExceptionsLibrary.NO_COUPONS_BY_PRICE);
        }
        return coupons;
    }

    /**
     * This function returns the details about a particular customer.
     *
     * @throws CustomExceptions in case the customer is not found in database.
     */
    public Customer getCustomerDetails() throws CustomExceptions {
        Customer customer = customerRepository.findById(customerId).get();
        if (customer == null) {
            throw new CustomExceptions(ExceptionsLibrary.NO_CUSTOMER);
        }
        return customer;
    }

    public int getCustomerId() {
        return customerId;
    }
}

