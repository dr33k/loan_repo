package com.agicomputers.LoanAPI.models.enums;

import java.util.HashSet;
import java.util.Set;

public enum AppUserRole {
    CUSTOMER(new HashSet<>()),
    ADMIN(new HashSet<AppUserPermission>(Set.of(
            AppUserPermission.CUSTOMER_READ,
            AppUserPermission.CUSTOMER_WRITE)));

    private final Set<AppUserPermission> permissions;

    AppUserRole(Set<AppUserPermission> permissions){
     this.permissions = permissions;
    }

    public Set<AppUserPermission> getPermissions() {
        return permissions;
    }
}
