package com.agicomputers.LoanAPI.controllers;
import com.agicomputers.LoanAPI.models.dto.CustomerDTO;

import com.agicomputers.LoanAPI.models.request.CustomerRequest;
import com.agicomputers.LoanAPI.models.response.CustomerResponse;
import com.agicomputers.LoanAPI.services.CustomerService;
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
	CustomerService customerService;
	@Autowired
	CustomerValidator customerValidator;

    @GetMapping
	public HashSet<CustomerResponse> getAllCustomers(){
	Set<CustomerResponse> customerResponseSet = new HashSet<>(0);
	Set<CustomerDTO> customerDtoSet = customerService.getAllCustomers();

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
    	CustomerDTO cdto = customerService.getCustomer(customerId);

    	if(cdto == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not Found");

		CustomerResponse customerResponse = new CustomerResponse();
    	BeanUtils.copyProperties(cdto,customerResponse);
    	return customerResponse;
	}

	@PostMapping
	public CustomerResponse createCustomer(@RequestBody CustomerRequest request){
    	//Create a DTO
		CustomerDTO cdto = new CustomerDTO();
		BeanUtils.copyProperties(request,cdto);

    	//Validate and purify input
		customerValidator.setCdto(cdto);
		cdto= customerValidator.cleanObject();
		LinkedHashMap<String, String> errors = customerValidator.validate();

		//Create an object of the return type
		CustomerResponse customerResponse = new CustomerResponse();
		//Handle errors if any
		if(errors.isEmpty()) {
			cdto = customerService.createCustomer(cdto);
			BeanUtils.copyProperties(cdto, customerResponse);
		}
		else	{
			customerResponse.setErrors(errors);
			customerValidator.setErrors(new LinkedHashMap<>(0));//Clear errors from previous request
		}

		return customerResponse;
	}
}
