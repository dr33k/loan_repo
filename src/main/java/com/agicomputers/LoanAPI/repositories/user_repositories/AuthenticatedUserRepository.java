package com.agicomputers.LoanAPI.repositories.user_repositories;

import com.agicomputers.LoanAPI.models.entities.Customer;
import com.agicomputers.LoanAPI.models.entities.Role;
import com.agicomputers.LoanAPI.models.enums.AppUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

@Repository
public class AuthenticatedUserRepository {

    @Autowired
    PasswordEncoder passwordEncoder;

    Set<Customer> authenticatedUsers;
    final Role ROLE_CUSTOMER = new Role(1,AppUserRole.CUSTOMER.name(),AppUserRole.CUSTOMER.getAuthorities());
    final Role ROLE_ADMIN = new Role(2,AppUserRole.ADMIN.name(),AppUserRole.ADMIN.getAuthorities());
    final Role ROLE_ADMINTRAINEE = new Role(3,AppUserRole.ADMINTRAINEE.name(),AppUserRole.ADMINTRAINEE.getAuthorities());

    public Set<Customer> getAuthenticatedUsers(){

        authenticatedUsers = Set.of(
                new Customer("emelieChukwuma",passwordEncoder.encode("###hashtag###"),ROLE_ADMIN),
                new Customer("ebubeChukwuma",passwordEncoder.encode("mumu"),ROLE_ADMINTRAINEE),
                new Customer("keneChukwuma",passwordEncoder.encode("john"),ROLE_CUSTOMER)
        );
        return authenticatedUsers;
    }

    public Optional<Customer> getAuthenticatedUser(String customerId){
        return  getAuthenticatedUsers()
                .stream()
                .filter((user)->{return user.getCustomerId().equals(customerId);})
                .findFirst();
    }

}
