package com.agicomputers.LoanAPI.models.response;

import com.agicomputers.LoanAPI.models.entities.Role;
import com.agicomputers.LoanAPI.models.enums.UserOccupation;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisibleAppUserData {
    private String appUserUid;//Username
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
    private String appUserNINPhoto;
    private Role role;
    private UserOccupation appUserOccupation;
    private String appUserOccupationLocation;
    private Boolean appUserRewarded;
}