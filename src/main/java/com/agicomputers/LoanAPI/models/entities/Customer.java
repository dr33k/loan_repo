package com.agicomputers.LoanAPI.models.entities;

import com.agicomputers.LoanAPI.models.enums.CustomerOccupation;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto Incremented

    @Column(nullable = false )
    private long id;

    @Column(nullable = false, unique = true)
    private String customerId;

    @Column( columnDefinition = "VARCHAR(255) NOT NULL CONSTRAINT f_name_check CHECK(CHAR_LENGTH(customer_fname) > 1)")
    private String customerFname;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL CONSTRAINT l_name_check CHECK(CHAR_LENGTH(customer_lname) > 1)")
    private String customerLname;

    @Column(nullable=false)
    @ManyToOne
    @JoinColumn(name="role_id",referencedColumnName = "role.id")
    private Role role;

    @Column(nullable = false)
    private LocalDate customerDob;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL CONSTRAINT email_check CHECK( customer_email LIKE '%@%.%')")
    private String customerEmail;

    @Column( columnDefinition = "VARCHAR(20) NOT NULL CONSTRAINT phone_1_check CHECK(customer_phone1 REGEXP '^[+-][0123456789]{11,15}')")
    private String customerPhone1;
    @Column(columnDefinition = "VARCHAR(20) NOT NULL CONSTRAINT phone_2_check CHECK(customer_phone2 REGEXP '^[+-][0123456789]{11,15}')")
    private String customerPhone2;
    @Column(nullable = false, length = 512)
    private String customerAddress;
    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String customerPassphrase;
    @Column(columnDefinition = "FLOAT NOT NULL DEFAULT 0.0")
    private Float customerBalance;
    @Column(columnDefinition = "VARCHAR(255) NOT NULL CONSTRAINT nin_check CHECK(customernin REGEXP '^[0123456789]{11}$')")
    private String customerNIN;
    @Column(columnDefinition = "VARCHAR(255)")
    private String customerPassportPhoto;
    @Column(columnDefinition = "VARCHAR(255)")
    private String customerNINPhoto;
    @Column(nullable = false)
    private CustomerOccupation customerOccupation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFname() {
        return customerFname;
    }

    public void setCustomerFname(String customerFname) {
        this.customerFname = customerFname;
    }

    public String getCustomerLname() {
        return customerLname;
    }

    public void setCustomerLname(String customerLname) {
        this.customerLname = customerLname;
    }

    public LocalDate getCustomerDob() {
        return customerDob;
    }

    public void setCustomerDob(LocalDate customerDob) {
        this.customerDob = customerDob;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone1() {
        return customerPhone1;
    }

    public void setCustomerPhone1(String customerPhone1) {
        this.customerPhone1 = customerPhone1;
    }

    public String getCustomerPhone2() {
        return customerPhone2;
    }

    public void setCustomerPhone2(String customerPhone2) {
        this.customerPhone2 = customerPhone2;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPassphrase() {
        return customerPassphrase;
    }

    public void setCustomerPassphrase(String customerPassphrase) {
        this.customerPassphrase = customerPassphrase;
    }

    public Float getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(Float customerBalance) {
        this.customerBalance = customerBalance;
    }

    public String getCustomerNIN() {
        return customerNIN;
    }

    public void setCustomerNIN(String customerNIN) {
        this.customerNIN = customerNIN;
    }

    public String getCustomerPassportPhoto() {
        return customerPassportPhoto;
    }

    public void setCustomerPassportPhoto(String customerPassportPhoto) {
        this.customerPassportPhoto = customerPassportPhoto;
    }

    public String getCustomerNINPhoto() {
        return customerNINPhoto;
    }

    public void setCustomerNINPhoto(String customerNINPhoto) {
        this.customerNINPhoto = customerNINPhoto;
    }

    public CustomerOccupation getCustomerOccupation() {
        return customerOccupation;
    }

    public void setCustomerOccupation(CustomerOccupation customerOccupation) {
        this.customerOccupation = customerOccupation;
    }

    public String getCustomerOccupationLocation() {
        return customerOccupationLocation;
    }

    public void setCustomerOccupationLocation(String customerOccupationLocation) {
        this.customerOccupationLocation = customerOccupationLocation;
    }

    public Boolean getCustomerRewarded() {
        return customerRewarded;
    }

    public void setCustomerRewarded(Boolean customerRewarded) {
        this.customerRewarded = customerRewarded;
    }

    public Boolean getAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        isAuthenticated = authenticated;
    }

    @Column(columnDefinition = "VARCHAR(255)")
    private String customerOccupationLocation;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean customerRewarded = false;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isAuthenticated = false;
}
