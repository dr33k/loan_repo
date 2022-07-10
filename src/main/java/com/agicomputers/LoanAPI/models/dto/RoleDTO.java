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
    private AppUserRole roleType = AppUserRole.APPUSER;
    private String[] roleAuthorities = {"app_user:read"};
    private String roleDescription;
}
