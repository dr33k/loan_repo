package com.agicomputers.LoanAPI.models.enums;

public enum PaymentScheduleType {
    MONTHLY("MONTHLY"),
    BI_MONTHLY("BI_MONTHLY"),
    TRI_MONTHLY("TRI_MONTHLY");
    private String scheduleName;
    PaymentScheduleType(String scheduleName){this.scheduleName = scheduleName;}
}
