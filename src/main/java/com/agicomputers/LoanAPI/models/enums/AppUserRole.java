package com.agicomputers.LoanAPI.models.enums;

import net.bytebuddy.build.Plugin;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum AppUserRole {
    CUSTOMER(new HashSet<>()),
    ADMIN(new HashSet<AppUserPermission>(Set.of(
            AppUserPermission.CUSTOMER_READ,
            AppUserPermission.CUSTOMER_WRITE))),
    ADMIN_TRAINEE(new HashSet<>(Set.of(
            AppUserPermission.CUSTOMER_READ)));

    private final Set<AppUserPermission> permissions;

    AppUserRole(Set<AppUserPermission> permissions){
     this.permissions = permissions;
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        Set<SimpleGrantedAuthority> authorities = permissions.stream().map(
                (permission)->new SimpleGrantedAuthority( permission.getPermissionString()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}
