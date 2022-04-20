package com.agicomputers.LoanAPI.models.dto;
import com.agicomputers.LoanAPI.models.entities.Role;
import com.agicomputers.LoanAPI.models.enums.CustomerOccupation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
public class CustomerDTO{
    private long id;
    private String customerId;//Username
    private String customerFname;
    private String customerLname;
    private LocalDate customerDob;
    private String customerEmail;
    private String customerPhone1;
    private String customerPhone2;
    private String customerAddress;
    private String customerPassphrase;
    private Float customerBalance = 0.0F;
    private String customerNIN;
    private String customerPassportPhoto;
    private String customerNINPhoto;
    private Role customerRole;
    private CustomerOccupation customerOccupation;
    private String customerOccupationLocation;
    private Boolean customerRewarded = false;
    private Boolean isAuthenticated = false;

    //Derived Properties from User Details

    private Boolean isAccountNonExpired = false;
    private Boolean isAccountNonLocked = false;
    private Boolean isCredentialsNonExpired = false;
    private Boolean isEnabled = false;
    private Set<? extends GrantedAuthority> authorities;


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

    public Role getCustomerRole() {  return customerRole;    }

    public void setCustomerRole(Role customerRole) {  this.customerRole = customerRole;   }

    public Boolean getCustomerRewarded() {
        return customerRewarded;
    }

    public void setCustomerRewarded(Boolean customerRewarded) {
        this.customerRewarded = customerRewarded;
    }

    public Boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        isAuthenticated = authenticated;
    }


    //Getters and Setters from UserDetails
    public Boolean getAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Set<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

}