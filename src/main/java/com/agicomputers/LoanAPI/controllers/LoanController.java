package com.agicomputers.LoanAPI.controllers;


import com.agicomputers.LoanAPI.models.dto.LoanDTO;
import com.agicomputers.LoanAPI.models.request.LoanRequest;
import com.agicomputers.LoanAPI.models.response.Response;
import com.agicomputers.LoanAPI.security.AppUserAuthentication;
import com.agicomputers.LoanAPI.services.loan_services.LoanService;
import com.agicomputers.LoanAPI.services.loan_services.LoanValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Set;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {
    @Autowired
    private final LoanService loanService;
    @Autowired
    private LoanValidatorService loanValidatorService;

    @Autowired
    private final AppUserAuthentication auth;


    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Response> getAllUsers() {
        //Create a data structure to hold all loans returned from database
        Set<LoanDTO> loanDtoSet = loanService.getAllLoans();

        //Return entity containing the users
        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .dataSet(loanDtoSet)
                        .timestamp(LocalDateTime.now())
                        .build()
        );

    }

    //@PreAuthorize("hasAuthority('appuser:read')")
    @GetMapping("/{loanCode}")
    public ResponseEntity<Response> getLoan(@PathVariable String loanCode) {

            //Return loan if any
            LoanDTO ldto = loanService.getLoan(loanCode);
            //Handle error if any
            if (ldto == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not Found");

            //Return response entity
            return ResponseEntity.ok(
                    Response.builder()
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .dataSet(Set.of(ldto))
                            .timestamp(LocalDateTime.now())
                            .build()
            );
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createLoan(@RequestBody LoanRequest request) {
        //Create a DTO
        LoanDTO ldto = new LoanDTO();
        BeanUtils.copyProperties(request, ldto);
        auth.getInstance().getName();

        //Validate and purify input using the UserValidator Component
        loanValidatorService.setDto(ldto);
        ldto = loanValidatorService.cleanObject();
        LinkedHashMap<String, String> errors = loanValidatorService.validate();

        //Handle errors if any
        if (errors.isEmpty()) {
            ldto = loanService.createLoan(ldto);

            //Return ResponseEntity
            return ResponseEntity.ok(
                    Response.builder()
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .dataSet(Set.of(ldto))
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        } else {
            loanValidatorService.setErrors(new LinkedHashMap<>(0));//Clear errors from previous request

            return ResponseEntity.ok(
                    //Return ResponseEntity containing the errors
                    Response.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .errors(errors)
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        }

    }

}
