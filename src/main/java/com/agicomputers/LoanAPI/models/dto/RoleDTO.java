package com.agicomputers.LoanAPI.models.dto;

import com.agicomputers.LoanAPI.models.enums.AppUserRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO implements DTO{
    private Integer roleId;
    private String roleName;
    private String[] roleAuthoritiesArray;
    private String roleDescription;
}
