package com.agicomputers.LoanAPI.models.request;
import com.agicomputers.LoanAPI.models.enums.AppUserRole;
import com.agicomputers.LoanAPI.models.enums.UserOccupation;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserRequest{

    private String appUserUid;
    private String appUserFname;
    private String appUserLname;
    private LocalDate appUserDob;
    private String appUserEmail;
    private String appUserPhone1;
    private String appUserPhone2;
    private String appUserAddress;
    private AppUserRole appUserRole = AppUserRole.APPUSER;
    private String appUserPassword;
    private String appUserNIN;
    private String appUserBVN;
    private UserOccupation appUserOccupation;
    private String appUserOccupationLocation;

    public String getUsername(){return this.appUserUid;}
    public String getPassword(){return this.appUserPassword;}

}