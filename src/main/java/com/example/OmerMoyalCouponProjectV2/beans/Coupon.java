package com.example.OmerMoyalCouponProjectV2.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    //@JsonManagedReference
    private Company company;
    @ManyToMany
    //@JoinTable(name = "customers_coupons",joinColumns = @JoinColumn(name = "coupons_id"),inverseJoinColumns = @JoinColumn(name = "customers_id"))

    //@Cascade(org.hibernate.annotations.CascadeType.DELETE)
    //@JsonBackReference(value = "customer-coupons")
    @JsonIgnore
    private Set<Customer> customers;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    @Lob
    private String image;

    public Coupon() {

    }

    public Coupon(Company company, Set<Customer> customers, Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.company = company;
        this.customers = customers;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }
    public Coupon(  Category category, String title, String description, Date startDate, Date endDate, int amount, double price, String image) {
        this.company = null;
        this.customers = null;
        this.category = category;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public int getCompanyId(){
        return this.company.getId();
    }

    @Override
    public String toString() {
        return "\nCoupon{" +
                "id=" + id +
               // ", company=" + company.getId() +
             //   ", customers=" + customers +
                ", category=" + category +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
