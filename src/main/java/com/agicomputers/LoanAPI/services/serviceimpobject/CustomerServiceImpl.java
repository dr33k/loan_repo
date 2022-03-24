package com.agicomputers.LoanAPI.services.serviceimpobject;

import com.agicomputers.LoanAPI.models.dto.CustomerDTO;
import com.agicomputers.LoanAPI.models.entities.Customer;
import com.agicomputers.LoanAPI.repositories.CustomerRepository;
import com.agicomputers.LoanAPI.services.CustomerService;
import com.agicomputers.LoanAPI.tools.generators.CustomerGenerator;
import com.agicomputers.LoanAPI.tools.validators.CustomerValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public HashSet<CustomerDTO> getAllCustomers() {
       Set<CustomerDTO> customers = new HashSet<CustomerDTO>(0);
       Iterable<Customer> customersFromRepo = customerRepository.findAll();

       CustomerDTO cdto;
       Iterator<Customer> iterator = customersFromRepo.iterator();
       while(iterator.hasNext()){
           cdto = new CustomerDTO();
           BeanUtils.copyProperties(iterator.next(),cdto);
           customers.add(cdto);
       }
       return (HashSet<CustomerDTO>)customers;
    }

    @Override
    public HashSet<CustomerDTO> getAllCustomers(String sqlRegex){
        Set<CustomerDTO> customers = new HashSet<CustomerDTO>(0);
        Iterable<Customer> customersFromRepo = customerRepository.findAllWithIdPattern(sqlRegex);

        CustomerDTO cdto;
        Iterator<Customer> iterator = customersFromRepo.iterator();
        while(iterator.hasNext()){
            cdto = new CustomerDTO();
            BeanUtils.copyProperties(iterator.next(),cdto);
            customers.add(cdto);
        }
        return (HashSet<CustomerDTO>)customers;
    }
    @Override
    public CustomerDTO getCustomer(String customerId){
        Optional<Customer> optionalCustomer = customerRepository.findByCustomerId(customerId);
        if (optionalCustomer.isPresent()){
           Customer customer =  optionalCustomer.get();
           CustomerDTO cdto = new CustomerDTO();
           BeanUtils.copyProperties(customer, cdto);
           return cdto;
        }
        else return null;
    }


    @Override
    public CustomerDTO createCustomer(CustomerDTO cdtoReq){
        Customer customer = new Customer(); //Customer Entity
        BeanUtils.copyProperties(cdtoReq,customer);

        customer.setCustomerId(CustomerGenerator.generateCustomerId(cdtoReq.getCustomerFname(),cdtoReq.getCustomerLname()));
        customer.setCustomerPassphrase(passwordEncoder.encode(cdtoReq.getCustomerPassphrase()));
        customerRepository.save(customer);
        BeanUtils.copyProperties(customer,cdtoReq);
        return cdtoReq;
    }
}
