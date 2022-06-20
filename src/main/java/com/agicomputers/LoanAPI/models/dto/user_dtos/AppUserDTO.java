package com.agicomputers.LoanAPI.models.dto.user_dtos;
import com.agicomputers.LoanAPI.models.entities.Role;
import com.agicomputers.LoanAPI.models.enums.UserOccupation;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
public class AppUserDTO{
    private long appUserId;
    private String appUserUid;//Username
    private String appUserFname;
    private String appUserLname;
    private LocalDate appUserDob;
    private String appUserEmail;
    private String appUserPhone1;
    private String appUserPhone2;
    private String appUserAddress;
    private String appUserPassphrase;
    private Float appUserBalance = 0.0F;
    private String appUserNIN;
    private String appUserBVN;

    private String appUserPassportPhoto;
    private String appUserNINPhoto;
    private Role appUserRole;
    private UserOccupation appUserOccupation;
    private String appUserOccupationLocation;
    private Boolean appUserRewarded = false;

    //Derived Properties from User Details

    private Boolean isAccountNonExpired = false;
    private Boolean isAccountNonLocked = false;
    private Boolean isCredentialsNonExpired = false;
    private Boolean isEnabled = false;
    private Set<? extends GrantedAuthority> authorities;


    public long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(long id) {
        this.appUserId = id;
    }

    public String getAppUserUid() {
        return appUserUid;
    }

    public void setAppUserUid(String uid) {
        this.appUserUid = uid;
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

    public String getAppUserPassphrase() {
        return appUserPassphrase;
    }

    public void setAppUserPassphrase(String appUserPassphrase) {
        this.appUserPassphrase = appUserPassphrase;
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

    public String getAppUserNINPhoto() {
        return appUserNINPhoto;
    }

    public void setAppUserNINPhoto(String appUserNINPhoto) {
        this.appUserNINPhoto = appUserNINPhoto;
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

    public Role getAppUserRole() {  return appUserRole;    }

    public void setAppUserRole(Role appUserRole) {  this.appUserRole = appUserRole;   }

    public Boolean getAppUserRewarded() {
        return appUserRewarded;
    }

    public void setAppUserRewarded(Boolean appUserRewarded) {
        this.appUserRewarded = appUserRewarded;
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