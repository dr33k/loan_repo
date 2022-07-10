package com.agicomputers.LoanAPI.controllers.user_controllers;

import com.agicomputers.LoanAPI.models.dto.AppUserDTO;
import com.agicomputers.LoanAPI.models.dto.RoleDTO;
import com.agicomputers.LoanAPI.models.enums.AppUserRole;
import com.agicomputers.LoanAPI.models.request.AppUserRequest;
import com.agicomputers.LoanAPI.models.request.RoleRequest;
import com.agicomputers.LoanAPI.models.response.AppUserResponse;
import com.agicomputers.LoanAPI.models.response.Response;
import com.agicomputers.LoanAPI.repositories.user_repositories.RoleRepository;
import com.agicomputers.LoanAPI.security.AppUserAuthentication;
import com.agicomputers.LoanAPI.services.role_service.RoleService;
import com.agicomputers.LoanAPI.services.user_services.AppUserServiceImpl;
import com.agicomputers.LoanAPI.services.user_services.AppUserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Collectors;

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

    private Collection<GrantedAuthority> authorities = (Set<GrantedAuthority>)authentication.getInstance().getAuthorities();


    @GetMapping("/{uid}")
    public ResponseEntity<AppUserResponse> getUser(@PathVariable String uid) {
        //Validate the Uid provided
        if (appUserValidatorService.validateUid(uid)) {
            //Return appUser if any
            AppUserDTO udto = appUserService.getUser(uid);
            //Handle error if any
            if (udto == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not Found; Valid UID but User account may have been deleted ");
            // Instance of VisibleAppUserData, not all details should be exposed to the client
            AppUserResponse.VisibleAppUserData vaud = AppUserResponse.getVisibleAppUserDataInstance();
            BeanUtils.copyProperties(udto,vaud);
            //Return entity containing the users
            return ResponseEntity.ok(
                    AppUserResponse.builder()
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .userDataSet(Set.of(vaud))
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid UID");
    }

    @GetMapping
    public ResponseEntity<AppUserResponse> getAllUsers() {
        //Create a data structure to hold all appUsers returned from database
        Set<AppUserDTO> appUserDtoSet = appUserService.getAllUsers();
        // Instance of VisibleAppUserData, not all details should be exposed to the client
        AppUserResponse.VisibleAppUserData vaud = AppUserResponse.getVisibleAppUserDataInstance();
        //Using a stream, convert the Set ofAppUserDTO types into a Set of VisibleAppUserData
        Set<AppUserResponse.VisibleAppUserData> vaudSet = appUserDtoSet.stream().map(
                (AppUserDTO udto) -> {
                    BeanUtils.copyProperties(udto, vaud);
                    return vaud;
                }
        ).collect(Collectors.toSet());


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

    @DeleteMapping("/{uid}")
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
            }
            else throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not Found; Valid UID but User account may have been deleted ");
        }
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Invalid UID");

    }

    @PutMapping("/{uid}")
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
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid UID");
    }

    private ResponseEntity<AppUserResponse> userOrError(LinkedHashMap<String, String> errors, AppUserDTO udto, String uid) {

        //Handle errors if any
        if (errors.isEmpty()) {
            //Identify the DTO object
            udto.setAppUserUid(uid);

            //Performs a role update if requested
            if(udto.getAppUserRole() != null)udto = performRoleUpdateIfAny(udto);


            //Finally, update user, reuse udto
            udto = appUserService.updateUser(udto);


            //If appUser with appUserId exists in database
            if (udto != null) {

                // Instance of VisibleAppUserData, not all details should be exposed to the client
                AppUserResponse.VisibleAppUserData vaud = AppUserResponse.getVisibleAppUserDataInstance();
                BeanUtils.copyProperties(udto,vaud);
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
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not Found; Valid UID but User account may have been deleted ");
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

    private AppUserDTO performRoleUpdateIfAny(AppUserDTO udto){
        udto.setRole( roleService.getRoleObject(udto.getAppUserRole().name()));
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

    @GetMapping("/roles/{rolename}")
    public ResponseEntity<Response> getRole(String roleName) {
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
    }

}
