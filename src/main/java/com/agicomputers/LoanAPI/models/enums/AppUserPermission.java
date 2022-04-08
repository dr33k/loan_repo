package com.agicomputers.LoanAPI.models.enums;

public enum AppUserPermission {
    CUSTOMER_READ("customer:read"),
    CUSTOMER_WRITE("customer:write");

    private final String permissionString;

    AppUserPermission(String permission) {
        this.permissionString = permission;
    }

    public String getPermissionString() {
        return permissionString;
    }
}
