package com.agicomputers.LoanAPI.models.enums;

import net.bytebuddy.build.Plugin;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public enum AppUserRole {
    ADMIN(new HashSet<AppUserPermission>(Set.of(
            AppUserPermission.APPUSER_READ,
            AppUserPermission.APPUSER_WRITE))),
    APPUSER(new HashSet<>(Set.of(
            AppUserPermission.APPUSER_READ)));

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
