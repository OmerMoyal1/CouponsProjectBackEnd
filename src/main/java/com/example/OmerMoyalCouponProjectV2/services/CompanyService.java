package com.example.OmerMoyalCouponProjectV2.services;

import com.example.OmerMoyalCouponProjectV2.beans.Category;
import com.example.OmerMoyalCouponProjectV2.beans.Company;
import com.example.OmerMoyalCouponProjectV2.beans.Coupon;
import com.example.OmerMoyalCouponProjectV2.exceptions.CustomExceptions;
import com.example.OmerMoyalCouponProjectV2.exceptions.ExceptionsLibrary;
import com.example.OmerMoyalCouponProjectV2.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@Service
public class CompanyService extends ClientService {
    private int companyId;

    public CompanyService() {
    }

    /**
     * With the help of this function you can connect to the system.
     *
     * @param email    email of the company
     * @param password the company login password
     * @return this CompanyService with companyID set to the id of the logged in company
     * @throws CustomExceptions if email or password are incorrect
     */
    @Override
    public ClientService login(String email, String password) throws CustomExceptions {
        boolean connect = companiesRepository.existsByEmailAndPassword(email, password);
        if (connect) {
            //return companiesRepository.findByEmailAndPassword(email, password).getId();
            this.companyId = companiesRepository.findByEmailAndPassword(email, password).getId();
            return this;
        } else {
            throw new CustomExceptions(ExceptionsLibrary.FAIL_2_CONNECT);
        }
    }

    /**
     * this function will add a coupon to a specific company in the system.
     * The function checks if there is a coupon with the same title for the same company already listed in database.
     *
     * @param coupon new coupon object of coupon entity
     * @throws CustomExceptions 1: in case the company id is not found in database.
     *                          this can happen if the requesting company did not enter the system through the login process
     *                          2: if already exist a similar coupon in the company's database
     *                          3: coupon with the same title already exist in database
     */
    public Coupon addCoupon(Coupon coupon) throws CustomExceptions {
        if (companyId == 0) {
            throw new CustomExceptions(ExceptionsLibrary.INVALID_PERMISSION_ID);
        }
        if (coupon.getCompany() != null) {
            if (coupon.getCompanyId() != companyId) {
                throw new CustomExceptions(ExceptionsLibrary.INVALID_PERMISSION);
            }
        }
        coupon.setCompany(companiesRepository.findById(companyId).get());
        ////Disabled for testing
        //validStartDate(coupon.getStartDate());
        //validEndDate(coupon.getEndDate(), coupon);
        if (couponRepository.existsByTitleAndCompany(coupon.getTitle(), coupon.getCompany())) {
            throw new CustomExceptions(ExceptionsLibrary.COUPON_ALREADY_EXIST);
        }
        validEndDate(coupon.getEndDate(), coupon);
        couponRepository.save(coupon);
        System.out.println("Coupon added successfully");
        return coupon;
    }

    /**
     * this function will update coupon's details in the system.
     * The function checks if this coupon exists in the system,
     * if the company requesting the update is the coupon's company listed in database
     * if the coupon's title exist in the company except where the id of the coupon is (so I can update if coupon title didn't change )
     * and if the end date of the coupon has not expired
     *
     * @param coupon coupon object to be updated
     * @throws CustomExceptions 1: if the coupon for updating is not found in database
     *                          2: the company requesting the update is not the registered company in the coupon detail
     *                          3: if the coupon title exist in another company
     */
    public Coupon updateCoupon(Coupon coupon) throws CustomExceptions {
        coupon.setCompany(companiesRepository.findById(companyId).get());
        if (couponRepository.findById(coupon.getId()).isEmpty()) {
            throw new CustomExceptions(ExceptionsLibrary.NO_COUPON_ID);
        }
        if (couponRepository.findById(coupon.getId()).get().getCompanyId() != companyId) {
            throw new CustomExceptions(ExceptionsLibrary.INVALID_PERMISSION);
        }
        if (couponRepository.existsByTitleAndCompanyAndIdNot(coupon.getTitle(), coupon.getCompany(), coupon.getId())) {
            throw new CustomExceptions(ExceptionsLibrary.COUPON_ALREADY_EXIST_UPDATE);
        }

        validEndDate(coupon.getEndDate(), coupon);

        couponRepository.save(coupon);
        System.out.println("Coupon updated successfully");
        return coupon;
    }

