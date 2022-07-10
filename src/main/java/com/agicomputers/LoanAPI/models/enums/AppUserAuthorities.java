package com.agicomputers.LoanAPI.models.enums;

public enum AppUserAuthorities {
    APPUSER_READ("appuser:read"),
    APPUSER_WRITE("appuser:write"),

    ADMIN_READ("admin:read"),

    ADMIN_WRITE("admin:write"),
    BLOG("blog:write");

    private final String permissionString;

    AppUserAuthorities(String permission) {
        this.permissionString = permission;
    }

    public String getPermissionString() {
        return permissionString;
    }
}
