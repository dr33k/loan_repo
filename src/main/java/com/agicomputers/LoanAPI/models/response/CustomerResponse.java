package com.agicomputers.LoanAPI.models.response;

import com.agicomputers.LoanAPI.models.enums.CustomerOccupation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;

@AllArgsConstructor
@NoArgsConstructor

public  class CustomerResponse implements Serializable{
    private String customerId;
    private String customerFname;
    private String customerLname;
    private LocalDate customerDob;
    private String customerEmail;
    private String customerPhone1;
    private String customerPhone2;
    private String customerAddress;
    private Float customerBalance;
    private String customerNIN;
    private String customerPassportPhoto;
    private transient CustomerOccupation customerOccupation;
    private String customerOccupationLocation;
    private Boolean customerRewarded = false;
    private Boolean isAuthenticated = false;
    private LinkedHashMap errors;

    public LinkedHashMap getErrors() {
        return errors;
    }

    public void setErrors(LinkedHashMap errors) {
        this.errors = errors;
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
}