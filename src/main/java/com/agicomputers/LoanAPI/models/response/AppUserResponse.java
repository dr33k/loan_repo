package com.agicomputers.LoanAPI.models.response;

import com.agicomputers.LoanAPI.models.dto.user_dtos.AppUserDTO;
import com.agicomputers.LoanAPI.models.entities.Role;
import com.agicomputers.LoanAPI.models.enums.UserOccupation;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppUserResponse {
    private Set<VisibleAppUserData> userDataSet;
    private LinkedHashMap errors;
    private String message;
    private int statusCode;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public static VisibleAppUserData getVisibleAppUserDataInstance() {
        return new VisibleAppUserData();
    }


    @Data
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
   public static class VisibleAppUserData {
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
        private Role appUserRole;
        private UserOccupation appUserOccupation;
        private String appUserOccupationLocation;
        private Boolean appUserRewarded;
    }
}