    /**
     * this function will delete a coupon from the system.(Cascades)
     * The function checks if this coupon exists in the system
     *
     * @param couponId the coupon object to be deleted from database
     * @throws CustomExceptions if the coupon for deleting is not found in database
     */
    public void deleteCoupon(int couponId) throws CustomExceptions {
        if (couponRepository.findById(couponId).isEmpty()) {
            throw new CustomExceptions(ExceptionsLibrary.NO_COUPON_ID);
        }
        if (couponRepository.findById(couponId).get().getCompanyId() != companyId) {
            throw new CustomExceptions(ExceptionsLibrary.INVALID_PERMISSION);
        }

        couponRepository.deleteById(couponId);
        System.out.println("Coupon deleted successfully");
    }

    /**
     * This function returns all the coupons of a particular company.
     *
     * @return Set of all coupons of this company
     *
     */
    public Set<Coupon> getAllCompanyCoupons() {
//        if (companyService.findCompanyCoupons(companyId).isEmpty()){
//            throw new CustomExceptions(ExceptionsLibrary.EMPTY_LIST);
//        }else {
        return companiesRepository.findCompanyCoupons(companyId);
//        }
    }

    /**
     * This function returns one specific coupon of a particular company.
     *
     * @return Coupon object with a specific id
     * @throws CustomExceptions if the coupon id is not listed in the company's database
     */
    public Coupon getOneCoupon(int couponId) throws CustomExceptions {
        if (couponRepository.findById(couponId).isEmpty()) {
            throw new CustomExceptions(ExceptionsLibrary.NO_COUPON_ID);
        }

        if (couponRepository.findById(couponId).get().getCompanyId() != companyId) {
            throw new CustomExceptions(ExceptionsLibrary.INVALID_PERMISSION);
        }

        return couponRepository.findById(couponId).get();
    }

    /**
     * This function returns all company coupons by a particular category.
     *
     * @return Set of all coupons of this company with the specific category from the input
     * @throws CustomExceptions if the category of the coupon requested is not found in database
     */
    public Set<Coupon> getCompanyCouponsByCategory(Category category) throws CustomExceptions {
        Set<Coupon> coupons = companiesRepository.findCompanyCouponsByCategory(category, companyId);
        if (coupons.isEmpty()) {
            throw new CustomExceptions(ExceptionsLibrary.NO_COUPONS_BY_CATEGORY);
        }
        return coupons;
    }

    /**
     * This function returns all company coupons by a defined maximum price.
     *
     * @param maxPrice defines the maximum price of the requested coupons.
     * @return Set of all coupons with a max price lower than the input double
     * @throws CustomExceptions if there are no coupons under the specified max price
     */
    public Set<Coupon> getCompanyCouponByMaxPrice(double maxPrice) throws CustomExceptions {
        Set<Coupon> coupons = companiesRepository.findCompanyCouponsByMaxPrice(maxPrice, companyId);
        if (coupons.isEmpty()) {
            throw new CustomExceptions(ExceptionsLibrary.NO_COUPONS_BY_PRICE);
        }
        return coupons;
    }

    /**
     * This function returns the details about the company.
     *
     * @return This company object
     * @throws CustomExceptions in case the company is not found in database
     */
    public String getCompanyDetails() throws CustomExceptions {
        Company company = companiesRepository.findById(companyId).get();
        company.setCoupons(getAllCompanyCoupons());
        if (company == null) {
            throw new CustomExceptions(ExceptionsLibrary.NO_COMPANY_ID);
        }
        return company.toString() + company.getCoupons();
    }

    /**
     * The function checks that the start date of the coupon is not previous of the current day of coupon creation.
     *
     * @param date date to be checked
     * @throws CustomExceptions if the start date of the coupon  is previous to current day of creation
     */
    private void validStartDate(Date date) throws CustomExceptions {
        Date currDate = Date.valueOf(LocalDate.now());
        if (date.before(currDate)) {
            throw new CustomExceptions(ExceptionsLibrary.COUPON_EXPIRED);
        }
    }

    /**
     * The function checks that the end date of the coupon is correct.
     *
     * @param date date to be checked
     * @throws CustomExceptions if the end date of the coupon is previous to the start day;
     *                          or if the end date is previous to the current day of creation
     */
    private void validEndDate(Date date, Coupon coupon) throws CustomExceptions {
        Date StartDate = coupon.getStartDate();
        if (date.before(StartDate) || date.before(Date.valueOf(LocalDate.now()))) {
            throw new CustomExceptions(ExceptionsLibrary.COUPON_IS_EXPIRED);
        }
    }

    public int getCompanyId() {
        return companyId;
    }
}

