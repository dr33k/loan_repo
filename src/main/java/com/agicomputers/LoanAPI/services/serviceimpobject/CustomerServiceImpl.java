package com.agicomputers.LoanAPI.services.serviceimpobject;

import com.agicomputers.LoanAPI.models.dto.CustomerDTO;
import com.agicomputers.LoanAPI.models.entities.Customer;
import com.agicomputers.LoanAPI.repositories.CustomerRepository;
import com.agicomputers.LoanAPI.services.CustomerService;
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

/*
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

 */
    @Override
    public CustomerDTO getCustomer(String customerId) {
        //Extract Database Id for quicker indexed search
        Long id = getDbId(customerId);

        //Use the CustomerRepository to findBy Id
       Optional<Customer> optionalCustomer = customerRepository.findById(id);
      if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            CustomerDTO cdto = new CustomerDTO();
            BeanUtils.copyProperties(customer, cdto);
            return cdto;
        }
      return null;
    }

    @Override
    public CustomerDTO getCustomerWithEmail(String email) {
        Optional<Customer> customerWithEmail = customerRepository.findByEmail(email);
        if (customerWithEmail.isPresent()){
            Customer customer = customerWithEmail.get();
            CustomerDTO cdto = new CustomerDTO();
            BeanUtils.copyProperties(customer,cdto);
            return cdto;
        }
        else return null;
    }

    @Override
    public Boolean deleteCustomer(String customerId) {
        //Extract Database Id for quicker indexed search
        Long id = getDbId(customerId);
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO cdtoReq) {
        Customer customer = new Customer(); //Customer Entity
        BeanUtils.copyProperties(cdtoReq, customer);

        //Set Customer's custom generated Id and encode password
        customer.setCustomerId(generateCustomerId(cdtoReq.getCustomerFname(), cdtoReq.getCustomerLname()));
        customer.setCustomerPassphrase(passwordEncoder.encode(cdtoReq.getCustomerPassphrase()));

        customerRepository.save(customer);
        BeanUtils.copyProperties(customer, cdtoReq);
        return cdtoReq;
    }

        //alpha and omega represents the customer's first and last names respectively
        private String generateCustomerId(String alpha, String omega) {
            //alpha's second letter
            String alphaSecondLetter = String.valueOf(Character.toUpperCase(alpha.charAt(1)));
            //omicron's second letter
            String omegaSecondLetter = String.valueOf(Character.toUpperCase(omega.charAt(1)));

            //alpha's second letter from the right
            String alphaSecondLetterPrime = String.valueOf(Character.toUpperCase(alpha.charAt(alpha.length() - 2)));
            //omicron's second letter from the right
            String omegaSecondLetterPrime = String.valueOf(Character.toUpperCase(omega.charAt(omega.length() - 2)));
            /*
            String sqlRegex = omegaSecondLetter + alphaSecondLetterPrime + "7" + "%" + "7" + omegaSecondLetterPrime + alphaSecondLetter;
            HashSet<CustomerDTO> matchedUsers = getAllCustomers(sqlRegex);
            int duplicateUserFactor = (matchedUsers.isEmpty()) ? 0 : matchedUsers.size();
            String draft = omegaSecondLetter + alphaSecondLetterPrime + "7" + duplicateUserFactor + "7" + omegaSecondLetterPrime + alphaSecondLetter;

             */
            //Random numbers from 0 to 9
            String numString1 = String.valueOf((int)(Math.random()*10));
            String numString2 = String.valueOf((int)(Math.random()*10));
            //Number of entries in the database + 1
            String count;
            try {
                count = String.valueOf(customerRepository.findLastId() + 1);
            }catch(NullPointerException ex){
                count = "1";
            }

            String newId = omegaSecondLetter
                    .concat(alphaSecondLetterPrime)
                    .concat(numString1)
                    .concat(count)
                    .concat(numString2)
                    .concat(omegaSecondLetterPrime)
                    .concat(alphaSecondLetter);

            return newId;
        }

        private Long getDbId(String customerId){
            String id = customerId.substring(3,customerId.length()-3);
            return Long.valueOf(id);
        }

    }
