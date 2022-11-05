package com.example.OmerMoyalCouponProjectV2.repositories;

import com.example.OmerMoyalCouponProjectV2.beans.Company;
import com.example.OmerMoyalCouponProjectV2.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    //crud repository - 50%
    //smart dialect - 40%
    Coupon findByTitleAndCompany(String title, int companyID);
    Boolean existsByTitleAndCompany(String title, Company company);

    Boolean existsByTitleAndCompanyAndIdNot(String title ,Company company,int id);

    //SQL queries - 10%

    /**
     * delete coupon that there are expired on the current date.
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM `couponsprojectv2`.`coupons` WHERE (end_Date) < curDate()", nativeQuery = true)
    void deleteCouponsByDate();
}