package com.agicomputers.LoanAPI.controllers;

import com.agicomputers.LoanAPI.models.enums.PaymentScheduleType;
import com.agicomputers.LoanAPI.models.request.LoanRequest;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(LoanController.class)
@ExtendWith(SpringExtension.class)

class LoanControllerTest {

    @Autowired
    private LoanController loanController;

    @Test
    @Disabled
    void getAllLoans() {
    }

    @Test
    @Disabled
    void getLoan() {
    }

    @Test
    @Disabled
    void getAllLoansBy() {
    }

    @Test
    @Disabled
    void createLoan() {
    }
}