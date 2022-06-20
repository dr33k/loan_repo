package com.agicomputers.LoanAPI.models.enums;

public enum AppUserPermission {
    APPUSER_READ("appuser:read"),
    APPUSER_WRITE("appuser:write"),

    ADMIN_READ("admin:read"),

    ADMIN_WRITE("admin:write");

    private final String permissionString;

    AppUserPermission(String permission) {
        this.permissionString = permission;
    }

    public String getPermissionString() {
        return permissionString;
    }
}
