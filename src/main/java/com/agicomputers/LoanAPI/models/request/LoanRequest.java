package com.agicomputers.LoanAPI.models.request;

import com.agicomputers.LoanAPI.models.enums.PaymentScheduleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanRequest {
    private Float loanPrincipal;
    private Integer loanPeriodMonths;
    private PaymentScheduleType  paymentScheduleType;
    private String purposeOfLoan;
}
