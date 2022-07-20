package com.agicomputers.LoanAPI.controllers.user_controllers;

import com.agicomputers.LoanAPI.models.dto.AppUserDTO;
import com.agicomputers.LoanAPI.models.request.AppUserRequest;
import com.agicomputers.LoanAPI.models.response.AppUserResponse;
import com.agicomputers.LoanAPI.models.response.VisibleAppUserData;
import com.agicomputers.LoanAPI.services.user_services.AppUserServiceImpl;
import com.agicomputers.LoanAPI.services.user_services.AppUserValidatorService;
import com.esotericsoftware.kryo.io.Input;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Set;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {
    private final AppUserServiceImpl appUserService;
    @Autowired
    private AppUserValidatorService appUserValidatorService;

    private static final String PASSPORT_PHOTO_PATH = "C:\\Users\\Emmanuel Obiakor\\Pictures\\Passport Photos";

    @PostMapping()
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
            VisibleAppUserData vaud = new VisibleAppUserData();
            BeanUtils.copyProperties(udto, vaud);
            //Return ResponseEntity containing the users
            return ResponseEntity.created(URI.create("/photoUpload")).body(
                    AppUserResponse.builder()
                            .status(HttpStatus.CREATED)
                            .statusCode(HttpStatus.CREATED.value())
                            .userDataSet(Set.of(vaud))
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        } else {
            appUserValidatorService.setErrors(new LinkedHashMap<>(0));//Clear errors from previous request

            return ResponseEntity.badRequest().body(
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

    @PostMapping("/photoUpload")
    public ResponseEntity<AppUserResponse> processPhoto(@RequestBody String appUserPhoto) throws IOException {
        ByteArrayInputStream input = new ByteArrayInputStream(appUserPhoto.getBytes(StandardCharsets.UTF_8));
        BufferedImage bufferedImage = ImageIO.read(input);
        ImageIO.write(bufferedImage,"jpg",Paths.get(PASSPORT_PHOTO_PATH).toFile());
        return null;
    }

}
