package com.agicomputers.LoanAPI.services.serviceimpobject;

import com.agicomputers.LoanAPI.models.dto.CustomerDTO;
import com.agicomputers.LoanAPI.models.entities.Customer;
import com.agicomputers.LoanAPI.repositories.CustomerRepository;
import com.agicomputers.LoanAPI.services.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService, UserDetailsService {
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
    @Transactional
    public CustomerDTO createCustomer(CustomerDTO cdto) {
        Customer customer = new Customer(); //Customer Entity
        BeanUtils.copyProperties(cdto, customer);

        //Set Customer's  encoded password
        customer.setCustomerPassphrase(passwordEncoder.encode(cdto.getCustomerPassphrase()));
        //Save record
        customerRepository.save(customer);

        //Get Customer's custom generated Id and database generated id
        String generatedCustomerId = generateCustomerId(cdto.getCustomerFname(), cdto.getCustomerLname());
        Long id = getDbId(generatedCustomerId);

        //Update saved record
        customerRepository.updateCustomerId(id,generatedCustomerId);

        //Set Customer's custom generated Id in the Customer object
        customer.setCustomerId(generatedCustomerId);

        //Copy properties into the DTO
        BeanUtils.copyProperties(customer, cdto);
        return cdto;
    }

    @Override
    @Transactional
    public CustomerDTO updateCustomer(CustomerDTO cdto) {
        Long id = getDbId(cdto.getCustomerId());
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if(cdto.getCustomerFname() != null)customer.setCustomerFname(cdto.getCustomerFname());
            if(cdto.getCustomerLname() != null)customer.setCustomerLname(cdto.getCustomerLname());
            if(cdto.getCustomerPhone1() != null)customer.setCustomerPhone1(cdto.getCustomerPhone1());
            if(cdto.getCustomerPhone2() != null)customer.setCustomerPhone2(cdto.getCustomerPhone2());
            if(cdto.getCustomerEmail() != null)customer.setCustomerEmail(cdto.getCustomerEmail());
            if(cdto.getCustomerPassphrase() != null)customer.setCustomerPassphrase(cdto.getCustomerPassphrase());
            if(cdto.getCustomerPassportPhoto() != null)customer.setCustomerPassportPhoto(cdto.getCustomerPassportPhoto());
            if(cdto.getCustomerNIN() != null)customer.setCustomerNIN(cdto.getCustomerNIN());
            if(cdto.getCustomerAddress() != null)customer.setCustomerAddress(cdto.getCustomerAddress());
            if(cdto.getCustomerOccupation() != null)customer.setCustomerOccupation(cdto.getCustomerOccupation());
            if(cdto.getCustomerOccupationLocation() != null)customer.setCustomerOccupationLocation(cdto.getCustomerOccupationLocation());

            customerRepository.save(customer);

            BeanUtils.copyProperties(customer,cdto);
            return cdto;
        }
        return null;
    }

    @Override
    public Boolean emailExists(String email) {
        Optional<Long> idWithEmail = customerRepository.existsByEmail(email);   //Id gotten with the email as a search key
        return idWithEmail.isPresent();
    }

    @Override
    public Boolean ninExists(String nin) {
        Optional<Long> idWithNin = customerRepository.existsByNin(nin);     //Id gotten with the NIN as a search key
        return idWithNin.isPresent();
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
                count = String.valueOf(customerRepository.findLastId());//This returns the most recent (largest) id in the database
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

    //This method returns th Customer (UserDetails) Object directly without the
    //use of a Data Transfer Object
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Extract Database Id for quicker indexed search
        Long id = getDbId(username);

        //Use the CustomerRepository to findBy Id
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return customer;
        }
        return null;
    }
}
