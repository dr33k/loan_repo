package com.agicomputers.LoanAPI.services.user_services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppUserValidatorServiceTest {
    @Autowired
    AppUserValidatorService appUserValidatorService;

    @Test
    void validateDob() {
        //Given
        String date = "2000-12-21";
        //Test

        appUserValidatorService.validateDob(LocalDate.parse(date));
        Assertions.assertTrue(appUserValidatorService.getErrors().isEmpty());
        System.out.println(appUserValidatorService.getErrors().toString());
    }

    @Test
    @Disabled
    void validate() {
    }

    @Test
    @Disabled
    void validateUid() {
    }
}