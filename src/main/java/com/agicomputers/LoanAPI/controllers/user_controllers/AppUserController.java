package com.agicomputers.LoanAPI.controllers.user_controllers;

import com.agicomputers.LoanAPI.models.request.AppUserRequest;
import com.agicomputers.LoanAPI.models.response.AppUserResponse;
import com.agicomputers.LoanAPI.services.user_services.AppUserServiceImpl;
import com.agicomputers.LoanAPI.services.user_services.AppUserValidatorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.agicomputers.LoanAPI.models.dto.user_dtos.AppUserDTO;

import java.util.*;

@RestController
@RequestMapping("/app_user")
public class AppUserController{

	AppUserServiceImpl appUserService;
	AppUserValidatorService appUserValidatorService;

	@Autowired
	public AppUserController(AppUserServiceImpl appUserService, AppUserValidatorService appUserValidatorService) {
		this.appUserService = appUserService;
		this.appUserValidatorService = appUserValidatorService;
	}


	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping
	public HashSet<AppUserResponse> getAllUsers(){
    	//Create a data structure to store the AppUserResponse objects
	Set<AppUserResponse> appAppUserResponseSet = new HashSet<>(0);
	//Create a data structure to hold all appUsers returned from database
	Set<AppUserDTO> appUserDtoSet = appUserService.getAllUsers();
	//Iterate through set and copy properties one after the other
	AppUserResponse cRes;
	for(AppUserDTO udto: appUserDtoSet) {
		cRes = new AppUserResponse();
		BeanUtils.copyProperties(udto,cRes);
		appAppUserResponseSet.add(cRes);
	}
	return (HashSet<AppUserResponse>) appAppUserResponseSet;
	}

	//@PreAuthorize("hasAuthority('appuser:read')")
	@GetMapping("/{uid}")
	public AppUserResponse getUser(@PathVariable String uid){
    	//Validate the Uid provided
		if(appUserService.validateUid(uid)) {
			//Return appUser if any
			AppUserDTO udto = appUserService.getUser(uid);
			//Handle error if any
			if (udto == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not Found; Valid UID but User account may have been deleted ");
			//Create return type
			AppUserResponse appAppUserResponse = new AppUserResponse();
			BeanUtils.copyProperties(udto, appAppUserResponse);
			return appAppUserResponse;
		}
		else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid UID");
	}

	@PostMapping("/register")
	public AppUserResponse createUser(@RequestBody AppUserRequest request){
    	//Create a DTO
		AppUserDTO udto = new AppUserDTO();
		BeanUtils.copyProperties(request,udto);

    	//Validate and purify input using the UserValidator Component
		appUserValidatorService.setDto(udto);
		udto= appUserValidatorService.cleanObject();
		LinkedHashMap<String, String> errors = appUserValidatorService.validate();

		//Create an object of the return type
		AppUserResponse appAppUserResponse = new AppUserResponse();
		//Handle errors if any
		if(errors.isEmpty()) {
			udto = appUserService.createUser(udto);
			BeanUtils.copyProperties(udto, appAppUserResponse);
		}
		else	{
			appAppUserResponse.setErrors(errors);
			appUserValidatorService.setErrors(new LinkedHashMap<>(0));//Clear errors from previous request
		}

		return appAppUserResponse;
	}

	//@PreAuthorize("hasAuthority('appuser:write')")
	@DeleteMapping("/{uid}")
	public String deleteUser(@PathVariable String uid){
    	//validate uid
		if (appUserService.validateUid(uid)) {
			//Return appUser if any
			Boolean response = appUserService.deleteUser(uid);
			//Handle error if any
			if (response)
				return "User ".concat(uid).concat(" deleted successfully");
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid UID");
	}

	//@PreAuthorize("hasAuthority('appuser:write')")
	@PutMapping("/{uid}")
	public AppUserResponse updateUser(@PathVariable String uid, @RequestBody AppUserRequest request) {
		//Validate Uid
		if (appUserService.validateUid(uid)) {
			AppUserDTO udtoRequest = new AppUserDTO();
			BeanUtils.copyProperties(request, udtoRequest);

			//Validate and purify input using the UserValidator Component
			appUserValidatorService.setPost(false);//Identifies a PUT operation using 'false' as POST status
			appUserValidatorService.setDto(udtoRequest);//Sets the request dto
			udtoRequest = appUserValidatorService.cleanObject();
			LinkedHashMap<String, String> errors = appUserValidatorService.validate();

			//Create an object of the return type
			AppUserResponse appAppUserResponse = new AppUserResponse();
			//Handle errors if any
			if (errors.isEmpty()) {
				//Identify the DTO object
				udtoRequest.setAppUserUid(uid);
				//Update user, reuse udtoRequest
				udtoRequest = appUserService.updateUser(udtoRequest);

				//If appUser with appUserId exists in database
				if (udtoRequest != null) {
					BeanUtils.copyProperties(udtoRequest, appAppUserResponse);
					}
				else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not Found; Valid UID but User account may have been deleted ");
				}
			else {
				appAppUserResponse.setErrors(errors);
				appUserValidatorService.setErrors(new LinkedHashMap<>(0));//Clear errors from previous request
			}
			return appAppUserResponse;
		}
		else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid UID");
	}
	}

