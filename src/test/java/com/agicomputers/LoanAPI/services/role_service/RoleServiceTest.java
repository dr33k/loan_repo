package com.agicomputers.LoanAPI.services.role_service;

import com.agicomputers.LoanAPI.models.dto.RoleDTO;
import com.agicomputers.LoanAPI.models.enums.AppUserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RoleServiceTest {
    @Autowired
    RoleService roleService;

    @Test
    void getRoleDTO() {
        RoleDTO dto = roleService.getRoleDTO("ADMIN");
        Assertions.assertNotEquals(dto,null);
    }

}