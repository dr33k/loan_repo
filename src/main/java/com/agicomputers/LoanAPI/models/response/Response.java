package com.agicomputers.LoanAPI.models.response;

import com.agicomputers.LoanAPI.models.dto.DTO;
import com.agicomputers.LoanAPI.models.dto.RoleDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private Set<? extends DTO> dataSet;
    String message;
    int statusCode;
    HttpStatus status;
    LocalDateTime timestamp;

}
