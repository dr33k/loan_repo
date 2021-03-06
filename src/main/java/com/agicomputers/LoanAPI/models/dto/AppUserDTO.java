package com.agicomputers.LoanAPI.models.dto;
import com.agicomputers.LoanAPI.models.entities.Role;
import com.agicomputers.LoanAPI.models.enums.AppUserRole;
import com.agicomputers.LoanAPI.models.enums.UserOccupation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserDTO implements DTO{
    private long appUserId;
    private String appUserUid;//Username
    private String appUserFname;
    private String appUserLname;
    private LocalDate appUserDob;
    private LocalDateTime appUserDor;
    private String appUserEmail;
    private String appUserPhone1;
    private String appUserPhone2;
    private String appUserAddress;
    private String appUserPassword;
    private Float appUserBalance = 0.0F;
    private String appUserNIN;
    private String appUserBVN;

    private String appUserPassportPhoto;
    private String appUserNINPhoto;
    private Role role;
    private AppUserRole appUserRole;
    private UserOccupation appUserOccupation;
    private String appUserOccupationLocation;
    private Boolean appUserRewarded = false;

    //Derived Properties from User Details

    private Boolean isAccountNonExpired = true;
    private Boolean isAccountNonLocked = true;
    private Boolean isCredentialsNonExpired = true;
    private Boolean isEnabled = true;
}