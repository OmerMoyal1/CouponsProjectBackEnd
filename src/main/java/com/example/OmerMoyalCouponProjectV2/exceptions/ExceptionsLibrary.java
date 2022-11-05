package com.example.OmerMoyalCouponProjectV2.exceptions;

public enum ExceptionsLibrary {
    /**
     * General exceptions
     */
    NAME_EXIST("Cannot add existing name."),
    EMAIL_EXIST("Email already exists."),
    ID_NOT_EXIST("ID not exist in database."),
    CAN_NOT_CHANGE_NAME("Can't change company name"),

    /**
     * coupons exceptions
     */
    COUPON_ALREADY_EXIST("Cannot add coupon, coupon title already exist in this company."),
    COUPON_ALREADY_EXIST_UPDATE("Cannot update coupon,title already exist in company."),
    COUPON_TITLE_EXIST("Cannot add coupon, title already exist."),
    COUPON_PURCHASED("Cannot purchase this item, already in your list."),
    NO_COUPONS("No coupons in database."),
    COUPONS_OUT_OF_STOCK("Coupons out of stock."),
    NO_COUPONS_BY_CATEGORY("No coupons in this category."),
    NO_COUPONS_BY_PRICE("No coupons under this price."),
    NO_COUPON_ID("No coupons with that id"),
    COUPON_EXPIRED("you cant purchase expired coupon"),

    COUPON_IS_EXPIRED("Coupon is expired"),
    /**
     * company exceptions
     */
    NO_COMPANY_ID("No company with this ID in database "),
    NO_COMPANY_NAME("No company with this name in the database"),
    COMPANY_DOES_NOT_HAVE_THIS_COUPON("Company doesn't have this coupon."),
    INVALID_PERMISSION("you are not allowed to do this"),
    INVALID_PERMISSION_ID("you are not allowed to do this ,id is not your company_id"),

    /**
     * customer exceptions
     */
    NO_CUSTOMER("No customer with this ID in database."),
    /**
     * category exceptions
     */
    CATEGORY_EXIST("Category already exist in database."),
    CATEGORY_NOT_EXIST("Category doesn't exist in database."),
    /**
     * login exceptions
     */
    NOT_ADMIN("You are not an Administrator."),
    FAIL_2_CONNECT("Fail to connect, please check entered email and password for error.");

    private String message;

    private ExceptionsLibrary(String message){
        setMessage(message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
