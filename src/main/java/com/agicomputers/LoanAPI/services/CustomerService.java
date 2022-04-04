package com.agicomputers.LoanAPI.services;

import com.agicomputers.LoanAPI.models.dto.CustomerDTO;
import com.agicomputers.LoanAPI.models.request.CustomerRequest;

import java.util.HashSet;
import java.util.Optional;

public interface CustomerService {

    HashSet<CustomerDTO> getAllCustomers();
    //HashSet<CustomerDTO> getAllCustomers(String sqlRegex);
    CustomerDTO createCustomer(CustomerDTO cdtoReq);
    CustomerDTO getCustomer(String customerId);
    CustomerDTO getCustomerWithEmail(String email);
    CustomerDTO updateCustomer(CustomerDTO cdto);
    Boolean deleteCustomer(String customerId);
    Boolean emailExists(String email);
    Boolean ninExists(String nin);

}
