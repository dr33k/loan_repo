package com.agicomputers.LoanAPI.models.response;

import com.agicomputers.LoanAPI.models.enums.UserOccupation;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashMap;

@NoArgsConstructor

public  class AppUserResponse implements Serializable{
    private String appUserUid;
    private String appUserFname;
    private String appUserLname;
    private LocalDate appUserDob;
    private String appUserEmail;
    private String appUserPhone1;
    private String appUserPhone2;
    private String appUserAddress;
    private Float appUserBalance;
    private String appUserNIN;

    private String appUserBVN;
    private String appUserPassportPhoto;
    private transient UserOccupation appUserOccupation;
    private String appUserOccupationLocation;
    private Boolean appUserRewarded = false;

    private LinkedHashMap errors;

    public LinkedHashMap getErrors() {
        return errors;
    }

    public void setErrors(LinkedHashMap errors) {
        this.errors = errors;
    }

    public String getAppUserUid() {
        return appUserUid;
    }

    public void setAppUserId(String appUserUid) {
        this.appUserUid = appUserUid;
    }

    public String getAppUserFname() {
        return appUserFname;
    }

    public void setAppUserFname(String appUserFname) {
        this.appUserFname = appUserFname;
    }

    public String getAppUserLname() {
        return appUserLname;
    }

    public void setAppUserLname(String appUserLname) {
        this.appUserLname = appUserLname;
    }

    public LocalDate getAppUserDob() {
        return appUserDob;
    }

    public void setAppUserDob(LocalDate appUserDob) {
        this.appUserDob = appUserDob;
    }

    public String getAppUserEmail() {
        return appUserEmail;
    }

    public void setAppUserEmail(String appUserEmail) {
        this.appUserEmail = appUserEmail;
    }

    public String getAppUserPhone1() {
        return appUserPhone1;
    }

    public void setAppUserPhone1(String appUserPhone1) {
        this.appUserPhone1 = appUserPhone1;
    }

    public String getAppUserPhone2() {
        return appUserPhone2;
    }

    public void setAppUserPhone2(String appUserPhone2) {
        this.appUserPhone2 = appUserPhone2;
    }

    public String getAppUserAddress() {
        return appUserAddress;
    }

    public void setAppUserAddress(String appUserAddress) {
        this.appUserAddress = appUserAddress;
    }

    public Float getAppUserBalance() {
        return appUserBalance;
    }

    public void setAppUserBalance(Float appUserBalance) {
        this.appUserBalance = appUserBalance;
    }

    public String getAppUserNIN() {
        return appUserNIN;
    }

    public void setAppUserNIN(String appUserNIN) {
        this.appUserNIN = appUserNIN;
    }
    public void setAppUserUid(String appUserUid) {
        this.appUserUid = appUserUid;
    }

    public String getAppUserBVN() {
        return appUserBVN;
    }

    public void setAppUserBVN(String appUserBVN) {
        this.appUserBVN = appUserBVN;
    }

    public String getAppUserPassportPhoto() {
        return appUserPassportPhoto;
    }

    public void setAppUserPassportPhoto(String appUserPassportPhoto) {
        this.appUserPassportPhoto = appUserPassportPhoto;
    }

    public UserOccupation getAppUserOccupation() {
        return appUserOccupation;
    }

    public void setAppUserOccupation(UserOccupation appUserOccupation) {
        this.appUserOccupation = appUserOccupation;
    }

    public String getAppUserOccupationLocation() {
        return appUserOccupationLocation;
    }

    public void setAppUserOccupationLocation(String appUserOccupationLocation) {
        this.appUserOccupationLocation = appUserOccupationLocation;
    }

    public Boolean getAppUserRewarded() {
        return appUserRewarded;
    }

    public void setAppUserRewarded(Boolean appUserRewarded) {
        this.appUserRewarded = appUserRewarded;
    }

}