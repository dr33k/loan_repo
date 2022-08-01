package com.agicomputers.LoanAPI.controllers;


import com.agicomputers.LoanAPI.models.dto.AppUserDTO;
import com.agicomputers.LoanAPI.models.dto.LoanDTO;
import com.agicomputers.LoanAPI.models.entities.AppUser;
import com.agicomputers.LoanAPI.models.request.LoanRequest;
import com.agicomputers.LoanAPI.models.response.AppUserResponse;
import com.agicomputers.LoanAPI.models.response.Response;
import com.agicomputers.LoanAPI.security.AppUserAuthentication;
import com.agicomputers.LoanAPI.services.loan_services.LoanService;
import com.agicomputers.LoanAPI.services.user_services.AppUserServiceImpl;
import com.agicomputers.LoanAPI.services.user_services.AppUserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Set;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;
    private final AppUserAuthentication authentication;
    private final AppUserServiceImpl appUserService;
    private final AppUserValidatorService appUserValidatorService;

    @GetMapping
    public ResponseEntity<Response> getAllLoans() {
        //Create a data structure to hold all loans returned from database
        Set<LoanDTO> loanDtoSet = loanService.getAllLoans();

        //Return entity containing the loans
        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .dataSet(loanDtoSet)
                        .timestamp(LocalDateTime.now())
                        .build()
        );

    }

    @GetMapping("/{uid}")
    public ResponseEntity<Response> getLoan(@PathVariable String uid, @RequestParam String loanCode) {
        //Validate the Uid provided
        if (loanService.validateLoanCode(loanCode)) {
            //Check if user owns the account
            if (authentication.getInstance().getName().equals(uid)) {
                //Return loan if any
                LoanDTO ldto = loanService.getLoan(loanCode);
                if (ldto == null)
                    return ResponseEntity.notFound().build();

                //Return entity containing the loan
                return ResponseEntity.ok(
                        Response.builder()
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .dataSet(Set.of(ldto))
                                .timestamp(LocalDateTime.now())
                                .build()
                );
            } else return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    Response.builder()
                            .status(HttpStatus.FORBIDDEN)
                            .statusCode(HttpStatus.FORBIDDEN.value())
                            .message("You dont own this account")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        } else return ResponseEntity.badRequest().body(
                Response.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid Uid")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/by/{uid}")
    public ResponseEntity<Response> getAllLoansBy(@PathVariable String uid) {
        //Validate the Uid provided
        if (appUserValidatorService.validateUid(uid)) {
            //Check if user owns the account
            if (authentication.getInstance().getName().equals(uid)) {
                //Return loan if any
                Set<LoanDTO> ldtoSet = loanService.getAllLoansBy(uid);
                if (ldtoSet.isEmpty())
                    return ResponseEntity.notFound().build();

                //Return entity containing the loans
                return ResponseEntity.ok(
                        Response.builder()
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .dataSet(ldtoSet)
                                .timestamp(LocalDateTime.now())
                                .build()
                );
            } else return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    Response.builder()
                            .status(HttpStatus.FORBIDDEN)
                            .statusCode(HttpStatus.FORBIDDEN.value())
                            .message("You dont own this account")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        } else return ResponseEntity.badRequest().body(
                Response.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid Uid")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @PostMapping("/create")
    public ResponseEntity<Response> createLoan(@Valid @RequestBody LoanRequest request) {
        //Create a DTO
        LoanDTO ldto = new LoanDTO();
        BeanUtils.copyProperties(request, ldto);

        AppUser appUser = (AppUser)authentication.getInstance().getPrincipal();
            ldto.setAppUser((appUser!=null)?appUser:null);
            ldto = loanService.createLoan(ldto);

            //Return ResponseEntity
            return ResponseEntity.created(URI.create("/dashboard")).body(
                    Response.builder()
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .dataSet(Set.of(ldto))
                            .timestamp(LocalDateTime.now())
                            .build()
            );

    }

}
