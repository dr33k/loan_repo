package com.agicomputers.LoanAPI.models.request;
import com.agicomputers.LoanAPI.models.enums.UserOccupation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor

public class AppUserRequest{
    private String appUserUid;
    private String appUserFname;
    private String appUserLname;
    private LocalDate appUserDob;
    private String appUserEmail;
    private String appUserPhone1;
    private String appUserPhone2;
    private String appUserAddress;
    private String appUserPassphrase;
    private String appUserNIN;
    private String appUserBVN;
    private String appUserPassportPhoto;
    private UserOccupation appUserOccupation;
    private String appUserOccupationLocation;


    public String getAppUserUid() { return appUserUid;   }
    public void setAppUserUid(String uid) { this.appUserUid = uid; }
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

    public String getAppUserPassphrase() {
        return appUserPassphrase;
    }

    public void setAppUserPassphrase(String appUserPassphrase) {
        this.appUserPassphrase = appUserPassphrase;
    }

    public String getAppUserNIN() {
        return appUserNIN;
    }

    public void setAppUserNIN(String appUserNIN) {
        this.appUserNIN = appUserNIN;
    }
    public String getAppUserBVN() {
        return appUserBVN;
    }

    public void setAppUserBvn(String appUserBVN) {
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
}