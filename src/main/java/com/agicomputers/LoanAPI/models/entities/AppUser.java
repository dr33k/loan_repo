package com.agicomputers.LoanAPI.models.entities;

import com.agicomputers.LoanAPI.models.enums.UserOccupation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "app_user")

public class AppUser implements UserDetails {
    @Id
  @SequenceGenerator(name="app_user_generator", sequenceName = "app_user_generator",allocationSize = 1)
    @GeneratedValue(generator ="app_user_generator",strategy = GenerationType.SEQUENCE)

    @Column(nullable = false )
    private long appUserId;

    @Column(nullable = true, unique = true)
    private String appUserUid;          //UID

    @Column( nullable = false)
    private String appUserFname;

    @Column(nullable = false)
    private String appUserLname;
    
    @ManyToOne
    @JoinColumn(name="role_id",referencedColumnName = "role_id")
    private Role appUserRole;

    @Column(nullable = false)
    private LocalDate appUserDob;

    @Column(nullable = false, unique = true)
    private String appUserEmail;

    @Column( length = 20, nullable = false)
    private String appUserPhone1;

    @Column( length = 20, nullable = false)
    private String appUserPhone2;

    @Column(nullable = false, length = 512)
    private String appUserAddress;

    @Column(nullable = false)
    private String appUserPassphrase;

    @Column(nullable = false)
    private Float appUserBalance = 0.0F;

    @Column(nullable = false, unique = true)
    private String appUserNIN;

    @Column(nullable = true, unique = true)
    private String appUserBVN;

    @Column
    private String appUserPassportPhoto;

    @Column
    private String appUserNINPhoto;

    @Column(nullable = false)
    private UserOccupation appUserOccupation;

    @Column
    private String appUserOccupationLocation;

    @Column
    private Boolean appUserRewarded = false;

    //Derived Properties from UserDetails
    @Column
    private Boolean isAccountNonExpired = true;

    @Column
    private Boolean isAccountNonLocked = true;

    @Column
    private Boolean isCredentialsNonExpired = true;

    @Column
    private Boolean isEnabled = true;


    public AppUser(){}
    public AppUser(String appUserUid, String appUserPassphrase, Role appUserRole) {
        this.appUserUid = appUserUid;
        this.appUserRole = appUserRole;
        this.appUserPassphrase = appUserPassphrase;
    }

    //Getters & Setters
    public long getAppUserId() {
        return this.appUserId;
    }

    public void setId(long appUserId) {
        this.appUserId = appUserId;
    }

    public String getAppUserUid() {
        return this.appUserUid;
    }

    public void setAppUserUid(String appUserUid) {
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
    public Role getAppUserRole() {    return appUserRole;    }

    public void setAppUserRole(Role appUserRole) {   this.appUserRole = appUserRole;    }

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



    //User Service Methods
    @Override
    public String getUsername() {
        return this.appUserUid;
    }

    @Override
    public String getPassword() {
        return appUserPassphrase;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = Set.of(appUserRole.getRoleAuthorities())
                .stream()
                .map((authority)->{return new SimpleGrantedAuthority(authority);})
                .collect(Collectors.toSet());
        return authorities;    }

    @Override
    public boolean isAccountNonExpired() { return isAccountNonExpired;    }

    @Override
    public boolean isAccountNonLocked() {  return isAccountNonLocked;    }

    @Override
    public boolean isCredentialsNonExpired() { return isCredentialsNonExpired;  }

    @Override
    public boolean isEnabled() {    return isEnabled;    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public void setAuthorities(String... authorities){
        appUserRole.setRoleAuthorities(authorities);
    }
}
