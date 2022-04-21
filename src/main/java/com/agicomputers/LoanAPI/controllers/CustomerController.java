package com.agicomputers.LoanAPI.controllers;
import com.agicomputers.LoanAPI.models.dto.CustomerDTO;

import com.agicomputers.LoanAPI.models.request.CustomerRequest;
import com.agicomputers.LoanAPI.models.response.CustomerResponse;
import com.agicomputers.LoanAPI.services.UserService;
import com.agicomputers.LoanAPI.services.serviceimpobject.CustomerUserServiceImpl;
import com.agicomputers.LoanAPI.tools.validators.CustomerValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerUserServiceImpl customerService;
	@Autowired
	CustomerValidator customerValidator;

    @GetMapping
	public HashSet<CustomerResponse> getAllCustomers(){
    	//Create a data structure to store the CustomerResponse objects
	Set<CustomerResponse> customerResponseSet = new HashSet<>(0);
	//Create a data structure to hold all customers returned from database
	Set<CustomerDTO> customerDtoSet = customerService.getAllUsers();
	//Iterate through set and copy properties one after the other
	CustomerResponse cRes;
	for(CustomerDTO cdto: customerDtoSet) {
		cRes = new CustomerResponse();
		BeanUtils.copyProperties(cdto,cRes);
		customerResponseSet.add(cRes);
	}
	return (HashSet<CustomerResponse>) customerResponseSet;
	}

	@GetMapping("/{customerId}")
	public CustomerResponse getCustomer(@PathVariable String customerId){
    	//Return customer if any
    	CustomerDTO cdto = customerService.getUser(customerId);
		//Handle error if any
    	if(cdto == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not Found");
		//Create return type
		CustomerResponse customerResponse = new CustomerResponse();
    	BeanUtils.copyProperties(cdto,customerResponse);
    	return customerResponse;
	}

	@PostMapping
	public CustomerResponse createCustomer(@RequestBody CustomerRequest request){
    	//Create a DTO
		CustomerDTO cdto = new CustomerDTO();
		BeanUtils.copyProperties(request,cdto);

    	//Validate and purify input using the CustomerValidator Component
		customerValidator.setCdto(cdto);
		cdto= customerValidator.cleanObject();
		LinkedHashMap<String, String> errors = customerValidator.validate();

		//Create an object of the return type
		CustomerResponse customerResponse = new CustomerResponse();
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

	@DeleteMapping("/{customerId}")
	public String deleteCustomer(@PathVariable String customerId){
    	//Return customer if any
		Boolean response = customerService.deleteUser(customerId);
		//Handle error if any
		if (response)
			return "Customer ".concat(customerId).concat(" deleted successfully") ;
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not Found");
	}

	@PutMapping("/{customerId}")
	public CustomerResponse updateCustomer(@PathVariable String customerId, @RequestBody CustomerRequest request){

		CustomerDTO cdtoRequest = new CustomerDTO();
		BeanUtils.copyProperties(request,cdtoRequest);

		//Validate and purify input using the CustomerValidator Component
		customerValidator.setPost(false);//Identifies a PUT operation using 'false' as POST status
		customerValidator.setCdto(cdtoRequest);//Sets the request dto
		cdtoRequest = customerValidator.cleanObject();
		LinkedHashMap<String, String> errors = customerValidator.validate();

		//Create an object of the return type
		CustomerResponse customerResponse = new CustomerResponse();
		//Handle errors if any
		if(errors.isEmpty()) {
			//Identify the DTO object
			cdtoRequest.setCustomerId(customerId);
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

