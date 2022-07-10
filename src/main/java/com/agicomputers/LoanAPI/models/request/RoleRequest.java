package com.agicomputers.LoanAPI.models.request;

import com.agicomputers.LoanAPI.models.enums.AppUserAuthorities;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleRequest {
    private String roleName;
    private Set<AppUserAuthorities> roleAuthorities;
}
