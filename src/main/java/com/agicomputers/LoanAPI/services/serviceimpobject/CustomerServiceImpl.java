package com.agicomputers.LoanAPI.services.serviceimpobject;

import com.agicomputers.LoanAPI.models.dto.CustomerDTO;
import com.agicomputers.LoanAPI.models.entities.Customer;
import com.agicomputers.LoanAPI.repositories.CustomerRepository;
import com.agicomputers.LoanAPI.services.CustomerService;
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
        while (iterator.hasNext()) {
            cdto = new CustomerDTO();
            BeanUtils.copyProperties(iterator.next(), cdto);
            customers.add(cdto);
        }
        return (HashSet<CustomerDTO>) customers;
    }

    @Override
    public HashSet<CustomerDTO> getAllCustomers(String sqlRegex) {
        Set<CustomerDTO> customers = new HashSet<CustomerDTO>(0);
        Iterable<Customer> customersFromRepo = customerRepository.findAllWithIdPattern(sqlRegex);

        CustomerDTO cdto;
        Iterator<Customer> iterator = customersFromRepo.iterator();
        while (iterator.hasNext()) {
            cdto = new CustomerDTO();
            BeanUtils.copyProperties(iterator.next(), cdto);
            customers.add(cdto);
        }
        return (HashSet<CustomerDTO>) customers;
    }

    @Override
    public CustomerDTO getCustomer(String customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findByCustomerId(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            CustomerDTO cdto = new CustomerDTO();
            BeanUtils.copyProperties(customer, cdto);
            return cdto;
        } else return null;
    }


    @Override
    public CustomerDTO createCustomer(CustomerDTO cdtoReq) {
        Customer customer = new Customer(); //Customer Entity
        BeanUtils.copyProperties(cdtoReq, customer);

        //Instance of the Inner class Customer Generator
        CustomerGenerator customerGenerator = new CustomerGenerator();
        customer.setCustomerId(customerGenerator.generateCustomerId(cdtoReq.getCustomerFname(), cdtoReq.getCustomerLname()));
        customer.setCustomerPassphrase(passwordEncoder.encode(cdtoReq.getCustomerPassphrase()));

        customerRepository.save(customer);
        BeanUtils.copyProperties(customer, cdtoReq);
        return cdtoReq;
    }

    /*Customer Generator is an Inner class that generates different parts of a Customer's properties*/
    class CustomerGenerator {

        //alpha and omicron represents the customer's first and last names respectively
        public String generateCustomerId(String alpha, String omicron) {

            String alphaSecondLetter = String.valueOf(Character.toUpperCase(alpha.charAt(1)));
            String omicronSecondLetter = String.valueOf(Character.toUpperCase(omicron.charAt(1)));

            String alphaSecondLetterPrime = String.valueOf(Character.toUpperCase(alpha.charAt(alpha.length() - 2)));
            String omicronSecondLetterPrime = String.valueOf(Character.toUpperCase(omicron.charAt(omicron.length() - 2)));

            String sqlRegex = omicronSecondLetter + alphaSecondLetterPrime + "7" + "%" + "7" + omicronSecondLetterPrime + alphaSecondLetter;
            HashSet<CustomerDTO> matchedUsers = getAllCustomers(sqlRegex);
            int duplicateUserFactor = (matchedUsers.isEmpty()) ? 0 : matchedUsers.size();

            String draft = omicronSecondLetter + alphaSecondLetterPrime + "7" + duplicateUserFactor + "7" + omicronSecondLetterPrime + alphaSecondLetter;
            return draft;
        }

    }
}