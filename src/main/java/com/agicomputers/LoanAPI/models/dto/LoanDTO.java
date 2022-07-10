package com.agicomputers.LoanAPI.models.dto;

import com.agicomputers.LoanAPI.models.entities.AppUser;
import com.agicomputers.LoanAPI.models.enums.LoanStatus;
import com.agicomputers.LoanAPI.models.enums.PaymentScheduleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanDTO implements DTO{
    private AppUser appUser;
    private String loanCode;
    private Float loanPrincipal;
    private Float loanInterestRatePerMonth;
    private Integer loanPeriodMonths;
    private Float loanAmount;
    private Float loanAmountPaid = 0.0F;
    private Float loanAmountRemaining = loanAmount;
    private LoanStatus loanStatus = LoanStatus.PROCESSING;
    private LocalDateTime loanTimestamp;
    private PaymentScheduleType paymentScheduleType = PaymentScheduleType.MONTHLY;
    private String purposeOfLoan;

}
