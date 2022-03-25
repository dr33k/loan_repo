package com.agicomputers.LoanAPI.models.enums;

public enum AppUserPermission {
    CUSTOMER_READ("customer:read"),
    CUSTOMER_WRITE("customer:write");

    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
