package com.example.OmerMoyalCouponProjectV2.beans;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table (name = "companies")
public class Company {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    //@JsonBackReference
    @OneToMany( cascade = CascadeType.REMOVE,fetch = FetchType.EAGER, mappedBy = "company")
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Set<Coupon> coupons;

    public Company(){

    }

    public Company(String name, String email, String password, Set<Coupon> coupons) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.coupons = coupons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Coupon> getCoupons() {
        return coupons;
    }

    public void setCoupons(Set<Coupon> coupons) {
        this.coupons = coupons;
    }
    @Override
    public String toString() {
        return "\nCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
