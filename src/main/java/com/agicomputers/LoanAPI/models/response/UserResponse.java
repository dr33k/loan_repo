package com.agicomputers.LoanAPI.models.response;

import com.agicomputers.LoanAPI.models.enums.UserOccupation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;

@NoArgsConstructor

public  class UserResponse implements Serializable{
    private String userId;
    private String userFname;
    private String userLname;
    private LocalDate userDob;
    private String userEmail;
    private String userPhone1;
    private String userPhone2;
    private String userAddress;
    private Float userBalance;
    private String userNIN;
    private String userPassportPhoto;
    private transient UserOccupation userOccupation;
    private String userOccupationLocation;
    private Boolean userRewarded = false;
    private Boolean isAuthenticated = false;
    private LinkedHashMap errors;

    public LinkedHashMap getErrors() {
        return errors;
    }

    public void setErrors(LinkedHashMap errors) {
        this.errors = errors;
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
}