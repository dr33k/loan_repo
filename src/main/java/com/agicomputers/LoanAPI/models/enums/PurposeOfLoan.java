package com.agicomputers.LoanAPI.models.enums;

public enum PurposeOfLoan {

        BUSINESS_STARTUP("BUSINESS_STARTUP"),
        DEBT("DEBT"),
        INVESTMENT("INVESTMENT"),
        SCHOOL_FEES("SCHOOL_FEES");
        private String purpose;
        PurposeOfLoan(String status){this.purpose=purpose;}

}
