package com.agicomputers.LoanAPI.models.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum AppUserRole {
    ADMIN(Set.of(
            AppUserAuthorities.APPUSER_READ,
            AppUserAuthorities.APPUSER_WRITE,
            AppUserAuthorities.ADMIN_READ,
            AppUserAuthorities.ADMIN_WRITE,
            AppUserAuthorities.BLOG)),

    SUBADMIN(Set.of( AppUserAuthorities.APPUSER_READ,
                AppUserAuthorities.APPUSER_WRITE)),

    BLOGGER( Set.of(AppUserAuthorities.BLOG)),

    APPUSER(Set.of(AppUserAuthorities.APPUSER_READ));

    private final Set<AppUserAuthorities> permissions;

    AppUserRole(Set<AppUserAuthorities> permissions){
     this.permissions = permissions;
    }

    public Set<AppUserAuthorities> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        Set<SimpleGrantedAuthority> authorities = permissions.stream().map(
                (permission)->new SimpleGrantedAuthority( permission.getPermissionString()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name().toUpperCase()));
        return authorities;
    }
}
