package com.agicomputers.LoanAPI.models.request;

import com.agicomputers.LoanAPI.models.enums.PaymentScheduleType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Validated
public class LoanRequest {
/*    private static final Long MAX_PRINCIPAL = 1500000L;
    private static final Integer MAX_MONTH = 18;
    private static final String NULL="Value cannot be null";
    private static final String LENGTH="Input too little";
    private static final String CONSTRAINT_LOW="Invalid Value";
    private static final String CONSTRAINT_HIGH="Maximum Value exceeded";
*/

    @Max(value = 1500000L,message = "Unfortunately, that's more than we can handle, try a lower amount please")
    @Min(value = 0L,message = "That's too little")
    private Float loanAmount;

    @Max(value=18,message="Maximum amount of time exceeded")
    @Min(value=0,message = "Maximum amount of time exceeded")
    private Integer loanPeriodMonths;

    @NotNull(message = "You haven selected a payment schedule yet")
    private PaymentScheduleType  paymentScheduleType;

    @NotBlank(message = "Please let us know the purpose of your loan")
    private String purposeOfLoan;
}
