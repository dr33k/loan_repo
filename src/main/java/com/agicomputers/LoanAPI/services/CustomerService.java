package com.agicomputers.LoanAPI.services;

import com.agicomputers.LoanAPI.models.dto.CustomerDTO;
import com.agicomputers.LoanAPI.models.request.CustomerRequest;

import java.util.HashSet;

public interface CustomerService {

    HashSet<CustomerDTO> getAllCustomers();
    HashSet<CustomerDTO> getAllCustomers(String sqlRegex);
    CustomerDTO createCustomer(CustomerDTO cdtoReq);
    CustomerDTO getCustomer(String customerId);

}
