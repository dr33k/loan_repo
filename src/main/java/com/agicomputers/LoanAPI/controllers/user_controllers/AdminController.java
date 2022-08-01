package com.agicomputers.LoanAPI.controllers.user_controllers;

import com.agicomputers.LoanAPI.models.dto.AppUserDTO;
import com.agicomputers.LoanAPI.models.dto.RoleDTO;
import com.agicomputers.LoanAPI.models.request.AppUserRequest;
import com.agicomputers.LoanAPI.models.response.AppUserResponse;
import com.agicomputers.LoanAPI.models.response.Response;
import com.agicomputers.LoanAPI.models.response.VisibleAppUserData;
import com.agicomputers.LoanAPI.security.AppUserAuthentication;
import com.agicomputers.LoanAPI.services.role_service.RoleService;
import com.agicomputers.LoanAPI.services.user_services.AppUserServiceImpl;
import com.agicomputers.LoanAPI.services.user_services.AppUserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

@RestController
@RequestMapping("/administrator")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final AppUserServiceImpl appUserService;
    @Autowired
    private AppUserValidatorService appUserValidatorService;

    @Autowired
    private final AppUserAuthentication authentication;

    @Autowired
    private final RoleService roleService;


    @GetMapping("/app_users/{uid}")
    public ResponseEntity<AppUserResponse> getUser(@PathVariable String uid) {
        //Validate the Uid provided
        if (appUserValidatorService.validateUid(uid)) {
            //Return appUser if any
            AppUserDTO udto = appUserService.getUser(uid);
            //Handle error if any
            if (udto == null) return ResponseEntity.notFound().build();

            // Instance of VisibleAppUserData, not all details should be exposed to the client
            VisibleAppUserData vaud = new VisibleAppUserData();
            BeanUtils.copyProperties(udto, vaud);
            //Return entity containing the users
            return ResponseEntity.ok(
                    AppUserResponse.builder()
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .userDataSet(Set.of(vaud))
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        } else return ResponseEntity.badRequest().body(
                AppUserResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid Uid")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/app_users")
    public ResponseEntity<AppUserResponse> getAllUsers() {
        //Create a data structure to hold all appUsers returned from database
        Set<AppUserDTO> appUserDtoSet = appUserService.getAllUsers();
        if(appUserDtoSet == null){
            return ResponseEntity.ok(
                    AppUserResponse.builder()
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .message("The Database of App Users is currently empty")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        }
        // Instance of VisibleAppUserData, not all details should be exposed to the client
        VisibleAppUserData vaud;
        //Using a loop, convert the Set ofAppUserDTO types into a Set of VisibleAppUserData
        Set<VisibleAppUserData> vaudSet = new LinkedHashSet<>(0);
        for (AppUserDTO udto : appUserDtoSet) {
            vaud = new VisibleAppUserData();
            BeanUtils.copyProperties(udto, vaud);
            vaudSet.add(vaud);
        }

        //Return entity containing the users
        return ResponseEntity.ok(
                AppUserResponse.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .userDataSet(vaudSet)
                        .timestamp(LocalDateTime.now())
                        .build()
        );

    }

    @DeleteMapping("/app_users/{uid}")
    public ResponseEntity<AppUserResponse> deleteUser(@PathVariable String uid) {
        //validate uid
        if (appUserValidatorService.validateUid(uid)) {
            //Return appUser if any
            Boolean response = appUserService.deleteUser(uid);
            //Handle error if any
            if (response) {
                return ResponseEntity.ok(
                        AppUserResponse.builder()
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .message("User " + uid + " deleted successfully")
                                .timestamp(LocalDateTime.now())
                                .build()
                );
            } else return ResponseEntity.notFound().build();
        } else return ResponseEntity.badRequest().body(
                AppUserResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid Uid")
                        .timestamp(LocalDateTime.now())
                        .build()
        );

    }

    @PutMapping("/app_users/{uid}")
    public ResponseEntity<AppUserResponse> updateUser(@PathVariable String uid, @RequestBody AppUserRequest request) {
        //Validate Uid
        if (appUserValidatorService.validateUid(uid)) {
            AppUserDTO udto = new AppUserDTO();
            BeanUtils.copyProperties(request, udto);

            //Validate and purify input using the UserValidator Component
            appUserValidatorService.setPost(false);//Identifies a PUT operation using 'false' as POST status
            appUserValidatorService.setDto(udto);//Sets the request dto
            udto = appUserValidatorService.cleanObject();
            LinkedHashMap<String, String> errors = appUserValidatorService.validate();

            return userOrError(errors, udto, uid);
        } else return ResponseEntity.badRequest().body(
                AppUserResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid Uid")
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    private ResponseEntity<AppUserResponse> userOrError(LinkedHashMap<String, String> errors, AppUserDTO udto, String uid) {

        //Handle errors if any
        if (errors.isEmpty()) {
            //Identify the DTO object
            udto.setAppUserUid(uid);

            //Performs a role update if requested
            if (udto.getAppUserRole() != null) udto = performRoleUpdateIfAny(udto);


            //Finally, update user, reuse udto
            udto = appUserService.updateUser(udto);


            //If appUser with appUserId exists in database
            if (udto != null) {

                // Instance of VisibleAppUserData, not all details should be exposed to the client
                VisibleAppUserData vaud = new VisibleAppUserData();
                BeanUtils.copyProperties(udto, vaud);
                return ResponseEntity.ok(
                        //Return ResponseEntity containing the user
                        AppUserResponse.builder()
                                .status(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .userDataSet(Set.of(vaud))
                                .timestamp(LocalDateTime.now())
                                .build()
                );
            } else
                return ResponseEntity.notFound().build();
        } else {
            appUserValidatorService.setErrors(new LinkedHashMap<>(0));//Clear errors from previous request
            return ResponseEntity.ok(
                    //Return ResponseEntity containing the errors
                    AppUserResponse.builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .timestamp(LocalDateTime.now())
                            .errors(errors)
                            .build()
            );
        }
    }

    private AppUserDTO performRoleUpdateIfAny(AppUserDTO udto) {
        udto.setRole(roleService.getRoleEntity(udto.getAppUserRole().name()));
        return udto;
    }

    //Roles
    @GetMapping("/roles")
    public ResponseEntity<Response> getRoles() {
        //Create a data structure to hold all Roles returned from database
        Set<RoleDTO> roleDtoSet = roleService.getAllRoles();


        //Return entity containing the users
        return ResponseEntity.ok(
                Response.builder()
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .dataSet(roleDtoSet)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("/roles/{roleName}")
    public ResponseEntity<Response> getRole(@PathVariable String roleName) {
        try {
            //Create a data structure to hold all Roles returned from database
            RoleDTO rdto = roleService.getRoleDTO(roleName);

            //Return entity containing the users
            return ResponseEntity.ok(
                    Response.builder()
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .dataSet(Set.of(rdto))
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Response.builder()
                            .timestamp(LocalDateTime.now())
                            .message(e.getMessage())
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .status(HttpStatus.BAD_REQUEST)
                            .build()
            );
        }
    }

}
