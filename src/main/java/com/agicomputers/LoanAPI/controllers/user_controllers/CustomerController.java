package com.agicomputers.LoanAPI.controllers.user_controllers;
import com.agicomputers.LoanAPI.models.dto.user_dtos.CustomerDTO;

import com.agicomputers.LoanAPI.models.request.UserRequest;
import com.agicomputers.LoanAPI.models.response.UserResponse;
import com.agicomputers.LoanAPI.services.user_services.CustomerUserServiceImpl;
import com.agicomputers.LoanAPI.tools.validators.user_validators.CustomerValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/customer")
public class CustomerController implements UserController {

	@Autowired
	CustomerUserServiceImpl customerService;
	@Autowired
	CustomerValidator customerValidator;

    @Override
	public HashSet<UserResponse> getAllUsers(){
    	//Create a data structure to store the UserResponse objects
	Set<UserResponse> customerResponseSet = new HashSet<>(0);
	//Create a data structure to hold all customers returned from database
	Set<CustomerDTO> customerDtoSet = customerService.getAllUsers();
	//Iterate through set and copy properties one after the other
	UserResponse cRes;
	for(CustomerDTO cdto: customerDtoSet) {
		cRes = new UserResponse();
		BeanUtils.copyProperties(cdto,cRes);
		customerResponseSet.add(cRes);
	}
	return (HashSet<UserResponse>) customerResponseSet;
	}

	@Override
	public UserResponse getUser(@PathVariable String customerId){
    	//Return customer if any
    	CustomerDTO cdto = customerService.getUser(customerId);
		//Handle error if any
    	if(cdto == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not Found");
		//Create return type
		UserResponse customerResponse = new UserResponse();
    	BeanUtils.copyProperties(cdto,customerResponse);
    	return customerResponse;
	}

	@Override
	public UserResponse createUser(@RequestBody UserRequest request){
    	//Create a DTO
		CustomerDTO cdto = new CustomerDTO();
		BeanUtils.copyProperties(request,cdto);

    	//Validate and purify input using the UserValidator Component
		customerValidator.setDto(cdto);
		cdto= customerValidator.cleanObject();
		LinkedHashMap<String, String> errors = customerValidator.validate();

		//Create an object of the return type
		UserResponse customerResponse = new UserResponse();
		//Handle errors if any
		if(errors.isEmpty()) {
			cdto = customerService.createUser(cdto);
			BeanUtils.copyProperties(cdto, customerResponse);
		}
		else	{
			customerResponse.setErrors(errors);
			customerValidator.setErrors(new LinkedHashMap<>(0));//Clear errors from previous request
		}

		return customerResponse;
	}

	@Override
	public String deleteUser(@PathVariable String customerId){
    	//Return customer if any
		Boolean response = customerService.deleteUser(customerId);
		//Handle error if any
		if (response)
			return "Customer ".concat(customerId).concat(" deleted successfully") ;
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not Found");
	}

	@Override
	public UserResponse updateUser(@PathVariable String customerId, @RequestBody UserRequest request){

		CustomerDTO cdtoRequest = new CustomerDTO();
		BeanUtils.copyProperties(request,cdtoRequest);

		//Validate and purify input using the UserValidator Component
		customerValidator.setPost(false);//Identifies a PUT operation using 'false' as POST status
		customerValidator.setDto(cdtoRequest);//Sets the request dto
		cdtoRequest = customerValidator.cleanObject();
		LinkedHashMap<String, String> errors = customerValidator.validate();

		//Create an object of the return type
		UserResponse customerResponse = new UserResponse();
		//Handle errors if any
		if(errors.isEmpty()) {
			//Identify the DTO object
			cdtoRequest.setUserId(customerId);
			//Update user, reuse cdtoRequest
			cdtoRequest = customerService.updateUser(cdtoRequest);

			//If customer with customerId exists in database
			if(cdtoRequest != null){
				BeanUtils.copyProperties(cdtoRequest, customerResponse);
			}
			else throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Customer not found");
		}
		else	{
			customerResponse.setErrors(errors);
			customerValidator.setErrors(new LinkedHashMap<>(0));//Clear errors from previous request
		}
		return customerResponse;
		}
	}

