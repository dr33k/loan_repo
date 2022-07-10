package com.agicomputers.LoanAPI.models.enums;

public enum LoanStatus {
    PROCESSING("PROCESSING"),
    APPROVED("APPROVED_IN_PROGRESS"),
    DECLINED("DECLINED"),
    COMPLETED("PAYMENT_COMPLETED");
    private String status;
    LoanStatus(String status){this.status=status;}
}
