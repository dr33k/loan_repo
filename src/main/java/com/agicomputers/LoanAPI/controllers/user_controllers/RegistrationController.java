package com.agicomputers.LoanAPI.controllers.user_controllers;

import com.agicomputers.LoanAPI.models.dto.AppUserDTO;
import com.agicomputers.LoanAPI.models.request.AppUserRequest;
import com.agicomputers.LoanAPI.models.response.AppUserResponse;
import com.agicomputers.LoanAPI.services.user_services.AppUserServiceImpl;
import com.agicomputers.LoanAPI.services.user_services.AppUserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Set;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final AppUserServiceImpl appUserService;
    @Autowired
    private AppUserValidatorService appUserValidatorService;

    @PostMapping("/register")
    public ResponseEntity<AppUserResponse> createUser(@RequestBody AppUserRequest request) {
        //Create a DTO
        AppUserDTO udto = new AppUserDTO();
        BeanUtils.copyProperties(request, udto);

        //Validate and purify input using the UserValidator Component
        appUserValidatorService.setDto(udto);
        udto = appUserValidatorService.cleanObject();
        LinkedHashMap<String, String> errors = appUserValidatorService.validate();

        //Handle errors if any
        if (errors.isEmpty()) {
            udto = appUserService.createUser(udto);


            // Instance of VisibleAppUserData, not all details should be exposed to the client
            AppUserResponse.VisibleAppUserData vaud = AppUserResponse.getVisibleAppUserDataInstance();
            BeanUtils.copyProperties(udto, vaud);
            //Return ResponseEntity containing the users
            return ResponseEntity.ok(
                    AppUserResponse.builder()
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .userDataSet(Set.of(vaud))
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        } else {
            appUserValidatorService.setErrors(new LinkedHashMap<>(0));//Clear errors from previous request

            return ResponseEntity.ok(
                    //Return ResponseEntity containing the errors
                    AppUserResponse.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .errors(errors)
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        }

    }

}
