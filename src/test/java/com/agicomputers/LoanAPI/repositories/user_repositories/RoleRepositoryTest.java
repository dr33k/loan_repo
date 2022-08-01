package com.agicomputers.LoanAPI.repositories.user_repositories;

import com.agicomputers.LoanAPI.models.entities.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"classpath:/application-test.properties"})
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByRoleName() {
        //Given
        String[] roleAuthorities = {"appuser:read","admin:read"};
        Role role = new Role("SALAMANDER",roleAuthorities,"A test with role name Salamander");
        roleRepository.save(role);

        //When
        Optional<Role> roleOptional = roleRepository.findByRoleName("SALAMANDER");

        //Then
        assertThat(roleOptional.get()).isNotNull();

        //Also
        assertThat(roleOptional.get().getRoleAuthoritiesArray()).hasOnlyElementsOfType(String.class);

        //Also
        assertThat(roleOptional.get().getRoleAuthoritiesArray()).contains("appuser:read","admin:read");
    }
}