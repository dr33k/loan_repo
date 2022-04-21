package com.agicomputers.LoanAPI.models.dto.user_dtos;
import com.agicomputers.LoanAPI.models.entities.Role;
import com.agicomputers.LoanAPI.models.enums.UserOccupation;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
public class UserDTO{
    private long id;
    private String userId;//Username
    private String userFname;
    private String userLname;
    private LocalDate userDob;
    private String userEmail;
    private String userPhone1;
    private String userPhone2;
    private String userAddress;
    private String userPassphrase;
    private Float userBalance = 0.0F;
    private String userNIN;
    private String userPassportPhoto;
    private String userNINPhoto;
    private Role userRole;
    private UserOccupation userOccupation;
    private String userOccupationLocation;
    private Boolean userRewarded = false;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public LocalDate getUserDob() {
        return userDob;
    }

    public void setUserDob(LocalDate userDob) {
        this.userDob = userDob;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhone1() {
        return userPhone1;
    }

    public void setUserPhone1(String userPhone1) {
        this.userPhone1 = userPhone1;
    }

    public String getUserPhone2() {
        return userPhone2;
    }

    public void setUserPhone2(String userPhone2) {
        this.userPhone2 = userPhone2;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPassphrase() {
        return userPassphrase;
    }

    public void setUserPassphrase(String userPassphrase) {
        this.userPassphrase = userPassphrase;
    }

    public Float getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(Float userBalance) {
        this.userBalance = userBalance;
    }

    public String getUserNIN() {
        return userNIN;
    }

    public void setUserNIN(String userNIN) {
        this.userNIN = userNIN;
    }

    public String getUserPassportPhoto() {
        return userPassportPhoto;
    }

    public void setUserPassportPhoto(String userPassportPhoto) {
        this.userPassportPhoto = userPassportPhoto;
    }

    public String getUserNINPhoto() {
        return userNINPhoto;
    }

    public void setUserNINPhoto(String userNINPhoto) {
        this.userNINPhoto = userNINPhoto;
    }

    public UserOccupation getUserOccupation() {
        return userOccupation;
    }

    public void setUserOccupation(UserOccupation userOccupation) {
        this.userOccupation = userOccupation;
    }

    public String getUserOccupationLocation() {
        return userOccupationLocation;
    }

    public void setUserOccupationLocation(String userOccupationLocation) {
        this.userOccupationLocation = userOccupationLocation;
    }

    public Role getUserRole() {  return userRole;    }

    public void setUserRole(Role userRole) {  this.userRole = userRole;   }

    public Boolean getUserRewarded() {
        return userRewarded;
    }

    public void setUserRewarded(Boolean userRewarded) {
        this.userRewarded = userRewarded;
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