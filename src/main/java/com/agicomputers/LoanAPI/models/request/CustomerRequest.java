package com.agicomputers.LoanAPI.models.request;
import com.agicomputers.LoanAPI.models.enums.CustomerOccupation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor

public class CustomerRequest{
    private String customerFname;
    private String customerLname;
    private LocalDate customerDob;
    private String customerEmail;
    private String customerPhone1;
    private String customerPhone2;
    private String customerAddress;
    private String customerPassphrase;
    private String customerNIN;
    private String customerPassportPhoto;
    private CustomerOccupation customerOccupation;
    private String customerOccupationLocation;

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
}