package com.agicomputers.LoanAPI.models.response;

import com.agicomputers.LoanAPI.models.entities.Role;
import com.agicomputers.LoanAPI.models.enums.UserOccupation;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

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

}

