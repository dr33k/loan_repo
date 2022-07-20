package com.agicomputers.LoanAPI.models.entities;

import com.agicomputers.LoanAPI.models.enums.LoanStatus;
import com.agicomputers.LoanAPI.models.enums.PaymentScheduleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @SequenceGenerator(name = "loan_generator", sequenceName = "loan_generator", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_generator")
    private int loanId;

    @ManyToOne
    @JoinColumn(name="app_user_id", referencedColumnName = "appUserUid")
    private AppUser appUser;
    @Column(length = 20, unique = true,nullable = false)
    private String loanCode;
    @Column(nullable = false, precision = 2)
    private Float loanPrincipal;
    @Column(nullable = false, precision = 2)
    private Float loanInterestRatePerMonth;
    @Column(nullable = false)
    private Integer loanPeriodMonths;
    @Column(nullable = false, precision = 2)
    private Float loanAmount;
    @Column(nullable = false, precision = 2)
    private Float loanAmountPaid = 0.0F;
    @Column(nullable = false, precision = 2)
    private Float loanAmountRemaining = loanAmount;
    @Column(nullable = false)
    private LoanStatus loanStatus = LoanStatus.PROCESSING;
    @Column(nullable = false)
    private LocalDateTime loanTimestamp=LocalDateTime.now();
    @Column(nullable = false)
    private PaymentScheduleType paymentScheduleType;
    @Column(nullable = false)
    private String purposeOfLoan;


}
