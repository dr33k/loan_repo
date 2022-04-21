package com.agicomputers.LoanAPI.services;

import com.agicomputers.LoanAPI.models.dto.CustomerDTO;
import com.agicomputers.LoanAPI.models.request.CustomerRequest;

import java.util.HashSet;

public interface UserService {

    HashSet<CustomerDTO> getAllUsers();
    //HashSet<CustomerDTO> getAllCustomers(String sqlRegex);
    CustomerDTO createUser(CustomerDTO cdtoReq);
    CustomerDTO getUser(String customerId);
    CustomerDTO getUserWithEmail(String email);
    CustomerDTO updateUser(CustomerDTO cdto);
    Boolean deleteUser(String customerId);
    Boolean emailExists(String email);
    Boolean ninExists(String nin);

}
