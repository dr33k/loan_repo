package com.agicomputers.LoanAPI.services.role_service;

import com.agicomputers.LoanAPI.models.dto.RoleDTO;
import com.agicomputers.LoanAPI.models.entities.Role;
import com.agicomputers.LoanAPI.repositories.user_repositories.RoleRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:/application-test.properties")
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;
    private RoleService roleService;

    @BeforeEach
    void setUp(){
        roleService = new RoleService(roleRepository);
    }


    @Test
    void getAllRoles() {
        //When
        roleService.getAllRoles();
        //Then
        verify(roleRepository).findAll(PageRequest.of(0, 10));
    }

    @Test
    void getRoleDTO() {
        String roleName = "SALAMANDER";
        //When
        RoleDTO rdto = roleService.getRoleDTO(roleName);
        //Then
        verify(roleRepository).findByRoleName(roleName);
        //Also
        assertThat(rdto).isNull();
    }

    @Test
    void getRoleEntity() {
        String roleName = "SALAMANDER";
        //When
        Role r = roleService.getRoleEntity(roleName);
        //Then
        verify(roleRepository).findByRoleName(roleName);
        //Also
        assertThat(r).isNull();
    }
}