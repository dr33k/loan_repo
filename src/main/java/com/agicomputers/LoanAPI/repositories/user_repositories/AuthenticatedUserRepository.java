/*package com.agicomputers.LoanAPI.repositories.user_repositories;

import com.agicomputers.LoanAPI.models.entities.AppUser;
import com.agicomputers.LoanAPI.models.entities.Role;
import com.agicomputers.LoanAPI.models.enums.AppUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;


@RequiredArgsConstructor
public class AuthenticatedUserRepository {

    private final PasswordEncoder passwordEncoder;

    Set<AppUser> authenticatedUsers;
    final Role ROLE_APPUSER = new Role(1,AppUserRole.APPUSER.name(),AppUserRole.APPUSER.getAuthorities());
    final Role ROLE_ADMIN = new Role(2,AppUserRole.ADMIN.name(),AppUserRole.ADMIN.getAuthorities());

    public Set<AppUser> getAuthenticatedUsers(){

        authenticatedUsers = Set.of(
                new AppUser("emelieChukwuma",passwordEncoder.encode("###hashtag###"),ROLE_ADMIN),
                new AppUser("keneChukwuma",passwordEncoder.encode("john"),ROLE_APPUSER)
        );
        return authenticatedUsers;
    }

    public Optional<AppUser> getAuthenticatedUser(String appUserUid){
        return  getAuthenticatedUsers()
                .stream()
                .filter((user)->{return user.getAppUserUid().equals(appUserUid);})
                .findFirst();
    }

}
*/