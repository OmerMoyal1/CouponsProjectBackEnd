package com.example.OmerMoyalCouponProjectV2.repositories;

import com.example.OmerMoyalCouponProjectV2.beans.Category;
import com.example.OmerMoyalCouponProjectV2.beans.Company;
import com.example.OmerMoyalCouponProjectV2.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CompaniesRepository extends JpaRepository<Company,Integer> {
    Company findByName(String name);
    Company findByEmail (String email);
    Company getByName(String name);
    Company findByEmailAndPassword(String email,String password);
    boolean existsByEmailAndPassword(String email,String password);
    boolean existsByEmailOrName(String email,String name);
    boolean existsByEmailAndIdNot(String email,int id);

    boolean existsByEmail(String email);
    boolean existsByName(String name);


    /**
     * find coupons that belong to single company by company id;
     * @param companyId the id of the company
     * @return list of coupons that belong to this company
     */
    @Query(value = "select c from Coupon c where company_Id = ?1")
    Set<Coupon> findCompanyCoupons(int companyId);

    /**
     * find coupons that belong to single company by company id and a specific category;
     * @param category the category of the coupons
     * @param companyId the id of the company
     * @return list of coupons that belong to this company and in this category
     */
    @Query(value = "select c from Coupon c where category = ?1 AND company_id =?2")
    Set<Coupon> findCompanyCouponsByCategory(Category category, int companyId);

    /**
     * find coupons that belong to single company by company id and under defined maximum price
     * @param maxPrice the top price desired for the search
     * @param companyId the id of the company
     * @return list of coupons that belong to this company and under max price
     */
    @Query(value = "select c from Coupon c where price < ?1 AND company_id =?2")
    Set<Coupon> findCompanyCouponsByMaxPrice(double maxPrice, int companyId);
}
